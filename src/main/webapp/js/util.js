var filelist = [];
var upload;
var _name ;
var _id;
var defaultId = 'defaultUpload-';
var pathString = '../../';// 根据系统路径获取向上返回的层数

/**
 * 获取匹配元素 内部所有标签的name和value 如果name和value为空则 不获取
 */
(function($) {
	/* pathString = getRootPath(); */
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (this.value != 'undefined' && this.value != '') {
						if (serializeObj[this.name]) {
							if ($.isArray(serializeObj[this.name])) {
								serializeObj[this.name].push(this.value);
							} else {
								serializeObj[this.name] = [
										serializeObj[this.name], this.value];
							}
						} else {
							serializeObj[this.name] = this.value;
						}
					}
				});
		return serializeObj;
	};
})($);
function exportExcel(grid, form, url) {
	var data = [];
	var columns = grid.getColumns();
	var option = {};
	if (form != null) {
		option = form.serializeJson();
	}
	option.service = $("#service").val();
	option.filename = $("title").text();
	option.display = "option";
	option.sortName = gridManager.get("sortName");
	option.sortOrder = gridManager.get("sortOrder");
	for ( var i in columns) {
		if (columns[i].display != null && columns[i].isExport == true && columns[i]._hide == false) {
			var temp = {};
			temp.display = columns[i].display;
			temp.groupName = columns[i].groupName;
			temp.width = columns[i].width;
			temp.name = columns[i].name;
			temp.align = columns[i].align;
			temp.format = columns[i].format;
			temp.type = columns[i].type;
			data.push(temp);
		}
	}
	data.push(option);
	
	// 下载文件
	$.ajax({
		type : "POST",
		url : "../../common/exportExcel",
		data : "json=" + JSON.stringify(data),
		async : false,
		dataType : "text",
		success : function(path) {
			window.open(url + '/common/downloadFile?path=' +  path);
		}
	});
}

function getRootPath(){
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    // 获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht + projectName + '/');
}
    
function iniUpload(id, name) {
	iniUpload(id, name,null,null);
}
function iniUpload(id,name,param){
	_name = name;
	_id = id;
	var fileSizeLimit = '200MB';
	if(param != undefined && param.fileSizeLimit != undefined && param.fileSizeLimit != null){
		fileSizeLimit = param.fileSizeLimit;
	}
	 upload = $('#' + id).uploadify({
		'swf' : pathString+'resources/lib/uploadify/uploadify.swf',
		'uploader' : pathString+'common/uploadserver',
		'multi' : true,
		/* 'id' : 'fileQueue', */
		'post' : true,
		'removeCompleted' : false,
		'method' : 'post',
		'height' : 20,
		'width' : 80,
		'auto' : true,
		// 'progressData' : 'speed',//显示进度速度例如：35/kb
		// 'formData': {pid:'1'},
		'fileObjName' : 'file',
		'fileSizeLimit' : fileSizeLimit,
		'removeTimeout' : 1,// 移除提示成功时间
		'uploadLimit' : 5,// 上传最大限度
		'fileTypeExts' : '*.*',// 允许上传的类型
		'buttonText' : '请选择附件',		
		'button_text_style' : 'background-color: blue;',
		'onCancel' : function(file){
			for(var i in filelist){
				if(filelist[i].id = file.id){
					// 删除后台文件
					$.ajax({
						type : "POST",
						url : pathString+"common/removeUploadFile",
						data : "filepath=" + encodeURI(encodeURI(filelist[i].path)),
						dataType : "text",
						error : function(result) {
							alert(result);
						}
					});
					filelist.splice(i, 1);					
					break;
				}
			}			
			$("#filehidden_" + file).remove();	
			if(param != undefined && param.onCancel != undefined &&  param.onCancel != null){
				onCancel();
			}
		},	
		'onUploadSuccess':function(file, data, response){ 
			// $("#" + id).append("<input type='hidden' id='"+ file.id +"'
			// name='"+ name +"' value='"+ data +"'/>");
			$("#" + id).append("<input type='hidden' id='filehidden_" + file.id + "' />");
			$("#filehidden_" + file.id).attr("name", name);
			$("#filehidden_" + file.id).val(data);
			if(!isExists(data)){
				var item = {};
				item.path = data;
				item.id = file.id;
				filelist.push(item);
			}else{
				// 删除重复文件
				var files = upload.data('uploadify').queueData.files;
				for(var i in files){
					if(files[i].name == file.name){
						$("#" + files[i].id).remove();
						delete files[i];
						break;
					} 
				}
			}
			if(param != undefined && param.onSuccess != undefined && param.onSuccess != null){
				onSuccess();
			}
		},  
	});
}
function getFileList(){
	return filelist;
}
/**
 * 清除附加控件默认值
 * 
 * @param param
 *            id : 附件控件id name ： 附件控件name
 */
