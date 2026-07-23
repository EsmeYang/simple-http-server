package com.esme.Handler;

import java.util.List;

public interface Store<T, ID> {
    T save(T entity);
    T findById(ID id);
    List<T> findAll();
    boolean deleteById(ID id);
}
