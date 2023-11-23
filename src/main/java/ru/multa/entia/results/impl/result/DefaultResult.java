package ru.multa.entia.results.impl.result;

import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;

record DefaultResult<T>(boolean ok, T value, Seed seed) implements Result<T> {}
