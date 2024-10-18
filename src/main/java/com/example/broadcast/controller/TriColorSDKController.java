package com.example.broadcast.controller;

import com.example.broadcast.util.TriColorSdkUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/sdk")
public class TriColorSDKController {

    @RequestMapping("/getVersion")
    public Object indexPage() {
        return TriColorSdkUtil.INSTANCE.GetVersion();
    }
}
