var pageSize = 9;
var pageLayer;
layui.use('layer', function(){
	pageLayer = layui.layer;
});
var currentPage1 = 1;
var currentPage2 = 1;



$(function(){
	layui.use('element', function(){
	    var element = layui.element;
	    element.on('tab(tabList)', function(data){
	    	if(data.index == 0){
	    		searchFriendList(1);
	    		$("#pageDiv2").css('display','none');
	    	}
	    	if(data.index == 1){
	    		$("#pageDiv1").css('display','none');
	    		searchFriendApply(1);
	    	}
	    	if(data.index == 2){
	    		$("#pageDiv1").css('display','none');
	    		$("#pageDiv2").css('display','none');
	    	}
	    });
	});
	searchFriendList(currentPage1);
});

/**
 * 查询好友列表数据
 * @param {Object} pageNo
 */
function searchFriendList(pageNo){
	var url = '/sd_baseData/friend/getFriendList?pageNo=' + pageNo + '&pageSize=' + pageSize;
	require(['ajaxUtil','template'], function(ajaxUtil, template){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				if(data.totalCount > 0){
					//第一页的时候加载分页栏
					if(1 == pageNo){
						$("#pageDiv1").css('display','');
						createPage1(pageSize, data.totalCount);
					}
					template.importSDM('/friend/friendList.sdm', '#tab1',function(vueObject){
						//加载完成
					},data);
				}else{
					$("#pageDiv1").css('display','none');
					$("#tab1").html("<h4 class='mgt-20 text-center text-danger'>没有好友信息</h4>")
				}
			}else{
				pageLayer.alert(data.message, {icon: 5});
			}
		});
	});
}

/**
 * 
 * @param {Object} pageSize
 * @param {Object} totalCount
 */
function createPage1(pageSize, totalCount){
	layui.use('laypage',function(){
		var laypage = layui.laypage;
		
		laypage.render({
			elem:'pageDiv1',
			count:totalCount,
			limit:pageSize,
			jump:function(obj, first){
				currentPage1 = obj.curr;
				if(!first){
					searchFriendList(currentPage1);
				}
			}
		});
	});
}

/**
 * 
 * @param {Object} pageSize
 * @param {Object} totalCount
 */
function createPage2(pageSize, totalCount){
	layui.use('laypage',function(){
		var laypage = layui.laypage;
		
		laypage.render({
			elem:'pageDiv2',
			count:totalCount,
			limit:pageSize,
			jump:function(obj, first){
				currentPage1 = obj.curr;
				if(!first){
					searchFriendList(currentPage2);
				}
			}
		});
	});
}

/**
 * 删除好友
 * @param {Object} friendId
 */
function deleteFriend(friendId){
	pageLayer.confirm("确认删除？",function (index){
		var url = "/sd_baseData/friend/delete?friendId=" + friendId;

		require(['ajaxUtil'],function(ajaxUtil){
			ajaxUtil.doGet(url, function(data){
				if(data.code == 0){
					pageLayer.alert(data.message);
					searchFriendList(currentPage1);
				}else{
					pageLayer.alert(data.message);
				}
			});
		});
	});					
}

/**
 * 查找好友
 */
function searchFriend(){
	var userCode = $("#userCode").val();
	var url = "/sd_baseData/userInfo?userCode=" + userCode;
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				var user = data.user;
				if(user != null){
					$("#noData").css('display','none');
					$("#friendCode").html(user.userCode);
					$("#friendNikename").html(user.userNikename);
					var htmlContent = "<i class='layui-icon cursor-pointer' title='添加' onclick='addFriend("+ user.userId +");'>&#xe61f;</i>"
					$("#addFriendBtn").html(htmlContent);
					$("#friendInfo").css('display','');
				}else{
					$("#noData").css('display','');
					$("#friendInfo").css('display','none');
				}
			}else{
				pageLayer.alert(data.message);
			}
		});
	});	
}


/**
 * 好友申请
 * @param {Object} friendId
 */
function addFriend(friendId){
	var url = "/sd_baseData/friend/apply?friendId=" + friendId;
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message);
			}else{
				pageLayer.alert(data.message);
			}
		});
	});		
}

/**
 * 查询好友申请列表
 * @param {Object} pageNo
 */
function searchFriendApply(pageNo){
	var url = '/sd_baseData/friend/getApplyList?pageNo=' + pageNo + '&pageSize=' + pageSize;
	require(['ajaxUtil','template'], function(ajaxUtil, template){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				if(data.totalCount > 0){
					//第一页的时候加载分页栏
					if(1 == pageNo){
						$("#pageDiv2").css('display','');
						createPage2(pageSize, data.totalCount);
					}
					template.importSDM('/friend/friendApplyList.sdm', '#tab2',function(vueObject){
						//加载完成
					},data);
				}else{
					$("#pageDiv2").css('display','none');
					$("#tab2").html("<h4 class='mgt-20 text-center text-danger'>没有好友申请</h4>")
				}
			}else{
				pageLayer.alert(data.message, {icon: 5});
			}
		});
	});	
}

/**
 * 处理好友请求
 * @param {Object} friendId
 */
function handleFriendApply(applyId, result, friendId){
	var url = "/sd_baseData/friend/handleApply?applyId=" + applyId + "&result=" + result + "&friendId=" + friendId;
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message);
				searchFriendApply(currentPage2);
			}else{
				pageLayer.alert(data.message);
				searchFriendApply(currentPage2);
			}
		});
	});		
}