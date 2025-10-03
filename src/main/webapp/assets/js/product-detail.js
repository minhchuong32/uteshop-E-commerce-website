
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
}
