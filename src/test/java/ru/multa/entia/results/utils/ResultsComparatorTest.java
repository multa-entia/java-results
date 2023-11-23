package ru.multa.entia.results.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsComparatorTest {

    @Test
    void shouldCheckComparison_nullMode_targetIsNotNull() {
        boolean result = new ResultsComparator(new TestResult<>(false, new Object(), null))
                .isNull()
                .compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_nullMode_targetIsNull() {
        boolean result = new ResultsComparator(null)
                .isNull()
                .compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_okMode_isSuccess_targetNull() {
        boolean result = new ResultsComparator(null).isSuccess().compare();

        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "true,true",
            "false,false"
    })
    void shouldCheckComparison_okMode_isSuccess(boolean initOk, boolean expectedResult) {
        TestResult<Object> target = new TestResult<>(initOk, null, null);
        boolean result = new ResultsComparator(target).isSuccess().compare();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckComparison_okMode_isFail_targetNull() {
        boolean result = new ResultsComparator(null).isFail().compare();

        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "true,false",
            "false,true"
    })
    void shouldCheckComparison_okMode_isFail(boolean initOk, boolean expectedResult) {
        TestResult<Object> target = new TestResult<>(initOk, null, null);
        boolean result = new ResultsComparator(target).isFail().compare();

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            ",,true",
            "some-value,,false",
            ",some-value,false"
    })
    void shouldCheckComparison_valueMode(String initValue, String expectedValue, boolean expectedResult) {
        TestResult<String> target = new TestResult<>(false, initValue, null);
        boolean result = new ResultsComparator(target).value(expectedValue).compare();

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            ",,true",
            "code,,false",
            ",code,false",
            "code,codex,false",
            "code,code,true"
    })
    void shouldCheckComparison_seedMode(String initCode, String expectedCode, boolean expectedResult) {
        TestSeed seed = new TestSeed(initCode, new Object[0]);
        boolean result = new ResultsComparator(new TestResult<Object>(false, null, seed))
                .seedsComparator()
                .code(expectedCode)
                .back()
                .compare();

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "true,true,value,value,code,code,true",
            "true,false,value,value,code,code,false",
            "true,true,value,other-value,code,code,false",
            "true,true,value,value,code,codex,false"
    })
    void shouldCheckComparison(boolean initOk,
                               boolean expectedOk,
                               String initValue,
                               String expectedValue,
                               String initCode,
                               String expectedCode,
                               boolean expectedResult) {
        boolean result = new ResultsComparator(new TestResult<>(initOk, initValue, new TestSeed(initCode, new Object[0])))
                .ok(expectedOk)
                .value(expectedValue)
                .seedsComparator()
                .code(expectedCode)
                .back()
                .compare();

        assertThat(result).isEqualTo(expectedResult);
    }

    private record TestResult<T>(boolean ok, T value, Seed seed) implements Result<T> {}
    private record TestSeed(String code, Object[] args) implements Seed {}
}