require.config({
    paths: {
    	jquery:"/appframe/kernel/jquery-3.2.1",
    	json2:"/appframe/component/open/json2",
    	ajaxUtil:"/appframe/component/private/ajaxUtil",
    	template:"/appframe/component/private/template",
    	jqueryI18n:"/appframe/component/open/jquery.i18n.properties",
    	cookie:"/appframe/component/open/jquery.cookie",
    	i18n:"/appframe/component/private/i18n",
    	sdI18n:"/appframe/component/private/sdI18n",
    	animate:"/appframe/component/private/animate",
    	verify_zh_CN:"/appframe/component/open/verify/verify_zh_CN",
    	verify_en_US:"/appframe/component/open/verify/verify_en_US",
    	uri:"/appframe/component/open/uri"
    },
    shim: {
    	verify_zh_CN:"/appframe/component/open/verify/verify_zh_CN",
    	verify_en_US:"/appframe/component/open/verify/verify_en_US",
    	uri:"/appframe/component/open/uri"
    }
})