package com.mercadolibre.usescases;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import com.mercadolibre.domains.ports.FindMutant;
import com.mercadolibre.domains.models.MutantModel;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = com.mercadolibre.MutantApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "dev")
class MutantUseCaseTest {

    @Autowired
    private FindMutant findMutant;

    @Test
    void findMutantTestSuccessVertical() {
        MutantModel mutantModel = new MutantModel(Arrays.asList("ATGCGA","AAGTGC","ATATGT","AGAAGG","AACATA","TCACTG"));
        StepVerifier.create(findMutant.isMutant(Mono.just(mutantModel)))
        .expectNext(true)
        .verifyComplete();
    }

    @Test
    void findMutantTestSuccessHorizontal() {
        MutantModel mutantModel = new MutantModel(Arrays.asList("TTTTGT","AAAAGC","TTTTGT","CTGCGA","ACACTG","TCACTG"));
        StepVerifier.create(findMutant.isMutant(Mono.just(mutantModel)))
        .expectNext(true)
        .verifyComplete();
    }

    @Test
    void findMutantDiagonalLeftToBottom() {
        MutantModel mutantModel = new MutantModel(Arrays.asList("TATAGT","CTGTGC","TATAGT","CTGTGA","ACACTG","TCACTG"));
        StepVerifier.create(findMutant.isMutant(Mono.just(mutantModel)))
        .expectNext(true)
        .verifyComplete();
    }

    @Test
    void findNoMutant() {
        MutantModel mutantModel = new MutantModel(Arrays.asList("TATAGT","CAGTGC","TATAGT","CTGCGA","ACACTG","TCACTG"));
        StepVerifier.create(findMutant.isMutant(Mono.just(mutantModel)))
        .expectNext(false)
        .verifyComplete();
    }
    
}
