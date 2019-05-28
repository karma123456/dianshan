
//商品信息表格
$("#mytable").bootstrapTable({
    method : "GET",
    url : "http://localhost:8090/item/list",//请求路径
    xhrFields:{withCredentials:true},
    search:true,
    searchOnEnterKey:true,
    searchAlign:"left",
    striped : true, //是否显示行间隔色
    pageNumber : 1, //初始化加载第一页
    pagination : true,//是否分页
    sidePagination : 'client',//server:服务器端分页|client：前端分页
    pageSize : 8,//单页记录数
    pageList : [ 5, 10, 20, 30 ],//可选择单页记录数
    queryParams : function(params) {//上传服务器的参数
        var temp = {//如果是在服务器端实现分页，limit、offset这两个参数是必须的
            limit : params.limit, // 每页显示数量
            offset : params.offset, // SQL语句起始索引
            Name : $('#search_name').val(),
            Tel : $('#search_tel').val()
        };
        return temp;
    },
    columns : [
        {
            title : '商品名',
            field : 'title',
            width: 160,
        }, {
            title : '商品图片',
            field : 'image',
            formatter:imagesrc,
            width: 160,

        }, {
            title : '商品描述',
            field : 'desciption',
            width: 200,
        }, {
            title : '商品价格',
            field : 'price',
            sortable:true,//排序
            width: 80,
        }, {
            title : '商品库存',
            field : 'stock',
            sortable:true,//排序
            width: 80,
        },{
            title : '商品销量',
            field : 'sales',
            sortable:true,//排序
            width: 80,
        },{
            title : '操作',
            field : 'id',
            formatter : operation,//对资源进行操作
            width: 80,
        }
    ],
    onLoadSuccess: function(){  //加载成功时执行
        console.info("加载成功");
    },
    onLoadError: function(){  //加载失败时执行
        console.info("加载数据失败");
    }
});
//图片
function imagesrc(value, row, index){
	var tarra = value.split(';'); 
    return "<img style='width:30px' src='"+tarra[0]+"'";
};

//删除、编辑操作
function operation(value, row, index) {
    var htm = "<span class='glyphicon glyphicon-trash' id='del' Itemid='"+value+"' onclick='delItem(this)'></span>&nbsp;&nbsp;<span class='glyphicon glyphicon-edit' editid='"+value+"' onclick='edit(this)'></span>";
    return htm;
};

//删除
function delItem(dom){
    var message = confirm("确定删除吗？");
    if(message == true){
        $.ajax({
            type:"POST",
            contentType:"application/x-www-form-urlencoded",
            url:"http://localhost:8090/item/delete",
            data:{
                "id":$(dom).attr("Itemid"),
            },
            success:function(data){
                alert("删除成功");
            },
            error:function(data){
                alert("服务器忙");
            }
        });
    }
};
//编辑
var edit_item = [];
function edit(dom){
    $.ajax({
        type:"GET",
        url:"http://localhost:8090/item/get",
        data:{
            "id":$(dom).attr("editid"),
        },
        success:function(data){
            edit_item=data.data;
            showmodel();
        },
        error:function(data){
            alert("服务器忙");
        }
    });
};
function showmodel(){
    $("#edit_title").val(edit_item.title);
    $("#edit_desciption").val(edit_item.desciption);
    $("#edit_price").val(edit_item.price);
    $("#edit_stock").val(edit_item.stock);
    $("#edit_imgurl").val(edit_item.image);
    $("#edit_sales").val(edit_item.sales);

    $("#editmodel").modal("show");
};
//商品更新编辑
$("#editSubmit").on("click",function(){
    var eid=edit_item.id;
    var title=$("#edit_title").val();
    var price=$("#edit_price").val();
    var desciption=$("#edit_desciption").val();
    var stock=$("#edit_stock").val();
    var imgurl=$("#edit_imgurl").val();
    var sales=$("#edit_sales").val();
    if(title == null || title == ""){
        alert("商品标题不能为空");
        return false;
    }
    if(price == null || price == ""){
        alert("商品价格不能为空");
        return false;
    }
    if(desciption == null || desciption == ""){
        alert("商品描述不能为空");
        return false;
    }
    if(stock == null || stock == ""){
        alert("商品库存不能为空");
        return false;
    }
    if(imgurl == null || imgurl == ""){
        alert("商品图片不能为空");
        return false;
    }
    if(sales == null || sales == ""){
        alert("商品销量不能为空");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8090/item/update",
        data:{
            "id":eid,
            "title":title,
            "price":price,
            "desciption":desciption,
            "stock":stock,
            "imgurl":imgurl,
            "sales":sales
        },
        xhrFields:{withCredentials:true},
        success:function (data) {
            if(data.status == "success"){

                alert("编辑成功");
            }else{
                alert("编辑失败，原因为："+data.data.errMsg);
            }
        },
        error:function (data) {
            alert("编辑失败,原因为:"+data.responseText)
        }
    });
    return false;

});

