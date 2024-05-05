package ru.multa.entia.results.api.result;

import ru.multa.entia.results.api.seed.Seed;

import java.util.List;

public interface Result<T> {
    boolean ok();
    T value();
    Seed seed();
    List<Result<?>> causes();
}
