
-- STACKLY SQL PROJECTS :
-- =======================

-- 1) BANKING SCENARIO :::::
-- ****************************

-- 1) i) Customers : 
-- ------------------

create database bank_project;

show databases;

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    city VARCHAR(50)
);

show tables;

-- 1) ii) Accounts : 
-- ------------------

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_type VARCHAR(20),
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

show tables;


INSERT INTO customers (name, city) VALUES
('Arun Kumar', 'Chennai'),
('Priya Sharma', 'Bengaluru'),
('Rahul Verma', 'Hyderabad'),
('Sneha Reddy', 'Mumbai'),
('Karthik Raj', 'Coimbatore'),
('Divya Nair', 'Kochi'),
('Vikram Singh', 'Delhi'),
('Anjali Gupta', 'Pune'),
('Suresh Babu', 'Madurai'),
('Meena Lakshmi', 'Salem'),
('Ramesh Kumar', 'Trichy'),
('Pooja Mehta', 'Ahmedabad'),
('Ajith Kumar', 'Vellore'),
('Neha Agarwal', 'Jaipur'),
('Manoj Das', 'Bhubaneswar'),
('Lakshmi Priya', 'Tirunelveli'),
('Harish Kumar', 'Mysuru'),
('Keerthana S', 'Erode'),
('Naveen Kumar', 'Vijayawada'),
('Asha Devi', 'Visakhapatnam'),
('Rohit Sharma', 'Nagpur'),
('Deepika Rao', 'Mangalore'),
('Ganesh Kumar', 'Thanjavur'),
('Swetha R', 'Tiruppur'),
('Prakash R', 'Dharmapuri'),
('Saravanan M', 'Karur'),
('Kavitha R', 'Namakkal'),
('Bharath Kumar', 'Hosur'),
('Nandhini V', 'Cuddalore'),
('Sathish Kumar', 'Kanchipuram'),
('Revathi S', 'Thoothukudi'),
('Arvind Rao', 'Warangal'),
('Sowmya P', 'Guntur'),
('Mahesh Babu', 'Nellore'),
('Geetha Lakshmi', 'Kurnool'),
('Vinod Kumar', 'Belagavi'),
('Preethi N', 'Hubballi'),
('Ashok Kumar', 'Shimoga'),
('Bhavani Devi', 'Udupi'),
('Sanjay Patel', 'Surat'),
('Komal Shah', 'Vadodara'),
('Yash Mehta', 'Rajkot'),
('Ritika Jain', 'Indore'),
('Amit Tiwari', 'Lucknow'),
('Shalini Verma', 'Kanpur'),
('Rajesh Yadav', 'Varanasi'),
('Anita Mishra', 'Prayagraj'),
('Sunil Joshi', 'Dehradun'),
('Pallavi Kulkarni', 'Nashik'),
('Abhishek Singh', 'Noida'),
('Mohan Raj', 'Chengalpattu'),
('Kavya Sri', 'Dindigul'),
('Hari Prasad', 'Sivakasi'),
('Anu Priya', 'Virudhunagar'),
('Senthil Kumar', 'Krishnagiri'),
('Nisha Patel', 'Anand'),
('Kiran Reddy', 'Kadapa'),
('Lavanya Devi', 'Anantapur'),
('Sandeep Sharma', 'Bhopal'),
('Monika Gupta', 'Gwalior'),
('Ravi Chandra', 'Raipur'),
('Shweta Kulkarni', 'Aurangabad'),
('Gokul Raj', 'Pollachi'),
('Sangeetha M', 'Nagapattinam'),
('Pradeep Kumar', 'Kumbakonam'),
('Reshma K', 'Palakkad'),
('Dinesh Kumar', 'Tiruvannamalai'),
('Vaishnavi R', 'Pudukkottai'),
('Lokesh Kumar', 'Ramanathapuram'),
('Anitha S', 'Nagercoil'),
('Nitin Agarwal', 'Agra'),
('Pavithra N', 'Karaikudi'),
('Srinivas Rao', 'Khammam'),
('Harini V', 'Ooty'),
('Vigneshwaran R', 'Kallakurichi'),
('Aravind S', 'Kanyakumari'),
('Keerthi Bala', 'Perambalur'),
('Murugan T', 'Ariyalur'),
('Gayathri R', 'Mayiladuthurai'),
('Vasanth Kumar', 'Tenkasi'),
('Priyanka S', 'Sankarankovil'),
('Raghav Sharma', 'Chandigarh'),
('Neelam Verma', 'Patna'),
('Aditya Mishra', 'Ranchi'),
('Pankaj Singh', 'Jamshedpur'),
('Kiran Joshi', 'Ujjain'),
('Sushmita Roy', 'Kolkata'),
('Tarun Kapoor', 'Amritsar'),
('Ananya Bose', 'Siliguri'),
('Rohini Devi', 'Alappuzha'),
('Naveen Raj', 'Thrissur'),
('Kishore Kumar', 'Kozhikode'),
('Megha Sharma', 'Panaji'),
('Arjun Menon', 'Margao'),
('Divakar R', 'Puducherry'),
('Sathya Priya', 'Villupuram'),
('Nirmal Raj', 'Chidambaram'),
('Harsha Vardhan', 'Eluru'),
('Ishita Jain', 'Jodhpur'),
('Balaji K', 'Dharmapuri');





