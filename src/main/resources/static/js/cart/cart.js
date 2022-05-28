/**
 * 
 */
 /*경로 */
 
 function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}
 function deleteItem(itemId){
	const uri = '/cart/deleteItem?itemId=' + itemId;
	encodeUri(uri);
}

function deleteItemByMarket(marketId){
	location.href='/cart/deleteItemByMarket?marketId=' + marketId;
}

$(".market-name").each(function(){

	var checkOfMarket = $(this).parent().find("input[type=checkbox]");
	checkOfMarket.attr('class', 'check' + i); //해당 마켓의 상품들의 checkbox에 class명 추가
	var tableName = $(this).parent().find("table");
	tableName.attr('class', 'marketTable' + i); //해당 마켓에 class명 추가
	var divName = $(this).parent();
	divName.attr('class', 'marketDiv_' + i); //마켓이 포함된 div에 class명 추가
	
	i++;
})

/*
 마켓 별로 price, shipping 담을 변수 생성
*/
let object = {};
for(var i = 0; i < document.querySelectorAll(".market-name").length; i++){
  object[`price${i}`] = 0;
  object[`shipping${i}`] = 0;
}

/*
 체크할 때마다 상품 금액과 배송비 계산
*/
$("table input[type=checkbox]").click(function(){
	var div = $(this).parents("div")[0];
	var sequence = div.className.split("_")[1];
	console.log(sequence);
	const total = $(this).parents("."+div.className).find(".total-price")[0];
	var strPrice;
	var strShipping;
	//체크 true
	if($(this).is(":checked") == true){
			strPrice = $(this).parent().siblings(".price")[0].innerHTML.split("원");
			strShipping = $(this).parent().siblings(".shipping")[0].innerHTML.split("원");
			object[`price${sequence}`] += Number(strPrice[0]);
			object[`shipping${sequence}`] += Number(strShipping[0]);
	 }else{ //체크 false
			strPrice = $(this).parent().siblings(".price")[0].innerHTML.split("원");
			strShipping = $(this).parent().siblings(".shipping")[0].innerHTML.split("원");
			object[`price${sequence}`] -= Number(strPrice[0]);
			object[`shipping${sequence}`] -= Number(strShipping[0]);
	}
	
	var totalPrice = object[`price${sequence}`] + object[`shipping${sequence}`];
	total.innerHTML = "상품 금액 <b>" + object[`price${sequence}`].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
	 + "</b>원<br>배송비 <b>" + object[`shipping${sequence}`].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "</b>원<br>총 금액 <b>"
	+  totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "</b>원";
})