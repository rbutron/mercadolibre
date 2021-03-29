package com.mercadolibre.adapter.web.mapper;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

@Getter
@Setter
@AllArgsConstructor
public class StatusTransaction {

    @NonNull
    private String messages;

    @NonNull
    private HttpStatus status;

}
