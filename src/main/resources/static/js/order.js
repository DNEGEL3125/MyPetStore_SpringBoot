let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";
let newOrderCnt = 0;


// 遭到更改的元素id
const changedInputSet = new Set();

function sendOrdersChange() {
    if (changedInputSet.size === 0) {
        // Nothing changed
        showInfoToast("Nothing changed");
        return;
    }
    let jsonData = {};
    for (const $targetInput of changedInputSet) {
        const id = $targetInput.attr("id");
        const splitRes = id.split('-');
        const orderId = splitRes[2];
        const attrName = splitRes[0];

        // Split attrName by dots to handle nested properties
        const attrNames = attrName.split('.');

        // Check if jsonData[orderId] exists, if not, initialize it
        if (!jsonData[orderId]) {
            jsonData[orderId] = {};
        }

        // Assign value recursively for nested properties
        let tempObj = jsonData[orderId];
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
        url: `/admin/orders/update`,
        type: 'POST',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (response) {
            showSuccessToast(response);
            resetOrdersChange();
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "Failed to update the orders";
            }
            showErrorToast(response);
        }
    });
}


function resetOrdersChange() {
    changedInputSet.clear();
    displaySearchOrderTable(searchKeyword, searchFor, currentPage);
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
                'id': `${attrName}-input-new${newOrderCnt}`,
                'oninput': 'onInputChanged($(this))'
            })
        )
    );

    return newCol;
}

function addRowToTable() {
    $.get("/admin/orders/view/table/row/empty", function (response) {
        newOrderCnt++;
        let $newRow = $(response);
        // Iterate over elements with id attribute containing "null"
        $newRow.find('[id*="-null-"], [id$="-null"]').each(function () {
            // Get the current id attribute value
            const currentId = $(this).attr('id');

            // Replace "null" with newOrderCnt in the id attribute value
            const newId = currentId.replace(/-null(?=-|$)/g, '-new' + newOrderCnt);

            // Set the new id attribute value
            $(this).attr('id', newId);
        });
        $('#order-table tbody').append($newRow);
        $newRow.find("select").select2();
        const $attributesBtn = $newRow.find(".attributes-btn").prop("disabled", true);
        // 设置按钮的 data-toggle 和 title 属性
        $attributesBtn.attr('data-toggle', 'tooltip');
        $attributesBtn.attr('title', 'You need to save changes first.');
        showSuccessToast("The order has been added to the bottom of the table");
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
    displaySearchOrderTable(searchKeyword, searchFor, page);
}

$(document).ready(function () {
    console.log("order.js is ready");
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");

    $(".search-select").select2();

    // keydown event of search-input
    $("#search-input").keydown(function (ev) {
        if (ev.key === "Enter") {
            onSearchOrderBtnClicked();
        }
    });

    displayOrderTable();

    pagePreviousBtn.prop("disabled", true);
    pageNextBtn.prop("disabled", true);
});

function displayOrderTable(pageNumber = 1) {
    if (pageNumber < 1) {
        return;
    }

    searchFor = searchKeyword = "";

    let jsonData = {
        "pageNumber": pageNumber
    };
    $.ajax({
        url: `/admin/orders/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#order-table").replaceWith(tableHtml);
            $(".petBreed-select").select2();
            $("#table-change-btn-container").collapse("hide");
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "An error occurred while fetching the table";
            }
            showErrorToast(response);
        }
    });

    $.get("/admin/orders/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(pageNumber);
    });
}

function onSearchOrderBtnClicked() {
    searchKeyword = $("#search-input").val();
    // Example: `username` of `search-username`
    searchFor = $("#select-search-for").val().split('-')[1];
    displaySearchOrderTable(searchKeyword, searchFor);
}

function displaySearchOrderTable(keyword, searchFor, pageNumber = 1) {
    if (!keyword) {
        displayOrderTable(pageNumber);
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
        url: `/admin/orders/search/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#order-table").replaceWith(tableHtml);
            $(".petBreed-select").select2();
            $("#table-change-btn-container").collapse("hide");
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
        url: "/admin/orders/search/view/maxPageNumber",
        type: "GET",
        contentType: 'application/json',
        data: jsonData,
        success: function (response) {
            MAX_PAGES = response;
            // update button status
            setCurrentPage(pageNumber);
        },
        error: function (xhr, status, error) {
            showErrorToast("Failed to get maximum page number");
        }
    })

}


function showOrderDetails(orderId) {
    location.href = `/admin/order/details/view/${orderId}`
}