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
<jsp:include page="comm.jsp" flush="true"/>
<div id='app'>
    <el-row>
        <el-col :span="24">
            <div class="grid-content bg-purple-dark" v-loading="loading2"
                 :element-loading-text="text"
                 :data="tableData"
            >

            </div>
        </el-col>
    </el-row>
</div>


</body>

<script type="text/javascript">

    var vm = new Vue({
        el: '#app',

        data() {
            return {
                loading2: true,
                time: 5,
                text: "激活成功，5后自动跳转到登录页面"
            };
        },
        methods: {
            timewait(){
                if (this.time != 0) {
                    this.time--;
                    setTimeout(() => {
                        this.text = "激活成功," + this.time + "后自动跳转到登录页面";
                        this.timewait();
                    }, 1000);
                }
            }
        },
        mounted() {
            this.timewait()
            setTimeout(() => {
                window.location.href = addres + "/hello/registsuccess"
            }, 5000);
        }
    })
</script>

<style>
    #app {

    }

    .alert {
        margin-top: 5%;
        height: 100px;
        width: 80%;
        margin-left: 10%;
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
        width: 300px;
        height: 320px;
        margin: 0 auto;
        border: 1px;
        filter: alpha(Opacity=60);
        -moz-opacity: 0.6;
        opacity: 0.6;
        margin-top: 10%;
    }

</style>

</html>

