package com.uma.example.springuma.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MedicoControllerIT extends AbstractIntegration {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private void saveMedico(Medico medico) throws Exception {
        //guardamos medico en base de datos (post)
        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("")
    public void prueba() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        saveMedico(medico);

        this.mockMvc.perform(get("/medico/1"))
        .andExpect(status().isOk()) 
        .andExpect(jsonPath("$.nombre").value("Paco"))
        .andExpect(jsonPath("$.dni").value("122"))
        .andExpect(jsonPath("$.especialidad").value("Oncología"));
    }
    

}
