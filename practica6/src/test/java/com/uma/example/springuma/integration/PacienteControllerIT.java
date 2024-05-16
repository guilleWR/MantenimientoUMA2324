package com.uma.example.springuma.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

public class PacienteControllerIT extends AbstractIntegration {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Medico medico;

    private Paciente paciente;

    @BeforeEach
    public void init(){
        medico = new Medico();
        medico.setNombre("Legolas");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("211");
        paciente.setEdad(20);
        paciente.setCita("Lean One Piece");
        paciente.setNombre("Aragorn");
        paciente.setMedico(medico);
    }

    private void savePaciente(Paciente pacientito) throws Exception {
        //guardamos medico en base de datos (post)
        this.mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(pacientito)))
        .andExpect(status().isCreated());
    }

   
    private void saveMedico(Medico med) throws JsonProcessingException, Exception {       
        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(med)))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("getPaciente devuelve el paciente dado un id correctamente de un paciente ya creado")
    public void getPaciente_PacienteSolicitadoPorId_DevuelvePaciente() throws Exception{
        
        //Hago un post del medico
        saveMedico(medico);
        //Hago un post del paciente
        savePaciente(paciente);

        this.mockMvc.perform(get("/paciente/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dni").value("211"))
        .andExpect(jsonPath("$.nombre").value("Aragorn"))
        .andExpect(jsonPath("$.edad").value(20))
        .andExpect(jsonPath("$.cita").value("Lean One Piece"))
        .andExpect(jsonPath("$.medico.id").value(medico.getId()));
        // compruebo el campo "$.medico.id, por que no funciona el comparar directamente el medico a pesar de que son iguales
    
    }

    @Test
    @DisplayName("getMedicoByDni devuelve 404 dado un dni que no existe en la base de datos")
    public void getPaciente_PacienteSolicitadoPorIdNoExiste_DevuelveInternalServerError() throws Exception { 
        this.mockMvc.perform(get("/paciente/404"))
            .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Devuelve una lista de pacientes asignados a un determinado medico")
    public void getPacientes_AsignadosAUnMedicoPorId_DevuelvelListaPacientes() throws Exception{
        //Hago un post del medico
        saveMedico(medico);
        //Hago un post del paciente
        savePaciente(paciente);

        this.mockMvc.perform(get("/paciente/medico/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0]").value(paciente));
    }


    @Test
    @DisplayName("")
    public void updateMedicoPaciente_PacienteConMedicoYaAsignado_ActualizaElMedicoDePaciente() throws JsonProcessingException, Exception{
        //Hago un post del medico
        saveMedico(medico);
        //Hago un post del paciente
        savePaciente(paciente);

        Medico nuevoMedico = new Medico();
        nuevoMedico.setNombre("elnuevo");
        nuevoMedico.setId(2);

        saveMedico(nuevoMedico);

        paciente.setMedico(nuevoMedico);

        this.mockMvc.perform(put("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/paciente/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.medico.id").value(nuevoMedico.getId()));


    }
    
    @Test
    @DisplayName("getMedicoByDni devuelve 404 dado un dni que no existe en la base de datos")
    public void updateMedicoPaciente_PacienteNoExistente_DevuelveInternalServerError() throws Exception { 
        
        this.mockMvc.perform(put("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("")
    public void DeletePaciente_PacienteYaExistente_EliminaElPaciente() throws Exception{
        //Hago un post del medico
        saveMedico(medico);
        //Hago un post del paciente
        savePaciente(paciente);

        this.mockMvc.perform(delete("/paciente/{id}", paciente.getId()))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("")
    public void DeletePaciente_PacienteNoExistente_DevuelveInternalServerError() throws Exception{
        Long id = 404L;

        this.mockMvc.perform(delete("/medico/{id}", id))
            .andExpect(status().isInternalServerError());
    }


}
