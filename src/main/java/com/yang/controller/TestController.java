package com.yang.controller;

import com.yang.dao.UserMapper;
import com.yang.pojo.Goods;
import com.yang.pojo.User;
import com.yang.service.UserService;
import com.yang.util.ExportCSVUtil;
import com.yang.util.InportCSVUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:Yang
 */
@Controller
public class TestController {

    @Autowired
    UserService userService;
    @RequestMapping("/test")
    public String test(){
        User user = new User();
        user.setAddress("address");
        user.setEmail("email");
        user.setPassword("123456");
        user.setRealname("yang");
        user.setSex("nv");
        user.setPhone("phone");
        userService.add(user);
        return "test";
    }
    @RequestMapping("/json")
    @ResponseBody
    public Map<String,Object> json(){
        Map<String,Object> map = new HashMap<>();
        map.put("yang","杨");
        return map;
    }

/**
 * 上传文件
 * **/
    @RequestMapping("/upload")
    public String fileUpload(@RequestParam("file") CommonsMultipartFile file , HttpServletRequest request) throws IOException {

        //获取文件名 : file.getOriginalFilename();
        String uploadFileName = file.getOriginalFilename();

        //如果文件名为空，直接回到首页！
        if ("".equals(uploadFileName)){
            return "redirect:test";
        }
        System.out.println("上传文件名 : "+uploadFileName);

        //上传路径保存设置
        String path = request.getServletContext().getRealPath("/upload");
        //如果路径不存在，创建一个
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        System.out.println("上传文件保存地址："+realPath);

        InputStream is = file.getInputStream(); //文件输入流
        OutputStream os = new FileOutputStream(new File(realPath,uploadFileName)); //文件输出流

        //读取写出
        int len=0;
        byte[] buffer = new byte[1024];
        while ((len=is.read(buffer))!=-1){
            os.write(buffer,0,len);
            os.flush();
        }
        os.close();
        is.close();
        return "redirect:test";
    }
    /**
     * 下载文件
     * **/
    @RequestMapping(value="/download")
    public String downloads(HttpServletResponse response , HttpServletRequest request) throws Exception{
        //要下载的图片地址
        String  path = request.getServletContext().getRealPath("/upload");
        String  fileName = "1.jpg";

        //1、设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("multipart/form-data"); //二进制传输数据
        //设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));

        File file = new File(path,fileName);
        //2、 读取文件--输入流
        InputStream input=new FileInputStream(file);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();

        byte[] buff =new byte[1024];
        int index=0;
        //4、执行 写出操作
        while((index= input.read(buff))!= -1){
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
        return null;
    }
    /**
     * 下载csv文件
     * **/
    @RequestMapping("/csv")
    public void downcsv(HttpServletResponse httpServletResponse){
        List<String> list = new ArrayList<>();
        Goods goods = new Goods();
        goods.setGid(1);
        goods.setGname("yang");
        goods.setGnum(10);
        goods.setGprice(new BigDecimal(100));
        String[] headers={"编号","姓名","数量","价格"};
        list.add(goods.toString());
        byte[] bytes = ExportCSVUtil.writeDataAfterToBytes(headers, list);

        ExportCSVUtil.responseSetProperties("test.csv",bytes,httpServletResponse);
    }
    /**
     * 上传csv文件
     * **/
    @RequestMapping("/upcsv")
    public void upcsv(@RequestParam(value = "file") MultipartFile file){
        String sourceName = file.getOriginalFilename(); // 原始文件名
        String fileType = sourceName.substring(sourceName.lastIndexOf("."));
        if (file.isEmpty() || StringUtils.isBlank(fileType)) {
            System.out.println("文件不能为空");
        }
        if (!".txt".equals(fileType.toLowerCase()) && !".csv".equals(fileType.toLowerCase())) {
            System.out.println("文件暂时只支持txt,csv格式");
        }
        List<String[]> csvDataToStringList = InportCSVUtil.getCsvDataToStringList(file);
        List<Goods> goodsList = new ArrayList<>();
        for (String[] strings : csvDataToStringList) {
            Goods goods = new Goods();
            goods.setGid(Integer.parseInt(strings[0]));
            goods.setGname(strings[1]);
            goods.setGnum(Integer.parseInt(strings[2]));
            goods.setGprice(new BigDecimal(strings[3]));
            goodsList.add(goods);
        }
        System.out.println(goodsList);
    }


}
