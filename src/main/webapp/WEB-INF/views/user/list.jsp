<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<div class="am-cf am-padding">
  <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">用户</strong> /
    <small>列表</small>
  </div>
</div>

<div class="am-g">
  <form action="${ctx}/user/">
    <div class="am-u-sm-12 am-u-md-6">
      <div class="am-btn-toolbar">
        <div class="am-btn-group am-btn-group-xs">
          <a type="button" class="am-btn am-btn-default" href="${ctx}/user/new"><span class="am-icon-plus"></span>
            新增</a>
          <button type="button" class="am-btn am-btn-default"><span class="am-icon-save"></span> 保存</button>
          <button type="button" class="am-btn am-btn-default"><span class="am-icon-archive"></span> 审核</button>
          <button type="button" class="am-btn am-btn-default"><span class="am-icon-trash-o"></span> 删除</button>
        </div>
      </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
      <div class="am-form-group">
        <select data-am-selected="{btnSize: 'sm'}">
          <option value="option1">所有类别</option>
          <option value="option2">IT业界</option>
          <option value="option3">数码产品</option>
          <option value="option3">笔记本电脑</option>
          <option value="option3">平板电脑</option>
          <option value="option3">只能手机</option>
          <option value="option3">超极本</option>
        </select>
      </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
      <div class="am-input-group am-input-group-sm">
        <input type="text" name="criteria_LIKE_userName" value="${param.criteria_LIKE_userName}"
               class="am-form-field">
            <span class="am-input-group-btn">
              <button class="am-btn am-btn-default" type="submit">搜索</button>
            </span>
      </div>
    </div>
  </form>
</div>

<div class="am-g">
  <div class="am-u-sm-12">
    <form class="am-form">
      <table class="am-table am-table-striped am-table-hover table-main">
        <thead>
        <tr>
          <th class="table-check"><input type="checkbox"/></th>
          <th class="table-id">id</th>
          <th class="table-title">用户名</th>
          <th class="table-type">邮箱</th>
          <th class="table-author am-hide-sm-only">创建日期</th>
          <th class="table-date am-hide-sm-only">修改日期</th>
          <th class="table-set">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users.content}" var="user" varStatus="status">
          <tr>
            <td><input type="checkbox"/></td>
            <td>${status.index + 1}</td>
            <td><a href="#">${user.userName}</a></td>
            <td>${user.email}</td>
            <td class="am-hide-sm-only"><fmt:formatDate value="${user.creationTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td class="am-hide-sm-only"><fmt:formatDate value="${user.modifiedTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
              <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                  <a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="${ctx}/user/edit/${user.id}"><span
                          class="am-icon-pencil-square-o"></span> 编辑
                  </a>
                  <button class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span class="am-icon-copy"></span> 复制
                  </button>
                  <a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span
                          class="am-icon-trash-o"></span> 删除
                  </a>
                </div>
              </div>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <tags:page page="${users}" paginationSize="5"/>
      <hr/>
      <p>注：.....</p>
    </form>
  </div>
</div>
