let attributeList;
// productAttributeId => newVal
// if newVal == "": delete
let productAttrContentChangedMap = {};

async function getProductAttributeListHtml(productId) {
    let resHtml;

    resHtml = await $.get(`/admin/product-attributes/view/list/productId/${productId}`);

    return $(resHtml);
}

function deleteAttributeByBtn($btnField) {
    const id = $btnField.attr("id");
    const productAttributeId = id.split('-')[3];
    if (productAttributeId.indexOf("new") === -1)
        // 如果不是新增的，则在数据库删除
        productAttrContentChangedMap[productAttributeId] = "";
    else {
        // 如果是新增的，则直接从前端删除
        delete productAttrContentChangedMap[productAttributeId];
    }
    $btnField.parent().remove();
}

function solveAddAttributeBtnClick($addAttributeBtn) {
    let newAttrIdCnt = 1;
    const $attributesUl = $addAttributeBtn.siblings("ul");
    const productId = $attributesUl.attr("id").split('-')[2];
    $addAttributeBtn.click(function () {
        const showAttrLiId = `show-attr-li-${productId}new${newAttrIdCnt}`;
        const attrInputId = `attr-input-${productId}new${newAttrIdCnt}`;
        const deleteAttrBtnId = `delete-attr-btn-${productId}new${newAttrIdCnt}`;
        const liHtml = `
        <li id='${showAttrLiId}' class="attr-item">
            <input class="attr-input" id="${attrInputId}" oninput="onAttrInputChanged($(this))">
            <button id="${deleteAttrBtnId}"
                class="btn delete-attr-btn btn-danger" 
                onclick="deleteAttributeByBtn($(this))">
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
            } else {
                // 此事件只触发一次
                $newAttrInput.off("blur");
            }
        });
        newAttrIdCnt++;
    });
}

function sendProductAttributeChanges() {
    if (Object.keys(productAttrContentChangedMap).length === 0) {
        showInfoToast("Nothing changed");
        return;
    }

    const jsonData = JSON.stringify(productAttrContentChangedMap);

    $.ajax({
        url: `/admin/product-attributes/update`,
        type: 'POST',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (response) {
            showSuccessToast(response);
            resetAttrInputChanges();
        },
        error: function (xhr, status, error) {
            if (!error) {
                error = "Failed to update the attributes";
            }
            showErrorToast(error);
        }
    });
}


function onAttrInputChanged($field) {
    const productAttrId = $field.attr("id").split("-")[2];
    productAttrContentChangedMap[productAttrId] = $field.val();
}

function resetAttrInputChanges() {
    productAttrContentChangedMap = {};
    console.log("resetAttrInputChanges")
}