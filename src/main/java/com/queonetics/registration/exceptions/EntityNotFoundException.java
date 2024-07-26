package com.queonetics.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(Long id, Boolean isVehicle) {
        super(HttpStatus.NOT_FOUND, String.format("Não foi possivel encontrar o %s com o id igual a %s.",
                isVehicle ? "Veículo" : "Condutor",
                id));

    }

}
