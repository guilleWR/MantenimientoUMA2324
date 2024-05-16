package com.uma.example.springuma.integration;

import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InformeControllerWenTestClientIT {
    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    private Imagen imagen;

    private Medico medico;

    private Paciente paciente;

    private Informe informe;


    @PostConstruct
    public void init(){
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofMillis(30000)).build();

        //Inicializo el medico
        medico = new Medico();
        medico.setNombre("Legolas");
        medico.setDni("122");
        medico.setEspecialidad("Oncología");
        medico.setId(1);

        //Inicializo el paciente
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("211");
        paciente.setEdad(20);
        paciente.setCita("Lean One Piece");
        paciente.setNombre("Aragorn");
        paciente.setMedico(medico);

        //Subo el medico al WebClient
        client.post().uri("/medico")
            .body(Mono.just(medico), Medico.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();
        
        //Subo el paciente al webClient
        client.post().uri("/paciente")
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();
    }


    @Test
    @DisplayName("Crear un informe para una imagen y obtenerlo")
    void crearInforme_ParaUnaImagenYaExistente_CreaElInforme() {
        
        //Asigno un id a la imagen
        Imagen imagen= new Imagen();
        imagen.setId(1);
        imagen.setPaciente(paciente);

        File uploadFile = new File("./src/test/resources/healthy.png");
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadFile));
        builder.part("paciente", paciente);
        
        // Subir imagen
        client.post()
            .uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful().returnResult(String.class);

        // Inicializar informe
        informe = new Informe();
        informe.setId(3L);
        informe.setContenido("Hola soy un informe");
        informe.setImagen(imagen);

        // Crear informe
        client.post().uri("/informe")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(informe), Informe.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();

        // Obtener informes relacionados con la imagen
        client.get().uri("/informe/imagen/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].contenido").isEqualTo("Hola soy un informe");
    }


    @Test
    @DisplayName("borrar un informe ya existente")
    void borrarInforme_InformeYaExistente_EliminaElInforme() {
        
        //Asigno un id a la imagen
        Imagen imagen= new Imagen();
        imagen.setId(1);
        imagen.setPaciente(paciente);

        File uploadFile = new File("./src/test/resources/healthy.png");
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadFile));
        builder.part("paciente", paciente);
        
        // Subir imagen
        client.post()
            .uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful().returnResult(String.class);

        // Inicializar informe
        informe = new Informe();
        informe.setId(1L);
        informe.setContenido("Hola soy un informe");
        informe.setImagen(imagen);

        // Crear informe
        client.post().uri("/informe")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(informe), Informe.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();

       //Borrar informe 
        client.delete().uri("/informe/1")
        .exchange()
        .expectStatus()
        .isNoContent();
        
    }
}