function clearUploadFile(param){
	var cid = _id;
	var cname = _name;
	if(param  && param!=undefined){
		cid = param.id  ? param.id : _id;
		cname = param.name ? param.name : _name;
	}
	upload.data('uploadify').queueData.files = {};
	filelist = [];
	$("input[name='" + cname+"']").remove();
	$("#" + cid + '-queue').html('');
	$("#" + cid + '-defaultvalue').html('');
	$("#" + cid + '-removevalue').html('');
}

/**
 * 附件设置默认值
 * 
 * @param data
 * @param removeFlag
 * @param showDiv
 */
function setDefaultValue(data, removeFlag,showDiv){
	var removeLink = '';	
	for(var i in data){
		if(removeFlag){
			removeLink = "&nbsp;&nbsp;<a href=\"javascript:removeAttachment("+ data[i].id +",'"+showDiv+"')\">删除</a>";
		}
		var row = "<div id='" + defaultId + data[i].id + "'><a href=\"javascript:downloadAttachment('" + data[i].id + "');\"> "+ data[i].name +"("+ data[i].formatSize +")" +"</a>"+ removeLink +"</div>";
		if(showDiv==undefined||showDiv=='')
			$("#" + _id + '-defaultvalue').append(row);
		else
			$("#" + showDiv + '-defaultvalue').append(row);
	}
}

function setDefaultValuefortemp(name, removeFlag,showDiv){
	var removeLink = '';	
		if(removeFlag){
			removeLink = "&nbsp;&nbsp;<a href=\"#\" onclick=\"removefile()\">删除</a>";
		}
		var row = "<div id='"+defaultId+"'><a href=\"#\"> "+ name +"</a>"+ removeLink +"</div>";
		if(showDiv==undefined||showDiv=='')
			$("#" + _id + '-defaultvalue').append(row);
		else
			$("#" + showDiv + '-defaultvalue').append(row);
}

function isExists(data){
	var flag = false;
	for(var i in filelist){
		if(filelist[i].path == data){
			flag = true;
			break;
		} 
	}
	return flag;
}
/*
 * function downloadAttachment(id){
 * window.open('../../common/downloadAttachment?id=' + id); }
 */
/*
 * function downloadAttachment(url){ window.open("ftp://" + url); }
 */
function downloadAttachment(id){
	window.open('../../common/downloadAttachment?id=' + id);
}
function searchAttachments(sourceId, sourceEntity,size){
	var height = '250';
	var pageSize = '';
	if(size != undefined && size >0){
		height = size * 27 + 125;
		pageSize='&pageSize='+size;
	}
	$.ligerDialog.open({
		url : '../../views/include/downloadattachment.jsp?sourceId=' + sourceId + '&sourceEntity=' + sourceEntity+pageSize,
		width : '520',
		height : height,
		title : "附件下载",
		buttons : [ {
			text : '关闭',
			onclick : function(item, dialog) {
				dialog.close();
			}
		}]
	});
}
function removeAttachment(id, container){
	var cid = _id;
	if(container && container!= "undefined"){
		cid = container;
	}
	$("#" + defaultId + id).remove();
	$("#" + cid + '-removevalue').append("<input type='hidden' name='remove_"+ _name +"' value='"+ id +"'/>");
}

/**
 * 创建选人控件
 * 
 * @param inputId
 *            控件id
 * @param orgType
 *            （'POSITION,EMPLOYEE,DEPT,TEAM'）选取类型为空则全选
 * @param hiddenId
 *            隐藏控件id
 * @param flag
 *            是否多选
 * @param outlist
 *            排除在外列表
 * @param include
 *            包含在内的值
 * @param onSelect
 *            选择后调用事件
 */
