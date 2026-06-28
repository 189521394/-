# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

**语言要求：所有回复请使用中文，代码和英文专有名词（如类名、方法名、配置项）除外。**

## 仓库概览

这是一个毕业论文仓库，包含**两个独立的 Maven Java Web 项目**，分别实现不同的数据分析与可视化子系统。两者均基于 Java 8，部署在 Tomcat 9 上。

```
毕业论文/
├── 动态数据分析/          # 子项目1：电商实时数据分析系统 ✅ 论文已完成
│   ├── Redis/              #   Maven 项目 (artifactId: Redis)
│   ├── 论文大纲.md          #   论文大纲
│   └── 系统架构图.png       #   系统架构图
└── 计算机行业职位分析/      # 子项目2：全国大数据职位分析系统 🔥 论文撰写中
    ├── jobSSM/             #   Maven 项目 (artifactId: jobSSM)
    ├── 论文大纲.md          #   论文大纲（已重构，加入数据清洗章节）
    ├── 新旧项目对比分析.md   #   新旧项目对比分析（老项目 → jobSSM）
    ├── 大数据技术框架.png    #   大数据技术架构图
    ├── 职位分析-老框架-老技术.docx  # 老论文（含完整的数据清洗+Hive+Sqoop流程）
    ├── 职位分析（老）.doc    #   老论文 .doc 格式副本
    └── 计算机行业职位分析.doc #  老论文另一副本
```

---

## 当前工作重点

**子项目1（动态数据分析）论文已定稿，无需再修改。**

**子项目2（计算机行业职位分析）论文正在撰写。** 论文结构是对老论文的"技术升级+内容整合"：

- **保留**老论文中完整的数据清洗流水线（MapReduce → Hive → Sqoop → MySQL）
- **升级**老论文中落后的 Web 层（原生 Servlet/手动 JDBC/手动拼 JSON）为 jobSSM 中的 SSM 三层架构
- **去掉**系统分析、系统测试等老论文也没有的填充章节

### 论文大纲（新）

参见 `计算机行业职位分析/论文大纲.md`，结构为：

1. 第一章 绪论
2. 第二章 相关技术介绍（Hadoop/Hive/Sqoop + SSM + ECharts 双线技术栈）
3. **第三章 数据采集与预处理**（核心新增章 — 来自老论文 §2+§3 的全部流程）
4. 第四章 系统设计（简化：架构 + 模块 + 数据库）
5. 第五章 系统实现（jobSSM 源码：配置 → Mapper → Service → Controller → 前端 8 图表）
6. 第六章 总结与展望

### 老论文与 jobSSM 的关系

老论文（`职位分析-老框架-老技术.docx`）提供了完整的**数据处理侧**内容：
- §2 数据预处理：JobClean / WholeFileRecordReader / WholeFileInputFormat / JobMapper / JobDriver 全部源码
- §3 数据分析：Hive 建库建表去重 / 6 维度 HQL 分析 / 薪资 3 步处理 / Sqoop 导出

jobSSM 提供了**Web 工程化侧**内容（替代老论文 §4 的简陋实现）：
- Druid 连接池替代手动 JDBC
- MyBatis 注解式 Mapper 替代手动 DAO + ResultSet
- Spring MVC @ResponseBody + Jackson 替代手动拼 JSON 字符串
- 8 维度 ECharts（饼图/柱状图/词云）替代简单展示

---

## 子项目1：动态数据分析（电商实时数据分析）✅

**技术栈：** Spark Streaming 3.3.0 + Kafka 3.2.1 + Redis (Jedis 2.9.0) + Spring MVC 5.2.8 + WebSocket + ECharts + jQuery

**五层流水线架构：**
1. **数据生成层** — `createData.scala`：每秒生成一条模拟订单（10 个商品品类），以 JSON 格式发送到 Kafka topic `product_order`
2. **消息传输层** — Kafka 集群（3 个 Broker：hadoop1/2/3:9092）
3. **流式计算层** — `processData.scala`：Spark Streaming（5 秒微批次）以 Direct 模式消费 Kafka 数据，按品类分组聚合销售额，写入 Redis
4. **数据存储层** — Redis Hash（`orderTotal`：field=品类名，value=累计销售额），使用 `hincrByFloat` 原子累加
5. **Web 展示层** — Spring MVC + WebSocket（每 2 秒推送） + ECharts 水平柱状图

**核心源文件：**

| 文件 | 作用 |
|------|------|
| `Redis/src/main/scala/r/createData.scala` | Kafka Producer：生成模拟订单，发送到 `product_order` 主题 |
| `Redis/src/main/scala/r/processData.scala` | Spark Streaming：消费 Kafka、聚合销售额、写入 Redis |
| `Redis/src/main/scala/r/redisUtil.scala` | Scala 端 JedisPool 单例（从 `redis.properties` 加载配置） |
| `Redis/src/main/java/org/example/dao/redisClient.java` | Java 端 JedisPool（静态初始化块，从 `redis.properties` 加载配置） |
| `Redis/src/main/java/org/example/service/getData.java` | 读取 Redis `orderTotal` hash，序列化为 JSON 格式 `[["品类1",...], ["100",...]]` |
| `Redis/src/main/java/org/example/webSocket/UIwebSocket.java` | `@ServerEndpoint("/uiWebSocket")`：每 2 秒向已连接客户端推送 Redis 数据 |
| `Redis/src/main/java/org/example/controller/cc.java` | Spring MVC `@Controller`：路由 `/` → `index.jsp` |
| `Redis/web/WEB-INF/views/index.jsp` | 前端页面：WebSocket 客户端 + ECharts 柱状图 |

