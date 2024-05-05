package ru.multa.entia.results.api.result;

import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;

public interface ResultBuilder<T> {
    ResultBuilder<T> success(boolean ok);
    ResultBuilder<T> value(T value);
    ResultBuilder<T> seed(Seed seed);
    ResultBuilder<T> causes(Result<?>... causes);
    SeedBuilder<T> seedBuilder();
    Result<T> build();
}
