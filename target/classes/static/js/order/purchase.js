/**
 * 
 */
 //총 상품 금액 계산
 var totalPrice = $("#total-price").text().replace(",", "").split("원")[0]; //총 결제 금액
 var totalShipping = $("#total-shipping").text().replace(",", "").split("원")[0]; //배송비
 var itemPrice = Number(totalPrice) - Number(totalShipping);
 $("#total-item").text(itemPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
 
 //잔여 포인트
 var leftover = $("#leftover").text().replace(",", "").replace("잔여", "").split("원")[0];
 
 $("#point").keyup(function(){
	var point = Number($(this).val());
	var total;
	
	if(point > leftover){ //잔여 포인트보다 큰 값 입력시
		$(this).val("");
		total = totalPrice;
		$("#leftover").css("color", "red");
	}else{
		total = Number(totalPrice) - point;
		$("#leftover").css("color", "#c4c4c4");
	}
	
	$("#total-price").text(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
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

 $("#validDate").keyup(function(){;
    var _slash = $(this).val();
        _slash = _slash.replace(/(\d{2})(\d{2})/, '$1/$2');
    $(this).val(_slash);
  });