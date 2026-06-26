# 选择填空

Spark和Hadoop在处理上的区别

spark在内存中计算，hadoop在磁盘（HDFS）上计算



spark基本工作原理-p14（待补充）



spark rdd 和 spark sql

- shuffle：把数据发送到另一个节点

map，一对一映射，不进行shuffle

flatmap，一对多映射再扁平化，不进行shuffle

reducebykey，聚合——>shuffle——>聚合

groupbykey，shuffle——>分组

> reduce先计算再搬运，group先搬运再计算

处理完出来的结果是啥

和功能，区别



rdd（弹性分布式数据集）：无Schema，不包含列名和元数据，适用于非结构化数据

dataframe：有Schema（列名+类型），类似关系型数据库的表，带元数据

核心区别，数据表元数据，rdd没有数据表



hbase是什么样的数据库

列式存储，分布式，NoSQL（非关系型数据库）

基于HDFS，按行键排序存储



实时处理框架，spark streaming

没有数据也会处理，按时间

dstream（离散流），包含rdd，多少，窗口时间倍数



struck streaming，流批一致，处理等待数据，水位线



kafka，内存中实现进程间数据转移的中转站

数据处理空间，主题

本身的数据存入后会缓存磁盘



机器学习，分类算法

逻辑回归（选择题，分类算法）

线性回归（预测算法）



管道，多个数据处理串联起来的工作流



scala，定义变量和方法，var变量，val常量，函数def



行动算子，转换算子

count，foeach，print

数据状态转换，未执行



14/15p模块是干啥的



spark集群yarn模式，

伪分布和全分布没用



spark streaming，数据结构，rdd，中文全称 弹性分布式数据集

spark sql，接收文件，读取文件，json，csv文件是csv，名字和类型一样



hbase列数据库，每行唯一标识，行键



spark rdd，算子，转换，行动

转换中，窄依赖宽依赖，数据集走到下一级，有几个集群机器参与



spark sql，rdd转df，转换方法，todf



spark streaming upstatebykey，历史数据统计，检测点目录，保存历史数据

数据恢复容错，checkpoint



kafka，维护元数据，zookeeper，



spark streaming，处理数据流，分成离散流dstream



ssming处理时间，数据进到框架被处理的时间



ssming，输出模式，三个，追加append，更新update，accomplate全局



机器学习，字符串标签转数字索引，转换器，



spark任务提交，submit，spark -submit，99p



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



线性向量积模型评估，代码，解释怎么评估的

# 命令操作

kafka集群怎么创建主题



kafka集群怎么发射数据



hbase怎么建立表

# 实操考试

sparksming test项目，menu菜单项目

spark依赖，sming，core，sql

生成模拟数据的代码抄，数据分析自己写