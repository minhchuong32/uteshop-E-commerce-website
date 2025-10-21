document.addEventListener("DOMContentLoaded", function() {
	const slider = document.getElementById("categorySlider");
	const prevBtn = document.getElementById("prevCategory");
	const nextBtn = document.getElementById("nextCategory");

	// lấy chiều rộng 1 item
	const item = slider.querySelector(".category-card");
	const itemWidth = item ? item.offsetWidth : 200;

	// click next
	nextBtn.addEventListener("click", () => {
		console.log(1)
		slider.scrollBy({ left: itemWidth, behavior: "smooth" });
	});

	// click prev
	prevBtn.addEventListener("click", () => {
		slider.scrollBy({ left: -itemWidth, behavior: "smooth" });
	});
});

//logic chuyển đổi layout 
document.addEventListener("DOMContentLoaded", function() {
	const btnGrid = document.getElementById("btnGrid");
	const btnList = document.getElementById("btnList");
	const gridView = document.getElementById("gridView");
	const listView = document.getElementById("listView");

	btnGrid.addEventListener("click", function() {
		btnGrid.classList.add("btn-primary-custom", "active");
		btnGrid.classList.remove("btn-outline-secondary");
		btnList.classList.add("btn-outline-secondary");
		btnList.classList.remove("btn-primary-custom", "active");
		gridView.classList.remove("d-none");
		listView.classList.add("d-none");
	});

	btnList.addEventListener("click", function() {
		btnList.classList.add("btn-primary-custom", "active");
		btnList.classList.remove("btn-outline-secondary");
		btnGrid.classList.add("btn-outline-secondary");
		btnGrid.classList.remove("btn-primary-custom", "active");
		listView.classList.remove("d-none");
		gridView.classList.add("d-none");
	});
});