function createOrgControl(inputId, orgType, hiddenId, flag, outlist, include, onSelect){
	var isMultiple = flag;
	var out = outlist;
	var clude = include;	 
	var selectFunction = onSelect;
	// 添加org容器
	$("#" + inputId).parent().append("<div id='orgdiv_" + inputId + "'></div>");
	// 获得org容器
	var div = $("#orgdiv_" + inputId)
	if(isMultiple){
		div.append("<div style='margin-top:20px;' id='c_"+ inputId +"'/>");
	}else{
		if("\v"=="v") {
			$("#" + inputId)[0].onpropertychange = function(){
				if($("#" + inputId).val().trim() == ''){
					$("#" + hiddenId).val('');
					$("#" + hiddenId + "_name").val('');
				}
			};
		}else{
			$("#" + inputId)[0].addEventListener("input",function(){
				if($("#" + inputId).val().trim() == ''){
					$("#" + hiddenId).val('');
					$("#" + hiddenId + "_name").val('');
				}
			},false);
		}
	}
	// 添加默认字体
	$("#" + inputId).attr("placeholder", _getOrgString(orgType));
	$("#" + inputId).attr("autocomplete", true);
	$("#" + inputId).attr("hiddenid", hiddenId);
	div.append("<input type='hidden' id='"+ hiddenId +"' name='"+ hiddenId +"'/>");
	div.append("<input type='hidden' id='"+ hiddenId +"_name' name='"+ hiddenId +"_name'/>");
	$.ajax({
		type : "POST",
		url : "../../org/findAllOrgObject",
		async : false,
		dataType : "json",
		success : function(result) {						
			$("#" + inputId).autocomplete({
				source: function( request, response) {
					if(isMultiple){
						request.term = request.term.split(',').pop();
					}
			        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );			        
			        response( $.grep(result, function(value ) {
			        	// 排除人员
				        if (out && _isOut(out, value.id)){
				        	return false;
				        }
				        // 包含人员
				        if(clude && !_include(clude, value.id)){
				        	return false;
				        }
			        	if(isMultiple){
			        		value.label = value.label.split(',').pop();
			        		return ((matcher.test(value.label) || _contans(request.term.trim().toUpperCase(), value.pyname)) && ($("#" + hiddenId).val().indexOf(value.id) == -1) && _isOrgType(orgType, value.orgtype));
			        	}else{
			        		return ((matcher.test(value.label) || _contans(request.term.trim().toUpperCase(), value.pyname)) && _isOrgType(orgType, value.orgtype));
			        	}
			        }));
		        },								        
				dataType : 'json',
				selectFirst : true,
				autoFocus : true,
				matchContains: true,
				close: function( event, ui ) {
					/*
					 * if(isMultiple){ $("#" + inputId).val(''); }
					 */
				},
				select: function(e, ui) {
					if(isMultiple){
						var terms = this.value.split(",");
						terms.pop();
						terms.push(ui.item.value);
						terms.push("");
						this.value = terms.join( "," );

						if($("#" + hiddenId).val()==''){
							$("#" + hiddenId).val(ui.item.id);
							$("#" + hiddenId + "_name").val(ui.item.value);
						}else{
							$("#" + hiddenId).val($("#" + hiddenId).val() + ',' + ui.item.id);
							$("#" + hiddenId + "_name").val($("#" + hiddenId + "_name").val() + ',' + ui.item.value);
						}
						_createAutocompleteSpan(inputId, hiddenId, ui.item.id, ui.item.value);
						return false;
					}else{
						$("#" + hiddenId).val(ui.item.id);		
						$("#" + hiddenId + "_name").val(ui.item.value);
					}
					if(selectFunction){
						selectFunction();
					}
				}
			});
		}
	});
}
function _getOrgString(str){
	var result = '请选择';
	if(str.indexOf("EMPLOYEE") != -1){
		result += "人员、";
	}
	if(str.indexOf("POSITION") != -1){
		result += "岗位、";	
	}
	if(str.indexOf("DEPT") != -1){
		result += "部门、";
	}
	if(str.indexOf("TEAM") != -1){
		result += "小组、";
	}
	return result.substr(0, result.length - 1);
}

/**
 * 销毁org控件
 * 
 * @param inputId
 */
function util_destroyOrgControl(inputId){
	$("#orgdiv_" + inputId).remove();
} 

/**
 * 设置org控件默认值
 * 
 * @param inputId
 * @param hideId
 * @param orgid
 *            组织结构id或empid
 * @param flag
 *            是否多选
 */
