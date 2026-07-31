
-- VEHICLE MANAGEMENT SYSTEM
-- =============================

create schema vehicle_project;

use vehicle_project;

create table customers (
	customer_id int auto_increment primary key,
    customer_name varchar(50),
    phone varchar(10),
    city varchar(50)
);

INSERT INTO customers(customer_name,phone,city)VALUES('Rahul','9876543210','Chennai'), 
('Priya','9876543211','Bangalore'), ('Arun','9876543212','Hyderabad'), 
('Sneha','9876543213','Coimbatore'), ('Karthik','9876543214','Mumbai');

create table vehicles (
	vehicle_id int auto_increment primary key,
    customer_id int,
    vehicle_number varchar(30),
    vehicle_model varchar(30),
    vehicle_type varchar(30),
    foreign key(customer_id) references customers(customer_id)
);

INSERT INTO Vehicles(customer_id,vehicle_number,vehicle_model,vehicle_type)VALUES(1,'TN10AB1234','Hyundai i20','Car'), 
(2,'KA05XY5678','Honda City','Car'), (3,'TS08PQ4321','Royal Enfield','Bike'),
 (4,'TN22KL9090','Maruti Swift','Car'), (5,'MH12AA1111','TVS Apache','Bike');
 
 
create table mechanics (
	mechanic_id int auto_increment primary key,
    mechanic_name varchar(50),
    specialization varchar(30),
    experience int
);

INSERT INTO Mechanics(mechanic_name,specialization,experience)VALUES('Ramesh','Engine',10), ('Suresh','Electrical',8), 
('Mahesh','General Service',6), ('Ganesh','Painting',12);


create table service_records (
	service_id int auto_increment primary key,
    vehicle_id int,
    mechanic_id int,
    service_type varchar(50),
    service_date varchar(30),
    cost int,
    foreign key(vehicle_id) references vehicles(vehicle_id),
    foreign key(mechanic_id) references mechanics(mechanic_id)
);

INSERT INTO Service_Records(vehicle_id,mechanic_id,service_type,service_date,cost)VALUES(1,1,'Engine Repair','2026-07-10',8000), 
(1,2,'Electrical Repair','2026-07-20',3000), (2,3,'General Service','2026-07-21',2500), (3,1,'Engine Repair','2026-07-15',5000), 
(4,3,'General Service','2026-07-18',2200), (5,2,'Electrical Repair','2026-06-30',1800), (2,4,'Painting','2026-07-25',7000), 
(3,3,'General Service','2026-07-27',2000);

create table bills (
	bill_id int auto_increment primary key,
    service_id int,
    total_amount int,
    payment_status varchar(20),
    foreign key(service_id) references service_records(service_id)
);


INSERT INTO Bills(service_id,total_amount,payment_status)VALUES(1,8000,'Paid'), (2,3000,'Paid'), (3,2500,'Pending'), 
(4,5000,'Paid'), (5,2200,'Paid'), (6,1800,'Pending'), (7,7000,'Paid'), (8,2000,'Pending');


select * from customers;
select * from vehicles;
select * from mechanics;
select * from service_records;
select * from bills;


-- QUERIES :::
-- ==============

-- 1. Find the mechanic handling the most services.

select m.mechanic_name, count(s.service_id) most_services from mechanics m join service_records s 
on m.mechanic_id = s.mechanic_id group by m.mechanic_name order by count(s.service_id) desc limit 1;


-- 2. Display service history for a vehicle.Vehicle Number = TN10AB1234

select s.service_type, s.service_date, s.cost, v.vehicle_number, v.vehicle_model, v.vehicle_type from vehicles v join service_records s 
on s.vehicle_id = v.vehicle_id where v.vehicle_number = 'TN10AB1234';


-- 3. Count services by type.

select s.service_type, count(v.vehicle_type) count_services from vehicles v join service_records s 
on v.vehicle_id = s.vehicle_id group by s.service_type;


-- 4. Find customers with more than three visits.

select c.customer_name, count(s.service_id) total_visits from customers c 
join vehicles v on c.customer_id = v.customer_id
join service_records s on v.vehicle_id = s.vehicle_id
group by c.customer_name order by count(s.service_id) desc;



-- 5. Display pending payments.

select * from bills where payment_status = 'pending';



-- 6. Find the most common service.

select service_type, count(service_type) common_service from service_records 
group by service_type order by count(service_type) desc;


-- 7. Find the highest bill.

select * from bills where total_amount = (select max(total_amount) from bills);


-- 8. Find the mechanic with the highest revenue.

select m.mechanic_name, sum(b.total_amount) highest_revenue from mechanics m
join service_records s on m.mechanic_id = s.mechanic_id
join bills b on b.service_id = s.service_id
group by m.mechanic_name;



-- 9. Find the average service cost

select avg(cost) average_service_cost from service_records;



-- 10. Find the most experienced mechanic.

select * from mechanics order by experience desc limit 1;




-- 11.Display all engine repairs.

select * from service_records where service_type = 'Engine Repair';




-- 12. Find customers from Chennai.

select * from customers where city = 'Chennai';
 















































