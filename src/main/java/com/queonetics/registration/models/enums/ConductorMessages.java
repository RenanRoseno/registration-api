package com.queonetics.registration.models.enums;

public enum ConductorMessages{
    EXISTING_REGISTRATION("Esta matrícula já existe."),
    SUCCESS_DELETE("Condutor removido com sucesso");

    private String description;

    ConductorMessages(String description){
        this.description = description;
    }

    public String getValue(){
        return this.description;
    }
}