
$("#mytable").bootstrapTable({
    method: "GET",
    url: "http://localhost:8090/order/list",//请求路径
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
            title: '订单号',
            field: 'id',
            width: 160,
        }, {
            title: '用户id',
            field: 'userId',

            width: 160,

        }, {
            title: '商品id',
            field: 'itemId',
            width: 200,
        }, {
            title: '商品价格',
            field: 'itemPrice',
            sortable: true,//排序
            width: 80,
        }, {
            title: '购买数量',
            field: 'amount',
            width: 80,
        },
        {
            title:'实付金额',
            field:'orderPrice',
            width:80,
        },
        {
            title:'活动id',
            field:'promoId',
            width:80,
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
//删除、编辑操作
function operation(value, row, index) {
    var htm = "<span class='glyphicon glyphicon-trash' id='del' orderid='" + value + "' onclick='delItem(this)'></span>&nbsp;&nbsp;<span class='glyphicon glyphicon-edit' editid='" + value + "' onclick='edit(this)'></span>";
    return htm;
};
//删除
function delItem(dom) {
    var message = confirm("确定删除吗？");
    if (message == true) {
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            url: "http://localhost:8090/order/detele",
            data: {
                "id": $(dom).attr("orderid"),
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
//查看