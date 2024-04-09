let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";

function setCurrentPage(page) {
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");


    if (page > MAX_PAGES || page < 1) {
        return false;
    }

    pagePreviousBtn.prop("disabled", false);
    pageNextBtn.prop("disabled", false);

    currentPage = page;

    if (currentPage === 1) {
        pagePreviousBtn.prop("disabled", true);
    }
    if (currentPage === MAX_PAGES) {
        pageNextBtn.prop("disabled", true);
    }

    showPageNumbers(MAX_PAGES, currentPage);

    console.log("current page number is ", currentPage);

    return true;
}


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
            changedIdSet.clear();
            displaySearchAccountTable(searchKeyword, searchFor, currentPage);
        },
        error: function (xhr, status, error) {
            showErrorToast(error);
        }
    });
}


function resetAccountsChange() {
    changedIdSet.clear();
    displayAccountTable(currentPage);
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
    displaySearchAccountTable(searchKeyword, searchFor, page);
}

$(document).ready(function () {
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");

    $(".search-select").select2();

    $("#table-change-btn-container").hide();

    // keydown event of search-input
    $("#search-input").keypress(function (ev) {
        if (ev.keyCode === 13) {
            displaySearchAccountTable($(this).val(), $(`#select-search-for`).val());
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
            $("#account-table").replaceWith(tableHtml);
        },
        error: function (xhr, status, error) {
            showErrorToast(error);
        }
    });

    $.get("/admin/accounts/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(currentPage);
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
            $("#account-table").replaceWith(tableHtml);
        },
        error: function (xhr, status, error) {
            showErrorToast(error);
        }
    });


    $.get("/admin/accounts/search/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(currentPage);
    });

}

