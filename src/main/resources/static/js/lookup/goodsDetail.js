 var swiper = new Swiper(".mySwiper", {
  spaceBetween: 30,
  centeredSlides: true,
  autoplay: {
    delay: 2500,
    disableOnInteraction: false,
  },
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});

const upload = document.querySelector('.upload');

const uploadBox = document.querySelector('.upload-img-icon-box');

uploadBox.addEventListener('click', () => upload.click());
upload.addEventListener('change', getImageFiles);

function getImageFiles() {
  var imgs = document.querySelectorAll(".reviewImage");
  
  if(imgs.length > 0){
	 Swal.fire({
				   text: '이미지는 1개까지 업로드가 가능합니다.',
				   confirmButtonColor: '#93c0b5',
				   confirmButtonText: '확인',
				});
      return;
 }else{
	previewFile();
 }
}

 /*
* input file에서 이미지 선택 시 선택한 이미지 보여줌.
*/
 function previewFile() {
  var preview = document.querySelector('.upload-img');
  var file    = document.querySelector('input[type=file]').files[0];
  var reader  = new FileReader();

  reader.onloadend = function () {
	var img = document.createElement("img");
	img.classList.add("reviewImage");
    img.src = reader.result;
    var div = document.createElement("div");
    div.classList.add("upload-img-box");
    div.appendChild(img);

         //삭제 버튼 <button></button>
        const button = document.createElement("button");
        
        button.innerText = "X";
        button.classList.add("deleteBtn");
        button.type = "button";
       
        div.appendChild(button);
         preview.appendChild(div);
        // 이미지 삭제 버튼 눌렀을 때
        button.addEventListener("click", deleteImg);
    
  }
  
  if (file) {
    reader.readAsDataURL(file);
  } else {
    preview.src = "";
  }

}

function deleteImg(event){
	     event.preventDefault();
	
		  /*<span> 버튼의 부모 element span 삭제
		       <button ..></button>
		    </span>
		  */
		  event.target.parentElement.remove(); 
		 
  }

/* ratingTotal */
var ratings = document.querySelectorAll("#rating");
var ratingTotal = document.querySelector("#ratingTotal");

if(ratings.length == 0){
	ratingTotal.innerHTML = "0.0";
}else{
	
	var sum = 0;
	var cnt = 0;
	for(var i = 0; i < ratings.length; i++){
		sum += Number(ratings[i].innerHTML);
		cnt++;	
	}
	ratingTotal.innerHTML = sum / cnt;
}
