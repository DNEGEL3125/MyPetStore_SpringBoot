if (!String.prototype.format) {
    String.prototype.format = function () {
        const args = arguments;
        return this.replace(/{(\d+)}/g, function (match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
}

$(document).ready(function () {
    $(".go-to-page-button").click(function (event) {
        const $targetButton = $(event.target);
        const selectedPageNumber = Number($targetButton.siblings(".page-number-input").val());
        switchPage(selectedPageNumber);
    });

    $(".page-number-input").keydown(function (event) {
        // Enter 页面跳转
        if (event.key !== 'Enter')
            return;
        const $targetInput = $(event.target);
        const selectedPageNumber = Number($targetInput.val());
        switchPage(selectedPageNumber)
    });
});

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

// 当前页数 <= 4:显示1~6...n-1,n; 当前页数 >= n-3:显示1,2...n-5~n; else 显示1...中间5个...n
function showPageNumbers(maxPageNumber, currentPageNumber) {
    const pageIconContainer = $(".page-number-icon-container");
    const availablePageNumberHtml = "<button class='page-number-icon-btn btn page-number-icon-btn-{0}' >{0}</button>";
    const ellipsisSpan = "<span class='ellipsis-span'>...</span>";
    pageIconContainer.empty();
    if (maxPageNumber < 8) {
        for (let i = 1; i <= maxPageNumber; i++) {
            pageIconContainer.append(availablePageNumberHtml.format(i));
        }
    } else if (currentPageNumber <= 4) {
        for (let i = 1; i <= 6; i++) {
            pageIconContainer.append(availablePageNumberHtml.format(i));
        }
        pageIconContainer.append(ellipsisSpan);
        for (let i = maxPageNumber - 1; i <= maxPageNumber; i++) {
            pageIconContainer.append(availablePageNumberHtml.format(i));
        }
    } else if (currentPageNumber >= maxPageNumber - 3) {
        for (let i = 1; i <= 2; i++) {
            pageIconContainer.append(availablePageNumberHtml.format(i));
        }
        pageIconContainer.append(ellipsisSpan);
        for (let i = maxPageNumber - 5; i <= maxPageNumber; i++) {
            pageIconContainer.append(availablePageNumberHtml.format(i));
        }
    } else {
        pageIconContainer.append(availablePageNumberHtml.format(1));
        pageIconContainer.append(ellipsisSpan);
        for (let i = currentPageNumber - 2; i <= currentPageNumber + 2; i++) {
            pageIconContainer.append(availablePageNumberHtml.format(i));
        }
        pageIconContainer.append(ellipsisSpan);
        pageIconContainer.append(availablePageNumberHtml.format(maxPageNumber));
    }

    $(".page-number-icon-btn").click(function (event) {
        const buttonText = event.target.textContent;
        const selectedPageNumber = Number.parseInt(buttonText);
        switchPage(selectedPageNumber);
    });

    // Can't select current page
    pageIconContainer.find(`.page-number-icon-btn-${currentPageNumber}`).prop("disabled", true);
}