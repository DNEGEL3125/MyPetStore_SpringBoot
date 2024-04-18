let logsViewMinId = Number.MAX_SAFE_INTEGER;
let logsViewMaxId = -1;
let selectedLevel = 'None';

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

async function showAddAdminModal() {
    const addAdminModal = await $.get("/admin/add/modal");
    const $addAdminModal = $(addAdminModal);
    $("#modal-container").html($addAdminModal);
    $addAdminModal.modal('show');
    $addAdminModal.find('form').submit(function (event) {
        event.preventDefault();
        const username = $addAdminModal.find("#admin-username-input").val();
        const password = $addAdminModal.find("#admin-password-input").val();
        const passwordRepeat = $addAdminModal.find("#admin-password-repeat-input").val();
        if (password !== passwordRepeat) {
            showErrorToast("Passwords don't match")
            return;
        }

        const jsonData = {
            "password": password,
            "username": username
        };

        $.ajax({
            url: "/admin/add",
            data: jsonData,
            success: function (response) {
                showSuccessToast(response);
                // Close modal
                $addAdminModal.modal("toggle");
            },
            error: function (xhr) {
                showErrorToast(xhr.responseText);
            }
        });
    });
}

function logOut() {
    $.ajax({
        url: "/admin/logOut",
        success: function (response) {

            showSuccessToast(response);
            setTimeout(function () {
                window.location.href = "/admin/login/form/view";
            }, 900);
        },
        error: function (xhr) {
            showErrorToast(xhr.responseText);
        }
    })
}

const putLogsToLogsView = function () {
    return function (logs, putOn = 'top') {


        const logsView = document.getElementById("logs-view");
        const $logsView = $(logsView)

        let putFunc;
        switch (putOn.toLowerCase()) {
            case "top":
                putFunc = $logsView.prepend.bind($logsView);
                break;
            case 'bottom':
                putFunc = $logsView.append.bind($logsView);
                break;
            default:
                throw new Error(`Unknown putOn '${putOn}'`)
        }

        const preScrollHeight = logsView.scrollHeight;
        const preScrollTop = $logsView.scrollTop();
        for (const log of logs) {
            const lineArray = [];
            lineArray.push(new Date(log.timestamp));
            lineArray.push(`<span class="log-${log['level'].toLowerCase()}">${log['level']}</span>`)
            lineArray.push(`<span style="color: limegreen">${log['loggerName']}</span>`)
            lineArray.push(log.message);
            const lineStr = lineArray.join(" |");
            putFunc(`<div>${lineStr}</div>`);
        }

        // 通过修改Scroll，让用户所看到的内容不发生改变
        $logsView.scrollTop(logsView.scrollHeight - preScrollHeight + preScrollTop);
    }
}();

const loadMoreLogs = function () {
    let lastId = -1;
    return function () {
        // 重复加载
        if (logsViewMinId === lastId && logsViewMinId !== Number.MAX_SAFE_INTEGER) {
            return;
        }
        lastId = logsViewMinId;
        console.log(`load more logs, minId = ${logsViewMinId}`);
        $.ajax({
            url: `/admin/log/log-level/${selectedLevel}/id-before/${logsViewMinId}`,
            success: function (response) {
                putLogsToLogsView(response);
                logsViewMinId = response[response.length - 1]['id'];
            },
            error: function () {
                showErrorToast("Fail to load logs");
            }
        });
    }
}();

const loadNewestLogs = function () {
    return function () {
        $.ajax({
            url: `/admin/log/id-after/${logsViewMaxId}`,
            success: function (response) {
                putLogsToLogsView(response, 'bottom');
                logsViewMaxId = response[response.length - 1]['id'];
            },
            error: function () {
                showErrorToast("Fail to load newest logs");
            }
        });
    }
}();

function reloadLogs() {
    $("#logs-view").empty();
    logsViewMinId = Number.MAX_SAFE_INTEGER;
    logsViewMaxId = -1;
    loadMoreLogs();
}


$(document).ready(function () {
    const logsView = document.getElementById("logs-view");
    const $logsView = $(logsView);
    loadMoreLogs();

    // 监听滚动事件
    $logsView.on('scroll', function () {
        // 如果滚动到顶部，则加载更多日志数据
        if ($(this).scrollTop() <= 20) {
            loadMoreLogs();
        }
    });

    // Handle change event of radio buttons
    $('input[name="filter"]').on('change', function () {
        selectedLevel = $(this).val();
        reloadLogs();
    });
});