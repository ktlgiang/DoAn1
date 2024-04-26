
CREATE DATABASE DACS1_1;
USE DACS1_1;

-- Tạo bảng Người dùng
CREATE TABLE Người_dùng (
    Mã_người_dùng INT AUTO_INCREMENT PRIMARY KEY,
    Tên_người_dùng NVARCHAR(50),
    Mật_khẩu VARCHAR(50),
    Email VARCHAR(100) UNIQUE CHECK (Email REGEXP '^[a-zA-Z0-9._%+-]+@gmail\.com$'),
    Vai_trò VARCHAR(50)
);

-- Tạo bảng Loại_tài_liệu
CREATE TABLE Loại_tài_liệu (
    Mã_loại_tài_liệu INT PRIMARY KEY,
    Tên_loại NVARCHAR(100)
);

-- Tạo bảng Môn_học
CREATE TABLE Môn_học (
    Mã_môn_học VARCHAR(7) PRIMARY KEY,
    Tên_môn_học NVARCHAR(100)
);
-- Tạo bảng Tài_liệu
CREATE TABLE Tài_liệu (
    Mã_tài_liệu INT AUTO_INCREMENT PRIMARY KEY,
    Tên_tài_liệu NVARCHAR(100),
    Mô_tả NVARCHAR(255),
    Ngày_tải datetime DEFAULT CURRENT_TIMESTAMP,
    Đường_dẫn NVARCHAR(255),
    Tác_giả NVARCHAR(100),
    Trạng_thái VARCHAR(50),
    Mã_loại_tài_liệu INT,
    Mã_môn_học VARCHAR(7),
    Mã_người_dùng INT,
    FOREIGN KEY (Mã_loại_tài_liệu) REFERENCES Loại_tài_liệu(Mã_loại_tài_liệu),
    FOREIGN KEY (Mã_môn_học) REFERENCES Môn_học(Mã_môn_học),
    FOREIGN KEY (Mã_người_dùng) REFERENCES Người_dùng(Mã_người_dùng)
);

-- Tạo bảng Người_dùng_Môn_học
CREATE TABLE Người_dùng_Môn_học (
    Mã_người_dùng_Môn_học INT AUTO_INCREMENT PRIMARY KEY,
    Mã_người_dùng INT,
    Mã_môn_học VARCHAR(7),
    FOREIGN KEY (Mã_người_dùng) REFERENCES Người_dùng(Mã_người_dùng),
    FOREIGN KEY (Mã_môn_học) REFERENCES Môn_học(Mã_môn_học)
);


-- Tạo bảng Bình_luận
CREATE TABLE Bình_luận (
    Mã_bình_luận INT AUTO_INCREMENT PRIMARY KEY,
    Mã_tài_liệu INT,
    Mã_người_dùng INT,
    Ngày_bình_luận datetime DEFAULT CURRENT_TIMESTAMP,
    Nội_dung NVARCHAR(550),
    FOREIGN KEY (Mã_tài_liệu) REFERENCES Tài_liệu(Mã_tài_liệu),
    FOREIGN KEY (Mã_người_dùng) REFERENCES Người_dùng(Mã_người_dùng)
);

-- Thêm dữ liệu vào bảng Loại_tài_liệu
INSERT INTO Loại_tài_liệu (Mã_loại_tài_liệu, Tên_loại) VALUES 
(1, 'Bài giảng'),
(2, 'Tài liệu thực hành'),
(3, 'Bài tập'),
(4, 'Tóm tắt'),
(5, 'Bài tập lớn'),
(6, 'Hướng dẫn'),
(7, 'Bài luận'),
(8, 'Khác');

-- Thêm dữ liệu vào bảng Môn_học
INSERT INTO Môn_học (Mã_môn_học, Tên_môn_học) VALUES 
('OOP', 'LẬP TRÌNH HƯỚNG ĐỐI TƯỢNG'),
('HTML', 'LẬP TRÌNH WEB'),
('DSA', 'CẤU TRÚC DỮ LIỆU VÀ GIẢI THUẬT'),
('JS', 'JAVASCRIPT'),
('IOT', 'LẬP TRÌNH NHÚNG');


-- Thêm dữ liệu vào bảng Người_dùng
INSERT INTO Người_dùng (Tên_người_dùng,Email, Mật_khẩu, Vai_trò)
VALUES 
('Nguoi dung 1','managerIOT@gmail.com', '123', 'admin'),
('Nguoi dung 2','managerPRJ@gmail.com', '123', 'admin'),
('Nguoi dung 3','managerMAS@gmail.com', '123', 'admin'),
('Nguoi dung 4','managerJPD@gmail.com', '123', 'admin'),
('Nguoi dung 5','managerSWE@gmail.com', '123', 'admin'),
('Nguoi dung 6','user1@gmail.com','123', 'user');

-- Thêm dữ liệu vào bảng Người_dùng_Môn_học
INSERT INTO Người_dùng_Môn_học (Mã_người_dùng, Mã_môn_học)
VALUES 
(1, 'IOT'),
(2, 'IOT'),
(3, 'OOP'),
(4, 'JS'),
(5, 'JS');

-- Thêm dữ liệu vào bảng Tài_liệu
INSERT INTO Tài_liệu (Tên_tài_liệu, Mô_tả, Ngày_tải, Đường_dẫn, Tác_giả, Trạng_thái, Mã_loại_tài_liệu, Mã_môn_học, Mã_người_dùng)
VALUES 
('Tài liệu 1', 'Mô tả cho Tài liệu 1', CURRENT_TIMESTAMP, '/path/to/document1.pdf', 'Tác giả 1', 'Hoạt động', 1, 'OOP', 1),
('Tài liệu 2', 'Mô tả cho Tài liệu 2', CURRENT_TIMESTAMP, '/path/to/document2.pdf', 'Tác giả 2', 'Hoạt động', 2, 'OOP', 2),
('Tài liệu 3', 'Mô tả cho Tài liệu 3', CURRENT_TIMESTAMP, '/path/to/document3.pdf', 'Tác giả 3', 'Hoạt động', 3, 'JS', 3),
('Tài liệu 4', 'Mô tả cho Tài liệu 4', CURRENT_TIMESTAMP, '/path/to/document4.pdf', 'Tác giả 4', 'Hoạt động', 1, 'HTML', 4),
('Tài liệu 5', 'Mô tả cho Tài liệu 5', CURRENT_TIMESTAMP, '/path/to/document5.pdf', 'Tác giả 5', 'Hoạt động', 2, 'DSA', 5);

-- Thêm dữ liệu vào bảng Bình_luận
INSERT INTO Bình_luận (Mã_tài_liệu, Mã_người_dùng, Nội_dung, Ngày_bình_luận)
VALUES 
(1, 1, 'Đây là bình luận thử nghiệm 1', CURRENT_TIMESTAMP),
(2, 2, 'Đây là bình luận thử nghiệm 2', CURRENT_TIMESTAMP);

-- Lấy dữ liệu từ các bảng
SELECT * FROM Tài_liệu;
SELECT * FROM Môn_học;
SELECT * FROM Loại_tài_liệu;
SELECT * FROM Người_dùng;
SELECT * FROM Người_dùng_Môn_học;
SELECT * FROM Bình_luận; 
