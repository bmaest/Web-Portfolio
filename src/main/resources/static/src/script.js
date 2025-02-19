//Nav Bar
function toggleMenu(){
    const menu = document.querySelector(".menu-links");
    const icon = document.querySelector(".icon");
    menu.classList.toggle("open");
    icon.classList.toggle("open");
}

// Dark Mode
document.addEventListener('DOMContentLoaded', () => {
    const darkModeIcon = document.getElementById('dark-mode-icon');
    const modeIcons = document.querySelectorAll('.mode-icon');
    const currentTheme = getCookie('theme');

    if (currentTheme === 'dark') {
        document.body.classList.add('dark-mode');
        modeIcons.forEach((icon) => {
            icon.src = icon.getAttribute('data-dark');
        });
    }

    darkModeIcon.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
        if (document.body.classList.contains('dark-mode')) {
            setCookie('theme', 'dark', 365);
            modeIcons.forEach((icon) => {
                icon.src = icon.getAttribute('data-dark');
            });
        } else {
            setCookie('theme', 'light', 365);
            modeIcons.forEach((icon) => {
                icon.src = icon.getAttribute('data-light');
            });
        }
    });
});

function setCookie(name, value, days) {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function getCookie(name) {
    const cname = name + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(cname) === 0) {
            return c.substring(cname.length, c.length);
        }
    }
    return "";
}












