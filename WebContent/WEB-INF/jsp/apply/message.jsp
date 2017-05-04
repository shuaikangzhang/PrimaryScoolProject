<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/mytag.tld" prefix="myTag" %> 
<!DOCTYPE html>
<html>
<head>
<c:set var="CTP" value="${pageContext.request.contextPath}"></c:set>
<c:set var="CTP_APPLY" value="${pageContext.request.contextPath}/resources/apply"></c:set>
<c:set var="CTP_HOME" value="${pageContext.request.contextPath}/resources/home"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../home/common/meta.jsp" flush="true"/>
<title>新生报名信息填写</title>

<link rel="stylesheet" href="${CTP_HOME}/css/header.css" />
<link rel="stylesheet" href="${CTP_HOME}/css/footer.css" />
<link rel="stylesheet" href="${CTP_APPLY}/css/applyMessage.css"/>
<link rel="stylesheet" href="${CTP_APPLY}/css/common.css"/>
<link href="${CTP}/resources/admin/css/magic-check.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CTP_HOME}/js/lib/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${CTP_HOME}/js/module/common.js" ></script>
<script type="text/javascript" src="${CTP}/resources/common/js/extends/layer-2.4/layer.js"></script>
<script type="text/javascript">
var CTPPATH="${pageContext.request.contextPath}";

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
   })
   
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
					
			}else{
				layer.tips(CheckIdCard(str), $(this));	
				if(!CheckIdCard(str).match("Error")){
					ok_mother_card=true;
				}
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
		
		if(stu_sex!=null){
			ok_stu_sex=true;
		}
        
		
		if(ok_stu_name==true && ok_stu_nation==true && ok_stu_sex==true && ok_stu_card==true && ok_stu_address==true && ok_stu_register==true && ok_father_name==true && ok_father_card==true && ok_mother_name==true && ok_mother_card==true){
			
			//注册
			//start
			 //注册
    		$.ajax({
				type:'post',
				dataType:'json',
				url:CTPPATH+"/apply/index/saveApplyInfo",
				data:{"uid":"${uid}","stuName":stu_name,"stuNation":stu_nation,"stuSex":stu_sex,"stuIdNum":stu_card,"originalSchool":original_school,"address":stu_address,"register":stu_register,"fatherName":father_name,"fatherIdNum":father_card,"fatherPhone":father_phone,"motherName":mother_name,"motherIdNum":mother_card,"motherPhone":mother_phone},
			
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
						layer.msg('恭喜！登录成功',{time:4000});
						window.location.href=CTPPATH+"/apply/index/status?id="+data;
					}else if(data==0){
						//代表用户名不可用
						layer.msg('*抱歉，登录失败，用户名或者密码错误！',{time:4000});
						
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
<jsp:include page="../home/common/header.jsp"></jsp:include>

<!-- 主体 -->
<div class="navi-info">
<img alt="" src="${CTP_APPLY}/img/navi.png">
<a href="javascript:void(0);">新生报名</a>
<span class="time">报名时间:<b>2016年  9月2日-2016年 9月30日</b></span>
<a href="javascript:void(0);" class="logout">退出报名</a>
</div>
<!--描述：需要填写的报名内容主要包括学生基本信息，监护人基本信息-->
		<div class="applybody-content-tab">
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
				<input type="text" class="tab-basicMes-idnum-input" required="required"/>
				
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
				<input type="text" class="tab-guardianMes-father-idnum-input" required="required"/>
				
				<span class="tab-guardianMes-father-phonenum">父亲电话</span>
				<input type="text" class="tab-guardianMes-father-phonenum-input" />
				
				<span class="tab-guardianMes-mother-name"><img src="${CTP_APPLY}/img/RequireField.png" />母亲姓名</span>
				<input type="text" class="tab-guardianMes-mother-name-input" required="required"/>
				
				<span class="tab-guardianMes-mother-idnum"><img src="${CTP_APPLY}/img/RequireField.png" />母亲身份证</span>
				<input type="text" class="tab-guardianMes-mother-idnum-input" required="required"/>
				
				<span class="tab-guardianMes-mother-phonenum">母亲电话</span>
				<input type="text" class="tab-guardianMes-mother-phonenum-input"/>
			</div>
			<div class="tab-message">填写注意：带<img src="${CTP_APPLY}/img/RequireField.png"/>为必填选项，请认真填写，检查无误后再提交，一经提交无法修改。</div>
			<div class="applybody-content-tab-submit">
				<a href="javascript:void(0);" class="submit-btn">提交报名</a>
			</div>
	</div>

<div class="clear"></div>

<%--E 主体 --%>


<%--引入footer --%>
<jsp:include page="../home/common/footer.jsp"></jsp:include>	
	
</body>
</html>