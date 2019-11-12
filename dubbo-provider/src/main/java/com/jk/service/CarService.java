package com.jk.service;

import com.jk.model.Car;

import java.util.List;

public interface CarService {

    List<Car> queryCarList();

    void addUser(Car car);

    void delall(Integer id);

    Car openUpdate(Integer id);

}
