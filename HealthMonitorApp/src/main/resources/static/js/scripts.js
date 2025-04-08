function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById("selectAll");
    const checkboxes = document.querySelectorAll("table input[type='checkbox']");

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAllCheckbox.checked;
    });

    checkSelectedOptions();
}

// Kiểm tra số lượng checkbox được chọn và cập nhật trạng thái của nút xóa
function checkSelectedOptions() {
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
        checkbox.addEventListener("change", checkSelectedOptions);
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

function renderDynamicDropdown( {
containerId,
        apiUrl,
        params = {},
        buildOptionText = item => item.label,
        pageSize = 10,
        placeholder = '',
        value = null
}) {
    let page = 1;
    let isLoading = false;
    let allLoaded = false;

    const container = document.getElementById(containerId);

    // Create temmp input dynamically to store selected value
    const tempBtn = document.createElement('button');
    tempBtn.classList.add('form-select');
    tempBtn.type = 'button';
    tempBtn.style.height = '37.6px';
    tempBtn.style.textAlign = 'left';


    const tempInput = container.querySelector('input');
    tempInput.type = 'hidden';

    // Create search input element dynamically
    const searchContainer = document.createElement('div');
    searchContainer.style.padding = '10px';
    searchContainer.style.position = 'sticky';
    searchContainer.style.top = '0';
    searchContainer.style.background = 'white';
    const searchInput = document.createElement('input');
    searchInput.type = 'text';
    searchInput.classList.add('form-control');


    // Create dropdown container dynamically
    const dropdown = document.createElement('div');
    dropdown.classList.add('dropdown-menu');
    dropdown.style.maxHeight = '300px';
    dropdown.style.overflowX = 'hidden';
    dropdown.style.overflowY = 'auto';
    dropdown.style.paddingTop = '0';

    // Append the search input, dropdown, and val input to the container
    container.prepend(tempBtn);
    container.appendChild(dropdown);
    if (value)
        tempBtn.innerText = buildOptionText(value);
    else
        tempBtn.innerText = placeholder;
    dropdown.appendChild(searchContainer);
    searchContainer.appendChild(searchInput);

    function loadData(reset = false, forceReset = false) {
        if ((isLoading || allLoaded) && !forceReset)
            return;

        if (reset) {
            page = 1;
            allLoaded = false;
            dropdown.innerHTML = '';
            dropdown.appendChild(searchContainer);
        }

        isLoading = true;

        const queryString = new URLSearchParams({
            page: page,
            size: pageSize,
            kw: searchInput.value,
            ...params
        }).toString();

        fetch(`${apiUrl}?${queryString}`)
                .then(res => res.json())
                .then(data => {
                    if (!data.results || data.results.length === 0) {
                        if (reset) {
                            dropdown.replaceChildren(searchContainer);
                            dropdown.insertAdjacentHTML('beforeend', "<div class='dropdown-item text-muted'>Không tìm thấy</div>");
                        }
                        allLoaded = true;
                        return;
                    }

                    data.results.forEach(item => {
                        const div = document.createElement('div');
                        div.classList.add('dropdown-item');
                        div.style.cursor = 'pointer';
                        div.textContent = buildOptionText(item);
                        div.dataset.id = item.id;
                        div.onclick = () => {
                            tempInput.value = item.id;
                            tempBtn.innerText = buildOptionText(item);
                            dropdown.style.display = 'none';
                        };
                        dropdown.appendChild(div);
                    });

                    if (!data.pagination || !data.pagination.more) {
                        allLoaded = true;
                    } else {
                        page++;
                    }
                })
                .finally(() => {
                    isLoading = false;
                    searchInput.focus();
                });
    }

    tempBtn.addEventListener('click', function () {
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        if (dropdown.style.display === 'block') {
            keyword = '';
            loadData(true);
            dropdown.style.width = `${tempBtn.offsetWidth}px`;
            searchInput.style.width = `calc(${tempBtn.offsetWidth}px - 30px)`;
            searchInput.focus();
        }
    });

    document.addEventListener('click', function (e) {
        if (!container.contains(e.target)) {
            dropdown.style.display = 'none';
        }
    });

    searchInput.addEventListener('input', function () {
        loadData(true, true);
    });

    dropdown.addEventListener('scroll', function () {
        if (this.scrollTop + this.clientHeight >= this.scrollHeight - 5) {
            loadData();
        }
    });
}

function previewImage(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        const imgElement = document.getElementById("avatarPreview");
        imgElement.src = e.target.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    }
}
