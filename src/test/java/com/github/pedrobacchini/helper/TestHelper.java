package com.github.pedrobacchini.helper;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Tag;

@Tag("all")
public abstract class TestHelper {

    protected static final Faker faker = new Faker();
}
