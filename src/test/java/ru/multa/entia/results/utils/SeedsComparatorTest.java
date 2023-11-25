package ru.multa.entia.results.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SeedsComparatorTest {
    @Test
    void shouldCheckIsNullChecker_ifModeIsOff() {
        SeedsComparator comparator = new SeedsComparator(null);
        SeedsComparator.IsNullChecker checker = new SeedsComparator.IsNullChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckIsNullChecker_ifModeIsOnAndTargetIsNotNull() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[0]);
        SeedsComparator comparator = new SeedsComparator(seed).isNull();
        SeedsComparator.IsNullChecker checker = new SeedsComparator.IsNullChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckIsNullChecker_ifModeIsOnAndTargetIsNull() {
        SeedsComparator comparator = new SeedsComparator(null).isNull();
        SeedsComparator.IsNullChecker checker = new SeedsComparator.IsNullChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckCodeCheckMode_ifIsNullModeIsOn() {
        SeedsComparator comparator = new SeedsComparator(null).isNull();
        SeedsComparator.CodeChecker checker = new SeedsComparator.CodeChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckCodeCheckMode_ifCodeModeIsOff() {
        SeedsComparator comparator = new SeedsComparator(null);
        SeedsComparator.CodeChecker checker = new SeedsComparator.CodeChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckCodeCheckMode_ifCodeModeIsOnAndCheckingFail() {
        String code = Faker.str_().random();
        TestSeed target = new TestSeed(code, new Object[0]);
        SeedsComparator comparator = new SeedsComparator(target).code(code + Faker.str_().random());
        SeedsComparator.CodeChecker checker = new SeedsComparator.CodeChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckCodeCheckMode_ifCodeModeIsOnAndCheckingSuccess() {
        String code = Faker.str_().random();
        TestSeed target = new TestSeed(code, new Object[0]);
        SeedsComparator comparator = new SeedsComparator(target).code(code);
        SeedsComparator.CodeChecker checker = new SeedsComparator.CodeChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckArgsCheckMode_ifIsNullModeIsOn() {
        SeedsComparator comparator = new SeedsComparator(null).isNull();
        SeedsComparator.ArgsChecker checker = new SeedsComparator.ArgsChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckArgsCheckMode_ifArgsModeIsOff() {
        SeedsComparator comparator = new SeedsComparator(null);
        SeedsComparator.ArgsChecker checker = new SeedsComparator.ArgsChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckArgsCheckMode_ifArgsModeIsOnAndCheckingFail() {
        TestSeed target = new TestSeed(null, new Object[0]);
        SeedsComparator comparator = new SeedsComparator(target).args(Faker.int_().random());
        SeedsComparator.ArgsChecker checker = new SeedsComparator.ArgsChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckArgsCheckMode_ifArgsModeIsOnAndCheckingSuccess() {
        Object[] args = new Object[]{Faker.int_().random(), Faker.str_().random()};
        TestSeed target = new TestSeed(null, args);
        SeedsComparator comparator = new SeedsComparator(target).args(args);
        SeedsComparator.ArgsChecker checker = new SeedsComparator.ArgsChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckArgsAreEmptyCheckMode_ifIsNullModeIsOn() {
        SeedsComparator comparator = new SeedsComparator(null).isNull();
        SeedsComparator.ArgsAreEmptyChecker checker = new SeedsComparator.ArgsAreEmptyChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckArgsAreEmptyCheckMode_ifArgsAreEmptyModeIsOff() {
        SeedsComparator comparator = new SeedsComparator(null);
        SeedsComparator.ArgsAreEmptyChecker checker = new SeedsComparator.ArgsAreEmptyChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckArgsAreEmptyCheckMode_ifArgsAreEmptyModeIsOnAndCheckingFail() {
        TestSeed target = new TestSeed(null, new Object[]{Faker.str_().random()});
        SeedsComparator comparator = new SeedsComparator(target).argsAreEmpty();
        SeedsComparator.ArgsAreEmptyChecker checker = new SeedsComparator.ArgsAreEmptyChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isFalse();
    }

    @Test
    void shouldCheckArgsAreEmptyCheckMode_ifArgsAreEmptyModeIsOnAndCheckingSuccess() {
        TestSeed target = new TestSeed(null, new Object[0]);
        SeedsComparator comparator = new SeedsComparator(target).argsAreEmpty();
        SeedsComparator.ArgsAreEmptyChecker checker = new SeedsComparator.ArgsAreEmptyChecker();

        Optional<Boolean> result = checker.apply(comparator);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    void shouldCheckComparison_isNullMode_whenTargetSeedIsNotNull() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).isNull().compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_isNullMode_whenTargetSeedIsNull() {
        boolean result = new SeedsComparator(null).isNull().compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_codeMode_whenTargetSeedIsNull() {
        boolean result = new SeedsComparator(null).code(Faker.str_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_codeMode_whenTargetSeedIsNotNullButCodeNull() {
        TestSeed seed = new TestSeed(null, new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).code(Faker.str_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_codeMode_whenTargetSeedIsNotNullButCodeInvalid() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).code(Faker.str_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_codeMode_whenTargetSeedIsNotNullCodeRight() {
        String code = Faker.str_().random();
        TestSeed seed = new TestSeed(code, new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).code(code).compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_argsMode_whenTargetSeedIsNull() {
        boolean result = new SeedsComparator(null).args(Faker.str_().random(), Faker.uuid_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_argsMode_whenTargetSeedIsNotNullButArgsInvalid() {
        Object[] initArgs = {Faker.int_().random(), Faker.uuid_().random()};
        Object[] args = {Faker.int_().random(), Faker.uuid_().random()};
        TestSeed seed = new TestSeed(Faker.str_().random(), initArgs);
        boolean result = new SeedsComparator(seed).args(args).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_argsMode_whenTargetSeedIsNotNullArgsRight() {
        Object[] args = {Faker.int_().random(), Faker.uuid_().random()};
        TestSeed seed = new TestSeed(Faker.str_().random(), args);
        boolean result = new SeedsComparator(seed).args(args).compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_argsAreEmptyMode_isNotEmpty() {
        Object[] args = {Faker.str_().random(), Faker.uuid_().random(), Faker.int_().random()};

        boolean result = new SeedsComparator(new TestSeed(null, args)).argsAreEmpty().compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_argsAreEmptyMode_isNull() {
        boolean result = new SeedsComparator(new TestSeed(null, null)).argsAreEmpty().compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_argsAreEmptyMode_isEmpty() {
        boolean result = new SeedsComparator(new TestSeed(null, new Object[0])).argsAreEmpty().compare();

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            ",,,,true",
            ",,,1-2-3,false",
            ",,4-5-6,,false",
            ",,4-5-6,1-2-3,false",
            ",,1-2-3,1-2-3,true",
            ",codex,,,false",
            ",codex,,1-2-3,false",
            ",codex,4-5-6,,false",
            ",codex,4-5-6,1-2-3,false",
            ",codex,1-2-3,1-2-3,false",
            "code,,,,false",
            "code,,,1-2-3,false",
            "code,,4-5-6,,false",
            "code,,4-5-6,1-2-3,false",
            "code,,1-2-3,1-2-3,false",
            "code,codex,,,false",
            "code,codex,,1-2-3,false",
            "code,codex,4-5-6,,false",
            "code,codex,4-5-6,1-2-3,false",
            "code,codex,1-2-3,1-2-3,false",
            "code,code,,,true",
            "code,code,,1-2-3,false",
            "code,code,4-5-6,,false",
            "code,code,4-5-6,1-2-3,false",
            "code,code,1-2-3,1-2-3,true",
    })
    void shouldCheckCodeAndArgsComparison(String code0, String code1, String argsLine0, String argsLine1, boolean expectedResult) {
        Object[] args0 = parseArgs(argsLine0);
        Object[] args1 = parseArgs(argsLine1);

        TestSeed target = new TestSeed(code0, args0);
        boolean result = new SeedsComparator(target).code(code1).args(args1).compare();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckBackMethod() {
        ResultsComparator parent = new ResultsComparator(null);
        ResultsComparator gottenParent = new SeedsComparator(null, parent).back();

        assertThat(parent).isEqualTo(gottenParent);
    }

    private record TestSeed(String code, Object[] args) implements Seed {}

    private Object[] parseArgs(String line) {
        if (line == null){
            return null;
        } else if (line.equals("-")) {
            return new Object[0];
        }

        return Arrays.stream(line.split("-")).toArray();
    }
}