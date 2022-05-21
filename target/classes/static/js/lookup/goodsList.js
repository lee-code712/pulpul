/**
 * 
 */
 function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}
 function moveToGoodsDetail(id){
	const uri = '/lookup/goodsDetail?itemId=' + id;
	encodeUri(uri);
}

jQuery(function($){ $('.filterText').keyup(function(event) { 
	var val = $(this).val(); 
 	if (val == "") {
	     $('.item').show();
	 }else {
		 $('.item').hide(); 
 		 $(".item-name:contains('"+val+"')").parent('.item').show();
 	  }
   });
 });
