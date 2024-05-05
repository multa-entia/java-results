package ru.multa.entia.results.utils;

import lombok.RequiredArgsConstructor;
import ru.multa.entia.results.api.result.Result;

import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public class ResultsComparator {
    private enum Mode {
        IS_NULL,
        OK,
        VALUE,
        SEED,
        CAUSES
    }

    private static final EnumMap<Mode, Function<ResultsComparator, Optional<Boolean>>> CHECKERS = new EnumMap<>(Mode.class) {{
        put(Mode.IS_NULL, new IsNullChecker());
        put(Mode.OK, new OkChecker());
        put(Mode.VALUE, new ValueChecker());
        put(Mode.SEED, new SeedChecker());
    }};

    private final Result<?> target;

    private final Set<Mode> modes = new HashSet<>();

    private SeedsComparator seedsComparator;
    private boolean ok;
    private Object value;

    public ResultsComparator isNull() {
        this.modes.clear();
        this.modes.add(Mode.IS_NULL);
        return this;
    }

    public ResultsComparator isSuccess() {
        return ok(true);
    }

    public ResultsComparator isFail() {
        return ok(false);
    }

    public ResultsComparator ok(final boolean ok) {
        this.modes.remove(Mode.IS_NULL);
        this.modes.add(Mode.OK);
        this.ok = ok;
        return this;
    }

    public ResultsComparator value(final Object value) {
        this.modes.remove(Mode.IS_NULL);
        this.modes.add(Mode.VALUE);
        this.value = value;
        return this;
    }

    public ResultsComparator causes(final List<Result<?>> causes) {
        // TODO: !!!
        return null;
    }

    public SeedsComparator seedsComparator() {
        this.modes.remove(Mode.IS_NULL);
        this.modes.add(Mode.SEED);
        return new SeedsComparator(target.seed() != null ? target.seed() : null, this);
    }

    public void setSeedsComparator(final SeedsComparator seedsComparator) {
        this.seedsComparator = seedsComparator;
    }

    public boolean compare() {
        boolean result = !modes.isEmpty();
        for (Mode mode : modes) {
            Optional<Boolean> checkResult = CHECKERS.get(mode).apply(this);
            if (checkResult.isPresent()) {
                result &= checkResult.get();
            }
        }

        return result;
    }

    public static class IsNullChecker implements Function<ResultsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final ResultsComparator comparator) {
            return comparator.modes.contains(Mode.IS_NULL)
                    ? Optional.of(comparator.target == null)
                    : Optional.empty();
        }
    }

    public static class OkChecker implements Function<ResultsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final ResultsComparator comparator) {
            Set<Mode> ms = comparator.modes;
            return !ms.contains(Mode.IS_NULL) && ms.contains(Mode.OK)
                    ? Optional.of(comparator.target != null && comparator.target.ok() == comparator.ok)
                    : Optional.empty();
        }
    }

    public static class ValueChecker implements Function<ResultsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final ResultsComparator comparator) {
            Set<Mode> ms = comparator.modes;
            return !ms.contains(Mode.IS_NULL) && ms.contains(Mode.VALUE)
                    ? Optional.of(comparator.target != null && Objects.equals(comparator.target.value(), comparator.value))
                    : Optional.empty();
        }
    }

    public static class SeedChecker implements Function<ResultsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final ResultsComparator comparator) {
            Set<Mode> ms = comparator.modes;
            return !ms.contains(Mode.IS_NULL) && ms.contains(Mode.SEED)
                    ? Optional.of(comparator.target != null && comparator.seedsComparator.compare())
                    : Optional.empty();
        }
    }

    public static class CausesChecker implements Function<ResultsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final ResultsComparator comparator) {

            return null;
        }
    }
}
