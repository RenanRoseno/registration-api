package com.queonetics.registration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.queonetics.registration.models.dto.ConductorDTO;
import com.queonetics.registration.models.dto.ConductorDTOFixture;
import com.queonetics.registration.services.ConductorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ConductorControllerTest {
    @InjectMocks
    ConductorController conductorController;

    @Mock
    ConductorService conductorService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;

    private ConductorDTO conductorDTO;

    private String json;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(conductorController).alwaysDo((print())).build();
        url = "/conductors";
        conductorDTO = ConductorDTOFixture.build(1L, "USUÁRIO TEST", "123456789");
        json = objectMapper.writeValueAsString(conductorDTO);
    }

    @Test
    void mustSaveConductorSuccess() throws Exception {
        when(conductorService.save(conductorDTO)).thenReturn(conductorDTO);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.json)
        ).andExpect(status().isOk());

        verify(conductorService).save(conductorDTO);
    }

    @Test
    void mustNotSaveConductorSuccess() throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(conductorService);
    }

    @Test
    void mustUpdateConductorSuccess() throws Exception {
        when(conductorService.update(1L, conductorDTO)).thenReturn(conductorDTO);

        mockMvc.perform(put("/conductors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(conductorService).update(1L, conductorDTO);
    }

    @Test
    void mustNotUpdateConductorSuccess() throws Exception {
        mockMvc.perform(put("/conductors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(conductorService);
    }

    @Test
    void mustGetConductorByIdSuccess() throws Exception {
        when(conductorService.getById(1L)).thenReturn(conductorDTO);

        mockMvc.perform(get("/conductors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(conductorService).getById(1L);
    }

    @Test
    void mustDeleteConductor() throws Exception {
        doNothing().when(conductorService).delete(1L);

        mockMvc.perform(delete("/conductors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(conductorService).delete(1L);
    }

    @Test
    void mustNotDeleteConductor() throws Exception {
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(conductorService);
    }
}
