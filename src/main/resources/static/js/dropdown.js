document.addEventListener('DOMContentLoaded', function() {
    var userMenu = document.getElementById('user-menu');
    var dropdownBtn = userMenu ? userMenu.querySelector('.user-dropdown-btn') : null;
    var dropdown = userMenu ? userMenu.querySelector('.user-dropdown') : null;

    if (!userMenu || !dropdownBtn || !dropdown) return;

    function openDropdown() {
        userMenu.classList.add('open');
        dropdownBtn.setAttribute('aria-expanded', 'true');
    }

    function closeDropdown() {
        userMenu.classList.remove('open');
        dropdownBtn.setAttribute('aria-expanded', 'false');
    }

    dropdownBtn.addEventListener('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        if (userMenu.classList.contains('open')) {
            closeDropdown();
        } else {
            openDropdown();
        }
    });

    dropdownBtn.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();
            dropdownBtn.click();
        }
        if (e.key === 'Escape') {
            closeDropdown();
            dropdownBtn.blur();
        }
    });

    userMenu.addEventListener('focusin', function() {
        openDropdown();
    });

    userMenu.addEventListener('focusout', function(e) {
        if (!userMenu.contains(e.relatedTarget)) {
            closeDropdown();
        }
    });

    document.addEventListener('click', function(e) {
        if (!userMenu.contains(e.target)) {
            closeDropdown();
        }
    });
});