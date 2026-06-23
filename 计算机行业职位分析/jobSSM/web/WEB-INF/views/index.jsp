<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>大数据职位分析</title>
    <script src="${pageContext.request.contextPath}/js/echarts4.3.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/echarts-wordcloud.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js"></script>
</head>
<body>
<div class="container">
    <div class="header">大数据职位分析</div>
    <div class="content">
        <div class="sidebar">
            <a href="javascript:city()">城市职位分布</a>
            <a href="javascript:degree()">职位学历分布</a>
            <a href="javascript:experience()">工作经验分布</a>
            <a href="javascript:avgSalary()">城市平均薪资</a>
            <a href="javascript:salary()">薪资区间分布</a>
            <a href="javascript:jobName()">岗位名称统计</a>
            <a href="javascript:skills()">技能需求统计</a>
            <a href="javascript:welfare()">岗位福利统计</a>
        </div>
        <div class="main" id="main"></div>
    </div>
</div>
</body>
</html>
<style>
    /* 全局重置 */
    * {
        box-sizing: border-box;
    }

    body, html {
        margin: 0;
        padding: 0;
        height: 100%;
        font-family: "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
        background-color: #f0f2f5; /* 整体背景淡灰，减少视觉疲劳 */
        color: #333;
    }

    .container {
        display: flex;
        flex-direction: column;
        height: 100vh;
    }

    /* 头部样式：科技感渐变 */
    .header {
        background: linear-gradient(90deg, #1e3c72 0%, #2a5298 100%); /* 深蓝渐变 */
        color: white;
        padding: 0 30px;
        height: 64px; /* 固定高度 */
        line-height: 64px;
        font-size: 24px;
        font-weight: 600;
        letter-spacing: 1px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15); /* 底部阴影 */
        z-index: 10; /* 保证头部在侧边栏之上（如果有重叠需求） */
    }

    .content {
        display: flex;
        flex: 1;
        overflow: hidden; /* 防止双滚动条 */
    }

    /* 侧边栏样式：深沉商务风 */
    .sidebar {
        background-color: #001529; /*以此致敬 Ant Design 的经典侧边栏色 */
        width: 240px;
        padding-top: 20px;
        display: flex;
        flex-direction: column;
        align-items: center; /* 居中对齐 */
        box-shadow: 2px 0 6px rgba(0,21,41,0.35);
        z-index: 5;
    }

    /* 侧边栏链接/按钮样式 */
    .sidebar a {
        display: block;
        width: 90%; /* 留一点边距 */
        padding: 12px 20px;
        margin-bottom: 8px;
        color: rgba(255, 255, 255, 0.65); /* 文字灰白 */
        text-decoration: none;
        font-size: 15px;
        border-radius: 4px;
        transition: all 0.3s ease; /* 平滑过渡动画 */
        border-left: 3px solid transparent; /* 预留左侧选中条位置 */
    }

    /* 鼠标悬停效果 */
    .sidebar a:hover {
        color: #fff;
        background-color: rgba(255, 255, 255, 0.08); /* 淡淡的白色背景 */
        padding-left: 25px; /* 悬停时稍微右移，增加动感 */
    }

    /* 这里可以预留一个激活类，如果是当前选中的页面 */
    .sidebar a.active {
        background-color: #1890ff;
        color: white;
    }

    /* 主体内容区：卡片式设计 */
    .main {
        flex: 1;
        margin: 20px; /* 给主体留出呼吸空间 */
        padding: 20px;
        background-color: #ffffff;
        border-radius: 8px; /* 圆角 */
        box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08); /* 轻微阴影 */
        position: relative;
        overflow: auto; /* 内容过多时只在卡片内滚动 */
    }
</style>