function showInform(categoryId) {
    console.log(categoryId);

    // Sending AJAX request using jQuery
    $.ajax({
        url: "categoryShowJsServlet",
        method: "GET",
        data: {categoryId: categoryId},
        success: function (response) {
            //显示悬浮层
            const inform = $("#inform");
            inform.text(response); // Use .text() method to set text content
            inform.css("display", "block"); // Use .css() method to set CSS properties
            console.log("Request successful:", response);
        },
        error: function (xhr, status, error) {
            // Handle error here
            console.error("Error:", status, error);
        }
    });
}


//隐藏悬浮层
function hiddenInform(event) {
    const informDiv = document.getElementById('inform');
    const x = event.clientX;
    const y = event.clientY;
    const divx1 = informDiv.offsetLeft;
    const divy1 = informDiv.offsetTop;
    const divx2 = informDiv.offsetLeft + informDiv.offsetWidth;
    const divy2 = informDiv.offsetTop + informDiv.offsetHeight;
    if (x < divx1 || x > divx2 || y < divy1 || y > divy2) {
        document.getElementById('inform').style.display = 'none';
    }
}