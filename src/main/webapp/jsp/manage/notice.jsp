<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<html>
<head>
  <title>case通知</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<script type="text/javascript">
  var gridManager = null;
  var saveForm = null;
  var saveWindow = null;
  var typeData = null;
  var icon = '${ctx}/ligerUI/skins/icons/pager.gif';

  $(function () {
    $("#searchbtn").ligerButton({
      click: function () {
        search();
      }
    });

    setGrid();

    $("#pageloading").hide();
  });


  function setGrid() {
    //表格
    gridManager = $("#maingrid").ligerGrid({
      columns: [
        {
          display: '消息',
          isSort: false,
          isExport: false,
          width: 50,
          align: 'right',
          render: function (rowdata, rowindex, value) {
            return "<img title='修改' onclick='editColumn(" + JSON.stringify(rowdata) + ");' style='margin-top:5px;cursor:pointer;' src='${ctx}/ligerUI/skins/icons/editform.png'/>&nbsp;&nbsp;&nbsp;";
          }
        },
        {
          display: '时间',
          name: 'name',
          align: 'center',
          width: 100,
          minWidth: 30
        }, {
          display: '登录名',
          name: 'username',
          align: 'center',
          width: 150,
          minWidth: 60
        }, {
          display: '手机号',
          name: 'phone',
          width: 100,
          minWidth: 30,
          align: 'center'
        }, {
          display: '性别',
          name: 'gender',
          minWidth: 10,
          align: 'center',
          width: 50,
          render: function (rowdata) {
            if (rowdata.gender == 0) {
              return "男"
            } else if (rowdata.gender == 1) {
              return "女";
            } else {
              return "未知"
            }

          }
        },
        {
          display: '角色',
          name: 'position',
          align: 'center',
          render: function (rowdata) {
            if (rowdata.role == 0) {
              return "enterprise admin"
            } else if (rowdata.role == 1) {
              return "case director";
            } else if (rowdata.role == 2) {
              return "case coordinator";
            } else {
              return "case manager"
            }

          }
        },
        {
          display: '地址',
          name: 'address',
          align: 'left'
        },
        {
          display: '邮箱',
          name: 'email',
          align: 'left'
        },
        {
          display: '密码',
          name: 'state',
          align: 'center',
          render: function (rowdata) {
            var html = "<a href='javascript:void(0)' onclick='resetpwdclick(" + rowdata.id + ");' >重置</a>";
            return html;
          }
        },
        {
          display: '状态',
          name: 'state',
          align: 'center',
          render: function (rowdata) {
            if (rowdata.state == 0) {
              var html = "<a href='javascript:void(0)' onclick='resetState(" + rowdata.id + ",1);' >已关闭</a>";
              return html;
            } else if (rowdata.state == 1) {
              var html = "<a href='javascript:void(0)' onclick='resetState(" + rowdata.id + ",0);' >使用中</a>";
              return html;
            }
          }
        }
      ],
      pageSize: 15,
      url: "${ctx}/doctor/searchlist",
      rownumbers: true,
      checkbox: true,
      selectRowButtonOnly: true,
      isScroll: true,
      toolbar: {
        items: [{
          id: 'add',
          text: '增加',
          click: itemclick,
          img: '${ctx}/ligerUI/skins/icons/addpage.png'
        }, {
          id: 'delete',
          text: '删除',
          click: itemclick,
          img: '${ctx}/ligerUI/skins/icons/busy.gif'
        }]
      }
    });
  }

  function editColumn(data) {
    var inputType = 'text';

    //  initSaveForm();
    showWindow();

    saveForm.setData({
      patientid: data.id,
      name: data.name,
      username: data.username,
      phone1: data.phone1,
      email: data.email,
      address: data.address,
      sex: data.sex
    });

  }

  function search() {
    var parms = $("#queryForm").serializeJson();
    gridManager.set("parms", parms);
    gridManager.loadData();
  }

  function renderDescription(rowdata, index, value) {
    if (value != null) {
      return "<div title='" + value + "'>" + value + "<div>";
    }
  }

  function showWindow() {
    if (saveWindow == null) {
      saveWindow = $.ligerDialog.open({
        target: $("#addWindow"),
        width: 400,
        height: 'auto',
        isResize: true
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
            settingid: null
          });
          return;
        case "delete":
          var data = gridManager.getCheckedRows();
          if (data.length == 0) {
            $.ligerDialog.waitting('请选择行');
            setTimeout(function () {
              $.ligerDialog.closeWaitting();
            }, 500);
          } else {
            $.ligerDialog.confirm('确认要删除?', function (yes) {
              if (yes) {
                removeData(data);
              }
            });
          }
          return;
        case "export":
          exportExcel(gridManager, $("#queryForm"), '${ctx}');
          return;
      }
    }
  }

  function closeWindow() {
    saveWindow.hide();
  }

  function removeData(data) {
    $.ajax({
      type: "POST",
      url: "remove",
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "text",
      success: function (result) {
        if (result == 'success') {
          search();
          $.ligerDialog.waitting('删除成功');
          setTimeout(function () {
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
        type: "POST",
        url: "adduser",
//                data : JSON.stringify(params),
        data: params,
        dataType: "text",
        success: function (result) {
          if (result == 'success') {
            search();
            saveWindow.hide();
            $.ligerDialog.waitting('操作成功');
            setTimeout(function () {
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

  function resetpwdclick(id) {
    $.ligerDialog.confirm('是否重置密码为111111?', function (yes) {
      if (yes) {
        resetpwd(id);
      }
    });
  }

  function resetpwd(id) {
    $.ajax({
      url: "resetpwd",
      data: {id: id},
      type: "POST",
      success: function (result) {
        $.ligerDialog.success("重置成功", "提示", function () {
          $.ligerDialog.hide();
          search();
        });
      }
    });
  }

  function resetState(id,state) {
    $.ligerDialog.confirm('是否重置账户状态?', function (yes) {
      if (yes) {
        $.ajax({
          url: "resetstate",
          data: {"id": id,"state":state},
          type: "POST",
          success: function (result) {
            $.ligerDialog.success("重置成功", "提示", function () {
              $.ligerDialog.hide();
              search();
            });
          }
        });
      }
    });

  }
</script>
</head>
<body style="padding: 5px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
<form id="queryForm">
  <div class="l-panel-search">
    <div class="l-panel-search-item">姓名</div>
    <div class="l-panel-search-item">
      <input type="text" id="queryname" name="queryname" class="liger-textbox"/>
    </div>
    <div class="l-panel-search-item">手机号</div>
    <div class="l-panel-search-item">
      <input type="text" id="queryphone1" name="queryphone1"
             class="liger-textbox"/>
    </div>
    <div class="l-panel-search-item">
      <input type="button" id="searchbtn" value="查询"/>
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
          <div class="l-dialog-btn-inner" onclick="closeWindow();">取消</div>
        </div>
        <div class="l-dialog-btn">
          <div class="l-dialog-btn-l"></div>
          <div class="l-dialog-btn-r"></div>
          <div class="l-dialog-btn-inner" onclick="save();">确定</div>
        </div>
        <div class="l-clear"></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
