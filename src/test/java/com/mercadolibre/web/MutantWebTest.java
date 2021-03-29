package com.mercadolibre.web;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import com.mercadolibre.adapter.web.mapper.StatusTransaction;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import com.mercadolibre.domains.models.MutantModel;
import java.util.Arrays;
import org.springframework.web.reactive.function.BodyInserters;
import org.junit.jupiter.api.BeforeEach;
import com.mercadolibre.adapter.persistence.mapper.StatsEntity;
import com.mercadolibre.adapter.persistence.config.IPersistenceMongoManager;
import com.mercadolibre.adapter.web.mapper.StatResponse;

import static com.mercadolibre.common.utility.ConstantManager.HEALTH_PATH;
import static com.mercadolibre.common.utility.ConstantManager.FIND_MUTANT_PATH;
import static com.mercadolibre.common.utility.ConstantManager.STATS_PATH;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = com.mercadolibre.MutantApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "dev")
class MutantWebTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutantWebTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private IPersistenceMongoManager<StatsEntity> manager;

    @BeforeEach
    void setup(){
        manager.deleteAll(StatsEntity.class).all().subscribe();
    }

    @Test
    void healthTest() {
        var obj = new ObjectMapper();
        webTestClient.get().uri(uriBuilder -> uriBuilder.path(HEALTH_PATH).build()).exchange().expectStatus()
        .isOk().expectBody(StatusTransaction.class).value(body -> {
            try {
                LOGGER.info("Json Result -> {}", obj.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Result -> {}", e.getMessage());
            }
            Assertions.assertEquals(body.getMessages(), "Eureka!!");
        });
    }

    @Test
    void findMutant() {
        var mutantModel1 = new MutantModel(Arrays.asList("AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA"));
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel1))
        .exchange()
        .expectStatus()
        .isOk();

        var mutantModel2 = new MutantModel(Arrays.asList("AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA"));
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel2))
        .exchange()
        .expectStatus()
        .isOk();
    }

    @Test
    void findNoMutant() {
        var mutantModel = new MutantModel(Arrays.asList("TATAGT","CAGTGC","TATAGT","CTGCGA","ACACTG","TCACTG"));
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel))
        .exchange()
        .expectStatus()
        .isForbidden();
    }

    @Test
    void findMutantWhenSendNull() {
        var obj = new ObjectMapper();
        MutantModel mutantModel = new MutantModel();
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel))
        .exchange()
        .expectStatus()
        .isBadRequest().expectBody(StatusTransaction.class).value(body -> {
            try {
                LOGGER.info("Json Result -> {}", obj.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Result -> {}", e.getMessage());
            }
        });
    }

    @Test
    void findMutantErrorSequence() {
        var obj = new ObjectMapper();
        var mutantModel = new MutantModel(Arrays.asList("TTT","TTT","TTT"));
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel))
        .exchange()
        .expectStatus()
        .isBadRequest().expectBody(StatusTransaction.class).value(body -> {
            try {
                LOGGER.info("Json Result -> {}", obj.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Result -> {}", e.getMessage());
            }
        });
    }

    @Test
    void findMutantErrorMatrix() {
        var obj = new ObjectMapper();
        var mutantModel = new MutantModel(Arrays.asList("TTT","TT","TTT"));
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel))
        .exchange()
        .expectStatus()
        .isBadRequest().expectBody(StatusTransaction.class).value(body -> {
            try {
                LOGGER.info("Json Result -> {}", obj.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Result -> {}", e.getMessage());
            }
        });
    }

    @Test
    void findMutantErrorDifferenceLetter() {
        var obj = new ObjectMapper();
        var mutantModel = new MutantModel(Arrays.asList("ZXSSSS","ZXSSSS","ZXSSSS", "ZXSSSS", "ZXSSSS", "ZXSSSS"));
        webTestClient.post().uri(uriBuilder -> uriBuilder.path(FIND_MUTANT_PATH).build())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mutantModel))
        .exchange()
        .expectStatus()
        .isBadRequest().expectBody(StatusTransaction.class).value(body -> {
            try {
                LOGGER.info("Json Result -> {}", obj.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Result -> {}", e.getMessage());
            }
        });
    }

    @Test
    void getStatMutantAndHuman() {
        var obj = new ObjectMapper();
        webTestClient.get().uri(uriBuilder -> uriBuilder.path(STATS_PATH).build())
        .exchange().expectStatus()
        .isOk().expectBody(StatResponse.class).value(body -> {
            try {
                LOGGER.info("Json Result -> {}", obj.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error Result -> {}", e.getMessage());
            }
            Assertions.assertEquals(body.getCountHumanDna(), 0.0);
        });
    }
}
