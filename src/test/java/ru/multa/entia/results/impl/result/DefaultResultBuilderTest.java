package ru.multa.entia.results.impl.result;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.result.ResultBuilder;
import ru.multa.entia.results.api.seed.Seed;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultResultBuilderTest {

    @SneakyThrows
    @Test
    void shouldCheckOkSetting_ifSuccessFalse() {
        ResultBuilder<String> builder = new DefaultResultBuilder<>();
        builder.success(false);
        Object result = getPrivateField(builder, "ok");

        assertThat(result).isEqualTo(false);
    }

    @SneakyThrows
    @Test
    void shouldCheckValueSetting() {
        String expectedValue = Faker.str_().random(5, 10);
        ResultBuilder<String> builder = new DefaultResultBuilder<String>().value(expectedValue);
        Object result = getPrivateField(builder, "value");

        assertThat(result).isEqualTo(expectedValue);
    }

    @SneakyThrows
    @Test
    void shouldCheckSeedSetting() {
        TestSeed expectedSeed = new TestSeed(Faker.str_().random(), Faker.str_().random());
        ResultBuilder<String> builder = new DefaultResultBuilder<String>().seed(expectedSeed);
        Object result = getPrivateField(builder, "seed");

        assertThat(result).isEqualTo(expectedSeed);
    }

    @SneakyThrows
    @Test
    void shouldCheckSeedBuilderGetting() {
        String expectedCode = Faker.str_().random(20);
        Object[] expectedArgs = {
                Faker.str_().random(10),
                Faker.str_().random(10),
                Faker.str_().random(10)
        };

        ResultBuilder<String> builder = new DefaultResultBuilder<String>()
                .seedBuilder()
                .code(expectedCode)
                .addLastArgs(expectedArgs)
                .apply();
        Seed seed = (Seed) getPrivateField(builder, "seed");

        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(Arrays.equals(seed.args(), expectedArgs)).isTrue();
    }

    @Test
    void shouldCheckBuilding() {
        boolean expectedSuccess = true;
        String expectedValue = Faker.str_().random(5, 10);
        String expectedCode = Faker.str_().random(5, 10);
        String expectedArg = Faker.str_().random(5, 10);

        Result<String> result = new DefaultResultBuilder<String>()
                .success(expectedSuccess)
                .value(expectedValue)
                .seedBuilder()
                .code(expectedCode)
                .addLastArgs(expectedArg)
                .apply()
                .build();

        assertThat(result.ok()).isEqualTo(expectedSuccess);
        assertThat(result.value()).isEqualTo(expectedValue);
        assertThat(result.seed().code()).isEqualTo(expectedCode);
        Object[] args = result.seed().args();
        assertThat(args).hasSize(1);
        assertThat(args[0]).isEqualTo(expectedArg);
    }

    @Test
    void shouldCheckBuilding_ok_withoutArgs() {
        Result<Object> result = DefaultResultBuilder.<Object>ok();

        assertThat(result.ok()).isTrue();
        assertThat(result.value()).isNull();
        assertThat(result.seed()).isNull();
    }

    @Test
    void shouldCheckBuilding_ok() {
        String expectedValue = Faker.str_().random(5, 10);
        Result<String> result = DefaultResultBuilder.<String>ok(expectedValue);

        assertThat(result.ok()).isTrue();
        assertThat(result.value()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckBuilding_fail() {
        String expectedCode = Faker.str_().random(5, 10);
        String expectedArg = Faker.str_().random(5, 10);

        Result<String> result = DefaultResultBuilder.<String>fail(expectedCode, expectedArg);

        assertThat(result.ok()).isFalse();
        assertThat(result.seed().code()).isEqualTo(expectedCode);
        Object[] args = result.seed().args();
        assertThat(args).hasSize(1);
        assertThat(args[0]).isEqualTo(expectedArg);
    }

    @Test
    void shouldCheckBuilding_failFromSeed() {
        String expectedCode = Faker.str_().random(5, 10);
        Object[] expectedArgs = {
                Faker.str_().random(5, 10),
                Faker.str_().random(5, 10)
        };

        Result<String> result = DefaultResultBuilder.<String>fail(new TestSeed(expectedCode, expectedArgs));

        assertThat(result.ok()).isFalse();
        assertThat(result.value()).isNull();
        assertThat(result.seed().code()).isEqualTo(expectedCode);
        assertThat(Arrays.equals(result.seed().args(), expectedArgs)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "some-value,",
            ","
    })
    void shouldCheckComputing_fromValueAndSeed_ifOk(String value, String code) {
        Result<String> result = DefaultResultBuilder.<String>compute(
                value,
                code == null ? null : new TestSeed(code)
        );

        assertThat(result.ok()).isTrue();
        assertThat(result.value()).isEqualTo(value);
        assertThat(result.seed()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
            ",some-code",
            "some-value,some-code"
    })
    void shouldCheckComputing_fromValueAndSeed_ifFail(String value, String code) {
        TestSeed expectedSeed = new TestSeed(code);
        Result<String> result = DefaultResultBuilder.<String>compute(value, expectedSeed);

        assertThat(result.ok()).isFalse();
        assertThat(result.value()).isNull();
        assertThat(result.seed().code()).isEqualTo(expectedSeed.code);
        assertThat(Arrays.equals(result.seed().args(), result.seed().args())).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "some-value,",
            ","
    })
    void shouldCheckComputing_fromValueAndCode_ifOk(String value, String code) {
        Result<String> result = DefaultResultBuilder.<String>compute(value, code);

        assertThat(result.ok()).isTrue();
        assertThat(result.value()).isEqualTo(value);
        assertThat(result.seed()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
            ",some-code",
            "some-value,some-code"
    })
    void shouldCheckComputing_fromValueAndCode_ifFail(String value, String code) {
        Result<String> result = DefaultResultBuilder.<String>compute(value, code);

        assertThat(result.ok()).isFalse();
        assertThat(result.value()).isNull();
        assertThat(result.seed().code()).isEqualTo(code);
        assertThat(result.seed().args()).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "some-value,",
            ","
    })
    void shouldCheckComputing_fromSupplier_ifOk(String value, String code) {
        Result<String> result = DefaultResultBuilder.<String>compute(
                () -> {
                    return value;
                },
                () -> {
                    return code != null ? new TestSeed(code) : null;
                }
        );

        assertThat(result.ok()).isTrue();
        assertThat(result.value()).isEqualTo(value);
        assertThat(result.seed()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
            ",some-code",
            "some-value,some-code"
    })
    void shouldCheckComputing_fromSupplier_ifFail(String value, String code) {
        Result<String> result = DefaultResultBuilder.<String>compute(
                () -> {
                    return value;
                },
                () -> {
                    return code != null ? new TestSeed(code) : null;
                }
        );

        assertThat(result.ok()).isFalse();
        assertThat(result.value()).isNull();
        assertThat(result.seed().code()).isEqualTo(code);
        assertThat(result.seed().args()).isEmpty();
    }

    private Object getPrivateField(Object instance, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(name);
        field.setAccessible(true);

        return field.get(instance);
    }

    private record TestSeed(String code, Object... args) implements Seed {}
}