# Gym Health Monitor

## 1. Giới thiệu
Gym Health Monitor là một hệ thống quản lý phòng gym và theo dõi sức khỏe, được xây dựng bằng **Spring MVC**, **Hibernate**, và **React.js**.

## 2. Yêu cầu hệ thống
- **Java**: JDK 23+
- **Spring Framework**: 6.0+
- **Hibernate**: 6.0+ (6.6.9.Final)
- **Node.js**: 20+ (20.11.1)
- **React.js**: 19+
- **Database**: MySQL 8.0+ (8.0.37)
- **Apache Tomcat**: 11+ (11.0.5)

## 3. Cấu hình hệ thống
### 3.1. Cấu hình Backend (Spring MVC + Hibernate)
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
      CREATE SCHEMA gym_health_db DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
   ```
   - Cấu hình `resources/database.properties`:
   ```properties
      hibernate.connection.url=jdbc:mysql://localhost:3306/gym_health_db?serverTimezone=UTC
      hibernate.connection.username=root
      hibernate.connection.password=Abc111!
   ```

3. **Cấu hình Tomcat trong IntelliJ và NetBeans**:
   - **Trong IntelliJ IDEA:**
      1. Mở **Run → Edit Configurations**
      2. Nhấn `+` → Chọn **Tomcat Server → Local**
      3. Chọn **Tomcat Home** (đường dẫn đến thư mục Tomcat)
      4. Trong **Deployment**, nhấn `+` → Chọn **Artifact** (`.war` nếu có)
      5. Vào tab **Server**, đặt `HTTP Port` (VD: `8080`)
      6. Nhấn **Apply → OK**
      7. Nhấn **Run** hoặc **Debug** để khởi động ứng dụng
   
   - **Trong NetBeans:**
      1. Vào **Tools → Servers**
      2. Nhấn **Add Server** → Chọn **Tomcat** → Chỉ đường dẫn tới thư mục Tomcat
      3. Nhấn **Next** và hoàn tất thiết lập
      4. Mở **Projects**, nhấn chuột phải vào dự án, chọn **Properties**
      5. Trong **Run**, chọn **Tomcat Server**
      6. Nhấn **OK** và nhấn **Run** để chạy ứng dụng

4. **Cài đặt và chạy Backend**:
   - **Trong IntelliJ IDEA**:
     ```sh
     mvn clean package
     ```
     - Copy file `.war` vào thư mục `webapps` của Tomcat và chạy:
     ```sh
     cd tomcat/bin
     ./startup.sh   # Linux/Mac
     ./startup.bat  # Windows
     ```
   
   - **Trong NetBeans:**
     - Nhấn **Run** trực tiếp từ IDE (Tomcat đã được cấu hình)
   
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
   - **Trong IntelliJ IDEA:**
     ```sh
     mvn clean package
     cd tomcat/bin
     ./startup.sh  # Hoặc ./startup.bat trên Windows
     ```
   - **Trong NetBeans:**
     - Mở NetBeans
     - Chạy project bằng cách nhấn **Run** (chắc chắn Tomcat đã được cấu hình)

2. **Chạy Frontend**:
   ```sh
   cd health-monitor-web
   npm start
   ```
3. **Truy cập ứng dụng**:
   - API Backend: `http://localhost:8080`
   - UI Frontend: `http://localhost:3000`
