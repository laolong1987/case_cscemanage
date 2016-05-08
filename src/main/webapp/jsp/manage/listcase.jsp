<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 16/3/6
  Time: 下午12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.web.entity.User" %>
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
    var role=null;
    $(function() {
        $("#searchbtn").ligerButton({
            click : function() {
                search();
            }
        });

        <%
        User user= (User)request.getSession().getAttribute("user");
        int role =0;
        if(null!=user){
          role=user.getRole();
        }
        %>
        role='<%=role%>';
        setGrid();
        initSaveForm();

        $("#pageloading").hide();
    });
    function initSaveForm(){
        saveForm = $("#saveForm").ligerForm({
            inputWidth : 170,
            labelWidth : 200,
            space : 45,
            validate : true,
            fields : [
                {display : "服务类型", name : "typename", type : "label", group : "明细", groupicon : icon},
                {display : "开case时间", name : "createtime", type : "label"},
                {display : "患者姓名", name : "name", type : "label",newline :true},
                {display : "性别", name : "sexname", type : "label"},
                {display : "申请人与患者关系(relationship to patient)", name : "relation", type : "label"},
                {display : "申请人姓名(applier name)", name : "createname", type : "label"},
                {display : "国家", name : "country", type : "label"},
                {display : "省", name : "province", type : "label"},
                {display : "城市", name : "city", type : "label"},
                {display : "详细地址", name : "address", type : "label"},
                {display : "首选电话", name : "phone1", type : "label"},
                {display : "备选电话", name : "phone2", type : "label"},
                {display : "邮箱", name : "email", type : "label"},
                {display : "联系时间", name : "phonetime", type : "label"},
                {display : "患者的情况和需求", name : "remark", type : "label"},
                {display : "患者当前的治疗医生的姓名", name : "doctor_name", type : "label"},
                {display : "患者当前的多治疗医生所处的医院", name : "doctor_hospital", type : "label"},
                {display : "患者当前的治疗医生的专科", name : "doctor_major", type : "label"},
                {display : "当前的Case Manager", name : "username", type : "label"},
                {display : "Note", name : "notecontent", type : "label"}
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
                    width : 100
                }, {
                    display : 'Case Manager',
                    name : 'username',
                    align : 'center',
                    width : 100,
                }, {
                    display : 'Country',
                    width : 80,
                    minWidth : 30,
                    align : 'center',
                    render: function (rowdata, rowindex, value)
                    {
                        if(null!=rowdata.country){
                            return rowdata.country
                        }else{
                            return "中国"
                        }
                    }
                },  {
                    display : 'City',
                    name : 'province',
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
                            return "Expert Medical Opinion"
                        }else if(rowdata.type==2){
                            return "Personal Healthy Advisory";
                        }else if(rowdata.type==3){
                            return "Stress Management";
                        }else if(rowdata.type==4){
                            return "Orientation and Navigation";
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
                            return "Completed";
                        }else if(rowdata.status==5){
                            return "Follow up";
                        }else{
                            return "Pending"
                        }
                }
                }, {
                    display: 'Action',
                    isSort: false,
                    isExport: false,
                    width: 250,
                    align : 'left',
                    render: function (rowdata, rowindex, value)
                    {
                        var html1 = '<a href="#" onclick="showassign(' + rowdata.id + ')">Assign</a> ';
                        var html2 = '<a href="#" onclick="updatestatus(' + rowdata.id + ',4)">Complete</a> ';
                        var html3 = '<a href="#" onclick="updatestatus(' + rowdata.id + ',5)">Follow up</a> ';
                        var html4 = '<a href="#" onclick="updatestatus(' + rowdata.id + ',3)">Cancel</a>';

                        if(1==role){
                            return html1+html2+html3+html4;
                        }else if(2==role){
                            return html1+html2+html3+html4;
                        }else if(3==role){
                            return ""
                        }else if(4==role){
                            return ""
                        }else{
                            return "";
                        }
                        <%--return "<img title='action' onclick='editColumn("+JSON.stringify(rowdata)+");' style='margin-top:5px;cursor:pointer;' src='${ctx}/ligerUI/skins/icons/editform.png'/>&nbsp;&nbsp;&nbsp;";--%>
                    }
                },{
                    display: 'note',
                    align : 'left',
                    width: 400,
                    render: function (rowdata, rowindex, value)
                    {
                        var a="Click me to type note";
                        if(null!=rowdata.note){
                            a = rowdata.note;
                        }
                        var html = "<a href='#' onclick='shownote("+JSON.stringify(rowdata)+")' >"+a+ "</a>";

                        if(1==role){
                            return html;
                        }else if(2==role){
                            return html;
                        }else if(3==role){
                            return html
                        }else if(4==role){
                            return ""
                        }else{
                            return "";
                        }

                        <%--return "<img title='note' onclick='shownote("+JSON.stringify(rowdata)+");' style='margin-top:5px;cursor:pointer;' src='${ctx}/ligerUI/skins/icons/editform.png'/>&nbsp;&nbsp;&nbsp;";--%>
                    }
                }
            ],
            pageSize : 20,
            pageSizeOptions:[10,20,30,40,50],
            url : "${ctx}/case/searchlist",
            rownumbers : true,
            checkbox : true,
            selectRowButtonOnly : true,
            isScroll : true
            <%
              if(1==role || 2==role){
              %>
            ,toolbar: {
                items: [{
                    id: 'delete',
                    text: '删除',
                    click: itemclick,
                    img: '${ctx}/ligerUI/skins/icons/busy.gif'
                }]
            }
            <%
              }
            %>
        });
    }

    function editColumn(data){
        var inputType = 'text';

        //  initSaveForm();
        showWindow();

        saveForm.setData({
            address: data.address,
            createtime: data.createtime,
            name: data.name,
            city : data.city,
            country : data.country,
            username : data.username,
            typename : data.typename,
            birthday : data.birthday,
            sexname : data.sexname,
            relation : data.relation,
            createname : data.createname,
            province : data.province,
            phone1 : data.phone1,
            phone2 : data.phone2,
            email : data.email,
            phonetime : data.phonetime,
            doctor_name : data.doctor_name,
            doctor_hospital : data.doctor_hospital,
            doctor_major : data.doctor_major,
            remark : data.remark,
            notecontent : data.note
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
                width : 600,
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
//                { display: 'id', name: 'id', width: 100 },
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
        <div class="l-panel-search-item">name</div>
        <div class="l-panel-search-item">
            <input type="text" id="queryname" name="queryname"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">country</div>
        <div class="l-panel-search-item">
            <input type="text" id="querycountry" name="querycountry"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">city</div>
        <div class="l-panel-search-item">
            <input type="text" id="querycity" name="querycity"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">service type</div>
        <div class="l-panel-search-item">
            <select name="queryservicetype" id="queryservicetype" >
                <option value="0">All</option>
                <option value="1">Expert Medical Opinion</option>
                <option value="2">Personal Healthy Advisory</option>
                <option value="3">Stress Management</option>
                <option value="4">Orientation and Navigation</option>
            </select>

        </div>
        <div class="l-panel-search-item">status</div>
        <div class="l-panel-search-item">
            <select name="querystatus" id="querystatus" >
                <option value="0">All</option>
                <option value="1">Pending</option>
                <option value="2">Assigned</option>
                <option value="3">Canceled</option>
                <option value="4">Completeed</option>
                <option value="5">Follow up</option>
            </select>
        </div>
        <div class="l-panel-search-item">
            <input type="button" id="searchbtn" value="search" />
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
        <textarea cols="100" rows="15" class="l-textarea"  style="width: 99%" id="note" name="note"></textarea>
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