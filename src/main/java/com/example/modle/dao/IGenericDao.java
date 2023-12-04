package com.example.modle.dao;

import java.util.List;

public interface IGenericDao <T,ID> {
    List<T> findAll();
    Boolean create(T t);
    Boolean update(T t,ID id);
    T findById(ID id);
    void delete(ID id);
}
