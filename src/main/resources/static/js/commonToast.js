const ToastBgColor = {
    Success: "#53a653",
    Info: "#0dcaf0",
    Danger: "#B71C1C"
};

class ToastQueue {

    constructor(size = 8) {
        this.qPointer = 0;
        this.size = size;
        this.data = [];
    }

    async init() {
        const toastsContainer = $("#toasts-container");

        // Fetch the HTML content once
        const htmlData = await $.get("/html-plugins/Toast.html");

        // Convert the HTML string to a jQuery object
        const $toastHtml = $(htmlData);

        for (let i = 0; i < this.size; i++) {
            const $toastClone = $toastHtml.clone();
            this.data.push($toastClone);
            toastsContainer.append($toastClone);
        }

        console.log(this.data)
    }

    getNewToast() {
        const toastToReturn = this.data[this.qPointer];
        if (toastToReturn.hasClass("show")) {
            console.log("All toasts have been used");
        }

        this.qPointer = (this.qPointer + 1) % this.size;
        return toastToReturn;
    }
}

const toastQueue = new ToastQueue();

$(document).ready(function () {
    const allToasts = $(".toast");

    void toastQueue.init();

    // 在显示 Toast 时可点击
    allToasts.on('show.bs.toast', function () {
        $(this).show();
    });

    // 在隐藏 Toast 时不可点击
    allToasts.on('hidden.bs.toast', function () {
        $(this).hide();
    });


});

function showInfoToast(text) {
    const $toastHtml = toastQueue.getNewToast();
    $toastHtml.find(".toast-body").text(text);
    $toastHtml.toast("show");
    $toastHtml.css("background-color", ToastBgColor.Info)
}

function showSuccessToast(text) {
    const $toastHtml = toastQueue.getNewToast();
    $toastHtml.find(".toast-body").text(text);
    $toastHtml.toast("show");
    $toastHtml.css("background-color", ToastBgColor.Success)
}

function showErrorToast(text) {
    const $toastHtml = toastQueue.getNewToast();
    $toastHtml.find(".toast-body").text(text);
    $toastHtml.toast("show");
    $toastHtml.css("background-color", ToastBgColor.Danger)
}

