var pageSize = 5;
var pageLayer;
layui.use('layer', function(){
	pageLayer = layui.layer;
});
var currentPage = 1;
var currentPage2 = 1;

$(function(){
	layui.use('element', function(){
	    var element = layui.element;
	    element.on('tab(tabList)', function(data){
	    	if(data.index == 0){
	    		searchUserList(1);
	    	}
	    	if(data.index == 1){
	    		$("#pageDiv1").css('display','none');
	    		searchNoticeList(1);
	    	}
	    });
	});
	
	searchUserList(1);
});



/**
 * 查询用户列表数据
 * @param {Object} pageNo
 */
function searchUserList(pageNo){
	var url = '/sd_baseData/userInfo/getUserInfoList?pageNo=' + pageNo + '&pageSize=' + pageSize;
	require(['ajaxUtil','template'], function(ajaxUtil, template){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				if(data.totalCount > 0){
					//第一页的时候加载分页栏
					if(1 == pageNo){
						$("#pageDiv1").css('display','');
						createPage(pageSize, data.totalCount);
					}
					template.importSDM('/sysman/userManage.sdm', '#tab1',function(vueObject){
						//加载完成
					},data);
				}else{
					$("#pageDiv1").css('display','none');
					$("#tab1").html("<h4 class='mgt-20 text-center text-danger'>没有用户信息</h4>")
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
function createPage(pageSize, totalCount){
	layui.use('laypage',function(){
		var laypage = layui.laypage;
		
		laypage.render({
			elem:'pageDiv1',
			count:totalCount,
			limit:pageSize,
			jump:function(obj, first){
				currentPage = obj.curr;
				if(!first){
					searchFriendList(currentPage);
				}
			}
		});
	});
}

/**
 * 更改用户状态
 * @param {Object} userId
 * @param {Object} status
 */
function changeUserStatus(userId, status){
	var url = "/sd_baseData/userInfo/changeUserStatus";
	var params = {};
	params.userId = userId;
	params.status = status;
	
	require(['ajaxUtil'], function(ajaxUtil){
		ajaxUtil.doPost(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message, {icon: 6});
				searchUserList(currentPage);
			}else{
				pageLayer.alert(data.message, {icon: 5});
				searchUserList(currentPage);
			}			
		},params);
	});
}

/**
 * 重设密码
 * @param {Object} userCode
 * @param {Object} status
 */
function resetPwd(userCode){
	var url = "/sd_baseData/userInfo/refreshUserPwd";
	var params = {};
	params.userCode = userCode;
	
	require(['ajaxUtil'], function(ajaxUtil){
		ajaxUtil.doPost(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message, {icon: 6});
				searchUserList(currentPage);
			}else{
				pageLayer.alert(data.message, {icon: 5});
				searchUserList(currentPage);
			}
		},params);
	});
}





/**
 * 查询
 * @param {Object} pageNo
 */
function searchNoticeList(pageNo){
	var noticeName = encodeURIComponent(encodeURIComponent($("#noticeNameInput").val()));
	var url = "/sd_baseData/notice/allList?noticeName=" + noticeName + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
	
	require(['ajaxUtil','template'], function(ajaxUtil, template){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				var totalCount = data.totalCount;
				//有文件
				if(totalCount > 0){
					if(1 == pageNo){
						$("#pageDiv2").css('display','');
						createPage2(pageSize, data.totalCount);
					}
					template.importSDM('/sysman/noticeManage.sdm','#noticeList',function(vueObject){
						
					},data);
				}else{//没有文件
					$("#pageDiv2").css('display','none');
					$('#noticeList').html("<hr/><h4 class='text-danger text-center'>没有发布的消息!</h4>");
				}
			}else{
				pageLayer.alert(data.message);
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
				currentPage2 = obj.curr;
				if(!first){
					searchNoticeList(currentPage2);
				}
			}
		});
	});
}

/**
 * 发布消息
 */
function publishNotice(){
	layui.use('layer', function(){
		layer.open({
			type: 2,
			title: "发布消息",
			area: ['350px', '360px'],
			content: "/sysman/notice.html",
			end: function(){
				searchNoticeList(1);
			}
		});
	});	
}


/**
 * 删除消息
 */
function deleteNotice(noticeId){
	var url = "/sd_baseData/notice/delete?noticeId=" + noticeId;
	
	require(['ajaxUtil'], function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message, {icon: 6});
				searchNoticeList(currentPage2);
			}else{
				pageLayer.alert(data.message, {icon: 5});
				searchNoticeList(currentPage2);
			}			
		});
	});	
}

/**
 * 选择人员
 * @param {Object} fileId
 */
function selectPerson(noticeId){
	pageLayer.open({
		type: 2,
		title: "分享文件",
		area: ['700px', '360px'],
		shadeClose: true,
		content: "/sysman/selectPreson.html?noticeId=" + noticeId
	});
}
