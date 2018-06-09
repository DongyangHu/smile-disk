define(['jquery','json2'],function(){
	var ajaxUtil = {};
	var baseUrl = 'http://yiyang66.top';
	
	/*session状态*/
	ajaxUtil.checkSessionStatus = function(data){
		//没有权限
		if(data.code == 'e10002'){
			return false;
		}
		return true;
	}
	
	/*JQuery get方法*/
	ajaxUtil.doGet = function(url,fn,async){
		if(typeof async != 'boolean'){
			async = true;
		}
		$.ajax({
			type:"get",
			url:url,
			async:async,
			success:function(result){
				if(ajaxUtil.checkSessionStatus(result)){
					fn(result);
				}else{
					var serverUrl = result.serverUrl;
					if(null == serverUrl || '' == serverUrl || 'undefined' == serverUrl){
						window.location.replace(baseUrl);
					}else{
						window.location.replace(serverUrl);
					}
				}
			},
			error:function(){
				fn();
			}
		});
	};
	
	/*JQuery post方法*/
	ajaxUtil.doPost = function(url,fn,data,async){
		if(typeof async != 'boolean'){
			async = true;
		}
		$.ajax({
			type:"post",
			contentType:"application/json; charset=UTF-8",
			url:url,
			data:JSON.stringify(data),
			async:async,
			success:function(result){
				if(ajaxUtil.checkSessionStatus(result)){
					fn(result);
				}else{
					var serverUrl = result.serverUrl;
					if(null == serverUrl || '' == serverUrl || 'undefined' == serverUrl){
						window.location.replace(baseUrl);
					}else{
						window.location.replace(serverUrl);
					}
				}
			},
			error:function(){
				fn();
			}
		});
	};
	

	
	return ajaxUtil;
});