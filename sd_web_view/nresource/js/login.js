var size;
var timeUtil;
var navNum = 1;
var lang = 'zh_CN';
var loadingIndex;

var registerPageTitle = '';
var findPwdPageTitle = '';
$(function(){
	preLoadImg();
	logoImgDynamic(1);
	size = $(".bg-index-banner").length;
	timeUtil = setInterval("startNav();",6000);
	initShowLang();
});

//初始化界面多语言显示
function initShowLang(){
	require(['sdI18n','cookie'], function(sdI18n,cookieUtil) {
		var i18n = sdI18n.getSdOpI18n();
		
		$("#languageType").html(i18n.prop("login_languageType"));
		$("#loginTitleH4").html(i18n.prop("login_loginTitleH4"));
		$("#register").html(i18n.prop("login_signUp"));
		$("#forgetPwd").html(i18n.prop("login_forgetPassword"));
		$("#loginButton").html(i18n.prop("login_signIn"));
		$("#userAccountText").attr('placeholder',i18n.prop("login_userAccountText"));
		$("#userPasswordText").attr('placeholder',i18n.prop("login_userPasswordText"));
		$("#indexShowMessageTop1").html(i18n.prop("login_indexShowMessageTop1"));
		$("#indexShowMessageButtom1").html(i18n.prop("login_indexShowMessageButtom1"));
		$("#indexShowMessageTop2").html(i18n.prop("login_indexShowMessageTop2"));
		$("#indexShowMessageButtom2").html(i18n.prop("login_indexShowMessageButtom2"));
		$("#indexShowMessageTop3").html(i18n.prop("login_indexShowMessageTop3"));
		$("#indexShowMessageButtom3").html(i18n.prop("login_indexShowMessageButtom3"));
		$("#atrMessageLi1").html(i18n.prop("login_atrMessageLi1"));
		$("#atrMessageLi2").html(i18n.prop("login_atrMessageLi2"));
		$("#atrMessageLi3").html(i18n.prop("login_atrMessageLi3"));
		$("#atrMessageLi4").html(i18n.prop("login_atrMessageLi4"));
		registerPageTitle = i18n.prop("login_registerPageTitle");
		findPwdPageTitle = i18n.prop("login_findPwdPageTitle");
	});
}
/*logo动态效果*/
function logoImgDynamic(flag){
	require(['animate'],function(){
		if(flag == 1){
			$("#logoImg").animateCss('animated slideInDown');
			$("#logoImg").mouseover(function(){
				$("#logoImg").css('background','url(/nresource/img/logo2.png) no-repeat scroll 0 transparent');
				$("#logoImg").animateCss('animated pulse');
			});
			$("#logoImg").mouseleave(function(){
				$("#logoImg").css('background','url(/nresource/img/logo1.png) no-repeat scroll 0 transparent');
			});
		}
	});
}

//轮播图跳转指定图片
function goNavNum(num){
	require(['animate'],function(){
		$("#bg-index-banner" + num).show();
		$("#bg-banner-img").animateCss('fadeIn');
		$("#bg-banner-img").addClass('bg-index-banner' + num);
		$("#focus-item" + num).addClass('current');
		for(var i = 1; i < size + 1; i++){
			if(num == i){
				continue;
			}else{
				$("#bg-index-banner" + i).hide();
				$("#focus-item"+i).removeClass('current');
				$("#bg-banner-img").removeClass('bg-index-banner' + i);
			}
		}
	});
}

//开始轮播
function startNav(){
	goNavNum(navNum%3 + 1);
	navNum++;
}

//登录
function login(){
	require(['ajaxUtil','sdI18n'],function(ajaxUtil,sdI18n){
		var params = {};
		var userCode = $("#userAccountText").val();
		var password = $("#userPasswordText").val();
		if(null == userCode || '' == userCode || userCode == 'undefined'){
			layui.use('layer',function(){
				layer = layui.layer;
				layer.alert("请输入用户名!");
			});
			return false;
		}
		if(null == password || '' == password || password == 'undefined'){
			layui.use('layer',function(){
				layer = layui.layer;
				layer.alert("请输入密码!");
			});
			return false;
		}
		
		
		params.userCode = userCode;
		params.password = sdRSAEncrypt.encrypt(password);
		params.languageType = lang;
		var url = '/sd_sso/login';
		layui.use('layer',function(){
			layer = layui.layer;
			loadingIndex = layer.load(0, {shade:0.3});
		});
		ajaxUtil.doPost(url,function(data){
			layer.close(loadingIndex);
			if(0 == data.code){
				if(data.type == 1){
					location.replace('/sysman/index.html');
				}else{
					layui.use('layer',function(){
						layer = layui.layer;
						layer.alert(data.message, {
							icon: 5,
							end: function () {
								$("#userAccountText").val('');
								$("#userPasswordText").val('');
							}
						});
					});					
				}
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message, {icon: 5});
				});
			}
		},params);
		
	});
	
}
//存语言类型的公共方法 type :zh_CN en_US
function saveCookie(type){
	 require(['cookie'], function(cookieUtil) {
	   		var option = {};
	   		var d = new Date();
	   		d.setDate(d.getDate()+30);
	   		option['expires'] = d;
	   		option['path'] = "/";
		    cookieUtil.cookie('userLanguage',type,option);
		   
   });
}
//语言改变
function changeLang(languageType){
	lang = languageType;
	saveCookie(languageType);
	initShowLang();
}
//预加载图片
function preLoadImg() {
	var imgUrlList = [];
	imgUrlList.push('/nresource/img/bg11.jpg');
	imgUrlList.push('/nresource/img/bg22.jpg');
	imgUrlList.push('/nresource/img/bg33.jpg');
	for(var i = 0; i < imgUrlList.length; i++){
		var img = new Image();
		img.src = imgUrlList[i];
	}
} 

//打开注册页面
function showRegister(){
    layui.use('layer', function(){
  		layer = layui.layer;
  		layer.open({
    		type: 2,
    		title: registerPageTitle,
    		//maxmin: true,
    		//shadeClose: true, //点击遮罩关闭层
    		area : ['600px' , '460px'],
    		content: '/baseData/register.html',
    		id: 'userRegister'
    	});
  	});
}

//打开找回密码界面
function showFindPwd(){
	    layui.use('layer', function(){
  		layer = layui.layer;
  		layer.open({
    		type: 2,
    		title: findPwdPageTitle,
    		//maxmin: true,
    		//shadeClose: true, //点击遮罩关闭层
    		area : ['480px' , '320px'],
    		content: '/baseData/findPwd.html',
    		id: 'findPwd'
    	});
  	});
}



//登录文本框校验
