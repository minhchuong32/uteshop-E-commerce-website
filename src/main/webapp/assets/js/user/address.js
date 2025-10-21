function showEditForm(id, name, phone, address, ward, district, city, isDefault) {
    document.getElementById('editFormContainer').style.display = 'block';
    document.getElementById('editAddressId').value = id;
    document.getElementById('editRecipientName').value = name;
    document.getElementById('editPhone').value = phone;
    document.getElementById('editAddressLine').value = address;
    document.getElementById('editWard').value = ward;
    document.getElementById('editDistrict').value = district;
    document.getElementById('editCity').value = city;
    document.getElementById('editIsDefault').checked = (isDefault === 'true');
}

function cancelEdit() {
    document.getElementById('editFormContainer').style.display = 'none';
}