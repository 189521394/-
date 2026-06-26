# 选择填空

## Spark 与 Hadoop 对比
- **Spark**：**内存计算**，中间结果存内存，迭代快
- **Hadoop MapReduce**：**磁盘计算**，中间结果落 HDFS，迭代慢（反复读写磁盘）
- Spark 不是要取代 Hadoop，而是补充（Spark 不自带存储，通常读 HDFS/HBase/Kafka）

## Spark 基本工作原理（p14）
- 用户提交 Job → **Driver** 解析 DAG 并划分 Stage → **Cluster Manager** 分配资源 → **Executor** 执行 Task → 结果返回 Driver
- 核心：**DAG 调度器**将 RDD 依赖关系划分为 **Stage**（以 Shuffle 为界），每个 Stage 内为 **TaskSet**（pipeline 执行）

## Spark RDD 算子：map / flatMap / reduceByKey / groupByKey

| 算子 | 功能 | 输入→输出 | 是否产生 Shuffle |
|------|------|-----------|:---:|
| **map** | 一对一映射 | 1条 → 1条 | ❌ |
| **flatMap** | 一对多映射再**扁平化** | 1条 → 0~N条 | ❌ |
| **reduceByKey** | 按 key 聚合，**先局部合并再 shuffle** | 同 key 多值 → 1个聚合结果 | ✅（带 Combiner） |
| **groupByKey** | 按 key **分组**（不聚合），全量 shuffle | 同 key 多值 → Iterable | ✅（无 Combiner） |

> 核心区别：**reduceByKey** 有 map-side **预聚合（Combiner）**，数据传输量小；**groupByKey** 全量传输，性能差。能用 reduceByKey 就不用 groupByKey。

## 数据模型：RDD vs DataFrame
- **RDD**（弹性分布式数据集）：**无 Schema**，不包含列名和类型元数据，适用于非结构化数据
- **DataFrame**：**有 Schema**（列名 + 类型），类似关系型数据库的表，带元数据，Catalyst 优化器自动优化
- DataFrame = RDD[Row] + Schema；DataFrame 是 **Dataset[Row]** 的别名

## HBase 是什么数据库
- **列式存储** + **分布式** + **NoSQL**（非关系型）
- 基于 **HDFS**，按**行键（RowKey）** 排序存储
- 表由 **行键 + 列族 + 列限定符 + 时间戳** 四维定位一个单元格

## 实时处理框架：Spark Streaming
- **微批次**处理：按时间间隔（如 5s）切分流数据，每个批次转为 RDD 处理
- 核心数据结构：**DStream（离散流）** = 一系列按时间排列的 RDD 序列
- **无数据也生成空批次**，按时间驱动（区别于 Structured Streaming 按事件驱动）
- DStream 的窗口操作：**窗口长度**必须是**滑动间隔**的**整数倍**

## Structured Streaming
- **流批一体**：流处理和批处理用同一套 DataFrame/Dataset API，引擎自动选择执行模式
- 不同于 Spark Streaming 的微批次，支持 **Continuous Processing**（毫秒级延迟）
- **水位线（Watermark）**：处理迟到数据的阈值，超时数据丢弃，解决状态无限增长问题
- 默认**等待迟到数据**而非空跑

## Kafka
- **消息中间件**：内存中实现进程间数据转移的**中转站**
- 核心概念：**Topic（主题）** = 数据分类/频道，**Partition（分区）** = 并行单位
- **Producer** 生产数据 → Topic → **Consumer** 消费数据
- Kafka 数据写入后**缓存磁盘**（持久化），不是纯内存
- **ZooKeeper**：维护 Kafka 集群元数据（Broker 列表、Topic 配置、分区信息、Controller 选举）

## 机器学习算法

| 算法 | 类型 | 用途 |
|------|------|------|
| **逻辑回归（Logistic Regression）** | **分类**算法 | 二分类（是/否、点击/不点击）、多分类 |
| **线性回归（Linear Regression）** | **预测/回归**算法 | 预测连续数值（房价、销售额） |
| **K-Means** | **无监督聚类**算法 | 客户分群、异常检测 |
| **协同过滤（ALS）** | **推荐**算法 | 评分矩阵分解，物品/用户推荐 |

## 管道（Pipeline）
- 将多个**数据处理步骤串联**起来的工作流
- 包含：**Transformer**（转换器，如 StringIndexer）+ **Estimator**（评估器/模型训练，如 LogisticRegression）
- 一次 `fit()` 完成整条管道训练，一次 `transform()` 完成整条管道预测

