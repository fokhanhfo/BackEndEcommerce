function getAllCategories() {
    fetch('http://localhost:8080/category/getAll', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lấy danh sách danh mục từ API.');
            }
            return response.json();
        })
        .then(categories => {
            const categoriesList = document.querySelector('.sidebar__item ul');
            categories.forEach(category => {
                const listItem = document.createElement('li');
                const link = document.createElement('a');
                link.href = `#`; // Đặt đường dẫn của liên kết tùy ý
                link.textContent = category.name; // Sử dụng tên danh mục từ API
                listItem.appendChild(link);
                categoriesList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error('Lỗi:', error);
        });
}

getAllCategories();