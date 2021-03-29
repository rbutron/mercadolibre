package com.mercadolibre.adapter.persistence.query;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.mercadolibre.common.utility.ConstantManager;


public class MutantQuery {

    public static Query findByHumanDna(String humanDna) {
        return Query.query(Criteria.where(ConstantManager.MONGO_HUMAN_DNA).is(humanDna));
    }

    public static Query findByIfMutant(boolean isMutant) {
        return Query.query(Criteria.where(ConstantManager.MONGO_IS_MUTANT).is(isMutant));
    }

    private MutantQuery() {}
}
