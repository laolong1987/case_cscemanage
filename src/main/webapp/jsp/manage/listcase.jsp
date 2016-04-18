<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 16/3/6
  Time: 下午12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp"%>
<html>
<head>
    <title>患者管理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<script type="text/javascript">
    var gridManager = null;
    var saveForm = null;
    var saveWindow = null;
    var assignWindow = null;
    var noteWindow = null;
    var typeData = null;
    var icon = '${ctx}/ligerUI/skins/icons/pager.gif';
    var condition = { fields: [{ name: 'name', label: 'name',width:90,type:'text' }] };

    $(function() {
        $("#searchbtn").ligerButton({
            click : function() {
                search();
            }
        });

        setGrid();
        initSaveForm();

        $("#pageloading").hide();
    });
    function initSaveForm(){
        saveForm = $("#saveForm").ligerForm({
            inputWidth : 170,
            labelWidth : 100,
            space : 45,
            validate : true,
            fields : [
                {display : "name", name : "name", type : "label", group : "明细", groupicon : icon},
                {display : "birthday", name : "birthday", type : "label"},
                {display : "remark", name : "remark", type : "label"}
          ]
        });
    }
    function setGrid(){
        //表格
        gridManager = $("#maingrid").ligerGrid({
            columns : [
                {
                    display : 'CaseID',
                    name : 'id',
                    align : 'center',
                    width : 80,
                    minWidth : 30,
                    render: function (rowdata, rowindex, value)
                    {
                        return "<a href='#'  onclick='editColumn("+JSON.stringify(rowdata)+");' >"+rowdata.id+"</a>";
                    }
                },{
                    display : 'Name',
                    name : 'name',
                    align : 'center',
                    minWidth : 60
                }, {
                    display : 'Case Manager',
                    name : 'username',
                    align : 'center',
                    minWidth : 60
                }, {
                    display : 'Country',
                    name : 'country',
                    width : 80,
                    minWidth : 30,
                    align : 'center'
                },  {
                    display : 'City',
                    name : 'city',
                    width : 80,
                    minWidth : 30,
                    align : 'center'
                }, {
                    display : 'Service Type',
                    name : 'type',
                    minWidth : 10,
                    align : 'center',
                    width : 200,
                    render : function(rowdata, rowindex, value) {
                        if(rowdata.type==1){
                            return "Expert Medical Report"
                        }else if(rowdata.type==2){
                            return "Personal Healthy Advisory";
                        }else if(rowdata.type==3){
                            return "Stress Management";
                        }else if(rowdata.type==4){
                            return "Oientation And Navigation";
                        }else{
                            return ""
                        }
                }

                },{
                    display : 'Status',
                    name : 'status',
                    minWidth : 10,
                    align : 'center',
                    width : 100,
                    render : function(rowdata, rowindex, value) {
                        if(rowdata.status==1){
                            return "Pending"
                        }else if(rowdata.status==2){
                            return "Assigned";
                        }else if(rowdata.status==3){
                            return "Canceled";
                        }else if(rowdata.status==4){
                            return "Completeed";
                        }else if(rowdata.status==5){
                            return "Followed";
                        }else{
                            return "Pending"
                        }
                }
                }, {
                    display: 'Action',
                    isSort: false,
                    isExport: false,
                    width: 200,
                    align : 'right',
                    render: function (rowdata, rowindex, value)
                    {
                        var html1 = '<a href="#" onclick="showassign(2)">Assign</a> ';
                        var html2 = '<a href="#" onclick="updatestatus(' + rowdata.id + ',4)">Complete</a> ';
                        var html3 = '<a href="#" onclick="updatestatus(' + rowdata.id + ',3)">Cancel</a> ';
                        var html4 = '<a href="#" onclick="updatestatus(' + rowdata.id + ',5)">Follow up</a> ';
                        return html1+html2+html3+html4;
                        <%--return "<img title='action' onclick='editColumn("+JSON.stringify(rowdata)+");' style='margin-top:5px;cursor:pointer;' src='${ctx}/ligerUI/skins/icons/editform.png'/>&nbsp;&nbsp;&nbsp;";--%>
                    }
                },{
                    display: 'note',
                    isSort: false,
                    isExport: false,
                    width: 40,
                    align : 'right',
                    render: function (rowdata, rowindex, value)
                    {
                        return "<img title='note' onclick='shownote("+JSON.stringify(rowdata)+");' style='margin-top:5px;cursor:pointer;' src='${ctx}/ligerUI/skins/icons/editform.png'/>&nbsp;&nbsp;&nbsp;";
                    }
                }
            ],
            pageSize : 15,
            url : "${ctx}/case/searchlist",
            rownumbers : true,
            checkbox : true,
            selectRowButtonOnly : true,
            isScroll : true
        });
    }

    function editColumn(data){
        var inputType = 'text';

        //  initSaveForm();
        showWindow();

        saveForm.setData({
            address: data.address,
            name: data.name,
            username : data.username,
            remark : data.remark
        });

    }

    function search(){
        var parms = $("#queryForm").serializeJson();
        gridManager.set("parms", parms);
        gridManager.loadData();
    }

    function renderDescription(rowdata, index, value) {
        if(value != null) {
            return "<div title='" + value + "'>" + value + "<div>";
        }
    }

    function showWindow(){
        if (saveWindow == null) {
            saveWindow = $.ligerDialog.open({
                target : $("#addWindow"),
                width : 400,
                height : 'auto',
                isResize : true
            });
        }
        $("#saveForm")[0].reset();
        saveForm.hideAllFieldError();
        //$("#saveForm").resetStyle();
        saveWindow.show();
    }

    function itemclick(item) {
        if (item.id) {
            switch (item.id) {
                case "add":
                    initSaveForm('text');
                    showWindow();
                    saveForm.setData({
                        settingid : null
                    });
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0) {
                        $.ligerDialog.waitting('请选择行');
                        setTimeout(function() {
                            $.ligerDialog.closeWaitting();
                        }, 500);
                    } else {
                        $.ligerDialog.confirm('确认要删除?', function(yes) {
                            if (yes) {
                                removeData(data);
                            }
                        });
                    }
                    return;
                case "export":
                    exportExcel(gridManager, $("#queryForm"), '${ctx}');
                    return ;
            }
        }
    }

    function closeWindow() {
        saveWindow.hide();
    }

    function removeData(data) {
        $.ajax({
            type : "POST",
            url : "remove",
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType : "text",
            success : function(result) {
                if (result == 'success') {
                    search();
                    $.ligerDialog.waitting('删除成功');
                    setTimeout(function() {
                        $.ligerDialog.closeWaitting();
                    }, 500);
                } else {
                    $.ligerDialog.warn(result);
                }
            }
        });
    }

    function save() {
        if (saveForm.valid()) {
            $("#pageloading").show();
            var params = saveForm.getData();
            $.ajax({
                type : "POST",
                url : "savepatient",
//                data : JSON.stringify(params),
                data : params,
                dataType : "text",
                success : function(result) {
                    if (result == 'success') {
                        search();
                        saveWindow.hide();
                        $.ligerDialog.waitting('操作成功');
                        setTimeout(function() {
                            $.ligerDialog.closeWaitting();
                        }, 500);
                    } else {
                        $.ligerDialog.warn(result);
                    }
                    $("#pageloading").hide();
                }
            });
        }
    }

    function updatestatus(id,status) {
        $.ajax({
            type : "POST",
            url : "updatestatus",
            data : {'id':id,'status':status},
            dataType : "text",
            success : function(result) {
                if (result == 'success') {
                    search();
                    $.ligerDialog.waitting('success');
                    setTimeout(function() {
                        $.ligerDialog.closeWaitting();
                    }, 500);
                } else {
                    $.ligerDialog.warn(result);
                }
            }
        });
    }

    function getGridOptions()
    {
        var options = {
            columns: [
                { display: 'id', name: 'id', width: 100 },
                { display: '名称', name: 'name', width: 100 }
            ], switchPageSizeApplyComboBox: false,
            url: "${ctx}/doctor/searchlist",
            pageSize: 10
        };
        return options;
    }

    function showassign(id){
        $("#txt2").ligerComboBox({
            width: 250,
            slide: false,
            selectBoxWidth: 500,
            selectBoxHeight: 240,
            valueField: 'id',
            textField: 'name',
            grid: getGridOptions(),
            condition: condition
        });

        if (assignWindow == null) {
            assignWindow = $.ligerDialog.open({
                title : "assign",
                target : $("#assignWindow"),
                width : 400,
                height : 'auto',
                isResize : true
            });
        }
        $("#upassignid").val(id);
        assignWindow.show();
    }

    function assign(){
        var upid = $("#upassignid").val();
        var userid = $("#txt2_val").val();

        $.ajax({
            type : "POST",
            url : "updateuserid",
            data : {'id':upid,'userid':userid},
            dataType : "text",
            success : function(result) {
                if (result == 'success') {
                    search();
                    $.ligerDialog.waitting('success');
                    setTimeout(function() {
                        $.ligerDialog.closeWaitting();
                    }, 500);
                } else {
                    $.ligerDialog.warn(result);
                }
            }
        });
        closeassignWindow();
    }


    function closeassignWindow() {
        assignWindow.hide();
    }
    function closenoteWindow() {
        noteWindow.hide();
    }

    function shownote(data){
        if (noteWindow == null) {
            noteWindow = $.ligerDialog.open({
                title : "note",
                target : $("#noteWindow"),
                width : 600,
                height : 'auto',
                isResize : true
            });
        }
        $("#upnoteid").val(data.id);
        $("#note").val(data.note);
        noteWindow.show();
    }

    function addnote(){
        var upid = $("#upnoteid").val();
        var note = $("#note").val();

        $.ajax({
            type : "POST",
            url : "updatenote",
            data : {'id':upid,'note':note},
            dataType : "text",
            success : function(result) {
                if (result == 'success') {
                    search();
                    $.ligerDialog.waitting('success');
                    setTimeout(function() {
                        $.ligerDialog.closeWaitting();
                    }, 500);
                } else {
                    $.ligerDialog.warn(result);
                }
            }
        });
        closenoteWindow();
    }
