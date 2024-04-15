let MAX_PAGES = 1;
let currentPage = 1;
let searchKeyword = "";
let searchFor = "";
let newPetBreedCnt = 0;


// 遭到更改的元素id
const changedInputSet = new Set();


function sendPetBreedsChange($sender) {
    if (changedInputSet.size === 0) {
        // Nothing changed
        showInfoToast("Nothing changed");
        return;
    }

    // 检查表单合法
    const $form = $sender.closest("form");
    if (!$form[0].checkValidity()) {
        return;
    }

    let jsonData = {};
    for (const $targetInput of changedInputSet) {
        const id = $targetInput.attr("id");
        const splitRes = id.split('-');
        const petBreedId = splitRes[2];
        const attrName = splitRes[0];

        if (attrName === "delete") {
            deletePetBreed(petBreedId);
            continue;
        }

        // Split attrName by dots to handle nested properties
        const attrNames = attrName.split('.');

        // Check if jsonData[petBreedId] exists, if not, initialize it
        if (!jsonData[petBreedId]) {
            jsonData[petBreedId] = {};
        }

        // Assign value recursively for nested properties
        let tempObj = jsonData[petBreedId];
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
        url: `/admin/petBreeds/update`,
        type: 'POST',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (response) {
            showSuccessToast(response);
            resetPetBreedsChange();
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response) {
                response = "Failed to update the petBreeds";
            }
            showErrorToast(response);
        }
    });
}


function resetPetBreedsChange() {
    changedInputSet.clear();
    displaySearchPetBreedTable(searchKeyword, searchFor, currentPage);

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
                'id': `${attrName}-input-new${newPetBreedCnt}`,
                'oninput': 'onInputChanged($(this))'
            })
        )
    );

    return newCol;
}

function addRowToTable() {
    $.get("/admin/petBreeds/view/table/row/empty", function (response) {
        newPetBreedCnt++;
        let $newRow = $(response);
        // Iterate over elements with id attribute containing "null"
        $newRow.find('[id*="-null-"], [id$="-null"]').each(function () {
            // Get the current id attribute value
            const currentId = $(this).attr('id');

            // Replace "null" with newPetBreedCnt in the id attribute value
            const newId = currentId.replace(/-null(?=-|$)/g, '-new' + newPetBreedCnt);

            // Set the new id attribute value
            $(this).attr('id', newId);
        });
        $('#petBreed-table tbody').append($newRow);
        const $selectsInRow = $newRow.find("select");
        $selectsInRow.select2();
        onInputChanged($selectsInRow);

        showSuccessToast("The petBreed has been added to the bottom of the table");
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
    displaySearchPetBreedTable(searchKeyword, searchFor, page);
}

$(document).ready(function () {
    console.log("petBreed.js is ready");
    const pagePreviousBtn = $(".page-previous");
    const pageNextBtn = $(".page-next");

    $(".search-select").select2();

    // keydown event of search-input
    $("#search-input").keydown(function (ev) {
        if (ev.key === "Enter") {
            searchPetBreedBtnClicked();
        }
    });

    displayPetBreedTable();

    pagePreviousBtn.prop("disabled", true);
    pageNextBtn.prop("disabled", true);

    $("#petBreed-table-form").submit(function (ev) {
        ev.preventDefault();
    });

});

function onAttributesBtnClick($targetButton) {
    const petBreedId = $targetButton.attr("id").split("-")[2];
    getPetBreedAttributeListHtml(petBreedId)
        .then(function (petBreedAttributeListHtml) {
            showModal(
                petBreedAttributeListHtml,
                "PetBreed Attributes",
                "Save changes",
                sendPetBreedAttributeChanges,
                resetAttrInputChanges
            );
        })
}

function displayPetBreedTable(pageNumber = 1) {
    if (pageNumber < 1) {
        return;
    }

    searchFor = searchKeyword = "";

    let jsonData = {
        "pageNumber": pageNumber
    };
    $.ajax({
        url: `/admin/petBreeds/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#petBreed-table").replaceWith(tableHtml);
            $(".category-select").select2();
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

    $.get("/admin/petBreeds/view/maxPageNumber", function (response) {
        MAX_PAGES = response;
        // update button status
        setCurrentPage(currentPage);
    });
}

function searchPetBreedBtnClicked() {
    searchKeyword = $("#search-input").val();
    // Example: `username` of `search-username`
    searchFor = $("#select-search-for").val().split('-')[1];
    displaySearchPetBreedTable(searchKeyword, searchFor);
}

function displaySearchPetBreedTable(keyword, searchFor, pageNumber = 1) {
    if (!keyword) {
        displayPetBreedTable(pageNumber);
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
        url: `/admin/petBreeds/search/view/table`,
        type: 'GET',
        contentType: 'application/json', // Set content type to JSON
        data: jsonData,
        success: function (tableHtml) {
            $("#petBreed-table").replaceWith(tableHtml);
            $(".category-select").select2();
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
        url: "/admin/petBreeds/search/view/maxPageNumber",
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

function reloadImg($imgElement) {
    // Get the current src attribute value
    const currentSrc = $imgElement.attr('src');

    // Modify src attribute to the same value, forcing the image to reload
    $imgElement.attr('src', currentSrc);
}

function uploadPetBreedImg($uploadBtn) {
    const $imageInput = $uploadBtn.siblings(".image-input");
    const petBreedId = $uploadBtn.attr('id').split('-')[3];
    const fileInput = $imageInput[0].files[0]; // Get the selected image file
    const formData = new FormData(); // Create FormData object
    formData.append('image', fileInput); // Append the image file to FormData object

    $.ajax({
        url: `/admin/petBreeds/update/${petBreedId}/image`, // Replace this with your Spring Boot endpoint
        type: 'POST',
        data: formData,
        contentType: false, // Set contentType to false, let jQuery handle it automatically
        processData: false, // Set processData to false, prevent jQuery from automatically converting data to string
        success: function (response) {
            console.log('Image uploaded successfully:', response);
            const $petBreedImg = $uploadBtn.closest("div").siblings("img");
            $petBreedImg.attr("src", response)
            showSuccessToast('Image uploaded successfully:', response)
        },
        error: function (xhr, status, error) {
            console.error('Error uploading image:', error);
            showErrorToast(`Error uploading image: ${xhr.responseText}`)
        }
    });
}

function solveDeleteBtnClick($targetBtn) {
    $targetBtn.closest("tr").hide();
    onInputChanged($targetBtn);
}

function deletePetBreed(petBreedId) {
    $.ajax({
        url: `/admin/petBreeds/delete/${petBreedId}`,
        success: function (resp) {
            showSuccessToast(resp);
        },
        error: function (xhr) {
            showErrorToast(xhr.responseText)
        }
    });
}