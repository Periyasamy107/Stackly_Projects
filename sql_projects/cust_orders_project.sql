-- STACKLY SQL PROJECTS :
-- =======================

-- 1) CUSTOMERS PROJECT :::::
-- ******************************

create schema cust_orders_project;

use cust_orders_project;

create table customers (
	customer_id int primary key,
    customer_name varchar(50),
    city varchar(30)
);

INSERT INTO Customers VALUES
(101,'Arun Kumar','Chennai'),
(102,'Priya Sharma','Bangalore'),
(103,'Rahul Verma','Hyderabad'),
(104,'Sneha Reddy','Mumbai'),
(105,'Karthik Raj','Coimbatore'),
(106,'Divya Nair','Kochi'),
(107,'Vikram Singh','Delhi'),
(108,'Anjali Gupta','Pune'),
(109,'Suresh Babu','Madurai'),
(110,'Meena Lakshmi','Salem'),
(111,'Ramesh Kumar','Trichy'),
(112,'Pooja Mehta','Ahmedabad'),
(113,'Ajith Kumar','Vellore'),
(114,'Neha Agarwal','Jaipur'),
(115,'Manoj Das','Bhubaneswar'),
(116,'Lakshmi Priya','Tirunelveli'),
(117,'Harish Kumar','Mysuru'),
(118,'Keerthana S','Erode'),
(119,'Naveen Kumar','Vijayawada'),
(120,'Asha Devi','Visakhapatnam'),
(121,'Rohit Sharma','Nagpur'),
(122,'Deepika Rao','Mangalore'),
(123,'Ganesh Kumar','Thanjavur'),
(124,'Swetha R','Tiruppur'),
(125,'Prakash R','Dharmapuri'),
(126,'Saravanan M','Karur'),
(127,'Kavitha R','Namakkal'),
(128,'Bharath Kumar','Hosur'),
(129,'Nandhini V','Cuddalore'),
(130,'Sathish Kumar','Kanchipuram'),
(131,'Revathi S','Thoothukudi'),
(132,'Aravind Rao','Warangal'),
(133,'Sowmya P','Guntur'),
(134,'Mahesh Babu','Nellore'),
(135,'Geetha Lakshmi','Kurnool'),
(136,'Vinod Kumar','Belagavi'),
(137,'Preethi N','Hubballi'),
(138,'Ashok Kumar','Shimoga'),
(139,'Bhavani Devi','Udupi'),
(140,'Sanjay Patel','Surat'),
(141,'Komal Shah','Vadodara'),
(142,'Yash Mehta','Rajkot'),
(143,'Ritika Jain','Indore'),
(144,'Amit Tiwari','Lucknow'),
(145,'Shalini Verma','Kanpur'),
(146,'Rajesh Yadav','Varanasi'),
(147,'Anita Mishra','Prayagraj'),
(148,'Sunil Joshi','Dehradun'),
(149,'Pallavi Kulkarni','Nashik'),
(150,'Abhishek Singh','Noida');

select * from customers;



-- 2) ORDERS PROJECT :::::
-- ******************************

create table orders (
	order_id int primary key,
    customer_id int,
    amount decimal(10,2),
    order_date date,
    foreign key(customer_id) references customers(customer_id)
);

