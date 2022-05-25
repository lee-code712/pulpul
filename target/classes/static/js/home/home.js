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
