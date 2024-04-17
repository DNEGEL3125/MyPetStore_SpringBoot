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


const solveAddAttributeBtnClick = (function () {
    let newAttrIdCnt = 1;
    return async function ($addAttributeBtn) {
        const $attributesUl = $addAttributeBtn.siblings("ul");
        const productId = $attributesUl.attr('id').split('-')[2];
        const showAttrLiId = `show-attr-li-${productId}new${newAttrIdCnt}`;
        const attrInputId = `attr-input-${productId}new${newAttrIdCnt}`;
        const deleteAttrBtnId = `delete-attr-btn-${productId}new${newAttrIdCnt}`;
        const liHtml = await $.get(`/admin/product-attributes/view/list/li/productId/${productId}`);

        const $liHtml = $(liHtml);

        console.log(liHtml);
        console.log($liHtml)
        $attributesUl.append($liHtml);

        $liHtml.attr('id', showAttrLiId);
        const $newAttrInput = $liHtml.find('.attr-input');
        $newAttrInput.attr('id', attrInputId);
        $liHtml.find('.delete-attr-btn').attr('id', deleteAttrBtnId);

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
    }
})();

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