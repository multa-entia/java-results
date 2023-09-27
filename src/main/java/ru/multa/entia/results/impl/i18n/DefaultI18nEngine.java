package ru.multa.entia.results.impl.i18n;

import lombok.RequiredArgsConstructor;
import ru.multa.entia.results.api.i18n.I18nEngine;
import ru.multa.entia.results.api.i18n.I18nTemplates;
import ru.multa.entia.results.api.seed.Seed;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DefaultI18nEngine implements I18nEngine {
    private final static Pattern PATTERN = Pattern.compile("<@\\d+>");

    private final I18nTemplates templates;

    @Override
    public Optional<String> execute(final Seed seed, final Locale locale) {
        Optional<String> maybeTemplate = templates.get(seed.code(), locale);
        if (maybeTemplate.isEmpty()){
            return Optional.empty();
        }

        String template = maybeTemplate.get();
        Map<String, Integer> argIdsByKey = getArgIdByKey(template);
        return Optional.of(replace(template, argIdsByKey, seed.args()));
    }

    private Map<String, Integer> getArgIdByKey(final String template) {
        Map<String, Integer> ids = new HashMap<>();
        Matcher matcher = PATTERN.matcher(template);
        while (matcher.find()){
            String key = template.substring(matcher.start(), matcher.end());
            if (!ids.containsKey(key)){
                Optional<Integer> maybeId = getArgId(key);
                maybeId.ifPresent((id) -> {ids.put(key, id);});
            }
        }

        return ids;
    }

    private Optional<Integer> getArgId(final String key) {
        try {
            return Optional.of(Integer.parseInt(key.substring(2, key.length()-1)));
        } catch (Exception ex){
            return Optional.empty();
        }
    }

    private String replace(final String template, final Map<String, Integer> argIdByKey, final Object[] args) {
        String result = template;
        for (Map.Entry<String, Integer> entry : argIdByKey.entrySet()) {
            Integer id = entry.getValue();
            if (id < args.length){
                result = result.replace(entry.getKey(), String.valueOf(args[id]));
            }
        }

        return result;
    }
}
