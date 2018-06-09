var i18n;
var submiteFlag = {};
submiteFlag.userCode = '0';
submiteFlag.userPwd = '0';
submiteFlag.reUserPwd = '0';
submiteFlag.userEmail = '0';
submiteFlag.validate = '0';
var lang = '';
var loadingIndex;

$(function(){
	require(['sdI18n','cookie'],function(sdI18n,cookieUtil){
		i18n = sdI18n.getSdOpI18n();
		lang = cookieUtil.cookie('userLanguage');
	    if(typeof (lang) == 'undefined' || lang == null || lang == '') {
	    	lang = 'zh_CN';
	    }
		//多语言配置
		$("#registerTitleH4").html(i18n.prop('register_userRegister'));
		$("#userCode").attr('placeholder',i18n.prop('register_userCode'));
		$("#userPwd").attr('placeholder',i18n.prop('register_password'));
		$("#reUserPwd").attr('placeholder',i18n.prop('register_rePassword'));
		$("#userEmail").attr('placeholder',i18n.prop('register_email'));
		$("#checkCode").attr('placeholder',i18n.prop('register_checkCode'));
		$("#registerButton").html(i18n.prop('register_registerButton'));
	});
	registerMsg();
	sliderCheck();
});

function registerMsg(){
	require(['animate'],function(){
		//用户名
		$("#userCode").focus(function(){
			$("#userCodeMsgSpan").html(i18n.prop("register_userCodeMsgSpan"));
			$("#userCodeMessage").removeClass('hidden');
			$("#userCodeMessage").animateCss('animated fadeIn');
		});
		$("#userCode").blur(function(){
			var userCode = $("#userCode").val();
			$("#userCodeMessage").addClass('hidden');
			if(null == userCode || '' == userCode || undefined == userCode){
				$("#userCode").removeClass('register-input-group-main-right');
				$("#userCode").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userCodeTips_empty"),'#userCode',{
						tips: [2, '#3595CC'],
  						time: 2000
					});
				});
				submiteFlag.userCode = '0';
			}else if(userCode.length > 16 || userCode.length < 4){
				$("#userCode").removeClass('register-input-group-main-right');
				$("#userCode").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userCodeTips_lengthError"),'#userCode',{
						tips: [2, '#3595CC'],
  						time: 2000
					});
				});
				submiteFlag.userCode = '0';
			}else{
				//字母、字母数字组合，不能为纯数字
				var regex = /^(?![0-9]+$)[0-9A-Za-z]{4,16}$/;
				var strArray = userCode.match(regex);
				if(null == strArray || 0 == strArray.length || undefined == strArray){
					$("#userCode").removeClass('register-input-group-main-right');
					$("#userCode").addClass('register-input-group-main-error');
					layui.use('layer',function(){
						layer = layui.layer;
						layer.tips(i18n.prop("register_userCodeTips_formatError"),'#userCode',{
							tips: [2, '#3595CC'],
  							time: 2000
						});
					});
					submiteFlag.userCode = '0';
					return;
				}else{
					for(var i = 0; i < strArray.length; i++){
						if(strArray[i].length != userCode.length){
							$("#userCode").removeClass('register-input-group-main-right');
							$("#userCode").addClass('register-input-group-main-error');
							layui.use('layer',function(){
								layer = layui.layer;
								layer.tips(i18n.prop("register_userCodeTips_formatError"),'#userCode',{
									tips: [2, '#3595CC'],
  									time: 2000
								});
							});
							submiteFlag.userCode = '0';
							return;
						}else{
							checkUserCode();
						}
					}
				}
			}
		});
		//第一次密码框
		$("#userPwd").focus(function(){
			$("#userPwdMsgSpan1").html(i18n.prop("register_userPwdMsgSpan1"));
			$("#userPwdMsgSpan2").html(i18n.prop("register_userPwdMsgSpan2"));
			$("#userPwdMessage").removeClass('hidden');
			$("#userPwdMessage").animateCss('animated fadeIn');
		});		
		$("#userPwd").blur(function(){
			var userPwd = $("#userPwd").val();
			$("#userPwdMessage").addClass('hidden');
			//长度
			if(null == userPwd || '' == userPwd || undefined == userPwd){
				$("#userPwd").removeClass('register-input-group-main-right');
				$("#userPwd").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userPwdTips_empty"),'#userPwd',{
						tips: [2, '#3595CC'],
  						time: 2000
					});
				});
				submiteFlag.userPwd = '0';
			}else if(userPwd.length < 6 || userPwd.length > 16){
				$("#userPwd").removeClass('register-input-group-main-right');
				$("#userPwd").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userPwdTips_lengthError"),'#userPwd',{
						tips: [2, '#3595CC'],
  						time: 2000
					});
				});
				submiteFlag.userPwd = '0';
			}else{
				var regex = /^[\u4e00-\u9fa5]{1,}|[\s]{1,}$/;
				if(regex.test(userPwd)){
					$("#userPwd").removeClass('register-input-group-main-right');
					$("#userPwd").addClass('register-input-group-main-error');
					layui.use('layer',function(){
						layer = layui.layer;
						layer.tips(i18n.prop("register_userPwdTips_formatError"),'#userPwd',{
							tips: [2, '#3595CC'],
	  						time: 2000
						});
					});
					submiteFlag.userPwd = '0';
				}else{
					$("#userPwd").removeClass('register-input-group-main-error');
					$("#userPwd").addClass('register-input-group-main-right');
					submiteFlag.userPwd = '1';
				}
			}
		});
		//二次密码框
		$("#reUserPwd").focus(function(){
			$("#reUserPwdMsgSpan").html(i18n.prop("register_reUserPwdMsgSpan"));
			$("#reUserPwdMessage").removeClass('hidden');
			$("#reUserPwdMessage").animateCss('animated fadeIn');
		});
		$("#reUserPwd").blur(function(){
			var userPwd = $("#userPwd").val();
			var reUserPwd = $("#reUserPwd").val();
			$("#reUserPwdMessage").addClass('hidden');
			if(null == userPwd || '' == userPwd || undefined == userPwd){
				$("#userPwd").removeClass('register-input-group-main-right');
				$("#userPwd").addClass('register-input-group-main-error');
				$("#reUserPwd").removeClass('register-input-group-main-right');
				$("#reUserPwd").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userPwdTips_empty"),'#userPwd',{
						tips: [2, '#3595CC'],
  						time: 2000
					});
				});
				submiteFlag.reUserPwd = '0';
			}else if(reUserPwd == userPwd){
				$("#reUserPwd").removeClass('register-input-group-main-error');
				$("#reUserPwd").addClass('register-input-group-main-right');
				submiteFlag.reUserPwd = '1';
			}else{
				$("#reUserPwd").removeClass('register-input-group-main-right');
				$("#reUserPwd").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_reUserPwdTips_diffrent"),'#reUserPwd',{
						tips: [2, '#3595CC'],
	  					time: 2000
					});
				});
				submiteFlag.reUserPwd = '0';
			}
		});
		//电子邮箱
		$("#userEmail").focus(function(){
			$("#userEmailMsgSpan").html(i18n.prop("register_userEmailMsgSpan"));
			$("#userEmailMessage").removeClass('hidden');
			$("#reUserPwdMessage").animateCss('animated fadeIn');
		})
		$("#userEmail").blur(function(){
			var userEmail = $("#userEmail").val();
			$("#userEmailMessage").addClass('hidden');
			var regex = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/i;
			if(null == userEmail || '' == userEmail || undefined == userEmail){
				$("#userEmail").removeClass('register-input-group-main-right');
				$("#userEmail").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userEmailTips_empty"),'#userEmail',{
						tips: [2, '#3595CC'],
						time: 2000
					});
				});
				submiteFlag.userEmail = '0';				
			}else if(regex.test(userEmail)){
				$("#userEmail").removeClass('register-input-group-main-error');
				$("#userEmail").addClass('register-input-group-main-right');
				submiteFlag.userEmail = '1';
			}else{
				$("#userEmail").removeClass('register-input-group-main-right');
				$("#userEmail").addClass('register-input-group-main-error');
				layui.use('layer',function(){
					layer = layui.layer;
					layer.tips(i18n.prop("register_userEmailTips_formatError"),'#userEmail',{
						tips: [2, '#3595CC'],
						time: 2000
					});
				});
				submiteFlag.userEmail = '0';
			}
		});
	});
}

