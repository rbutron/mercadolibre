package com.mercadolibre.domains.usescases;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import com.mercadolibre.domains.models.MutantModel;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import java.util.Objects;

import com.mercadolibre.domains.models.HumanModel;
import com.mercadolibre.domains.models.StatModel;
import com.mercadolibre.domains.ports.Population;
import com.mercadolibre.domains.ports.Stat;

@Service
public class PopulationUseCase implements Stat {

    private final Population population;

    @Autowired
    public PopulationUseCase(Population population) {
        this.population = population;
    }

    @Override
    public Mono<Boolean> dna(boolean isMutant, Mono<MutantModel> mutantModel) {
        return mutantModel.map(model -> String.join("", model.getDna()))
               .flatMap(humanDna -> population.getOneStats(humanDna)
                                    .defaultIfEmpty(new HumanModel())
                                    .map(result -> {
                                        if (Objects.isNull(result.getHumanDna())) {
                                            var statModel = new HumanModel();
                                            statModel.setHumanDna(humanDna);
                                            statModel.setIsMutant(isMutant);
                                            CompletableFuture.runAsync(() -> population.save(statModel).subscribe());
                                        }
                                        return isMutant;
                                    }));
    }

    @Override
    public Mono<StatModel> report() {
        return population.getStats(Boolean.TRUE).collectList().zipWith(population.getStats(Boolean.FALSE).collectList(),
        (mutant, human) -> {
            var mutantAmount = mutant.size();
            var humanAmount = human.size();
            return new StatModel(mutantAmount, humanAmount, calculateRatio(mutantAmount, humanAmount));
        });
    }

    private double calculateRatio(int mutantAmount, int humanAmount) {
        if (humanAmount <= 0 && mutantAmount <= 0) {
            return 0.0;
        }
        if (humanAmount == 0) {
            return 1.0;
        }
        return ((double) mutantAmount / humanAmount);
    }

}