INSERT INTO Orders VALUES
(1001,101,2500.00,'2024-01-05'),
(1002,101,3200.00,'2024-01-18'),
(1003,101,2800.00,'2024-02-02'),
(1004,101,2200.00,'2024-02-20'),
(1005,102,1800.00,'2024-01-10'),
(1006,102,2100.00,'2024-02-15'),
(1007,103,4500.00,'2024-01-08'),
(1008,103,3800.00,'2024-02-12'),
(1009,103,2900.00,'2024-03-01'),
(1010,104,5200.00,'2024-01-25'),
(1011,104,4100.00,'2024-02-27'),
(1012,105,1500.00,'2024-01-14'),
(1013,105,1800.00,'2024-03-08'),
(1014,106,2600.00,'2024-01-22'),
(1015,106,2700.00,'2024-02-14'),
(1016,107,3400.00,'2024-01-17'),
(1017,107,3600.00,'2024-02-18'),
(1018,108,6200.00,'2024-01-09'),
(1019,109,2900.00,'2024-02-06'),
(1020,109,3300.00,'2024-03-11'),
(1021,110,4100.00,'2024-01-30'),
(1022,110,2400.00,'2024-02-28'),
(1023,111,5500.00,'2024-03-05'),
(1024,112,4800.00,'2024-02-09'),
(1025,113,3100.00,'2024-03-15'),
(1026,114,3900.00,'2024-01-12'),
(1027,114,2500.00,'2024-02-21'),
(1028,115,4600.00,'2024-01-19'),
(1029,115,4200.00,'2024-03-03'),
(1030,116,5100.00,'2024-02-11'),
(1031,117,2700.00,'2024-01-27'),
(1032,117,2900.00,'2024-02-25'),
(1033,118,6100.00,'2024-01-06'),
(1034,119,2300.00,'2024-01-29'),
(1035,119,2800.00,'2024-03-07'),
(1036,120,7200.00,'2024-02-05'),
(1037,121,3100.00,'2024-01-15'),
(1038,121,3300.00,'2024-02-13'),
(1039,122,4400.00,'2024-01-24'),
(1040,123,2600.00,'2024-02-08'),
(1041,124,1900.00,'2024-01-20'),
(1042,124,2100.00,'2024-03-10'),
(1043,125,5300.00,'2024-02-17'),
(1044,126,3500.00,'2024-01-11'),
(1045,127,2800.00,'2024-02-16'),
(1046,128,6400.00,'2024-01-31'),
(1047,129,3000.00,'2024-02-19'),
(1048,130,4500.00,'2024-03-09'),
(1049,131,3900.00,'2024-02-22'),
(1050,132,2500.00,'2024-03-18');

select * from orders;


-- 16.	Find the total order amount for each customer. 

select c.customer_name, sum(o.amount) as total_orders_amount
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name;


-- 17.	Find customers who have placed more than 1 orders. 

select c.customer_name, count(o.order_id) as placed_orders 
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name having count(o.order_id) > 1;


-- 18.	Find the average order amount for each customer. 

select c.customer_name, avg(o.amount) as average_order_amount
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name;


-- 19.	Find the highest order amount placed by each customer. 

select c.customer_name, max(o.amount) as highest_order 
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name;


-- 20.	Display customers sorted by their total purchase amount. 

select c.customer_name, sum(o.amount) as total_purchase_amount 
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name order by sum(o.amount) asc;


-- 21.	Find customers whose total purchase amount exceeds 10,000. 

select c.customer_name, sum(o.amount) as total_purchase_amount
from customers c join orders o on c.customer_id = o.customer_id 
group by c.customer_name having sum(o.amount) > 10000;


-- 22.	Display customer names along with the total number of orders placed. 

select c.customer_name, count(o.order_id) as total_order_placed
from customers c left join orders o on c.customer_id = o.customer_id 
group by c.customer_name;


-- 23.	Find the customer who spent the highest amount. 

select c.customer_name, sum(o.amount) as highest_spender
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name order by sum(o.amount) desc limit 1;


-- 24.	Find the customer who placed the maximum number of orders. 

select c.customer_name, count(o.order_id) as maximum_order_placer 
from customers c join orders o on c.customer_id = o.customer_id 
group by c.customer_name limit 1;


-- 25.	Find customers whose average order amount is greater than 2,000. 

select c.customer_name, avg(o.amount) as average_amount 
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name having avg(o.amount) > 2000;


-- 26.	Display the top 5 customers based on total purchase amount. 

select c.customer_name, sum(o.amount) as total_purchase_amount
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name order by sum(o.amount) desc limit 5;


-- 27.	Find the minimum order amount for each customer. 

select c.customer_name, min(o.amount) minimum_order_amount 
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name;


-- 28.	Find customers who have placed orders worth more than 5,000 in total. 

select c.customer_name, sum(o.amount) as order_placed
from customers c join orders o on c.customer_id = o.customer_id
group by c.customer_name having sum(o.amount) > 5000;


-- 29.	Display customer-wise total orders and total purchase amount. 

select c.customer_name, count(o.order_id) total_orders, sum(o.amount) total_purchase_amount
from customers c join orders o on c.customer_id = o.customer_id group by c.customer_name;


-- 30.	Find customers who placed more than 2 orders and spent more than 8,000. 

select c.customer_name, count(o.order_id) total_orders, sum(o.amount) total_purchase_amount
from customers c join orders o on c.customer_id = o.customer_id 
group by c.customer_name having count(o.order_id) > 2 and sum(o.amount) > 8000;






















