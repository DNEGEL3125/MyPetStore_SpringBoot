let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";


// 遭到更改的元素id
const changedIdSet = new Set();

function sendAccountsChange() {
    if (changedIdSet.size === 0) {
        // Nothing changed
        showInfoToast("Nothing changed");
        return;
    }
    let jsonData = {};
    for (const id of changedIdSet) {
        const splitRes = id.split('-');
        const accountId = splitRes[2];
        const attrName = splitRes[0];

        // Check if jsonData[accountId] exists, if not, initialize it
        if (!jsonData[accountId]) {
            jsonData[accountId] = {};
        }
        jsonData[accountId][attrName] = $(`#${id}`).val();
    }

    // Convert the changedIdList to JSON string
    jsonData = JSON.stringify(jsonData);


    $.ajax({
        url: `/admin/accounts/update`,
        type: 'POST',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (response) {
            showSuccessToast(response);
            resetAccountsChange();
        },
        error: function (xhr, status, error) {
            showErrorToast(xhr.responseText);
        }
    });
}


function resetAccountsChange() {
    changedIdSet.clear();
    displaySearchAccountTable(searchKeyword, searchFor, currentPage);

}

function onInputChanged($targetInput) {
    const id = $targetInput.attr("id");
    changedIdSet.add(id);
    // 标注被修改的cell
    $targetInput.closest("td").addClass("table-info");
    // 更改帮助按钮栏
    $("#table-change-btn-container").collapse('show');
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
    displaySearchAccountTable(searchKeyword, searchFor, page);
}

$(document).ready(function () {
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");

    $(".search-select").select2();

    // keydown event of search-input
    $("#search-input").keypress(function (ev) {
        if (ev.key === "Enter") {
            onSearchAccountBtnClicked();
        }
    });

    displayAccountTable();

    pagePreviousBtn.prop("disabled", true);
    pageNextBtn.prop("disabled", true);

});

function displayAccountTable(pageNumber = 1) {
    if (pageNumber < 1) {
        return;
    }

    searchFor = searchKeyword = "";

    let jsonData = {
        "pageNumber": pageNumber
    };
    $.ajax({
        url: `/admin/accounts/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            const $accountTable = $("#account-table");
            $accountTable.replaceWith(tableHtml);
            $("#table-change-btn-container").collapse('hide');
            $('.status-select').select2();
        },
        error: function (xhr, status, error) {
            showErrorToast(xhr.responseText);
        }
    });

    $.get("/admin/accounts/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(pageNumber);
    });
}

function onSearchAccountBtnClicked() {
    searchKeyword = $("#search-input").val();
    // Example: `username` of `search-username`
    searchFor = $("#select-search-for").val().split('-')[1];
    displaySearchAccountTable(searchKeyword, searchFor);
}

function displaySearchAccountTable(keyword, searchFor, pageNumber = 1) {
    if (!keyword) {
        displayAccountTable(pageNumber);
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
        url: `/admin/accounts/search/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            const $accountTable = $("#account-table");
            $accountTable.replaceWith(tableHtml);
            $("#table-change-btn-container").collapse('hide');
            $('.status-select').select2();
        },
        error: function (xhr, status, error) {
            showErrorToast(xhr.responseText);
        }
    });

    $.ajax({
        url: "/admin/accounts/search/view/maxPageNumber",
        data: jsonData,
        success: function (response) {
            MAX_PAGES = response;
            // update button status
            setCurrentPage(pageNumber);
        },
        error: function (xhr) {
            showErrorToast(xhr.responseText);
        }
    })

}

