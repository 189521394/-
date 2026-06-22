# 对话总结 - 2025-06-22

## 项目概况

毕业论文项目：**全国大数据职位分析系统**

### 项目结构
- `论文重构/jobSSM/` — SSM + ECharts 可视化 Web 应用（Spring + Spring MVC + MyBatis）
- `论文重构/2420501025_节熙（职位）（新）.docx` — 论文/结项报告文档（会被 Word 锁定）
- `.claude/workspace/` — Claude 工作空间，存放复制的文档和临时文件

### 技术栈
- **后端**: Spring 5.2.8 + Spring MVC 5.2.8 + MyBatis 3.5.2
- **数据库**: MySQL 8.0.30，连接池 Druid 1.1.20
- **前端**: JSP + ECharts 4.3.0 + jQuery 1.11.3
- **大数据**: Hadoop 3.3.0, MapReduce, Hive, Sqoop（文档中提及，未在当前 jobSSM 代码中）

### 数据分析维度（8个）
1. 城市职位分布 (CityCount)
2. 职位学历分布 (DegreeCount)
3. 工作经验分布 (ExpCount)
4. 城市平均薪资 (SalaryAvg)
5. 薪资区间分布 (Salary)
6. 岗位名称统计 (JobName)
7. 技能需求统计 (SkillCount)
8. 岗位福利统计 (WelfareCount)

### 代码架构
- `org.controller.myController` — 8 个 @RequestMapping 端点对应上述维度
- `org.service.*` — 各维度 Service 层
- `org.mapper.*` — MyBatis Mapper 接口，@Select 注解查询
- `org.bean.*` — POJO：DataBean（name/value），各维度的 CountBean
- `ac.xml` — Spring + MyBatis 整合配置
- `springMVC.xml` — Spring MVC 配置
- `web/WEB-INF/views/index.jsp` — 单页应用，侧边栏 + ECharts 主区域
- `web/js/view.js` — 前端 AJAX + ECharts 渲染逻辑

## 已完成工作
- 读取了论文文档全文，了解了项目背景和章节结构
- 撰写了"选题的目的与意义"（精简版），已交付用户
- 创建了 `.claude/workspace/` 作为临时工作目录
- 复制了论文文档到 workspace 供后续操作

## 注意事项
- Word 文档打开时 officecli 无法读写，需先复制副本再操作
- `动态数据分析/` 目录目前为空
