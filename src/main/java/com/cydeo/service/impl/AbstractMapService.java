package com.cydeo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService<T,ID> {

    public Map<ID,T> map = new HashMap<>(); //DataBase

    public T save(ID id,T object){
        map.put(id,object);
        return object;
    }

    public T findById(ID id){
        return map.get(id);
    }

    public List<T> findAll(){
        return new ArrayList<>(map.values());
    }

    public void deleteById(ID id){
        map.remove(id);
    }

}
