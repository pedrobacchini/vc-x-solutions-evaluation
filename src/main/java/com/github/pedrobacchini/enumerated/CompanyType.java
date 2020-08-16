package com.github.pedrobacchini.enumerated;

import java.security.SecureRandom;

public enum CompanyType {

    TAKER, PROVIDER;

    public static CompanyType random() {
        return CompanyType.values()[new SecureRandom().nextInt(CompanyType.values().length)];
    }
}
