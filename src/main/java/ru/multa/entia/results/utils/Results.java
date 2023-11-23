package ru.multa.entia.results.utils;

import ru.multa.entia.results.api.result.Result;

import java.util.Objects;

public class Results {
    public static boolean equal(Result<?> r0, Result<?> r1) {
        return (r0 == r1) ||
                (r0 != null &&
                        r1 != null &&
                        r0.ok() == r1.ok() &&
                        Objects.equals(r0.value(), r1.value()) &&
                        Seeds.equal(r0.seed(), r1.seed())
                );
    }

    public static ResultsComparator comparator(final Result<?> target) {
        return new ResultsComparator(target);
    }
}
