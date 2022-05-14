 /*
* input file에서 이미지 선택 시 선택한 이미지 보여줌.
*/
 function previewFile() {
  var preview = document.querySelector('.marketImage');
  var file    = document.querySelector('input[type=file]').files[0];
  var reader  = new FileReader();

  reader.onloadend = function () {
    preview.src = reader.result;
  }
  
  if (file) {
    reader.readAsDataURL(file);
  } else {
    preview.src = "";
  }
  
   isImage();

}

//input type="file"에서 file 선택하면 이미지 보여짐.
function isImage(){
	//input type="file"
	var inputImg = document.getElementById('fileWrap').value;
	//이미지 경로 나타날 자리
	var marketImgPath = document.getElementById("isMarketImage").getElementsByTagName("td")[1];
	//var isMarketImg = document.getElementsByTagName('td')[7].childNodes[0].nodeValue;
	
	if(inputImg != null){ //사용자가 이미지 선택한 경우

		//이미지를 추가했다면 바꾼 이미지 경로 보여줌 - fakePath ex) c:\fakepath\test.html 로컬 경로에 접근 불가
		marketImgPath.innerHTML = inputImg;
		
		//fake 경로를 '\'로 분할 후 실제 이미지 이름 추출.
		var a = marketImgPath.innerHTML;
		var filePathSplit = a.split('\\'); 
		var fileName = filePathSplit[filePathSplit.length - 1];

		marketImgPath.innerHTML = fileName;
		
	}
}
