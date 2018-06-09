define(['i18n','cookie'],function(i18n,cookieUtil){
	var sdI18n = {};
	
	sdI18n.getSdOpI18n = function(){
		var settings = {};
		var lang = cookieUtil.cookie('userLanguage');
	    if(typeof (lang) == 'undefined' || lang == null) {
	    	lang = 'zh_CN';
	    }
	    settings.language = lang;
	    settings.name = "sd_op";
	    i18n.init(settings);

		return i18n;	    
	}
	
	return sdI18n;
});
