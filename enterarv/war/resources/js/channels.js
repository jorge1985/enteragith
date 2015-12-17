/**
 * Channel page. 
 */

function clear_ciudad(){
	$("#channel_ciudad option").removeAttr('selected');
}
function clear_direccion(){
	$("#channel_direccion option").removeAttr('selected');
}
function clear_empresa(){
	$("#channel_empresa option").removeAttr('selected');
}

function expand_channel(pos){
	$(".channels-tree li[pos="+pos+"] >ol").slideToggle("fast", function(){
		var s = $(".channels-tree li[pos="+pos+"] >div >div >a.expand_icon").attr('state');
		if (s=='1'){
			$(".channels-tree li[pos="+pos+"] >div >div >a.expand_icon").attr('state', '0');
			$(".channels-tree li[pos="+pos+"] >div >div >a.expand_icon").html('<i class="fa fa-plus fa-lg">&nbsp;&nbsp;&nbsp;');
		}else{
			$(".channels-tree li[pos="+pos+"] >div >div >a.expand_icon").attr('state', '1');
			$(".channels-tree li[pos="+pos+"] >div >div >a.expand_icon").html('<i class="fa fa-minus fa-lg">&nbsp;&nbsp;&nbsp;');
		}
	});	
}

function getSubTree(data){
	var has=false;
	var sub_tree = '';
	$.each(data, function(index, row){
		has=true;
		
		var temp = '';//'<ol>';
		temp = getSubTree(row.child) ;//+ '</ol>';
		if (temp!=''){
			sub_tree += '<li pos="'+row.parent.id+'">' + 
			'<div>' +
			'<div class="actions-wrapper">' +
			'<a class="expand_icon" state="1" href="javascript:expand_channel(\''+row.parent.id+'\')"><i class="fa fa-minus fa-lg"></i>&nbsp;&nbsp;&nbsp;</a>' + 
			'<a href="javascript:edit_channel(\'' + row.parent.id + '\')"><i class="fa fa-pencil fa-lg edit"></i></a>' +
			'<a href="javascript:update_channel(\'delete\',\'' + row.parent.id + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
			'</div>'+row.parent.name+'</div>'
			;
		}else{
			sub_tree += '<li pos="'+row.parent.id+'">' + 
			'<div>' +
			'<div class="actions-wrapper">' +
			'<a href="javascript:edit_channel(\'' + row.parent.id + '\')"><i class="fa fa-pencil fa-lg edit"></i></a>' +
			'<a href="javascript:update_channel(\'delete\',\'' + row.parent.id + '\')"><i class="fa fa-times-circle fa-lg delete"></i></a>' +
			'</div>'+row.parent.name+'</div>'
			;
		}
		
		if (temp!=''){
			sub_tree += "<ol>";
			sub_tree += temp;
			sub_tree += "</ol>";
		}
		sub_tree += '</li>';
	});
	
	if (has)
		return sub_tree;
	else
		return '';
}

function showData(data){
	$("#channel_tree").html('');
	$.each(data.list, function(index, row){
		var sub="";
		sub = '<dd class="accordion-navigation" style="margin-top:3px;"><a href="#sub_panel'+row.parent.id+'"><i class="fa fa-sitemap fa-2x"> '+row.parent.name+'</i></a>';
		
		var tree=getSubTree(row.child);
		if (tree!=''){
			sub+='<div id="sub_panel'+row.parent.id+'" class="content" style="padding-top:0px;">';
			sub+='<ol class="sortable channels-tree">';
			sub+=tree;
			sub+='</ol>';
			sub+='</div>';
		}
		
		sub+='</dd>';
		$("#channel_tree").append(sub);
	});
	
    $('.channels-tree').nestedSortable({
        forcePlaceholderSize: true,
			handle: 'div',
			helper:	'clone',
			items: 'li',
			opacity: .6,
			placeholder: 'placeholder',
			revert: 250,
			tabSize: 25,
			tolerance: 'pointer',
			toleranceElement: '> div',
			maxLevels: 0,
			isTree: true,
			expandOnHover: 700,
			startCollapsed: true,
			stop: function()	{	saveOrder();	}
    });	
    
    $(".channels-tree li").each(function(index, row){
    	expand_channel($(this).attr('pos'));
    });
}

function edit_channel(id){
	$.ajax({
		type: "POST",
        url: 'getChannel.html',
        data: {  
        	channel: id
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#type").val('edit');
        		$("#channel_id").val(id);
        		
        		$("#btnAddNew").show();
        		
        		showEditData(data);
        	}
        },
        error: function() {
        	init();
        }
    });  		
}

