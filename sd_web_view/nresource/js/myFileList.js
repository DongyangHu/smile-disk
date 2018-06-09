var dirId = '';
var parentDirId = '';
var parentDirNode;
var pageLayer;
layui.use('layer',function(){
	pageLayer = layui.layer;
});
var pageSize = 9;
var treeObj;
var fileType = '';
var currentPage = 1;

$(function(){
	require(['uri'],function(uri){
		var urlObj = new uri(location.href);
        fileTypeTmp = urlObj.getQueryParamValue("fileType");
        if(null != fileTypeTmp && 'undefined' != fileTypeTmp && '' != fileTypeTmp && '-1' != fileTypeTmp){
        	fileType = fileTypeTmp;
        }
        tree();
	});
	
	
	//上传
	$("#uploadBtn").click(function(){
		layui.use('layer',function(){
			var layer = layui.layer;
			layer.open({
	    		type: 2,
	    		title: "文件上传",
	    		//maxmin: true,
	    		//shadeClose: true, //点击遮罩关闭层
	    		area : ['600px' , '320px'],
	    		content: '/fileManage/fileUpload.html',
	    		id: 'fileUploadPage'
	    	});
		});
	});
	$("#createDirBtn").click(function(){
		layui.use('layer',function(){
			var layer = layui.layer;
			layer.prompt({
				title: '请输入文件夹名称', 
				formType: 3,
				maxlength: 20,
				offset: 'auto'
			}, function(dirName, index){
				beforeCreateDir(dirName);
  				layer.close(index);
			});
		});
	});
	$("#deletFileBtn").click(function(){
		var fileBoxList = document.getElementsByName("fileBox");
		var fileIdArray = [];
		for(var i = 0; i < fileBoxList.length; i++){
			if(fileBoxList[i].checked == true){
				fileIdArray.push(fileBoxList[i].value);
			}
		}
		pageLayer.confirm("确认删除？",function (index){
			var url = "/sd_fm/rest/fileInfo/deleteFileById";
			var params = {};
			params.fileIdArray = fileIdArray;
			params.type = '1';
			
			require(['ajaxUtil'],function(ajaxUtil){
				ajaxUtil.doPost(url, function(data){
					if(data.code == 0){
						pageLayer.alert(data.message);
						search(currentPage);
					}else{
						pageLayer.alert(data.message);
					}
				},params);
			});
		});		
	});

});

//获取根路径下的文件
function rootFileList(){
	treeObj.cancelSelectedNode();
	parentDirId = '';
	search(1);
}

function beforeCreateDir(dirName){
	if(null == parentDirId || '' == parentDirId){
		pageLayer.confirm("未选择父文件夹，将在根路径创建目录!",function (){
			createDir(dirName);
		},
		function (){
			pageLayer.alert("请选择父文件夹，再新建文件夹!");
			return false;
		});
	}else{
		createDir(dirName);
	}
}

//新建文件夹
function createDir(dirName){
	dirName = dirName.replace(/\s+/g,"");
	if(null == dirName || "" == dirName || typeof(dirName) == dirName){
		dirName = "新建文件夹";
	}
	var url = '/sd_fm/rest/fileInfo/createDir?dirName=' + encodeURIComponent(encodeURIComponent(dirName)) + '&parentDirId=' + parentDirId;
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message, {icon: 6});
				});
				treeObj.reAsyncChildNodes(parentDirNode, "refresh");
			}else{
				layui.use('layer',function(){
					layer = layui.layer;
					layer.msg(data.message, {icon: 5});
				});	
			}
		});
	});
	
}