**配置文件：**
- `src/main/resources/springMVC.xml` — Controller 扫描（`org.example.controller`）、视图解析器、静态资源映射
- `src/main/resources/redis.properties` — Redis 主机/端口/连接池参数
- `web/WEB-INF/web.xml` — 仅配置 DispatcherServlet（无 ContextLoaderListener，该项目使用单一 Spring MVC 上下文）

**构建：** 标准 Maven（`mvn clean package`）。Scala 源码在 `src/main/scala/`，Java 源码在 `src/main/java/`。

**启动顺序：** 启动 Kafka → 启动 Redis → 运行 `processData`（Spark Streaming）→ 运行 `createData`（数据生成器）→ 部署 WAR 到 Tomcat → 打开浏览器。

---

## 子项目2：计算机行业职位分析（全国大数据职位分析系统）🔥

**技术栈：** Spring 5.2.8 + Spring MVC 5.2.8 + MyBatis 3.5.2 + Druid 1.1.20 + MySQL 8.0 + Jackson 2.13.4 + ECharts 4.3.0 + jQuery 1.11.3

### 论文数据流水线（老论文提供）

```
原始招聘JSON数据
  → MapReduce 清洗（JobClean/JobMapper/JobDriver — 老论文 §2）
  → Hive 数据分析（6 维度 HQL + 薪资处理 — 老论文 §3）
  → Sqoop 导出（→ MySQL 8 张分析结果表 — 老论文 §3.3.2）
```

### jobSSM 三层架构（替代老论文的简陋 Web 层）

```
Controller (myController.java, @Controller, 9 个路由)
  → Service (@Service, 8 个 Service, @Autowired 注入 Mapper)
    → Mapper (@Mapper 接口 + @Select 注解, 8 个 Mapper)
      → MySQL (job 数据库, 8 张分析结果表)
```

**两级 Spring 上下文（父子容器）：**
- **Root WebApplicationContext**（`ac.xml`）：管理 DataSource（Druid）、SqlSessionFactory、Service 扫描（`org.service`）、Mapper 扫描（`org.mapper`）
- **Servlet WebApplicationContext**（`springMVC.xml`）：管理 Controller 扫描（`org.controller`）、注解驱动 MVC、静态资源映射

**8 个数据接口**（均通过 `@ResponseBody` + Jackson 返回 JSON）：

| URL | 图表类型 | 返回类型 | 数据库表 |
|-----|---------|---------|---------|
| `/city` | 饼图 | `DataBean[]` | `city_count` |
| `/degree` | 饼图 | `DataBean[]` | `degree_count` |
| `/experience` | 饼图 | `DataBean[]` | `exp_count` |
| `/avgSalary` | 柱状图 | `Object[]`（两个数组） | `salary_avg` |
| `/salary` | 柱状图 | `Object[]`（两个数组） | `salary_count` |
| `/jobName` | 词云图 | `DataBean[]` | `jobname_count` |
| `/skills` | 词云图 | `DataBean[]` | `skill_count` |
| `/welfare` | 词云图 | `DataBean[]` | `welfare_count` |

**包结构：**
```
org.bean/       — 9 个 POJO（CityCountBean, DataBean, DegreeCountBean, ExpCountBean,
                  JobNameBean, SalaryAvgBean, SalaryBean, SkillCountBean, WelfareCountBean）
org.controller/ — myController（单一 Controller，包含所有路由）
org.service/    — 8 个 Service 类
org.mapper/     — 8 个 MyBatis Mapper 接口（使用 @Select 注解）
```

**关键配置文件：**
- `src/main/resources/ac.xml` — 根上下文：Druid 数据源、SqlSessionFactory、MapperScannerConfigurer、Service 组件扫描
- `src/main/resources/springMVC.xml` — Servlet 上下文：Controller 扫描、`<mvc:annotation-driven>`、静态资源映射
- `src/main/resources/jdbc.properties` — 数据库连接信息（MySQL `job` 库）
- `web/WEB-INF/web.xml` — ContextLoaderListener（加载 `ac.xml`）+ DispatcherServlet（加载 `springMVC.xml`）

**常见坑点（详见 `项目问题修复总结.md`）：**
- 包名必须一致：`ac.xml` 扫描的是 `org.service` / `org.mapper`（不是 `org.example.*`）
- Bean 类必须声明 `package org.bean;`（不是 `package bean;`）
- 数据库表名：`exp_count`（非 `experience_count`）、`skill_count`（非 `skills_count`）、`jobname_count`（非 `jobName_count`）
- `salary_count` 表按 `min_salary ASC` 排序（不是 `sort_key`）
- 切换图表时务必先 `echarts.dispose(dom)` 再 `echarts.init(dom)`，否则报 "instance already initialized"
- jQuery AJAX 会自动反序列化 `@ResponseBody` 返回的 JSON，success 回调中**不要**再调用 `JSON.parse()`
- JS 资源路径用 `${pageContext.request.contextPath}/js/xxx`（绝对路径）

**构建：** 在 `jobSSM/` 目录下执行 `mvn clean package`，部署 WAR 到 Tomcat 9。需要 MySQL `job` 数据库并预置 8 张分析结果表。

---

## Git 约定

- 默认分支：`main`
- `.doc` / `.docx` 论文文件直接跟踪（注意 git status 中的重命名记录）
- `.idea/` 目录（IntelliJ IDEA 项目配置）已提交
