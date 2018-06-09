/**
 * @desc多语言插件
 * @author HuDongyang
 * @version 0.0.1
 * @date 2018.01.29
 */
define(['jqueryI18n'],function(){
	var i18n = {};
	
	/*通过配置参数初始化i18n*/
	i18n.init = function(settings){
		var defaults = {
			name: 'sd_op',
			language: 'zh_CN',
			path: '/nresource/lang/',
			mode: 'map',
			cache: true,
			encoding: 'UTF-8',
			async: false,
			checkAvailableLanguages: false,
			callback: null,
			suffix: '.js'
		};
		settings = $.extend(defaults, settings);
		$.i18n.properties(settings);
	}
	
	/*获取单个key的value*/
	i18n.prop = function(keyStr){
		var targetStr = '';
		
		targetStr = $.i18n.prop(keyStr);
		return targetStr;
	}
	
	return i18n;
});