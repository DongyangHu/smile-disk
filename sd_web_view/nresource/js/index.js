//退出
function loginOut(){
	layui.use('layer',function(){
		layer = layui.layer;
		layer.confirm("您确认退出吗？",{
			time: 0,
			area: '120px',
			btnAlign: 'c',
			btn: ['退出','取消']
		},
		function(index){
			layer.close(index);
			var url = '/sd_sso/login/out';
			require(['ajaxUtil'],function(ajaxUtil){
				ajaxUtil.doGet(url,function(data){
					if(0 == data.code){
						layer.msg('期待下次与您相聚。',{
							icon: 5,
							time: 2000,
							end: function(){
								location.replace('/');
							}
						});
					}else{
						layer.msg(data.message,{icon: 5});
					}
				});
			});
		});
	});
}
//改变主页面内容
function changeContent(url, fileType){
	url = url + "?time="+new Date().getTime() + "&fileType=" + fileType;
	$("#mainContent").attr('src',url);
}

//改变页面左侧列表内容
function changeLeft(parentModuleId){
	$('#leftItems').html("");
	require(['ajaxUtil'],function(ajaxUtil){
		//左侧菜单
		var url = '/sd_baseData/userInfo/getUserModuleList?parentModuleId=' + parentModuleId;
		ajaxUtil.doGet(url,function(data){
			if(data.code == 0){
				var moduleList = data.moduleList;
				dataSerialize(moduleList);
				for(var i = 0; i < moduleList.length; i++){
					var module = moduleList[i];
					if(i == 0){
						var item = "<li class='layui-nav-item layui-nav-itemed'><a href= 'javascript:;' >"+ module.moduleName +"</a>";
					}else{
						if(module.moduleUrl != null){
							var item = "<li class='layui-nav-item'><a href= \""+module.moduleUrl+"\" >"+ module.moduleName +"</a>";
						}else{
							var item = "<li class='layui-nav-item'><a href= 'javascript:;'>"+ module.moduleName +"</a>";
						}
					}
					var childUrl = '/sd_baseData/userInfo/getUserModuleList?parentModuleId=' + module.moduleId;
					ajaxUtil.doGet(childUrl, function(childData){
						var childModuleList = childData.moduleList;
						dataSerialize(childModuleList);
						var childItem = "<dl class='layui-nav-child'>";
						for(var j = 0; j < childModuleList.length; j++){
							var childModule = childModuleList[j];
							childItem += "<dd><a id='childModule_"+ j +"' href= \""+ childModule.moduleUrl +"\" >"+ childModule.moduleName +"</a></dd>"
						}
						childItem += "</dl>"
						item += childItem;
						item += "</li>";
						$('#leftItems').append(item);
						if(i == 0){
							$("#childModule_0")[0].click();
						}
					},false);
				}
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message, {icon: 5});
				});	
			}
		});
	});
}

$(function(){
	require(['ajaxUtil'],function(ajaxUtil){
		//顶部导航
		var url = '/sd_baseData/userInfo/getUserModuleList'
		ajaxUtil.doGet(url,function(data){
			if(data.code == 0){
				var moduleList = data.moduleList;
				dataSerialize(moduleList);
				for(var i = 0; i < moduleList.length; i++){
					var module = moduleList[i];
					var item = "<li class='layui-nav-item'><a id='topModule_"+ i +"' href= \""+ module.moduleUrl +"\" >"+ module.moduleName +"</a></li>";
					$('#topItems').append(item);
				}
				$("#topModule_0")[0].click();
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message, {icon: 5});
				});	
			}
		});
		var url1 = '/sd_baseData/userInfo/user';
		ajaxUtil.doGet(url1,function(data){
			if(data.code == 0){
				var userNikename = data.user.userNikename;
				$("#userNikename").html(userNikename);
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message, {icon: 5});
				});	
			}
		});
	});
	
});

/**
 * 根据模块的orderby进行排序
 * @param {Array} moduleList
 */
function dataSerialize(moduleList) {
	for(var i = 0; i < moduleList.length; i++){
		for(var j = 0; j < moduleList.length - 1 - i; j++){
			if(moduleList[j].moduleOrderby > moduleList[j+1].moduleOrderby){
				var temp = moduleList[j];
				moduleList[j] = moduleList[j+1];
				moduleList[j+1] = temp;
			}
		}
	}
}

/**
 * 分享
 */
function openShareWindow(){
	layui.use('layer', function(){
		layer.open({
			type: 2,
			title: "分享",
			area: ['800px', '500px'],
			shadeClose: true,
			maxmin: true,
			content: "/fileManage/share.html"
		});
	});
}


/**
 * 后台管理
 */
function openAdminWindow(){
	layui.use('layer', function(){
		layer.open({
			type: 2,
			title: "后台管理",
			area: ['800px', '500px'],
			shadeClose: true,
			maxmin: true,
			content: "/sysman/admin.html"
		});
	});
}
