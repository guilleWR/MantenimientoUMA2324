// Westerhof Rodríguez Guillermo Alejandro
// Rueda Cabrera Pedro

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;




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
    @DisplayName("getMetido devuelve el medico correctamente dado un id de un medico ya existente en la base de datos")
    public void getMedico_MedicoSolicitadoPorId_DevuelveMedico() throws Exception {
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

    @Test
    @DisplayName("getMetido devuelve el medico correctamente dado un dni de un medico ya existente en la base de datos")
    public void getMedico_MedicoSolicitadoPorDni_DevuelveMedico() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        saveMedico(medico);

        this.mockMvc.perform(get("/medico/dni/122"))
        .andExpect(status().isOk()) 
        .andExpect(jsonPath("$.nombre").value("Paco"))
        .andExpect(jsonPath("$.dni").value("122"))
        .andExpect(jsonPath("$.especialidad").value("Oncología"));
    }
    
    @Test
    @DisplayName("getMedicoByDni devuelve 404 dado un dni que no existe en la base de datos")
    public void getMedico_MedicoSolicitadoPorDniPeroNoExiste_DevuelveNoEncontrado() throws Exception {
       
        this.mockMvc.perform(get("/medico/dni/999"))
            .andExpect(status().isNotFound());
    }
 
    @Test
    @DisplayName("Cuando se quiere borrar un medico que no existe genera un internal server error")
    public void deleteMedico_MedicoNoExiste_DevuelveInternalServerError() throws Exception {
        Long idNoExistente = 999L;

        this.mockMvc.perform(delete("/medico/{id}", idNoExistente))
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Cuando se borra un medico existente borra el medico especificado")
    public void deleteMedico_MedicoConDniExistente_BorraMedico() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        saveMedico(medico);

        this.mockMvc.perform(delete("/medico/{id}", medico.getId()))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Cuando se quiere guardar un medico que no existe, lo guarda en la base de datos")
    public void saveMedico_MedicoNoExistente_GuardaMedico() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

         this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cuando se quiere guardar un medico ya existente genera un internal server error")
    public void saveMedico_MedicoYaExistente_GeneraInternalServerError() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isCreated());

        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isInternalServerError());

        // Supuestamente cuando guardamos un medico que YA EXISTIA deberia devolver un internal server error
        // Pero el metodo addMedico solo hace repositoryMedico.saveAndFlush.
        // Entonces se debe añadir una condicion para comprobar si dicho medico esta o no en la base de datos
        // Y si esta, lanzar una excepcion y esa se capturaria en el controlador y se generaria un error 500
    }


    @Test
    @DisplayName("Cuando se quiere actualizar un medico que no existe genera un internal server error")
    public void updateMedico_MedicoNoExiste_DevuelveInternalServerError() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        //no lo guardamos en la base de datos con save

        this.mockMvc.perform(put("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Cuando se quiere actualizar un medico que si existe, se actualizan sus datos")
    public void updateMedico_MedicoYaExiste_ActualizaDatosMedico() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Paco");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        saveMedico(medico);

        this.mockMvc.perform(put("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isNoContent());
    }
}
