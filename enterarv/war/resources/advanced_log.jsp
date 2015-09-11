<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

<%@include file="jsp/topbar.jsp"%>

<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>

	<div class="small-10 medium-8 large-9 columns">
		<div id="content">
			<h1 class="main-title"><%=registry.getStringOfLanguage("log.title.advanced",
					language)%></h1>

			<div id="advanced_log">

				<div id="msg_alert">
					<img id="msg_alert_progress" src="<%=resPath%>images/progress.gif">
					<span>Message</span>
				</div>
				<div class="row left">
					<div class="large-12 columns">
						<div class="row">
							<div class="large-2 columns">&nbsp;</div>
							<div class="large-3 columns">
								<input id="log_search_text" type="text"
									placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
									value="" />
							</div>
							<div class="large-2 columns">
								<input type="checkbox" id="log_option_exact" /> <label
									for="log_option_exact"><%=registry.getStringOfLanguage("log.input.exact", language)%></label>
							</div>
							<div class="large-1 columns">
								<a href="javascript:search()" class="button tiny"><%=registry.getStringOfLanguage("btn.search", language)%></a>
							</div>
							<div class="large-1 columns">
								<a href="javascript:export_csv()" class="button tiny"><%=registry.getStringOfLanguage("btn.export", language)%></a>
							</div>
							<div class="large-2 columns">&nbsp;</div>
						</div>
						<div class="row">
							<div class="large-1 columns">&nbsp;</div>
							<div class="large-1 columns">
								<label for="log_date_start" class="right inline"><%=registry.getStringOfLanguage("log.date.from", language)%></label>
							</div>
							<div class="large-2 columns">
								<input id="log_date_start" class="date fdatepicker"
									data-date-format="yyyy-mm-dd" type="text"
									placeholder="<%=registry
					.getStringOfLanguage("placeholder.date", language)%>"
									value="" maxlength="10" />
							</div>
							<div class="large-1 columns">
								<label for="log_date_end" class="right inline"><%=registry.getStringOfLanguage("log.date.to", language)%></label>
							</div>
							<div class="large-2 columns">
								<input id="log_date_end" class="date fdatepicker"
									data-date-format="yyyy-mm-dd" type="text"
									placeholder="<%=registry
					.getStringOfLanguage("placeholder.date", language)%>"
									value="" maxlength="10" />
							</div>
							<div class="large-3 columns">&nbsp;</div>
						</div>
						<div class="row" style="margin-left: 20px;">
							<div class="large-3 medium-3 columns">
								<input type="radio" id="log_option_login" name="search_option"
									value="0" checked="checked" /> <label for="log_option_login"><%=registry.getStringOfLanguage("log.input.login", language)%></label>
							</div>
							<div class="large-3 medium-3 columns">
								<input type="radio" id="log_option_channel" name="search_option"
									value="2" /> <label for="log_option_channel"><%=registry.getStringOfLanguage("log.input.channel",
					language)%></label>
							</div>
							<div class="large-3 medium-3 columns">
								<input type="radio" id="log_option_content" name="search_option"
									value="3" /> <label for="log_option_content"><%=registry.getStringOfLanguage("log.input.content",
					language)%></label>
							</div>
							<!--<div class="large-3 medium-3 columns">
								<input type="radio" id="log_option_user" name="search_option"
									value="1" /> <label for="log_option_user"><%=registry.getStringOfLanguage("log.input.user", language)%></label>
							</div>-->

						</div>
					</div>
				</div>

				<div class="row left describe">
					<div class="large-12 columns">

						<table id="log_table" class="display" cellspacing="0"
							cellpadding="0" width="100%">
							<thead>
								<tr>
									<th><%=registry.getStringOfLanguage("log.table.username",
					language)%></th>
									<th><%=registry.getStringOfLanguage("log.table.date", language)%></th>
									<th width="15%"><%=registry.getStringOfLanguage("log.table.ip", language)%></th>
									<th width="25%" id="log_table_detail_column"></th>
								</tr>
							</thead>
						</table>

					</div>
				</div>

			</div>
		</div>
	</div>
</div>

<input type="hidden" id="validate_date_msg"
	value="<%=registry.getStringOfLanguage("validate.date", language)%>" />
<input type="hidden" id="basePath" value="<%=basePath%>" />

<input type="hidden" id="table_column_channel"
	value="<%=registry.getStringOfLanguage("log.table.channel",
					language)%>" />
<input type="hidden" id="table_column_content"
	value="<%=registry.getStringOfLanguage("log.table.content",
					language)%>" />

<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
<script
	src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
<script src="<%=resPath%>js/foundation/foundation-datepicker.js"></script>
<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>
<script src="<%=resPath%>js/jquery/jquery.fileDownload.js"></script>
<script src="<%=resPath%>js/app.js"></script>

<script src="<%=resPath%>js/advanced_log.js"></script>

<%@include file="jsp/footer.jsp"%>