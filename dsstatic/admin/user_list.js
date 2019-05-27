
$("#mytable").bootstrapTable({
    method: "GET",
    url: "http://localhost:8090/user/list",//请求路径
    xhrFields: {withCredentials: true},
    search: true,
    searchOnEnterKey: true,
    searchAlign: "left",
    striped: true, //是否显示行间隔色
    pageNumber: 1, //初始化加载第一页
    pagination: true,//是否分页
    sidePagination: 'client',//server:服务器端分页|client：前端分页
    pageSize: 4,//单页记录数
    pageList: [5, 10, 20, 30],//可选择单页记录数
    queryParams: function (params) {//上传服务器的参数
        var temp = {//如果是在服务器端实现分页，limit、offset这两个参数是必须的
            limit: params.limit, // 每页显示数量
            offset: params.offset, // SQL语句起始索引
            Name: $('#search_name').val(),
            Tel: $('#search_tel').val()
        };
        return temp;
    },
    columns: [
        {
            title: '用户名',
            field: 'name',
            width: 160,
        }, {
            title: '用户性别',
            field: 'gender',
            formatter: sex,
            width: 160,

        }, {
            title: '用户年龄',
            field: 'age',
            width: 200,
        }, {
            title: '手机号号码',
            field: 'telphone',
            sortable: true,//排序
            width: 80,
        }, {
            title: '用户图片',
            field: 'thridPartyId',
            width: 80,
            formatter: imagesrc,
        },
        {
            title: '操作',
            field: 'id',
            formatter: operation,//对资源进行操作
            width: 80,
        }
    ],
    onLoadSuccess: function () {  //加载成功时执行
        console.info("加载成功");
    },
    onLoadError: function () {  //加载失败时执行
        console.info("加载数据失败");
    }
});

//性别
function sex(value, row, index) {
    if (value == 0) {
        return "男";
    } else {
        return "女";
    }
};

//图片
function imagesrc(value, row, index) {
    return "<img style='width:30px' src='" + value + "'";
};

//删除、编辑操作
function operation(value, row, index) {
    var htm = "<span class='glyphicon glyphicon-trash' id='del' Itemid='" + value + "' onclick='delItem(this)'></span>&nbsp;&nbsp;<span class='glyphicon glyphicon-edit' editid='" + value + "' onclick='edit(this)'></span>";
    return htm;
};

//删除
function delItem(dom) {
    var message = confirm("确定删除吗？");
    if (message == true) {
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            url: "http://localhost:8090/item/delete",
            data: {
                "id": $(dom).attr("Itemid"),
            },
            success: function (data) {
                alert("删除成功");
            },
            error: function (data) {
                alert("服务器忙");
            }
        });
    }
};
//编辑
var edit_item = [];

function edit(dom) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8090/user/get",
        data: {
            "id": $(dom).attr("editid"),
        },
        success: function (data) {
            edit_item = data.data;
            showmodel();
        },
        error: function (data) {
            alert("服务器忙");
        }
    });
};

function showmodel() {
    $("#edit_title").val(edit_item.name);
    if (edit_item.sex == 0) {
        $("#edit_sex").val("男");
    } else {
        $("#edit_sex").val("女");
    }
    $("#edit_age").val(edit_item.age);
    $("#edit_phone").val(edit_item.telphone);
    $("#edit_imgurl").val(edit_item.thridPartyId);
    $("#editmodel").modal("show");
};
//用户更新编辑
$("#editSubmit").on("click", function () {
    var eid = edit_item.id;
    var title = $("#edit_title").val();
    var sex;
    if ($("#edit_sex").val() == "男") {
        sex = 0;
    } else {
        sex = 1;
    }
    var age = $("#edit_age").val();
    var telphone = $("#edit_phone").val();
    var img = $("#edit_imgurl").val();
    if (title == null || title == "") {
        alert("姓名不能为空");
        return false;
    }
    if (sex == null || sex == "") {
        alert("性别不能为空");
        return false;
    }
    if (age == null || age == "") {
        alert("年龄不能为空");
        return false;
    }
    if (telphone == null || telphone == "") {
        alert("手机号码不能为空");
        return false;
    }
    if(img == null|img == ""){
        alert("用户图片不能为空");
        return false;
    }
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "http://localhost:8090/user/update",
        data: {
            "id": eid,
            "name": title,
            "age": age,
            "gender": sex,
            "telphone": telphone,
            "img":img,
        },
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status == "success") {

                alert("编辑成功");
            } else {
                alert("编辑失败，原因为：" + data.data.errMsg);
            }
        },
        error: function (data) {
            alert("编辑失败,原因为:" + data.responseText)
        }
    });
    return false;
});
//用户信息编辑图片上传
$("#file-edit").fileinput({//初始化上传文件框
    showUpload : false,
    showRemove : false,
    uploadAsync: true,
    uploadLabel: "上传",//设置上传按钮的汉字
    uploadClass: "btn btn-primary",//设置上传按钮样式
    showCaption: false,//是否显示标题
    language: "zh",//配置语言
    uploadUrl: "http://localhost:8090/item/upfile",
    maxFileSize : 0,
    maxFileCount: 1,/*允许最大上传数，可以多个，当前设置单个*/
    enctype: 'multipart/form-data',
    allowedFileExtensions : ["jpg", "png","gif"],/*上传文件格式*/
    initialPreview: [
        "<img src='__ROOT__"+edit_item.image+"' class='file-preview-image'>"
    ],
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    dropZoneTitle: "请通过拖拽图片文件放到这里",
    dropZoneClickTitle: "或者点击此区域添加图片",
    showBrowse: false,
    browseOnZoneClick: true,
    slugCallback : function(filename) {
        return filename.replace('(', '_').replace(']', '_');
    }
});
//上传文件成功，回调函数
$("#file-edit").on("fileuploaded", function(event, data, previewId, index) {
    var result = data.response; //后台返回的json
    $("#edit_imgurl").val(result.data);
    $("#picid").val(result.id);//拿到后台传回来的id，给图片的id赋值序列化表单用
    $.ajax({//上传文件成功后再保存图片信息
        url:'BannerPicAction!savaForm.action',
        type:'post',
        dataType:'json',
        data:$('#edit').serialize(),//form表单的值
        success:function(data,status){
            if (status == "success") {

                if(data.status == "success"){//提交成功
                }else{
                    alert("添加失败,编码的错误!");
                }

            } else {
                alert("添加失败,ajax请求返回失败!");
            }
        },
        cache:false,                    //不缓存
    });
});
$('#savePic').on('click',function (){// 提交图片信息 //
    $('#file-pic').fileinput('upload');
});