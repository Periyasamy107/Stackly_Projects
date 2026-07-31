-- STACKLY SQL PROJECTS :
-- =======================

-- 1) STUDENTS PROJECT :::::
-- ******************************

create database student_project;

use student_project;

create table students (
	student_id int primary key,
    student_name varchar(50),
    department varchar(30),
    marks int 
);



INSERT INTO students VALUES
(101,'Arun Kumar','Computer Science',95),
(102,'Priya Sharma','Computer Science',88),
(103,'Rahul Verma','Computer Science',82),
(104,'Sneha Reddy','Computer Science',91),
(105,'Karthik Raj','Computer Science',76),
(106,'Divya Nair','Computer Science',69),
(107,'Vikram Singh','Information Technology',84),
(108,'Anjali Gupta','Information Technology',79),
(109,'Suresh Babu','Information Technology',92),
(110,'Meena Lakshmi','Information Technology',87),
(111,'Ramesh Kumar','Information Technology',73),
(112,'Pooja Mehta','Information Technology',81),
(113,'Ajith Kumar','Electronics',66),
(114,'Neha Agarwal','Electronics',72),
(115,'Manoj Das','Electronics',78),
(116,'Lakshmi Priya','Electronics',83),
(117,'Harish Kumar','Electronics',91),
(118,'Keerthana S','Electronics',88),
(119,'Naveen Kumar','Mechanical',74),
(120,'Asha Devi','Mechanical',69),
(121,'Rohit Sharma','Mechanical',81),
(122,'Deepika Rao','Mechanical',77),
(123,'Ganesh Kumar','Mechanical',85),
(124,'Swetha R','Mechanical',89),
(125,'Prakash R','Civil',71),
(126,'Saravanan M','Civil',68),
(127,'Kavitha R','Civil',75),
(128,'Bharath Kumar','Civil',82),
(129,'Nandhini V','Civil',79),
(130,'Sathish Kumar','Civil',64),
(131,'Revathi S','Electrical',93),
(132,'Aravind Rao','Electrical',86),
(133,'Sowmya P','Electrical',78),
(134,'Mahesh Babu','Electrical',81),
(135,'Geetha Lakshmi','Electrical',74),
(136,'Vinod Kumar','Electrical',88),
(137,'Preethi N','Artificial Intelligence',97),
(138,'Ashok Kumar','Artificial Intelligence',94),
(139,'Bhavani Devi','Artificial Intelligence',89),
(140,'Sanjay Patel','Artificial Intelligence',91),
(141,'Komal Shah','Artificial Intelligence',85),
(142,'Yash Mehta','Artificial Intelligence',80),
(143,'Ritika Jain','Data Science',90),
(144,'Amit Tiwari','Data Science',84),
(145,'Shalini Verma','Data Science',79),
(146,'Rajesh Yadav','Data Science',73),
(147,'Anita Mishra','Data Science',88),
(148,'Sunil Joshi','Data Science',92),
(149,'Pallavi Kulkarni','Cyber Security',76),
(150,'Abhishek Singh','Cyber Security',83);


select * from students;


-- 31.	Find the average marks scored by students in each department. 

select department, avg(marks) average_mark from students group by department;


-- 32.	Find departments whose average marks are above 83. 

select department, avg(marks) average_mark
from students group by department having avg(marks) > 83;


-- 33.	Find the highest mark scored in each department. 

select department, max(marks) highest_mark 
from students group by department; 


-- 34.	Find the total number of students in each department. 

select department, count(*) total_students 
from students group by department; 



-- 35.	Find departments having more than 5 students. 

select department, count(student_id) students 
from students group by department having count(student_id) > 5;


-- 36.	Display departments sorted by average marks in descending order. 

select department, avg(marks) avg_marks from students
group by department order by avg(marks) desc;



-- 37.	Find the top 3 departments based on average marks. 

select department, avg(marks) avg_marks from students
group by department order by avg(marks) desc limit 3;



-- 38.	Find departments whose average marks are between 80 and 85. 

select department, avg(marks) avg_marks from students
group by department having avg(marks) between 80 and 85;



-- 39.	Find the total marks scored by students in each department. 

select department, sum(marks) total_marks from students group by department; 




-- 40.	Display departments sorted by the total number of students. 

select department, count(student_id) total_students from students
group by department order by count(student_id) asc;


-- 41.	Find the lowest mark scored in each department. 

select department, min(marks) lowest_marks from students
group by department;


-- 42.	Find departments where the highest mark is greater than 90. 

select department, max(marks) highest_mark from students
group by department having max(marks) > 90;


-- 43.	Find the number of students scoring above 80 in each department. 

select department, count(student_id) score from students
where marks > 80 group by department;



-- 44.	Find departments where more than 3 students scored above 75. 

select department, count(student_id) total_students from students 
where marks > 75 group by department having count(student_id) > 3;



-- 45.	Display departments ordered by highest mark in descending order. 

select department, max(marks) highest_marks from students
group by department order by max(marks) desc;















