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

function usernameIsExist() {
    //var username = document.NewAccountForm.username.value;
    const username = document.getElementById('username').value;
    sendRequest("usernameIsExistServlet?username=" + username);
}

function passwordIsSame() {
    const password = document.getElementById("password").value;
    const passwordRepeat = document.getElementById("repeat-password").value;
    const msgField = document.getElementById("repeat-password-msg");
    if (passwordRepeat === "") {
        return;
    }
    if (passwordRepeat !== password) {
        msgField.innerHTML = `<span style="color: red; ">密码不一致</span>`;
    } else {
        msgField.innerHTML = `<span style="color: green; ">✔</span>`;
    }
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
            const responseInfo = xmlHttpRequest.responseXML.getElementsByTagName("msg")[0].firstChild.data;

            const div1 = document.getElementById('usernameMsg');

            if (responseInfo === "Exist") {
                div1.innerHTML = `<span style="color: red;">用户名已存在</span>`;
            } else {
                div1.innerHTML = '<span style="color: green;">用户名可用</span>';
            }
        }
    }
}
