package ru.multa.entia.results.impl.i18n;

import ru.multa.entia.results.api.i18n.I18nEngine;
import ru.multa.entia.results.api.i18n.I18nService;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class DefaultI18nService implements I18nService {
    private final I18nEngine engine;
    private final Function<Seed, String> defaultMessageFunction;

    public DefaultI18nService(final I18nEngine engine, final Function<Seed, String> defaultMessageFunction) {
        this.engine = engine;
        this.defaultMessageFunction = defaultMessageFunction;
    }

    public DefaultI18nService(final I18nEngine engine) {
        this.engine = engine;
        this.defaultMessageFunction = new DefaultMessageFunction();
    }

    @Override
    public String translate(final Seed seed, final Locale locale) {
        Optional<String> result = engine.execute(seed, locale);
        return result.orElseGet(() -> {return defaultMessageFunction.apply(seed);});
    }

    @Override
    public String translate(final Seed seed) {
        return translate(seed, Locale.getDefault());
    }

    public static class DefaultMessageFunction implements Function<Seed, String> {
        @Override
        public String apply(final Seed seed) {
            return seed.code() + " " + Arrays.stream(seed.args()).toList();
        }
    }
}