function setOrgControlDefaultValue(inputId, hideId, orgid, flag){
	if(orgid){
		$.ajax({
			type : "POST",
			url : "../../org/findOrgObjectByOrgids",
			data : JSON.stringify((orgid + '').split(',')),
			contentType : "application/json; charset=utf-8",
			async : false,
			success : function(result) {
				if(result != null && result != undefined && result.length>0){
					if(flag){
						for(var i =0 ;i<result.length;i++){
							var id = result[i].id;
							var name = result[i].name;
							_createAutocompleteSpan(inputId, hideId, id, name);
							$("#" + inputId).val($("#" + inputId).val() + name + ",");
							if($("#" + hideId).val()==''){
								$("#" + hideId).val(id);
								$("#" + hideId + "_name").val(name);
							}else{
								$("#" + hideId).val($("#" + hideId).val() + ',' + id);
								$("#" + hideId + "_name").val($("#" + hideId + "_name").val() + ',' + name);
							}
						}
					}else{
						var id = result[0].id;
						var name = result[0].name;
						$("#" + inputId).val(name);						
						$("#" + hideId).val(id);						
						$("#" + hideId + "_name").val(name);
					}
				}
			}
		});
	}
	// ie浏览器会有选择框弹出为了解决这个问题加上
	$("#" + inputId).focus();
	$("#" + inputId).blur();
}
function clearOrgControl(inputId, hideId){
	$("#c_"+inputId).html('');
	$("#" + hideId).val('');
	$("#" + hideId + "_name").val('');
}
function _createAutocompleteSpan(inputId, hideid, id, text){
	$("#c_" + inputId).append("<span id='span_"+ hideid + id +"' class='spanclose'><a href=\"javascript:_closeAutocompleteSpan('" + inputId + "', " + id +", '"+ text +"','" + hideid + "')\">×</a>"+ text +"</span>");
}
function _closeAutocompleteSpan(inputId, id, text, hideid){
	$("#span_" + hideid + id).remove();
	var inputIdName = (','+$("#" + inputId).val()).replace(','+text + ',', ",");
	var hideIdValue = (','+$("#" + hideid).val()+',').replace(','+id + ',', ",");
	var hideIdName = (','+$("#" + hideid + "_name").val()+',').replace(','+text + ',', ",");
	inputIdName = inputIdName == ',' ? '' : inputIdName.substring(1);
	hideIdValue = hideIdValue==',' ? '' : hideIdValue.substring(1,hideIdValue.length-1);
	hideIdName = hideIdName==',' ? '' : hideIdName.substring(1,hideIdName.length-1);
	
	$("#" + inputId).val(inputIdName);
	$("#" + hideid).val(hideIdValue);
	$("#" + hideid + "_name").val(hideIdName);
}
/**
 * 判断是否是排除对象
 * 
 * @param outlist
 *            排除列表
 * @param value
 *            当前Employeeid
 * @returns {Boolean}
 */
function _isOut(outlist, value){
	for(var i in outlist){
		if(outlist[i].employeeid == value){
			return true;
		}
	}
	return false;
}

/**
 * 判断包含对象
 * 
 * @param include
 *            排除列表
 * @param value
 *            当前Employeeid
 * @returns {Boolean}
 */
function _include(include, value){
	for(var i in include){
		if(include[i].employeeid == value){
			return true;
		}
	}
	return false;
}

function _isOrgType(orgType, value){
	if(orgType){
		if(orgType.indexOf(value) == -1){
			return false;
		}else{
			return true;
		}
	}
	return true;
}
function _contans(text, value ) {
	if(value==undefined||value==''||value==null)
		return false;
  	if(value.indexOf(text) == 0){
  		return true;
  	}
  	return false;
}

/**
 * grid中显示附件标识及数量
 * 
 * @param size
 * @param id
 * @param entityName
 * @returns {String}
 */
function util_showAttachmentsFlag(size,id,entityName){
	if(size>0){
		var space = size < 10 ? '&nbsp;&nbsp;' : '';
		return "<img name='attachmentFlag' onclick='searchAttachments("+id+", \""+entityName+"\","+size+");' style='margin-top:5px;cursor:pointer;' src='"+pathString+"resources/lib/images/attachment1.png'/><span style='color:red;font-size:4'>"+size+space+"</span>";
	}else
		return "";
}

/**
 * 自动填充控件
 * 
 * @param url
 *            数据源
 * @param inputId
 *            页面中inputId
 * @param hiddenId
 *            页面中hiddenId
 * @param selectFunction
 *            选中后的回调函数
 * @param isMultiple
 *            是否多选
 * @param placeholder
 *            默认显示的提示语
 * @param contantFunction
 *            匹配方法
 */
