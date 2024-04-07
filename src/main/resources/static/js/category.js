let currentPage = 1;
const changedIdSet = new Set();

function sendCategoriesChange() {

}

function resetCategoriesChange() {
    changedIdSet.clear();
    displayCategoryTable(currentPage);
}

function displayCategoryTable(page = 1) {
    if (page < 1) {
        return;
    }

    let jsonData = {
        "page": page
    };
    $.ajax({
        url: `/admin/categories/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#category-table").replaceWith(tableHtml);
        },
        error: function (xhr, status, error) {
            showErrorToast(error);
        }
    });
}

function displaySearchCategoryTable(searchInputField, searchSelectField) {

}

$(document).ready(function () {
    // Preview image dynamically
    $(".imagePath-input").on("input", function (event) {
        const $input = $(event.target); // Get the input element that triggered the event
        const imageUrl = $input.val();
        const categoryId = $input.attr('id').split('-')[2];
        $(`#imagePreview-${categoryId}`).attr('src', imageUrl);
    });
});
