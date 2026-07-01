<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spark项目可视化</title>
    <script src="${pageContext.request.contextPath}/js/echarts4.3.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/echarts-wordcloud.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js"></script>
</head>
<body>
<div class="container">
    <div class="header">Spark项目可视化</div>
    <div class="content">
        <div class="sidebar">
            <a class="nav-item" href="javascript:top10()" onclick="setActive(this)">十大火爆商品品类</a>
            <a class="nav-item" href="javascript:top3()" onclick="setActive(this)">五十个州火爆商品数据</a>
            <a class="nav-item" href="javascript:jump()" onclick="setActive(this)">网站页面单跳转化率</a>
        </div>
        <div class="main" id="main"></div>
    </div>
</div>
</body>
</html>
<style>
    /* ========== 全局重置 ========== */
    * {
        box-sizing: border-box;
    }

    body, html {
        margin: 0;
        padding: 0;
        height: 100%;
        font-family: "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
        background: #f0f2f5;
        color: #333;
    }

    /* ========== 容器 ========== */
    .container {
        display: flex;
        flex-direction: column;
        height: 100vh;
    }

    /* ========== 头部：渐变 + 玻璃质感 ========== */
    .header {
        background: linear-gradient(135deg, #0f1f3d 0%, #1a3a6b 30%, #1e4d8c 60%, #1a3a6b 100%);
        background-size: 200% 200%;
        animation: headerShimmer 8s ease infinite;
        color: #fff;
        padding: 0 36px;
        height: 64px;
        line-height: 64px;
        font-size: 22px;
        font-weight: 600;
        letter-spacing: 2px;
        box-shadow: 0 2px 12px rgba(0, 20, 50, 0.25);
        z-index: 10;
        position: relative;
        display: flex;
        align-items: center;
    }

    .header::before {
        content: "📊";
        margin-right: 12px;
        font-size: 26px;
        animation: bounceIcon 2s ease-in-out infinite;
    }

    .header::after {
        content: "";
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        height: 3px;
        background: linear-gradient(90deg, #00d4ff, #7b2ff7, #00d4ff);
        background-size: 200% 100%;
        animation: headerShimmer 4s linear infinite;
    }

    @keyframes headerShimmer {
        0%, 100% { background-position: 0% 50%; }
        50% { background-position: 100% 50%; }
    }

    @keyframes bounceIcon {
        0%, 100% { transform: translateY(0); }
        50% { transform: translateY(-4px); }
    }

    /* ========== 主体布局 ========== */
    .content {
        display: flex;
        flex: 1;
        overflow: hidden;
    }

    /* ========== 侧边栏 ========== */
    .sidebar {
        background: linear-gradient(180deg, #001529 0%, #002140 40%, #001a33 100%);
        width: 260px;
        min-width: 260px;
        padding: 24px 0;
        display: flex;
        flex-direction: column;
        align-items: center;
        box-shadow: 2px 0 16px rgba(0, 21, 41, 0.3);
        z-index: 5;
        position: relative;
    }

    .sidebar::before {
        content: "数据导航";
        display: block;
        width: 100%;
        padding: 0 24px 20px;
        color: rgba(255, 255, 255, 0.35);
        font-size: 12px;
        letter-spacing: 3px;
        text-transform: uppercase;
        text-align: center;
        border-bottom: 1px solid rgba(255, 255, 255, 0.08);
        margin-bottom: 8px;
    }

    /* 侧边栏导航项 */
    .sidebar .nav-item {
        display: flex;
        align-items: center;
        width: calc(100% - 20px);
        padding: 13px 18px 13px 20px;
        margin-bottom: 4px;
        color: rgba(255, 255, 255, 0.6);
        text-decoration: none;
        font-size: 14px;
        border-radius: 8px;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border-left: 3px solid transparent;
        position: relative;
        overflow: hidden;
    }

    /* 每个导航项前的图标 */
    .sidebar .nav-item::before {
        margin-right: 10px;
        font-size: 16px;
        width: 22px;
        text-align: center;
        flex-shrink: 0;
        transition: transform 0.3s ease;
    }

    .sidebar .nav-item:nth-child(2)::before { content: "🔥"; }
    .sidebar .nav-item:nth-child(3)::before { content: "🏪"; }
    .sidebar .nav-item:nth-child(4)::before { content: "📈"; }

    /* 悬停效果 */
    .sidebar .nav-item:hover {
        color: #fff;
        background: rgba(24, 144, 255, 0.15);
        padding-left: 28px;
        border-left-color: #1890ff;
        box-shadow: inset 0 0 30px rgba(24, 144, 255, 0.05);
    }

    .sidebar .nav-item:hover::before {
        transform: scale(1.2);
    }

    /* 选中状态 */
    .sidebar .nav-item.active {
        color: #fff;
        background: linear-gradient(90deg, rgba(24, 144, 255, 0.25), rgba(24, 144, 255, 0.08));
        border-left: 3px solid #1890ff;
        font-weight: 500;
        box-shadow: 0 2px 8px rgba(24, 144, 255, 0.15);
    }

    /* 选中项右侧指示点 */
    .sidebar .nav-item.active::after {
        content: "";
        position: absolute;
        right: 12px;
        top: 50%;
        transform: translateY(-50%);
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background: #1890ff;
        box-shadow: 0 0 8px #1890ff;
    }

    /* 侧边栏底部装饰 */
    .sidebar::after {
        content: "";
        margin-top: auto;
        width: 60%;
        height: 1px;
        background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent);
    }

    /* ========== 主内容区（图表容器） ========== */
    .main {
        flex: 1;
        margin: 20px;
        padding: 24px;
        background: #ffffff;
        border-radius: 12px;
        box-shadow:
            0 1px 3px rgba(0, 0, 0, 0.04),
            0 4px 16px rgba(0, 0, 0, 0.06);
        position: relative;
        overflow: hidden;
        transition: box-shadow 0.3s ease;
    }

    .main:hover {
        box-shadow:
            0 2px 6px rgba(0, 0, 0, 0.06),
            0 8px 24px rgba(0, 0, 0, 0.09);
    }

    /* 空状态占位 */
    .main:empty::after {
        content: "👈 请从左侧导航选择一个图表";
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 18px;
        color: #bbb;
        white-space: nowrap;
        pointer-events: none;
    }

    /* ========== 自定义滚动条 ========== */
    ::-webkit-scrollbar {
        width: 6px;
        height: 6px;
    }

    ::-webkit-scrollbar-track {
        background: transparent;
    }

    ::-webkit-scrollbar-thumb {
        background: #c1c1c1;
        border-radius: 3px;
    }

    ::-webkit-scrollbar-thumb:hover {
        background: #a0a0a0;
    }

    /* ========== 响应式适配 ========== */
    @media (max-width: 768px) {
        .sidebar {
            width: 200px;
            min-width: 200px;
        }

        .sidebar .nav-item {
            font-size: 13px;
            padding: 10px 14px;
        }

        .header {
            font-size: 18px;
            padding: 0 20px;
        }

        .main {
            margin: 12px;
            padding: 16px;
        }
    }
</style>