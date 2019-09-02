package com.mye.demo.controller;

import com.mye.demo.model.Application;
import com.mye.demo.service.impl.ApplicationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="用车审批",tags = {"用车审批API"},description = "描述信息")
@RestController
public class ApplicationController {
    @Autowired
    ApplicationServiceImpl applicationServiceImpl;

    @ApiOperation(value = "获取表单列表", notes = "获取所有的表单信息", produces = "application/json")
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public String getAllApplications() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        //获得到所有的申请表单
        List<Application> applicationList = applicationServiceImpl.getAllApplications();

        if(applicationList.size() == 0)
            jsonData.put("judge", "-1");
        else
            jsonData.put("judge", "1");
        jsonData.put("applicationList", applicationList);
        jsonObject.put("data", jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "查找申请表单", notes = "输入id来查找对应的表单信息", produces = "application/json")
    @RequestMapping(value = "searchApplication", method = RequestMethod.POST)
    public String selectApplicationById(@RequestParam int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        Application application = applicationServiceImpl.selectApplicationById(id);
//-1表示不存在
        if (application == null)
            jsonData.put("judge", "-1");
        else
            jsonData.put("judge", "1");
        jsonData.put("application", application);
        jsonObject.put("data", jsonData);

        return jsonObject.toString();
    }

    @ApiOperation(value = "审核表单操作", notes = "将id为${id}的表单的status字段设置为${status}", produces = "application/json")
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkApplication(int id, int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Application application = applicationServiceImpl.selectApplicationById(id);

        if (application == null)
            jsonData.put("judge", "-1");
        else {
            applicationServiceImpl.updateApplication(id, status);
//0表示更新成功。
            jsonData.put("judge", "0");
        }

        jsonObject.put("data", jsonData);
        return jsonObject.toString();
    }
}
