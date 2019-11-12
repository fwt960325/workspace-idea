
package com.jk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.model.Car;
import com.jk.service.CarService;
import com.jk.util.ExportExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CarController {

    @Autowired
    private SolrClient client;


    @Reference
    private CarService carService;
    @RequestMapping("show")
    public String toshow(){
        return "show";
    }

    @RequestMapping("hahah")
    public String toHAHAHA(){
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

        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id",car.getCarId() );
            doc.setField("carName", car.getCarName());
            doc.setField("carPrice", car.getCarPrice());
            /* 如果spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数
             * 下面都是一样的
             */

            client.add("core1", doc);
            //client.commit();
            client.commit("core1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("delall")
    @ResponseBody
    public void delall(Integer id){
        carService.delall(id);

        try {
            client.deleteById("core1",id);
            client.commit("core1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("openUpdate")
    @ResponseBody
    public Car openUpdate(Integer id){

        return carService.openUpdate(id);
    }

    @RequestMapping("Export")
    public void Export(HttpServletResponse response, String[] id){

        if(id.length<=0){

            List<Car> list= new ArrayList<Car>();
            try {
                list = carService.queryCarList();

                String title ="汽车列表";

                String[] rowName={"编号","车辆名称","价格","日期"};

                List<Object[]>  dataList = new ArrayList<Object[]>();

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

                for (Car car:list) {
                    Object[] obj=new Object[rowName.length];
                    obj[0]=car.getCarId();
                    obj[1]=car.getCarName();
                    obj[2]= car.getCarPrice();
                    obj[3]=sdf.format(car.getCarTime());

                    dataList.add(obj);
                }
                ExportExcel exportExcel=new ExportExcel(title,rowName,dataList,response);
                exportExcel.export();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else{

            List<Car> list= new ArrayList<Car>();

            try {
                list = carService.queryTwo(id);


                String title ="汽车列表";

                String[] rowName={"编号","车辆名称","价格","日期"};

                List<Object[]>  dataList = new ArrayList<Object[]>();

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

                for (Car car:list) {
                    Object[] obj=new Object[rowName.length];
                    obj[0]=car.getCarId();
                    obj[1]=car.getCarName();
                    obj[2]=car.getCarPrice();
                    obj[3]=sdf.format(car.getCarTime());

                    dataList.add(obj);
                }
                ExportExcel exportExcel=new ExportExcel(title,rowName,dataList,response);
                exportExcel.export();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping("import")
    public String Import(MultipartFile file, HttpServletResponse response){

        //上传文件的名称
        String fileName = file.getOriginalFilename();

        System.out.println(fileName);

        //生成新的文件名称
        String filePath = "../src/main/resources/templates/fileupload/";

        //创建list集合接收传递的参数
        List<Car> list= new ArrayList<Car>();

        //上传文件
        try {
            uploadFile(file.getBytes(), filePath, fileName);

            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            //通过文件创建流
            FileInputStream fileInputStream = new FileInputStream(filePath+fileName);
            //创建操作excel的对象   因为xls的文件需要HSSFWorkbook  xlsx需要的XSSFWorkbook 因此直接使用workBook对象就行了
            Workbook workbook= WorkbookFactory.create(fileInputStream) ;
            //通过workbook获得sheet页  sheet有可能有多个
            for(int i=0;i<workbook.getNumberOfSheets();i++){
                //创建sheet对象
                Sheet sheetAt = workbook.getSheetAt(i);
                //根绝sheet创建行  row
                for(int j=3;j<sheetAt.getPhysicalNumberOfRows();j++){
                    //创建row对象
                    Row row = sheetAt.getRow(j);

                    //创建对象接收从文件读取的内容
                    Car car = new Car();
                    if( !"".equals(row.getCell(1)) && row.getCell(1)!=null){
                        car.setCarName(row.getCell(1).toString());
                    }
                    if( !"".equals(row.getCell(2)) && row.getCell(2)!=null){
                        car.setCarPrice(Double.valueOf(row.getCell(2).toString()));
                    }

                    if( !"".equals(row.getCell(3)) && row.getCell(3)!=null){
                        car.setCarTime(sdf.parse(String.valueOf(row.getCell(3))).toString());
                    }

                    list.add(car);
                }

            }
            carService.addTwo(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "show";

    }








    //上传文件的方法
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }


}


