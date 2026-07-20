package com.esme.Handler;

import java.util.List;

public interface Store<T> {
    T save(T entity);
    T findById(int id);
    List<T> findAll();
    boolean deleteById(int id);
}