function init(){
	$("#type").val('add');
	$("#channel_id").val('');
	$("#btnReset").trigger('click');
}

function showEditData(data){
	$("select option").removeAttr('selected');
	$("input[type=checkbox]").prop('checked', false);
	
	$("#channel_name").val(data.channel.name);
	$("#channel_email").val(data.channel.email)
	$("#channel_password").val(data.channel.password);
	
	$("#channel_family").val(data.channel.familyID);
	if ($("#channel_family").val()!=''){
		getParentData($("#channel_family").val(), data.channel.parentID);
	}
	
	if (data.channel.access_level=="1"){
		$("#accessPublic").prop('checked', true);
	}else{
		$("#accessPrivate").prop('checked', true);
	}
	
	$("#channel_security").val(data.channel.security_level);
	
//	if (data.channel.people_manager=='001'){
//		$("#peopleOnly").prop('checked', true);
//	}else{
//		$("#peopleAll").prop('checked', true);
//	}
//
//	if (data.channel.newhire=='001'){
//		$("#newhireYes").prop('checked', true);
//	}else{
//		$("#newhireNo").prop('checked', true);
//	}
//	
//	$.each(data.division, function(index, row){
//		$("#channel_division option[value='"+row.division_id+"']").attr('selected','selected');
//	});
//	getSubDivision(data.sub_division);
//	
//	$.each(data.geographical, function(index, row){
//		$("#channel_geographical option[value='"+row.city_id+"']").attr('selected','selected');
//	});
//	getCity(data.city);
//	
//	$.each(data.promote, function(index, row){
//		$("#channel_promote"+row.promote_id+"").prop('checked',true);
//	});
//
//	$.each(data.jobgrade, function(index, row){
//		$("#channel_jobgrade"+row.jobgrade_id+"").prop('checked',true);
//	});
//	
//	$.each(data.payowner, function(index, row){
//		$("#channel_payowner"+row.payowner_id+"").prop('checked',true);
//	});
	
	$.each(data.direccion, function(index, row){
		$("#channel_direccion option[value='"+row.Maindirection_MaindirectionId+"']").attr('selected','selected');
	});
	
	$.each(data.empresa, function(index, row){
		$("#channel_empresa option[value='"+row.Company_CompanyId+"']").attr('selected','selected');
	});
	
	$.each(data.ciudad, function(index, row){
		$("#channel_ciudad option[value='"+row.City_CityId+"']").attr('selected','selected');
	});
}

function update_channel(type, id){
	if (type=='delete'){
		var t = confirm('Are you sure to delete this channel?');
		if (!t)
			return;
	}else if (type=='edit'){
		id = $("#channel_id").val();
	}else if (type=='add'){
		id = '';
	}else{
		return;
	}	
	
//	var division="";
//	$("#channel_division option:selected").each(function(index, row){
//		if (division!='')
//			division+=",";
//		division+=row.value;
//	});
//	
//	var sub_division="";
//	$("#channel_subdivision option:selected").each(function(index, row){
//		if (sub_division!='')
//			sub_division+=",";
//		sub_division+=row.value;
//	});
//
//	var geographical="";
//	$("#channel_geographical option:selected").each(function(index, row){
//		if (geographical!='')
//			geographical+=",";
//		geographical+=row.value;
//	});
//	
//	var city="";
//	$("#channel_city option:selected").each(function(index, row){
//		if (city!='')
//			city+=",";
//		city+=row.value;
//	});
//
//	var promote="";
//	$(".channel_promote").each(function(index, row){
//		if ($(row).prop('checked')){
//			if (promote!='')
//				promote+=",";
//			promote+=$(row).attr('pos');
//		}
//	});
//
//	var jobgrade="";
//	$(".channel_jobgrade").each(function(index, row){
//		if ($(row).prop('checked')){
//			if (jobgrade!='')
//				jobgrade+=",";
//			jobgrade+=$(row).attr('pos');
//		}
//	});
//	
//	var payowner="";
//	$(".channel_payowner").each(function(index, row){
//		if ($(row).prop('checked')){
//			if (payowner!='')
//				payowner+=",";
//			payowner+=$(row).attr('pos');
//		}
//	});

	var direccion="";
	$("#channel_direccion option:selected").each(function(index, row){
		if (direccion!='')
			direccion+=",";
		direccion+=row.value;
	});
	
	var empresa="";
	$("#channel_empresa option:selected").each(function(index, row){
		if (empresa!='')
			empresa+=",";
		empresa+=row.value;
	});

	var ciudad="";
	$("#channel_ciudad option:selected").each(function(index, row){
		if (ciudad!='')
			ciudad+=",";
		ciudad+=row.value;
	});
	
	$.ajax({
		type: "POST",
        url: 'updateChannel.html',
        data: {  
			type: type, channel_id: id,
			channel_name: $("#channel_name").val(),
			family_id: $("#channel_family").val(),
			email: $("#channel_email").val(),
			password: $("#channel_password").val(),
			parent: $("#channel_parent").val(),
			access: $("#accessPublic").prop('checked') ? 1 : 2,
			security_level: $("#channel_security").val(),
			direccion: direccion,
			empresa: empresa,
			ciudad: ciudad,
//			channel_division: division,
//			channel_subdivision: sub_division,
//			channel_geographical: geographical,
//			channel_city: city,
//			channel_promote: promote,
//			channel_jobgrade: jobgrade,
//			channel_payowner: payowner,
//			people_manager: $("#peopleOnly").prop('checked') ? '001' : '002',
//			newhire: $("#newhireYes").prop('checked') ? '001' : '002'
		},
        dataType: 'json', 
        success: function(data) {
    		showAlert(data.errMsg);
    		init();
        	if (data.errCode==0){
        		showData(data);
        	}
        },
        error: function() {
        	init();
        }
    });  	
}

