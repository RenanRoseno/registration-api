package com.queonetics.registration.models.dto;

public class VehicleDTOFixture {

    public static VehicleDTO build(
            Long id,
            String plate
    ) {
        return new VehicleDTO(id, plate);
    }
}
