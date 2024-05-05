package ru.multa.entia.results.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsComparatorTest {

    @Test
    void shouldCheckIsNullChecker_ifModeIsOff() {
        ResultsComparator comparator = new ResultsComparator(null);
        ResultsComparator.IsNullChecker checker = new ResultsComparator.IsNullChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckIsNullChecker_ifModeIsOnAndTargetIsNotNull() {
        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, null, null)).isNull();
        ResultsComparator.IsNullChecker checker = new ResultsComparator.IsNullChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckIsNullChecker_ifModeIsOnAndTargetIsNull() {
        ResultsComparator comparator = new ResultsComparator(null).isNull();
        ResultsComparator.IsNullChecker checker = new ResultsComparator.IsNullChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckOkCheckMode_ifIsNullModeIsOn() {
        ResultsComparator comparator = new ResultsComparator(null).isNull();
        ResultsComparator.OkChecker checker = new ResultsComparator.OkChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckOkCheckMode_ifOkModeIsOff() {
        ResultsComparator comparator = new ResultsComparator(null);
        ResultsComparator.OkChecker checker = new ResultsComparator.OkChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckOkCheckMode_ifOkModeIsOnAndCheckingFail() {
        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, null, null)).ok(true);
        ResultsComparator.OkChecker checker = new ResultsComparator.OkChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckOkCheckMode_ifOkModeIsOnAndCheckingSuccess() {
        boolean ok = true;
        ResultsComparator comparator = new ResultsComparator(new TestResult<>(ok, null, null)).ok(ok);
        ResultsComparator.OkChecker checker = new ResultsComparator.OkChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckValueCheckMode_ifIsNullModeIsOn() {
        ResultsComparator comparator = new ResultsComparator(null).isNull();
        ResultsComparator.ValueChecker checker = new ResultsComparator.ValueChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckValueCheckMode_ifValueModeIsOff() {
        ResultsComparator comparator = new ResultsComparator(null);
        ResultsComparator.ValueChecker checker = new ResultsComparator.ValueChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckValueCheckMode_ifValueModeIsOnAndCheckingFail() {
        String value = Faker.str_().random();
        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, value, null))
                .value(value + Faker.str_().random());
        ResultsComparator.ValueChecker checker = new ResultsComparator.ValueChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckValueCheckMode_ifValueModeIsOnAndCheckingSuccess() {
        String value = Faker.str_().random();
        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, value, null))
                .value(value);
        ResultsComparator.ValueChecker checker = new ResultsComparator.ValueChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckSeedCheckMode_ifIsNullModeIsOn() {
        ResultsComparator comparator = new ResultsComparator(null).isNull();
        ResultsComparator.SeedChecker checker = new ResultsComparator.SeedChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckSeedCheckMode_ifSeedModeIsOff() {
        ResultsComparator comparator = new ResultsComparator(null);
        ResultsComparator.SeedChecker checker = new ResultsComparator.SeedChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckSeedCheckMode_ifSeedModeIsOnAndCheckingFail() {
        String code = Faker.str_().random();
        TestSeed seed = new TestSeed(code, new Object[0]);

        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, null,  seed))
                .seedsComparator().code(code + Faker.str_().random()).back();
        ResultsComparator.SeedChecker checker = new ResultsComparator.SeedChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckSeedCheckMode_ifSeedModeIsOnAndCheckingSuccess() {
        String code = Faker.str_().random();
        TestSeed seed = new TestSeed(code, new Object[0]);

        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, null,  seed))
                .seedsComparator().code(code).back();
        ResultsComparator.SeedChecker checker = new ResultsComparator.SeedChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckCauseCheckMode_ifCauseModeIsOff() {
        ResultsComparator comparator = new ResultsComparator(null);
        ResultsComparator.CausesChecker checker = new ResultsComparator.CausesChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckCauseCheckMode_ifCauseModeIsOnAndCheckingFail() {
//        String code = Faker.str_().random();
//        TestSeed seed = new TestSeed(code, new Object[0]);
//
//        new ResultsComparator()

//        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, null,  seed))
//                .seedsComparator().code(code + Faker.str_().random()).back();
//        ResultsComparator.SeedChecker checker = new ResultsComparator.SeedChecker();
//
//        Optional<Boolean> result = checker.apply(comparator);
//
//        assertThat(result).isPresent();
//        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckCauseCheckMode_ifCauseModeIsOnAndCheckingSuccess() {
//        String code = Faker.str_().random();
//        TestSeed seed = new TestSeed(code, new Object[0]);
//
//        ResultsComparator comparator = new ResultsComparator(new TestResult<>(false, null,  seed))
//                .seedsComparator().code(code).back();
//        ResultsComparator.SeedChecker checker = new ResultsComparator.SeedChecker();
//
//        Optional<Boolean> result = checker.apply(comparator);
//
//        assertThat(result).isPresent();
//        assertThat(result.get()).isTrue();
    }

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

    private record TestResult<T>(boolean ok, T value, Seed seed, List<Result<?>> causes) implements Result<T> {
        public TestResult(boolean ok, T value, Seed seed) {
            this(ok, value, seed, List.of());
        }
    }
    private record TestSeed(String code, Object[] args) implements Seed {}
}