package ru.multa.entia.results.impl.result;

import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.result.ResultBuilder;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.api.seed.SeedBuilder;
import ru.multa.entia.results.impl.seed.DefaultSeedBuilder;

public class DefaultResultBuilder<T> implements ResultBuilder<T> {
    private boolean ok;
    private T value;
    private Seed seed;

    public static <T> Result<T> ok(T value){
        return new DefaultResultBuilder<T>().success(true).value(value).build();
    }

    public static  <T> Result<T> fail(String code, Object... args){
        return new DefaultResultBuilder<T>()
                .success(false)
                .seedBuilder()
                .code(code)
                .addLastArgs(args)
                .apply()
                .build();
    }

    @Override
    public ResultBuilder<T> success(boolean ok) {
        this.ok = ok;
        return this;
    }

    @Override
    public ResultBuilder<T> value(T value) {
        this.value = value;
        return this;
    }

    @Override
    public ResultBuilder<T> seed(Seed seed) {
        this.seed = seed;
        return this;
    }

    @Override
    public SeedBuilder<T> seedBuilder() {
        return createSeedBuilder();
    }

    @Override
    public Result<T> build() {
        return new DefaultResult<T>(ok, value, seed);
    }

    private SeedBuilder<T> createSeedBuilder(){
        return new DefaultSeedBuilder<>(this);
    }
}
