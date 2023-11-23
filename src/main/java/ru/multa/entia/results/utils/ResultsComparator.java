package ru.multa.entia.results.utils;

import lombok.RequiredArgsConstructor;
import ru.multa.entia.results.api.result.Result;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class ResultsComparator {
    private enum Mode {
        IS_NULL,
        OK,
        VALUE,
        SEED
    }

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

    public SeedsComparator seedsComparator() {
        this.modes.remove(Mode.IS_NULL);
        this.modes.add(Mode.SEED);
        return new SeedsComparator(target.seed() != null ? target.seed() : null, this);
    }

    public void setSeedsComparator(final SeedsComparator seedsComparator) {
        this.seedsComparator = seedsComparator;
    }

    public boolean compare() {
        if (modes.contains(Mode.IS_NULL)) {
            return target == null;
        }

        boolean result = !modes.isEmpty();
        for (Mode mode : modes) {
            switch (mode) {
                case OK -> {
                    result &= target != null && ok == target.ok();
                }

                case VALUE -> {
                    result &= target != null && Objects.equals(value, target.value());
                }

                case SEED -> {
                    result &= seedsComparator.compare();
                }
            }
        }

        return result;
    }
}
