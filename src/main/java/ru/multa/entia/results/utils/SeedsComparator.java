package ru.multa.entia.results.utils;

import ru.multa.entia.results.api.seed.Seed;

import java.util.Arrays;
import java.util.Objects;

public class SeedsComparator {
    private enum Mode {
        NONE,
        IS_NULL,
        CODE,
        ARGS,
        CODE_AND_ARGS
    }

    private final Seed target;
    private final ResultsComparator parent;

    public SeedsComparator(final Seed target) {
        this(target, null);
    }

    public SeedsComparator(final Seed target, final ResultsComparator parent) {
        this.target = target;
        this.parent = parent;
    }

    private Mode mode = Mode.NONE;
    private String code = null;
    private Object[] args = new Object[0];

    public SeedsComparator isNull() {
        mode = Mode.IS_NULL;
        return this;
    }

    public SeedsComparator code(final String code) {
        this.mode = this.mode.equals(Mode.ARGS) || this.mode.equals(Mode.CODE_AND_ARGS)
                ? Mode.CODE_AND_ARGS
                : Mode.CODE;
        this.code = code;

        return this;
    }

    public SeedsComparator args(final Object... args) {
        this.mode = this.mode.equals(Mode.CODE) || this.mode.equals(Mode.CODE_AND_ARGS)
                ? Mode.CODE_AND_ARGS
                : Mode.ARGS;
        this.args = args;

        return this;
    }

    public boolean compare() {
        switch (mode) {
            case IS_NULL -> {
                return target == null;
            }

            case CODE -> {
                return checkCode();
            }

            case ARGS -> {
                return checkArgs();
            }

            case CODE_AND_ARGS -> {
                return checkCode() && checkArgs();
            }

            default -> {
                return false;
            }
        }
    }

    public ResultsComparator back() {
        parent.setSeedsComparator(this);
        return parent;
    }

    private boolean checkCode() {
        return target != null && Objects.equals(target.code(), code);
    }

    private boolean checkArgs() {
        return target != null && Arrays.equals(target.args(), args);
    }
}
