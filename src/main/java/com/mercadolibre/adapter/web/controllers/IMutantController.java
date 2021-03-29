package com.mercadolibre.adapter.web.controllers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
public interface IMutantController {

    Mono<ServerResponse> isMutant(ServerRequest request);
    Mono<ServerResponse> stats(ServerRequest request);

}