//商品创建
var images=[];
var i=0;
$("#ItemSubmit").on("click",function () {
    var title=$("#title").val();
    var price=$("#price").val();
    var desciption=$("#desciption").val();
    var stock=$("#stock").val();
var imgurl;
for(var n=0;n<images.length;n++){
    imgurl=images[n]+';';
}
    var sales=$("#sales").val();
    if(title == null || title == ""){
        alert("商品标题不能为空");
        return false;
    }
    if(price == null || price == ""){
        alert("商品价格不能为空");
        return false;
    }
    if(desciption == null || desciption == ""){
        alert("商品描述不能为空");
        return false;
    }
    if(stock == null || stock == ""){
        alert("商品库存不能为空");
        return false;
    }
    if(imgurl == null || imgurl == ""){
        alert("商品图片不能为空");
        return false;
    }
    if(sales == null || sales == ""){
        alert("商品销量不能为空");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8090/item/create",
        data:{
            "title":title,
            "price":price,
            "desciption":desciption,
            "stock":stock,
            "imgurl":imgurl,
            "sales":sales,
        },
        xhrFields:{withCredentials:true},
        success:function (data) {
            if(data.status == "success"){

                alert("提交成功");
            }else{
                alert("提交失败，原因为："+data.data.errMsg);
            }
        },
        error:function (data) {
            alert("提交失败,原因为:"+data.responseText);
        }
    });
    return false;
});
//商品添加图片上传
$("#file-pic").fileinput({//初始化上传文件框
showClose:false,

    showUpload : true,
    showRemove : true,
    uploadAsync: true,
    uploadLabel: "上传",//设置上传按钮的汉字
    uploadClass: "btn btn-primary",//设置上传按钮样式
    showCaption: false,//是否显示标题
    language: "zh",//配置语言
    uploadUrl: "http://localhost:8090/item/upfile",
    maxFileSize : 0,
    maxFileCount: 5,/*允许最大上传数，可以多个，当前设置单个*/
    enctype: 'multipart/form-data',
    allowedFileExtensions : ["jpg", "png","gif"],/*上传文件格式*/
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    dropZoneTitle: "请通过拖拽图片文件放到这里",
    dropZoneClickTitle: "或者点击此区域添加图片",
    showBrowse: true,
    browseOnZoneClick: false,
    slugCallback : function(filename) {
        return filename.replace('(', '_').replace(']', '_');
    }
});

//上传文件成功，回调函数
$("#file-pic").on("fileuploaded", function(event, data, previewId, index) {
    var result = data.response; //后台返回的json
  	//$("#imgurl").val(result.data);
	images[i]=result.data;
	i=i+1;
    $("#picid").val(result.id);//拿到后台传回来的id，给图片的id赋值序列化表单用
    $.ajax({//上传文件成功后再保存图片信息
        url:'BannerPicAction!savaForm.action',
        type:'post',
        dataType:'json',
        data:$('#form1').serialize(),//form表单的值
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
$('#savePic').on('click',function (){// 提交图片信息
    $('#file-pic').fileinput('upload');
});
//商品信息编辑图片上传
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
    maxFileCount: 5,/*允许最大上传数，可以多个，当前设置单个*/
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
