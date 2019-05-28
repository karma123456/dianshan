//从url中解析商品id值
function getParam(paramName) {
    paramVlaue = "", isFound = !1;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
        while (i < arrSource.length && !isFound) {
            arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramVlaue = arrSource[i].split("=")[1], isFound = !0)
        }
    }
    return paramVlaue == "" && (paramValue = null), paramVlaue
};
//根据id值获取商品信息
var g_itemvo = [];
jQuery(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http:/localhost:8090/item/get",
        data: {
            "id": getParam("id"),
        },
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status == "success") {
                g_itemvo = data.data;
                reloadDom();
                setInterval(reloadDom, 1000);
		//商品图片
 		 var tarra = g_itemvo.image.split(';',5);
		$("#pic").attr("src",tarra[0]);
		for(var i = 1;i<=tarra.length;i++){
		var dom =  "<li id='"+i+"'><img src='"+tarra[i-1]+"'/></li>";
		$("#ul_img").append(dom);
		}
            }
            else {
                alert("获取信息失败" + data.data.errMsg);
            }
        },
        error: function (data) {
            //  alert("获取信息失败"+data.responseText);
        },
    });
});


//将获取到的商品信息填入页面
//对特价商品添加倒计时模块
function reloadDom() {
   $("#title").text(g_itemvo.desciption);
    $("#stock").text(g_itemvo.stock);
    $("#price").text(g_itemvo.price);
    <!--$("#imgurl").attr("src", g_itemvo.imgurl);-->
	 
   $("#sales").text(g_itemvo.sales);
	var itemprice = $("#price");
      
    if (g_itemvo.promoStatus == 1) {
        //秒杀活动还未开始
        //计时器
        var startTime = g_itemvo.startDate.replace(new RegExp("-", "gm"), "/");
        startTime = (new Date(startTime)).getTime();
        var nowTime = Date.parse(new Date());
        var delta = (startTime - nowTime) / 1000;

       
        if (delta <= 0) {
            //活动开始
            g_itemvo.promoStatus = 2;
            reloadDom();
        }
        $("#promoStartDate").text("秒杀活动将于" + g_itemvo.startDate + "开始 倒计时" + delta + "秒");
        $("#promoprice").text(g_itemvo.promoPrice);
        $("#createorder").attr("disabled", true);
        $("#buyorder").attr("disabled", true);
        $("#promostartDateContainer").show();
	$("#basc").hide();
    } else if (g_itemvo.promoStatus == 2) {
        //秒杀活动进行中
        $("#promoStartDate").text("秒杀活动正在进行中");
        $("#itemprice").text(g_itemvo.promoPrice);
        $("#createorder").attr("disabled", false);
        $("#buyorder").attr("disabled", false);
        $("#promostartDateContainer").show();
	$("#basc").hide();

        itemprice.css("text-decoration", "line-through");
    }
};
//加入购物车下单操作
$("#createorder").on("click", function () {
    var amount = $("#amount").val();
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "http:/localhost:8090/order/createOrder",
        data: {
            "itemId": g_itemvo.id,
            "amount": amount,
            "promoId": g_itemvo.promoId,
        },
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status == "success") {
                alert("下单成功");
                window.location.reload();
            } else {
                alert("下单失败,原因为：" + data.data.errMsg);
                if (data.errorCode == 10004) {
                    window.location.href = "getotp.html";
                }
            }
        },
        error: function (data) {
            alert("下单失败，原因为：" + data.responseText);
        }
    });
    return false;
});

//数量增加
$(".add").click(function () {
    var num = document.getElementById("amount");
    if (num.value >= g_itemvo.stock) {
       num.val(g_itemvo.stock);
    }
    num.value = parseInt(num.value) + 1;
});
//数量减少
$(".min").click(function () {
    var num = document.getElementById("amount");
    if (num.value == 1) {
        num.val(1);
    }
    num.value = parseInt(num.value) - 1;
});


//商品推荐
var g_itemlist = [];
jQuery(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http:/localhost:8090/item/list",
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status == "success") {
                g_itemlist = data.data;
     		reloadlist();
            }
            else {
                alert("获取信息失败" + data.data.errMsg);
            }
        },
        error: function (data) {
            //  alert("获取信息失败"+data.responseText);
        },
    });
});
function reloadlist(){
	for(var i=0;i<g_itemlist.length;i++){
		var itemlist=g_itemlist[i];
		var tarra = itemlist.image.split(';'); 
		var dom = "<div class='cary cary-left' name='box'><img src='"+tarra[0]+"'><div><label>￥"+itemlist.price+"</label><h3>"+itemlist.title+"</h3></div></div>";
		$("#itemlist").append(dom);
		}
		pagelist();
		
};
//分页
var $div = $("#list_item");
var pgindex = 1;//当前页
var eachCnt = 4;//每页显示个数
var boxes = $("div[name='box']");
var cnt = boxes.length;
var indexs = new Array(cnt);
for (var i = 0; i < cnt; i++) {
    indexs[i] = i;
}
var allPages = Math.ceil(cnt / eachCnt);

function pagelist(){
$div = $("#list_item");
pgindex = 1;//当前页
eachCnt = 4;//每页显示个数
boxes = $("div[name='box']");
cnt = boxes.length;
indexs = new Array(cnt);
for (var i = 0; i < cnt; i++) {
    indexs[i] = i;
}
allPages = Math.ceil(cnt / eachCnt);

showPage(1);
};
function showPage(pageIndex) {
    if (pageIndex == 0 || pageIndex == (allPages + 1)) {
        return;
    }
    var start = (pageIndex - 1) * 4;
    var end = start + 4;
    end = end > cnt ? cnt : end;
    var subIndexs = indexs.slice(start, end);
    for (var i = 0; i < cnt; i++) {
        if (contains(i, subIndexs)) {
            boxes.eq(i).show();
        } else {
            boxes.eq(i).hide();
        }
    }
    pgindex = pageIndex;
}
function gotopage(value) {
    try {
        value == "-1" ? showPage(pgindex - 1) : showPage(pgindex + 1);
    } catch (e) {
    }
}


var contains = function (element, arr) {

    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == element) {
            return true;
        }
    }
    return false;
};