function util_autocomplete(params){
	var url = params.url;
	var inputId = params.inputId;
	var hiddenId = params.hiddenId;
	var selectFunction = params.selectFunction;
	var isMultiple = params.isMultiple == undefined ? false : params.isMultiple;
	var placeholder = params.placeholder == undefined ? '输入后可选择内容' : params.placeholder;
	var contantFunction = params.contantFunction;
	var firstOnly = params.firstOnly == undefined ? true : params.firstOnly;
	
	if(isMultiple){
		$("#" + inputId).parent().append("<div style='margin-top:20px;' id='c_"+ inputId +"'/>");
	}else{
		if("\v"=="v") {
			$("#" + inputId)[0].onpropertychange = function(){
				if($("#" + inputId).val().trim() == ''){
					$("#" + hiddenId).val('');
					$("#" + hiddenId + "_name").val('');
				}
			};
		}else{
			$("#" + inputId)[0].addEventListener("input",function(){
				if($("#" + inputId).val().trim() == ''){
					$("#" + hiddenId).val('');
					$("#" + hiddenId + "_name").val('');
				}	
			},false);
		}
	}
	$("#" + inputId).attr("placeholder", placeholder);
	$("#" + inputId).attr("autocomplete", true);
	$("#" + inputId).attr("hiddenid", hiddenId);
	$("#" + inputId).parent().append("<input type='hidden' id='"+ hiddenId +"' name='"+ hiddenId +"'/>");
	$("#" + inputId).parent().append("<input type='hidden' id='"+ hiddenId +"_name' name='"+ hiddenId +"_name'/>");
	// 自动填充
	$.ajax({
		type : "POST",
		async : false,
		url : url,
		dataType : "json",
		success : function(result) {
			$("#"+inputId).autocomplete({
				source: function( request, response ) {
					if(isMultiple){
						request.term = request.term.split(',').pop();
					}
			        var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
			        response( $.grep( result, function( value ) {
			        	var py = value.pyname;
			        	var temp = value.label.trim().toUpperCase();
			        	 if(contantFunction){
				        	contantFunction(value);
				         }else{
				        	if(isMultiple){
				        		value.label = value.label.split(',').pop();
				        		if(firstOnly){
				        			if(temp.indexOf(request.term.trim().toUpperCase()) == 0 || py.indexOf(request.term.trim().toUpperCase()) == 0){
				        				return true;
				        			}
				        			return false;
				        		}else{
				        			return ((matcher.test(value.label) || _contans(request.term.trim().toUpperCase(), value.pyname)) && ($("#" + hiddenId).val().indexOf(value.id) == -1));
				        		}
				        	}else{
				        		if(firstOnly){
				        			if(temp.indexOf(request.term.trim().toUpperCase()) == 0 || py.indexOf(request.term.trim().toUpperCase()) == 0){
				        				return true;
				        			}
				        			return false;
				        		}else{
				        			return matcher.test( value.label ) || _contans(request.term.trim().toUpperCase(), value.pyname);
				        		}
				        	}
				         }
			        }));
			        
		        },								        
				dataType : 'json',
				selectFirst : true,
				autoFocus : true,
				matchContains: true,
				close: function( event, ui ) {
					/*
					 * if(isMultiple){ $("#" + inputId).val(''); }
					 */
				},
				select: function(e,ui){
					if(isMultiple){
						var terms = this.value.split(",");
						terms.pop();
						terms.push(ui.item.value);
						terms.push("");
						this.value = terms.join( "," );
						
						if($("#" + hiddenId).val()==''){
							$("#" + hiddenId).val(ui.item.id);
							$("#" + hiddenId + "_name").val(ui.item.value);
						}else{
							$("#" + hiddenId).val($("#" + hiddenId).val() + ',' + ui.item.id);
							$("#" + hiddenId + "_name").val($("#" + hiddenId + "_name").val() + ',' + ui.item.value);
						}
						_createAutocompleteSpan(inputId, hiddenId, ui.item.id, ui.item.value);
						return false;
					}else{
						$("#" + hiddenId).val(ui.item.id);		
						$("#" + hiddenId + "_name").val(ui.item.value);
					}
					// 回调函数
					if(typeof selectFunction=='function'){
						selectFunction(e,ui);
					}
				}
			});
		}
	});
}
/**
 * 自动填充控件赋值
 * 
 * @param inputId
 *            页面中inputId
 * @param hideId
 *            页面中hideId
 * @param id
 *            数据的id(例:11,22,23)
 * @param name
 *            数据的显示内容(例:服务1,服务2,服务3)
 * @param isMultiple
 *            是否多选
 */
