function showNotification() {
    function clearField() {
        let notifications = document.querySelectorAll('#notification-field .notification');
        if (notifications.length === 0) {
            document.querySelector('#notification-field').remove();
        }
    }

    let notification = document.createElement('div');
    notification.innerText = "Добавлено в корзину";
    notification.classList.add("notification");

    if (!document.querySelector('#notification-field')) {
        let field = document.createElement('div');
        field.id = "notification-field";
        document.body.appendChild(field);
    }
    document.querySelector('#notification-field').appendChild(notification);

    setTimeout(() => {
        notification.remove();
        clearField();
    }, 3000);
}

requestFunction = function (event) {
    event.preventDefault();
    let form = new FormData(event.target);
    let id = 'product_id=' + encodeURIComponent(form.get('product_id'));

    let request = new XMLHttpRequest();
    request.open("POST", context + "/menu", true);
    request.addEventListener('readystatechange', function () {
        if ((request.readyState === 4) && (request.status === 200)) {
            showNotification();
        } else if ((request.readyState === 4) && (request.status === 300)) {
            document.location.href = "login?redirect=" + context + "/menu";
        }
    });
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(id);
};

registerListeners = function () {
    let forms = document.getElementsByTagName('form');
    for (let i = forms.length - 1; i >= 0; i--) {
        let form = forms[i];
        if (form.className === 'add-product-from') {
            forms[i].addEventListener("submit", requestFunction);
        }
    }
};

document.addEventListener("DOMContentLoaded", registerListeners);
