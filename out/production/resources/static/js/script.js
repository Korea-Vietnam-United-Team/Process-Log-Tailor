// ===== Event for the Get Started button ===== //
document.getElementById('Upload').addEventListener('click', function () {
    fetchContent('upload.html');
});

function fetchContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById('content-section').innerHTML = data;
        })
        .catch(error => {
            console.error('Error fetching content:', error);
            document.getElementById('content-section').innerHTML = '<p>Error loading content. Please try again later.</p>';
        });
}

// ===== FAQ Answer ===== //
$(document).ready(function () {
    $('.faq-section li').click(function () {
        $(this).toggleClass('open');
        $(this).find('.answer').slideToggle();
    });
    // ===== Like icon  ===== //
    $('.like-icon').click(function () {
        const likeCountElement = $(this).siblings('.like-count');
        let likeCount = parseInt(likeCountElement.text());
        let isLiked = $(this).data('liked');
        if (isLiked) {
            likeCount -= 1;
            $(this).attr('src', 'https://cdn-icons-png.freepik.com/256/8647/8647296.png?ga=GA1.1.648560867.1713175690&semt=ais_hybrid');
            $(this).data('liked', false);
        } else {
            likeCount += 1;
            $(this).attr('src', 'https://cdn-icons-png.freepik.com/128/2839/2839214.png');
            $(this).data('liked', true);
        }
        likeCountElement.text(likeCount + ' likes');
    });

    // ===== Change image every 3 seconds ===== //
    let currentIndex = 0;
    const images = $('.carousel-image');
    const totalImages = images.length;

    function showNextImage() {
        images.eq(currentIndex).removeClass('active');
        currentIndex = (currentIndex + 1) % totalImages;
        images.eq(currentIndex).addClass('active');
    }

    setInterval(showNextImage, 3000);
});
