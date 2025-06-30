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

// Site Cookies
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

//Photo Gallery
let currentDeckIndex = 0;

function changeDeckImage(direction) {
    const items = document.querySelectorAll('.deck-gallery .gallery-item');
    if (!items.length) return;

    items[currentDeckIndex].classList.remove('active');
    currentDeckIndex = (currentDeckIndex + direction + items.length) % items.length;
    items[currentDeckIndex].classList.add('active');
}

//PowerShell Terminal
function openTerminal() {
    document.getElementById('powershell-modal').classList.remove('hidden');
    document.getElementById('powershell-window').innerHTML = '';
    addPrompt(); // Reinitialize prompt
}

function closeTerminal() {
    document.getElementById('powershell-modal').classList.add('hidden');
}

document.addEventListener('DOMContentLoaded', () => {
    const terminal = document.getElementById('powershell-window');
    addPrompt();
});

function addPrompt() {
    const terminal = document.getElementById('powershell-window');

    // Remove 'active' class from previous input
    const previous = terminal.querySelector('.user-input.active');
    if (previous) previous.classList.remove('active');

    const prompt = document.createElement('div');
    prompt.innerHTML = 'PS C:\\Users\\Administrator&gt; <span class="user-input active" contenteditable="true" spellcheck="false">\u200B</span>';
    terminal.appendChild(prompt);

    const input = prompt.querySelector('.user-input');
    input.focus();

    prompt.addEventListener('click', () => {
        input.focus();
    });

    input.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const cmd = input.innerText.replace(/\u200B/g, '').trim();
            input.contentEditable = false;
            input.classList.remove('active');

            fetch('/api/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ command: cmd })
            })
                .then(res => res.json())
                .then(data => {
                    appendLine(data.output);
                    addPrompt();
                });
        }
    });
}

function appendLine(html) {
    const terminal = document.getElementById('powershell-window');
    const line = document.createElement('div');
    line.innerHTML = html;
    terminal.appendChild(line);
    terminal.scrollTop = terminal.scrollHeight;
}