//加载树
function tree(){
	var setting = {
		treeId: "fileDirTree",
		view: {
			dblClickExpand:false,
			selectedMulti:false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: true,
			showRenameBtn: true,
			removeTitle: "删除",
			renameTitle: "重命名"
		},
		data: {
			key:{
				name: "dirName"
			},
	        simpleData: {
	           enable: true,
	           idKey: "dirId",
	           pIdKey: "parentDirId",
	           rootPId: ""
	        }
	   },
	   async: {
	        enable: true,
	        url:'/sd_fm/rest/fileInfo/getDirList',
	        autoParam:["dirId=parentDirId"],
	        dataFilter: filter,
	        type: "get"
	    },
	    callback:{
	        onAsyncSuccess: onAsyncSuccess,
	        beforeDrag: beforeDrag,
			//beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
	        onClick:function(event,treeId,treeNode){
	        	parentDirId = treeNode.dirId;
	        	parentDirNode = treeNode;
	        	search(1);
	        }
	    }
	}
	treeObj = $.fn.zTree.init($("#dirTree"), setting);
}

//数据拦截器
function filter(treeId, parentNode, data) {
    if(data.code == '0') {
        var fileDirList = data.fileDirList;
    	return fileDirList;
    }else{
    	pageLayer.alert("no data!");
    }
    return null;
}

//异步加载成功
function onAsyncSuccess(event, treeId, treeNode, msg) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if(treeNode == null) {
        var nodes = treeObj.getNodes();
        if(nodes.length>0) {
            //treeObj.selectNode(nodes[0]);
            //parentDirId = nodes[0].dirId;
            search(1);
        }
    }
}

//获取文件列表
function search(pageNo){
	var fileName = encodeURIComponent(encodeURIComponent($("#fileNameInput").val()));
	var url = "/sd_fm/rest/fileInfo/getFileList?parentDirId=" + parentDirId + "&fileName=" + fileName + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&fileType=" + fileType;
	
	require(['ajaxUtil','template'], function(ajaxUtil, template){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				var totalCount = data.totalCount;
				//有文件
				if(totalCount > 0){
					if(1 == pageNo){
						$("#pageDiv").css('display','');
						createPage(pageSize, data.totalCount);
					}
					template.importSDM('/fileManage/fileList.sdm','#fileList',function(vueObject){
						
					},data);
				}else{//没有文件
					$("#pageDiv").css('display','none');
					$('#fileList').html("<hr/><h4 class='text-danger text-center'>该文件夹下没有文件!</h4>");
				}
			}else{
				
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
			elem:'pageDiv',
			count:totalCount,
			limit:pageSize,
			jump:function(obj, first){
				currentPage = obj.curr;
				if(!first){
					search(currentPage);
				}
			}
		});
	});
}
//点击编辑
function beforeEditName(treeId, treeNode){
	treeObj.selectNode(treeNode);
	pageLayer.confirm("确认编辑文件夹-- " + treeNode.dirName + " 吗？",function (index){
		treeObj.editName(treeNode);
		pageLayer.close(index);
	},function (index){
		pageLayer.close(index);
		return false;
	});
}
//改名
function beforeRename(treeId, treeNode, newName, isCancel){
	treeObj.selectNode(treeNode);
	var oldName = treeNode.dirName;
	var newDirName = newName.replace(/\s+/g,"");
	if(newDirName.length == 0){
		pageLayer.alert("文件夹名不能为空！");
		treeObj.cancelEditName(oldName);
		return false;
	}else{
		var url = "/sd_fm/rest/fileInfo/renameDir?dirId=" + treeNode.dirId + "&dirName=" + newDirName;
		require(['ajaxUtil'],function(ajaxUtil){
			ajaxUtil.doGet(url, function(data){
				if(data.code == 0){
					treeObj.reAsyncChildNodes(null, "refresh");
					pageLayer.alert(data.message);
					return true;
				}else{
					treeObj.reAsyncChildNodes(null, "refresh");
					pageLayer.alert(data.message);
					return false;
				}
			},false);
		});
	}
}
//禁止拖动
function beforeDrag(treeId, treeNodes){
	return false;
}
//删除
function beforeRemove(treeId, treeNode){
	treeObj.selectNode(treeNode);
	if(confirm("确认删除文件夹 -- " + treeNode.dirName + " 吗？")){
		var url = "/sd_fm/rest/fileInfo/deleteFileDirById?dirId=" + treeNode.dirId;
		require(['ajaxUtil'],function(ajaxUtil){
			ajaxUtil.doGet(url, function(data){
				if(data.code == 0){
					//存在文件
					if(data.type == 0){
						treeObj.reAsyncChildNodes(null, "refresh");
						pageLayer.alert(data.message);
						return false;
					}
					//删除失败
					if(data.type == 2){
						treeObj.reAsyncChildNodes(null, "refresh");
						pageLayer.alert(data.message);
						return false;
					}
					//删除成功
					if(data.type == 1){
						treeObj.reAsyncChildNodes(null, "refresh");
						pageLayer.alert(data.message);
						return true;
					}
				}else{
					treeObj.reAsyncChildNodes(null, "refresh");
					pageLayer.alert(data.message);
					return false;
				}
			});
		});
	}else{
		return false;
	}
}

