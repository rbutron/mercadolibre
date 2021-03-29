package com.mercadolibre.adapter.web.controllers.impl;

import com.mercadolibre.adapter.web.controllers.IMutantController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mercadolibre.domains.models.MutantModel;
import org.springframework.stereotype.Controller;
import com.mercadolibre.domains.ports.FindMutant;
import com.mercadolibre.adapter.web.handlers.Validate;
import com.mercadolibre.adapter.web.mapper.StatusTransaction;
import com.mercadolibre.domains.ports.Stat;
import com.mercadolibre.adapter.web.mapper.StatResponse;

@Controller
public class MutantController implements IMutantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutantController.class);

    private final FindMutant findMutant;
    private final Stat stat;

    @Autowired
    public MutantController(FindMutant findMutant, Stat stat) {
        this.findMutant = findMutant;
        this.stat = stat;
    }

    @Override
    public Mono<ServerResponse> isMutant(ServerRequest request) {
        return request.bodyToMono(MutantModel.class).flatMap(validateModel -> {
            var error = Validate.matrix(validateModel.getDna());
            if (error != null) {
                return Mono.error(new IllegalArgumentException(error));
            }
            return Mono.just(validateModel);
        }).flatMap(model -> findMutant.isMutant(Mono.just(model)))
               .flatMap(isMutant -> {
                   if (Boolean.TRUE.equals(isMutant)) {
                       return ServerResponse.ok()
                              .contentType(MediaType.APPLICATION_JSON)
                              .build();
                   }
                   return ServerResponse.status(HttpStatus.FORBIDDEN)
                          .contentType(MediaType.APPLICATION_JSON)
                          .build();
               })
               .onErrorResume(e -> {
                   LOGGER.error(e.getMessage(), e);
                   return ServerResponse.status(HttpStatus.BAD_REQUEST)
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(BodyInserters.fromValue(new StatusTransaction(e.getMessage(),
                          HttpStatus.BAD_REQUEST)));
               });
    }

    @Override
    public Mono<ServerResponse> stats(ServerRequest request) {
        return stat.report().flatMap(result -> ServerResponse.ok()
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .body(BodyInserters
                                                     .fromValue(new StatResponse(result.getMutants(),
                                                     result.getHuman(), result.getRatio()))))
               .onErrorResume(e -> {
                   LOGGER.error(e.getMessage(), e);
                   return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(BodyInserters.fromValue(new StatusTransaction(e.getMessage(),
                          HttpStatus.INTERNAL_SERVER_ERROR)));
               });
    }
}
