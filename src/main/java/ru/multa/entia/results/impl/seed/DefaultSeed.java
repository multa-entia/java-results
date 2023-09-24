package ru.multa.entia.results.impl.seed;

import ru.multa.entia.results.api.seed.Seed;

record DefaultSeed(String code, Object... args) implements Seed {}
