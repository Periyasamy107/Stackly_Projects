
-- STACKLY SQL PROJECTS :
-- =======================

-- 2) RAILWAY RESERVATION SCENARIO :::::
-- ***************************************

create schema railway_project;

use railway_project;

-- 2) i) Trains : 
-- ------------------

create table trains (
	train_id int primary key auto_increment,
    train_name varchar(50),
    source varchar(50),
    destination varchar(50)
);

select * from trains;


INSERT INTO trains (train_name, source, destination) VALUES
('Express1', 'Chennai', 'Madurai'),
('Express2', 'Coimbatore', 'Salem'),
('Express3', 'Madurai', 'Chennai'),
('Express4', 'Chennai', 'Coimbatore'),
('Express5', 'Salem', 'Bengaluru'),
('Express6', 'Chennai', 'Trichy'),
('Express7', 'Trichy', 'Madurai'),
('Express8', 'Madurai', 'Coimbatore'),
('Express9', 'Bengaluru', 'Chennai'),
('Express10', 'Chennai', 'Hyderabad'),
('Express11', 'Hyderabad', 'Vijayawada'),
('Express12', 'Vijayawada', 'Visakhapatnam'),
('Express13', 'Chennai', 'Tirunelveli'),
('Express14', 'Salem', 'Erode'),
('Express15', 'Erode', 'Coimbatore'),
('Express16', 'Chennai', 'Puducherry'),
('Express17', 'Puducherry', 'Chennai'),
('Express18', 'Madurai', 'Rameswaram'),
('Express19', 'Rameswaram', 'Madurai'),
('Express20', 'Chennai', 'Mumbai'),
('Express21', 'Mumbai', 'Pune'),
('Express22', 'Pune', 'Goa'),
('Express23', 'Goa', 'Mangaluru'),
('Express24', 'Chennai', 'Kanyakumari'),
('Express25', 'Kanyakumari', 'Chennai'),
('Express26', 'Coimbatore', 'Chennai'),
('Express27', 'Chennai', 'Thanjavur'),
('Express28', 'Thanjavur', 'Trichy'),
('Express29', 'Trichy', 'Chennai'),
('Express30', 'Chennai', 'Nagapattinam'),
('Express31', 'Nagapattinam', 'Mayiladuthurai'),
('Express32', 'Mayiladuthurai', 'Chidambaram'),
('Express33', 'Chidambaram', 'Chennai'),
('Express34', 'Chennai', 'Villupuram'),
('Express35', 'Villupuram', 'Cuddalore'),
('Express36', 'Cuddalore', 'Puducherry'),
('Express37', 'Chennai', 'Vellore'),
('Express38', 'Vellore', 'Bengaluru'),
('Express39', 'Bengaluru', 'Mysuru'),
('Express40', 'Mysuru', 'Chennai'),
('Express41', 'Chennai', 'Dharmapuri'),
('Express42', 'Dharmapuri', 'Salem'),
('Express43', 'Salem', 'Namakkal'),
('Express44', 'Namakkal', 'Karur'),
('Express45', 'Karur', 'Trichy'),
('Express46', 'Chennai', 'Tirupati'),
('Express47', 'Tirupati', 'Chennai'),
('Express48', 'Chennai', 'Vijayawada'),
('Express49', 'Vijayawada', 'Secunderabad'),
('Express50', 'Secunderabad', 'Chennai');

select * from trains;

-- 2) i) Bookings : 
-- ------------------

create table bookings (
	booking_id int primary key auto_increment,
    train_id int, 
    passenger_name varchar(50),
    fare decimal(10,2),
    status varchar(20),
    foreign key(train_id) references trains(train_id)
);

select * from bookings;

