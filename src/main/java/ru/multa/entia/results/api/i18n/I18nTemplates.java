package ru.multa.entia.results.api.i18n;

import java.util.Locale;
import java.util.Optional;

public interface I18nTemplates {
    Optional<String> get(String code, Locale locale);
}
