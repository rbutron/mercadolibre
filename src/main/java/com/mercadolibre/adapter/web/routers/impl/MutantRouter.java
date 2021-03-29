package com.mercadolibre.adapter.web.routers.impl;

import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import com.mercadolibre.adapter.web.controllers.impl.HealthController;
import com.mercadolibre.adapter.web.controllers.impl.MutantController;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.http.MediaType;
import com.mercadolibre.common.utility.ConstantManager;
import com.mercadolibre.adapter.web.routers.IMutantRouter;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;

@Component
public class MutantRouter implements IMutantRouter {

    @Bean
    @Override
    public RouterFunction<ServerResponse> health(HealthController controller) {
        return RouterFunctions.route(RequestPredicates.GET(ConstantManager.HEALTH_PATH)
                                     .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), controller::health);
    }

    @Bean
    @Override
    public RouterFunction<ServerResponse> mutant(MutantController controller) {
        return RouterFunctions.route(RequestPredicates.POST(ConstantManager.FIND_MUTANT_PATH)
                                     .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), controller::isMutant)
        .and(RouterFunctions.route(RequestPredicates.GET(ConstantManager.STATS_PATH)
                                   .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), controller::stats));
    }
}
