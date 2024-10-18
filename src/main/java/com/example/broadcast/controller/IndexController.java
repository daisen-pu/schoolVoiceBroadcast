package com.example.broadcast.controller;

import com.example.broadcast.mudule.SampleMudule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

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
        ModelAndView mav = new ModelAndView("large_screen");
        return mav;
    }

    @RequestMapping("/singl_line_output")
    public ModelAndView singlLineOutput(){
        ModelAndView mav = new ModelAndView("singl_line_output");
        return mav;
    }
}
