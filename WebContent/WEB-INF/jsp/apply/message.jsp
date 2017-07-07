<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/mytag.tld" prefix="myTag" %> 
<%@ page import="java.util.Date"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
<head>
<c:set var="CTP" value="${pageContext.request.contextPath}"></c:set>
<c:set var="CTP_APPLY" value="${pageContext.request.contextPath}/resources/apply"></c:set>
<c:set var="CTP_HOME" value="${pageContext.request.contextPath}/resources/home"></c:set>
<c:set var="CTP_ADMIN" value="${pageContext.request.contextPath}/resources/admin"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../home/common/meta.jsp" flush="true"/>
<title>新生报名信息填写</title>

<link rel="stylesheet" href="${CTP_HOME}/css/header.css" />
<link rel="stylesheet" href="${CTP_HOME}/css/footer.css" />
<link rel="stylesheet" href="${CTP_APPLY}/css/applyMessage.css"/>
<link rel="stylesheet" href="${CTP_APPLY}/css/common.css"/>
<link href="${CTP}/resources/admin/css/magic-check.css" rel="stylesheet" type="text/css" />
<link href="${CTP_ADMIN }/js/extends/uploadify/css/uploadify.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${CTP}/resources/home/js/extends/zoomify/zoomify.min.css"/>
<script type="text/javascript" src="${CTP_HOME}/js/lib/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${CTP_HOME}/js/module/common.js" ></script>
<script type="text/javascript" src="${CTP_ADMIN }/js/extends/uploadify/js/jquery.uploadify.min.js" ></script>
<script type="text/javascript" src="${CTP}/resources/common/js/extends/layer-2.4/layer.js"></script>
<script type="text/javascript" src="${CTP}/resources/home/js/extends/zoomify/zoomify.min.js" ></script>
	
<script type="text/javascript">
var CTPPATH="${pageContext.request.contextPath}";
var CTP_ADMIN=CTPPATH+"/resources/admin";

</script>

