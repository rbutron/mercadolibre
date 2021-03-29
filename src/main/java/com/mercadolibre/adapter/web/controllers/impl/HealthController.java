package com.mercadolibre.adapter.web.controllers.impl;

import com.mercadolibre.adapter.web.controllers.IHealthController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import com.mercadolibre.adapter.web.mapper.StatusTransaction;
import com.mercadolibre.common.utility.ConstantManager;
import org.springframework.stereotype.Controller;

@Controller
public class HealthController implements IHealthController {

    @Override
    public Mono<ServerResponse> health(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
               .body(BodyInserters.fromValue(new StatusTransaction(ConstantManager.MSG_HEALTH, HttpStatus.OK)));
    }
}
