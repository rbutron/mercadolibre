package com.mercadolibre.domains.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HumanModel {
    private String humanDna;
    private Boolean isMutant;
}