<script type="text/javascript">
$(function(){
	/****校验学生信息***/
	var ok_stu_name=false;
	var ok_stu_nation=false;
	var ok_stu_sex=false;
	var ok_stu_card=false;
	var ok_stu_address=false;
	var ok_stu_register=false;
	
	var ok_father_name=false;
	var ok_father_card=false;

	var ok_mother_name=false;
	var ok_mother_card=false;
	
	//检验不能为空和数字
	 function check(className,tips){
		 if(document.readyState == "complete"){
		    var str = $(className).val();
			str = str.replace(/\s/g , '');//输入空格时自动忽略，\s表示空格
	     	if( str ==""|| str==null ){
	     		
	     			layer.tips("*"+tips+"不能为空", $(className));		
	     		
	   		
			$(className).focus();
				return false;
			}else{
				var filter=/^\d*$/;
				if(filter.test(str)){
					layer.tips("*"+tips+"不能为数字",$(className));
					return false;
				}else {
					
					return true;
				}
			}
		 }
	   }
	/*****初始验证******/	
	
	
	
	//初始检测--用户名
	if( check($(".tab-basicMes-stuname-input"),"学生姓名")){
		  ok_stu_name=true;	
	}
	
	//初始检测 --民族
	
	if( check($(".tab-basicMes-nation-input"),"民族")){
		  ok_stu_nation=true;	
	}
	
	//初始检测--现地址校验
	
	
	 //现地址校验

	 if( check($(".tab-basicMes-address-input"),"现地址") ){
		 ok_stu_address=true;
	 }

  
   //所在户籍

	 if( check($(".tab-basicMes-register-input"),"所在户籍")){
		 ok_stu_register=true;
	 } 
   
   
   
   //验证父亲姓名

	  if( check($(".tab-guardianMes-father-name-input"),"父亲姓名")){
		  ok_father_name=true;
	  } 
  
	//母亲姓名
		
	if(check($(".tab-guardianMes-mother-name-input"),"母亲姓名")){
		ok_mother_name=true;
	} 
		
	
	
	//用户名校验
	$(".tab-basicMes-stuname-input").bind("blur",function(){
		
		if( check($(this),"学生姓名")){
		  ok_stu_name=true;	
		}
	});
	
	//验证民族

	$(".tab-basicMes-nation-input").bind("blur",function(){
		if( check($(this),"民族")){
			  ok_stu_nation=true;	
		}
		
	});
	

	
	
	 //定义地区数组  
    var CityArray = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" }  
  
    //验证身份证及返回地区、出生年月、性别  
    function CheckIdCard(sId) {  
        if (sId.length == 15) {  
            sId = sId.replace(/([\d]{6})(\d{9})/, "$119$2x");  
        }  
        var iSum = 0  
        var info = ""  
        if (!/^\d{17}(\d|x)$/i.test(sId)) return "Error:非法的身份证号";  
        sId = sId.replace(/x$/i, "a");  
        if (CityArray[parseInt(sId.substr(0, 2))] == null) return "Error:非法地区";  
        sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));  
        var d = new Date(sBirthday.replace(/-/g, "/"))  
        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) return "Error:非法生日";  
        for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11)  
        if (iSum % 11 != 1) return "Error:非法证号";  
        return CityArray[parseInt(sId.substr(0, 2))] + "," + sBirthday + "," + (sId.substr(16, 1) % 2 ? "男" : "女")  
    }  
	
   //校验身份证
   $(".tab-basicMes-idnum-input").bind("blur",function(){
	  
	   var mythis=$(this);
	   var str = $(this).val();
		str = str.replace(/\s/g , '');//输入空格时自动忽略，\s表示空格
	   	if( str ==""|| str==null ){
	   		layer.tips('*身份证不能为空', $(this));
	   		$(this).focus();
	   		ok_stu_card=false;
		}else{
			layer.tips(CheckIdCard(str), $(this));
			if(CheckIdCard(str).match("Error") ==null){
 				
 				//ok_card=true;
 				//检测身份证是否被注册
 				//start
				$.ajax({
					type:'post',
					dataType:'json',
					url:CTPPATH+"/apply/index/checkCard",
					data:{"card":str},
				
					beforeSend:function(){
						//显示正在加载
						layer.load(1,{offset: 0,time:2000});
					},
					success:function(data){
	
						//关闭正在加载
						setTimeout(function(){
							  layer.closeAll('loading');
						}, 1000);
						
						//代表用户名可用
						if(data==1){
							layer.tips('恭喜！身份证可以被注册', mythis);
							ok_stu_card=true;
						}else if(data==0){
							//代表用户名不可用
							layer.tips('*抱歉，身份证已被使用', mythis);
							$(this).focus();
							ok_stu_card=false;
						}
					},
					error:function(){
	
						//关闭正在加载
						setTimeout(function(){
							  layer.closeAll('loading');
						}, 1000);
						layer.msg("出错了", {icon: 2,time:2000});
					}
				});
				
				//end
 			}
		}
   });
   
   //现地址校验
   $(".tab-basicMes-address-input").bind("blur",function(){
	 if( check($(this),"现地址") ){
		 ok_stu_address=true;
	 }
   });
  
   //所在户籍
   $(".tab-basicMes-register-input").bind("blur",function(){
	 if( check($(this),"所在户籍")){
		 ok_stu_register=true;
	 } 
   });
   
   /*****监护人验证******/
   
   //验证父亲姓名
   $(".tab-guardianMes-father-name-input").bind("blur",function(){
	  if( check($(this),"父亲姓名")){
		  ok_father_name=true;
	  } 
   });
   //验证父亲身份证
   $(".tab-guardianMes-father-idnum-input").bind("blur",function(){
	   var str = $(this).val();
		str = str.replace(/\s/g , '');//输入空格时自动忽略，\s表示空格
	   	if( str ==""|| str==null ){
	   		layer.tips('*身份证不能为空', $(this));
	   		$(this).focus();
	   		ok_father_card=false;	
		}else{
			layer.tips(CheckIdCard(str), $(this));	
			if(!CheckIdCard(str).match("Error")){
				ok_father_card=true;
			}
		}
   });
   
   
   //手机号  ，座机
