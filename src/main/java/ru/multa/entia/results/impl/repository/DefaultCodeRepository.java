package ru.multa.entia.results.impl.repository;

import ru.multa.entia.results.api.repository.CodeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DefaultCodeRepository implements CodeRepository {
    private static CodeRepository instance;

    private final Params params;
    private final Function<Object, String> absenceCodeGenerator;
    private final Map<Object, String> codes;

    public static CodeRepository newInstance(final Params params,
                                             final Function<Object, String> absenceCodeGenerator,
                                             final Map<Object, String> codes){
        return new DefaultCodeRepository(
                params != null ? params : new Params(),
                absenceCodeGenerator != null ? absenceCodeGenerator : new AbsenceCodeGenerator(),
                codes != null ? codes : new HashMap<Object, String>()
        );
    }

    public synchronized static CodeRepository getDefaultInstance(){
        if (instance == null) {
            instance = newInstance(null, null, null);
        }
        return instance;
    }

    private DefaultCodeRepository(final Params params,
                                  final Function<Object, String> absenceCodeGenerator,
                                  final Map<Object, String> codes) {
        this.params = params;
        this.absenceCodeGenerator = absenceCodeGenerator;
        this.codes = codes;
    }

    @Override
    public synchronized boolean update(final Object key, final String code) {
        boolean mayPut = !params.closed() && (params.updatable() || !codes.containsKey(key));
        if (mayPut) {
            codes.put(key, code);
        }

        return mayPut;
    }

    @Override
    public synchronized String get(final Object key) {
        return codes.getOrDefault(key, absenceCodeGenerator.apply(key));

    }

    public static class AbsenceCodeGenerator implements Function<Object, String> {
        private final static String CODE_TEMPLATE = "absence-code-%s";
        @Override
        public String apply(final Object key) {
            return String.format(CODE_TEMPLATE, key == null ? "null" : String.valueOf(key));
        }
    }

    public record Params(boolean closed, boolean updatable) {
        public Params() {
            this(false, false);
        }
    }
}
