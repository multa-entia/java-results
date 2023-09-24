package ru.multa.entia.results.impl.seed;

import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSeedFactoryTest {

    @Test
    void shouldCheckOnlyCodeCreation_ifCodeNull() {
        final Seed seed = new DefaultSeedFactory().create(null);

        assertThat(seed.code()).isEqualTo(DefaultSeedFactory.DEFAULT_CODE);
        assertThat(Arrays.equals(seed.args(), DefaultSeedFactory.DEFAULT_ARGS)).isTrue();
    }

    @Test
    void shouldCheckOnlyCodeCreation() {
        final String expectedCode = Faker.str_().random(5, 10);
        final Seed seed = new DefaultSeedFactory().create(expectedCode);

        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(Arrays.equals(seed.args(), DefaultSeedFactory.DEFAULT_ARGS)).isTrue();
    }

    @Test
    void shouldCheckCreation_ifCodeAndArgsNull() {
        final Seed seed = new DefaultSeedFactory().create(null, null);

        assertThat(seed.code()).isEqualTo(DefaultSeedFactory.DEFAULT_CODE);
        assertThat(Arrays.equals(seed.args(), DefaultSeedFactory.DEFAULT_ARGS)).isTrue();
    }

    @Test
    void shouldCheckCreation() {
        final String template = "[a-c]{10:20}.[x-z]{5:7}";
        final String expectedCode = Faker.str_().fromTemplate(template);
        final Integer argQuantity = Faker.int_().between(5, 10);
        final Object[] expectedArgs = new Object[argQuantity];
        for (int i = 0; i < argQuantity; i++) {
            expectedArgs[i] = Faker.str_().fromTemplate(template);
        }

        final Seed seed = new DefaultSeedFactory().create(expectedCode, expectedArgs);
        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(Arrays.equals(seed.args(), expectedArgs)).isTrue();
    }
}