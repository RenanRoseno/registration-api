package com.queonetics.registration.controllers;

import com.queonetics.registration.handler.ResponseHandler;
import com.queonetics.registration.models.dto.ConductorDTO;
import com.queonetics.registration.messages.ConductorMessages;
import com.queonetics.registration.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conductors")
public class ConductorController {

    @Autowired
    ConductorService conductorService;

    @PostMapping
    public ConductorDTO save(@RequestBody ConductorDTO conductorDTO) {
        return conductorService.save(conductorDTO);
    }

    @GetMapping
    public List<ConductorDTO> listAllConductors() {
        return conductorService.listAll();
    }

    @GetMapping("{id}")
    public ConductorDTO getByid(@PathVariable Long id) {
        return conductorService.getById(id);
    }

    @PutMapping("{id}")
    public ConductorDTO update(@PathVariable Long id, @RequestBody ConductorDTO conductorDTO) {
        return conductorService.update(id, conductorDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        conductorService.delete(id);
        return ResponseHandler.generateResponse(ConductorMessages.SUCCESS_DELETE.getValue(), HttpStatus.OK, null);
    }
}
