const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");
const dropbtn = document.querySelector(".dropbtn");

function toggleMenu() {
    if (menu.classList.contains("expanded")) {
        menu.classList.remove("expanded");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="fa-solid fa-bars"></i>';
    } else {
        menu.classList.add("expanded");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="fa-solid fa-bars-staggered"></i>';
    }
}

function toggleDropdown(event) {
    event.preventDefault(); // Zapobiega przeładowaniu strony
    const parent = this.parentElement;
    if (parent.classList.contains("expanded")) {
        parent.classList.remove("expanded");
        dropbtn.innerHTML = 'Clothes <i class="fa fa-caret-down"></i>';
    } else {
        parent.classList.add("expanded");
        dropbtn.innerHTML = 'Clothes <i class="fa fa-caret-up"></i>';
    }
}

toggle.addEventListener("click", toggleMenu, false);
dropbtn.addEventListener("click", toggleDropdown, false);