function util_autocompleteSetValue(params){
	var inputId = params.inputId;
	var hiddenId = params.hiddenId;
	var id = params.id;
	var name = params.name;
	var isMultiple = params.isMultiple == undefined ? false : params.isMultiple;
	if(isMultiple){
		if(id != undefined && name != undefined && id != '' && name != '' && id != null && name != null){
			if(id.substr(id.length-1) == ',')
				id = id.substr(0,id.length-1);
			if(name.substr(name.length-1) == ',')
				name = name.substr(0,name.length-1);
			var idArray = id.split(',');
			var nameArray = name.split(',');
			if(idArray.length == nameArray.length){
				$("#" + inputId).val(name+',');
				$("#" + hiddenId).val(id);
				$("#" + hiddenId + "_name").val(name);	
				for(var i=0;i<idArray.length;i++){
					_createAutocompleteSpan(inputId,hiddenId,idArray[i],nameArray[i]);
				}
			}else{
				$("#" + inputId).val('');
				$("#" + hiddenId).val('');
				$("#" + hiddenId + "_name").val('');
			}
		}else{
			$("#" + inputId).val('');
			$("#" + hiddenId).val('');
			$("#" + hiddenId + "_name").val('');
		}
	}else{
		$("#" + inputId).val(name);
		$("#" + hiddenId).val(id);
		$("#" + hiddenId + "_name").val(name);	
	}
	// ie浏览器会有选择框弹出为了解决这个问题加上
	$("#" + inputId).focus();
	$("#" + inputId).blur();
}
/**
 * ajax调用后台方法
 * 
 * @param url
 * @param jsonString
 *            json字符串参数
 * @param successFunction
 *            返回结果后的回调函数
 * @param dataType
 *            返回数据类型
 * @param contentType
 *            参数类型
 * @param async
 *            是否异步操作
 * @param errorFunction
 *            报错后的回调函数
 */
function util_ajaxCallMethod(params){
	var url = params.url;
	var jsonString = params.jsonString;
	var successFunction = params.successFunction;
	var dataType = params.dataType;
	var contentType = params.contentType;
	var async = params.async;
	var errorFunction = params.errorFunction;
	$.ajax({
		type : "POST",
		async : (async == undefined || String(async) == '' || async == null) ? false :async, 
		contentType : (contentType == undefined || contentType == '' || contentType == null) ? "application/json; charset=utf-8" : contentType,
		url : url,
		data:jsonString,
		dataType : (dataType == undefined || dataType == '' || dataType == null) ? "json" : dataType,
		success :function(result){
			if(typeof successFunction == 'function'){
				successFunction(result);
			}
		},
		error: errorFunction
	});
}

/**
 * 显示附件
 * 
 * @param sourceId
 * @param sourceEntity
 * @param removeFlag
 *            是否显示删除链接
 * @param divId
 *            指定显示的div
 */
function util_setFileDefaultValue(sourceId,sourceEntity,removeFlag,divId){
	// 默认附件
	$.ajax({
		type : "POST",
		url : pathString+"common/findAttachments",
		data : "sourceId=" + sourceId + "&sourceEntity="+sourceEntity,
		dataType : "json",
		success : function(result) {
			setDefaultValue(result, (removeFlag == undefined || String(removeFlag) == '' || removeFlag == null) ? true : removeFlag,divId);
		}
	});
}

/**
 * grid中显示div的title
 * 
 * @param rowdata
 * @param index
 * @param value
 * @returns {String}
 */
function util_defaultRender(rowdata, index, value) {
	if(value != null) {
		// html problem
		value = value.replace(/</g, '&lt;').replace(/>/g, '&gt;');
		return "<div title='" + value + "'>" + value + "<div>";
	}
}

/**
 * grid中显示密码为*
 * 
 * @param rowdata
 * @param index
 * @param value
 * @returns {String}
 */
function util_passwordRender(rowdata, index, value) {
	if(value != null) {
		if(rowdata.name!=undefined&&rowdata.name!=null&&rowdata.name.indexOf('PASSWORD')>=0){
			var tempValue = "";
			for(var i =0;i<rowdata.value.length;i++){
				tempValue+="*";
			}
			return tempValue;
		}else
			return "<div title='" + value + "'>" + value + "<div>";
	}
}

