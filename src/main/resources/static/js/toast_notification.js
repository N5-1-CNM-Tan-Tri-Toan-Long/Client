function toast({
                   title = '',
                   message = '',
                   type = 'info',
                   duration = 3000
               }) {
    const main = document.getElementById('toast');
    if (main) {
        const toast = document.createElement('div');
        // Auto remove toast
        const autoRemoveId = setTimeout(function () {
            main.removeChild(toast);
        }, duration + 1000);

        // Remove toast when clickes
        toast.onclick = function (e) {
            if (e.target.closest('.toast__close')) {
                main.removeChild(toast);
                clearTimeout(autoRemoveId);
            }
        }


        const icons = {
            success: 'fa fa-check-circle',
            info: 'fa fa-info-circle',
            warning: 'fa fa-exclamation-circle',
            error: 'fa fa-exclamation-circle',

        };
        const icon = icons[type];
        const delay = (duration / 1000).toFixed(2);

        toast.classList.add('toast', `toast--${type}`);
        toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;

        toast.innerHTML = `
                <div class="toast__icon">
                    <i class="${icon}"></i>
                </div>
                <div class="toast__body">
                    <h3 class="toast__title">${title}</h3>
                    <p class="toast__msg">${message}</p>
                </div>
                <div class="toast__close">
                    <i class="fa fa-times"></i>
                </div>
                `;
        main.appendChild(toast);


    }
}

var mess = document.getElementById("mes");
var suc_err = document.getElementById("suc_err");
var time = 3500;

if(mess.textContent === ''){
    time = -3500;
}

function showToast() {
    toast({
        title: 'Thông báo!',
        message: mess.textContent + '',
        type: suc_err.textContent + '',
        duration: time
    });
}