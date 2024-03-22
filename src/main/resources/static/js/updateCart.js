var xmlHttpRequest;

function createXMLHttpRequest() {
    if (window.XMLHttpRequest) //非IE浏览器
    {
        xmlHttpRequest = new XMLHttpRequest();
    } else if (window.ActiveObject)//IE6以上版本的IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Msxml2.XMLHTTP");
    } else //IE6及以下版本IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Microsoft.XMLHTTP");
    }
}

function updateCart(element) {
    //var username = document.NewAccountForm.username.value;
    //var workingItemId = document.getElementById('workingItemId').value;
    //sendRequest("updateCartQuantities?username=" + username);
    const quantity = element.value;
    let itemId = element.id;
    itemId = itemId.substring(0, itemId.length - "-quantity".length);


    sendRequest(`updateCartJSServlet?quantity=${quantity}&itemId=${itemId}`);
    //sendRequest("updateCartQuantities");
}

function sendRequest(url) {
    createXMLHttpRequest();
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.onreadystatechange = processResponse;
    xmlHttpRequest.send(null);
}

function processResponse() {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            const resp = JSON.parse(xmlHttpRequest.responseText);
            const itemId = resp["itemId"];
            const subtotal = document.getElementById("subtotal");
            const totalField = document.getElementById(itemId + "-total");

            totalField.innerText = resp["total"];
            subtotal.innerText = resp["subTotal"];
        }
    }
}
