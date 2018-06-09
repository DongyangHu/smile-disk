define(['jquery'],function(){
	jQuery.fn.animateCss = function(animationName){
		var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
	    this.addClass('animated ' + animationName).one(animationEnd, function() {
	        $(this).removeClass('animated ' + animationName);
	    });
	    return this;
	};	
});