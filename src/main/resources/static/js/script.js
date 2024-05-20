const body = document.querySelector('body'),
    sidebar = body.querySelector('nav'),
    toggle = body.querySelector(".toggle"),
    searchBtn = body.querySelector(".search-box"),
    modeSwitch = body.querySelector(".toggle-switch"),
    modeText = body.querySelector(".mode-text");


toggle.addEventListener("click", () => {
    sidebar.classList.toggle("close");
})

searchBtn.addEventListener("click", () => {
    sidebar.classList.remove("close");
})

modeSwitch.addEventListener("click", () => {
    body.classList.toggle("dark");

    if (body.classList.contains("dark")) {
        modeText.innerText = "Light mode";
    } else {
        modeText.innerText = "Dark mode";

    }
});

/* ===== Phần điều khiển chuyển trang ===== */
// Lấy phần section home
const homeSection = document.querySelector('.home');
// Lấy phần section upload
const homeSection = document.querySelector('.upload');
// Lấy phần section 기능 1
const homeSection = document.querySelector('.select');
// Lấy phần section 기능 2
const homeSection = document.querySelector('.slice');
// Lấy phần section 기능 3
const homeSection = document.querySelector('.union');
// Lấy phần section about
const homeSection = document.querySelector('.about');

// Lấy tất cả các liên kết menu
const menuLinks = document.querySelectorAll('.menu-links .nav-link');

// Lặp qua từng liên kết menu và thêm sự kiện click
menuLinks.forEach(link => {
    link.addEventListener('click', function (event) {
        // Ngăn chặn hành vi mặc định của thẻ a
        event.preventDefault();

        // Lấy đường dẫn từ thuộc tính href của thẻ a
        const href = this.querySelector('a').getAttribute('href');

        // Kiểm tra xem nếu là mục Revenue thì load upload.html vào section home
        if (href === '#Revenue') {
            fetch('index.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
        if (href === '#Home') {
            fetch('home.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
        if (href === '#') {
            fetch('home.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
        if (href === '#기능 1') {
            fetch('home.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
        if (href === '#기능 2') {
            fetch('home.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
        if (href === '#기능 3') {
            fetch('home.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
        if (href === '#About') {
            fetch('home.html') // Load upload.html
                .then(response => response.text())
                .then(html => {
                    homeSection.innerHTML = html; // Thay đổi nội dung của section home bằng nội dung của upload.html
                })
                .catch(error => console.error('Error fetching content:', error));
        }
    });
});

