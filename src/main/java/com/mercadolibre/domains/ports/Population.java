package com.mercadolibre.domains.ports;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import com.mercadolibre.domains.models.HumanModel;

public interface Population {

    Mono<HumanModel> getOneStats(String humanDna);
    Flux<HumanModel> getStats(boolean humanDna);
    Mono<HumanModel> save(HumanModel model);

}
