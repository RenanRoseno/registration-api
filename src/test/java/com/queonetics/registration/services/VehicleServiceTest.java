package com.queonetics.registration.services;

import com.queonetics.registration.exceptions.EntityNotFoundException;
import com.queonetics.registration.models.Vehicle;
import com.queonetics.registration.models.dto.VehicleDTO;
import com.queonetics.registration.models.dto.VehicleDTOFixture;
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

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    VehicleService vehicleService;

    @Mock
    VehicleRepository vehicleRepository;

    Vehicle vehicleEntity;

    VehicleDTO vehicleDTO;

    Optional<Vehicle> vehicleOptional;

    private final static Long ID = 1L;

    @BeforeEach
    public void setUp(){
        //Usar Builder
        vehicleEntity = new Vehicle();
        vehicleEntity.setId(1L);
        vehicleEntity.setPlate("ABC1G34");

        vehicleDTO = VehicleDTOFixture.build(1L, "ABC1G34");
        vehicleOptional = Optional.of(vehicleEntity);
    }

    @Test
    void mustSaveVehicleSuccess(){
        when(vehicleRepository.save(vehicleEntity)).thenReturn(vehicleEntity);

        VehicleDTO vehicleDTOSaved = vehicleService.save(vehicleDTO);
        assertEquals(this.vehicleDTO, vehicleDTOSaved);
        verify(vehicleRepository).save(vehicleEntity);
    }

    @Test
    void mustNotSaveVehicle(){
        vehicleDTO.setPlate("SA09SAAAA");
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> vehicleService.save(vehicleDTO));

        assertThat(e, notNullValue());
        assertThat(e.getReason(), is("Placa está com informato incorreto"));
    }

    @Test
    void mustNotFoundVehicleException(){
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> vehicleService.getById(1L));

        assertThat(e, notNullValue());
        assertThat(e.getReason(), is("Não foi possivel encontrar o Veículo com o id igual a 1."));
    }

    @Test
    void mustUpdateVehicleSuccess(){
        when(vehicleRepository.findById(ID)).thenReturn(vehicleOptional);
        when(vehicleRepository.save(vehicleEntity)).thenReturn(vehicleEntity);

        VehicleDTO vehicleDTOSaved = vehicleService.update(ID, vehicleDTO);
        assertEquals(this.vehicleDTO, vehicleDTOSaved);
        verify(vehicleRepository).findById(ID);
        verify(vehicleRepository).save(vehicleEntity);
    }

    @Test
    void mustReturnById(){
        when(vehicleRepository.findById(ID)).thenReturn(vehicleOptional);

        VehicleDTO vehicleReturned = vehicleService.getById(ID);

        assertEquals(vehicleDTO, vehicleReturned);
    }

    @Test
    @DisplayName("Excluir veículo")
    void mustDeleteVehicle(){
        when(vehicleRepository.findById(ID)).thenReturn(vehicleOptional);
        doNothing().when(vehicleRepository).deleteById(ID);
        vehicleService.delete(ID);

        verify(vehicleRepository).deleteById(ID);
    }
}
