<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html style="height:100%;width: 100%;">
<head>
    <meta charset="UTF-8">
    <!-- 引入样式 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/element-ui/1.4.1/theme-default/index.css">
</head>
<body class="body">
<div id="app" style="height:100%;width: 100%;">

    <el-upload
            action="''"
            name="fileContent"
            :data="body"
            list-type="picture-card"
            :headers="headers"
            :multiple="multiple"
            :on-error="onerror"
            :thumbnail-mode="true"
            :file-list="fileList"
            :before-upload="beforeAvatarUpload"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
    >
        <i class="el-icon-plus"></i>
    </el-upload>


    <el-dialog v-model="dialogVisible" size="tiny">
        <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>


</div>
</body>
<jsp:include page="comm.jsp" flush="true"/>
<%-- qcloud --%>


<%--suppress JSAnnotator --%>
<script>

    var vm = new Vue({
        el: '#app',
        data(){
            return {
                dialogImageUrl: '',
                dialogVisible: false,
                imgFileInitList: [],
                multiple: true,
                headers: {
//                    Authorization: localStorage.getItem("Authorization"),

                },
//                filecontent: "/Users/apple/Desktop/123.jpg",
                body: {
                    "op": "upload",
                },
                fileList: [],
                src: 'http://sh.file.myqcloud.com/files/v2/1251972445/muyoucun/123.jpg' +  "?sign=" + localStorage.getItem("Authorization"),
                src1:'http://web.image.myqcloud.com/photos/v2/1251972445/muyoucun/0/'

            }
        },
        methods: {
            successCallBack(){},
            errorCallBack(result){
                console.log(result);
            },
            onerror(err, file, fileList){
                console.log(err);
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);

            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            beforeAvatarUpload(file){
                console.log(file)
                this.fileList.push({url: file.url})
                cos.uploadFile(successCallBack, errorCallBack, progressCallBack, "muyoucun", "123.jpg", file, 0);
                throw new Error('aaaaa')

            },
            upload(){

                cos.getAppSignOnce((data) => {
                    localStorage.setItem("Authorization", data);
                });



                this.headers.Authorization = localStorage.getItem("Authorization");

            }
        },
        mounted() {
            this.upload();
        }
    })
</script>
<style>


    .body {
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
        margin-left: 0px;
        height: 100%;
        font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
        width: 100%;
    }
</style>
</html>