function showSubDivision(data, somedata){
	$("#channel_subdivision").html('');
	
	$.each(data, function(index, row){
		var listbox = $('<select class="channel" cnn="'+row.parent.id+'" id="channel'+row.parent.id+'" multiple="multiple" size="5" style="height: 250px;">');
		listbox.append('<option cnn="all" value="'+row.parent.id+'">'+row.parent.name+'</option>');
		$.each(row.child, function(index, row2){
			listbox.append('<option cnn="'+row2.id+'" value="'+row2.id+'">-----&nbsp;'+row2.name+'</option>');
		});

		$('#channel_subdivision').append($('<div class="large-12 columns">').append(listbox));
	});
	
	$('select.channel').change(function(){
		var cid=$(this).attr('cnn');

		if ($("#channel"+cid+" option:selected[cnn='all']").length>0){
   			$("#channel"+cid+" option:selected").removeAttr('selected')
   			$("#channel"+cid+" option[cnn='all']").attr('selected','selected');
   		}		
	});
	
	if (somedata!=''){
		$.each(somedata, function(index, row){
			$("#channel_subdivision option[value='"+row.division_id+"']").attr('selected','selected');
		});
	}
}

function getSubDivision(somedata){
	var temp="";
	
	$("#channel_division option:selected").each(function(index, row){
		if (temp!='')
			temp+=",";
		temp+=row.value;
	});
	
	if (temp=='')
		return;
	
	$.ajax({
		type: "POST",
        url: 'loadDivision.html',
        data: {  
        	division: temp
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showSubDivision(data.list, somedata);
        	}
        },
        error: function() {
        }
    });  	
}

function showCity(data, somedata){
	$("#channel_city").html('');
	
	$.each(data, function(index, row){
		var listbox = $('<select class="city" cty="'+row.parent.id+'" id="city'+row.parent.id+'" multiple="multiple" size="5" style="height: 250px;">');
		listbox.append('<option cty="all" value="'+row.parent.id+'">'+row.parent.name+'</option>');
		$.each(row.child, function(index, row2){
			listbox.append('<option cty="'+row2.id+'" value="'+row2.id+'">-----&nbsp;'+row2.name+'</option>');
		});

		$('#channel_city').append($('<div class="large-12 columns">').append(listbox));
	});
	
	$('select.city').change(function(){
		var cid=$(this).attr('cty');

		if ($("#city"+cid+" option:selected[cty='all']").length>0){
   			$("#city"+cid+" option:selected").removeAttr('selected')
   			$("#city"+cid+" option[cty='all']").attr('selected','selected');
   		}		
	});
	
	if (somedata!=''){
		$.each(somedata, function(index, row){
			$("#channel_city option[value='"+row.city_id+"']").attr('selected','selected');
		});
	}
}

function getCity(somedata){
	var temp="";
	
	$("#channel_geographical option:selected").each(function(index, row){
		if (temp!='')
			temp+=",";
		temp+=row.value;
	});
	
	if (temp=='')
		return;
	
	$.ajax({
		type: "POST",
        url: 'loadCity.html',
        data: {  
        	geographical: temp
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showCity(data.list, somedata);
        	}
        },
        error: function() {
        }
    });  	
}

