var pageSize = 9;
var pageLayer;
layui.use('layer', function(){
	pageLayer = layui.layer;
});
var currentPage = 1;
var noticeId;

$(function(){
	searchFriendList(1);
	var url = window.location.href;
	noticeId = url.substr(url.indexOf("=")+1);
});


/**
 * 查询好用户列表数据
 * @param {Object} pageNo
 */
function searchFriendList(pageNo){
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
					template.importSDM('/sysman/selectUser.sdm', '#friendList',function(vueObject){
						//加载完成
					},data);
				}else{
					$("#pageDiv1").css('display','none');
					$("#friendList").html("<h4 class='mgt-20 text-center text-danger'>没有用户信息</h4>")
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
 * 选择用户
 */
function submiteShare(){
	var params = {};
	params.noticeId = noticeId;
	var userBoxList = document.getElementsByName("userBox");
	var userIdArray = [];
	for(var i = 0; i < userBoxList.length; i++){
		if(userBoxList[i].checked == true){
			userIdArray.push(userBoxList[i].value);
		}
	}
	if(userIdArray.length == 0){
		pageLayer.alert("请选择需要发布的用户");
		return false;
	}
	params.userIdArray = userIdArray;
	
	var url = "/sd_baseData/notice/noticeUser";
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doPost(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message,{
					end:function(){
						window.parent.pageLayer.closeAll();
					}
				});
			}else{
				pageLayer.alert(data.message,{
					end:function(){
						window.parent.pageLayer.closeAll();
					}
				});
			}
		},params);
	});
}