INSERT INTO bookings (train_id, passenger_name, fare, status) VALUES
(1, 'Arun', 500.00, 'Confirmed'),
(1, 'Janani', 500.00, 'Waiting'),
(2, 'Priya', 300.00, 'Confirmed'),
(2, 'Meena', 350.00, 'Confirmed'),
(3, 'Karthik', 450.00, 'Cancelled'),
(3, 'Akash', 600.00, 'Confirmed'),
(4, 'Divya', 420.00, 'Confirmed'),
(5, 'Anitha', 250.00, 'Waiting'),
(5, 'Suresh', 275.00, 'Confirmed'),
(6, 'Ajay', 700.00, 'Confirmed'),
(7, 'Ravi', 380.00, 'Cancelled'),
(8, 'Aarthi', 450.00, 'Confirmed'),
(8, 'Vignesh', 450.00, 'Waiting'),
(9, 'Naveen', 520.00, 'Confirmed'),
(10, 'Ashwin', 800.00, 'Confirmed'),
(10, 'Pooja', 500.00, 'Confirmed'),
(11, 'Bala', 310.00, 'Waiting'),
(12, 'Aravind', 480.00, 'Confirmed'),
(13, 'Deepika', 390.00, 'Cancelled'),
(14, 'Anand', 650.00, 'Confirmed'),
(15, 'Keerthana', 340.00, 'Confirmed'),
(15, 'Anjali', 900.00, 'Confirmed'),
(16, 'Harish', 560.00, 'Waiting'),
(17, 'Akhil', 430.00, 'Confirmed'),
(18, 'Monika', 290.00, 'Cancelled'),
(18, 'Abishek', 320.00, 'Confirmed'),
(19, 'Saranya', 410.00, 'Confirmed'),
(20, 'Ajith', 550.00, 'Waiting'),
(20, 'Nithya', 550.00, 'Confirmed'),
(21, 'Arun Kumar', 360.00, 'Confirmed'),
(22, 'Lokesh', 280.00, 'Cancelled'),
(23, 'Aishwarya', 470.00, 'Confirmed'),
(24, 'Manoj', 500.00, 'Confirmed'),
(25, 'Arthi', 390.00, 'Waiting'),
(25, 'Gokul', 390.00, 'Confirmed'),
(26, 'Anand Raj', 610.00, 'Confirmed'),
(27, 'Ramesh', 430.00, 'Cancelled'),
(28, 'Abinaya', 340.00, 'Confirmed'),
(29, 'Sathish', 295.00, 'Waiting'),
(30, 'Aravind Kumar', 900.00, 'Confirmed'),
(30, 'Priyanka', 900.00, 'Confirmed'),
(31, 'Balaji', 460.00, 'Confirmed'),
(32, 'Anusha', 510.00, 'Waiting'),
(33, 'Mohan', 375.00, 'Confirmed'),
(34, 'Akila', 445.00, 'Confirmed'),
(35, 'Senthil', 330.00, 'Cancelled'),
(36, 'Anupriya', 580.00, 'Confirmed'),
(37, 'Dinesh', 260.00, 'Waiting'),
(38, 'Arjun', 490.00, 'Confirmed'),
(40, 'Kavitha', 520.00, 'Confirmed');

select * from bookings;

-- 41.	Retrieve all bookings with fare greater than 400. 

select * from bookings where fare > 400;


-- 42.	Find bookings where status is not 'Confirmed'. 

select * from bookings where status != 'Confirmed';


-- 43.	Display trains starting from Chennai. 

select * from trains where source = 'Chennai';


-- 44.	Retrieve bookings with fare between 300 and 500. 

select * from bookings where fare between 300 and 500;


-- 45.	Find passengers whose names start with 'A'. 

select * from bookings where passenger_name like 'A%';


-- 46.	Retrieve train names along with passenger names. 

select t.train_name, b.passenger_name from trains t join bookings b 
on t.train_id = b.train_id;


-- 47.	Count number of bookings for each train. 

select t.train_name, count(b.booking_id) as no_of_bookings from trains t join bookings b
on t.train_id = b.train_id group by t.train_name;

-- 48.	Display train names and total fare collected for each train. 

select t.train_name, sum(b.fare) as total_fare from trains t join bookings b 
on t.train_id = b.train_id group by t.train_name;


-- 49.	Find bookings with fare equal to the highest fare. 

select * from bookings where fare = (select max(fare) from bookings);


-- 50.	Retrieve trains that have more than one booking. 

select * from trains where train_id in (
select train_id from bookings group by train_id 
having count(*) > 1);




















