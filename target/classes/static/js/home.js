/* 임의로 js 추가 */
document.addEventListener('DOMContentLoaded', function () {
	for (let i = 0; i < 3; i++) {
	  const newItemWrap = document.querySelector("#newItemWrap");
	
	  const newItem = document.createElement("div");
	  newItem.setAttribute("class", "new-item");
	
	  const itemImg = document.createElement("img");
	  itemImg.setAttribute("class", "item-img");
	  itemImg.setAttribute("src", "/images/newItemImg.svg");
	
	  const itemName = document.createElement("div");
	  itemName.setAttribute("class", "item-name");
	  itemName.innerHTML = "Sample";
	
	  const itemPrice = document.createElement("div");
	  itemPrice.setAttribute("class", "item-price");
	  itemPrice.innerHTML = "8000원";
	
	  newItem.appendChild(itemImg);
	  newItem.appendChild(itemName);
	  newItem.appendChild(itemPrice);
	
	  newItemWrap.appendChild(newItem);
	}
});