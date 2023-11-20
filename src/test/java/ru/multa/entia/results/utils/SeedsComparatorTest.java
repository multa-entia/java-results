package ru.multa.entia.results.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SeedsComparatorTest {

    @Test
    void shouldCheckComparison_IfOnlyIsNull_whenTargetSeedIsNotNull() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).isNull().compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_IfOnlyIsNull_whenTargetSeedIsNull() {
        boolean result = new SeedsComparator(null).isNull().compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_IfOnlyCode_whenTargetSeedIsNull() {
        boolean result = new SeedsComparator(null).code(Faker.str_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_IfOnlyCode_whenTargetSeedIsNotNullButCodeNull() {
        TestSeed seed = new TestSeed(null, new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).code(Faker.str_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_IfOnlyCode_whenTargetSeedIsNotNullButCodeInvalid() {
        TestSeed seed = new TestSeed(Faker.str_().random(), new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).code(Faker.str_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_IfOnlyCode_whenTargetSeedIsNotNullCodeRight() {
        String code = Faker.str_().random();
        TestSeed seed = new TestSeed(code, new Object[]{Faker.int_().random(), Faker.uuid_().random()});
        boolean result = new SeedsComparator(seed).code(code).compare();

        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckComparison_IfOnlyArgs_whenTargetSeedIsNull() {
        boolean result = new SeedsComparator(null).args(Faker.str_().random(), Faker.uuid_().random()).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_IfOnlyCode_whenTargetSeedIsNotNullButArgsInvalid() {
        Object[] initArgs = {Faker.int_().random(), Faker.uuid_().random()};
        Object[] args = {Faker.int_().random(), Faker.uuid_().random()};
        TestSeed seed = new TestSeed(Faker.str_().random(), initArgs);
        boolean result = new SeedsComparator(seed).args(args).compare();

        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckComparison_IfOnlyCode_whenTargetSeedIsNotNullArgsRight() {
        Object[] args = {Faker.int_().random(), Faker.uuid_().random()};
        TestSeed seed = new TestSeed(Faker.str_().random(), args);
        boolean result = new SeedsComparator(seed).args(args).compare();

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