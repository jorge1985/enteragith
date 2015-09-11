/**
 * Dashboard page. 
 */

$(document).ready(function() {
   	$.ajax({
        url: 'count.html',
        data: {  },
        dataType: 'json', 
        success: function(data) {
        	if (data.errCode==0){
        		$("#total_visitor_today").html(data.total_visitor_today);
        		$("#unique_visitor_today").html(data.unique_visitor_today);

        		$("#total_visitor_month").html(data.total_visitor_month);
        		$("#unique_visitor_month").html(data.unique_visitor_month);

        		$("#total_visitor_historic").html(data.total_visitor_historic);
        		$("#outside_visitor_historic").html(data.outside_visitor_historic);
        		
        		$("#total_system_user").html(data.total_system_user);
        		$("#active_system_user").html(data.active_system_user);
        		
        		$("#total_channels").html(data.channel);
        		$("#active_contents").html(data.content);
        	}
        },
        error: function() {
        }
    });  	
});

