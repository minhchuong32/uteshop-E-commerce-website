


function changeImage(el) {
    document.getElementById("mainImg").src = el.src;
    document.querySelectorAll(".thumb-img").forEach(img => img.classList.remove("active"));
    el.classList.add("active");
}

function changeQty(delta) {
    let qty = document.getElementById("qty");
    let val = parseInt(qty.value) + delta;
    if (val < 1) val = 1;
    qty.value = val;
    syncQty(); // đồng bộ luôn khi bấm +/-
}



// Đồng bộ số lượng từ input qty vào 2 form
function syncQty() {
    let qty = document.getElementById("qty").value;
    document.getElementById("formQty").value = qty;
    document.getElementById("formQtyNow").value = qty;
}

// Khi DOM load xong mới gắn event
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("qty").addEventListener("input", syncQty);
});


