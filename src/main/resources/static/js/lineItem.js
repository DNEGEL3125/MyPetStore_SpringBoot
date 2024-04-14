let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";
let orderId = 1;

function setOrderId(newOrderId) {
    orderId = Number(newOrderId);
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
    displaySearchLineItemTable(searchKeyword, searchFor, page);
}

$(document).ready(function () {
    console.log("product.js is ready");
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");

    $(".search-select").select2();

    // keydown event of search-input
    $("#search-input").keydown(function (ev) {
        if (ev.key === "Enter") {
            solveSearchLineItemBtnClicked();
        }
    });

    displayLineItemTable();

    pagePreviousBtn.prop("disabled", true);
    pageNextBtn.prop("disabled", true);


});

function displayLineItemTable(pageNumber = 1) {
    if (pageNumber < 1) {
        return;
    }

    searchFor = searchKeyword = "";

    let jsonData = {
        "pageNumber": pageNumber
    };
    $.ajax({
        url: `/admin/order/details/view/${orderId}/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#lineItem-table").replaceWith(tableHtml);
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "An error occurred while fetching the table";
            }
            showErrorToast(response);
        }
    });

    $.get(`/admin/order/details/view/${orderId}/totalPageNumber`, function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(currentPage);
    });
}

function solveSearchLineItemBtnClicked() {
    searchKeyword = $("#search-input").val();
    // Example: `username` of `search-username`
    searchFor = $("#select-search-for").val().split('-')[1];
    displaySearchLineItemTable(searchKeyword, searchFor);
}

function displaySearchLineItemTable(keyword, searchFor, pageNumber = 1) {
    if (!keyword) {
        displayLineItemTable(pageNumber);
        return;
    }

    let jsonData = {
        "pageNumber": pageNumber,
        "searchKeyword": keyword,
        "searchFor": searchFor
    };

    // Construct the relative URL with the query parameter
    // Navigate to the search results page
    $.ajax({
        url: `/admin/order/details/search/view/${orderId}/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#lineItem-table").replaceWith(tableHtml);
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
        url: `/admin/order/details/search/view/${orderId}/totalPageNumber`,
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