function showChildList(prefix, data){
	$.each(data, function(index, row){
		$("#channel_parent").append('<option value="'+row.parent.id+'">'+prefix+row.parent.name+'</option>');
		showChildList(prefix+"-----&nbsp;", row.child);
	});
}

function showParentData(data, parentID){
	$("#channel_parent").html('');
	$("#channel_parent").append('<option value="">&nbsp;</option>');
	
	$.each(data, function(index, row){
		$("#channel_parent").append('<option value="'+row.parent.id+'">'+row.parent.name+'</option>');
		showChildList("-----&nbsp;", row.child);
	});
	
	if (parentID!='' && parentID!=0 && parentID!='0'){
		$("#channel_parent").val(parentID);
	}
}

function getParentData(family, parentID){
	$.ajax({
		type: "POST",
        url: 'loadParent.html',
        data: {  
        	family: family, type: $('#type').val(), channel_id: $("#channel_id").val()
		},
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		showParentData(data.list, parentID);
        	}
        },
        error: function() {
        }
    });  	
}

function findSubElement(element){
	var i=0;
	var pos=[];
	
	$(element).find('>ol>li').each(function(index,row){
		pos[i]={ id:$(row).attr('pos'), child: findSubElement(row)	}
		i++;
	});
	
	return pos;
}

function saveOrder(){
	var pos = [];
	var i=0;
	$('.channels-tree').find('>li').each(function(index, row){
		pos[i]={ id:$(row).attr('pos'), child: findSubElement(row)	}
		i++;
	});
	
   	$.ajax({
        url: 'updatePosition.html',
        type: "POST",
        data: {  type: 'channel', position: JSON.stringify(pos) },
        dataType: 'json', 
        success: function(data) {
        	showAlert(data.errMsg);
        },
        error: function() {
        }
    });  	
}

$(document).ready(function() {
	$("#btnAddNew").hide();
	
	showLoading();
	
	
	
   	$.ajax({
        url: 'loadChannels.html',
        data: {  },
        dataType: 'json', 
        success: function(data) {
        	hideLoading();
        	if (data.errCode==0){
        		showData(data);
        	}
        },
        error: function() {
        	hideLoading();
        }
    });

   	$("#channel_form").on("valid invalid submit", function(event) {
   		event.stopPropagation();
   		event.preventDefault();
   		
   		if (event.type=='valid'){
//   			if ($("#channel_subdivision option:selected").length==0){
//   				showAlert($("#subdivision_empty_msg").val());
//   				return;
//   			}
//   			
//   			if ($("#channel_city option:selected").length==0){
//   				showAlert($("#city_empty_msg").val());
//   				return;
//   			}

//   			console.log(event);
   			update_channel($("#type").val(),'');
   		}
   	});
   	
   	$("#btnReset").click(function(){
   		$("select option").removeAttr('selected');
//   		$("#channel_subdivision").html('<span style="margin-left: 20px;">'+$("#placeholder_select").val()+'</span>');
//   		$("#channel_city").html('<span style="margin-left: 20px;">'+$("#placeholder_select").val()+'</span>');
   		$("#btnAddNew").hide();
   		$("#type").val('add');
   	});
   	
   	$("#btnAddNew").click(function(){
   		$("#type").val('add');
   	});
   	
   	$("#channel_family").change(function(){
   		if ($("#channel_family").val()!=''){
   			getParentData($("#channel_family").val(), '');
   		}
   	});
   	
//   	$("#channel_division").change(function(){
//   		if ($("#channel_division option:selected[value='all']").length>0){
//   			$("#channel_division option:selected").removeAttr('selected')
//   			$("#channel_division option[value='all']").attr('selected','selected');
//   		}
//   		
//   		getSubDivision('');
//   	});
//   	
//   	$("#channel_geographical").change(function(){
//   		if ($("#channel_geographical option:selected[value='all']").length>0){
//   			$("#channel_geographical option:selected").removeAttr('selected')
//   			$("#channel_geographical option[value='all']").attr('selected','selected');
//   		}
//   		
//   		getCity('');
//   	});
//
//   	$("#channel_promote").change(function(){
//   		if ($("#channel_promote option:selected[value='001']").length>0){
//   			$("#channel_promote option:selected").removeAttr('selected')
//   			$("#channel_promote option[value='001']").attr('selected','selected');
//   		}
//   	});
//
//   	$("#channel_jobshrt").change(function(){
//   		if ($("#channel_jobshrt option:selected[value='all']").length>0){
//   			$("#channel_jobshrt option:selected").removeAttr('selected')
//   			$("#channel_jobshrt option[value='all']").attr('selected','selected');
//   		}
//   	});
});

