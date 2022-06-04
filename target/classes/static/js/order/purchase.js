/**
 * 
 */
 //배송비 상품들 중 배송비 가장 큰 값
 var shippings = document.querySelectorAll(".shippingFee");
 var shippingArr = [];
 for(var i = 0; i < shippings.length; i++){
	shippingArr.push(shippings[i].innerHTML);
}
var maxShipping = Math.max.apply(null, shippingArr);

$("#total-shipping").text(maxShipping.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
 
 //총 결제 금액 계산
 var totalItem = Number($("#total-item").text().replace(",", "").split("원")[0]); //결제 상품 금액
 var point = Number($("#point").text());
 
if(point > 0) {
	$("#total-price").text((totalItem + maxShipping - point).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
}else{
	$("#total-price").text((totalItem + maxShipping).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
}
$("#total-input").attr("value", $("#total-price").text()); //total-price input hidden
 
 //잔여 포인트
 var leftover = $("#leftover").text().replace(",", "").replace("잔여 포인트", "").split("원")[0];
 var totalPrice = $("#total-price").text().replace(",", "").split("원")[0];
 $("#point").keyup(function(){
	
	var point = Number($(this).val());
	var total;
	if(point > Number(leftover)){ //잔여 포인트보다 큰 값 입력시
		$(this).val("");
		total = totalPrice;
		$("#leftover").css("color", "red");
	}else{
		total = Number(totalPrice) - point;
		$("#leftover").css("color", "#c4c4c4");
	}
	
	$("#total-price").text(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
	$("#total-input").attr("value", total.toString());
})

 
 /* 카드 번호 형식 */
 var cardNumber = document.getElementById('cardNum');
cardNumber.onkeyup = function(event){
    event = event || window.event;
    var _val = this.value.trim();
    this.value = autoHypenCard(_val) ;
};

function autoHypenCard(str){
    str = str.replace(/[^0-9]/g, '');
    var tmp = '';
    if( str.length < 4){
        return str;
    }
    else if(str.length < 8){
        tmp += str.substr(0, 4);
        tmp += '-';
        tmp += str.substr(4,4);
        return tmp;
    }else if(str.length < 12){
        tmp += str.substr(0, 4);
        tmp += '-';
        tmp += str.substr(4, 4);
        tmp += '-';
        tmp += str.substr(8,4);
        return tmp;
    }else if(str.length < 17) {

        tmp += str.substr(0, 4);
        tmp += '-';
        tmp += str.substr(4, 4);
        tmp += '-';
        tmp += str.substr(8,4);
        tmp += '-';
        tmp += str.substr(12,4);
        return tmp;
    }
    return str;
}

/* 카드 유효기간 */
 $("#validDate").keyup(function(){
	
	if($(this).val().length == 2){
		if(Number($(this).val()) > 12 || Number($(this).val()) < 1){
			$(this).val("");
		}
	}
	
    var _slash = $(this).val();
        _slash = _slash.replace(/(\d{2})(\d{2})/, '$1/$2');
    $(this).val(_slash);
  });