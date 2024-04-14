const plugin = {
    id: 'customCanvasBackgroundColor',
    beforeDraw: (chart, args, options) => {
        const {ctx} = chart;
        ctx.save();
        ctx.globalCompositeOperation = 'destination-over';
        ctx.fillStyle = options.color || 'white';
        ctx.fillRect(0, 0, chart.width, chart.height);
        ctx.restore();
    }
};

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
                plugins: [plugin],
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
        plugins: [plugin],
    };

    new Chart($("#total-revenue-chart"), config);
}

async function displayCategorySales() {
    const response = await $.get("/admin/dashboard/data/categorySales");

    const labels = [];
    const dataSetData = [];
    for (const categorySalesDto of response) {
        labels.push(categorySalesDto['categoryName']);
        dataSetData.push(categorySalesDto['sales']);
    }

    const data = {
        labels: labels,
        datasets: [{
            label: 'Category sales',
            data: dataSetData,
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            backgroundColor: [
                'rgba(230, 62, 68, 0.6)', // 淡红色
                'rgba(224, 69, 249, 0.6)', // 淡紫色
                'rgba(255, 205, 86, 0.6)', // 淡黄色
                'rgba(54, 162, 235, 0.6)', // 淡蓝色
                'rgba(65, 231, 74, 0.6)'    // 淡绿色
            ],
            tension: 0.1
        }]

    };

    const config = {
        type: 'doughnut', // 设置图表类型
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Category Sales'
                }
            }
        },
        plugins: [plugin]
    };

    new Chart($("#category-sales-chart"), config);
}

function displayAccountCountText() {
    $.get("/admin/dashboard/data/accountCount").then(function (response) {
        // $("#account-count-text").text('网站注册总人数：');
        $("#account-count-number").text(response);
    });
}

function displayOrderCountText() {
    $.get("/admin/dashboard/data/orderCount").then(function (response) {
        // $("#order-count-text").text('订单数：');
        $("#order-count-number").text(response);
    });
}

function displayTotalRevenueText() {
    $.get("/admin/dashboard/data/totalRevenue").then(function (response) {
        // $("#total-revenue-text").text('订单额：$');
        $("#total-revenue-number").text(`$${response}`);
    });
}

function updateDashboardTexts() {
    displayAccountCountText();
    displayTotalRevenueText()
    displayOrderCountText()
}

$(document).ready(function () {
        displayOrderCount();
        void displayTotalRevenue();
        void displayCategorySales();

        updateDashboardTexts();

        setInterval(updateDashboardTexts, 5000);
    }
)