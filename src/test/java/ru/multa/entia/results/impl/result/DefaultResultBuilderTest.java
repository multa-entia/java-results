package ru.multa.entia.results.impl.result;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.result.ResultBuilder;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultResultBuilderTest {

    // TODO: 25.09.2023 !!!
//    @SneakyThrows
//    @Test
//    void shouldCheckOkSetting_ifSuccessFalse() {
//        ResultBuilder<String> builder = new DefaultResultBuilder<>();
//        builder.success(false);
//        Object result = getPrivateField(builder, "ok");
//
//        assertThat(result).isEqualTo(false);
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckValueSetting() {
//        String expectedValue = Faker.str_().random(5, 10);
//        ResultBuilder<String> builder = new DefaultResultBuilder<String>().value(expectedValue);
//        Object result = getPrivateField(builder, "value");
//
//        assertThat(result).isEqualTo(expectedValue);
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckCodeSetting() {
//        String expectedCode = Faker.str_().random(5, 10);
//        ResultBuilder<String> builder = new DefaultResultBuilder<String>().code(expectedCode);
//        Object result = getPrivateField(builder, "code");
//
//        assertThat(result).isEqualTo(expectedCode);
//    }

    // TODO: 25.09.2023 !!!
//    @SneakyThrows
//    @Test
//    void shouldCheckArgsSetting_withoutDirect_ifNull() {
//        ResultBuilder<String> builder = new DefaultResultBuilder<String>().args(null);
//        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");
//
//        assertThat(result).isEmpty();
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckArgsSetting_withoutDirect() {
//        Object[][] initArgs = {
//                {Faker.str_().random(), Faker.str_().random()},
//                {Faker.str_().random()},
//                {Faker.str_().random(), Faker.str_().random(), Faker.str_().random()}
//        };
//
//        Deque<Object> expected = new ArrayDeque<>();
//        for (Object[] item : initArgs) {
//            expected.addAll(Arrays.asList(item));
//        }
//
//        DefaultResultBuilder<String> builder = new DefaultResultBuilder<>();
//        for (Object[] args : initArgs) {
//            builder.args(args);
//        }
//        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");
//
//        assertThat(Arrays.equals(result.toArray(), expected.toArray())).isTrue();
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckArgsMethod_ifArgsNull() {
//        ResultBuilder<String> builder = new DefaultResultBuilder<String>().args(true, null);
//        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");
//
//        assertThat(result).isEmpty();
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckArgsMethod() {
//        Object[] expectedArgs = {
//                Faker.str_().random(5, 10),
//                Faker.str_().random(5, 10),
//                Faker.str_().random(5, 10),
//
//                Faker.str_().random(5, 10),
//                Faker.str_().random(5, 10),
//                Faker.str_().random(5, 10),
//                Faker.str_().random(5, 10),
//
//                Faker.str_().random(5, 10),
//                Faker.str_().random(5, 10)
//        };
//
//        DefaultResultBuilder<String> builder = new DefaultResultBuilder<>();
//
//        for (int i = 3; i <= 6; i++) {
//            builder.args(true, expectedArgs[i]);
//        }
//
//        for (int i = 2; i >= 0; i--) {
//            builder.args(false, expectedArgs[i]);
//        }
//
//        for (int i = 7; i <= 8; i++){
//            builder.args(true, expectedArgs[i]);
//        }
//
//        Deque<Object> result = (Deque<Object>) getPrivateField(builder, "args");
//
//        assertThat(Arrays.equals(result.toArray(), expectedArgs)).isTrue();
//    }

    @Test
    void shouldCheckBuilding() {

    }

    @Test
    void shouldCheckBuilding_ok() {

    }

    @Test
    void shouldCheckBuilding_fail() {

    }

    private Object getPrivateField(Object instance, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(name);
        field.setAccessible(true);

        return field.get(instance);
    }
}