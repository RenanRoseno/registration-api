package com.queonetics.registration.models.dto.converters;

import com.queonetics.registration.models.Conductor;
import com.queonetics.registration.models.dto.ConductorDTO;
import com.queonetics.registration.models.dto.ConductorDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConductorConverterTest {

    ConductorDTO conductorDTO;

    Conductor conductorEntity;

    @BeforeEach
    public void setUp(){
        //Usar Builder
        conductorEntity = new Conductor();
        conductorEntity.setId(1L);
        conductorEntity.setName("USUÁRIO TEST");
        conductorEntity.setRegistration("123456789");

        conductorDTO = ConductorDTOFixture.build(1L, "USUÁRIO TEST", "123456789");
    }

    @Test
    void mustConvertToEntity(){
        Conductor conductor = conductorDTO.toEntity();
        assertEquals(conductorEntity, conductor);
    }

}
