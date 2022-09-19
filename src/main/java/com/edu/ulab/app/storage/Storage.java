package com.edu.ulab.app.storage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
//TODO:1) Какая структура лучше?

public  interface Storage<K,V>{
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции

    public V save(V entity);
    public Optional<V> findById(K primaryKey);
    public void delete(K primaryKey);
    public V update(V entity);
    public Iterable<V> getAllEntity();
}
