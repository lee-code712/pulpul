/**
 * 
 */
 
 //검색 필터
jQuery(function($){ $('.filterText').keyup(function(event) { 
	var val = $(this).val(); 
 	if (val == "") {
	     $('.order').show();
	 }else {
		 $('.order').hide(); 
		if($(".trackingNumber:contains('"+val+"')")){
			$(".trackingNumber:contains('"+val+"')").parents('.order').show();
		}
		
 	  }
   });
 });