/*     function checkTel(value){  
    	 var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;  
    	 var isMob=/^((\+?86)|(\+86))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	     var val=value.trim();  
	     alert(isMob.test(val)+"---"+isPhone.test(val));
	     if(isMob.test(val)||isPhone.test(val)){  
	         return true;  
	     }else{  
	         return false;  
	     }  
     }   
    */
  
	//母亲姓名
	$(".tab-guardianMes-mother-name-input").bind("blur",function(){
		if(check($(this),"母亲姓名")){
			ok_mother_name=true;
		} 
	});

	//身份证  
	$(".tab-guardianMes-mother-idnum-input").bind("blur",function(){
		 var str = $(this).val();
			str = str.replace(/\s/g , '');//输入空格时自动忽略，\s表示空格
		   	if( str ==""|| str==null ){
		   		layer.tips('*身份证不能为空', $(this));
		   		$(this).focus();
		   		ok_mother_card=false;
			}else{
				layer.tips(CheckIdCard(str), $(this));	
				if(!CheckIdCard(str).match("Error")){
					ok_mother_card=true;
				}
			}
	});
	
	 //判断协议的同意情况
	   var agree_status= $("input[type=radio][name=agree]:checked").val();
		
	    if(agree_status=="no"){
	    	$(".submit-btn").addClass("no-allow");
	    	$(".submit-btn").attr('disabled',true);  
	    	$(".submit-btn").text("禁止提交");
	    }else{
	    	$(".submit-btn").removeClass("no-allow");
	    	$(".submit-btn").removeAttr('disabled',true);
	    	$(".submit-btn").text("提交报名");
	    }		

    
	 //协议判断事件
	 $("input[type=radio][name=agree]").bind("click",function(){
		
		//判断协议的同意情况
		   var agree_status= $("input[type=radio][name=agree]:checked").val();
			
		    if(agree_status=="no"){
		    	$(".submit-btn").addClass("no-allow");
		    	$(".submit-btn").attr('disabled',true);
		    	$(".submit-btn").text("禁止提交");
		    }else{
		    	$(".submit-btn").removeClass("no-allow");
		    	$(".submit-btn").removeAttr('disabled',true);
		    	$(".submit-btn").text("提交报名");
		    }	
	 });
	    
	$(".submit-btn").bind("click",function(){
		
		
		
		var stu_name=$(".tab-basicMes-stuname-input").val();
		var stu_nation=$(".tab-basicMes-nation-input").val();
		var stu_sex=$("input[type='radio']:checked").val();
		var stu_card=$(".tab-basicMes-idnum-input").val();
		var original_school=$(".tab-basicMes-originalsch-input").val();
		var stu_address=$(".tab-basicMes-address-input").val();
		var stu_register=$(".tab-basicMes-register-input").val();
		
		//监护人
		var father_name=$(".tab-guardianMes-father-name-input").val();
		var father_card=$(".tab-guardianMes-father-idnum-input").val();
		var father_phone=$(".tab-guardianMes-father-phonenum-input").val();
		
		var mother_name=$(".tab-guardianMes-mother-name-input").val();
		var mother_card=$(".tab-guardianMes-mother-idnum-input").val();
		var mother_phone=$(".tab-guardianMes-mother-phonenum-input").val();
		
		
		//获取 图片
		var img_path=$(".hidden-item-image").val();
		
		if(stu_sex!=null){
			ok_stu_sex=true;
		}
        
		
		if(ok_stu_name==true && ok_stu_nation==true && ok_stu_sex==true && ok_stu_card==true && ok_stu_address==true && ok_stu_register==true && ok_father_name==true && ok_father_card==true && ok_mother_name==true && ok_mother_card==true  && img_path!=""){
			
			//注册
			//start
			 //注册
    		$.ajax({
				type:'post',
				dataType:'json',
				url:CTPPATH+"/apply/index/saveApplyInfo",
				data:{"uid":"${uid}","stuName":stu_name,"stuNation":stu_nation,"stuSex":stu_sex,"stuIdNum":stu_card,"originalSchool":original_school,"address":stu_address,"register":stu_register,"fatherName":father_name,"fatherIdNum":father_card,"fatherPhone":father_phone,"motherName":mother_name,"motherIdNum":mother_card,"motherPhone":mother_phone,"imgPath":img_path},
			
				beforeSend:function(){
					//显示正在加载
					layer.msg('正在报名',{offset: 0,time:2000});
				},
				success:function(data){

					//关闭正在加载
					setTimeout(function(){
						  layer.closeAll('loading');
					}, 1000);
					
					//代表用户名可用
					if(data>0){
						layer.msg('恭喜！报名成功',{time:4000});
						window.location.href=CTPPATH+"/apply/index/status?id="+data;
					}else if(data==0){
						//代表用户名不可用
						layer.msg('*抱歉，报名失败，请仔细检查信息再进行提交！',{time:4000});
						
					}
				},
				error:function(){

					//关闭正在加载
					setTimeout(function(){
						  layer.closeAll('loading');
					}, 1000);
					layer.msg("出错了", {icon: 2,time:2000});
				}
			});
			
        }else{
        	layer.msg("*用户信息验证失败，请仔细核对无误后进行提交！");
        }
    	
    });
			
   
});
</script>
<script type="text/javascript">

