package com.web;

import com.web.dao.UserDaoMapper;
import com.web.dbuitl.UserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.web"})
@RestController
@MapperScan("com.web.dao")
public class App 
{
    @Autowired
    private UserDaoMapper userDaoMapper;
    @RequestMapping("/")
    public String home(){
        UserDao userDao = userDaoMapper.selectByPrimaryKey(1);
        if(userDao==null){
            return "用户不存在";
        }
        else{
            return userDao.getName();
        }
    }
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
