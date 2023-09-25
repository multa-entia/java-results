package ru.multa.entia.results.api.seed;

import ru.multa.entia.results.api.result.ResultBuilder;

public interface SeedBuilder<T> {
    SeedBuilder<T> code(String code);
    SeedBuilder<T> addFirstArgs(Object... args);
    SeedBuilder<T> addLastArgs(Object... args);
    Seed build();
    ResultBuilder<T> apply();
}