/**
 * 禁用grid行中的checkbox
 * 
 * @param gridName
 *            定义grid的div中的id
 * @param data
 *            数据集
 * @param name
 *            字段名称
 * @param expression
 *            条件表达式(逻辑符号加上值,如: =='管理员' 或者 >=100等等) 例：grid定义时增加属性 onAfterShowData :
 *            function(data){
 *            util_disabledCheckBox("grid",data,"NAME","=='管理员'"); }
 */
function util_disabledCheckBox(gridName,data,name,expression){
	for(var i in data.Rows){
		if(eval(data.Rows[i][name]+expression))
			$("#"+gridName+"grid .l-grid-body-table tbody tr").eq(i).find(".l-grid-row-cell-btn-checkbox").remove();
	}
}

/**
 * 获取framecenter
 * 
 * @param index
 *            向上寻找的次数
 * @returns
 */
function util_getFramecenter(index){
	index = index == undefined ? 5 : index;
	var parentString = "";
	var framecenter;
	for(var i =0;i<index;i++){
		if(eval(parentString+'liger.get("framecenter")')!=undefined){
			framecenter = eval(parentString+'liger.get("framecenter")');
			break;
		}else{
			parentString +='parent.';
		}
	}
	return framecenter;
}

/**
 * 
 * @returns 获取系统菜单id
 */
function util_getMenuId(){
	var framecenter = util_getFramecenter();
	if(framecenter!=undefined && framecenter!=null){
		return util_getFramecenter().selectedTabId;
	}
	return 0;
}

/**
 * 
 * @param allItems
 *            {'buttonid1': 需要调用的函数1,'buttonid2': 需要调用的函数2...}
 * @param buttontype
 *            不传默认为1表示grid工具栏
 * @returns {Array}
 */
function util_getToolBar(allItems,buttontype){
	var array = [];
	var index = 0;
	buttontype = buttontype == undefined ? 1 : buttontype;
	util_ajaxCallMethod({
		url : pathString+"common/findFunction",
		jsonString : JSON.stringify({
			menuid : util_getMenuId(),
			buttontype:buttontype
		}),
		successFunction : function(data) {
			for(var i =0;i<data.length;i++){
				if(allItems[data[i].ButtonId]!=undefined){
					var object = new Object();
					object.id=data[i].ButtonId;
					object.click = eval(allItems[data[i].ButtonId]);
					object.img = data[i].ButtonIcon;
					object.text = data[i].ButtonName;
					array[index] = object;
					index++;
				}
			}
		}
	});
	return array;
}

/**
 * 
 * @param allItems
 *            {'buttonid1': 需要调用的函数1,'buttonid2': 需要调用的函数2...}
 * @param buttontype
 *            不传默认为默认2，表示操作栏
 * @param rowdata
 *            行数据
 * @returns {String}
 */
var util_operationButtonsData=[];
function util_getOperationButtons(allItems,buttontype,rowdata){
	var result='';
	buttontype = buttontype == undefined ? 2 : buttontype;
	if(rowdata == undefined || rowdata.__previd == -1){
		util_ajaxCallMethod({
			url : pathString+"common/findFunction",
			jsonString : JSON.stringify({
				menuid : util_getMenuId(),
				buttontype:buttontype
			}),
			successFunction : function(data) {
				util_operationButtonsData = data;
			}
		});
	}
	for(var i =0;i<util_operationButtonsData.length;i++){
		if(allItems[util_operationButtonsData[i].ButtonId]!=undefined){
			var marginLeft = 0;
			var marginRight = 0;
			if(result!='')
				marginLeft+=8;
			if(rowdata!=undefined){
				if(rowdata.__previd==-1 && util_operationButtonsData[i].ButtonId == 'up' || rowdata.__nextid==undefined && util_operationButtonsData[i].ButtonId == 'down'){
					continue;
				}else if(rowdata.__previd==-1 && util_operationButtonsData[i].ButtonId == 'down'){
					marginLeft += 24;
				}else if(rowdata.__nextid==undefined && util_operationButtonsData[i].ButtonId == 'up'){
					marginRight += 24;
				}
			}
			result +="<img title='"+util_operationButtonsData[i].ButtonName+"' onclick='"+allItems[util_operationButtonsData[i].ButtonId]
			+"' style='margin-top:5px;cursor:pointer;margin-left:"+marginLeft+"px;margin-right:"+marginRight+"px;' src='"+util_operationButtonsData[i].ButtonIcon+"'/>";
		}
	}
	return result;
}

