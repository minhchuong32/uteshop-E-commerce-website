// =====================
//  Hiệu ứng Loading (Bootstrap)
// =====================

// Chờ khi toàn bộ trang load xong
window.addEventListener("load", () => {
  const preloader = document.getElementById("preloader");

  if (preloader) {
    // Thêm hiệu ứng mờ dần
    preloader.classList.add("fade-out");

    // Ẩn hoàn toàn sau 0.5 giây
    setTimeout(() => {
      preloader.style.display = "none";
    }, 500);
  }
});
