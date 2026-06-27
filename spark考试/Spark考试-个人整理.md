# 选择填空

### Spark和Hadoop对比

spark在内存中计算，hadoop在磁盘（HDFS）上计算



### spark基本工作原理-p14（待补充）



### spark rdd 算子对比

- shuffle：把数据发送到另一个节点



map，一对一映射，不进行shuffle

flatmap，一对多映射再扁平化，不进行shuffle

reducebykey，同一个key的多个值，聚合——shuffle——聚合

groupbykey，同一个key的多个值，shuffle——分组



> reduce先计算再搬运，group先搬运再计算



### rdd 和 dataframe的区别

rdd（弹性分布式数据集）：无Schema，不包含列名和元数据，适用于非结构化数据

dataframe：有Schema（列名+类型），类似关系型数据库的表，带元数据



### hbase是什么样的数据库

列式存储，分布式，NoSQL（非关系型数据库）

基于HDFS，按行键排序存储



### 实时处理框架：spark streaming

按时间间隔切分数据，每个批次转换为rdd处理

无数据也生成批次，按时间驱动（区别于structured streaming按事件驱动）

窗口长度必须是滑动间隔的整数倍



### structured streaming

流批一体：structured streaming使用同一套api同时处理流式数据（实时数据）和批式数据（离线数据）

水位线：等待迟到数据的时间值，超时则丢弃数据

> 如，一个窗口时间（10秒中），数据因为延迟，要等到第12秒才可以到达，水位线就是决定，等多久



### kafka

内存中实现进程间数据转移的中转站

- topic：持久化的，只能追加的数据流
- partition：对topic的数据流进行分流，实现并行处理

producer生产数据——topic——consumer消费数据

数据生产——进入内存/同步写入（追加）磁盘——消费者读取

zookeeper：维护kafka集群元数据



### 机器学习，分类算法

逻辑回归，分类算法，二分，多分

线性回归，预测/回归算法，预测连续数值（销售额）



### 管道（机器学习）

将多个数据处理步骤串联起来的工作流

> 适用的工作流：格式化数据——训练模型——训练完打分



### scala基础

var：变量

val：常量

def：方法/函数



### 转换算子和行动算子

**转换算子**只绘制蓝图，不会真正开始计算，只是一个**计划**

当运行到**行动算子**的时候，前面所有的**转换算子**都会开始计算

```scala
// 这些全部是转换算子，一个都没执行！
rdd.map(...)
 .filter(...)
 .reduceByKey(...)   // ← 转换
 .sortBy(...)        // ← 转换
 .mapValues(...)     // ← 转换

// 只有最后加上这个，前面才会跑起来
 .collect()          // ← 这才是行动算子
```

> 返回rdd的就是转换算子，返回数组/写到文件的就是行动算子



14/15p模块是干啥的



### spark集群部署-YARN模式

> driver是总指挥，指挥集群里面的机器干活

- client：driver在自己电脑上，提交完任务电脑不能关，电脑断电集群无法工作，此模式下自己的电脑上可以看到任务日志
- cluster：driver在集群里面，提交完任务电脑可以关机，集群自己继续工作，此模式下日志在集群上，看日志不方便



### spark streaming 数据结构

> 数据流按批次切分，每一批就是一个dstream，一个dstream包含若干个rdd

- dstream（离散流）
- rdd（弹性分布式数据集）



### spark sql 文件操作

```scala
spark.read.json()	// 读取json文件
spark.read.csv()	// 读取csv文件
```

文件扩展名和读取方法名一致



### hbase核心概念

> hbase是个分布式NoSQL数据库，每一行由行键唯一标识，行键自动排序，同时决定数据存在于哪个机器，所以时序行键会导致热点问题，解决办法就是使用随机行键
>
> 列族是物理存储策略的分组，一般分冷数据和热数据，一种数据一个列族，建表时定好，一般不超过三个
>
> 列限定符就是字段，动态自由添加

每一行用行键作为唯一标识

行键排序存储



### RDD依赖：窄依赖和宽依赖

> spark中，对数据的任何操作，都不是直接修改，而是生成一个新的RDD
>
> 所以，父rdd就是变换前的rdd，子rdd就是变换后的
>
> 同时，rdd是逻辑关系，分区是物理位置，一个rdd有多个分区，分区可能不在同一台机器上
>
> 
>
> 有些操作和其他分区的数据无关，比如大小写转换，就是窄依赖
>
> 有些操作要看其他分区的数据，比如sortbykey，排序看全局，当前分区不包括所有数据，所以要看其他分区的数据，就是宽依赖

- 窄依赖：每条数据独立处理
- 宽依赖：需要把所有分区的数据凑到一起才可以操作



### RDD → DataFrame 转换

toDF方法

```scala
import spark.implicits._	// 需要提前引入隐式转换
rdd.toDF()
```





spark streaming upstatebykey，历史数据统计，检测点目录，保存历史数据

数据恢复容错，checkpoint



kafka，维护元数据，zookeeper，



spark streaming，处理数据流，分成离散流dstream



ssming处理时间，数据进到框架被处理的时间



ssming，输出模式，三个，追加append，更新update，accomplate全局



机器学习，字符串标签转数字索引，转换器，



spark任务提交，submit，spark-submit，99p



机器学习，推荐，协同过滤，als矩阵算法

# 简答题

什么是rdd，ljurdd的属性



简述hbase工作原理，数据存储，读写



ssming三个时间，批处理，窗口，huad，含义



spark sql，dataframe dataset区别



kafka中，生产者，消费者，代理，作用



机器学习，监督/无监督学习的区别，举例子，带标签的数据，没标签的数据

kmeans无监督，逻辑回归有监督

# 读代码

求城市平均气温例子代码



课程，教师，学生，成绩，学生科目排序，窗口排序



房地产，每个季度销售房屋数量，总额



upstatebykey，统计数据开始到现在，词频统计总量，历史数据统计



stcustming，窗口操作，滚动，滑动，会话窗口，解释这是什么窗口



### 机器学习部分读代码

朴素贝叶斯算法模型评估，怎么评估



线性回归模型评估，代码，解释怎么评估的

# 命令操作

kafka集群怎么创建主题



kafka集群怎么发射数据



hbase怎么建立表

# 实操考试

sparksming test项目，menu菜单项目

spark依赖，sming，core，sql

生成模拟数据的代码抄，数据分析自己写