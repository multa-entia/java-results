package ru.multa.entia.results.api.result;

import ru.multa.entia.results.api.seed.Seed;

public interface Result<T> {
    boolean ok();
    T value();
    Seed seed();
}
