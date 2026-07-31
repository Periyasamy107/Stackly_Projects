-- STACKLY SQL PROJECTS :
-- =======================

-- 1) EMPLOYEE MANAGEMENT :::::
-- ******************************

create schema employee_project;

use employee_project;

CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50),
    department VARCHAR(30),
    salary DECIMAL(10,2),
    city VARCHAR(30),
    joining_date DATE
);

show tables;

select * from employee;

INSERT INTO Employee VALUES
(101,'John','IT',60000,'Chennai','2022-01-15'),
(102,'David','HR',45000,'Bangalore','2021-03-10'),
(103,'Smith','IT',70000,'Chennai','2020-07-12'),
(104,'Mary','Finance',55000,'Mumbai','2023-01-20'),
(105,'James','HR',48000,'Delhi','2022-05-05'),
(106,'Linda','Finance',65000,'Mumbai','2021-08-18'),
(107,'Arun','IT',72000,'Chennai','2021-04-12'),
(108,'Priya','HR',47000,'Bangalore','2022-06-25'),
(109,'Karthik','Sales',52000,'Coimbatore','2020-09-14'),
(110,'Meena','Sales',49000,'Coimbatore','2021-11-30'),
(111,'Ravi','Marketing',58000,'Hyderabad','2023-02-18'),
(112,'Anitha','Marketing',62000,'Hyderabad','2022-10-05'),
(113,'Suresh','Finance',69000,'Mumbai','2019-08-21'),
(114,'Divya','IT',81000,'Chennai','2020-03-15'),
(115,'Lokesh','HR',51000,'Delhi','2022-07-11'),
(116,'Harini','Finance',53000,'Mumbai','2021-12-19'),
(117,'Vignesh','Sales',61000,'Madurai','2020-01-08'),
(118,'Nisha','IT',76000,'Chennai','2019-05-27'),
(119,'Ganesh','Marketing',54000,'Pune','2021-09-09'),
(120,'Pooja','HR',46000,'Delhi','2023-04-16'),
(121,'Ajay','Finance',73000,'Mumbai','2020-06-22'),
(122,'Deepika','IT',68000,'Bangalore','2022-08-14'),
(123,'Mohan','Sales',45000,'Salem','2021-01-29'),
(124,'Keerthana','Marketing',59000,'Pune','2023-03-11'),
(125,'Akash','IT',88000,'Chennai','2018-12-05'),
(126,'Saranya','HR',49500,'Bangalore','2022-02-08'),
(127,'Bala','Finance',57000,'Chennai','2021-07-17'),
(128,'Aarthi','Sales',63000,'Madurai','2020-10-26'),
(129,'Manoj','Marketing',51000,'Hyderabad','2019-11-13'),
(130,'Sathish','IT',79000,'Chennai','2023-01-10'),
(131,'Anand','Finance',60000,'Mumbai','2020-04-04'),
(132,'Swetha','HR',52000,'Delhi','2021-05-15'),
(133,'Aravind','Sales',68000,'Coimbatore','2019-09-09'),
(134,'Kavitha','Marketing',56000,'Pune','2022-11-01'),
(135,'Prakash','IT',74000,'Bangalore','2021-03-22'),
(136,'Monika','Finance',82000,'Mumbai','2018-08-30'),
(137,'Naveen','Sales',47000,'Salem','2023-06-12'),
(138,'Reshma','HR',54000,'Bangalore','2020-12-24'),
(139,'Dinesh','IT',66000,'Chennai','2022-09-18'),
(140,'Shalini','Marketing',64000,'Hyderabad','2021-06-07'),
(141,'Rajesh','Finance',75000,'Chennai','2019-02-20'),
(142,'Harish','Sales',58000,'Madurai','2022-04-09'),
(143,'Akhil','IT',91000,'Chennai','2018-10-10'),
(144,'Anjali','HR',57000,'Delhi','2023-05-01'),
(145,'Ramesh','Finance',61000,'Mumbai','2020-07-28'),
(146,'Bhavani','Marketing',60000,'Pune','2021-10-19'),
(147,'Senthil','Sales',55000,'Coimbatore','2022-01-03'),
(148,'Lavanya','IT',85000,'Bangalore','2019-06-16'),
(149,'Abinaya','HR',50000,'Chennai','2020-08-25'),
(150,'Kishore','Finance',78000,'Mumbai','2021-02-14');


select * from employee;



-- 1.	Find the total number of employees in each department. 

select department, count(*) as total_count from employee group by department;


-- 2.	Find the average salary of employees in each department. 

select department, avg(salary) as avg_salary from employee group by department;


-- 3.	Display departments having more than one employee. 

select department, count(emp_name) as employee_count from employee 
group by department having  count(*) > 1;


-- 4.	Find the highest salary in each department. 

select department, max(salary) as maximum_salary from employee group by department;


-- 5.	Find the lowest salary in each department. 

select department, min(salary) as minimum_salary from employee group by department;


-- 6.	Find departments whose average salary is greater than 50,000. 

select department, avg(salary) as avg_salary_greater_than_50000 from employee 
group by department having avg(salary) > 50000;


-- 7.	Calculate the total salary expenditure for each department. 

select department, sum(salary) as total_expenditure from employee 
group by department;


-- 8.	Display all employees sorted by salary in descending order. 

select * from employee order by salary desc;


-- 9.	Display employees sorted first by department and then by salary in descending order. 

select * from employee order by department asc, salary desc;


-- 10.	Find cities that have more than one employee. 

select city, count(emp_name) total_employee from employee 
group by city having count(emp_name) > 1;


-- 11.	Find the total salary paid in each city. 

select city, sum(salary) as total_salary from employee group by city;


-- 12.	Display departments ordered by total salary expenditure from highest to lowest. 

select department, sum(salary) as total_expenditure from employee
group by department order by total_expenditure desc;


-- 13.	Find the number of employees in each department whose salary is greater than 50,000. 

select department, count(emp_id) as total_employees from employee 
where salary > 50000 group by department;


-- 14.	Find the difference between the highest and lowest salary in each department. 

select department, max(salary) - min(salary) as difference_salary 
from employee group by department;


-- 15.	Display the top 3 highest-paid employees. 

select emp_name, salary from employee order by salary desc limit 3;
















