$(function() {
	 /*************setting***************/  
   var definedData = [];   
   definedData.fileSizeLimit = "20MB";  //上传大小  
   definedData.queueSizeLimit = 5;      //允许上传个数文件  
	
	 var errorData = [];  
    errorData.err100 = "文件个数超出系统限制，只允许上传" + definedData.queueSizeLimit + "个文件！";  
    errorData.err110 = "文件超出系统限制的大小，限制文件大小" + definedData.fileSizeLimit + "！";  
    errorData.err120 = "文件大小异常！";    
	
    //start uplaoding
	$("#uploadify").uploadify({
		debug			: false, 

		swf 			:  CTP_ADMIN+'/js/extends/uploadify/js/uploadify.swf',	//swf文件路径
		method			: 'post',	// 提交方式
		uploader		:  CTPPATH+'/admin/upload/uploadimage;jsessionid=${pageContext.session.id}', // 服务器端处理该上传请求的程序(controller)

		preventCaching	: true,		// 加随机数到URL后,防止缓存

		buttonCursor	: 'hand',	// 上传按钮Hover时的鼠标形状
	//	buttonImage		: 'img/.....png',	// 按钮的背景图片,会覆盖文字
		buttonText		: '选择图片'	, //按钮上显示的文字，默认”SELECTFILES”
		height			: 30	, // 30 px
		width			: 120	, // 120 px
		
		fileObjName		: 'filedata',	//文件对象名称, 即属性名
		fileSizeLimit	: '10MB'	,		// 文件大小限制, 10M
		fileTypeDesc	: '图片类型(.JPG,.GIF,.PNG)'	,	//文件类型说明 any(*.*)
		fileTypeExts	: '*.jpg;*.png;.gif',		// 允许的文件类型,分号分隔
		//formData		: {'id':'1', 'type':'myFile'} , //指定上传文件附带的其他数据。也动态设置。可通过getParameter()获取
	
		auto            : true,    //选择完文件是否自动上传
		multi			: false,	// 多文件上传
		progressData	: 'speed,percentage',	// 进度显示, speed-上传速度,percentage-百分比	
		queueID			: 'fileQueue',//上传队列的DOM元素的ID号
		queueSizeLimit	: 99	,	// 队列长度
		removeCompleted : true	,	// 上传完成后是否删除队列中的对应元素
		removeTimeout	: 5	,	//上传完成后多少秒后删除队列中的进度条, 
		requeueErrors	: true,	// 上传失败后重新加入队列
		uploadLimit		: definedData.queueSizeLimit,	// 最多上传文件数量

		successTimeout	: 30	,//表示文件上传完成后等待服务器响应的时间。超过该时间，那么将认为上传成功。
		// 在文件被移除出队列时触发	
		//onCancel : function(file) { alert( 'The file ' + file.name + ' was cancelled!' ); },
		
		// 在调用cancel方法且传入参数’*’时触发
		onClearQueue : function (queueItemCount) {
				if(queueItemCount>0){
				layer.msg(queueItemCount + ' 文件将从上传列队中移除'); 
				}
			},

		// 打开文件对话框 关闭时触发
		onDialogClose : function (queueData) {
						/*	alert(
								"文件对话窗口中选了几个文件:" + queueData.filesSelected + "---" +
								"队列中加了几个文件:" + queueData.filesQueued + "---" +
								"队列中被替换掉的文件数:" + queueData.filesReplaced + "---" +
								"取消掉的文件数:" + queueData.filesCancelled + "---" + 
								"上传出错的文件数:" + queueData.filesErrored
							); */
						},
		
		// 选择文件对话框打开时触发
		onDialogOpen : function () { /*alert( 'please select files' ) */ },
	
		// 没有兼容的FLASH时触发
		onFallback : function(){ alert( '您未安装FLASH控件,无法上传文件!请安装FLASH控件后再试。' ) },
		
		// 每次初始化一个队列时触发, 即浏览文件后, 加入一个队列
		//onInit : function (instance) { alert( 'The queue ID is ' + instance.settings.queueID ) },
	
		// 上传文件处理完成后触发（每一个文件都触发一次）, 无论成功失败
		//onUploadComplete : function(file){ alert( 'The file ' + file.name + ' uploaded finished!' ) },

		// 上传文件失败触发
		onUploadError : function(file, errorCode, errorMsg, errorString){ 
                         
			 layer.msg(file.name + ' 上传失败！ ' + 
                   'errorCode: ' + errorCode +
                   'errorMsg:' + errorMsg +
                   'errorString:' + errorString,{icon: 2,time:2000}); 
                           /*  alert(
                               file.name + ' upload failed! ' + 
                               'errorCode: ' + errorCode +
                               'errorMsg:' + errorMsg +
                               'errorString:' + errorString
                           ); */
						}, 
       
       // 在每一个文件上传成功后触发
       onUploadSuccess : function(file, data, response) {
                         
       	layer.msg(  file.name + ' 上传成功！  ',{icon: 1,time:3000});
       	//将 图片信息写到图片展示 
        $('.image-show').attr("src",data);
       	//将图片路径写入隐藏域，后面还用
       	$('.hidden-item-image').val(data);
       	
       },
       
       //返回一个错误，选择文件的时候触发  
       'onSelectError': function (file, errorCode, errorMsg) {  
           switch (errorCode) {  
               case -100:  
                   alert(errorData.err100);  
                   break;  
               case -110:  
                   alert(errorData.err110);  
                   break;  
               case -120:  
                   alert(errorData.err120);  
                   break;  
           
           }  
       }

	});
	
	
});

