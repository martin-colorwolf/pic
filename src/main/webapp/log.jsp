<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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

<jsp:include page="comm.jsp" flush="true"/>

<div id="app">


    <el-row justify="center" align="middle" type="flex">
        <el-col :span="24">
            <div class="grid-content bg-purple-dark" v-loading="loading2"
                 element-loading-text="注册中...">
                <p class="p1">木又寸</p>
                <el-input class="input1" v-model="inputusername" placeholder="请输入用户名"></el-input>
                <el-input class="input2" type="password" v-model="inputpassword" placeholder="请输入密码"></el-input>
                <el-input class="input4" v-model="code1" placeholder="请输入验证码"></el-input>
                <img alt="验证码" class="codeimg" id="scode" @click="code" :src="src">
                <p class="p2">（点击图片更换）</p>
                <el-button @click="log" class="btn1" type="primary">登录</el-button>
                <br/>
                <el-button class="btn2" @click="zhuce" type="text">注册</el-button>

            </div>
        </el-col>
    </el-row>


</div>
</body>


<script>

    var vm = new Vue({
        el: '#app',
        data(){
            return {
                inputusername: '308291390@qq.com',
                inputpassword: 'w787825233',
                inputpassword1: 'w787825233',
                code1: '',
                src: addres + '/hello/getValidateCode?t=' + new Date().getTime(),
                status: 0,
                loading2: false
            }
        },
        methods: {
            code(){
                this.src = addres + "/hello/getValidateCode?t=" + new Date().getTime();
            },
            zhuce(){
                window.location.href = addres + "/hello/registpage"
            },
            log(){

                this.loading2 = true;
                if (this.inputpassword == '' || this.inputusername == '') {
                    vm.$message('用户名或密码空白');
                    vm.code();
                    this.loading2 = false;
                    return false;
                } else if (this.code1.length != 4 || this.code1 == '') {
                    vm.$message('请输入4位验证码');
                    vm.code();
                    this.loading2 = false;
                    return false;
                } else {

                    $.get(addres + "/hello/checkValidateCode?code=" + this.code1, (data) => {
                        this.status = data.status;

                        if (this.status == 200) {

                            if (this.inputpassword == this.inputpassword1 && this.inputpassword != '') {

                                var inputpassword = md5(this.inputpassword);

                                $.ajax({
                                    type: "POST",
                                    timeout: 10000,
                                    beforeSend: (xhr) => {

                                    },
                                    headers: {},
                                    url: addres + "/hello/log",
                                    data: {
                                        username: this.inputusername,
                                        password: inputpassword
                                    },
                                    success: (data) => {
                                        if (data.status != 200) {
                                            vm.$message(data.data);
                                            vm.code();
                                            this.loading2 = false;
                                        } else {
                                            localStorage.setItem("username",this.inputusername);
                                            localStorage.setItem("token", data.data.token);
                                            vm.code();
                                            this.loading2 = false;
                                            vm.$message({
                                                message: '恭喜你，登录成功',
                                                type: 'success'
                                            });

                                            setTimeout(
                                                window.location.href = addres + "/hello/home"
                                                , 2000);
                                        }
                                    },
                                    error: (err) => {

                                    },
                                    complete: (XMLHttpRequest, status) => { //请求完成后最终执行参数　

                                    }
                                });

                            }
                        } else {
                            vm.$message('验证码错误');
                            vm.code();
                            this.loading2 = false;
                            return false;
                        }

                    });
                }
            }
        },
        mounted() {

        }
    })
</script>
<style>
    #app {

    }

    .el-row {
        margin-bottom: 20px;

    &
    :last-child {
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
        height: 360px;
        width: 300px;
        margin: 0 auto;
        border: 1px;
        filter: alpha(Opacity=60);
        -moz-opacity: 0.6;
        opacity: 0.6;
        margin-top: 10%;
    }

    .input1 {
        width: 70%;
        margin-left: 15%;

    }

    .input2 {
        width: 70%;
        margin-left: 15%;
        margin-top: 15px;

    }

    .input4 {
        width: 40%;
        margin-left: 15%;
        margin-top: 15px;
    }

    .codeimg {
        width: 30%;
        display: inline;
        margin-top: 15px;
    }

    .btn1 {
        margin-left: 33%;
        width: 34%;
        margin-top: 20px;

    }

    .btn2 {
        margin-left: 33%;
        width: 34%;
        margin-top: 10px;
    }

    .p1 {
        font-size: 40px;
        font-family: "Helvetica Neue";
        text-align: center;

    }

    .p2 {
        height: 1px;
        font-size: 1px;
        font-family: "Helvetica Neue";
        text-align: right;
        margin-top: -10px;
        margin-right: 30px;
    }


</style>
</html>

