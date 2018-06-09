define(['ajaxUtil'],function(ajaxUtil){
	var template = {};
	/**
	 * 导入模板到页面
	 * @param {Object} url 模板绝对路径
	 * @param {Object} targetElement 目标元素
	 * @param {Object} data 导入数据
	 */
	template.importSDM = function(url,targetElement,fn,data){
		var templateUrl;
		var target;
		var impotData;
		if(url.substr(0,1) == '/'){
			templateUrl = url;
		}else{
			console.log("The url must be absolute path.");
		}
		if(targetElement.substr(0,1) == '#'){
			target = targetElement;
		}else{
			target = '#' + targetElement;
		}
		impotData = JSON.parse(JSON.stringify(data));
		
		ajaxUtil.doGet(templateUrl,function(content){
			$(target).css('display','none');
			$(target).html('');
			$(target).append(content);
			var vueObject = new Vue({
				el:target,
				data:impotData
			});
			fn(vueObject);
			$(target).css('display','');
		});
	};
	
	return template;
});