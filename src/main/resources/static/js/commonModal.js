let $modal;

async function loadModal() {
    const htmlData = await $.get("/html-plugins/Modal.html");
    $modal = $(htmlData);
    $("#modal-container").html($modal);
}

function showModal(htmlBody, title = "", yesBtnText = "OK", yesFunction = null, closeFunction = null) {
    const $yesBtn = $modal.find(".yes-btn");
    const $closeBtn = $modal.find(".close");
    $modal.modal("show");
    $modal.find(".modal-body").html(htmlBody);
    $modal.find(".modal-title").text(title);
    $yesBtn.click(function () {
        // 关闭时消除之前设置的点击事件，防止多重事件触发
        $yesBtn.off("click");
        $closeBtn.off("click");
    }).text(yesBtnText);
    if (yesFunction)
        $yesBtn.click(yesFunction);
    if (closeFunction)
        $closeBtn.click(closeFunction);
    $closeBtn.click(function () {
        // 关闭时消除之前设置的点击事件，防止多重事件触发
        $yesBtn.off("click");
        $closeBtn.off("click");
    });

}

$(document).ready(function () {
    void loadModal();
});