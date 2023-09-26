package ru.multa.entia.results.impl.seed;

import ru.multa.entia.results.api.result.ResultBuilder;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DefaultSeedBuilder<T> implements SeedBuilder<T> {
    public static final String DEFAULT_CODE = "";

    private final Deque<Object> args = new ArrayDeque<>();

    private String code = DEFAULT_CODE;
    private ResultBuilder<T> resultBuilder;

    public DefaultSeedBuilder() {
    }

    public DefaultSeedBuilder(ResultBuilder<T> result) {
        this.resultBuilder = result;
    }

    @Override
    public SeedBuilder<T> code(final String code) {
        this.code = code != null ? code : DEFAULT_CODE;
        return this;
    }

    @Override
    public SeedBuilder<T> addFirstArgs(final Object... args) {
        if (args != null){
            for (Object arg : args) {
                this.args.addFirst(arg);
            }
        }
        return this;
    }

    @Override
    public SeedBuilder<T> addLastArgs(final Object... args) {
        if (args != null && args.length != 0){
            this.args.addAll(List.of(args));
        }
        return this;
    }

    @Override
    public Seed build() {
        return new DefaultSeed(code, args.toArray());
    }

    @Override
    public ResultBuilder<T> apply() {
        resultBuilder.seed(new DefaultSeed(code, args.toArray()));
        return resultBuilder;
    }
}
