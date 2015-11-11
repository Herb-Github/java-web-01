<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
  <div class="am-offcanvas-bar admin-offcanvas-bar">
    <ul class="am-list admin-sidebar-list">
      <li><a href="${ctx}/main/"><span class="am-icon-home"></span> 首页</a></li>
      <li><a href="${ctx}/user/" class="am-cf"><span class="am-icon-users"></span> 用户<span
              class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
      <li><a href="${ctx}/role/"><span class="am-icon-gear"></span> 角色</a></li>
      <li><a href="${ctx}/authority/"><span class="am-icon-trello"></span> 权限</a></li>
      <li><a href="${ctx}/server/"><span class="am-icon-server"></span> 机器</a></li>
      <li><a href="${ctx}/service/"><span class="am-icon-table"></span> 服务</a></li>
      <li><a href="${ctx}/logout"><span class="am-icon-sign-out"></span> 退出</a></li>
    </ul>
    <div class="am-panel am-panel-default admin-sidebar-panel">
      <div class="am-panel-bd">
        <p><span class="am-icon-tag"></span> wiki</p>

        <p>Welcome to the Amaze UI wiki!</p>
      </div>
    </div>
  </div>
</div>