//上传文件
function upload(id){
  $("#" + id).uploadify("settings", "formData",{});
  $("#" + id).uploadify("upload", "*");
}

//清空列队
function clean(id){
	$("#" + id).uploadify('cancel', '*');
}
//获取上传文件总个数
function getQueueSize(id){
	return $("#"+id).data('uploadify').queueData.queueLength; 
}
</script>
</head>
<body>
<%--引入header --%>
<script type="text/javascript">
<!--
$(function(){
	//退出系统处理
	$('.logout').bind('click',function(){
		layer.confirm('是否退出报名系统？', {
			  btn: ['退出','取消'] 
			}, function(){
				window.location.href="${pageContext.request.contextPath}/apply/show/stuLogin";
			}, function(){
			  
			});
	});
});
//-->
</script>

<%--检测浏览器 --%>
<jsp:include page="../common/browsehappy.jsp"></jsp:include>

<jsp:include page="../home/common/header.jsp"></jsp:include>

<%--S 设置 时间 --%>
<c:set var="nowDate">  
    <fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" type="date"/>  
</c:set>  
<c:set var="XstartDate">  
    <fmt:formatDate value="${dateInfo.startDate}" pattern="yyyy-MM-dd" type="date"/>  
</c:set>  
<c:set var="XendDate">  
    <fmt:formatDate value="${dateInfo.endDate}" pattern="yyyy-MM-dd " type="date"/>  
