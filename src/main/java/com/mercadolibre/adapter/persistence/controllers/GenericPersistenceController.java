package com.mercadolibre.adapter.persistence.controllers;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.mercadolibre.adapter.persistence.mapper.StatsEntity;
import com.mercadolibre.adapter.persistence.config.IPersistenceMongoManager;
import com.mercadolibre.adapter.persistence.query.MutantQuery;
import java.util.function.Function;
import com.mercadolibre.domains.models.HumanModel;
import com.mercadolibre.domains.ports.Population;
import reactor.core.publisher.Flux;

@Component
public class GenericPersistenceController implements Population {

    private final IPersistenceMongoManager<StatsEntity> manager;

    @Autowired
    public GenericPersistenceController(IPersistenceMongoManager<StatsEntity> manager) {
        this.manager = manager;
    }

    @Override
    public Mono<HumanModel> getOneStats(String humanDna) {
        return manager.oneObject(MutantQuery.findByHumanDna(humanDna), StatsEntity.class)
               .map(getStatsEntityStatsModelFunction());
    }

    @Override
    public Mono<HumanModel> save(HumanModel model) {
        var entity = new StatsEntity();
        entity.setHumanDna(model.getHumanDna());
        entity.setIsMutant(model.getIsMutant());
        return manager.save(entity).map(getStatsEntityStatsModelFunction());
    }

    public Flux<HumanModel> getStats(boolean humanDna) {
        return manager.listObject(MutantQuery.findByIfMutant(humanDna), StatsEntity.class)
               .map(getStatsEntityStatsModelFunction());
    }

    private Function<StatsEntity, HumanModel> getStatsEntityStatsModelFunction() {
        return result -> new HumanModel(
        result.getHumanDna(),
        result.getIsMutant()
        );
    }
}
