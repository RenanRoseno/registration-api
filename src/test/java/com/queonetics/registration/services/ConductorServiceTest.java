package com.queonetics.registration.services;

import com.queonetics.registration.exceptions.EntityNotFoundException;
import com.queonetics.registration.models.Conductor;
import com.queonetics.registration.models.Vehicle;
import com.queonetics.registration.models.dto.ConductorDTO;
import com.queonetics.registration.models.dto.ConductorDTOFixture;
import com.queonetics.registration.models.dto.VehicleDTO;
import com.queonetics.registration.models.dto.VehicleDTOFixture;
import com.queonetics.registration.repositories.ConductorRepository;
import com.queonetics.registration.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConductorServiceTest {
    @InjectMocks
    ConductorService conductorService;

    @Mock
    ConductorRepository conductorRepository;

    Conductor conductorEntity;

    ConductorDTO conductorDTO;

    Optional<Conductor> conductorOptional;

    private final static Long ID = 1L;

    @BeforeEach
    public void setUp(){
        //Usar Builder
        conductorEntity = new Conductor();
        conductorEntity.setId(1L);
        conductorEntity.setName("USUÁRIO TEST");
        conductorEntity.setRegistration("123456789");

        conductorDTO = ConductorDTOFixture.build(1L, "USUÁRIO TEST", "123456789");
        conductorOptional = Optional.of(conductorEntity);
    }

    @Test
    void mustSaveVehicleSuccess(){
        when(conductorRepository.save(conductorEntity)).thenReturn(conductorEntity);

        ConductorDTO conductorDTOSaved = conductorService.save(conductorDTO);
        assertEquals(this.conductorDTO, conductorDTOSaved);
        verify(conductorRepository).save(conductorEntity);
    }

    @Test
    void mustNotFoundVehicleException(){
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> conductorService.getById(1L));

        assertThat(e, notNullValue());
        assertThat(e.getReason(), is("Não foi possivel encontrar o Condutor com o id igual a 1."));
    }

    @Test
    void mustUpdateVehicleSuccess(){
        when(conductorRepository.findById(ID)).thenReturn(conductorOptional);
        when(conductorRepository.save(conductorEntity)).thenReturn(conductorEntity);

        ConductorDTO conductorDTOSaved = conductorService.update(ID, conductorDTO);
        assertEquals(this.conductorDTO, conductorDTOSaved);
        verify(conductorRepository).findById(ID);
        verify(conductorRepository).save(conductorEntity);
    }

    @Test
    void mustReturnById(){
        when(conductorRepository.findById(ID)).thenReturn(conductorOptional);

        ConductorDTO conductorDTOReturned = conductorService.getById(ID);

        assertEquals(conductorDTO, conductorDTOReturned);
    }

    @Test
    @DisplayName("Excluir condutor")
    void mustDeleteVehicle(){
        when(conductorRepository.findById(ID)).thenReturn(conductorOptional);
        doNothing().when(conductorRepository).deleteById(ID);
        conductorService.delete(ID);

        verify(conductorRepository).deleteById(ID);
    }

}