</c:set>

<%--E 设置 时间 --%>
<!-- 主体 -->
<div class="navi-info">
<img alt="" src="${CTP_APPLY}/img/navi.png">
<a href="javascript:void(0);">新生报名</a>
<span class="time">报名时间:<b>
<fmt:formatDate value="${dateInfo.startDate}" pattern="yyyy-MM-dd"/>
 ~ 
<fmt:formatDate value="${dateInfo.endDate}" pattern="yyyy-MM-dd"/>

</b></span>
<a href="javascript:void(0);" class="logout">退出报名</a>
</div>
<c:choose>

<%-- 后台不开启报名  ，页面提示报名未开始  --%>
<c:when test="${dateInfo.beginApply eq 0 || empty dateInfo}">
<div class="no-begin-apply">

<img alt="" src="${CTP_APPLY}/img/未开启.png"/><br>
<span>系统关闭报名功能</span>
<span>该系统将会在报名时间之后关闭，请注意时间</span>
<span>今年报名时间为：<b>
<fmt:formatDate value="${dateInfo.startDate}" pattern="yyyy-MM-dd"/>
 ~ 
<fmt:formatDate value="${dateInfo.endDate}" pattern="yyyy-MM-dd"/>

</b></span>
</div>

</c:when>


<%-- 后台不开启报名  ，页面禁止显示渲染 --%>
<c:when test="${dateInfo.beginApply eq 1}">


<%-- S  判断是否在报名时间段 --%>

<!-- S 时间段判断  -->
<c:choose>
<c:when test="${nowDate >= XstartDate && nowDate <= XendDate }">


