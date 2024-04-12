function displayOrderCount() {
    $.ajax({
        type: 'GET',
        url: '/admin/dashboard/data/orderCount/year',
        success: function (response) {
            const labels = [];
            const dataSetData = [];
            for (const orderDto of response) {
                labels.push(orderDto['timePeriod']);
                dataSetData.push(orderDto['orderCount']);
            }

            const data = {
                labels: labels,
                datasets: [{
                    label: 'Number of orders',
                    data: dataSetData,
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)', // 设置线的颜色
                    tension: 0.1
                }]
            };

            const config = {
                type: 'line', // 设置图表类型
                data: data,
            };

            new Chart($("#order-count-chart"), config);
        },
        error: function (xhr, status, error) {
            let response = xhr.responseText;
            if (!response || response.length > 32) {
                response = "Fail to fetch order count data";
            }
            showErrorToast(response);
        }
    });
}

async function displayTotalRevenue() {
    const response = await $.get("/admin/dashboard/data/totalRevenue/year");

    const labels = [];
    const dataSetData = [];
    for (const totalRevenueDto of response) {
        labels.push(totalRevenueDto['timePeriod']);
        dataSetData.push(totalRevenueDto['totalRevenue']);
    }

    const data = {
        labels: labels,
        datasets: [{
            label: 'Annual revenue',
            data: dataSetData,
            fill: false,
            borderColor: 'rgb(75, 192, 192)', // 设置线的颜色
            tension: 0.1
        }]
    };

    const config = {
        type: 'line', // 设置图表类型
        data: data,
    };

    new Chart($("#total-revenue-chart"), config);
}

function displayAccountCount() {
    $.get("/admin/dashboard/data/accountCount").then(function (response) {
        $("#account-count-display").text(`网站注册人数：${response}`);
    });
}

$(document).ready(function () {
        displayOrderCount();
        void displayTotalRevenue();

        displayAccountCount();
        setInterval(displayAccountCount, 5000);
    }
)