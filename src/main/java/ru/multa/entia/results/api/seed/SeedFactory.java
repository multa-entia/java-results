package ru.multa.entia.results.api.seed;

public interface SeedFactory {
    Seed create(String code);
    Seed create(String code, Object... args);
}
