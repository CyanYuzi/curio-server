# Curio

> 一个 Agent 读得懂的学习资料库 —— 服务于个人学习、团队协作与课堂。

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![Status](https://img.shields.io/badge/status-M1%20in%20progress-yellow.svg)

<!-- ↑ 徽章里的 Java / Spring Boot 版本，等你把骨架建好后按实际改 -->

---

## 这是什么

<!-- ⚠️ 下面是我起的草稿。请用你自己的话重写一遍，然后删掉这段注释。
     写的时候回答三个问题：解决什么问题？谁会用？和别人有什么不一样？ -->

Curio 是一个学习资料库服务。

个人可以在这里归档学习资料，团队可以共享资料并分配任务，课堂可以把资料和产出集中在一处。
**库里的 Agent 读得懂你存进去的东西。** 它不只是把文件存起来，而是能回答问题、能 review 你的产出。

基于个人学习存在文件繁多，文件与任务不统一。团队中文件不统一，没有明确的任务交付流程而编写的基于agent的多端知识库服务。

---

## 技术栈

| 层       | 选型                           |
|----------|--------------------------------|
| 语言     | Java 21                        |
| 框架     | Spring Boot 4.x                |
| 持久层   | MyBatis                        |
| 数据库   | MySQL 8                        |
| 接口文档 | springdoc-openapi (Swagger UI) |
| 缓存     | Redis（M3 引入）               |
| AI       | Spring AI（M2 引入）           |

---

## 项目结构

```text
curio-server/
├── src/main/java/com/cyan/curio-server/
│   ├── controller/       # 接口层
│   ├── service/          # 业务逻辑
│   ├── mapper/           # 数据访问
│   ├── entity/           # 数据库实体
│   ├── dto/              # 请求 / 响应对象
│   ├── common/           # 统一响应、全局异常处理
│   └── config/           # 配置类
├── src/main/resources/
│   ├── application.yml
│   ├── application-local.yml     # 本地配置，已在 .gitignore 中
│   └── mapper/                   # MyBatis XML
└── sql/                          # 建表脚本
```

<!-- 包名 com.cyanyuzi.curio 是按你的 GitHub 名起的，想换随便换，改完记得同步这里 -->

---

## 快速开始

```bash
# 1. 建库建表
mysql -u root -p < sql/schema.sql

# 2. 配置本地数据库连接
cp src/main/resources/application-example.yml src/main/resources/application-local.yml
# 然后编辑 application-local.yml，填你自己的密码

# 3. 启动
./mvnw spring-boot:run
```

启动后访问 `http://localhost:8080/swagger-ui.html` 查看接口文档。

---

## 路线图

- [ ] **M1** 资料库基础 —— 上传 / 列表 / 下载 / 删除 / 秒传去重
- [ ] **M2** Agent 读资料问答 —— 检索 + SSE 流式响应
- [ ] **M3** 团队协作 —— RBAC 权限模型 + 多租户数据隔离
- [ ] **M4** Agent review 产出 —— 工具调用 + 异步任务
- [ ] **M5** 评估集 / 单元测试 / 压测报告

> M1 + M2 完成后即可投入使用。

---

## 踩坑记录

<!-- ⚠️ 每踩一个坑就立刻在这里加一条。别攒到最后补 —— 那时你已经忘了细节。
     格式：

     ### 坑的标题

     **现象**：看到了什么
     **原因**：为什么
     **解决**：怎么改的

     这是整份 README 里对面试最有价值的部分。 -->

*(施工中)*

---

## License

[MIT](LICENSE)
