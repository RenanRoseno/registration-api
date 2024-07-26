package com.queonetics.registration.services;

import com.queonetics.registration.exception.EntityNotFoundException;
import com.queonetics.registration.models.Conductor;
import com.queonetics.registration.models.dto.ConductorDTO;
import com.queonetics.registration.models.enums.ConductorMessages;
import com.queonetics.registration.repositories.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConductorService {
    @Autowired
    ConductorRepository conductorRepository;

    private final Boolean isVehicle = false;

    public ConductorDTO save(ConductorDTO conductorDTO) {
        if (!conductorRepository.existsConductorBYRegistration(conductorDTO.getRegistration())) {
            Conductor entitySaved = conductorRepository.save(conductorDTO.toEntity());
            conductorDTO.setId(entitySaved.getId());
            return conductorDTO;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConductorMessages.EXISTING_REGISTRATION.getValue());

    }

    public List<ConductorDTO> listAll() {
        return this.conductorRepository.findAll().stream().map(ConductorDTO::new).collect(Collectors.toList());
    }

    public ConductorDTO getById(Long id) {
        Optional<Conductor> optionalConductor = this.conductorRepository.findById(id);
        if (optionalConductor.isPresent()) {
            return new ConductorDTO(optionalConductor.get());
        }
        throw new EntityNotFoundException(id, isVehicle);
    }

    public ConductorDTO update(Long id, ConductorDTO conductorDTO) {
        validateExistingConductor(id, conductorDTO.getRegistration());
        return this.conductorRepository.findById(id)
                .map(conductor -> {
                    conductor = conductorDTO.toEntity();
                    conductor.setId(id);
                    return new ConductorDTO(conductorRepository.save(conductor));
                })
                .orElseThrow(() -> new EntityNotFoundException(id, isVehicle));
    }

    public void delete(Long id) {
        if (conductorRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id, isVehicle);
        conductorRepository.deleteById(id);
    }

    public void validateExistingConductor(Long id, String registration) {
        if (conductorRepository.getExistingRegistrationExceptId(id, registration) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ConductorMessages.EXISTING_REGISTRATION.getValue());
    }
}
