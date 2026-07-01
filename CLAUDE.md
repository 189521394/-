# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

**语言要求：所有回复请使用中文，代码和英文专有名词（如类名、方法名、配置项）除外。**

## 仓库概览

这是一个毕业论文仓库，包含**三个独立的 Maven 项目**，分别实现不同的数据分析与可视化子系统。均基于 Java 8。

```
毕业论文/
├── 动态数据分析/               # 论文1：电商实时数据分析系统 ✅ 已完成
│   ├── Redis/                  #   Maven 项目 (artifactId: Redis)
│   ├── 论文大纲.md
│   └── 系统架构图.png
├── 计算机行业职位分析/          # 论文2：全国大数据职位分析系统 ✅ 已完成
│   ├── jobSSM/                 #   Maven 项目 (artifactId: jobSSM)
│   ├── 论文大纲.md
│   ├── 新旧项目对比分析.md
│   ├── 大数据技术框架.png
│   ├── 职位分析-老框架-老技术.docx
│   └── 计算机行业职位分析.doc
└── Amazon电商平台数据分析/      # 论文3：电商平台数据分析系统 🔥 论文撰写中
    ├── session/                #   Maven 项目 (artifactId: session) — Spark SQL 离线分析
    ├── sparkView/              #   Maven 项目 (artifactId: sparkView) — Spring MVC Web 可视化
    ├── 论文大纲.md              #   大纲已定稿，摘要+第一章已写
    └── 电商平台数据分析.doc     #   论文 .doc 文件
```

---

## 当前工作重点

**论文1（动态数据分析）已完成，无需再修改。**

**论文2（计算机行业职位分析）已完成。**

**论文3（Amazon电商平台数据分析）正在撰写 —— 当前唯一在进行的论文。** 大纲已定稿，摘要和第一章已完成，正在进行后续章节。

---

## 论文1：动态数据分析（电商实时数据分析）✅

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

## 论文2：计算机行业职位分析（全国大数据职位分析系统）✅

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

## 论文3：Amazon电商平台数据分析 🔥

**技术栈：** Spark SQL 3.3.0 + HBase 2.4.9 + Spring MVC 5.2.8 + Jackson 2.13.4 + ECharts 4.3.0 + jQuery 1.11.3

**两个独立子项目，形成"本地分析 → 集群存储 → Web 展示"链路：**

### 子项目 A：session（Spark SQL 离线分析，写入 HBase）

Maven 项目（artifactId: session），Scala 2.12 + Java 8。读取本地 JSON 数据，Spark SQL 分析后写入 Linux 集群 HBase。

```
本地 JSON 文件 → Spark SQL DataFrame 分析 → 写入 HBase（Linux 集群 hadoop1/2/3）
```

| 文件 | 作用 |
|------|------|
| `session/src/main/scala/example/Amazon.scala` | 按 category_id 统计 view/cart/purchase 三种行为，取 Top10 品类，写入 HBase `categoryCount` 表 |
| `session/src/main/scala/example/AmazonOrder.scala` | 按 address_name 用窗口函数 row_number() 取每地区 Top3 热品，筛选 3 个特定商品（1005115/1004856/1004767），写入 HBase `top3` 表 |
| `session/src/main/scala/example/Conversion.scala` | 按 userid 用 lead() 窗口函数计算页面跳转路径及转化率，写入 HBase `conversion` 表 |
| `session/src/main/scala/example/HBaseUtil.scala` | HBase 工具类：ZooKeeper 连接（hadoop1/2/3:2181）、建表（列族 info）、批量写入、查询。定义 Info / Product / conBean 三个 case class |

依赖：spark-core_2.12 3.3.0、spark-sql_2.12 3.3.0、hbase-client 2.4.9、hbase-common 2.4.9。

### 子项目 B：sparkView（Spring MVC Web 可视化，读取 HBase）

Maven Web 项目（artifactId: sparkView），Java 8，部署于 Tomcat 9。从 HBase 读取分析结果，ECharts 柱状图展示。

```
Spring MVC Controller → Service → DAO → HBase Scan → JSON → ECharts 柱状图
```

**两级 Spring 上下文（父子容器）：**
- **Root WebApplicationContext**（`ac.xml`）：扫描 `org.service`、`org.DAO`、`org.cn`
- **Servlet WebApplicationContext**（`springMVC.xml`）：扫描 `org.controller`、注解驱动 MVC、静态资源映射 `/js/**`

**3 个数据接口**（@ResponseBody + Jackson 自动序列化）：

| URL | 图表类型 | HBase 表 | 返回数据 |
|-----|---------|---------|---------|
| `/top10` | 分组柱状图 | `categoryCount` | `[id[], view[], cart[], purchase[]]` |
| `/top3` | 分组柱状图 | `top3` | `[name[], pro4767[], pro4856[], pro5115[]]` |
| `/jump` | 柱状图 | `conversion` | `[jump[], conversion[]]` |

**包结构：**
```
org.bean/       — 3 个 POJO（Top10Bean, Top3Bean, conversionBean）
org.controller/ — myController（单一 Controller，路由 /、/top10、/top3、/jump）
org.service/    — service（@Service，注入 DAO，数据格式转换为 Object[]）
org.DAO/        — DAO（@Repository，HBase Scan 全表扫描）
org.cn/         — Connect（@Component，HBase 连接管理）
```

**关键配置文件：**
- `src/main/resources/ac.xml` — Root 上下文：扫描 org.service / org.DAO / org.cn
- `src/main/resources/springMVC.xml` — Servlet 上下文：扫描 org.controller、`<mvc:annotation-driven>`、静态资源映射
- `web/WEB-INF/web.xml` — ContextLoaderListener（ac.xml）+ DispatcherServlet（springMVC.xml）

依赖：spring-* 5.2.8、hbase-client 2.4.9、hbase-common 2.4.9、jackson-* 2.13.4。

构建：在 `sparkView/` 目录下执行 `mvn clean package`，部署 WAR 到 Tomcat 9。需要 Linux 集群 HBase 正常运行且已预置三张分析结果表。

### 论文大纲

参见 `Amazon电商平台数据分析/论文大纲.md`，5 章结构：

1. 第一章 绪论
2. 第二章 相关技术介绍（Scala + Spark SQL + HBase + Spring MVC + ECharts + Maven）
3. **第三章 数据分析与处理**（session 项目：集群环境 → 数据源 → 3 个分析程序 → HBaseUtil）
4. **第四章 Web 可视化系统设计与实现**（sparkView 项目：架构 → 配置 → DAO → Service → Controller → 前端 3 图表）
5. 第五章 结束语

### 与论文1、论文2的关键区别

- **论文1**：实时流处理（Spark Streaming + Kafka + Redis + WebSocket），5 层流水线
- **论文2**：离线批处理全流程（MapReduce 清洗 → Hive 分析 → Sqoop 导出 → MySQL → SSM + ECharts），数据预处理是核心章节
- **论文3（本文）**：离线分析 + 直接 HBase 读写（Spark SQL → HBase ← Spring MVC），两个独立项目各占一章，无中间件（无 Kafka/Redis/Sqoop/MyBatis），结构最精简

---

## Git 约定

- 默认分支：`main`
- `.doc` / `.docx` 论文文件直接跟踪（注意 git status 中的重命名记录）
- `.idea/` 目录（IntelliJ IDEA 项目配置）已提交
