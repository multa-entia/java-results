package ru.multa.entia.results.utils;

import ru.multa.entia.results.api.result.Result;

import java.util.List;
import java.util.Objects;

public class Results {
    public static boolean equal(final Result<?> r0, final Result<?> r1) {
        return (r0 == r1) ||
                (r0 != null &&
                        r1 != null &&
                        r0.ok() == r1.ok() &&
                        Objects.equals(r0.value(), r1.value()) &&
                        Seeds.equal(r0.seed(), r1.seed()) &&
                        equalCauses(r0.causes(), r1.causes())
                );
    }

    public static ResultsComparator comparator(final Result<?> target) {
        return new ResultsComparator(target);
    }

    public static boolean equalCauses(List<Result<?>> causes0, List<Result<?>> causes1) {
        if (causes0 == causes1) {
            return true;
        } else if (causes0.size() != causes1.size()) {
            return false;
        }
        boolean equal = true;
        for (int i = 0; i < causes0.size(); i++) {
            Result<?> cause0 = causes0.get(i);
            Result<?> cause1 = causes1.get(i);
            if (cause0.ok() != cause1.ok() ||
                !Objects.equals(cause0.value(), cause1.value()) ||
                !Seeds.equal(cause0.seed(), cause1.seed())) {
                equal = false;
                break;
            }
        }

        return equal;
    }
}
