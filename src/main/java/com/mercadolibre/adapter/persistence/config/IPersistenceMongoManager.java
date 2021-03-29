package com.mercadolibre.adapter.persistence.config;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.ReactiveRemoveOperation.ReactiveRemove;

public interface IPersistenceMongoManager<T> {

    ReactiveRemove<T> deleteAll(Class<T> clazz);
    Mono<T> oneObject(Query query, Class<T> clazz);
    Mono<T> save(T model);
    Flux<T> listObject(Query query, Class<T> clazz);

}
