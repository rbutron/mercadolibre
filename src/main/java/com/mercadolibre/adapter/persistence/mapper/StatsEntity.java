package com.mercadolibre.adapter.persistence.mapper;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "stats")
public class StatsEntity implements Serializable {

    private static final long serialVersionUID = 1304819065565938096L;

    @Id
    private String id;
    private String humanDna;
    private Boolean isMutant;

}
