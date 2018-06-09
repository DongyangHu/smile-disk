var pageSize = 9;
var pageLayer;
layui.use('layer', function(){
	pageLayer = layui.layer;
});
var currentPage1 = 1;
var currentPage2 = 1;




$(function(){
	searchReceiveShare(1);
	layui.use('element', function(){
	    var element = layui.element;
	    element.on('tab(tabList)', function(data){
	    	if(data.index == 0){
	    		$("#pageDiv2").css('display','none');
	    		searchReceiveShare(currentPage1);
	    	}
	    	if(data.index == 1){
	    		$("#pageDiv1").css('display','none');
	    		sendReceiveShare(currentPage2);
	    	}
	    });
	});
});


/**
 * 下载文件
 * @param {Object} fileId
 */
function downloadFile(fileId){
	var url = "/sd_fm/mvc/fileUpload/download?fileId=" + fileId;
	window.open(url);
}

/**
 * 收到的分享
 * @param {Object} pageNo
 */
function searchReceiveShare(pageNo){
	var fileName = encodeURIComponent(encodeURIComponent($("#fileNameInput").val()));
	var url = "/sd_fm/rest/fileShare/receiveShare?pageNo=" + pageNo + "&pageSize=" + pageSize;
	
	require(['ajaxUtil','template'], function(ajaxUtil, template){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				var totalCount = data.totalCount;
				//有文件
				if(totalCount > 0){
					if(1 == pageNo){
						$("#pageDiv1").css('display','');
						createPage1(pageSize, data.totalCount);
					}
					template.importSDM('/fileManage/receiveShare.sdm','#tab1',function(vueObject){
						
					},data);
				}else{//没有文件
					$("#pageDiv1").css('display','none');
					$('#tab1').html("<hr/><h4 class='text-danger text-center'>没有收到分享!</h4>");
				}
			}else{
				pageLayer.alert(data.message);
			}
		});
	});
}

/**
 * 发出的分享
 * @param {Object} pageNo
 */
function sendReceiveShare(pageNo){
	var fileName = encodeURIComponent(encodeURIComponent($("#fileNameInput").val()));
	var url = "/sd_fm/rest/fileShare/sendShare?pageNo=" + pageNo + "&pageSize=" + pageSize;
	
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
					template.importSDM('/fileManage/receiveShare.sdm','#tab2',function(vueObject){
						
					},data);
				}else{//没有文件
					$("#pageDiv2").css('display','none');
					$('#tab2').html("<hr/><h4 class='text-danger text-center'>没有收到分享!</h4>");
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
				currentPage2 = obj.curr;
				if(!first){
					searchFriendList(currentPage2);
				}
			}
		});
	});
}

/**
 * 删除共享
 * @param {Object} shareId
 */
function deleteShare(shareId){
	pageLayer.confirm("确认删除？",function (index){
		var url = "/sd_fm/rest/fileShare/cancel?shareId=" + shareId;
		require(['ajaxUtil'],function(ajaxUtil){
			ajaxUtil.doGet(url, function(data){
				if(data.code == 0){
					pageLayer.alert(data.message,{
						end: function(){
							searchReceiveShare(currentPage1);
						}
					});
				}else{
					pageLayer.alert(data.message);
				}
			});
		});
	});
}

function showImg(fileId){
	var imgPath = "/sd_fm/mvc/fileUpload/download?fileId="+fileId;
	layui.use('layer',function(){
		var layer = layui.layer;
		layer.open({
		    type: 1,
		    title: false,
		    offset: 't',
		    closeBtn: 0,
		    skin: 'layui-layer-nobg', //没有背景色
		    shadeClose: true,
		    content: "<img src="+imgPath +" />"
		});
	});
}
