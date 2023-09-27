package ru.multa.entia.results.impl.i18n;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.i18n.I18nEngine;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultI18nServiceTest {

    @Test
    void shouldCheckDefaultMessageFunction() {
        String code = Faker.str_().random(10, 20);
        Object[] args = {
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10)
        };
        String expected = code + " " + Arrays.stream(args).toList();

        String result = new DefaultI18nService.DefaultMessageFunction().apply(createSeed(code, args));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldCheckTranslation_withLocale_templateIsAbsence() {
        Seed seed = createSeed();
        Locale locale = Locale.getDefault();
        String expected = Faker.str_().random();

        DefaultI18nService service = new DefaultI18nService(
                createI18nEngine(seed, locale, null),
                createDefaultMessageFunction(expected)
        );
        String result = service.translate(seed, locale);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldCheckTranslation_withLocale() {
        Seed seed = createSeed();
        Locale locale = Locale.getDefault();
        String expected = Faker.str_().random();

        DefaultI18nService service = new DefaultI18nService(
                createI18nEngine(seed, locale, expected),
                createDefaultMessageFunction("")
        );
        String result = service.translate(seed, locale);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldCheckTranslation_withoutLocale_templateIsAbsence() {
        Seed seed = createSeed();
        String expected = Faker.str_().random();

        DefaultI18nService service = new DefaultI18nService(
                createI18nEngine(seed, Locale.getDefault(), null),
                createDefaultMessageFunction(expected)
        );
        String result = service.translate(seed);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldCheckTranslation_withoutLocale() {
        Seed seed = createSeed();
        String expected = Faker.str_().random();

        DefaultI18nService service = new DefaultI18nService(
                createI18nEngine(seed, Locale.getDefault(), expected),
                createDefaultMessageFunction("")
        );
        String result = service.translate(seed);
        assertThat(result).isEqualTo(expected);
    }

    private Seed createSeed(String code, Object... args){
        Seed seed = Mockito.mock(Seed.class);
        Mockito
                .when(seed.code())
                .thenReturn(code);
        Mockito
                .when(seed.args())
                .thenReturn(args);

        return seed;
    }

    private Seed createSeed(){
        String code = Faker.str_().random(10, 20);
        Object[] args = {
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10)
        };

        Seed seed = Mockito.mock(Seed.class);
        Mockito
                .when(seed.code())
                .thenReturn(code);
        Mockito
                .when(seed.args())
                .thenReturn(args);

        return seed;
    }

    private I18nEngine createI18nEngine(Seed seed, Locale locale, String line){
        Optional<String> result = line == null ? Optional.empty() : Optional.of(line);
        I18nEngine engine = Mockito.mock(I18nEngine.class);
        Mockito
                .when(engine.execute(seed, locale))
                .thenReturn(result);

        return engine;
    }

    private DefaultI18nService.DefaultMessageFunction createDefaultMessageFunction(String result){
        DefaultI18nService.DefaultMessageFunction function
                = Mockito.mock(DefaultI18nService.DefaultMessageFunction.class);
        Mockito
                .when(function.apply(Mockito.any()))
                .thenReturn(result);

        return function;
    }
}