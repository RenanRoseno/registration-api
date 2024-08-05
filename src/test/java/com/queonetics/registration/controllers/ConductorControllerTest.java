package com.queonetics.registration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.queonetics.registration.models.dto.VehicleDTO;
import com.queonetics.registration.models.dto.VehicleDTOFixture;
import com.queonetics.registration.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConductorControllerTest {
    @InjectMocks
    ConductorController conductorController;

    @Mock
    VehicleService vehicleService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;

    private VehicleDTO vehicleDTO;

    private String json;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).alwaysDo((print())).build();
        url = "/vehicles";
        vehicleDTO = VehicleDTOFixture.build(1L, "ABC1G34");
        json = objectMapper.writeValueAsString(vehicleDTO);
    }

    @Test
    void mustSaveVehicleSuccess() throws Exception {
        when(vehicleService.save(vehicleDTO)).thenReturn(vehicleDTO);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.json)
        ).andExpect(status().isOk());

        verify(vehicleService).save(vehicleDTO);
    }

    @Test
    void mustNotSaveVehicleException() throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(vehicleService);
    }

    @Test
    void mustUpdateVehicleSuccess() throws Exception {
        when(vehicleService.update(1L, vehicleDTO)).thenReturn(vehicleDTO);

        mockMvc.perform(put("/vehicles/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(vehicleService).update(1L, vehicleDTO);
    }

    @Test
    void mustNotUpdateVehicleException() throws Exception {
        mockMvc.perform(put("/vehicles/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(vehicleService);
    }

    @Test
    void mustGetVehicleSByPlateSuccess() throws Exception {
        when(vehicleService.getVehicleByPlate("ABC1G34")).thenReturn(vehicleDTO);

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("plate", "ABC1G34")
                .content(json)
        ).andExpect(status().isOk());

        verify(vehicleService).getVehicleByPlate("ABC1G34");
    }

    @Test
    void mustNotGetVehicleSByPlateSuccess() throws Exception {
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(vehicleService);
    }

    @Test
    void mustDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).delete(1L);

        mockMvc.perform(delete("/vehicles/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(vehicleService).delete(1L);
    }

    @Test
    void mustNotDeleteVehicle() throws Exception {
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(vehicleService);
    }
}
