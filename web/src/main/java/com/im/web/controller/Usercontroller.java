package com.im.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.web.annotation.ControllerLogger;
import com.im.api.apiservice.user.IUserService;
import com.im.api.dto.article.BaseResponse;
import com.im.api.dto.user.User;
import com.im.web.util.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.im.api.util.UUID.UU64;


@Api(value = "用户与系统交互")
@Controller
@RequestMapping(value = "/a")
public class Usercontroller {

    @Reference
    IUserService userService;
    @Autowired
    UserSession userSession;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public BaseResponse login(@RequestParam String username,@RequestParam String password) throws Exception {
        User login = userService.login(username, password);
        return BaseResponse.ok(login);
    }
}
