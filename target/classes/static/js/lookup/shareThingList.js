// uri
function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}

// 공유 상세정보로 이동
function moveToShareThingDetail(id){
	const uri = '/lookup/shareThingDetail?itemId=' + id;
	encodeUri(uri);
}

// 검색필터
$(document).ready(function(){
  $("#itemSearch").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    
    $(".item").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});