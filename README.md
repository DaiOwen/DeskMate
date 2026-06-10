# DeskMate

[![Java CI](https://github.com/DaiOwen/DeskMate/actions/workflows/maven.yml/badge.svg)](https://github.com/DaiOwen/DeskMate/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

DeskMate is a B2C campus e-commerce platform built on a distributed architecture using the SSM (Spring + Spring MVC + MyBatis) framework. It provides both a back-office shop management system and a front-facing shopping portal. Subsystems communicate via HTTP, with Redis powering SSO and caching, MySQL as the database, and SUI Mobile for the frontend UI framework.

> 中文文档请参阅 [README_CN.md](./README_CN.md)

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    DeskMate Platform                     │
├──────────────────────┬──────────────────────────────────┤
│  Frontend Portal      │  Backend Management              │
│  (Customer-facing)    │  (Shop Owner & Admin)            │
├──────────────────────┼──────────────────────────────────┤
│  • Main page          │  • Shop registration & editing    │
│  • Shop listing        │  • Product CRUD with images      │
│  • Shop detail         │  • Product category management   │
│  • Product detail      │  • Permission control            │
│                       │  • Area management                │
├──────────────────────┴──────────────────────────────────┤
│                    Shared Services                       │
├─────────────────────────────────────────────────────────┤
│  Auth: WeChat OAuth2  │  Local Account Login / Bind / SSO│
│  Cache: Redis (Jedis)  │  Image: Thumbnailator + FTP/Nginx│
│  DB: MySQL + C3P0 Pool │  Mapper: MyBatis XML            │
└─────────────────────────────────────────────────────────┘
```

## Tech Stack

| Category | Technology | Version |
|----------|------------|---------|
| Language | Java | 1.8 |
| Framework | Spring (Core, Beans, Context, JDBC, TX, Web, WebMVC) | 4.3.7 |
| ORM | MyBatis + MyBatis-Spring | 3.4.6 / 1.3.2 |
| Database | MySQL | 5.x |
| Connection Pool | C3P0 | 0.9.5.2 |
| Cache | Redis (Jedis client) | 2.9.0 |
| Logging | Logback Classic | 1.2.3 |
| JSON | Jackson Databind | 2.9.6 |
| Image Processing | Thumbnailator | 0.4.8 |
| Captcha | Kaptcha | 2.3.2 |
| File Upload | Commons FileUpload | 1.3.2 |
| Build | Maven (WAR packaging) | - |
| Frontend | SUI Mobile + jQuery + HTML5/CSS3 | - |
| Web Server | Nginx + Apache Tomcat | - |
| FTP | vsftpd (image uploads) | - |

## Project Structure

```
src/main/java/com/dai/
├── cache/                 # Redis/Jedis abstraction layer (Keys, Strings, Lists, Sets, Hash)
│   ├── JedisPoolWriper.java
│   └── JedisUtil.java
├── dao/                   # MyBatis DAO interfaces
│   ├── AreaDao.java
│   ├── HeadLineDao.java
│   ├── LocalAuthDao.java
│   ├── PersonInfoDao.java
│   ├── ProductCategoryDao.java
│   ├── ProductDao.java
│   ├── ProductImgDao.java
│   ├── ShopCategoryDao.java
│   ├── ShopDao.java
│   ├── WechatAuthDao.java
│   └── split/             # Dynamic data source routing (read/write splitting)
├── dto/                   # Data Transfer Objects & execution results
├── entity/                # Domain entities (Area, Shop, Product, PersonInfo, etc.)
├── enums/                 # State enums for all entities
├── exception/             # Custom operation exceptions
├── service/               # Business logic interfaces
│   └── impl/              # Service implementations with @Service
├── util/                  # Utilities (DES, MD5, image, path, code, page calc)
│   └── wechat/            # WeChat SDK utilities (sign, token, HTTPS trust)
└── web/                   # Spring MVC Controllers (REST JSON APIs)
    ├── frontend/          # Customer-facing: MainPage, ShopList, ShopDetail, ProductDetail
    ├── local/             # Local auth: Login, Logout, Bind, Change Password
    ├── shopadmin/         # Shop management: Shop CRUD, Product CRUD, Category management
    ├── superadmin/        # Super admin: Area listing
    └── wechat/            # WeChat OAuth2 login & user info
```

## Core Entities

| Entity | Description |
|--------|-------------|
| **Area** | Geographic region for shops |
| **PersonInfo** | User profile (shared by local & WeChat auth) |
| **LocalAuth** | Username/password credentials (1:1 with PersonInfo) |
| **WechatAuth** | WeChat OpenID mapping (1:1 with PersonInfo) |
| **Shop** | Store with owner, category, area, status |
| **ShopCategory** | Hierarchical store categories (parent-child) |
| **Product** | Product with prices, images, shop & category |
| **ProductCategory** | Product categories scoped within a shop |
| **ProductImg** | Product detail gallery images |
| **HeadLine** | Homepage news/hero content |

## Key Features

### Authentication & SSO
- **WeChat OAuth2 Login**: Users log in via WeChat browser. New users auto-register. Supports role routing (frontend shopper vs. shop admin).
- **Local Account**: Username/password login, account binding to WeChat identity, password change — all backed by Redis-based SSO session management.
- **Captcha**: Kaptcha-generated verification codes for sensitive operations (registration, password change).

### Frontend Portal (`/frontend`)
- **Main page** — Headlines + first-level shop categories
- **Shop listing** — Filterable by category hierarchy, geographic area, and shop name; paginated
- **Shop detail** — Individual store with product listings
- **Product detail** — Product info with thumbnail + detail image gallery

### Shop Management (`/shopadmin`)
- **Shop CRUD** — Register new shops with cover image, edit shop details, store listing
- **Product management** — Add/edit/status-toggle products; thumbnail + up to 6 detail images per product; category and name filtering
- **Product category management** — Batch-add and delete categories per shop
- **Permission control** — Session-scoped: a shop owner can only manage their own shops

### Super Admin (`/superadmin`)
- **Area management** — List all geographic regions for shop assignment

### Caching
- Redis-backed with a custom Jedis utility class supporting Keys, Strings, Lists, Sets, and Hash operations
- `CacheService` supports pattern-based key removal (`keyPrefix*`) for cache invalidation

### Image Handling
- Upload via Spring `CommonsMultipartResolver`
- Thumbnail generation with Thumbnailator
- FTP storage (vsftpd) served via Nginx
- Auto-cleanup of old images on update

### Security
- DES-encrypted database credentials (decrypted at startup via `EncryptPropertyPlaceholderConfigurer`)
- Captcha verification on sensitive write operations
- Session-based authentication for shop management

## Configuration

### Database (`src/main/resources/jdbc.properties`)
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/school_o2o?useUnicode=true&characterEncoding=utf8&useSSL=false
jdbc.username=<encrypted>
jdbc.password=<encrypted>
```

### Redis (`src/main/resources/redis.properties`)
```
redis.hostname=<host>
redis.port=6379
redis.pool.maxActive=100
redis.pool.maxIdle=20
redis.pool.maxWait=3000
```

### Spring Context XMLs (`src/main/resources/spring/`)
- `spring-dao.xml` — Data source (C3P0), SqlSessionFactory, MyBatis MapperScanner
- `spring-service.xml` — Component scan for services, annotation-driven transactions
- `spring-web.xml` — Spring MVC with view resolver, multipart upload, static resources
- `spring-redis.xml` — Jedis connection pool and utility beans
- `mybatis-config.xml` — Auto-generated keys, underscore-to-camelCase mapping

## Prerequisites

- JDK 8+
- Maven 3.x
- MySQL 5.x
- Redis server
- Tomcat 8+ (or any Servlet 3.1 container)
- Nginx + vsftpd (for image storage and serving)

## Quick Start

### Docker (Recommended)

```bash
# Clone the repository
git clone git@github.com:DaiOwen/DeskMate.git
cd DeskMate

# Build and run with Docker Compose
mvn clean package -DskipTests
docker-compose up -d

# Open http://localhost:8080
```

### Manual Deployment

```bash
# Clone the repository
git clone git@github.com:DaiOwen/DeskMate.git
cd DeskMate

# Configure database & Redis properties
# Edit src/main/resources/jdbc.properties
# Edit src/main/resources/redis.properties

# Build
mvn clean package -DskipTests

# Deploy target/school_o2o.war to Tomcat webapps/
```

## License

This project is licensed under the MIT License — see the [LICENSE](./LICENSE) file for details.

---

<p align="center">
  <b>⭐ If this project helps you, please consider giving it a star!</b><br>
  <i>Your support keeps this project alive and growing 🚀</i>
</p>