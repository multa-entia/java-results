package ru.multa.entia.results.impl.seed;

import ru.multa.entia.results.api.seed.Seed;

record DefaultSeed(String code, Object... args) implements Seed {
    public static final String DEFAULT_CODE = "";
    public static final Object[] DEFAULT_ARGS = new Object[0];
}
