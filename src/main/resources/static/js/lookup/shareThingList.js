/**
 * 
 */
 function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}
 function moveToShareThingDetail(id){
	const uri = '/lookup/shareThingDetail?itemId=' + id;
	encodeUri(uri);
}