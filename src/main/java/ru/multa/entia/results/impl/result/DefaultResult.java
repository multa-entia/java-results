package ru.multa.entia.results.impl.result;

import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;

import java.util.List;
import java.util.Objects;

record DefaultResult<T>(boolean ok, T value, Seed seed, List<Result<?>> causes) implements Result<T> {

    public DefaultResult(T value) {
        this(true, value, null, List.of());
    }

    public DefaultResult(Seed seed) {
        this(false, null, seed, List.of());
    }

    public DefaultResult(boolean ok, T value, Seed seed) {
        this(ok, value, seed, List.of());
    }

    DefaultResult(boolean ok, T value, Seed seed, List<Result<?>> causes) {
        this.ok = ok;
        this.value = value;
        this.seed = seed;
        this.causes = Objects.requireNonNullElse(causes, List.of());
    }
}
