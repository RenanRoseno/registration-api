package com.queonetics.registration.models.dto;

public class ConductorDTOFixture {

    public static ConductorDTO build(
            Long id,
            String name,
            String registration
    ) {
        return new ConductorDTO(id, name, registration);
    }
}
