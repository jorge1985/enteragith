<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Utils"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	Registry registry_Media = Registry.getInstance();
	SessionHandler handler_Media = SessionHandler.getInstance();
	String language_Media = handler_Media.getLanguage(session);
	String basePath_Media = request.getContextPath()+"/";
	String resPath_Media = basePath_Media + "resources/";
%>

<div id="mediamanager">
	
	<div class="row left">
		<ul id="top_bar" class="inline-list" style="margin-bottom: 0px;">
			<li>
				<small>&nbsp;</small>
				<ul class="inline-list">
					<li class="divider">
					<a href="#">
				    	<i class="fa fa-3x">|</i>
				  	</a>
				  	</li>
				</ul>								
			</li>							

			<li>
				<small>&nbsp;</small>
				<ul class="inline-list">
					<li>
					<a class="item" href="javascript:getList('0')">
				    	<i class="fa fa-home fa-3x"></i>
				  	</a>
				  	</li>
				</ul>								
			</li>							

			<li>
				<small>&nbsp;</small>
				<ul class="inline-list">
					<li class="divider">
					<a href="#">
				    	<i class="fa fa-3x">|</i>
				  	</a>
				  	</li>
				</ul>								
			</li>							

			<li>
				<small><%=registry_Media.getStringOfLanguage("media.folder_tool", language_Media) %></small>
				<ul class="inline-list">
					<li>
					<a class="item" href="javascript:updateFolder('add')">
				    	<i class="fa fi-folder-add fa-3x"></i>
				  	</a>
				  	</li>
				  	
				  	<li>
					<a class="item" href="javascript:updateFolder('edit')">
				    	<i class="fa fi-pencil fa-3x"></i>
				  	</a>
				  	</li>
				  	
				  	<li>
					<a class="item" href="javascript:updateFolder('delete')">
				    	<i class="fa fi-x fa-3x"></i>
				  	</a>
				  	</li>
			  	</ul>
		  	</li>

			<li>
				<small>&nbsp;</small>
				<ul class="inline-list">
					<li class="divider">
					<a href="#">
				    	<i class="fa fa-3x">|</i>
				  	</a>
				  	</li>
				</ul>								
			</li>							
			
			<li>
				<small><%=registry_Media.getStringOfLanguage("media.file_tool", language_Media) %></small>
				<ul class="inline-list">
					<li>
					<a class="item" href="javascript:uploadFile()">
				    	<i class="fa fi-page-add fa-3x"></i>
				  	</a>
				  	</li>
				  	
				  	<li>
					<a class="item" href="javascript:updateFile('edit')">
				    	<i class="fa fi-page-edit fa-3x"></i>
				  	</a>
				  	</li>
				  	
				  	<li>
					<a class="item" href="javascript:updateFile('delete')">
				    	<i class="fa fi-page-delete fa-3x"></i>
				  	</a>
				  	</li>
				</ul>								
			</li>							
		  	
			<li>
				<small>&nbsp;</small>
				<ul class="inline-list">
					<li class="divider">
					<a href="#">
				    	<i class="fa fa-3x">|</i>
				  	</a>
				  	</li>
				</ul>								
			</li>
										
			<li>
				<small>&nbsp;</small>
				<i class="fa fa-2x"><%=registry_Media.getStringOfLanguage("media.desc", language_Media) %></i>
			</li>			
		</ul>
	</div>
					
	<div class="row left media_manager" style="margin-top: 10px;">
		<div class="large-3 columns">
						
<ul class="side-nav explorer" id="explorer">
	<li>
		<a href="javascript:getList('0')" id="parent_folder0"><i class="fa fi-folder-lock fa-2x">&nbsp;/</i></a>
		<ul id="folder0"></ul>
	</li>
</ul>	
		</div>
		
		<div class="large-9 columns" style="padding-top: 20px;" id="table_container">
			<table id="media_table" class="display" cellspacing="0" cellpadding="0" width="100%">
			<thead>
				<tr>
					<th><%=registry_Media.getStringOfLanguage("media.filename", language_Media) %></th>
					<th width="100"><%=registry_Media.getStringOfLanguage("media.size", language_Media) %></th>
					<th width="200"><%=registry_Media.getStringOfLanguage("media.create_time", language_Media) %></th>
				</tr>
			</thead>
			
			<tbody id="filelist">
			</tbody>
			
			</table>
		</div>
	</div>
	
	<div class="row left" style="margin-top: 20px;">
		<a href="javascript:select()" class="medium button right">Select</a>
	</div>
</div>
<a class="close-reveal-modal">&#215;</a>

<div id="uploadManagerModal" >
</div>
	
	<input type="hidden" id="basePath1" value="<%=basePath_Media %>" />
	<input type="hidden" id="resPath1" value="<%=resPath_Media %>" />
	<input type="hidden" id="selection_type" value="<%=request.getParameter("type") %>">
	<input type="hidden" id="selection_page" value="<%=request.getParameter("page") %>">
	<input type="hidden" id="selection_method" value="<%=request.getParameter("select") %>">
	<input type="hidden" id="selection_detail" value="<%=Utils.checkNull(request.getParameter("detail")) %>">
	
	<input type="hidden" id="alert_select_image" value="<%=registry_Media.getStringOfLanguage("media.alert.image", language_Media) %>">
	<input type="hidden" id="alert_select_video" value="<%=registry_Media.getStringOfLanguage("media.alert.video", language_Media) %>">
	<input type="hidden" id="alert_invalid_format" value="<%=registry_Media.getStringOfLanguage("media.alert.format", language_Media) %>">

	<script src="<%=resPath_Media%>js/jquery/jquery.dataTables.min.js"></script>
	<script src="<%=resPath_Media%>js/foundation/dataTables.foundation.min.js"></script>
	<script src="<%=resPath_Media%>js/foundation/dataTables.responsive.js"></script>

	<script src="<%=resPath_Media%>js/jquery/jquery.mousewheel-3.0.6.pack.js"></script>
	<script src="<%=resPath_Media%>js/jquery/jquery.fileDownload.js"></script>
	<script src="<%=resPath_Media%>js/jquery/jquery.form.js"></script>
	<script src="<%=resPath_Media%>js/jquery/pgwmodal.min.js"></script>

	<script src="<%=resPath_Media%>js/fancybox/jquery.fancybox.pack.js"></script>
	<script src="<%=resPath_Media%>js/fancybox/jquery.fancybox-buttons.js"></script>
	<script src="<%=resPath_Media%>js/fancybox/jquery.fancybox-media.js"></script>
	<script src="<%=resPath_Media%>js/fancybox/jquery.fancybox-thumbs.js"></script>

	<script src="<%=resPath_Media%>js/popup_mediamanager.js"></script>
