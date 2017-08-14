<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- 先引入 Vue -->
<script src="http://cdn.bootcss.com/vue/2.4.2/vue.js"></script>
<!-- 引入组件库 -->
<script src="http://cdn.bootcss.com/element-ui/1.4.1/index.js"></script>

<script
        src="http://code.jquery.com/jquery-3.2.1.js"
        integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
        crossorigin="anonymous"></script>

<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.min.js"></script>

<script src="http://localhost:8089/js/common.js"></script>

<script type="text/javascript" src="/js/cos-js-sdk-v4.js"></script>

<script>
    //初始化逻辑
    //特别注意: JS-SDK使用之前请先到console.qcloud.com/cos 对相应的Bucket进行跨域设置
    var cos = new CosCloud({
        appid: 1251972445,// APPID 必填参数
        bucket: "muyoucun",//bucketName 必填参数
        region: 'sh',//地域信息 必填参数 华南地区填gz 华东填sh 华北填tj
        getAppSign:  (callback) => {//获取签名 必填参数

            //下面简单讲一下获取签名的几种办法

            //1.搭建一个鉴权服务器，自己构造请求参数获取签名，推荐实际线上业务使用，优点是安全性好，不会暴露自己的私钥
            //拿到签名之后记得调用callback


            $.ajax({
                type: "GET",
                timeout: 10000,
                beforeSend: (xhr) => {

                },
                headers: {

                },
                url: addres + "/bucket/upload",
                data: {},
                success: (data) => {
                    var sig = data.data.sig;
                    callback(sig);
                },
                error: (err) => {

                },
                complete: (XMLHttpRequest, status) => { //请求完成后最终执行参数　

                }
            });


            //2.直接在浏览器前端计算签名，需要获取自己的accessKey和secretKey, 一般在调试阶段使用
            //拿到签名之后记得调用callback
            //var res = getAuth(); //这个函数自己根据签名算法实现
            //callback(res);


            //3.直接复用别人算好的签名字符串, 一般在调试阶段使用
            //拿到签名之后记得调用callback
            //callback('YOUR_SIGN_STR')
            //

        },
        getAppSignOnce:  (callback) => {//单次签名，必填参数，参考上面的注释即可
            //填上获取单次签名的逻辑
            $.ajax({
                type: "GET",
                timeout: 10000,
                beforeSend: (xhr) => {

                },
                headers: {},
                url: addres + "/bucket/upload",
                data: {},
                success: (data) => {

                    var sig = data.data.sig;

                    console.log(sig);
                    callback(sig);
                },
                error: (err) => {

                },
                complete: (XMLHttpRequest, status) => { //请求完成后最终执行参数　

                }
            });

        }
    });

    var successCallBack =  (result) => {
        $("#result").val(JSON.stringify(result));
    };

    var errorCallBack =  (result) => {
        result = result || {};
        $("#result").val(result.responseText || 'error');
    };

    var progressCallBack = (curr) => {
        $("#result").val('uploading... curr progress is '+curr);
    };

</script>


