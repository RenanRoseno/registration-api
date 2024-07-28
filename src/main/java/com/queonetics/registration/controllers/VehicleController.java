package com.queonetics.registration.controllers;

import com.queonetics.registration.handler.ResponseHandler;
import com.queonetics.registration.messages.VehicleMessages;
import com.queonetics.registration.models.dto.VehicleDTO;
import com.queonetics.registration.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicles")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @PostMapping
    public VehicleDTO save(@RequestBody VehicleDTO vehicleDTO) {
        return vehicleService.save(vehicleDTO);
    }

    @GetMapping
    public VehicleDTO getVehicleByPlate(@RequestParam String plate) {
        return vehicleService.getVehicleByPlate(plate);
    }

    @GetMapping("{id}")
    public VehicleDTO getByid(@PathVariable Long id) {
        return vehicleService.getById(id);
    }

    @PutMapping("{id}")
    public VehicleDTO update(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
        return vehicleService.update(id, vehicleDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseHandler.generateResponse(VehicleMessages.SUCCESS_DELETE.getValue(), HttpStatus.OK, null);
    }
}