## Scala 基础
- `var`：**变量**（可变），`val`：**值/常量**（不可变，推荐）
- `def`：定义**方法/函数**，如 `def add(x: Int, y: Int): Int = x + y`

## 转换算子 vs 行动算子

| 类型 | 行为 | 例子 |
|------|------|------|
| **Transformation**（转换算子） | **惰性执行**，只记录依赖关系，不触发计算 | `map`, `flatMap`, `filter`, `reduceByKey`, `groupByKey` |
| **Action**（行动算子） | **触发计算**，提交 Job，返回结果或写存储 | `count`, `collect`, `foreach`, `saveAsTextFile`, `take`, `first` |

- 核心概念：**惰性求值（Lazy Evaluation）**——转换只构建 DAG，遇到 Action 才真正执行

## Spark 模块功能（p14-15）
- **Spark Core**：RDD、任务调度、内存管理、容错（基础引擎）
- **Spark SQL**：结构化数据处理，DataFrame/Dataset，SQL 查询
- **Spark Streaming**：实时流处理（微批次）
- **MLlib**：机器学习算法库（分类、回归、聚类、推荐）
- **GraphX**：图计算

## Spark 集群部署：YARN 模式
- **YARN-Client**：Driver 在提交节点，适合调试，日志本地可见
- **YARN-Cluster**：Driver 在 ApplicationMaster 中，生产环境首选
- 伪分布式和全分布模式不是考试重点

## Spark Streaming 数据结构
- **DStream（离散流）**：连续数据流的抽象，内部是一系列 **RDD**
- **RDD** 中文全称：**弹性分布式数据集**（Resilient Distributed Dataset）
- RDD 五大属性：分区列表、计算函数、依赖关系、分区器（可选）、首选位置（可选）

## Spark SQL 读取文件
- `spark.read.json("path")` → 读取 **JSON** 文件自动推断 Schema
- `spark.read.csv("path")` → 读取 **CSV** 文件，需 `.option("header","true")` 指定表头
- 文件扩展名和读取方法类型要一致

## HBase 核心概念
- 每行用**行键（RowKey）** 唯一标识
- 行键按**字典序**排序存储
- 列族（Column Family）需预先定义，列限定符（Qualifier）可动态添加

## RDD 依赖：窄依赖 vs 宽依赖

| 依赖类型 | 特征 | 分区关系 | 算子示例 |
|----------|------|----------|----------|
| **窄依赖（Narrow）** | 父 RDD 的每个分区**最多**被 1 个子分区使用 | 1:1 或 N:1 | `map`, `flatMap`, `filter`, `union` |
| **宽依赖（Wide）** | 父 RDD 的每个分区被**多个**子分区使用 | 1:N，需 Shuffle | `reduceByKey`, `groupByKey`, `join`（非 co-partitioned） |

> 窄依赖 = 一个父分区被一个子分区用，不产生 Shuffle；宽依赖 = 数据跨节点重分布，产生 **Shuffle**，划分 Stage 的边界。

## RDD → DataFrame 转换
- 方法：`rdd.toDF("col1", "col2", ...)` 或通过 `SparkSession.createDataFrame(rdd, schema)`
- 需要引入隐式转换：`import spark.implicits._`

## updateStateByKey（Spark Streaming）
- 功能：跨批次**累积历史状态**（如累计词频）
- 必须设置 **Checkpoint 目录**：`ssc.checkpoint("hdfs://path")`
- Checkpoint 用于：**状态持久化** + **故障恢复**

## Kafka 元数据管理
- **ZooKeeper** 管理 Kafka 元数据：Broker 注册、Topic 配置、分区 Leader 选举、Consumer Group 偏移量
- （注：新版 Kafka 逐步将 Consumer 偏移量迁移到内部 Topic `__consumer_offsets`）

## Spark Streaming 处理模型
- 数据流被切分为**离散流 DStream**
- 每个时间窗口的数据 = 一组 RDD

## Structured Streaming 时间概念

| 时间类型 | 含义 |
|----------|------|
| **事件时间（Event Time）** | 数据**产生**的时间（嵌入数据中的时间戳） |
| **处理时间（Processing Time）** | 数据**进入框架被处理**的时间（当前机器时间） |
| **摄入时间（Ingestion Time）** | 数据到达 Spark 的时间 |

