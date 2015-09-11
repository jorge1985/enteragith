<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<%
	Registry registry_Upload = Registry.getInstance();
	SessionHandler handler_Upload = SessionHandler.getInstance();
	String language_Upload = handler_Upload.getLanguage(session);
	String basePath_Upload = request.getContextPath()+"/";
	String resPath_Upload = basePath_Upload + "resources/";
%>

	<h2><%=registry_Upload.getStringOfLanguage("opp.upload", language_Upload) %></h2>
	<button type="button" class="medium" id="btnUpload"><%=registry_Upload.getStringOfLanguage("opp.select_file", language_Upload) %></button>
	<div class="progress" id="upload_progressbar">
	  <span class="meter" style="width: 0;"></span>
	</div>  
	<input type="file" name="myFile" id="takeFileUpload" style="visibility: hidden;" accept=".jpg,.JPG,.jpeg,.JPEG,.png,.PNG,.gif,.GIF" data-url="<%= blobstoreService.createUploadUrl("/upload") %>">
	<input type="hidden" id="alert_invalid_format" value="<%=registry_Upload.getStringOfLanguage("media.alert.format", language_Upload) %>">
	<a class="close-reveal-modal">&#215;</a>
	
	<script src="<%=resPath_Upload%>js/jquery/jquery.fileupload.js"></script>
	<script src="<%=resPath_Upload%>js/opp_upload.js"></script>
	