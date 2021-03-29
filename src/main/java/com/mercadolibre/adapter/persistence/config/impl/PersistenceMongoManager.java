package com.mercadolibre.adapter.persistence.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import com.mercadolibre.adapter.persistence.config.IPersistenceMongoManager;
import org.springframework.data.mongodb.core.ReactiveRemoveOperation.ReactiveRemove;
import reactor.core.publisher.Flux;

@Repository
public class PersistenceMongoManager<T> implements IPersistenceMongoManager<T> {

    private final ReactiveMongoTemplate templateManager;

    @Autowired
    public PersistenceMongoManager(ReactiveMongoTemplate templateManager) {
        this.templateManager = templateManager;
    }

    @Override
    public Mono<T> oneObject(Query query, Class<T> clazz) {
        return templateManager.findOne(query, clazz);
    }

    @Override
    public Mono<T> save(T model) {
        return templateManager.save(model);
    }

    @Override
    public Flux<T> listObject(Query query, Class<T> clazz) {
        return templateManager.find(query, clazz);
    }

    @Override
    public ReactiveRemove<T> deleteAll(Class<T> clazz) {
        return templateManager.remove(clazz);
    }

}
