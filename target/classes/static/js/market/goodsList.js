/**
 * 
 */
 function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}
 function updateGoods(id){
	const uri = '/market/goods/update?itemId=' + id;
	encodeUri(uri);
}

function deleteGoods(id){
	const uri = '/market/goods/delete?itemId=' + id;
	encodeUri(uri);
}