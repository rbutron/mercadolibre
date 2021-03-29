package com.mercadolibre.adapter.web.handlers;

import java.util.List;
import com.mercadolibre.common.utility.ConstantManager;
import reactor.core.Exceptions;

public class Validate {
    public static String matrix(List<String> dna) {
        if (dna == null) {
            return "DNA -> NULL";
        }
        if (!isMatrix(dna)) {
            return "DNA -> It has to be a NxN Matrix.";
        }
        if (dna.size() < ConstantManager.NUMBER_OF_CHARACTERS + 1) {
            return "DNA -> The sequence is small, you have to try at least "
                                               + (ConstantManager.NUMBER_OF_CHARACTERS + 1) + "x" +
                                               (ConstantManager.NUMBER_OF_CHARACTERS + 1);
        }
        if (dna.parallelStream().anyMatch(dnaRow -> !dnaRow.matches("^[ATCG]*$"))) {
            return "Invalid argument.";
        }
        return null;
    }

    private static boolean isMatrix(List<String> dna) {
        final var rowAmount = dna.size();
        return dna.stream().noneMatch(row -> row.length() != rowAmount);
    }

    private Validate(){}
}
