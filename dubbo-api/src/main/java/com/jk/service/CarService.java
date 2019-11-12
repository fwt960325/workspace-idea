package com.jk.service;

import com.jk.model.Car;

import java.util.HashMap;
import java.util.List;

public interface CarService {

     List<Car> queryCarList();

     void addUser(Car car);

    void delall(Integer id);

    Car openUpdate(Integer id);

    List<Car> queryTwo(String[] id) throws Exception;

    void addTwo(List<Car> list);
}
