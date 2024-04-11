$(document).ready(function () {
    $('#login-form').submit(function (event) {
        // Prevent the default form submission
        event.preventDefault();

        // Serialize the form data
        const formData = $(this).serialize();

        // Make an AJAX POST request
        $.ajax({
            type: 'POST',
            url: '/admin/login',
            data: formData,
            success: function (response) {
                // Handle the successful response from the server
                console.log('Form submitted successfully!');
                alert(response);
            },
            error: function (xhr, status, error) {
                let response = xhr.responseText;
                if (!response) {
                    response = "账号或密码错误";
                }
                showErrorToast(response);
                renewVerificationCode();
            }
        });
    });
});

function renewVerificationCode() {
    // Make an AJAX POST request
    $.ajax({
        type: 'POST',
        url: '/admin/login/form/v-code',
        success: function (response) {
            $("#v-code-image").attr("src", "/admin/login/form/v-code");
        },
        error: function (xhr, status, error) {
            // Handle errors
            console.error('Error:', error);
        }
    });
}