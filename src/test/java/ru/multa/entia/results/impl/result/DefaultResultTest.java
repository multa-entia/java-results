package ru.multa.entia.results.impl.result;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.seed.Seed;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultResultTest {

    @Test
    void shouldCheckCreation_ifOkFalse() {
        DefaultResult<String> result = new DefaultResult<>(false, null, null);
        assertThat(result.ok()).isFalse();
    }

    @Test
    void shouldCheckCreation_ifValueNull() {
        DefaultResult<String> result = new DefaultResult<>(false, null, null);
        assertThat(result.value()).isNull();
    }

    @Test
    void shouldCheckCreation_ifSeedNull() {
        DefaultResult<String> result = new DefaultResult<>(false, null, null);
        assertThat(result.seed()).isNull();
    }

    @Test
    void shouldCheckCreation() {
        final String expectedValue = Faker.str_().random(5, 10);
        final TestSeed expectedSeed = new TestSeed(Faker.int_().between(5, 10));

        DefaultResult<String> result = new DefaultResult<>(true, expectedValue, expectedSeed);

        assertThat(result.ok()).isTrue();
        assertThat(result.value()).isEqualTo(expectedValue);
        assertThat(result.seed()).isEqualTo(expectedSeed);
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class TestSeed implements Seed {
        private final int value;

        @Override
        public String code() { return null; }
        @Override
        public Object[] args() { return new Object[0];}
    }
}