//滑动验证
function sliderCheck (){
			require(['cookie'],function(cookieUtil){
			var lang = cookieUtil.cookie('userLanguage');
			if(null == lang || undefined == lang){
				lang = 'zh_CN';
			}
			require(['verify_' + lang],function(){
				$('#checkCode').slideVerify({
					type : 1,		//类型
					vOffset : 5,	//误差量，根据需求自行调整
					barSize : {
						width : '260px',
						height : '34px',
					},
					ready : function() {
					},
					success : function() {
						submiteFlag.validate = '1';
					},
					error : function() {
				    	submiteFlag.validate = '0';
					}
				
				});
			});
		
		
		});	
}

//验证userCode
function checkUserCode(){
	var userCode = $("#userCode").val();
	var url = '/sd_baseData/userInfo/checkUserCode?userCode='+userCode+'&languageType='+lang;
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doGet(url,function(data){
			if( 0 == data.code){
				if(data.flag){
					$("#userCode").removeClass('register-input-group-main-error');
					$("#userCode").addClass('register-input-group-main-right');
					submiteFlag.userCode = 1;
				}else{
					$("#userCode").removeClass('register-input-group-main-right');
					$("#userCode").addClass('register-input-group-main-error');
					layui.use('layer',function(){
						layer = layui.layer;
						layer.tips(i18n.prop("register_userCodeTips_userExists"),'#userCode',{
							tips: [2, '#3595CC'],
	  						time: 2000
						});
					});
					submiteFlag.userCode = '0';
				}
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message);
				});
			}
		});
	});
}
//注册
function submiteRegist(){
	for(var i in submiteFlag){
		if('validate' == i){
			
		}else if(0 == submiteFlag[i]){
			$("#"+i).removeClass('register-input-group-main-right');
			$("#"+i).addClass('register-input-group-main-error');
			layui.use('layer',function(){
				layer = layui.layer;
				layer.msg("输入有误");
			});
			return;
		}
	}
	var url = '/sd_baseData/userInfo/userRegister';
	var params = {};
	params.userCode = $("#userCode").val();
	var password = $("#userPwd").val();
	params.password = sdRSAEncrypt.encrypt(password);
	params.email = $("#userEmail").val();
	params.languageType = lang;
	require(['ajaxUtil'],function(ajaxUtil){
		layui.use('layer',function(){
			layer = layui.layer;
			loadingIndex = layer.load(0, {shade:0.3});
		});
		ajaxUtil.doPost(url,function(data){
			layer.close(loadingIndex);
			if(0 == data.code){
				submiteFlag = {};
				layui.use('layer',function(){
					layer = layui.layer;
					layer.alert('注册成功，请前往您的邮箱，进行验证。为了您的账户安全，必须验证之后才能登陆。感谢您的配合。', {
						icon: 6,
						end: function () {
							window.parent.layer.closeAll();
						}
					});
				});
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg("注册失败");
				});
			}
		},params);
	});
}