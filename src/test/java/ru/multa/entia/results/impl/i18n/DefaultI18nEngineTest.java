package ru.multa.entia.results.impl.i18n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.i18n.I18nTemplates;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultI18nEngineTest {

    @Test
    void shouldCheckExecution_ifTemplateIsAbsence() {
        DefaultI18nEngine engine = new DefaultI18nEngine(createEmptyI18nTemplates());
        Seed seed = createSeed(Faker.str_().random(5, 10), Faker.str_().random());
        Optional<String> result = engine.execute(seed, Locale.getDefault());

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckExecution.csv")
    void shouldCheckExecution(String template, String rawArgs, String expected) {
        Seed seed = createSeed("", createArgs(rawArgs));
        Optional<String> result = new DefaultI18nEngine(createI18nTemplates(template))
                .execute(seed, Locale.getDefault());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expected);
    }

    private Object[] createArgs(String line) {
        return Arrays.stream(line.split("-")).map(s -> {
            return s.equals("null") ? null : s;
        }).toList().toArray();
    }

    private I18nTemplates createEmptyI18nTemplates(){
        I18nTemplates templates = Mockito.mock(I18nTemplates.class);
        Mockito
                .when(templates.get(Mockito.anyString(), Mockito.any()))
                .thenReturn(Optional.empty());

        return templates;
    }

    private I18nTemplates createI18nTemplates(String template){
        I18nTemplates templates = Mockito.mock(I18nTemplates.class);
        Mockito
                .when(templates.get(Mockito.anyString(), Mockito.any()))
                .thenReturn(Optional.of(template));

        return templates;
    }

    private Seed createSeed(final String code, final Object... args){
        Seed seed = Mockito.mock(Seed.class);
        Mockito
                .when(seed.code())
                .thenReturn(code);
        Mockito
                .when(seed.args())
                .thenReturn(args);

        return seed;
    }
}