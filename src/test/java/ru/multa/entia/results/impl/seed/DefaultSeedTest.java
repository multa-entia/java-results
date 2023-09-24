package ru.multa.entia.results.impl.seed;

import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSeedTest {

    @Test
    void shouldCheckCreation_ifCodeNull() {
        DefaultSeed seed = new DefaultSeed(null);
        assertThat(seed.code()).isNull();
    }

    @Test
    void shouldCheckCreation_ifArgsNull() {
        DefaultSeed seed = new DefaultSeed(null, null);
        assertThat(seed.args()).isNull();
    }

    @Test
    void shouldCheckCreation() {
        String template = "[a-c]{10:20}.[x-z]{5:7}";
        String expectedCode = Faker.str_().fromTemplate(template);
        Integer argQuantity = Faker.int_().between(5, 10);
        Object[] expectedArgs = new Object[argQuantity];
        for (int i = 0; i < argQuantity; i++) {
            expectedArgs[i] = Faker.str_().fromTemplate(template);
        }

        DefaultSeed seed = new DefaultSeed(expectedCode, expectedArgs);
        assertThat(seed.code()).isEqualTo(expectedCode);
        assertThat(seed.args()).isEqualTo(expectedArgs);
    }
}