## Structured Streaming 输出模式

| 输出模式 | 行为 |
|----------|------|
| **Append（追加）** | 只输出**新增行**（已确认永不再变的数据） |
| **Update（更新）** | 输出**有变化**的行（新增或更新） |
| **Complete（全局）** | 每次输出**整个结果表**（必须为聚合查询） |

## 机器学习：特征处理
- **StringIndexer**：将字符串标签**转换为数字索引**（如 "cat"→0, "dog"→1），是一个 **Transformer（转换器）**

## Spark 任务提交（p99）
- 命令：**`spark-submit`**
- 常用参数：
  - `--class`：主类全限定名
  - `--master`：集群 URL（yarn / local[N]）
  - `--deploy-mode`：client / cluster
  - `--executor-memory` / `--driver-memory`：内存配置

## 推荐算法
- **协同过滤（Collaborative Filtering）**
- 核心算法：**ALS（交替最小二乘法）**，将用户-物品评分矩阵分解为两个低维矩阵

---

# 简答题

## 1. 什么是 RDD？RDD 的五大属性
- **RDD**：**弹性分布式数据集**，Spark 最核心的数据抽象
- **五大属性**：
  1. **分区列表**（Partitions）：数据切分，并行处理
  2. **计算函数**（Compute）：对每个分区的操作
  3. **依赖关系**（Dependencies）：记录与父 RDD 的血缘关系（Lineage）
  4. **分区器**（Partitioner，可选）：Key-Value 数据的 hash/range 分区方式
  5. **首选位置**（Preferred Locations，可选）：数据本地性优化

## 2. 简述 HBase 工作原理（数据存储、读写）
- **存储模型**：表按行键排序，水平切分为 **Region**，一个 Region = 一个行键范围
- **Region 架构**：每个 Region 由 **HRegionServer** 管理，HMaster 负责元数据和负载均衡
- **写入**：先写 **WAL（预写日志）** → 再写 **MemStore（内存）** → 达到阈值 flush 为 **HFile（HDFS 磁盘）**
- **读取**：合并 MemStore + HFile（按时间戳取最新），利用 **BlockCache** 缓存热数据
- **Compaction**：合并小 HFile 为大文件，清理过期/删除数据

## 3. Structured Streaming 三个时间概念及含义
1. **事件时间（Event Time）**：数据本身发生的时刻（数据中的时间戳字段）
2. **处理时间（Processing Time）**：Spark 接收并处理数据的时刻
3. **摄入时间（Ingestion Time）**：数据到达 Spark 引擎的时刻
- **批处理时间（Batch Interval）**：触发处理的间隔
- **窗口时间（Window Duration）**：窗口覆盖的时间范围
- **滑动间隔（Slide Duration）**：窗口移动步长

## 4. Spark SQL 中 DataFrame vs Dataset 的区别
- **DataFrame** = `Dataset[Row]`：弱类型，Row 是通用行对象，编译期不检查字段类型
- **Dataset[T]**：**强类型**（T 为自定义 case class），编译期类型检查，出错在编译阶段发现
- 共性：都是结构化 API，基于 Catalyst 优化器和 Tungsten 执行引擎，都带 Schema
- DataFrame 是**无类型 API**，Dataset 是**类型安全 API**

## 5. Kafka 中生产者、消费者、代理的作用
- **Producer（生产者）**：向指定 Topic 发布消息，负责消息分区策略
- **Consumer（消费者）**：从 Topic 订阅消息（Pull 模式），属于**消费者组（Consumer Group）**，组内并行消费不同分区
- **Broker（代理/节点）**：Kafka 集群中的一个服务器实例，存储消息、处理请求
- 补充：**Topic** 是消息的逻辑分类，每条消息由 `(topic, partition, offset)` 唯一标识

## 6. 监督学习 vs 无监督学习的区别（举例）
| | 监督学习 | 无监督学习 |
|------|----------|------------|
| **数据** | **带标签**的样本 | **无标签**的数据 |
| **目标** | 学习输入→输出的映射 | 发现数据内在结构/模式 |
| **典型算法** | 逻辑回归（分类）、线性回归（预测） | K-Means（聚类）、PCA（降维） |

---

# 读代码题

