package ru.multa.entia.results.impl.seed;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Deque;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSeedBuilderTest {

    @SneakyThrows
    @Test
    void shouldCheckCodeSetting_ifCodeNull() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().code(null);
        Object result = getPrivateField(builder, "code");

        assertThat(result).isEqualTo(DefaultSeedBuilder.DEFAULT_CODE);
    }

    @SneakyThrows
    @Test
    void shouldCheckCodeSetting() {
        String expectedCode = Faker.str_().random(5, 10);
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().code(expectedCode);
        Object result = getPrivateField(builder, "code");

        assertThat(result).isEqualTo(expectedCode);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckFirstArgsSetting_ifItNull() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addFirstArgs(null);
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckFirstArgsSetting_ifItEmpty() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addFirstArgs();
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckFirstArgsSetting() {
        Object[] args = {
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        };

        Object[] expectedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            expectedArgs[args.length-1-i] = args[i];
        }

        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addFirstArgs(args);
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(Arrays.equals(result.toArray(), expectedArgs)).isTrue();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckLastArgsSetting_ifItNull() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addLastArgs(null);
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckLastArgsSetting_ifItEmpty() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addLastArgs();
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckLastArgsSetting() {
        Object[] expectedArgs = {
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        };


        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addLastArgs(expectedArgs);
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(Arrays.equals(result.toArray(), expectedArgs)).isTrue();
    }

    @Test
    void shouldCheckBuilding() {
        Object[] args = {
                Faker.str_().random(10),
                Faker.str_().random(10),

                Faker.str_().random(10),
                Faker.str_().random(10),

                Faker.str_().random(10)
        };

        Object[] expectedArgs = {
                args[3],
                args[2],
                args[0],
                args[1],
                args[4]
        };

        String expectedCode = Faker.str_().random(20);

        Seed seed = new DefaultSeedBuilder<Object>()
                .code(expectedCode)
                .addLastArgs(args[0], args[1])
                .addFirstArgs(args[2], args[3])
                .addLastArgs(args[4])
                .build();

        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(Arrays.equals(seed.args(), expectedArgs)).isTrue();
    }

    @Test
    void shouldCheckStaticMethodSeed_withoutArgs() {
        String expectedCode = Faker.str_().random();
        Seed seed = DefaultSeedBuilder.<Object>seed(expectedCode);

        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(seed.args()).isEmpty();
    }

    @Test
    void shouldCheckStaticMethodSeed() {
        String expectedCode = Faker.str_().random();
        Integer arg0 = Faker.int_().random();
        UUID arg1 = Faker.uuid_().random();

        Seed seed = DefaultSeedBuilder.<Object>seed(expectedCode, arg0, arg1);

        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(Arrays.equals(seed.args(), new Object[]{arg0, arg1})).isTrue();
    }

    @Test
    void shouldCheckComputingFromSeedSuppliers_ifTheyEmpty() {
        Seed seed = DefaultSeedBuilder.<Object>compute();

        assertThat(seed).isNull();
    }

    @Test
    void shouldCheckComputingFromSeedSuppliers_ifNoOneRetSeed() {
        Seed seed = DefaultSeedBuilder.<Object>compute(
                () -> {return null;},
                () -> {return null;},
                () -> {return null;}
        );

        assertThat(seed).isNull();
    }

    @Test
    void shouldCheckComputingFromSeedSuppliers() {
        String expectedCode0 = Faker.str_().random();
        Integer arg00 = Faker.int_().random();
        UUID arg01 = Faker.uuid_().random();

        String expectedCode1 = Faker.str_().random();
        Integer arg10 = Faker.int_().random();
        UUID arg11 = Faker.uuid_().random();

        Object[] expectedArgs = {arg00, arg01};

        Seed seed = DefaultSeedBuilder.<Object>compute(
                () -> {return null;},
                () -> {return new TestSeed(expectedCode0, expectedArgs);},
                () -> {return null;},
                () -> {return new TestSeed(expectedCode1, new Object[]{arg10, arg11});},
                () -> {return null;}
        );

        assertThat(seed.code()).isEqualTo(expectedCode0);
        assertThat(Arrays.equals(seed.args(),expectedArgs)).isTrue();
    }

    @Test
    void shouldCheckComputingFromStringSuppliers_ifTheyEmpty() {
        Seed seed = DefaultSeedBuilder.<Object>computeFromCodes();

        assertThat(seed).isNull();
    }

    @Test
    void shouldCheckComputingFromStringSuppliers_ifNoOneRetStr() {
        Seed seed = DefaultSeedBuilder.<Object>computeFromCodes(
                () -> {return null;},
                () -> {return null;},
                () -> {return null;}
        );

        assertThat(seed).isNull();
    }

    @Test
    void shouldCheckComputingFromStringSuppliers() {
        String expectedCode0 = Faker.str_().random();
        String expectedCode1 = Faker.str_().random();

        Seed seed = DefaultSeedBuilder.<Object>computeFromCodes(
                () -> {return null;},
                () -> {return expectedCode0;},
                () -> {return null;},
                () -> {return expectedCode1;},
                () -> {return null;}
        );

        assertThat(seed.code()).isEqualTo(expectedCode0);
        assertThat(seed.args()).isEmpty();
    }

    private record TestSeed(String code, Object[] args) implements Seed {}

    private Object getPrivateField(Object instance, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(name);
        field.setAccessible(true);

        return field.get(instance);
    }
}