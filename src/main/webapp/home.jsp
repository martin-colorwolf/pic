<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html style="height:100%;width: 100%;">
<head>
    <meta charset="UTF-8">
    <!-- 引入样式 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/element-ui/1.4.1/theme-default/index.css">

</head>
<body class="body">
<jsp:include page="comm.jsp" flush="true"/>
<div id="app" style="height:100%;width: 100%;">

    <header class="header">

        <el-col :span="3" style="height:100%;">
            <p class="p1">木又寸</p>
        </el-col>
        <el-row style="height:100%;">
            <el-col :span="3" style="height:100%;">

                <el-button type="primary" icon="menu" class="menubtn" @click="menu"></el-button>

            </el-col>
            <el-col :span="18" style="height:100%;">
            </el-col>
        </el-row>
    </header>

    <div class="container">

        <el-row style="height:100%;">
            <el-col :span="span1" style="height:100%;">

                <div class="grid-content1 bg-purple-dark">
                    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @select="select"
                             :collapse="isCollapse">
                        <el-menu-item index="1">
                            <i class="el-icon-message"></i>
                            <span slot="title">导航一</span>
                        </el-menu-item>
                        <el-menu-item index="upload">
                            <i class="el-icon-upload2"></i>
                            <span slot="title">上传</span>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <i class="el-icon-setting"></i>
                            <span slot="title">导航三</span>
                        </el-menu-item>
                    </el-menu>
                </div>
            </el-col>

            <el-col :span="span2" style="height:100%;">
                <iframe :src="iframesrc" class="frame"></iframe>
            </el-col>

        </el-row>


    </div>


</div>
</body>


<script>

    var vm = new Vue({
        el: '#app',
        data(){
            return {
                isCollapse: false,
                is: 0,
                span1: 3,
                span2: 21,
                iframesrc: addres + "/registSuccess.jsp"
            }
        },
        methods: {
            select(key){
                if (key == "upload") {

                    console.log(key);
                }

            },
            menu(){
                if (this.is == 1) {
                    this.isCollapse = !this.isCollapse;
                    this.is = 0;
                    this.span1 = 3;
                    this.span2 = 21;
                } else {
                    this.isCollapse = !this.isCollapse;
                    this.is = 1;
                    this.span1 = 2;
                    this.span2 = 22;
                }
            },

            open() {
                this.$alert('请重新登录', {
                    confirmButtonText: '确定',
                    callback: action => {
                        window.location.href = addres + "/log.jsp";
                    }
                });
            },

            checklog(){
                if (localStorage.getItem("token") == null || localStorage.getItem("username") == null || localStorage.getItem("token") == "" || localStorage.getItem("username") == "") {
                    this.open();
                }
            }

        },
        mounted() {
            this.checklog();
        }
    })
</script>
<style>
    .el-row {
        margin-bottom: 0px;

    &
    :last-child {
        margin-bottom: 0;
    }

    }
    .el-col {
        border-radius: 0px;
    }

    .bg-purple-dark {
        background: #EFF2F7;
    }

    .bg-purple {
        background: #d3dce6;
    }

    .bg-purple-light {
        background: #e5e9f2;
    }

    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }

    .grid-content1 {
        width: 100%;
        height: 100%;
        border-radius: 4px;
        float: left;
        box-sizing: border-box;
    }

    .frame {
        height: 100%;
        width: 100%;
        padding-top: 0px;
        padding-right: 0px;
        padding-bottom: 0px;
        padding-left: 0px;
        float: left;
        box-sizing: border-box;
        border: 0px;
    }

    .header {
        height: 80px;
        position: absolute;
        width: 100%;
        top: 0;
        left: 0;
        padding: 0 20px;
        z-index: 1;
        box-sizing: border-box;
        background: #99a9bf;
        display: block;
        background: #20A0FF;

    }

    .container {
        box-sizing: border-box;
        padding-top: 80px;
        height: 100%;
        position: relative;

    }

    .body {
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
        margin-left: 0px;
        height: 100%;
        font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
        width: 100%;
    }

    .p1 {
        margin-top: 17px;
        margin-left: 0vw;
        font-size: 3vw;
        color: #FFFFFF;
    }

    .menubtn {
        margin-top: 4vh;
        margin-left: 0vw;
    }
</style>
</html>



