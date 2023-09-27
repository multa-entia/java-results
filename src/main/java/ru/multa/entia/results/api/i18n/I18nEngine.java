package ru.multa.entia.results.api.i18n;

import ru.multa.entia.results.api.seed.Seed;

import java.util.Locale;
import java.util.Optional;

public interface I18nEngine {
    Optional<String> execute(Seed seed, Locale locale);
}
