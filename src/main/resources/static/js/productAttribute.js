let attributeList;

function structAttributeListHtmlItem() {

}

async function getProductAttributeListHtml(productId) {
    let resHtml = await $.get(`/admin/product-attributes/view/list/productId/${productId}`);

    return $(resHtml);
}

function addAddAttributeBtn() {
    let newAttrIdCnt = 1;
    const $addAttributeBtn = $("#add-attribute-btn");
    const $attributesUl = $addAttributeBtn.siblings("ul");
    $addAttributeBtn.click(function () {
        const showAttrLiId = `show-attr-li-new${newAttrIdCnt}`;
        const attrInputId = `attr-input-new${newAttrIdCnt}`;
        const deleteAttrBtnId = `delete-attr-btn-new${newAttrIdCnt}`;
        const liHtml = `
        <li id='${showAttrLiId}' class="attr-item">
            <input class="attr-input" id="${attrInputId}">
            <button id="${deleteAttrBtnId}"
                class="btn delete-attr-btn btn-danger" 
                onclick="$(this).parent().hide()">
     -
     </button>
     </li>
        `;
        $attributesUl.append(liHtml);

        const $newAttrInput = $attributesUl.find("#" + attrInputId);

        $newAttrInput.focus();

        $newAttrInput.blur(function () {
            const inputVal = $(this).val();
            // 如果失去焦点并且为空，表示被放弃
            if (!inputVal) {
                $attributesUl.children("#" + showAttrLiId).remove();
            }
        });
        newAttrIdCnt++;
    });
}


$(document).ready(function () {

});