package com.jk.mapper;


import com.jk.model.Car;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CarMapper {

    @Select("select * from  t_car")
    List<Car> queryCarList();

    void addUser(Car car);
    @Delete("delete from t_car where carId = #{id}")
    void delall(Integer id);
    @Select("select * from t_car where carId = #{id}")
    Car openUpdate(Integer id);
    @Update("update t_car set carName = #{carName},carPrice = #{carPrice},carTime = #{carTime} where carId = #{carId}")
    void updaye(Car car);
}
