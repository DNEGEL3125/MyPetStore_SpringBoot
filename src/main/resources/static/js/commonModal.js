let $modal;

async function loadModal() {
    const htmlData = await $.get("/html-plugins/Modal.html");
    $modal = $(htmlData);
    $("#modal-container").html($modal);
}

function showModal(htmlBody, title = "", yesFunction) {
    $modal.modal("show");
    $modal.find(".modal-body").html(htmlBody);
    $modal.find(".modal-title").text(title);
    $modal.find(".yes-btn").click(yesFunction);
    $modal.find(".close").click(function () {
        // 关闭时消除之前设置的点击事件，防止多重事件触发
        $modal.find(".yes-btn").off("click", yesFunction);
        $modal.modal("hide");
    });
}

$(document).ready(function () {
    void loadModal();
});