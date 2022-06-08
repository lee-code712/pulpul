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

//배송비 계산
 var totalItem = $("#total-item").text().replace(",", "").split("원")[0];
 var totalPrice = Number($("#totalPrice").text().replace(",", "").split("원")[0]);
 var shipping = $("#total-shippingFee");

var itemNum = $(".item-name");
	if(!$("#point")){
	shipping.text("+" + (totalPrice - totalItem).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
	}else{
		var point = -Number($("#point").text().replace(",", "").split("원")[0]);
		shipping.text("+" + (totalPrice - totalItem + point).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");	
}