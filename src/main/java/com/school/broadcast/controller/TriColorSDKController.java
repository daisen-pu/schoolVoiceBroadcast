package com.school.broadcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.broadcast.util.TriColorSdkUtil;

@Controller
@ResponseBody
@RequestMapping("/sdk")
public class TriColorSDKController {

    @RequestMapping("/getVersion")
    public Object indexPage() {
        return TriColorSdkUtil.INSTANCE.GetVersion();
    }
}
