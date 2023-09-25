package ru.multa.entia.results.impl.result;

import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.result.ResultBuilder;
import ru.multa.entia.results.api.seed.Seed;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DefaultResultBuilder<T> implements ResultBuilder<T> {
    @Override
    public ResultBuilder<T> success(boolean ok) {
        // TODO: 25.09.2023 !!!
        return null;
    }

    @Override
    public ResultBuilder<T> value(T value) {
        // TODO: 25.09.2023 !!!
        return null;
    }

    @Override
    public ResultBuilder<T> seed(Seed seed) {
        // TODO: 25.09.2023 !!!
        return null;
    }

    @Override
    public Result<T> build() {
        // TODO: 25.09.2023 !!!
        return null;
    }

    // TODO: 25.09.2023 !!!
//    final private Deque<Object> args = new ArrayDeque<>();
//
//    private boolean ok;
//    private T value;
//    private String code;
//
//    public static <T> Result<T> ok(T value){
//        // TODO: 24.09.2023 !!!
//        throw new RuntimeException("");
//    }
//
//    public static  <T> Result<T> fail(String code, Object... args){
//        // TODO: 24.09.2023 !!!
//        throw new RuntimeException("");
//    }
//
//    @Override
//    public ResultBuilder<T> success(boolean ok) {
//        this.ok = ok;
//        return this;
//    }
//
//    @Override
//    public ResultBuilder<T> value(T value) {
//        this.value = value;
//        return this;
//    }
//
//    @Override
//    public ResultBuilder<T> code(String code) {
//        this.code = code;
//        return this;
//    }
//
//    @Override
//    public ResultBuilder<T> args(Object... args) {
//        if (args == null){
//            return this;
//        }
//        this.args.addAll(Arrays.asList(args));
//        return this;
//    }
//
//    @Override
//    public ResultBuilder<T> args(boolean direct, Object... args) {
//        if (args == null){
//            return this;
//        }
//
//        if (direct){
//            this.args.addAll(Arrays.asList(args));
//        } else {
//            for (Object arg : args) {
//                this.args.addFirst(arg);
//            }
//        }
//
//        return this;
//    }
//
//    @Override
//    public Result<T> build() {
//        // TODO: 24.09.2023 !!!
//        return null;
//    }
}
