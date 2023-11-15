package ru.multa.entia.results.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SeedsTest {
    @ParameterizedTest
    @CsvSource({
            ",,,,true",
            ",,,-,false",
            ",,,a-b-c,false",
            ",,,1-2-3,false",
            ",,-,,false",
            ",,-,-,true",
            ",,-,a-b-c,false",
            ",,-,1-2-3,false",
            ",,a-b-c,,false",
            ",,a-b-c,-,false",
            ",,a-b-c,a-b-c,true",
            ",,a-b-c,1-2-3,false",
            ",,1-2-3,,false",
            ",,1-2-3,-,false",
            ",,1-2-3,a-b-c,false",
            ",,1-2-3,1-2-3,true",
            ",code,,,false",
            ",code,,-,false",
            ",code,,a-b-c,false",
            ",code,,1-2-3,false",
            ",code,-,,false",
            ",code,-,-,false",
            ",code,-,a-b-c,false",
            ",code,-,1-2-3,false",
            ",code,a-b-c,,false",
            ",code,a-b-c,-,false",
            ",code,a-b-c,a-b-c,false",
            ",code,a-b-c,1-2-3,false",
            ",code,1-2-3,,false",
            ",code,1-2-3,-,false",
            ",code,1-2-3,a-b-c,false",
            ",code,1-2-3,1-2-3,false",
            "code,,,,false",
            "code,,,-,false",
            "code,,,a-b-c,false",
            "code,,,1-2-3,false",
            "code,,-,,false",
            "code,,-,-,false",
            "code,,-,a-b-c,false",
            "code,,-,1-2-3,false",
            "code,,a-b-c,,false",
            "code,,a-b-c,-,false",
            "code,,a-b-c,a-b-c,false",
            "code,,a-b-c,1-2-3,false",
            "code,,1-2-3,,false",
            "code,,1-2-3,-,false",
            "code,,1-2-3,a-b-c,false",
            "code,,1-2-3,1-2-3,false",
            "code,codex,,,false",
            "code,codex,,-,false",
            "code,codex,,a-b-c,false",
            "code,codex,,1-2-3,false",
            "code,codex,-,,false",
            "code,codex,-,-,false",
            "code,codex,-,a-b-c,false",
            "code,codex,-,1-2-3,false",
            "code,codex,a-b-c,,false",
            "code,codex,a-b-c,-,false",
            "code,codex,a-b-c,a-b-c,false",
            "code,codex,a-b-c,1-2-3,false",
            "code,codex,1-2-3,,false",
            "code,codex,1-2-3,-,false",
            "code,codex,1-2-3,a-b-c,false",
            "code,codex,1-2-3,1-2-3,false",
            "code,code,,,true",
            "code,code,,-,false",
            "code,code,,a-b-c,false",
            "code,code,,1-2-3,false",
            "code,code,-,,false",
            "code,code,-,-,true",
            "code,code,-,a-b-c,false",
            "code,code,-,1-2-3,false",
            "code,code,a-b-c,,false",
            "code,code,a-b-c,-,false",
            "code,code,a-b-c,a-b-c,true",
            "code,code,a-b-c,1-2-3,false",
            "code,code,1-2-3,,false",
            "code,code,1-2-3,-,false",
            "code,code,1-2-3,a-b-c,false",
            "code,code,1-2-3,1-2-3,true"
    })
    void shouldCheckEqualMethod(String code0, String code1, String argsLine0, String argsLine1, boolean expectedResult) {
        Object[] args0 = parseArgs(argsLine0);
        Object[] args1 = parseArgs(argsLine1);

        TestSeed seed0 = new TestSeed(code0, args0);
        TestSeed seed1 = new TestSeed(code1, args1);

        assertThat(Seeds.equal(seed0, seed1)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            ",,true",
            ",code,false",
            "code,,false",
    })
    void shouldCheckEqualMethod_ifSomeSeedIsNull(String code0, String code1, boolean expectedResult) {
        TestSeed seed0 = code0 != null ? new TestSeed(code0, new Object[0]) : null;
        TestSeed seed1 = code1 != null ? new TestSeed(code1, new Object[0]) : null;

        assertThat(Seeds.equal(seed0, seed1)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckEqualMethod_ifOnceObject() {
        TestSeed seed = new TestSeed("code", new Object[]{1, 2, 3});

        assertThat(Seeds.equal(seed, seed)).isTrue();
    }

    private Object[] parseArgs(String line) {
        if (line == null){
            return null;
        } else if (line.equals("-")) {
            return new Object[0];
        }

        return Arrays.stream(line.split("-")).toArray();
    }

    private record TestSeed(String code, Object[] args) implements Seed {}
}