-- Create the database
CREATE DATABASE IF NOT EXISTS gym_management;
USE gym_management;

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'pt', 'member') NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    avatar VARCHAR(255),
    status ENUM('active', 'inactive') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: members
CREATE TABLE IF NOT EXISTS members (
    member_id INT PRIMARY KEY,
    height FLOAT NOT NULL CHECK (height > 0),
    weight FLOAT NOT NULL CHECK (weight > 0),
    fitness_goal TEXT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: pt
CREATE TABLE IF NOT EXISTS pt (
    pt_id INT PRIMARY KEY,
    major VARCHAR(100) NOT NULL,
    FOREIGN KEY (pt_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: packages
CREATE TABLE IF NOT EXISTS packages (
    package_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    duration ENUM('monthly', 'quarterly', 'yearly') NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    description TEXT NOT NULL,
    pt_sessions INT NOT NULL CHECK (pt_sessions >= 0),
    status ENUM('active', 'inactive') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: subscriptions
CREATE TABLE IF NOT EXISTS subscriptions (
    subscription_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    package_id INT NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status ENUM('active', 'expired') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
    FOREIGN KEY (package_id) REFERENCES packages(package_id) ON DELETE CASCADE
);

-- Table: schedules
CREATE TABLE IF NOT EXISTS schedules (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    subscription_id INT NOT NULL,
    member_id INT NOT NULL,
    pt_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    type ENUM('pt_session', 'self_training') NOT NULL,
    status ENUM('pending', 'approved', 'rejected') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(subscription_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
    FOREIGN KEY (pt_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: progress
CREATE TABLE IF NOT EXISTS progress (
    progress_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    pt_id INT NOT NULL,
    date DATETIME NOT NULL,
    weight FLOAT NOT NULL CHECK (weight > 0),
    body_fat FLOAT NOT NULL CHECK (body_fat >= 0 AND body_fat <= 100),
    muscle_mass FLOAT NOT NULL CHECK (muscle_mass >= 0 AND muscle_mass <= 100),
    notes TEXT,
    FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
    FOREIGN KEY (pt_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: notifications
CREATE TABLE IF NOT EXISTS notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    type ENUM('reminder', 'promotion', 'expiry') NOT NULL,
    status ENUM('unread', 'read') NOT NULL DEFAULT 'unread',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: payments
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    subscription_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL CHECK (amount >= 0),
    method ENUM('momo', 'vnpay', 'bank_transfer') NOT NULL,
    receipt_image VARCHAR(255),
    status ENUM('pending', 'completed') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(subscription_id) ON DELETE CASCADE
);

-- Table: feedback
CREATE TABLE IF NOT EXISTS feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE
);

-- Table: statistics
CREATE TABLE IF NOT EXISTS statistics (
    statistic_id INT AUTO_INCREMENT PRIMARY KEY,
    total_members INT NOT NULL CHECK (total_members >= 0),
    total_revenue DECIMAL(15, 2) NOT NULL CHECK (total_revenue >= 0),
    peak_hours VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);