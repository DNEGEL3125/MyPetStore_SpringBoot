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
                window.location.href = "/admin";
            },
            error: function (xhr, status, error) {
                let response = xhr.responseText;
                if (!response) {
                    response = "Incorrect username or password";
                }
                showErrorToast(response);
                renewVerificationCode();
                // 清空验证码输入
                $("#v-code-input").val("");
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
            console.error(error);
        }
    });
}