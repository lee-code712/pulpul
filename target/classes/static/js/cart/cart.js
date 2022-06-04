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

/* 
페이지 첫 로드할 때 div와 table, checkbox에 class명 추가
체크할 때마다 가격 보여주기 위해 필요한 함수
 */
cartCheck();

function cartCheck(){
	
	var i = 0;
	
	$(".market-name").each(function(){

	var tableName = $(this).parent().find("table");
	tableName.attr('class', 'marketTable' + i); //cartItem들을 보여주는 Table에 class명 추가
	var divName = $(this).parent();
	divName.attr('class', 'marketDiv_' + i); //cartItemList들이 포함된 div에 class명 추가
	
	i++;
})
	
}


/*
 같은 마켓의 상품만 주문 가능
 */
$(".check-item-order").each(function(){
	$(this).click(function(event){
		
		event.preventDefault();
		var sequence = $(this).parents("div")[1].classList[0].split("_")[1] //table class sequence
		console.log(sequence);
		var table = "marketTable" + sequence;
	
		var checkLength = $("." + table  + " input[type=checkbox]:checked").length; //해당 마켓에서 선택한 상품 개수
		var otherMarket = $("table input[type=checkbox]:checked"); //다른 마켓의 checkbox
		var otherMarketCheck = otherMarket.not("." + table  + " input[type=checkbox]:checked").length;
		
		if(otherMarketCheck > 0){ //다른 마켓의 상품도 선택했을 경우
			 Swal.fire({
			      text: '같은 마켓의 상품만 주문 가능합니다.',
			      confirmButtonColor: '#93c0b5',
			      confirmButtonText: '확인',
			    })
		}
	    else if(checkLength == 0){ //선택한 상품이 없는 경우
			 Swal.fire({
			      text: '해당 마켓에 선택하신 상품이 없습니다.',
			      confirmButtonColor: '#93c0b5',
			      confirmButtonText: '확인',
			    })
		}else{
			$("form").submit();
		}
	})
})

/*
마켓별로 담은 상품 보여주기 위해
마켓 이름이 같으면 해당 item을 마켓 이름이 같은 table에 append 후,
item을 담고 있던 div 삭제 -> div class명 다시 정의 (class명 형태-div_0, div_1)
*/
var marketName = document.querySelectorAll(".market-name");
for(let k = 0; k < marketName.length; k++){
	for(let j = k + 1; j < marketName.length; j++){
		if(marketName[k].innerHTML == marketName[j].innerHTML){
			var cartItem = $(marketName[j]).next().children().find(".cartItem-wrap"); 
			$(cartItem).closest("div").remove();
			cartCheck();
			$(marketName[k]).next().children().append(cartItem);
		}	
	}
}

/*
 카트에 담긴 마켓 별로 총 상품 금액(price * quantity), 배송비(shipping) 담을 변수 생성
*/
let object = {};
for(var i = 0; i < document.querySelectorAll(".market-name").length; i++){
  object[`price${i}`] = 0; //마켓 수만큼 동적으로 변수 생성 price0, price1, price2..
  object[`shipping${i}`] = 0;
}

/*
 체크할 때마다 상품 금액과 배송비 계산 - 총 결제 금액 보여주기 위해
*/
$("table input[type=checkbox]").click(function(){
	var div = $(this).parents("div")[0];
	var sequence = div.className.split("_")[1]; //div 번호로 해당 객체(object) 받아옴
	const total = $(this).parents("."+div.className).find(".total-price");
	
	var price = $(this).parent().siblings(".price").text();
	var shipping = $(this).parent().siblings(".shipping").text();
	var quantity = Number($(this).parent().siblings(".item-num").text());
	
	//체크 true
	if($(this).is(":checked") == true){
			object[`price${sequence}`] += Number(price) * quantity; //총 상품 금액 저장
			console.log(object[`shipping${sequence}`]);
			if(object[`shipping${sequence}`] < Number(shipping)){
				object[`shipping${sequence}`] = Number(shipping); //배송비 저장
			}
	 }else{ //체크 false
			object[`price${sequence}`] -= Number(price) * quantity;
			var table = "marketTable" + sequence;
			var checkLength = $("." + table  + " input[type=checkbox]:checked").length; //해당 마켓에서 선택한 상품 개수
			
			if(checkLength == 0){ //선택한 상품 없을 때
				object[`shipping${sequence}`] = 0;
			}else{
				var arr = [];
				$("." + table  + " input[type=checkbox]:checked").each(function(){
					var checkShipping = $(this).parent().siblings(".shipping").text();
					arr.push(checkShipping);
				})
				var maxShipping = Math.max.apply(null, arr); //가장 큰 배송비 선택
				object[`shipping${sequence}`] = maxShipping;
			}
	}
	
	var totalPrice = object[`price${sequence}`] + object[`shipping${sequence}`]; //총 결제 금액
	$(this).parents("."+div.className).find(".total-price-item").text(object[`price${sequence}`].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$(this).parents("."+div.className).find(".total-shipping").text(object[`shipping${sequence}`].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	total.text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
})

