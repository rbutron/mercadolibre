package com.mercadolibre.common.utility;

public class ConstantManager {

    private static final String PATH_NAME = "/mutant/";
    public static final String ENVAPP = "${app.env}";

    public static final String HEALTH_PATH = PATH_NAME.concat("health");
    public static final String STATS_PATH = PATH_NAME.concat("stats");
    public static final String FIND_MUTANT_PATH = PATH_NAME;

    public static final int NUMBER_OF_CHARACTERS = 4;

    public static final String MONGO_HUMAN_DNA = "humanDna";
    public static final String MONGO_IS_MUTANT = "isMutant";

    public static final String MSG_HEALTH = "Eureka!!";

    private ConstantManager() {
    }
}
