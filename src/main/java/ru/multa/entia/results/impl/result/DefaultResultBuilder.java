package ru.multa.entia.results.impl.result;

import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.result.ResultBuilder;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;
import ru.multa.entia.results.impl.seed.DefaultSeedBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class DefaultResultBuilder<T> implements ResultBuilder<T> {
    private boolean ok;
    private T value;
    private Seed seed;
    private List<Result<?>> causes = new ArrayList<>();

    public static <T> Result<T> ok(){
        return new DefaultResultBuilder<T>().success(true).value(null).build();
    }

    public static <T> Result<T> ok(final T value){
        return new DefaultResultBuilder<T>().success(true).value(value).build();
    }

    public static <T> Result<T> fail(final String code, final Object... args){
        return new DefaultResultBuilder<T>()
                .success(false)
                .seedBuilder()
                .code(code)
                .addLastArgs(args)
                .apply()
                .build();
    }

    public static <T> Result<T> fail(final Seed seed){
        return fail(seed.code(), seed.args());
    }

    public static <T> Result<T> compute(final T value, final Seed seed) {
        return seed == null ? ok(value) : fail(seed);
    }

    public static <T> Result<T> compute(final T value, final String code) {
        return code == null ? ok(value) : fail(code);
    }

    @SafeVarargs
    public static <T> Result<T> compute(final Supplier<T> valueSupplier, final Supplier<Seed>... seedSuppliers){
        for (Supplier<Seed> supplier : seedSuppliers) {
            Seed seed = supplier.get();
            if (seed != null) {
                return fail(seed);
            }
        }
        return ok(valueSupplier.get());
    }

    @SafeVarargs
    public static <T> Result<T> computeFromCodes(final Supplier<T> valueSupplier, final Supplier<String>... codeSuppliers){
        for (Supplier<String> supplier : codeSuppliers) {
            String code = supplier.get();
            if (code != null) {
                return fail(code);
            }
        }
        return ok(valueSupplier.get());
    }

    @Override
    public ResultBuilder<T> success(final boolean ok) {
        this.ok = ok;
        return this;
    }

    @Override
    public ResultBuilder<T> value(final T value) {
        this.value = value;
        return this;
    }

    @Override
    public ResultBuilder<T> seed(final Seed seed) {
        this.seed = seed;
        return this;
    }

    @Override
    public ResultBuilder<T> causes(final Result<?>... causes) {
        this.causes.addAll(Arrays.asList(causes));
        return this;
    }

    @Override
    public SeedBuilder<T> seedBuilder() {
        return createSeedBuilder();
    }

    @Override
    public Result<T> build() {
        return new DefaultResult<T>(ok, value, seed, causes);
    }

    private SeedBuilder<T> createSeedBuilder(){
        return new DefaultSeedBuilder<>(this);
    }
}
