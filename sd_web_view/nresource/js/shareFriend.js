var pageSize = 9;
var pageLayer;
layui.use('layer', function(){
	pageLayer = layui.layer;
});
var currentPage = 1;
var fileId;

$(function(){
	searchFriendList(1);
	var url = window.location.href;
	fileId = url.substr(url.indexOf("?")+8);
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
						createPage(pageSize, data.totalCount);
					}
					template.importSDM('/fileManage/shareFriendList.sdm', '#friendList',function(vueObject){
						//加载完成
					},data);
				}else{
					$("#pageDiv1").css('display','none');
					$("#friendList").html("<h4 class='mgt-20 text-center text-danger'>没有好友信息</h4>")
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
 * 分享文件
 */
function submiteShare(){
	var params = {};
	params.fileId = fileId;
	var friendBoxList = document.getElementsByName("friendBox");
	var friendIdArray = [];
	for(var i = 0; i < friendBoxList.length; i++){
		if(friendBoxList[i].checked == true){
			friendIdArray.push(friendBoxList[i].value);
		}
	}
	if(friendIdArray.length == 0){
		pageLayer.alert("请选择需要分享的好友");
		return false;
	}
	params.friendIdArray = friendIdArray;
	
	var url = "/sd_fm/rest/fileShare/share";
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