/**
 * 删除文件夹
 */
function deleteFileDir(dirId){
	var url = "/sd_fm/rest/fileInfo/deleteFileDirById?dirId=" + dirId;
	require(['ajaxUtil'],function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				//存在文件
				if(data.type == 0){
					pageLayer.alert(data.message);
					return false;
				}
				//删除失败
				if(data.type == 2){
					pageLayer.alert(data.message);
					return false;
				}
				//删除成功
				if(data.type == 1){
					pageLayer.alert(data.message);
					return true;
				}
			}else{
				pageLayer.alert(data.message);
				return false;
			}
		});
	});
}

/**
 * 删除文件
 * @param {Object} fileId
 */
function deleteFile(fileId){
	pageLayer.confirm("确认删除？",function (index){
		var url = "/sd_fm/rest/fileInfo/deleteFileById";
		var params = {};
		var fileIdArray = [];
		
		fileIdArray.push(fileId);
		params.fileIdArray = fileIdArray;
		params.type = '1';
		
		require(['ajaxUtil'],function(ajaxUtil){
			ajaxUtil.doPost(url, function(data){
				if(data.code == 0){
					pageLayer.alert(data.message);
					search(currentPage);
				}else{
					pageLayer.alert(data.message);
				}
			},params);
		});
		//pageLayer.close(index);
	});
}

/**
 * 下载文件
 * @param {Object} fileId
 */
function downloadFile(fileId){
	var url = "/sd_fm/mvc/fileUpload/download?fileId=" + fileId;
	window.open(url);
}

/**
 * 文件重命名
 * @param {Object} fileId
 * @param {Object} newFileName
 */
function renameFile(fileId, newFileName){
	var url = "/sd_fm/rest/fileInfo/rename?fileId=" + fileId + "&newFileName=" + encodeURIComponent(encodeURIComponent(newFileName));
	require(['ajaxUtil'], function(ajaxUtil){
		ajaxUtil.doGet(url, function(data){
			if(data.code == 0){
				pageLayer.alert(data.message);
				search(currentPage);
			}else{
				pageLayer.alert(data.message);
			}
		});
	});
	
}

/**
 * 文件重命名之前
 * @param {Object} fileId
 */
function editRenameFile(fileId, oldFileName){
	layui.use('layer',function(){
		var layer = layui.layer;
		layer.prompt({
			title: '请输入新名称', 
			value: oldFileName,
			formType: 3,
			maxlength: 20,
			offset: 'auto'
		}, function(newFileName, index){
			renameFile(fileId,newFileName);
  			layer.close(index);
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

/**
 * 全选
 */
function selectAll(obj){
	var fileBoxList = document.getElementsByName("fileBox");
	if(obj.checked){
		for(var i = 0; i < fileBoxList.length; i++){
			fileBoxList[i].checked = true;
		}
	}else{
		for(var i = 0; i < fileBoxList.length; i++){
			fileBoxList[i].checked = false;
		}		
	}
}

/**
 * 分享文件
 * @param {Object} fileId
 */
function shareFile(fileId){
	pageLayer.open({
		type: 2,
		title: "分享文件",
		area: ['600px', '280px'],
		shadeClose: true,
		content: "/fileManage/shareFriend.html?fileId="+fileId
	});
}


