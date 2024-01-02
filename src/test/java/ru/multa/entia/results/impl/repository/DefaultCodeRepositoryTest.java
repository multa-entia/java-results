package ru.multa.entia.results.impl.repository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultCodeRepositoryTest {

    @Test
    void shouldCheckParams_withoutArgs() {
        DefaultCodeRepository.Params params = new DefaultCodeRepository.Params();

        assertThat(params.closed()).isFalse();
        assertThat(params.updatable()).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "false,false",
            "false,true",
            "true,false",
            "true,true"
    })
    void shouldCheckParams(boolean expectedClosed, boolean expectedUpdatable) {
        DefaultCodeRepository.Params params = new DefaultCodeRepository.Params(expectedClosed, expectedUpdatable);

        assertThat(params.closed()).isEqualTo(expectedClosed);
        assertThat(params.updatable()).isEqualTo(expectedUpdatable);
    }

    @Test
    void shouldCheckAbsenceCodeGenerator_keyIsNull() {
        String expectedCode = "absence-code-null";
        String result = new DefaultCodeRepository.AbsenceCodeGenerator().apply(null);

        assertThat(result).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckAbsenceCodeGenerator() {
        UUID key = Faker.uuid_().random();
        String codeTemplate = "absence-code-%s";
        String result = new DefaultCodeRepository.AbsenceCodeGenerator().apply(key);

        assertThat(result).isEqualTo(String.format(codeTemplate, key));
    }

    @SneakyThrows
    @Test
    void shouldCheckInstanceCreation_ifParamsNull() {
        CodeRepository repository = DefaultCodeRepository.newInstance(null, null, null);

        Field field = repository.getClass().getDeclaredField("params");
        field.setAccessible(true);
        Object params = field.get(repository);

        assertThat(params).isEqualTo(new DefaultCodeRepository.Params());
    }

    @SneakyThrows
    @Test
    void shouldCheckInstanceCreation_ifAbsenceCodeGeneratorNull() {
        CodeRepository repository = DefaultCodeRepository.newInstance(null, null, null);

        Field field = repository.getClass().getDeclaredField("absenceCodeGenerator");
        field.setAccessible(true);
        Object generator = field.get(repository);

        assertThat(generator.getClass()).isEqualTo(DefaultCodeRepository.AbsenceCodeGenerator.class);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckInstanceCreation_ifInitCodesNull() {
        CodeRepository repository = DefaultCodeRepository.newInstance(null, null, null);

        Field field = repository.getClass().getDeclaredField("codes");
        field.setAccessible(true);
        Map<Object, String> codes = (Map<Object, String>) field.get(repository);

        assertThat(codes).isEmpty();
    }

    @SneakyThrows
    @Test
    void shouldCheckInstanceCreation() {
        DefaultCodeRepository.Params expectedParams = new DefaultCodeRepository.Params(true, true);
        Function<Object, String> expectedAbsenceCodeGenerator = obj -> {return "";};
        Map<Object, String> expectedCodes = new HashMap<>() {{
            put(Faker.uuid_().random(), Faker.str_().random());
        }};

        CodeRepository repository = DefaultCodeRepository.newInstance(
                expectedParams,
                expectedAbsenceCodeGenerator,
                expectedCodes
        );

        Class<? extends CodeRepository> repoType = repository.getClass();
        Field field = repoType.getDeclaredField("params");
        field.setAccessible(true);
        Object params = field.get(repository);

        field = repoType.getDeclaredField("absenceCodeGenerator");
        field.setAccessible(true);
        Object generator = field.get(repository);

        field = repoType.getDeclaredField("codes");
        field.setAccessible(true);
        Object codes = field.get(repository);

        assertThat(params).isEqualTo(expectedParams);
        assertThat(generator).isEqualTo(expectedAbsenceCodeGenerator);
        assertThat(codes).isEqualTo(expectedCodes);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckUpdating_ifClosed() {
        DefaultCodeRepository.Params initParams = new DefaultCodeRepository.Params(true, false);
        Map<Object, String> initCodes = new HashMap<>() {{
            put(Faker.uuid_().random(), Faker.str_().random());
            put(Faker.uuid_().random(), Faker.str_().random());
            put(Faker.uuid_().random(), Faker.str_().random());
        }};
        CodeRepository repository = DefaultCodeRepository.newInstance(initParams, null, initCodes);

        boolean result = repository.update(Faker.str_().random(), Faker.str_().random());

        Field field = repository.getClass().getDeclaredField("codes");
        field.setAccessible(true);
        Map<Object, String> gottenCodes = (Map<Object, String>) field.get(repository);

        assertThat(result).isFalse();
        assertThat(gottenCodes).isEqualTo(initCodes);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckUpdating_ifNotUpdatableAndCodeAbsence() {
        DefaultCodeRepository.Params initParams = new DefaultCodeRepository.Params(false, false);
        CodeRepository repository = DefaultCodeRepository.newInstance(initParams, null, null);

        boolean result = true;
        HashMap<Object, String> codes = new HashMap<>();
        Integer range = Faker.int_().between(10, 20);
        for (int i = 0; i < range; i++) {
            UUID key = Faker.uuid_().random();
            String code = Faker.str_().random();
            result &= repository.update(key, code);
            codes.put(key, code);
        }

        Field field = repository.getClass().getDeclaredField("codes");
        field.setAccessible(true);
        Map<Object, String> gottenCodes = (Map<Object, String>) field.get(repository);

        assertThat(result).isTrue();
        assertThat(gottenCodes).isEqualTo(codes);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckUpdating_ifNotUpdatableAndCodePresented() {
        DefaultCodeRepository.Params initParams = new DefaultCodeRepository.Params(false, false);
        CodeRepository repository = DefaultCodeRepository.newInstance(initParams, null, null);

        UUID key = Faker.uuid_().random();
        String firstCode = Faker.str_().random();
        boolean firstResult = repository.update(key, firstCode);
        HashMap<Object, String> codes = new HashMap<>(){{
            put(key, firstCode);
        }};

        boolean result = false;
        Integer range = Faker.int_().between(10, 20);
        for (int i = 0; i < range; i++) {
            result |= repository.update(key, Faker.str_().random());
        }

        Field field = repository.getClass().getDeclaredField("codes");
        field.setAccessible(true);
        Map<Object, String> gottenCodes = (Map<Object, String>) field.get(repository);

        assertThat(firstResult).isTrue();
        assertThat(result).isFalse();
        assertThat(gottenCodes).isEqualTo(codes);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckUpdating() {
        DefaultCodeRepository.Params initParams = new DefaultCodeRepository.Params(false, true);
        CodeRepository repository = DefaultCodeRepository.newInstance(initParams, null, null);

        boolean result = true;
        HashMap<Object, String> expectedCodes = new HashMap<>();
        int keyQuantity = Faker.int_().between(3, 10);
        for (int i = 0; i < keyQuantity; i++) {
            UUID key = Faker.uuid_().random();
            int codeQuantity = Faker.int_().between(2, 7);
            for (int ii = 0; ii < codeQuantity; ii++) {
                String code = Faker.str_().random();
                expectedCodes.put(key, code);
                result &= repository.update(key, code);
            }
        }

        Field field = repository.getClass().getDeclaredField("codes");
        field.setAccessible(true);
        Map<Object, String> gottenCodes = (Map<Object, String>) field.get(repository);

        assertThat(result).isTrue();
        assertThat(gottenCodes).isEqualTo(expectedCodes);
    }

    @Test
    void shouldCheckGetting_ifAbsence() {
        String expectedCode = Faker.str_().random();
        Function<Object, String> absenceCodeGenerator = obj -> { return expectedCode; };
        CodeRepository repository = DefaultCodeRepository.newInstance(null, absenceCodeGenerator, null);

        String code = repository.get(Faker.uuid_().random());

        assertThat(code).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckGetting() {
        UUID key = Faker.uuid_().random();
        String expectedCode = Faker.str_().random();
        HashMap<Object, String> codes = new HashMap<>(){{
            put(key, expectedCode);
        }};
        CodeRepository repository = DefaultCodeRepository.newInstance(null, null, codes);

        String code = repository.get(key);

        assertThat(code).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckDefaultInstanceGetting() {
        CodeRepository firstInstance = DefaultCodeRepository.getDefaultInstance(null, null, null);
        CodeRepository secondInstance = DefaultCodeRepository.getDefaultInstance(null, null, null);

        assertThat(firstInstance).isEqualTo(secondInstance);
    }

    @SneakyThrows
    @Test
    void shouldCheckMultiThreadUsage() {
        Function<Object, String> absenceCodeGenerator = obj -> { return null; };
        CodeRepository repository = DefaultCodeRepository.newInstance(
                new DefaultCodeRepository.Params(false, true),
                absenceCodeGenerator,
                null
        );

        HashSet<UUID> keys = new HashSet<>();
        int keyQuantity = Faker.int_().between(5, 10);
        for (int i = 0; i < keyQuantity; i++) {
            keys.add(Faker.uuid_().random());
        }

        Runnable r = () -> {
            for (UUID key : keys) {
                repository.update(key, Faker.str_().random());
            }
        };

        ArrayList<Thread> threads = new ArrayList<>();
        int threadQuantity = Faker.int_().between(5, 10);
        for (int i = 0; i < threadQuantity; i++) {
            threads.add(new Thread(r));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(100);

        boolean result = true;
        for (UUID key : keys) {
            result &= repository.get(key) != null;
        }

        assertThat(result).isTrue();
    }
}