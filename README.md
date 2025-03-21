# Gym Health Monitor

## 1. Giới thiệu
Gym Health Monitor là một hệ thống quản lý phòng gym và theo dõi sức khỏe, được xây dựng bằng **Spring Boot**, **Hibernate**, và **React.js**.

## 2. Yêu cầu hệ thống
- **Java**: JDK 23+
- **Spring Boot**: 3.0+
- **Hibernate**: 6.0+ (6.6.9.Final)
- **Node.js**: 20+ (20.11.1)
- **React.js**: 19+
- **Database**: MySQL 8.0+ (8.0.37)
- **Apache Tomcat**: 11+ (11.0.5)

## 3. Cấu hình hệ thống
### 3.1. Cấu hình Backend (Spring Boot + Hibernate)
1. **Cài đặt Java & Maven**:
   - Kiểm tra phiên bản:
     ```sh
     java -version
     mvn -version
     ```
   - Nếu chưa có, cài đặt JDK và Maven theo hướng dẫn của hệ điều hành.

2. **Cấu hình MySQL**:
   - Tạo database: `gym_health_db`
   ```sh
      CREATE SCHEMA gym_health_db DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
   ```
   - Cấu hình `persistence.xml`:
     ```properties
      <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/gym_health_db?useSSL=false"/>
      <property name="jakarta.persistence.jdbc.user" value="root"/>
      <property name="jakarta.persistence.jdbc.password" value="password"/>
     ```
     - Cấu hình `HibernateUtils.java`:
     ```properties
      props.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/gym_health_db");
      props.put(Environment.JAKARTA_JDBC_USER, "root");
      props.put(Environment.JAKARTA_JDBC_PASSWORD, "password");
     ```

3. **Chạy ứng dụng Backend**:
   ```sh
   mvn spring-boot:run
   ```
   - Ứng dụng chạy tại: `http://localhost:8080`

### 3.2. Cấu hình Frontend (React.js)
1. **Cài đặt Node.js và Yarn/NPM**:
   - Kiểm tra phiên bản:
     ```sh
     node -v
     npm -v
     ```
   - Nếu chưa có, tải từ [https://nodejs.org/](https://nodejs.org/)

2. **Cài đặt dependencies**:
   ```sh
   cd health-monitor-web
   npm install
   ```

3. **Chạy ứng dụng React**:
   ```sh
   npm start
   ```
   - Ứng dụng chạy tại: `http://localhost:3000`

## 4. Cách chạy hệ thống hoàn chỉnh
1. **Chạy Backend trước**:
   ```sh
   mvn spring-boot:run
   ```
2. **Chạy Frontend**:
   ```sh
   cd health-monitor-web
   npm start
   ```
3. **Truy cập ứng dụng**:
   - API Backend: `http://localhost:8080`
   - UI Frontend: `http://localhost:3000`
