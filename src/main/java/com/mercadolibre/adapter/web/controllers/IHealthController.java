package com.mercadolibre.adapter.web.controllers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IHealthController {

    Mono<ServerResponse> health(ServerRequest request);

}
