package com.app.calendar.service;

import java.util.List;

public interface CalendarService<T> {
    T create(T t);

    List<T> getAll();

    T getByName(String name);

    T getById(Long Id);

    void delete(Long id);

    T update (Long id,T t);
}
