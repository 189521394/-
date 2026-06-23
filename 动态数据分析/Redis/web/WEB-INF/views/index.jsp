<%--
  Created by IntelliJ IDEA.
  User: WinLx
  Date: 2026/6/9
  Time: 09:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<body>
    <h1>商品销售额数据汇总</h1>
    <div id="main" style="height: 50%;width: 100%"></div>
    <script>
        let myCharts = echarts.init($("#main")[0]);
        myCharts.setOption({
            title: {
                text: "商品销售额汇总"
            },
            tooltip: {
                trigger: 'axis',
                axisPoint: {
                    type: 'shadow'
                }
            },
            legend: {
                data: ["2026年6月9日"]
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: "category",
                data: []
            },
            series: [{
                name: "2026年6月9日",
                type: "bar",
                data: []
            }]
        })
        var webSocket = null;
        if ("WebSocket" in window) {
            webSocket = new WebSocket("ws://127.0.0.1:8080/Redis_Web_exploded/uiWebSocket");
            webSocket.onopen = function(ev) {

            }
            webSocket.onmessage = function(ev) {
                var info = ev.data;
                let jsonArray = JSON.parse(info);
                myCharts.setOption({
                    yAxis: {
                        data: jsonArray[0]
                    },
                    series: [{
                        data: jsonArray[1]
                    }]
                })
            }
            webSocket.onclose = function(ev) {

            }
            webSocket.onerror = function(ev) {

            }
        } else {
            alert("浏览器版本过低")
        }
    </script>
</body>
</html>
