package com.wang.out.user.controller;




import com.qcloud.PicCloud;
import com.qcloud.PicInfo;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Sign;
import com.qcloud.sign.FileCloudSign;

import com.wang.out.common.Qcloud;
import com.wang.out.common.ResultPojo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;



@Controller
@RequestMapping(value = "/bucket")
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @ResponseBody
    public ResultPojo upload(HttpServletRequest inRequest) {
        ResultPojo result = new ResultPojo();
        try {
            long expired = System.currentTimeMillis() / 1000 + 6000;
            String signStr = Sign.getPeriodEffectiveSign("muyoucun", "/123.jpg", Qcloud.getCred(), expired);

//            String signStr = Sign.getOneEffectiveSign("muyoucun", "/pic/test.jpg", Qcloud.getCred());

//            int max = 100000000;
//            int min = 999999999;
//            Random random = new Random();
//            int s = (random.nextInt(max) % (max - min + 1) + min);
//            System.out.println(s);
//
//            String appid = "1251972445";
//            String bucket = "muyoucun";
//            String secret_id = "AKIDZJtKepgdGhwzzsF5uqfJn8GDza2H9DtO";
//            String secret_key = "bozza5Xm1GMGyUnupY5QZVEkaKsDMXdY";
//            Long expired = System.currentTimeMillis() / 1000 + 1000;
//            System.out.println(expired);
//            Integer onceExpired = 0;
//            Long current = System.currentTimeMillis() / 1000;
//            System.out.println(current);
//            String rdm = Integer.toString(s);
//            String userid = "0";
//            String fileid = "wsl";
//
//
//
//            String srcStr = "a=" + appid + "&b=" + bucket + "&k=" + secret_id + "&e=" + expired + "&t=" + current + "&r=" + rdm + "&u=0" + "&f=";
//
//
//
//            String srcWithFile = "a=" + appid + "&b=" + bucket + "&k=" + secret_id + "&e=" + expired + "&t=" + current + "&r=" + rdm + "&u="
//                    + userid + "&f=" + fileid;
//
//            String srcStrOnce = "a=" + appid + "&b=" + bucket + "&k=" + secret_id + "&e=" + onceExpired + "&t=" + current + "&r=" + rdm
//                    + "&u=" + userid + "&f=" + fileid;
//
//
//            String signStr = Base64.getEncoder().encodeToString((Hmacsha1.HmacSHA1Encrypt(srcStr, secret_key) + srcStr).getBytes("utf-8"));
//
//            System.out.println(signStr);


//            UploadFileRequest uploadFileRequest = new UploadFileRequest("muyoucun","/123.jpg", "/Users/apple/Desktop/123.jpg");
//            String uploadFileRet = Qcloud.getCosClient().uploadFile(uploadFileRequest);
//
//            System.out.println(uploadFileRet);

//            Long expired = System.currentTimeMillis() / 1000 + 1000;

//            String signStr = FileCloudSign.appSignV2(1251972445,"AKIDZJtKepgdGhwzzsF5uqfJn8GDza2H9DtO","bozza5Xm1GMGyUnupY5QZVEkaKsDMXdY","muyoucun",expired);


            PicCloud pc = new PicCloud(1251972445, "AKIDZJtKepgdGhwzzsF5uqfJn8GDza2H9DtO", "bozza5Xm1GMGyUnupY5QZVEkaKsDMXdY");
            PicInfo ret = pc.stat("muyoucun/123.jpg");
            System.out.println(ret);

            Map<String, String> resultMap = new HashMap();
            resultMap.put("sig", signStr);
            result.setData(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultPojo(500, "服务器错误");
        }

        return result;
    }

}
