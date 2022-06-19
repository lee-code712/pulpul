/**
 * 
 */
 
 function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}
 function findBorrowShareThing(id){
	const uri = '/market/shareThing/borrowList?itemId=' + id;
	encodeUri(uri);
}

function updateShareThing(id){
	const uri = '/market/shareThing/update?itemId=' + id;
	encodeUri(uri);
}

function deleteShareThing(id){
	const uri = '/market/shareThing/delete?itemId=' + id;
	encodeUri(uri);
}

function moveToShareThingDetail(id){
	const uri = '/lookup/shareThingDetail?itemId=' + id;
	encodeUri(uri);
}