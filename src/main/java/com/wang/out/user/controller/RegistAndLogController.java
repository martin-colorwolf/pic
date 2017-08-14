package com.wang.out.user.controller;

import com.wang.out.common.ValidateCodeUtils;
import com.wang.out.common.ResultPojo;

import com.wang.out.user.service.RegistAndLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wang.out.common.Token;

/**
 * 登录、注册、修改、删除
 *
 * @author 王世林
 */
@Controller
@RequestMapping(value = "/hello")
public class RegistAndLogController {

    @Resource
    private RegistAndLogService registAndLogService;
    private ValidateCodeUtils validateCodeUtils;

    /*
    * 登录home界面
    * */

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public  String homepage() {
        return "home";
    }

    /*
    * 用户登录
    * */
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    @ResponseBody
    public ResultPojo log(HttpServletRequest inRequest) {
        ResultPojo result = new ResultPojo();
        try {
            Map<String, String[]> map = new HashMap(inRequest.getParameterMap());
            //            判断用户名或密码空白
            if (StringUtils.isBlank(map.get("username")[0]) || StringUtils.isBlank(map.get("password")[0])) {
                return new ResultPojo(103, "用户名或密码空白");
            }
            ResultPojo resultPojo = registAndLogService.checklogService(map.get("username")[0], map.get("password")[0]);

            if (resultPojo.getStatus() != 200) {
                return resultPojo;
            } else {
                String token = registAndLogService.createTokenService(map.get("username")[0]);
                Map<String, String> resultMap = new HashMap();
                resultMap.put("token", token);
                resultPojo.setData(resultMap);
                return resultPojo;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultPojo(500, "服务器错误");
        }

    }

    /*
     * 用户注册
     * */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public ResultPojo regist(HttpServletRequest inRequest) {
        ResultPojo result = new ResultPojo();
        try {
            Map<String, String[]> map = new HashMap(inRequest.getParameterMap());
//            判断用户名或密码空白
            if (StringUtils.isBlank(map.get("username")[0])) {
                return new ResultPojo(103, "用户名或密码空白");
            }
//            判断是否是邮箱格式 和 密码是否合法
            if (!registAndLogService.checkEmail(map.get("username")[0])) {
                return new ResultPojo(104, "用户名Email地址不符合规范");
            }
//            判断是否用户名存在
            if (registAndLogService.usernameIsExistService(map.get("username")[0])) {
                return new ResultPojo(105, "用户名已经存在");
            }
//            注册用户
            registAndLogService.userRegistService((Map<String, String[]>) map);


            Map<String, String> resultMap = new HashMap();
            resultMap.put("info", "注册成功");
            result.setData(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultPojo(500, "服务器错误");
        }

        return result;
    }

    /*
    * 获取验证码
    * */

    @RequestMapping(value = "/getValidateCode", method = RequestMethod.GET)
    public void ValidateCode(HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            ValidateCodeUtils vCode = new ValidateCodeUtils(120, 40, 4, 10);
            request.getSession().setAttribute("picCode", vCode.getCode());
            vCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    * 校验图像验证码
    * */

    @RequestMapping(value = "/checkValidateCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultPojo checkValidateCode(HttpServletRequest inRequest, HttpSession httpSession) {
        ResultPojo resultPojo = new ResultPojo();
        try {
            Map<String, String[]> params = new HashMap(inRequest.getParameterMap());
            String code = (String) httpSession.getAttribute("picCode");
            // 校验入参
            if (code.equals(params.get("code")[0])) {
                return new ResultPojo(200, "验证成功");
            }
            return new ResultPojo(108, "验证码错误");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultPojo(500, "服务器错误");
        }

    }

    /*
    * 用户激活账户
    * */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(HttpServletRequest inRequest) {
        ResultPojo result = new ResultPojo();
        try {

            Map<String, String[]> map = new HashMap(inRequest.getParameterMap());
            if (StringUtils.isBlank(map.get("checkid")[0])) {
                return "activationFalse";
            }
            if (!registAndLogService.checkedService(map.get("checkid")[0])) {
                return "activationFalse";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "activationFalse";
        }

        return "activationSuccess";
    }


    /*
    * 用户成功注册页面
    * */
    @RequestMapping(value = "/registsuccess", method = RequestMethod.GET)
    public String success() {
        return "registSuccess";
    }


    /*
    * 用户注册页面
    * */
    @RequestMapping(value = "/registpage", method = RequestMethod.GET)
    public String registpage() {
        return "regist";
    }
}
