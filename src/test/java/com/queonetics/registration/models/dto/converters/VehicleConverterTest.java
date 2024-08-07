package com.queonetics.registration.models.dto.converters;

import com.queonetics.registration.models.Conductor;
import com.queonetics.registration.models.Vehicle;
import com.queonetics.registration.models.dto.VehicleDTO;
import com.queonetics.registration.models.dto.VehicleDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleConverterTest {

    VehicleDTO vehicleDTO;

    Vehicle vehicleEntity;

    @BeforeEach
    public void setUp(){
        //Usar Builder
        vehicleEntity = new Vehicle();
        vehicleEntity.setId(1L);
        vehicleEntity.setPlate("ABC1G34");

        vehicleDTO = new VehicleDTO(vehicleEntity);
    }

    @Test
    void mustConvertToEntity(){
        Vehicle vehicle = vehicleDTO.toEntity();
        assertEquals(vehicleEntity, vehicle);
    }
}
