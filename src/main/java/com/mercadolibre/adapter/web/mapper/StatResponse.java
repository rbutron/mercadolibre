package com.mercadolibre.adapter.web.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
public class StatResponse {
    @JsonProperty("count_mutant_dna")
    private int countMutantDna;

    @JsonProperty("count_human_dna")
    private int countHumanDna;

    private double ratio;
}
