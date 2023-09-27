package ru.multa.entia.results.api.i18n;

import ru.multa.entia.results.api.seed.Seed;

import java.util.Locale;

public interface I18nService {
    String translate(Seed seed, Locale locale);
    String translate(Seed seed);
}
