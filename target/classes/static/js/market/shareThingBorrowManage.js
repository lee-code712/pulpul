function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}

function returnShareThing(id){
	const uri = '/borrow/return?borrowId=' + id;
	encodeUri(uri);
}