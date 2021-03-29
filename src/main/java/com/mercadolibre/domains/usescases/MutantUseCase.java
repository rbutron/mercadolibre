package com.mercadolibre.domains.usescases;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.mercadolibre.domains.models.MutantModel;
import com.mercadolibre.domains.ports.FindMutant;

import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import com.mercadolibre.domains.ports.Stat;

@Service
public class MutantUseCase implements FindMutant {

    private final Stat stat;

    @Autowired
    public MutantUseCase(Stat stat) {
        this.stat = stat;
    }

    @Override
    public Mono<Boolean> isMutant(Mono<MutantModel> model) {
        return model.map(mutantModel -> validateHumanDna(mutantModel.getDna()))
               .flatMap(isMutant -> stat.dna(isMutant, model));
    }

    private boolean validateHumanDna(List<String> dna) {
        // Horizontal
        long mutantDnaFoundCounter = dna.stream()
                                     .filter(getDnaMonitoring())
                                     .count();
        if (mutantDnaFoundCounter > 1) {
            return true;
        }

        mutantDnaFoundCounter += buildVerticalDnaStrings(dna).stream()
                                 .filter(getDnaMonitoring())
                                 .count();
        if (mutantDnaFoundCounter > 1) {
            return true;
        }

        mutantDnaFoundCounter += buildDiagonalLeftToBottom(dna).stream()
                                 .filter(getDnaMonitoring())
                                 .count();
        if (mutantDnaFoundCounter > 1) {
            return true;
        }

        mutantDnaFoundCounter += buildDiagonalLeftToTop(dna).stream()
                                 .filter(getDnaMonitoring())
                                 .count();
        return mutantDnaFoundCounter > 1;
    }

    private List<String> buildVerticalDnaStrings(final List<String> dnaStrings) {
        List<String> verticalDnaString = new ArrayList<>();
        for (int i = 0; i < dnaStrings.size(); i++) {
            StringBuilder columnArrange = new StringBuilder(dnaStrings.size());
            for (String dnaString : dnaStrings) {
                columnArrange.append(dnaString.charAt(i));
            }
            verticalDnaString.add(columnArrange.toString());
        }
        return verticalDnaString;
    }

    private List<String> buildDiagonalLeftToBottom(final List<String> dnaStrings) {
        List<String> diagonalDnaString = new ArrayList<>();
        for (int i = 0; i < dnaStrings.size() / 2; i++) {
            StringBuilder diagonalUpper = new StringBuilder(dnaStrings.size());
            StringBuilder diagonalLower = new StringBuilder(dnaStrings.size());
            for (int j = 0; j < dnaStrings.size() - i; j++) {
                diagonalUpper.append(dnaStrings.get(j).charAt(j + i));
                if (i != 0) {
                    diagonalLower.append(dnaStrings.get(i + j).charAt(j));
                }
            }
            if (diagonalUpper.length() > 0) {
                diagonalDnaString.add(diagonalUpper.toString());
            }
            if (diagonalLower.length() > 0) {
                diagonalDnaString.add(diagonalLower.toString());
            }
        }
        return diagonalDnaString;
    }

    private List<String> buildDiagonalLeftToTop(final List<String> dnaStrings) {
        List<String> diagonalDnaString = new ArrayList<>();
        for (int i = 0; i < dnaStrings.size() / 2; i++) {
            StringBuilder diagonalUpper = new StringBuilder(dnaStrings.size());
            StringBuilder diagonalLower = new StringBuilder(dnaStrings.size());
            for (int j = dnaStrings.size() - 1; j >= i; j--) {
                diagonalUpper.append(dnaStrings.get(j).charAt(i + dnaStrings.size() - 1 - j));
                if (i != 0) {
                    diagonalLower.append(dnaStrings.get(j - i).charAt(dnaStrings.size() - 1 - j));
                }
            }
            if (diagonalUpper.length() > 0) {
                diagonalDnaString.add(diagonalUpper.toString());
            }
            if (diagonalLower.length() > 0) {
                diagonalDnaString.add(diagonalLower.toString());
            }
        }
        return diagonalDnaString;
    }

    private Predicate<String> getDnaMonitoring() {
        return s -> s.matches(".*(AAAA|CCCC|GGGG|TTTT).*");
    }
}
