package com.uma.example.springuma.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    RepositoryMedico repositoryMedico;

    public List<Medico> getAllMedicos(){
        return repositoryMedico.findAll();
    }

    public Medico getMedico(Long id){
        return repositoryMedico.getReferenceById(id);
    }

    public Medico addMedico(Medico m){
        //añadido porque si no, en el controlador Medico nunca entraria en el "cath" si ya existe un medico
        if (repositoryMedico.getMedicoByDni(m.getDni()) != null) {
            throw new IllegalStateException("El medico ya existe");
        }

        return repositoryMedico.saveAndFlush(m);
    }

    public void updateMedico(Medico m){
        //Aqui tambien se añade para el catch
        if (!repositoryMedico.existsById(m.getId())) {
            throw new IllegalStateException("No se puede actualizar: el médico no existe en la base de datos.");
        }
        repositoryMedico.save(m);
    }

    public void removeMedico(Medico m){
        repositoryMedico.delete(m);
    }

    public void removeMedicoID(Long id){
        repositoryMedico.deleteById(id);
    }

    public Medico getMedicoByDni(String dni) {
        return repositoryMedico.getMedicoByDni(dni);
    }
}
