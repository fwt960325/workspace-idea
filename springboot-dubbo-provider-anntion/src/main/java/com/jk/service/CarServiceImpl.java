package com.jk.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jk.mapper.CarMapper;
import com.jk.model.Car;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
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


    @Override
    public List<Car> queryTwo(String[] id) throws Exception{
        return carMapper.queryTwo(id);
    }

    @Override
    public void addTwo(List<Car> list) {
        carMapper.addTwo(list);
    }


}

