function previewAvatar(event) {
	const file = event.target.files[0];
	if (!file) return;

	// Validate file size (max 5MB)
	if (file.size > 5 * 1024 * 1024) {
		alert('📦 File quá lớn! Vui lòng chọn ảnh dưới 5MB.');
		event.target.value = '';
		return;
	}

	// Validate file type
	if (!file.type.startsWith('image/')) {
		alert('⚠️ Vui lòng chọn file ảnh!');
		event.target.value = '';
		return;
	}

	// Preview image
	const reader = new FileReader();
	reader.onload = function(e) {
		document.getElementById('avatarPreview').src = e.target.result;
	};
	reader.readAsDataURL(file);
}

// Form validation
document.getElementById('profileForm').addEventListener('submit', function(e) {
	const newPassword = document.querySelector('input[name="newPassword"]').value;
	const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;
	const oldPassword = document.querySelector('input[name="oldPassword"]').value;

	// If user is trying to change password
	if (newPassword || confirmPassword || oldPassword) {
		if (!oldPassword) {
			alert('🔒 Vui lòng nhập mật khẩu hiện tại!');
			e.preventDefault();
			return;
		}

		if (newPassword.length < 6) {
			alert('🔒 Mật khẩu mới phải có ít nhất 6 ký tự!');
			e.preventDefault();
			return;
		}

		if (newPassword !== confirmPassword) {
			alert('🔒 Mật khẩu xác nhận không khớp!');
			e.preventDefault();
			return;
		}
	}
});