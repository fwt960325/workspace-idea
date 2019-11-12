package com.jk.controller;

import com.jk.model.Car;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("solr")
public class SolrController {


    @Autowired
    private SolrClient client;


    /**
     * 新增/修改 索引
     * 当 id 存在的时候, 此方法是修改(当然, 我这里用的 uuid, 不会存在的), 如果 id 不存在, 则是新增
     * @return
     */
    @RequestMapping("add")
    public String add() {

        Car car=new Car();
        car.setCarName("比亚迪");
        car.setCarPrice(5000.05);
        car.setCarTime(new Date());


        //执行数据库新增
        //carService.addCar(car);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", 2);
            doc.setField("carName", car.getCarName());
            doc.setField("carPrice", car.getCarPrice());
            /* 如果spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数
             * 下面都是一样的
             */

            client.add("core1", doc);
            //client.commit();
            client.commit("core1");
            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }
}
