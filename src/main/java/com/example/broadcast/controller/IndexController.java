package com.example.broadcast.controller;

import com.example.broadcast.mudule.ClassModule;
import com.example.broadcast.mudule.SampleMudule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    @RequestMapping("/")
    public ModelAndView hi() {
        SampleMudule sampleMudule = new SampleMudule();
        sampleMudule.setAge(18);
        sampleMudule.setName("daisen");
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("sample",sampleMudule);
        return mav;
    }

    @RequestMapping("/secure_page")
    public ModelAndView securePage(){
        ModelAndView mav = new ModelAndView("secure_page");
        return mav;
    }

    @RequestMapping("/checkType")
    public ModelAndView checkType(@RequestParam("type") String type){
        if (StringUtils.isEmpty(type)){
            return null;
        }
        if ("large_screen".equals(type)) {
            ModelAndView mav = new ModelAndView("redirect:large_screen");
            return mav;
        }else{
            ModelAndView mav = new ModelAndView("redirect:singl_line_output");
            return mav;
        }
    }

    @RequestMapping("/large_screen")
    public ModelAndView largeScreen(){
        Map<String, ClassModule> maps = new HashMap<>();
        maps.put("一年级(1)班", new ClassModule("一年级(1)班", 0));
        maps.put("一年级(2)班", new ClassModule("一年级(2)班", 1));
        maps.put("一年级(3)班", new ClassModule("一年级(3)班", 0));
        maps.put("一年级(4)班", new ClassModule("一年级(4)班", 1));
        maps.put("一年级(5)班", new ClassModule("一年级(5)班", 0));
        maps.put("二年级(1)班", new ClassModule("二年级(1)班", 1));
        maps.put("二年级(2)班", new ClassModule("二年级(2)班", 0));
        maps.put("二年级(3)班", new ClassModule("二年级(3)班", 1));
        maps.put("二年级(4)班", new ClassModule("二年级(4)班", 0));
        maps.put("二年级(5)班", new ClassModule("二年级(5)班", 1));
        maps.put("三年级(1)班", new ClassModule("三年级(1)班", 0));
        maps.put("三年级(2)班", new ClassModule("三年级(2)班", 1));
        maps.put("三年级(3)班", new ClassModule("三年级(3)班", 0));
        maps.put("三年级(4)班", new ClassModule("三年级(4)班", 1));
        maps.put("三年级(5)班", new ClassModule("三年级(5)班", 0));

        ModelAndView mav = new ModelAndView("large_screen");
        mav.addObject("maps", maps);
        return mav;
    }

    @RequestMapping("/singl_line_output")
    public ModelAndView singlLineOutput(){
        ModelAndView mav = new ModelAndView("singl_line_output");
        return mav;
    }
}
