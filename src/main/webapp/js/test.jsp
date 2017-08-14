<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 引入样式 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/element-ui/1.4.1/theme-default/index.css">
</head>
<body style="background: url(http://r.photo.store.qq.com/psb?/V13K2R4l01AmjW/Xjb12fmtOnSYBqaKiyZqdHolD4OUML0ocEzQ*NUPnmA!/r/dGwBAAAAAAAA) no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;">



<div id='app1'>
    <el-row>
        <el-col :span="24">
            <div class="grid-content bg-purple-dark">
                <p class="p1">木又寸</p>
                <el-alert class="alert"  type="success"  :closable="false" show-icon>
                    <p class="p2">恭喜你成功注册木又寸，请在邮箱激活点击链接激活账号就可以啦。</p>
                </el-alert>
                <el-button class="btn1" type="primary" @click="log" >去登录</el-button>
            </div>
        </el-col>
    </el-row>
</div>


</body>

<jsp:include page="../comm.jsp" flush="true"/>

<script type="text/javascript">

    var mv = new Vue({
        el:'#app1',
        methods:{
            log(){
                window.location.href = addres;
            }
        }
    })
</script>



<style >
    #app{

    }
    .alert{
        margin-top: 5%;
        height: 100px;
        width: 80%;
        margin-left: 10%;
    }
    .el-row {
        margin-bottom: 20px;
    &:last-child {
         margin-bottom: 0;
     }
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #FFFFFF;
    }
    .grid-content {
        border-radius: 4px;
        width: 300px;
        height: 320px;
        margin: 0 auto;
        border: 1px;
        filter: alpha(Opacity=60);
        -moz-opacity: 0.6;
        opacity: 0.6;
        margin-top: 10%;
    }
    .h{
        text-align: center;
    }
    .p1{
        font-size: 40px;
        font-family: "Helvetica Neue";
        text-align: center;

    }
    .p2{
        font-size: 19px;
        font-family: "Helvetica Neue";
        text-align: center;

    }
    .btn1{
        width: 30%;
        margin-left: 35%;
        margin-top: 10%;
    }
</style>

</html>
