package ru.multa.entia.results.impl.seed;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Deque;

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

    @SneakyThrows
    @Test
    void shouldCheckFirstArgsSetting_ifItNull() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addFirstArgs(null);
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

    @SneakyThrows
    @Test
    void shouldCheckFirstArgsSetting_ifItEmpty() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addFirstArgs();
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

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

    @SneakyThrows
    @Test
    void shouldCheckLastArgsSetting_ifItNull() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addLastArgs(null);
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

    @SneakyThrows
    @Test
    void shouldCheckLastArgsSetting_ifItEmpty() {
        SeedBuilder<Object> builder = new DefaultSeedBuilder<>().addLastArgs();
        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");

        assertThat(result).isEmpty();
    }

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

    private Object getPrivateField(Object instance, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(name);
        field.setAccessible(true);

        return field.get(instance);
    }
}