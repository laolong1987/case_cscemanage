<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 16/3/1
  Time: 上午1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- jquery -->
<script type="text/javascript" src='${ctx}/js/jquery.min.js'></script>
<script type="text/javascript" src='${ctx}/js/util.js'></script>

<!-- ligerUI css -->
<link href="${ctx}/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<!-- ligerui -->
<script src="${ctx}/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${ctx}/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="${ctx}/ligerUI/js/ligerGrid.showFilter.js" type="text/javascript"></script>

<script src="${ctx}/js/jquery.cookie.js"></script>
<script src="${ctx}/js/json2.js"></script>

<script src="${ctx}/js/jquery-validation/jquery.validate.min.js"></script>
<script src="${ctx}/js/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery-validation/messages_cn.js" type="text/javascript"></script>
