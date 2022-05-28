document.addEventListener("DOMContentLoaded", function() {    
	goodsJson(marketId); // 마켓 페이지로 처음 이동했을 때 goodsList json 정보를 가져옴
});

// goodsList의 json 정보를 비동기적으로 가져오는 함수
function goodsJson(marketId) {
	$.ajax({
			type: "get",
			url: "/lookup/market/goodsList/" + marketId,
			contentType: "application/json",
			processData: false,
			success: function(responseJson){	// responseJson: JS object parsed from JSON text			
			   	alert(JSON.stringify(responseJson));
			   	// 이 부분에서 상품 리스트를 출력하는 코드를 짜야 함
		    },
			error: function(){
				alert("ERROR", arguments);
			}
		});
}

// shareThingList의 json 정보를 비동기적으로 가져오는 함수
function shareThingJson(marketId) {
	$.ajax({
			type: "get",
			url: "/lookup/market/shareThingList/" + marketId,
			contentType: "application/json",
			processData: false,
			success: function(responseJson){	// responseJson: JS object parsed from JSON text			
			   	alert(JSON.stringify(responseJson));
			   	// 이 부분에서 공유물품 리스트를 출력하는 코드를 짜야 함
		    },
			error: function(){
				alert("ERROR", arguments);
			}
		});
}