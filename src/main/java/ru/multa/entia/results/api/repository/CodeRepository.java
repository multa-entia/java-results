package ru.multa.entia.results.api.repository;


public interface CodeRepository {
    boolean update(Object key, String code);
    String get(Object key);
}