</script>
</head>
<body style="padding: 5px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
<form id="queryForm">
    <div class="l-panel-search">
        <div class="l-panel-search-item">姓名</div>
        <div class="l-panel-search-item">
            <input type="text" id="queryname" name="queryname"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">手机号</div>
        <div class="l-panel-search-item">
            <input type="text" id="queryphone1" name="queryphone1"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">
            <input type="button" id="searchbtn" value="查询" />
        </div>
    </div>
</form>



<!-- 表格 -->
<div id="maingrid" style="margin:0; padding:0"></div>
<div style="display:none;"></div>
<div id="addWindow" style="width:99%; margin:3px; display:none;">
    <div class="l-dialog-body" style="width: 100%;">
        <form id="saveForm"></form>
        <div class="l-dialog-buttons">
            <div class="l-dialog-buttons-inner">
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="closeWindow();">关闭</div>
                </div>
                <div class="l-clear"></div>
            </div>
        </div>
    </div>
</div>


<div id="assignWindow" style="width:99%; margin:3px; display:none;">
    <div class="l-dialog-body" style="width: 100%;">
        <input type="text" id="txt2" />
        <input type="hidden" id="upassignid" name="upassignid" />
        <div class="l-dialog-buttons">
            <div class="l-dialog-buttons-inner">
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="closeassignWindow();">取消</div>
                </div>
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="assign();">确定</div>
                </div>
                <div class="l-clear"></div>
            </div>
        </div>
    </div>
</div>



<div id="noteWindow" style="width:99%; margin:3px; display:none;">
    <div class="l-dialog-body" style="width: 100%;">
        <textarea cols="100" rows="4" class="l-textarea"  id="note" name="note"></textarea>
        <input type="hidden" id="upnoteid" name="upnoteid" />
        <div class="l-dialog-buttons">
            <div class="l-dialog-buttons-inner">
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="closenoteWindow();">取消</div>
                </div>
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="addnote();">确定</div>
                </div>
                <div class="l-clear"></div>
            </div>
        </div>
    </div>
</div>

</body>
</html>