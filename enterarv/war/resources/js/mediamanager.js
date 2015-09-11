/**
 * Media Manager page.
 */

var folder_id = '0';
var file_id = '';
var panel = '';

function getList(id) {
	$.ajax({
		url : 'getMedia.html',
		data : {
			folder_id : id
		},
		dataType : 'json',
		success : function(data) {
			$("#folder" + id).html('');
			$("#filelist").html('');
			if (data.errCode == 0) {
				$("#explorer a").removeClass('active');
				$("#parent_folder" + id).addClass('active');

				folder_id = id;
				showFolder('folder' + id, data.folder);
				showFile(data.file);
			}
		},
		error : function() {
			$("#folder" + id).html('');
			$("#filelist").html('');
		}
	});
}

function showFolder(subid, data) {
	file_id = '';
	$.each(data, function(index, row) {
		$("#" + subid).append(
				'<li>' + '<a href="javascript:getList(\'' + row.id
						+ '\')" id="parent_folder' + row.id + '">' +
						// '<img id="folder'+row.id+'_progress"
						// class="folder_progress"
						// src="'+$("#resPath").val()+'images/progress.gif">' +
						'<i class="fa fa-folder fa-2x">&nbsp;' + row.name
						+ '</i></a>' + '<ul id="folder' + row.id + '"></ul>'
						+ '</li>');
	});
}

function showFile(data) {
	file_id = "";
	$("#table_container").html(panel);

	$.each(data, function(index, row) {
		$("#filelist").append(
				'<tr class="file_row" file_id="' + row.id + '">' + '<td>'
						+ row.name + '</td>' + '<td>' + row.size + '</td>'
						+ '<td>' + row.create_time + '</td>' + '</tr>');
	});

	var table = $('#media_table').DataTable();

	$('#media_table tbody').on('click', 'tr', function() {
		file_id = $(this).attr('file_id');
		$('#media_table tbody tr').removeClass('active');
		$(this).addClass('active');
	});

	$('#media_table tbody').on('dblclick', 'tr', function() {
		downloadFile($(this).attr('file_id'));
	});
}

function updateFolder(type) {
	if (folder_id == '0' && (type == 'edit' || type == 'delete')) {
		return;
	}

	if (folder_id != '') {
		var v = '';
		if (type == 'add' || type == 'edit') {
			v = prompt("Input Folder Name..", "New Folder");
			if (!v)
				return;
		} else {
			var t = confirm('Are you sure to delete this?');
			if (!t)
				return;
		}

		$.ajax({
			url : 'updateFolder.html',
			data : {
				type : type,
				folder_id : folder_id,
				name : v
			},
			dataType : 'json',
			success : function(data) {
				if (data.errCode == 0) {
					var fid = data.FolderId;
					folder_id = fid;
					$("#folder" + fid).html('');
					$("#filelist").html('');
					showFolder('folder' + fid, data.folder);
					showFile(data.file);
				}
			},
			error : function() {

				$("#folder" + id).html('');
				$("#filelist").html('');
			}
		});
	}
}

function updateFile(type) {
	if ((type == 'edit' || type == 'delete') && file_id == '')
		return;

	if (folder_id != '') {
		var v = '';
		if (type == 'edit') {
			v = prompt("Input File Name..", "New File");
			if (!v)
				return;
		}
		if (type == 'delete') {
			var t = confirm('Are you sure to delete this?');
			if (!t)
				return;
		}

		$.ajax({
			url : 'updateFile.html',
			data : {
				type : type,
				folder_id : folder_id,
				file_id : file_id,
				name : v
			},
			dataType : 'json',
			success : function(data) {
				if (data.errCode == 0) {
					$("#folder" + folder_id).html('');
					$("#filelist").html('');
					showFolder('folder' + folder_id, data.folder);
					showFile(data.file);
				}
			},
			error : function() {
				$("#folder" + id).html('');
				$("#filelist").html('');
			}
		});
	}
}

function uploadFile() {
	$('#uploadFileModal').foundation('reveal', 'open', {
		close_on_background_click : false,
		url : $('#resPath1').val() + 'upload.jsp',
		data : {
			param1 : folder_id
		},
		success : function(data) {
		},
		error : function() {
		}
	});
}

function downloadFile(id) {
	if (id != '') {
		$.ajax({
			url : 'getFile.html',
			data : {
				file_id : id
			},
			dataType : 'json',
			success : function(data) {
				if (data.errCode == 0) {
					var content = data.file.type;
					console.log(data.file);

					// Refer to http://fancyapps.com/fancybox/#license.
					if (content == "1") {
						$.fancybox.open({
							type : 'image',
							href : $("#basePath").val() + "serve?blob-key="
									+ data.file.cloud_key
						}, {
							autoSize : true,
							openEffect : 'elastic',
							closeEffect : 'fade'
						});

					} else if (content == "2" || content == "3"
							|| content == "4" || content == "5") {
						$.fancybox.open({
							type : 'iframe',
							href : $("#basePath").val() + "serve?blob-key="
									+ data.file.cloud_key
						}, {
							autoSize : true,
							openEffect : 'elastic',
							closeEffect : 'fade'
						});

					} else {
						$.fileDownload($("#basePath").val() + "serve?blob-key="
								+ data.file.cloud_key + "&name="
								+ data.file.name);
					}

				}
			},
			error : function() {
			}
		});
	}
}

$(document).ready(function() {
	panel = $("#table_container").html();
	getList('0');
});
