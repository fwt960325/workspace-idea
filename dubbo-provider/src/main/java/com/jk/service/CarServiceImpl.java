package com.jk.service;

import com.jk.mapper.CarMapper;
import com.jk.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service("CarService")
public class CarServiceImpl implements CarService{

    @Autowired
    private CarMapper carMapper;

    @Override
    public List<Car> queryCarList() {
        return carMapper.queryCarList();
    }

    @Override
    public void addUser(Car car) {
        Integer id = car.getCarId();
        if(id != null){
            carMapper.updaye(car);
        }else{
            carMapper.addUser(car);
        }

    }

    @Override
    public void delall(Integer id) {
        carMapper.delall(id);
    }

    @Override
    public Car openUpdate(Integer id) {

        return carMapper.openUpdate(id);
    }


}

