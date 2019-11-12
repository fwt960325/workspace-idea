package com.jk.service;

import java.util.List;
import java.util.Map;

public interface CarService {
    List<Map<String, Object>> queryCar();

    List<Map<String, Object>> queryCarAll();

    List<Map<String, Object>> queryZhu();

    List<Map<String, Object>> querymianji();

}
