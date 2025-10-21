document.getElementById('reviewForm').addEventListener('submit', function(e) {
	const rating = document.querySelector('input[name="rating"]:checked');
	const comment = document.getElementById('comment').value.trim();

	if (!rating) {
		alert('⭐ Vui lòng chọn số sao đánh giá!');
		e.preventDefault();
		return;
	}

	if (comment.length < 5) {
		if (!confirm('💬 Bình luận của bạn khá ngắn. Bạn có chắc muốn gửi không?')) {
			e.preventDefault();
		}
	}
});

function deleteReview() {
	if (confirm('Bạn chắc chắn muốn xóa đánh giá này?')) {
		document.getElementById('deleteForm').submit();
	}
}

function previewMedia(event) {
	const file = event.target.files[0];
	const preview = document.getElementById("previewContainer");
	preview.innerHTML = "";

	if (!file) return;

	if (file.size > 10 * 1024 * 1024) {
		alert("📦 File quá lớn! Vui lòng chọn file dưới 10MB.");
		event.target.value = "";
		return;
	}

	const fileURL = URL.createObjectURL(file);

	if (file.type.startsWith("image/")) {
		const img = document.createElement("img");
		img.src = fileURL;
		img.alt = "Preview";
		preview.appendChild(img);
	} else if (file.type.startsWith("video/")) {
		const video = document.createElement("video");
		video.src = fileURL;
		video.controls = true;
		preview.appendChild(video);
	}
}