<!--描述：需要填写的报名内容主要包括学生基本信息，监护人基本信息-->
		<div class="applybody-content-tab clearfix">
			<span class="applybody-content-tab-top">招生信息表</span>
			<!--描述：报名内容中的学生基本信息模块-->
			<div class="applybody-content-tab-basicMes">
				<span class="tab-basicMes-top">学生基本信息</span>
				
				<span class="tab-basicMes-stuname"><img src="${CTP_APPLY}/img/RequireField.png" />姓名</span>
				<input type="text" class="tab-basicMes-stuname-input" required="required"/>
				
				<span class="tab-basicMes-stusex"><img src="${CTP_APPLY}/img/RequireField.png" />性别</span>
				
			
				<input type="radio" id="male" name="radio" value="男" class="tab-basicMes-stusex-input1 sex"/><label for="male" class="tab-basicMes-stusex-men">男</label>
				<input type="radio" id="female" name="radio" value="女" class="tab-basicMes-stusex-input2 sex"/><label for="female" class="tab-basicMes-stusex-women">女</label>
				
				<span class="tab-basicMes-nation"><img src="${CTP_APPLY}/img/RequireField.png" />民族</span>
				<input type="text" class="tab-basicMes-nation-input" required="required"/>
				
				<span class="tab-basicMes-idnum"><img src="${CTP_APPLY}/img/RequireField.png" />身份证号</span>
				<input type="text" class="tab-basicMes-idnum-input" required="required"  autocomplete="off"/>
				
				<span class="tab-basicMes-originalsch">原学校名称</span>
				<input type="text" class="tab-basicMes-originalsch-input"/>
				
				<span class="tab-basicMes-address"><img src="${CTP_APPLY}/img/RequireField.png" />现住址</span>
				<input type="text" class="tab-basicMes-address-input" required="required"/>
				
				<span class="tab-basicMes-register"><img src="${CTP_APPLY}/img/RequireField.png" />所在户籍</span>
				<input type="text" class="tab-basicMes-register-input" required="required"/>
			</div>
			<!--描述：报名内容中监护人信息-->
			<div class="applybody-contentn-tab-guardianMes">
				<span class="tab-guardianMes-top">监护人信息</span>
				
				<span class="tab-guardianMes-father-name"><img src="${CTP_APPLY}/img/RequireField.png" />父亲姓名</span>
				<input type="text" class="tab-guardianMes-father-name-input" required="required"/>
				
				<span class="tab-guardianMes-father-idnum"><img src="${CTP_APPLY}/img/RequireField.png" />父亲身份证</span>
				<input type="text" class="tab-guardianMes-father-idnum-input" required="required" autocomplete="off"/>
				
				<span class="tab-guardianMes-father-phonenum">父亲电话</span>
				<input type="text" class="tab-guardianMes-father-phonenum-input" />
				
				<span class="tab-guardianMes-mother-name"><img src="${CTP_APPLY}/img/RequireField.png" />母亲姓名</span>
				<input type="text" class="tab-guardianMes-mother-name-input" required="required"/>
				
				<span class="tab-guardianMes-mother-idnum"><img src="${CTP_APPLY}/img/RequireField.png" />母亲身份证</span>
				<input type="text" class="tab-guardianMes-mother-idnum-input" required="required" autocomplete="off"/>
				
				<span class="tab-guardianMes-mother-phonenum">母亲电话</span>
				<input type="text" class="tab-guardianMes-mother-phonenum-input"/>
		      <div class="clear"></div>
		      <div class="upload-tips-div">
		     <span><img src="${CTP_APPLY}/img/RequireField.png" /> 上传户口本户主页，学生页，房产证页等等</span> <a href="javascript:void(0);" class="upload-a">点击上传</a>  
		     
			      <img alt="" src="" class="image-show">
			      <input type="hidden" class="hidden-item-image" value="">
		      </div>
		     
			</div>
			<div class="tab-message">填写注意：带<img src="${CTP_APPLY}/img/RequireField.png"/>为必填选项，请认真填写，检查无误后再提交，身份证必须经过验证才能提交。</div>
			<div class="tab-message">是否同意  <a href="javascript:void(0);" class="agreement-btn"  style="color:orange; text-decoration:underline;" title="点击查看协议详细内容">报名协议</a>       <input type="radio" id="s1" name="agree" value="yes" checked> <label for="s1">同意</label>&nbsp;&nbsp;<input type="radio" name="agree" value="no" id="s2"> <label for="s2">不同意</label></div>
			<div class="applybody-content-tab-submit">
				<button  class="submit-btn">提交报名</button>
			</div>
	</div>
</c:when>

<c:otherwise>
<div class="no-begin-apply">

<img alt="" src="${CTP_APPLY}/img/sys-close.png">
<br>
<br>

<span class="no-begin-tips">不在报名时间段，系统暂不开放</span>
<br>
<br>

<br>
<br>
</div>

</c:otherwise>
</c:choose>

<!--E 时间段判断   -->	
	
</c:when>
</c:choose>
<div class="clear"></div>

<%--E 主体 --%>


<%--引入footer --%>
<jsp:include page="../home/common/footer.jsp"></jsp:include>	
	
	
	
