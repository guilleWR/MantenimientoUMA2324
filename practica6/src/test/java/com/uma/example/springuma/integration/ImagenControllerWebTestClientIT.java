package com.uma.example.springuma.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.Duration;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.http.MediaType;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {
    
    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    private Imagen imagen;

    private Medico medico;

    private Paciente paciente;

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
    @DisplayName("Subo una imagen a un paciente ya existente y esta se sube correctamente")
	void subirImagen_APacienteYaExistente_SubeLaImagenCorrectamente() throws Exception {
          
        File uploadFile = new File("./src/test/resources/healthy.png");
  
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadFile));
        builder.part("paciente",paciente);

        //Subo la foto de la mamografía a un paciente
        client.post()
        .uri("/imagen")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchange()
        .expectStatus().is2xxSuccessful().returnResult(String.class);
    
        client.get().uri("/imagen/1")
        .exchange().expectHeader().contentType("image/png");
    }

    @Test
    @DisplayName("Hago una prediccion de una imagen sana ya asignada a un paciente devuelve que no tiene cancer")
	void prediccionImagen_APacienteYaExistenteConImagenSana_DevuelveQueNoTieneCancer() throws Exception {

        File uploadFile = new File("./src/test/resources/healthy.png");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadFile));
        builder.part("paciente",paciente);

        // submit the logo
        client.post()
        .uri("/imagen")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchange()
        .expectStatus().is2xxSuccessful().returnResult(String.class);

        client.get().uri("/imagen/1")
        .exchange().expectHeader().contentType("image/png");
        
        client.get().uri("/imagen/predict/1")
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.prediction").value(Matchers.containsString("Not cancer (label 0)"));
    }

    @Test
    @DisplayName("Hago una prediccion de una imagen no sana ya asignada a un paciente devuelve que tiene cancer")
	void prediccionImagen_APacienteYaExistenteConImagenNoSana_DevuelveQueTieneCancer() throws Exception {

        File uploadFile = new File("./src/test/resources/no_healthty.png");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadFile));
        builder.part("paciente",paciente);

        // submit the logo
        client.post()
        .uri("/imagen")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchange()
        .expectStatus().is2xxSuccessful().returnResult(String.class);

        client.get().uri("/imagen/1")
        .exchange().expectHeader().contentType("image/png");
        
        client.get().uri("/imagen/predict/1")
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.prediction").value(Matchers.containsString("Cancer (label 1)"));
    }

}
