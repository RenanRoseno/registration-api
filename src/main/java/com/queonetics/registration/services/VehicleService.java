package com.queonetics.registration.services;

import com.queonetics.registration.exceptions.EntityNotFoundException;
import com.queonetics.registration.messages.ConductorMessages;
import com.queonetics.registration.messages.VehicleMessages;
import com.queonetics.registration.models.Vehicle;
import com.queonetics.registration.models.dto.VehicleDTO;
import com.queonetics.registration.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    private final Boolean isVehicle = true;

    public VehicleDTO save(VehicleDTO vehicleDTO) {
        if (!vehicleRepository.existsVehiclesByPlate(vehicleDTO.getPlate())) {
            this.isValidPlate(vehicleDTO.getPlate());
            Vehicle entitySaved = vehicleRepository.save(vehicleDTO.toEntity());
            vehicleDTO.setId(entitySaved.getId());
            return vehicleDTO;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VehicleMessages.EXISTING_REGISTRATION.getValue());

    }

    public List<VehicleDTO> listAll() {
        return this.vehicleRepository.findAll().stream().map(VehicleDTO::new).collect(Collectors.toList());
    }

    public VehicleDTO getById(Long id) {
        Optional<Vehicle> optionalVehicle = this.vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            return new VehicleDTO(optionalVehicle.get());
        }

        throw new EntityNotFoundException(id, isVehicle);
    }

    public VehicleDTO update(Long id, VehicleDTO vehicleDTO) {
        validateExistingConductor(id, vehicleDTO.getPlate());
        this.isValidPlate(vehicleDTO.getPlate());
        return this.vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle = vehicleDTO.toEntity();
                    vehicle.setId(id);
                    return new VehicleDTO(vehicleRepository.save(vehicle));
                })
                .orElseThrow(() -> new EntityNotFoundException(id, isVehicle));
    }

    public void validateExistingConductor(Long id, String registration) {
        if (vehicleRepository.getExistingVehicleExceptId(id, registration) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ConductorMessages.EXISTING_REGISTRATION.getValue());
    }

    public void delete(Long id) {
        if (vehicleRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id, isVehicle);
        vehicleRepository.deleteById(id);
    }

    private boolean validateOldPattern(String plate) {
        Pattern pattern = Pattern.compile("[A-Z]{3}-\\d{4}");
        Matcher mat = pattern.matcher(plate);
        return mat.matches();
    }

    private boolean validateNewPattern(String plate) {
        Pattern pattern = Pattern.compile("[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}[0-9]{4}");
        Matcher mat = pattern.matcher(plate);
        return mat.matches();
    }

    private void isValidPlate(String plate){
        if(!this.validateNewPattern(plate) && !this.validateOldPattern(plate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Placa está com informato incorreto");
    }
}