<!-- 弹窗 -->
<div class="mark"></div>
<div class="dialog">
     
        <div class="dialog-close" onclick="$('.mark,.dialog').hide();"></div> 
        <div class="dialog-title">图片上传</div> 
        <div class="dialog-content"> 
            <input type="hidden" class="hide-checked-id" value="">
            <div class="opt-div">
            <div align="center">
            <h4>选择文件后文件自动上传</h4>
            <h5>可以重复上传，最多5次，以最后一次为准</h5>
            </div>
			
			<div class="dialog-content">
		    <div align="center"><span id="uploadify"></span></div>
			<div id="fileQueue"></div>     
			<br>
			 <div class="dialog-link">
		<!--     <a href="javascript:upload('uploadify')"  class="start-upload">手动上传</a>&nbsp; 
 -->
	       	<a href="javascript:clean('uploadify')" class="clean-queue">清空列表</a>
		  </div>
		  </div>
           
        </div>
</div> 
</div>
    
 <!--协议  -->   
    <!--弹窗-->
<div class="modalDialog">
	<div>
		<a href="javascript:void(0);" title="关闭" class="close agreement-close" >X</a>
		<span class="openModal-title">万科城小学-报名协议</span>
        <div class="openModal-agreement">
            
            <div class="agreement-content">
            
            欢迎您使用腾讯的服务！<br>
　　为使用腾讯的服务，您应当阅读并遵守《腾讯服务协议》（以下简称“本协议”）和《QQ号码规则》。请您务必审慎阅读、充分理解各条款内容，特别是免除或者限制责任的条款、管辖与法律适用条款，以及开通或使用某项服务的单独协议。限制、免责条款可能以黑体加粗或加下划线的形式提示您重点注意。除非您已阅读并接受本协议所有条款，否则您无权使用腾讯提供的服务。您使用腾讯的服务即视为您已阅读并同意上述协议的约束。<br>
　　如果您未满18周岁，请在法定监护人的陪同下阅读本协议，并特别注意未成年人使用条款。<br>

　　一、【协议的范围】<br>
　　1.1本协议是您与腾讯之间关于用户使用腾讯相关服务所订立的协议。“腾讯”是指腾讯公司及其相关服务可能存在的运营关联单位。“用户”是指使用腾讯相关服务的使用人，在本协议中更多地称为“您”。<br>
　　1.2本协议项下的服务是指腾讯向用户提供的包括但不限于即时通讯、网络媒体、互联网增值、互动娱乐、电子商务和广告等产品及服务（以下简称“本服务”）。<br>
        
        
                
            欢迎您使用腾讯的服务！<br>
　　为使用腾讯的服务，您应当阅读并遵守《腾讯服务协议》（以下简称“本协议”）和《QQ号码规则》。请您务必审慎阅读、充分理解各条款内容，特别是免除或者限制责任的条款、管辖与法律适用条款，以及开通或使用某项服务的单独协议。限制、免责条款可能以黑体加粗或加下划线的形式提示您重点注意。除非您已阅读并接受本协议所有条款，否则您无权使用腾讯提供的服务。您使用腾讯的服务即视为您已阅读并同意上述协议的约束。<br>
　　如果您未满18周岁，请在法定监护人的陪同下阅读本协议，并特别注意未成年人使用条款。<br>


　 
        
            </div> 
            
           
        </div>       
		
	</div>
</div>
    
	
</body>
</html>

<script type="text/javascript">

$(function(){
	
	$(".image-show").zoomify();
	
	$(".upload-a").bind("click",function(){
		$('.mark,.dialog').show();
		
	});
	
	$(".agreement-btn").bind("click",function(){
		$('.modalDialog').show();
		$('html').addClass('overHiden');
	});

	$('.agreement-close').bind('click',function(){
		$('.modalDialog').hide();
		$('html').removeClass('overHiden');
	});
	
	
});
</script>