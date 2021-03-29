package com.mercadolibre.domains.ports;

import reactor.core.publisher.Mono;
import com.mercadolibre.domains.models.MutantModel;
import com.mercadolibre.domains.models.StatModel;

public interface Stat {
    Mono<Boolean> dna(boolean isMutant, Mono<MutantModel> mutantModel);
    Mono<StatModel> report();
}
