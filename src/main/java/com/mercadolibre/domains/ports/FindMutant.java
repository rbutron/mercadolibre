package com.mercadolibre.domains.ports;

import reactor.core.publisher.Mono;
import com.mercadolibre.domains.models.MutantModel;

public interface FindMutant {

    Mono<Boolean> isMutant(Mono<MutantModel> model);

}
