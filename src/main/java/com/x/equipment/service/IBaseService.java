package com.x.equipment.service;

public interface IBaseService<T> {

    String generateName();

    T loadObjectByName(String name);
}