INSERT INTO accounts (customer_id, account_type, balance) VALUES
(1, 'Savings', 15000.00),
(2, 'Current', 32000.50),
(3, 'Savings', 45500.75),
(4, 'Savings', 12000.00),
(5, 'Current', 87500.00),
(6, 'Savings', 23450.60),
(7, 'Current', 99000.25),
(8, 'Savings', 17890.00),
(9, 'Savings', 64000.40),
(10, 'Current', 28000.90),
(11, 'Savings', 5400.75),
(12, 'Current', 75000.00),
(13, 'Savings', 18400.20),
(14, 'Savings', 36000.00),
(15, 'Current', 125000.00),
(16, 'Savings', 9600.50),
(17, 'Current', 52000.75),
(18, 'Savings', 43000.25),
(19, 'Savings', 27800.00),
(20, 'Current', 88000.10),
(21, 'Savings', 14000.00),
(22, 'Current', 67000.65),
(23, 'Savings', 9200.30),
(24, 'Savings', 48300.80),
(25, 'Current', 102500.00),
(26, 'Savings', 26500.00),
(27, 'Current', 48000.50),
(28, 'Savings', 71000.75),
(29, 'Savings', 15900.00),
(30, 'Current', 84500.25),
(31, 'Savings', 30500.80),
(32, 'Current', 98500.40),
(33, 'Savings', 21250.00),
(34, 'Savings', 43800.60),
(35, 'Current', 56000.00),
(36, 'Savings', 11800.20),
(37, 'Current', 132000.00),
(38, 'Savings', 28750.30),
(39, 'Savings', 36600.75),
(40, 'Current', 75400.90),
(41, 'Savings', 22150.00),
(42, 'Current', 64800.25),
(43, 'Savings', 17500.45),
(44, 'Savings', 53900.00),
(45, 'Current', 91500.10),
(46, 'Savings', 24700.75),
(47, 'Current', 120500.00),
(48, 'Savings', 19400.50),
(49, 'Savings', 82000.30),
(50, 'Current', 150000.00),
(51, 'Savings', 18450.00),
(52, 'Current', 92500.75),
(53, 'Savings', 46800.20),
(54, 'Savings', 13500.00),
(55, 'Current', 118000.50),
(56, 'Savings', 29600.90),
(57, 'Current', 74300.00),
(58, 'Savings', 15250.75),
(59, 'Current', 86400.40),
(60, 'Savings', 33800.30),
(61, 'Current', 104500.60),
(62, 'Savings', 22300.80),
(63, 'Savings', 57100.00),
(64, 'Current', 137500.25),
(65, 'Savings', 41600.50),
(66, 'Current', 68800.10),
(67, 'Savings', 9800.00),
(68, 'Savings', 25400.45),
(69, 'Current', 83200.90),
(70, 'Savings', 44750.00),
(71, 'Current', 126000.00),
(72, 'Savings', 16200.35),
(73, 'Current', 59800.70),
(74, 'Savings', 27100.25),
(75, 'Current', 145800.00),
(76, 'Savings', 18750.00),
(77, 'Current', 62500.50),
(78, 'Savings', 29300.75),
(79, 'Savings', 14200.00),
(80, 'Current', 98500.25),
(81, 'Savings', 36400.90),
(82, 'Current', 118750.00),
(83, 'Savings', 21400.35),
(84, 'Current', 79250.60),
(85, 'Savings', 50600.45),
(86, 'Current', 133800.00),
(87, 'Savings', 24800.00),
(88, 'Current', 69100.80),
(89, 'Savings', 17950.25),
(90, 'Current', 84500.90),
(91, 'Savings', 38700.00),
(92, 'Current', 129400.50),
(93, 'Savings', 15600.40),
(94, 'Current', 97800.00),
(95, 'Savings', 46250.30),
(96, 'Current', 111600.75),
(97, 'Savings', 20350.00),
(98, 'Current', 72100.20),
(99, 'Savings', 33950.60),
(100, 'Current', 150250.00);


select * from accounts;
select * from customers;





-- 1.	Retrieve all accounts with balance greater than 20,000. 

select * from accounts where balance > 20000;




-- 2.	Find customers who live in Chennai. 

select * from customers where city = 'chennai';



-- 3.	Display accounts with balance between 20,000 and 50,000. 

