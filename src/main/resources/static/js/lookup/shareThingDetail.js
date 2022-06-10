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

function encodeUri(uri){
	const encoded = encodeURI(uri);
	location.href = encoded;
}

function updateShareThing(id){
	const uri = '/market/shareThing/update?itemId=' + id;
	encodeUri(uri);
}

function clickRezBtn() {
	if (borrow.shareThing.reservationNumber == 2)
			alert("공유물품 예약자 마감");

	if (borrow.shareThing.reservationNumber < 2)
			location.href='/member/myPage';
}