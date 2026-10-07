package ru.ksenia.NauJava.crud;

import java.util.List;

public interface Crud<T, ID> {
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
}
