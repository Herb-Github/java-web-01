<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="selectedMenu" scope="request">
  <sitemesh:getProperty property='meta.menu'/>
</c:set>
<c:set var="subMenu" scope="request">
  <sitemesh:getProperty property='meta.subMenu'/>
</c:set>

<html><!DOCTYPE html>

<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
  <meta charset="utf-8"/>
  <title><sitemesh:title/></title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
  <meta content="" name="description"/>
  <meta content="" name="author"/>

  <link rel="shortcut icon" href="${ctx }/favicon.ico"/>

  <sitemesh:head/>
</head>
<body class="page-header-fixed">
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
  <%@ include file="/WEB-INF/layouts/menu.jsp" %>
  <!-- BEGIN CONTENT -->
  <div class="page-content-wrapper">
    <sitemesh:body/>
  </div>

  <%@ include file="/WEB-INF/layouts/loading.jsp" %>
</div>
<%@ include file="/WEB-INF/layouts/footer.jsp" %>

<sitemesh:getProperty property="page.script"></sitemesh:getProperty>
<script>
  jQuery(document).ready(function () {
  });
</script>
</html>