select * from accounts where balance between 20000 and 50000;



-- 4.	Find customers whose names start with 'M'. 

select * from customers where name like 'm%';



-- 5.	Retrieve accounts of type 'Savings' or 'Current'. 

select * from accounts where account_type = 'savings' or account_type = 'current';



-- 6.	Display accounts that are not 'Savings'. 

select * from accounts where account_type != 'Savings';



-- 7.	Find customers whose names contain the letter 'a'. 

select * from customers where name like '%a%';



-- 8.	Retrieve accounts with balance less than or equal to 30,000. 

select * from accounts where balance <= 30000;



-- 9.	Find customers who are not from Chennai. 

select * from customers where city not in ('Chennai');



-- 10.	Display accounts where balance is not between 10,000 and 40,000. 

select * from accounts where balance not between 10000 and 40000;



-- 11.	Retrieve customers whose names end with 'i'. 

select * from customers where name like '%i';



-- 12.	Find accounts with balance equal to 75,000. 

select * from accounts where balance = 75000;



-- 13.	Display customers whose city is either Chennai or Salem. 

select * from customers where city in ('Chennai', 'Salem');



-- 14.	Find accounts with balance greater than 10,000 and less than 40,000. 

select * from accounts where balance > 10000 and balance < 40000;



-- 15.	Retrieve accounts where account type is not in ('Current'). 

select * from accounts where account_type not in ('Current');



-- 16.	Display all accounts sorted by balance in descending order. 

select * from accounts order by balance desc;



-- 17.	List customers sorted alphabetically by name. 

select * from customers order by name asc;



-- 18.	Display accounts sorted by account type and then by balance (descending). 

select * from accounts order by account_type desc, balance desc;



-- 19.	Find the total balance of all accounts. 

select sum(balance) as 'Total_Balance' from accounts;



-- 20.	Calculate the average balance of accounts. 

select avg(balance) as 'Average_Balance' from accounts;



-- 21.	Find the maximum account balance. 

select max(balance) as 'Maximum_Balance' from accounts;



-- 22.	Find the minimum account balance. 

select min(balance) 'Minimum_Balance' from accounts;



-- 23.	Count the total number of customers. 

select count(*) 'Total_Customers' from customers;



-- 24.	Find total balance grouped by account type. 

select account_type, sum(balance) 'total_balance' from accounts group by account_type;



-- 25.	Find average balance for each account type. 

select account_type, avg(balance) 'average_balance' from accounts group by account_type;



-- 26.	Display account types having average balance greater than 20,000. 

select account_type, avg(balance) 'average_balance' from accounts group by account_type having avg(balance) > 20000;



-- 27.	Count number of accounts for each customer. 

select customer_id, count(account_id) 'total_accounts' from accounts group by customer_id;



-- 28.	Display customers having more than one account. 

select customer_id, count(account_id) total_accounts from accounts 
group by customer_id having count(account_id) > 1;


-- 29.	Retrieve customer names along with their account balances. 

select c.name, a.balance from customers c join accounts a on c.customer_id = a.customer_id;


-- 30.	Display all customers and their accounts (including customers without accounts).
 
select c.customer_id, c.name, c.city,a.account_id, a.account_type, a.balance 
from customers c left join accounts a on c.customer_id = a.customer_id;


-- 31.	Display all accounts and corresponding customer details. 

select c.customer_id, c.name, c.city, a.account_id, a.account_type, a.balance 
from customers c inner join accounts a on c.customer_id = a.customer_id;


-- 32.	Retrieve customer names and account types where balance is greater than 20,000. 

select c.name, a.account_type from customers c join accounts a
on c.customer_id = a.customer_id where a.balance > 20000;


-- 33.	List customers with their total balance using JOIN. 

select c.name, sum(a.balance) as total_balance from customers c join accounts a 
on a.customer_id = c.customer_id group by c.name;


-- 34.	Display customer names and balances sorted by balance. 

select c.name, a.balance from customers c join accounts a 
on a.customer_id = c.customer_id order by balance asc;


-- 35.	Count number of accounts for each city using JOIN. 

select c.city, count(a.account_id) as total_city_accounts from customers c join accounts a 
on c.customer_id = a.customer_id group by c.city;


-- 36.	Find accounts with balance greater than average balance. 

select * from accounts where balance > (select avg(balance) from accounts);


-- 37.	Retrieve customers who have accounts. 

select * from customers where customer_id in (select customer_id from accounts);



-- 38.	Find customers who do not have any accounts. 

select * from customers where customer_id not in (select customer_id from accounts);


-- 39.	Display account(s) with the maximum balance. 

select * from accounts where balance = ( select max(balance) from accounts);


-- 40.	Find customers whose total balance is greater than 40,000. 

select * from customers where customer_id in (select customer_id from accounts where balance > 40000);