/**
 * 
 * @param tableName
 *            表名
 * @param columnName
 *            列名
 * @param rowdata
 *            行数据
 * @param type
 *            排序类型 up/down
 * @param grid
 * @param successFunction
 *            回调函数
 */
function util_dataSort(tableName,columnName,rowdata,type,grid,successFunction) {
	var params = {};
	params.id = rowdata.ID;
	params.value = rowdata.SORT;
	params.tableName = tableName;// 表名
	params.columnName = columnName;// 列名
	if (type == 'up') {
		params.targetId = grid.getRow(rowdata.__previd).ID;// 目标id
		params.targetValue = grid.getRow(rowdata.__previd).SORT;// 目标值
	} else {
		params.targetId = grid.getRow(rowdata.__nextid).ID;
		params.targetValue = grid.getRow(rowdata.__nextid).SORT;
	}
	$.ajax({
		type : "POST", url : pathString+"common/orderData"
			, data : JSON.stringify(params)
			, contentType : "application/json; charset=utf-8"
			, dataType : "text"
			, success : function(result) {
			if (result == 1) {
				if(typeof successFunction == 'function' ){
					successFunction();
				}
				$.ligerDialog.waitting('调整成功');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
				}, 1000);
			} else {
				$.ligerDialog.warn("调整失败");
			}
		}
	});
}

/**
 * 显示人员信息窗口
 * 
 * @param tableId
 *            显示表格的id
 * @param orgid
 *            组织或者人员id
 */
function util_showEmployeeInfo(params){
     	$.ajax({
		type : "POST",
		url : pathString+"common/findemployeeinfo",
		data : "orgid=" + params.orgId,
		dataType : "json",
		success : function(result) {
			$("#"+params.tableId).html("");
			$("#"+params.tableId).append("<tr style=\"border:1px solid #A3C0E8;height:25px;background:url('"+pathString+"resources/lib/ligerUI/skins/Aqua/images/layout/tabs-bg.gif') top repeat-x;\">"
				+"<td width=\"100px\" class=\"l-grid-hd-cell\">姓名</td>"
				+"<td width=\"150px\" class=\"l-grid-hd-cell\">工号</td>"
				+"<td width=\"150px\" class=\"l-grid-hd-cell\">电话</td>"
				+"<td width=\"150px\" class=\"l-grid-hd-cell\">手机</td>"
				+"<td class=\"l-grid-hd-cell\">邮箱</td>"
			    +"</tr>");								
			var str="";
			for(var i in result){
			str+="<tr style=\"border:1px solid #A3C0E8;height:25px;\"><td style='text-align:center;word-wrap:break-word;' class=\"l-grid-hd-cell\">"+ (result[i].name == null?"":result[i].name)
			                + "</td><td style='text-align:left;word-wrap:break-word;' class=\"l-grid-hd-cell\">"+ (result[i].worknumber==null?"":result[i].worknumber)
							+ "</td><td style='text-align:left;word-wrap:break-word;' class=\"l-grid-hd-cell\">"+ (result[i].phone==null?"":result[i].phone)
							+ "</td><td style='text-align:left;word-wrap:break-word;' class=\"l-grid-hd-cell\">"+ (result[i].mobilephone==null?"":result[i].mobilephone)
							+ "</td><td style='text-align:left;word-wrap:break-word;' class=\"l-grid-hd-cell\">"+ (result[i].mail==null?"":result[i].mail)
							+ "</td></tr>";
			}
			 $("#"+params.tableId).append(str);
		}
	});
}

/**
 * 
 * @param tabid
 * @param params
 *            格式：key::value;;key1::value1....
 */
function util_addFramecenterTab(tabid,params){
	var framecenter = util_getFramecenter();
	if(framecenter!=undefined){
		util_ajaxCallMethod({
			url : pathString+"common/getMenuById",
			jsonString : JSON.stringify({id:tabid}),
			successFunction : function(data) {
				var  url = data.url;
				if(params!=undefined && params!=""){
					url += "?"+params.replace(/::/g,'=').replace(/;;/g,'&');
				}
				if(data!=null){
					framecenter.addTabItem({                  
				        text: data.name,
				        tabid:tabid,
				        url: url,
				        reload : true
				        /*
						 * , callback: function () { addFrameSkinLink(tabid); }
						 */
				    });
				}
			}
		});
	}
}
