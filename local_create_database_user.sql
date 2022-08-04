DROP DATABASE IF EXISTS school;
CREATE DATABASE school;
DROP USER IF EXISTS 'school_admin';
CREATE USER 'school_admin'@'%' IDENTIFIED BY 'school_admin';
GRANT ALL PRIVILEGES ON school.* TO 'school_admin'@localhost IDENTIFIED BY 'school_admin';