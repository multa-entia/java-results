package ru.multa.entia.results.utils;

import ru.multa.entia.results.api.seed.Seed;

import java.util.*;
import java.util.function.Function;

public class SeedsComparator {
    private enum Mode {
        IS_NULL,
        CODE,
        ARGS,
        ARGS_ARE_EMPTY
    }

    private static final EnumMap<Mode, Function<SeedsComparator, Optional<Boolean>>> CHECKERS = new EnumMap<>(Mode.class){{
        put(Mode.IS_NULL, new IsNullChecker());
        put(Mode.CODE, new CodeChecker());
        put(Mode.ARGS, new ArgsChecker());
        put(Mode.ARGS_ARE_EMPTY, new ArgsAreEmptyChecker());
    }};

    private final Seed target;
    private final ResultsComparator parent;

    private final Set<Mode> modes = new HashSet<>();
    private String code = null;
    private Object[] args = new Object[0];

    public SeedsComparator(final Seed target) {
        this(target, null);
    }

    public SeedsComparator(final Seed target, final ResultsComparator parent) {
        this.target = target;
        this.parent = parent;
    }

    public SeedsComparator isNull() {
        this.modes.clear();
        this.modes.add(Mode.IS_NULL);
        return this;
    }

    public SeedsComparator code(final String code) {
        this.code = code;
        this.modes.remove(Mode.IS_NULL);
        this.modes.add(Mode.CODE);
        return this;
    }

    public SeedsComparator args(final Object... args) {
        this.args = args;
        this.modes.remove(Mode.IS_NULL);
        this.modes.add(Mode.ARGS);
        return this;
    }

    public SeedsComparator argsAreEmpty() {
        this.modes.remove(Mode.IS_NULL);
        this.modes.remove(Mode.ARGS);
        this.modes.add(Mode.ARGS_ARE_EMPTY);
        return this;
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

    public ResultsComparator back() {
        parent.setSeedsComparator(this);
        return parent;
    }

    public static class IsNullChecker implements Function<SeedsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final SeedsComparator comparator) {
            return comparator.modes.contains(Mode.IS_NULL)
                    ? Optional.of(comparator.target == null)
                    : Optional.empty();
        }
    }

    public static class CodeChecker implements Function<SeedsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final SeedsComparator comparator) {
            Set<Mode> ms = comparator.modes;
            return !ms.contains(Mode.IS_NULL) && ms.contains(Mode.CODE)
                    ? Optional.of(comparator.target != null && Objects.equals(comparator.target.code(), comparator.code))
                    : Optional.empty();
        }
    }

    public static class ArgsChecker implements Function<SeedsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final SeedsComparator comparator) {
            Set<Mode> ms = comparator.modes;
            return !ms.contains(Mode.IS_NULL) && ms.contains(Mode.ARGS)
                    ? Optional.of(comparator.target != null && Arrays.equals(comparator.target.args(), comparator.args))
                    : Optional.empty();
        }
    }

    public static class ArgsAreEmptyChecker implements Function<SeedsComparator, Optional<Boolean>> {
        @Override
        public Optional<Boolean> apply(final SeedsComparator comparator) {
            Set<Mode> ms = comparator.modes;
            return !ms.contains(Mode.IS_NULL) && ms.contains(Mode.ARGS_ARE_EMPTY)
                    ? Optional.of(comparator.target != null && (comparator.target.args() == null || comparator.target.args().length == 0))
                    : Optional.empty();
        }
    }
}