## 求城市平均气温
- 数据格式：`(城市, 温度)` 
- `rdd.mapValues(x => (x, 1)).reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2)).mapValues(x => x._1 / x._2)`
- 原理：先 map 为 (值, 1)，reduceByKey 汇总后做除法求均值

## 课程、教师、学生、成绩——窗口排序
- 使用 `row_number()` / `rank()` / `dense_rank()` 窗口函数
- `Window.partitionBy("subject").orderBy(desc("score"))`
- 关键点：partitionBy 定义分组，orderBy 定义排序，三种排名对并列值的处理不同

## 房地产：每季度销售房屋数量、总额
- `groupBy(year, quarter).agg(count("house_id"), sum("price"))`
- 或 RDD 方式：`map` 为 `((year, quarter), (1, price))` → `reduceByKey` 聚合

## updateStateByKey：词频累计统计
- 从程序开始运行到当前的**历史总量**
- 必须设置 **Checkpoint**
- 核心函数签名：`updateStateByKey((Seq[V], Option[S]) => Option[S])`
- 维护的状态：键-累计值，每批次用新数据更新历史状态

## Structured Streaming 窗口操作

| 窗口类型 | 特点 | 重复/间隙 |
|----------|------|:---:|
| **滚动窗口（Tumbling）** | 固定大小，首尾相接 | 无重叠无间隙 |
| **滑动窗口（Sliding）** | 固定大小，按滑动步长移动 | 有重叠 |
| **会话窗口（Session）** | 按活动间隔动态划分 | 动态，无重叠 |

> 代码中通过 `window(时间列, "窗口长度", "滑动间隔")` 定义

---

## 机器学习部分读代码

### 朴素贝叶斯算法模型评估
- 常用指标：
  - **准确率（Accuracy）**：`(TP + TN) / 总数`
  - **精确率（Precision）**：`TP / (TP + FP)`
  - **召回率（Recall）**：`TP / (TP + FN)`
  - **F1 值**：精确率和召回率的调和平均
- `MulticlassClassificationEvaluator`：通过 `setMetricName("accuracy"/"f1"/"precision"/"recall")` 计算

### 线性回归模型评估
- 常用指标：
  - **RMSE（均方根误差）**：`√(Σ(预测值 - 真实值)² / n)`，越小越好
  - **MSE（均方误差）**：RMSE 的平方
  - **MAE（平均绝对误差）**：`Σ|预测值 - 真实值| / n`
  - **R²（决定系数）**：越接近 1 拟合越好
- `RegressionEvaluator`：通过 `setMetricName("rmse"/"mse"/"mae"/"r2")` 计算
- **VectorAssembler** 将多个特征列合并为向量列 `features`
- 评估代码一般为：`evaluator.evaluate(predictions)`，小值（RMSE）或大值（R²）代表模型好

---

# 命令操作

## Kafka 集群命令

### 创建主题
```bash
kafka-topics.sh --create --bootstrap-server <broker:9092> \
  --topic <主题名> --partitions <分区数> --replication-factor <副本数>
```
- 示例：`kafka-topics.sh --create --bootstrap-server hadoop1:9092 --topic test --partitions 3 --replication-factor 2`

### 发送数据（生产者）
```bash
kafka-console-producer.sh --bootstrap-server <broker:9092> --topic <主题名>
```

### 消费数据（消费者）
```bash
kafka-console-consumer.sh --bootstrap-server <broker:9092> --topic <主题名> --from-beginning
```

## HBase Shell 命令

### 建表
```bash
create '<表名>', '<列族1>', '<列族2>'
```
- 示例：`create 'student', 'info', 'score'`
- 至少指定一个列族

### 插入/查询数据
```bash
put '<表名>', '<行键>', '<列族:列名>', '<值>'
get '<表名>', '<行键>'
scan '<表名>'
```

---

# 实操考试

## Spark Streaming Test 项目（Menu 菜单项目）
- 功能：展示 Spark 各模块基础操作
- 依赖项：`spark-streaming`、`spark-core`、`spark-sql`
- 菜单结构：选择对应编号执行不同 Spark 演示功能

## 数据生成与数据分析
- **生成模拟数据**：代码可参考/复用（抄），以 `spark.sql` 或 `ssc.socketTextStream` 接收
- **数据分析**：需**自己写**，重点：
  - `spark.read.json/csv` 读取数据
  - 用 `groupBy`、`agg`、`join`、窗口函数等完成分析
  - `df.write.csv/json` 输出结果
