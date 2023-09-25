package ru.multa.entia.results.api.result;

import ru.multa.entia.results.api.seed.Seed;

public interface ResultBuilder<T> {
    ResultBuilder<T> success(boolean ok);
    ResultBuilder<T> value(T value);
    ResultBuilder<T> seed(Seed seed);
    Result<T> build();
}
