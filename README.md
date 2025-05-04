# Hướng Dẫn Cài Đặt Dự Án

## Yêu Cầu Hệ Thống
Trước khi cài đặt, đảm bảo rằng bạn đã cài đặt các công cụ sau:
- **Java 17** trở lên
- **Maven 3.8+**
- **MySQL 8+** (hoặc một cơ sở dữ liệu tương thích)
- **Cloudinary Account** (nếu sử dụng tính năng tải ảnh)
- **VNPay Account** (nếu sử dụng tính năng thanh toán)
- **Twilio Account** (nếu sử dụng dịch vụ SMS)

## Cấu Hình Môi Trường

Thay đổi các thông tin cấu hình CLOUDINARY_NAME, CLOUDINARY_KEY, CLOUDINARY_SECRET, VNP_TMNCODE, VNP_SECRETKEY, VNP_RETURNURL, TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN, TWILIO_PHONE_NUMBER bằng cách truyền biến môi trường hoặc thay thế trực tiếp vao tệp `src/main/resources/application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /

spring:
  datasource:
    url: ${DB_URL:jdbc:mysql://localhost:3306/restaurant}
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:1234567890}
    driver-class-name: ${DB_DRIVER:com.mysql.cj.jdbc.Driver} 
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  thymeleaf:
    cache: false
    prefix: classpath:/templates/
    suffix: .html
  web:
    locale: en_US

cloudinary:
  cloud_name: ${CLOUDINARY_NAME:111}
  api_key: ${CLOUDINARY_KEY:111}
  api_secret: ${CLOUDINARY_SECRET:111}
  secure: true

payment:
  vnpay:
    tmnCode: ${VNP_TMNCODE:12}
    secretKey: ${VNP_SECRETKEY:12}
    returnUrl: ${VNP_RETURNURL:http://localhost:8080/payment/vn-pay-callback}

twilio:
  accountSid: ${TWILIO_ACCOUNT_SID:abcd}
  authToken: ${TWILIO_AUTH_TOKEN:abcd}
  fromPhoneNumber: ${TWILIO_PHONE_NUMBER:abcd}
```

## Cài Đặt & Chạy Dự Án

### 1. Clone Repository
```sh
git clone https://github.com/In-University/Restaurant-Management-Web.git
cd Restaurant-Management-Web
```

### 2. Cấu Hình Cơ Sở Dữ Liệu
- Đảm bảo MySQL đang chạy
- Tạo cơ sở dữ liệu:
```sql
CREATE DATABASE restaurant;
```

### 3. Cài Đặt Dependencies
```sh
mvn clean install
```

### 4. Chạy Ứng Dụng
```sh
mvn spring-boot:run
```
Ứng dụng sẽ chạy trên `http://localhost:8080/`

## Cấu Trúc Dự Án
```
├── src/
│   ├── main/
│   │   ├── java/com/example/restaurant/
│   │   ├── resources/
│   │   │   ├── templates/   # Giao diện Thymeleaf
│   │   │   ├── static/      # CSS, JS, hình ảnh
│   │   │   ├── application.yml  # Cấu hình Spring Boot 3
├── pom.xml   # Quản lý dependencies
└── README.md
```


