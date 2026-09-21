    document.querySelectorAll('img').forEach(img => {
        img.addEventListener('click', function() {
            this.classList.toggle('zoomed');
        });
    });
