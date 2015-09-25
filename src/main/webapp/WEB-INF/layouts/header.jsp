<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- BEGIN HEADER -->
<div class="header navbar navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="header-inner">
		<!-- BEGIN LOGO -->
		<a class="navbar-brand" href="${ctx}/device" style="width:445px;">
			<img src="${ctx}/assets/img/logo.png"  alt="logo" class="img-responsive" style="height:42px;margin:5px"/>
		</a>
		
		<!-- <h2 class="navbar-brand" style="color:white;margin-left:10px;">互动营销管理平台</h2> -->
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<img src="${ctx}/assets/img/menu-toggler.png"  alt=""/>
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<ul class="nav navbar-nav pull-right">
			<!-- BEGIN NOTIFICATION DROPDOWN -->
			<%-- <li class="dropdown" id="header_notification_bar">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-warning"></i>
					<span class="badge">
						 6
					</span>
				</a>
				<ul class="dropdown-menu extended notification">
					<li>
						<p>
							 2个通知
						</p>
					</li>
					<li>
						<ul class="dropdown-menu-list scroller" style="height: 250px;">
							<li>
								<a href="#">
									<span class="label label-icon label-success">
										<i class="fa fa-plus"></i>
									</span>
									 新用户注册
									<span class="time">
										 刚刚
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="label label-icon label-danger">
										<i class="fa fa-bolt"></i>
									</span>
									 有审批事项
									<span class="time">
										 15分钟前
									</span>
								</a>
							</li>
						</ul>
					</li>
					<li class="external">
						<a href="#">
							 查看所有 <i class="m-icon-swapright"></i>
						</a>
					</li>
				</ul>
			</li>
			<!-- END NOTIFICATION DROPDOWN -->
			<!-- BEGIN INBOX DROPDOWN -->
			<li class="dropdown" id="header_inbox_bar">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-envelope"></i>
					<span class="badge">
						 5
					</span>
				</a>
				<ul class="dropdown-menu extended inbox">
					<li>
						<p>
							 12条新消息
						</p>
					</li>
					<li>
						<ul class="dropdown-menu-list scroller" style="height: 250px;">
							<li>
								<a href="inbox.html-a=view.htm" >
									<span class="photo">
										<img src="${ctx}/assets/img/avatar2.jpg"  alt=""/>
									</span>
									<span class="subject">
										<span class="from">
											 李雷
										</span>
										<span class="time">
											 刚刚
										</span>
									</span>
									<span class="message">
										 有更新
									</span>
								</a>
							</li>
						</ul>
					</li>
					<li class="external">
						<a href="inbox.html" >
							 查看所有消息 <i class="m-icon-swapright"></i>
						</a>
					</li>
				</ul>
			</li>
			<!-- END INBOX DROPDOWN -->
			<!-- BEGIN TODO DROPDOWN -->
			<li class="dropdown" id="header_task_bar">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-tasks"></i>
					<span class="badge">
						 5
					</span>
				</a>
				<ul class="dropdown-menu extended tasks">
					<li>
						<p>
							 12个任务
						</p>
					</li>
					<li>
						<ul class="dropdown-menu-list scroller" style="height: 250px;">
							<li>
								<a href="#">
									<span class="task">
										<span class="desc">
											 新版本发布
										</span>
										<span class="percent">
											 30%
										</span>
									</span>
									<span class="progress">
										<span style="width: 40%;" class="progress-bar progress-bar-success" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100">
											<span class="sr-only">
												 已完成40%
											</span>
										</span>
									</span>
								</a>
							</li>
						</ul>
					</li>
					<li class="external">
						<a href="#">
							 查看所有任务 <i class="m-icon-swapright"></i>
						</a>
					</li>
				</ul>
			</li> --%>
			<!-- END TODO DROPDOWN -->
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<%-- <img alt="" src="${ctx}/assets/img/avatar1_small.jpg" /> --%>
					<i class="fa fa-user" style="color:#ffffff"></i>
					<span class="username">
						 <shiro:principal/>
					</span>
					<i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<!-- <li>
						<a href="extra_profile.html" >
							<i class="fa fa-user"></i> 个人资料
						</a>
					</li>
					<li>
						<a href="page_calendar.html" >
							<i class="fa fa-calendar"></i> 日历
						</a>
					</li>
					<li>
						<a href="inbox.html" >
							<i class="fa fa-envelope"></i> 消息
							<span class="badge badge-danger">
								 3
							</span>
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-tasks"></i> 任务
							<span class="badge badge-success">
								 7
							</span>
						</a>
					</li>
					<li class="divider">
					</li> -->
					<li>
						<a href="${ctx}/user/updatePwd/<shiro:principal property="id"/>">
							<i class="fa fa-edit"></i> 修改密码
						</a>
					</li>
					<li>
						<a href="${ctx}/logout">
							<i class="fa fa-key"></i> 登出
						</a>
					</li>
				</ul>
			</li>
			<!-- END USER LOGIN DROPDOWN -->
		</ul>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->