
package com.jk.controller;

import com.jk.model.Car;

import com.jk.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class CarController {

    @Autowired
    private CarService carService;
    @RequestMapping("show")
    public String toshow(){
        return "show";
    }

    @RequestMapping("queryCarList")
    @ResponseBody
    public List<Car> queryCarList(){

        List<Car> list= carService.queryCarList();

        return list;
    }
    @RequestMapping("addUser")
    @ResponseBody
    public void addUser(Car car){
        carService.addUser(car);
    }

    @RequestMapping("delall")
    @ResponseBody
    public void delall(Integer id){
        carService.delall(id);
    }
    @RequestMapping("openUpdate")
    @ResponseBody
    public Car openUpdate(Integer id){

        return carService.openUpdate(id);
    }
}


