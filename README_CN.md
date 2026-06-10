# DeskMate

[![Java CI](https://github.com/DaiOwen/DeskMate/actions/workflows/maven.yml/badge.svg)](https://github.com/DaiOwen/DeskMate/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

DeskMate 是一个基于分布式架构的B2C校园电商平台，采用SSM（Spring + Spring MVC + MyBatis）框架构建。系统包含后台店铺管理系统和前端的商城购物系统，各个子系统之间通过HTTP相互调用，使用Redis实现SSO和缓存，MySQL作为数据库，SUI Mobile作为前端UI框架。

> For English documentation, please refer to [README.md](./README.md)

## 系统架构

```
┌─────────────────────────────────────────────────────────┐
│                     DeskMate 平台                        │
├──────────────────────┬──────────────────────────────────┤
│   前端购物门户          │  后台管理系统                      │
│   (面向用户)            │  (面向店家和管理员)                 │
├──────────────────────┼──────────────────────────────────┤
│  • 主页/头条展示        │  • 店铺注册与编辑                   │
│  • 店铺列表与筛选        │  • 商品增删改查（含图片）             │
│  • 店铺详情             │  • 商品类别管理                    │
│  • 商品详情             │  • 权限控制                       │
│                       │  • 区域管理                       │
├──────────────────────┴──────────────────────────────────┤
│                      共享服务层                            │
├─────────────────────────────────────────────────────────┤
│  认证：微信OAuth2      │  本地帐号登录 / 绑定 / SSO         │
│  缓存：Redis (Jedis)   │  图片：Thumbnailator + FTP/Nginx  │
│  数据库：MySQL + C3P0  │  持久层：MyBatis XML映射           │
└─────────────────────────────────────────────────────────┘
```

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 开发语言 | Java | 1.8 |
| 核心框架 | Spring (Core, Beans, Context, JDBC, TX, Web, WebMVC) | 4.3.7 |
| ORM | MyBatis + MyBatis-Spring | 3.4.6 / 1.3.2 |
| 数据库 | MySQL | 5.x |
| 连接池 | C3P0 | 0.9.5.2 |
| 缓存 | Redis（Jedis客户端） | 2.9.0 |
| 日志 | Logback Classic | 1.2.3 |
| JSON解析 | Jackson Databind | 2.9.6 |
| 图片处理 | Thumbnailator | 0.4.8 |
| 验证码 | Kaptcha | 2.3.2 |
| 文件上传 | Commons FileUpload | 1.3.2 |
| 构建工具 | Maven（WAR打包） | - |
| 前端 | SUI Mobile + jQuery + HTML5/CSS3 | - |
| Web服务器 | Nginx + Apache Tomcat | - |
| FTP服务 | vsftpd（图片上传） | - |

## 项目结构

```
src/main/java/com/dai/
├── cache/                 # Redis/Jedis抽象层（Keys、Strings、Lists、Sets、Hash操作）
│   ├── JedisPoolWriper.java
│   └── JedisUtil.java
├── dao/                   # MyBatis数据访问接口
│   ├── AreaDao.java       # 区域
│   ├── HeadLineDao.java   # 头条
│   ├── LocalAuthDao.java  # 本地帐号
│   ├── PersonInfoDao.java # 用户信息
│   ├── ProductCategoryDao.java # 商品类别
│   ├── ProductDao.java    # 商品
│   ├── ProductImgDao.java # 商品图片
│   ├── ShopCategoryDao.java # 店铺类别
│   ├── ShopDao.java       # 店铺
│   ├── WechatAuthDao.java # 微信帐号
│   └── split/             # 动态数据源路由（读写分离）
├── dto/                   # 数据传输对象与执行结果封装
├── entity/                # 领域实体（Area、Shop、Product、PersonInfo等）
├── enums/                 # 各实体的状态枚举
├── exception/             # 自定义操作异常
├── service/               # 业务逻辑接口
│   └── impl/              # 业务逻辑实现（通过@Service注解注入）
├── util/                  # 工具类（DES加密、MD5、图片处理、路径、验证码、分页计算）
│   └── wechat/            # 微信SDK工具（签名、Token、HTTPS信任）
└── web/                   # Spring MVC控制器（REST JSON API）
    ├── frontend/          # 前端展示：主页、店铺列表、店铺详情、商品详情
    ├── local/             # 本地认证：登录、登出、帐号绑定、修改密码
    ├── shopadmin/         # 店铺管理：店铺CRUD、商品CRUD、类别管理
    ├── superadmin/        # 超级管理员：区域管理
    └── wechat/            # 微信OAuth2登录与用户信息获取
```

## 核心实体

| 实体 | 说明 |
|------|------|
| **Area** | 区域信息，店铺所属地理位置 |
| **PersonInfo** | 用户个人信息（本地帐号与微信帐号共用） |
| **LocalAuth** | 用户名密码认证（与PersonInfo一一对应） |
| **WechatAuth** | 微信OpenID映射（与PersonInfo一一对应） |
| **Shop** | 店铺信息，包含所属人、类别、区域、状态 |
| **ShopCategory** | 层级店铺类别（支持父子级联） |
| **Product** | 商品信息，包含价格、图片、所属店铺和类别 |
| **ProductCategory** | 商品类别，每个店铺独立管理 |
| **ProductImg** | 商品详情图片列表 |
| **HeadLine** | 首页头条/轮播内容 |

## 核心功能

### 认证与SSO单点登录
- **微信OAuth2登录**：用户在微信浏览器中授权登录，新用户自动注册。支持角色路由（前端购物用户 vs. 后台店铺管理员）。
- **本地帐号**：用户名密码登录、微信帐号绑定本地帐号、修改密码，全部基于Redis实现SSO会话管理。
- **验证码**：通过Kaptcha生成图片验证码，保护注册、改密等敏感操作。

### 前端购物门户 (`/frontend`)
- **主页展示** — 头条内容 + 一级店铺类别列表
- **店铺列表** — 支持按类别层级、区域、店铺名称模糊搜索筛选，分页展示
- **店铺详情** — 展示店铺信息及所属商品列表
- **商品详情** — 展示商品缩略图、详情图库及价格信息

### 后台店铺管理 (`/shopadmin`)
- **店铺管理** — 注册新店铺（含封面图上传）、编辑店铺信息、店铺列表
- **商品管理** — 商品添加/编辑/上下架；每个商品支持1张缩略图 + 最多6张详情图；支持按类别和名称筛选
- **商品类别管理** — 批量添加和删除店铺内商品类别
- **权限控制** — 基于Session的店铺隔离，店家只能操作自己的店铺

### 超级管理员 (`/superadmin`)
- **区域管理** — 管理店铺可关联的所有地理区域

### 缓存机制
- 基于Redis实现缓存，自定义Jedis工具类封装了Keys、Strings、Lists、Sets、Hash等全部数据结构操作
- `CacheService` 支持基于前缀的模式匹配批量删除缓存（`keyPrefix*`），实现缓存失效

### 图片处理
- 通过Spring `CommonsMultipartResolver` 接收上传文件
- 使用Thumbnailator生成缩略图
- 通过vsftpd上传至FTP服务器，由Nginx提供HTTP访问
- 更新图片时自动清理旧图片文件

### 安全机制
- 数据库帐号密码采用DES加密存储（启动时由 `EncryptPropertyPlaceholderConfigurer` 解密加载）
- 写入类操作强制验证码校验
- 基于Session的店铺管理权限控制

## 配置说明

### 数据库配置 (`src/main/resources/jdbc.properties`)
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/school_o2o?useUnicode=true&characterEncoding=utf8&useSSL=false
jdbc.username=<加密存储>
jdbc.password=<加密存储>
```

### Redis配置 (`src/main/resources/redis.properties`)
```
redis.hostname=<服务器地址>
redis.port=6379
redis.pool.maxActive=100
redis.pool.maxIdle=20
redis.pool.maxWait=3000
```

### Spring上下文配置 (`src/main/resources/spring/`)
- `spring-dao.xml` — 数据源（C3P0连接池）、SqlSessionFactory、MyBatis MapperScanner
- `spring-service.xml` — Service层组件扫描，基于注解的声明式事务管理
- `spring-web.xml` — Spring MVC配置：视图解析器、文件上传解析器、静态资源映射
- `spring-redis.xml` — Jedis连接池配置及各项操作工具Bean的注册
- `mybatis-config.xml` — MyBatis全局配置：自增主键、驼峰命名自动映射

## 运行环境要求

- JDK 8+
- Maven 3.x
- MySQL 5.x
- Redis 服务器
- Tomcat 8+（或其他 Servlet 3.1 容器）
- Nginx + vsftpd（用于图片存储和访问）

## 快速开始

### Docker部署（推荐）

```bash
# 克隆仓库
git clone git@github.com:DaiOwen/DeskMate.git
cd DeskMate

# 使用 Docker Compose 一键构建运行
mvn clean package -DskipTests
docker-compose up -d

# 打开浏览器访问 http://localhost:8080
```

### 手动部署

```bash
# 克隆仓库
git clone git@github.com:DaiOwen/DeskMate.git
cd DeskMate

# 配置数据库与Redis连接信息
# 编辑 src/main/resources/jdbc.properties
# 编辑 src/main/resources/redis.properties

# 编译构建
mvn clean package -DskipTests

# 将 target/school_o2o.war 部署至 Tomcat webapps/ 目录下
```

## 许可证

本项目基于 MIT 许可证开源 — 详见 [LICENSE](./LICENSE) 文件。

---

<p align="center">
  <b>⭐ 如果此项目对您有帮助，请赐予一颗星标！</b><br>
  <i>您的支持让这个项目持续成长 🚀</i>
</p>