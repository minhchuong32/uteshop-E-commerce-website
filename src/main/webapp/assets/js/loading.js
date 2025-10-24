//const loader = document.getElementById("preloader");
//
//// Ẩn preloader sau khi trang load xong
//window.addEventListener("load", () => {
//	setTimeout(() => loader.classList.add("hidden"), 300);
//});
//
//// Hiện preloader khi rời trang
//window.addEventListener("beforeunload", () => {
//	loader.classList.remove("hidden");
//});
//
//// Ẩn preloader nếu người dùng quay lại trang (Back/Forward cache)
//window.addEventListener("pageshow", (event) => {
//	if (event.persisted) {
//		setTimeout(() => loader.classList.add("hidden"), 100);
//	}
//});


document.addEventListener('DOMContentLoaded', function() {
    // === KHAI BÁO BIẾN ===
    const loader = document.getElementById("preloader");
    const exportPdfBtn = document.getElementById('exportPdfBtn');
    let isDownloading = false; // "Cờ" để kiểm soát việc tải file

    // Nếu không tìm thấy preloader, thoát để tránh lỗi
    if (!loader) {
        console.error("Phần tử preloader không được tìm thấy!");
        return;
    }

    // === LOGIC PRELOADER CHUNG CỦA TRANG ===

    // 1. Ẩn preloader sau khi trang load xong lần đầu
    // Sử dụng 'load' để đảm bảo mọi tài nguyên đã sẵn sàng
    window.addEventListener("load", () => {
	    setTimeout(() => loader.classList.add("hidden"), 300);
    });

    // 2. Hiện preloader khi rời trang (trừ khi đang tải file)
    window.addEventListener("beforeunload", () => {
        // Chỉ hiện loader nếu không phải đang trong quá trình tải file
        if (!isDownloading) {
            loader.classList.remove("hidden");
        }
    });

    // 3. Xử lý khi người dùng quay lại trang bằng nút back/forward
    window.addEventListener("pageshow", (event) => {
	    if (event.persisted) { // Trang được load từ bfcache
		    setTimeout(() => loader.classList.add("hidden"), 100);
	    }
    });


    // === LOGIC XỬ LÝ RIÊNG CHO VIỆC TẢI FILE PDF ===

    if (exportPdfBtn) {
        exportPdfBtn.addEventListener('click', function(e) {
            // Bật "cờ" báo hiệu đang tải file
            isDownloading = true;

            // Hiển thị preloader ngay lập tức
            loader.classList.remove("hidden");

            // Bắt đầu kiểm tra cookie từ server
            let checkCookieInterval = setInterval(function() {
                const getCookie = (name) => {
                    const value = `; ${document.cookie}`;
                    const parts = value.split(`; ${name}=`);
                    if (parts.length === 2) return parts.pop().split(';').shift();
                };

                // Nếu server đã xử lý xong và gửi cookie về
                if (getCookie('download_token') === 'completed') {
                    // Dừng việc kiểm tra
                    clearInterval(checkCookieInterval);
                    // Ẩn preloader
                    loader.classList.add("hidden");
                    // Xóa cookie để chuẩn bị cho lần tải tiếp theo
                    document.cookie = "download_token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
                    
                    // Tắt "cờ" sau khi hoàn tất
                    isDownloading = false;
                }
            }, 500); // Kiểm tra mỗi nửa giây
            
            // Đặt lại "cờ" sau một khoảng thời gian ngắn phòng trường hợp có lỗi xảy ra
            // và sự kiện beforeunload không được kích hoạt đúng
            setTimeout(() => { isDownloading = false; }, 2000); 
        });
    }
});