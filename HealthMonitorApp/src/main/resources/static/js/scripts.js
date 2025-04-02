function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById("selectAll");
    const checkboxes = document.querySelectorAll("table input[type='checkbox']");

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAllCheckbox.checked;
    });

    checkSelectedUsers();
}

// Kiểm tra số lượng checkbox được chọn và cập nhật trạng thái của nút xóa
function checkSelectedUsers() {
    const checkboxes = document.querySelectorAll("table input[type='checkbox']");
    const checkedBoxes = document.querySelectorAll("table input[type='checkbox']:checked");
    const selectAllCheckbox = document.getElementById("selectAll");
    const deleteButton = document.getElementById("deleteButton");

    selectAllCheckbox.checked = checkboxes.length === checkedBoxes.length;

    deleteButton.disabled = checkedBoxes.length === 0;
}

// Gán sự kiện `onchange` cho từng checkbox để theo dõi sự thay đổi
document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll("table input[type='checkbox']");
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener("change", checkSelectedUsers);
    });
});

// Thông báo
setTimeout(function () {
    document.querySelectorAll('.alert').forEach(alert => {
        alert.style.display = 'none';
    });
}, 10000);

// Hàm hiển thị thông báo
function showMessage(type, message) {
    const messageContainer = document.querySelector("#messageContainer");
    const alertDiv = document.createElement("div");
    alertDiv.classList.add("alert", `alert-${type}`, "alert-dismissible", "fade", "show");
    alertDiv.setAttribute("role", "alert");
    alertDiv.innerHTML = `
                <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'danger' ? 'exclamation-circle' : 'info-circle'}"></i>
                <span>${message}</span>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            `;
    messageContainer.appendChild(alertDiv);

    setTimeout(() => {
        alertDiv.classList.remove('show');
    }, 10000);
}