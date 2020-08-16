package com.github.pedrobacchini.enumerated;

import java.util.Random;

public enum CompanyType {

    TAKER, PROVIDER;

    public static CompanyType random() {
        return CompanyType.values()[new Random().nextInt(CompanyType.values().length)];
    }
}
