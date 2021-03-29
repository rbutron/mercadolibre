package com.mercadolibre.adapter.web.routers;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.mercadolibre.adapter.web.controllers.impl.HealthController;
import com.mercadolibre.adapter.web.controllers.impl.MutantController;

public interface IMutantRouter {

    RouterFunction<ServerResponse> health(HealthController controller);
    RouterFunction<ServerResponse> mutant(MutantController controller);

}
