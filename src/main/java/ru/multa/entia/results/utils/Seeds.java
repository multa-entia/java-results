package ru.multa.entia.results.utils;

import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;
import java.util.Objects;

public class Seeds {
    public static boolean equal(final Seed s0, final Seed s1) {
        if (s0 == s1) {
            return true;
        } else if (s0 != null && s1 != null) {
            return Objects.equals(s0.code(), s1.code()) && Arrays.equals(s0.args(), s1.args());
        }

        return false;
    }

    public static SeedsComparator comparator(final Seed target) {
        return new SeedsComparator(target);
    }
}
