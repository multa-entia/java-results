package ru.multa.entia.results.impl.seed;

import ru.multa.entia.results.api.result.ResultBuilder;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;

import java.util.*;
import java.util.function.Supplier;

public class DefaultSeedBuilder<T> implements SeedBuilder<T> {
    public static final String DEFAULT_CODE = "";

    private final Deque<Object> args = new ArrayDeque<>();

    private String code = DEFAULT_CODE;
    private ResultBuilder<T> resultBuilder;

    public static <T> Seed seed(final String code, final Object... args) {
        return new DefaultSeedBuilder<T>()
                .code(code)
                .addLastArgs(args)
                .build();
    }

    @SafeVarargs
    public static <T> Seed compute(final Supplier<Seed>... suppliers) {
        for (Supplier<Seed> supplier : suppliers) {
            Seed seed = supplier.get();
            if (seed != null) {
                return seed;
            }
        }
        return null;
    }

    @SafeVarargs
    public static <T> Seed computeFromStr(final Supplier<String>... suppliers) {
        for (Supplier<String> supplier : suppliers) {
            String code = supplier.get();
            if (code != null){
                return seed(code);
            }
        }
        return null;
    }

    public DefaultSeedBuilder() {
    }

    public DefaultSeedBuilder(final ResultBuilder<T> result) {
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
