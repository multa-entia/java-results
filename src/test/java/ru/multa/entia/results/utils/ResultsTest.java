package ru.multa.entia.results.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsTest {
    @ParameterizedTest
    @CsvSource({
            "false,false,,,,,true",
            "false,false,,,,code,false",
            "false,false,,,code,,false",
            "false,false,,,code,codex,false",
            "false,false,,,code,code,true",
            "false,false,,value,,,false",
            "false,false,,value,,code,false",
            "false,false,,value,code,,false",
            "false,false,,value,code,codex,false",
            "false,false,,value,code,code,false",
            "false,false,value,,,,false",
            "false,false,value,,,code,false",
            "false,false,value,,code,,false",
            "false,false,value,,code,codex,false",
            "false,false,value,,code,code,false",
            "false,false,value,valuex,,,false",
            "false,false,value,valuex,,code,false",
            "false,false,value,valuex,code,,false",
            "false,false,value,valuex,code,codex,false",
            "false,false,value,valuex,code,code,false",
            "false,false,value,value,,,true",
            "false,false,value,value,,code,false",
            "false,false,value,value,code,,false",
            "false,false,value,value,code,codex,false",
            "false,false,value,value,code,code,true",
            "false,true,,,,,false",
            "false,true,,,,code,false",
            "false,true,,,code,,false",
            "false,true,,,code,codex,false",
            "false,true,,,code,code,false",
            "false,true,,value,,,false",
            "false,true,,value,,code,false",
            "false,true,,value,code,,false",
            "false,true,,value,code,codex,false",
            "false,true,,value,code,code,false",
            "false,true,value,,,,false",
            "false,true,value,,,code,false",
            "false,true,value,,code,,false",
            "false,true,value,,code,codex,false",
            "false,true,value,,code,code,false",
            "false,true,value,valuex,,,false",
            "false,true,value,valuex,,code,false",
            "false,true,value,valuex,code,,false",
            "false,true,value,valuex,code,codex,false",
            "false,true,value,valuex,code,code,false",
            "false,true,value,value,,,false",
            "false,true,value,value,,code,false",
            "false,true,value,value,code,,false",
            "false,true,value,value,code,codex,false",
            "false,true,value,value,code,code,false",
            "true,false,,,,,false",
            "true,false,,,,code,false",
            "true,false,,,code,,false",
            "true,false,,,code,codex,false",
            "true,false,,,code,code,false",
            "true,false,,value,,,false",
            "true,false,,value,,code,false",
            "true,false,,value,code,,false",
            "true,false,,value,code,codex,false",
            "true,false,,value,code,code,false",
            "true,false,value,,,,false",
            "true,false,value,,,code,false",
            "true,false,value,,code,,false",
            "true,false,value,,code,codex,false",
            "true,false,value,,code,code,false",
            "true,false,value,valuex,,,false",
            "true,false,value,valuex,,code,false",
            "true,false,value,valuex,code,,false",
            "true,false,value,valuex,code,codex,false",
            "true,false,value,valuex,code,code,false",
            "true,false,value,value,,,false",
            "true,false,value,value,,code,false",
            "true,false,value,value,code,,false",
            "true,false,value,value,code,codex,false",
            "true,false,value,value,code,code,false",
            "true,true,,,,,true",
            "true,true,,,,code,false",
            "true,true,,,code,,false",
            "true,true,,,code,codex,false",
            "true,true,,,code,code,true",
            "true,true,,value,,,false",
            "true,true,,value,,code,false",
            "true,true,,value,code,,false",
            "true,true,,value,code,codex,false",
            "true,true,,value,code,code,false",
            "true,true,value,,,,false",
            "true,true,value,,,code,false",
            "true,true,value,,code,,false",
            "true,true,value,,code,codex,false",
            "true,true,value,,code,code,false",
            "true,true,value,valuex,,,false",
            "true,true,value,valuex,,code,false",
            "true,true,value,valuex,code,,false",
            "true,true,value,valuex,code,codex,false",
            "true,true,value,valuex,code,code,false",
            "true,true,value,value,,,true",
            "true,true,value,value,,code,false",
            "true,true,value,value,code,,false",
            "true,true,value,value,code,codex,false",
            "true,true,value,value,code,code,true",
    })
    void shouldCheckEqualMethod(boolean ok0,
                                boolean ok1,
                                String value0,
                                String value1,
                                String code0,
                                String code1,
                                boolean expectedValue) {
        TestResult<String> result0
                = new TestResult<String>(ok0, value0, code0 == null ? null : new TestSeed(code0, new Object[0]));
        TestResult<String> result1
                = new TestResult<String>(ok1, value1, code1 == null ? null : new TestSeed(code1, new Object[0]));

        assertThat(Results.equal(result0, result1)).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @CsvSource({
            "code0,code0,cause0-cause1,cause0-cause1,true",
            "code0,code1,cause0-cause1,cause0-cause1,false",
            "code0,code0,cause0-cause1,cause10-cause11,false",
            "code0,code1,cause0-cause1,cause10-cause11,false"
    })
    void shouldCheckEqualMethod_byCauses(String code0,
                                         String code1,
                                         String rawCauses0,
                                         String rawCauses1,
                                         boolean expectedResult) {
        ArrayList<Result<?>> causes0 = new ArrayList<>();
        for (String code : rawCauses0.split("-")) {
            causes0.add(new TestResult<>(false, null, new TestSeed(code, new Object[0])));
        }

        ArrayList<Result<?>> causes1 = new ArrayList<>();
        for (String code : rawCauses1.split("-")) {
            causes1.add(new TestResult<>(false, null, new TestSeed(code, new Object[0])));
        }

        TestResult<String> result0 = new TestResult<String>(false, code0, new TestSeed("", new Object[0]), causes0);
        TestResult<String> result1 = new TestResult<String>(false, code1, new TestSeed("", new Object[0]), causes1);

        assertThat(Results.equal(result0, result1)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            ",,true",
            ",value,false",
            "value,,false",
    })
    void shouldCheckEqualMethod_ifSomeResultIsNull(String value0, String value1, boolean expectedResult) {
        TestResult<String> result0 = value0 == null ? null : new TestResult<String>(true, value0, null);
        TestResult<String> result1 = value1 == null ? null : new TestResult<String>(true, value1, null);

        assertThat(Results.equal(result0, result1)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckEqualMethod_ifOnceObject() {
        TestResult<String> result = new TestResult<>(
                Faker.bool_().random(),
                Faker.str_().random(),
                new TestSeed(Faker.str_().random(), new Object[0])
        );

        assertThat(Results.equal(result, result)).isTrue();
    }

    @Test
    void shouldCheckEqualMethod_ifValueIsEqButDifferentTypes() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[0]);
        Boolean ok = Faker.bool_().random();
        String value = Faker.str_().random();
        TestResult<String> result0 = new TestResult<>(ok, value, seed);
        TestResult<Object> result1 = new TestResult<>(ok, (Object) value, seed);

        assertThat(Results.equal(result0, result1)).isTrue();
    }

    @Test
    void shouldCheckEqualMethod_ifValueIsNotEqButDifferentTypes() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[0]);
        Boolean ok = Faker.bool_().random();
        String value = Faker.str_().random();
        TestResult<String> result0 = new TestResult<>(ok, value, seed);
        TestResult<Object> result1 = new TestResult<>(ok, new Object(), seed);

        assertThat(Results.equal(result0, result1)).isFalse();
    }

    private record TestSeed(String code, Object[] args) implements Seed {}
    private record TestResult<T>(boolean ok, T value, Seed seed, List<Result<?>> causes) implements Result<T> {
        public TestResult(boolean ok, T value, Seed seed) {
            this(ok, value, seed, List.of());
        }
    }
}