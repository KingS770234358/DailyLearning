package com.wq.nginxlearning.controller;

import com.wq.nginxlearning.config.PortConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

// @Controller
@Controller
public class HelloController {

    @Autowired
    private PortConfig portConfig;

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld(){
        String serverIP = "";
        try {
            serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String res = "服务器：" + serverIP + ":" + portConfig.getServerPort() + "受理该服务";
        System.out.println(res);
        return res;
    }

    @RequestMapping(("/getRes"))
    public String getRes(){
        return "helloworld";
    }
}
