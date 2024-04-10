let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";


// 遭到更改的元素id
const changedIdSet = new Set();

function sendProductsChange() {
    if (changedIdSet.size === 0) {
        // Nothing changed
        showInfoToast("Nothing changed");
        return;
    }
    let jsonData = {};
    for (const id of changedIdSet) {
        const splitRes = id.split('-');
        const productId = splitRes[2];
        const attrName = splitRes[0];

        // Check if jsonData[productId] exists, if not, initialize it
        if (!jsonData[productId]) {
            jsonData[productId] = {};
        }
        jsonData[productId][attrName] = $(`#${id}`).val();
    }

    // Convert the changedIdList to JSON string
    jsonData = JSON.stringify(jsonData);


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
            if (!error) {
                error = "Failed to update the products";
            }
            showErrorToast(error);
        }
    });
}


function resetProductsChange() {
    changedIdSet.clear();
    displaySearchProductTable(searchKeyword, searchFor, currentPage);
    $("#table-change-btn-container").hide();
}

function onInputChanged(id) {
    changedIdSet.add(id);
    // 标注被修改的cell
    $(`#${id}`).closest("td").addClass("table-info");
    // 更改帮助按钮栏
    $("#table-change-btn-container").show();
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

    $("#table-change-btn-container").hide();

    // keydown event of search-input
    $("#search-input").keypress(function (ev) {
        if (ev.keyCode === 13) {
            onSearchProductBtnClicked();
        }
    });

    displayProductTable();

    pagePreviousBtn.prop("disabled", true);
    pageNextBtn.prop("disabled", true);


});

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
            $(".attributes-btn").click(function (event) {
                const $targetButton = $(event.target);
                const productId = $targetButton.attr("id").split("-")[2];
                getProductAttributeListHtml(productId).then(function (productAttributeListHtml) {
                    showModal(
                        productAttributeListHtml,
                        "Product Attributes",
                        function () {
                            alert(404)
                        });
                    addAddAttributeBtn();
                })
            });
        },
        error: function (xhr, status, error) {
            if (!error) {
                error = "An error occurred while fetching the table";
            }
            showErrorToast(error);
        }
    });

    $.get("/admin/products/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(currentPage);
    });
}

function sendAttributesChange() {

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
            $(".attributes-btn").click(function (event) {
                const $targetButton = $(event.target);
                const productId = $targetButton.attr("id").split("-")[2];
                getProductAttributeListHtml(productId).then(function (productAttributeListHtml) {
                    showModal(
                        productAttributeListHtml,
                        "Product Attributes",
                        function () {
                            alert(404)
                        });
                    addAddAttributeBtn();
                })

            });
        },
        error: function (xhr, status, error) {
            if (!error) {
                error = "An error occurred while fetching the table";
            }
            showErrorToast(error);
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

