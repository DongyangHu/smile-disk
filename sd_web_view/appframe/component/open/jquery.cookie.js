/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	define(factory);
}(function () {
	/**
   * 组件名:cookie<br/>
   * 组件功能：cookie的工具类
   * @exports cookieUtil
   * @class
   */
	var cookieUtil={};
	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	 /**
   * @description 写入cookie
   * @param {string} key 写入的cookie的键
   * @param {string} value 写入的cookie的值
   * @param {json} options 写入cookie的配置项，如expires：定义cookie的存活时间，以天为单位，默认为回话cookie
   *	path：定义cookie的有效路径，默认为/ domain:定义有效的域，默认为创建该页面的域 secure:该cookie是否需要https传送，默认为false
   * @example
   *	1、创建一个有效期为7天的cookie：$.cookie('name', 'value', { expires: 7, path: '/' });
   *2、读取cookie $.cookie('name'); // => "value" $.cookie('nothing'); // => undefined 
   *3、读取所有的cookie $.cookie(); // => { "name": "value" }L
   */
	cookieUtil.cookie= function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setTime(+t + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {};

		// To prevent the for loop in the first place assign an empty array
		// in case there are no cookies at all. Also prevents odd result when
		// calling $.cookie().
		var cookies = document.cookie ? document.cookie.split('; ') : [];

		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decode(parts.shift());
			var cookie = parts.join('=');

			if (key && key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};
	
	$.cookie=config=cookieUtil.cookie;;
	config.defaults = {};
  /**
   * @description 删除指定的cookie
   * @param {string} key 删除cookie的key
   * @param {json} options 写入cookie的配置项，如expires：定义cookie的存活时间，以天为单位，默认为回话cookie
   * @example	 
   *	$.cookie('name', 'value', { path: '/' });
   *$.removeCookie('name'); // => false
   *$.removeCookie('name', { path: '/' }); // => true
   * @return {string}  删除成功返回true，失败为false
   */
	cookieUtil.removeCookie = function (key, options) {
		if (cookieUtil.cookie(key) === undefined) {
			return false;
		}
		// Must not alter options, thus extending a fresh object...
		cookieUtil.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !cookieUtil.cookie(key);
	};
	$.removeCookie=cookieUtil.removeCookie;
	return cookieUtil;
}));
