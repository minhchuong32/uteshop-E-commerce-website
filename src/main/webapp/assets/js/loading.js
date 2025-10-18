const loader = document.getElementById("preloader");

// Ẩn preloader sau khi trang load xong
window.addEventListener("load", () => {
	setTimeout(() => loader.classList.add("hidden"), 300);
});

// Hiện preloader khi rời trang
window.addEventListener("beforeunload", () => {
	loader.classList.remove("hidden");
});

// Ẩn preloader nếu người dùng quay lại trang (Back/Forward cache)
window.addEventListener("pageshow", (event) => {
	if (event.persisted) {
		setTimeout(() => loader.classList.add("hidden"), 100);
	}
});