let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";
let newProductCnt = 0;


// 遭到更改的元素id
const changedInputSet = new Set();

function sendProductsChange() {
    if (changedInputSet.size === 0) {
        // Nothing changed
        showInfoToast("Nothing changed");
        return;
    }
    let jsonData = {};
    for (const $targetInput of changedInputSet) {
        const id = $targetInput.attr("id");
        const splitRes = id.split('-');
        const productId = splitRes[2];
        const attrName = splitRes[0];

        // Split attrName by dots to handle nested properties
        const attrNames = attrName.split('.');

        // Check if jsonData[productId] exists, if not, initialize it
        if (!jsonData[productId]) {
            jsonData[productId] = {};
        }

        // Assign value recursively for nested properties
        let tempObj = jsonData[productId];
        for (let i = 0; i < attrNames.length - 1; i++) {
            const propName = attrNames[i];
            if (!tempObj[propName]) {
                tempObj[propName] = {};
            }
            tempObj = tempObj[propName];
        }
        tempObj[attrNames[attrNames.length - 1]] = $targetInput.val();
    }

    // Convert the changedIdList to JSON string
    jsonData = JSON.stringify(jsonData);

    console.log("update jsonData", jsonData)


    $.ajax({
        url: `/admin/products/update`,
        type: 'POST',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (response) {
            showSuccessToast(response);
            resetProductsChange();
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "Failed to update the products";
            }
            showErrorToast(response);
        }
    });
}


function resetProductsChange() {
    changedInputSet.clear();
    displaySearchProductTable(searchKeyword, searchFor, currentPage);
    $("#table-change-btn-container").collapse("hide");
}

function onInputChanged($targetInput) {
    changedInputSet.add($targetInput);
    // 标注被修改的cell
    $targetInput.closest("td").addClass("table-info");
    // 显示更改帮助按钮栏
    $("#table-change-btn-container").collapse("show");
}

function structInputTdHtml(attrName) {
    let newCol = $("<td>");
    newCol.append(
        $('<label>').append(
            $('<input>', {
                'id': `${attrName}-input-new${newProductCnt}`,
                'oninput': 'onInputChanged($(this))'
            })
        )
    );

    return newCol;
}

function addRowToTable() {
    $.get("/admin/products/view/table/row/empty", function (response) {
        newProductCnt++;
        let $newRow = $(response);
        // Iterate over elements with id attribute containing "null"
        $newRow.find('[id*="-null-"], [id$="-null"]').each(function () {
            // Get the current id attribute value
            const currentId = $(this).attr('id');

            // Replace "null" with newProductCnt in the id attribute value
            const newId = currentId.replace(/-null(?=-|$)/g, '-new' + newProductCnt);

            // Set the new id attribute value
            $(this).attr('id', newId);
        });
        $('#product-table tbody').append($newRow);
        $newRow.find("select").select2();
        const $attributesBtn = $newRow.find(".attributes-btn").prop("disabled", true);
        // 设置按钮的 data-toggle 和 title 属性
        $attributesBtn.attr('data-toggle', 'tooltip');
        $attributesBtn.attr('title', 'You need to save changes first.');
        showSuccessToast("The product has been added to the bottom of the table");
    });
}

function nextPage() {
    switchPage(currentPage + 1);
}

function previousPage() {
    switchPage(currentPage - 1);
}

function switchPage(page) {
    let setRes = setCurrentPage(page);
    if (!setRes) {
        return;
    }
    displaySearchProductTable(searchKeyword, searchFor, page);
}

$(document).ready(function () {
    console.log("product.js is ready");
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");

    $(".search-select").select2();

    // keydown event of search-input
    $("#search-input").keydown(function (ev) {
        if (ev.key === "Enter") {
            onSearchProductBtnClicked();
        }
    });

    displayProductTable();

    pagePreviousBtn.prop("disabled", true);
    pageNextBtn.prop("disabled", true);


});

function onAttributesBtnClick($targetButton) {
    const productId = $targetButton.attr("id").split("-")[2];
    getProductAttributeListHtml(productId).then(function (productAttributeListHtml) {
        showModal(
            productAttributeListHtml,
            "Product Attributes",
            sendProductAttributeChanges,
            resetAttrInputChanges
        );
        addAddAttributeBtn();
    })
}

function displayProductTable(pageNumber = 1) {
    if (pageNumber < 1) {
        return;
    }

    searchFor = searchKeyword = "";

    let jsonData = {
        "pageNumber": pageNumber
    };
    $.ajax({
        url: `/admin/products/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#product-table").replaceWith(tableHtml);
            $(".petBreed-select").select2();
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "An error occurred while fetching the table";
            }
            showErrorToast(response);
        }
    });

    $.get("/admin/products/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(currentPage);
    });
}

function onSearchProductBtnClicked() {
    searchKeyword = $("#search-input").val();
    // Example: `username` of `search-username`
    searchFor = $("#select-search-for").val().split('-')[1];
    displaySearchProductTable(searchKeyword, searchFor);
}

function displaySearchProductTable(keyword, searchFor, pageNumber = 1) {
    if (!keyword) {
        displayProductTable(pageNumber);
        return;
    }

    let jsonData = {
        "pageNumber": pageNumber,
        "keyword": keyword,
        "searchFor": searchFor
    };

    // Construct the relative URL with the query parameter
    // Navigate to the search results page
    $.ajax({
        url: `/admin/products/search/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#product-table").replaceWith(tableHtml);
            $(".petBreed-select").select2();
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "An error occurred while fetching the table";
            }
            showErrorToast(response);
        }
    });

    $.ajax({
        url: "/admin/products/search/view/maxPageNumber",
        type: "GET",
        contentType: 'application/json',
        data: jsonData,
        success: function (response) {
            MAX_PAGES = response;
            // update button status
            setCurrentPage(currentPage);
        },
        error: function (xhr, status, error) {
            showErrorToast("Failed to get maximum page number");
        }
    })

}

