function stopServer() {
    const recheckHtml = `
    <h5>Input password to continue</h5>
    <input id="check-admin-password-input" type="password" required>
    `;
    showModal(recheckHtml, "Input password", "OK", function () {
        const password = $("#check-admin-password-input").val();
        const jsonData = {
            "password": password
        };
        // Send a POST request to the /stop-requests endpoint using jQuery
        $.ajax({
            url: '/admin/stopRequests',
            type: 'POST',
            data: jsonData,
            success: function (response) {
                console.log(response);
                // Reload only the HTML content of the page
                $.get(location.href, function (html) {
                    $('html').html(html);
                    toastQueue.init().then(function () {
                        showSuccessToast(response);
                    })

                });

            },
            error: function () {
                showErrorToast('Failed to stop requests');
            }
        });

    });
}

function startServer() {
    // Send a POST request to the /stop-requests endpoint using jQuery
    $.ajax({
        url: '/admin/startRequests',
        type: 'POST',
        success: function (response) {
            showSuccessToast('Requests started');
            window.location.reload();
        },
        error: function (xhr) {
            console.log(xhr)
            showErrorToast('Failed to start requests');
        }
    });
}

