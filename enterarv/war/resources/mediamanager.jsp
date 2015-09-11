<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

<%@include file="jsp/topbar.jsp"%>

<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>

	<div class="small-10 medium-8 large-9 columns">
		<div id="content">
			<h1 class="main-title"><%=registry.getStringOfLanguage("media.title", language)%></h1>

			<div id="mediamanager">

				<div id="msg_alert">
					<img id="msg_alert_progress" src="<%=resPath%>images/progress.gif">
					<span>Message</span>
				</div>

				<div class="row left">
					<ul id="top_bar" class="inline-list" style="margin-bottom: 0px;">
						<li><small>&nbsp;</small>
							<ul class="inline-list">
								<li class="divider"><a href="#"> <i class="fa fa-3x">|</i>
								</a></li>
							</ul></li>

						<li><small>&nbsp;</small>
							<ul class="inline-list">
								<li><a class="item" href="javascript:getList('0')"> <i
										class="fa fa-home fa-3x"></i>
								</a></li>
							</ul></li>

						<li><small>&nbsp;</small>
							<ul class="inline-list">
								<li class="divider"><a href="#"> <i class="fa fa-3x">|</i>
								</a></li>
							</ul></li>

						<li><small><%=registry.getStringOfLanguage("media.folder_tool",
					language)%></small>
							<ul class="inline-list">
								<li><a class="item" href="javascript:updateFolder('add')">
										<i class="fa fi-folder-add fa-3x"></i>
								</a></li>

								<li><a class="item" href="javascript:updateFolder('edit')">
										<i class="fa fi-pencil fa-3x"></i>
								</a></li>

								<li><a class="item"
									href="javascript:updateFolder('delete')"> <i
										class="fa fi-x fa-3x"></i>
								</a></li>
							</ul></li>

						<li><small>&nbsp;</small>
							<ul class="inline-list">
								<li class="divider"><a href="#"> <i class="fa fa-3x">|</i>
								</a></li>
							</ul></li>

						<li><small><%=registry.getStringOfLanguage("media.file_tool", language)%></small>
							<ul class="inline-list">
								<li><a class="item" href="javascript:uploadFile()"> <i
										class="fa fi-page-add fa-3x"></i>
								</a></li>

								<li><a class="item" href="javascript:updateFile('edit')">
										<i class="fa fi-page-edit fa-3x"></i>
								</a></li>

								<li><a class="item" href="javascript:updateFile('delete')">
										<i class="fa fi-page-delete fa-3x"></i>
								</a></li>
							</ul></li>

						<li><small>&nbsp;</small>
							<ul class="inline-list">
								<li class="divider"><a href="#"> <i class="fa fa-3x">|</i>
								</a></li>
							</ul></li>

						<li><small>&nbsp;</small> <i class="fa fa-2x"><%=registry.getStringOfLanguage("media.desc", language)%></i>
						</li>
					</ul>
				</div>

				<div class="row left media_manager">
					<div class="large-3 columns">

						<ul class="side-nav explorer" id="explorer">
							<li><a href="javascript:getList('0')" id="parent_folder0"><i
									class="fa fi-folder-lock fa-2x">&nbsp;/</i></a>
								<ul id="folder0"></ul></li>
						</ul>
					</div>

					<div class="large-9 columns" style="padding-top: 20px;"
						id="table_container">
						<table id="media_table" class="display" cellspacing="0"
							cellpadding="0" width="100%">
							<thead>
								<tr>
									<th><%=registry.getStringOfLanguage("media.filename", language)%></th>
									<th width="100"><%=registry.getStringOfLanguage("media.size", language)%></th>
									<th width="200"><%=registry.getStringOfLanguage("media.create_time",
					language)%></th>
								</tr>
							</thead>

							<tbody id="filelist">
							</tbody>

						</table>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>

<div id="uploadFileModal" class="reveal-modal" data-reveal></div>

<input type="hidden" id="basePath" value="<%=basePath%>" />
<input type="hidden" id="resPath1" value="<%=resPath%>" />

<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
<script
	src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>

<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>

<script src="<%=resPath%>js/jquery/jquery.fileDownload.js"></script>
<script src="<%=resPath%>js/jquery/jquery.form.js"></script>

<script src="<%=resPath%>js/jquery/jquery.mousewheel-3.0.6.pack.js"></script>
<script src="<%=resPath%>js/fancybox/jquery.fancybox.pack.js"></script>
<script src="<%=resPath%>js/fancybox/jquery.fancybox-buttons.js"></script>
<script src="<%=resPath%>js/fancybox/jquery.fancybox-media.js"></script>
<script src="<%=resPath%>js/fancybox/jquery.fancybox-thumbs.js"></script>

<script src="<%=resPath%>js/app.js"></script>
<script src="<%=resPath%>js/mediamanager.js"></script>

<%@include file="jsp/footer.jsp"%>