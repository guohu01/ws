<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>websocket</title>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
    <script type="text/javascript">
        var websocket = null;
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://192.168.3.150:8081/websocket/server");
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://192.168.3.150:8081/websocket/server");
        } else {
            websocket = new SockJS("http://192.168.3.150:8081/sockjs/server");
        }
        websocket.onopen = onOpen;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        websocket.onclose = onClose;

        function onOpen(event) {
            alert(event.type);
        }

        function onMessage(messageEvent) {
            alert(messageEvent.data);
        }

        function onError(event) {
        }

        function onClose(closeEvent) {
            alert(closeEvent.reason);
        }

        function doSendUser() {
            if (websocket.readyState === websocket.OPEN) {
                var msg = document.getElementById("inputMsg").value;
                websocket.send(msg);//发送消息
                alert("发送成功!");
            } else {
                alert("连接失败!");
            }
        }

        window.close = function () {
            websocket.onclose();
        };
        function websocketClose() {
            websocket.close();
            alert("连接关闭");
        }
    </script>
</head>
<body>

请输入：<input id="inputMsg" name="inputMsg"/>
<button onclick="doSendUser();">发送</button>
<button onclick="websocketClose();">关闭连接</button>
</body>
</html>