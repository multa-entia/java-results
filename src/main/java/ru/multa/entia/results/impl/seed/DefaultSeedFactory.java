package ru.multa.entia.results.impl.seed;

import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedFactory;

public class DefaultSeedFactory implements SeedFactory {
    public static final String DEFAULT_CODE = "";
    public static final Object[] DEFAULT_ARGS = new Object[0];

    @Override
    public Seed create(String code) {
        return create(code, DEFAULT_ARGS);
    }

    @Override
    public Seed create(String code, Object... args) {
        return new DefaultSeed(
                code == null ? DEFAULT_CODE : code,
                args == null ? DEFAULT_ARGS : args
        );
    }
}
