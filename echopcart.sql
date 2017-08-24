
-- Customer Table
create table Customer
(
  UserEmail varchar(40),
  Password varchar(20),
  FName varchar(15),
  MInit varchar(1),
  LName varchar(25),
  constraint Customer_PK primary key (UserEmail)
);

--Recipient table
create table Recipient
(
  RecipientID int IDENTITY(1,1),
  UserEmail varchar(40),
  Name varchar(35),
  StreetAddr varchar(50) ,
  Zip varchar(10),
  Country char(2),
  State char(2),
  City varchar(15),

  constraint Recipient_PK primary key (RecipientID),
  constraint Recipient_Customer_UserEmail foreign key (UserEmail) references Customer(UserEmail)
);


--User payments/types
create table Payment
(
  PaymentID int,
  Alias varchar(25),
  PaymentType varchar(6),
  UserEmail varchar(40),
  constraint Payment_PK primary key (PaymentID),
  constraint Payment_Customer_UserEmail foreign key (UserEmail) references Customer(UserEmail)
);

create table Card
(
  CardNum numeric(16,0),
  EXPDate date,
  HolderName varchar(35),
  CVV numeric(3,0),
  PaymentID int,
  constraint Card_Payment_PaymentID foreign key (PaymentID) references Payment(PaymentID),
  constraint Card_PK primary key (PaymentID)
);

create table ECheck
(
  AccountNum int,
  RoutingNum int,
  PaymentID int,
  constraint ECheck_Payment_PaymentID foreign key (PaymentID) references Payment(PaymentID),
  constraint ECheck_PK primary key (PaymentID)
);

create table CustOrder
(
  OrderID int,
  Date date,
  constraint CustOrder_PK primary key (OrderID)
);

create table Vendor
(
  ID int,
  Name varchar(25),
  URL varchar(45),
  constraint Vendor_PK primary key (ID)
);

create table AuthorizedOrders
(
  UserEmail varchar(40),
  RecipientID int,
  PaymentID int,
  OrderID int,
  constraint AuthorizedOrders_PK primary key (OrderID),
  constraint AuthorizedOrders_Customer_UserEmail foreign key (UserEmail) references Customer(UserEmail),
  constraint AuthorizedOrders_Recipient_RecipientID foreign key (RecipientID) references Recipient(RecipientID),
  constraint AuthorizedOrders_Payment_PaymentID foreign key (PaymentID) references Payment(PaymentID),
  constraint AuthorizedOrders_CustOrder_OrderID foreign key (OrderID) references CustOrder(OrderID)
);

create table Item
(
  Price money,
  Name varchar(40),
  SKU int,
  VendorID int,
  constraint Item_PK primary key (SKU,VendorID),
  constraint Item_Vender_ID foreign key (VendorID) references Vendor(ID)
);

create table ItemOrder
(
  Quantity int,
  OrderID int,
  SKU int,
  VendorID int,
  constraint ItemOrder_PK primary key (OrderID,SKU,VendorID),
  constraint ItemOrder_CustOrder_OrderID foreign key (OrderID) references CustOrder(OrderID),
  constraint ItemOrder_Item_SKU_VendorID foreign key (SKU, VendorID) references Item(SKU, VendorID)
);

--https://www.mockaroo.com/
--Used for random data generation

--Initial Data Population

--Customer
insert into Customer values ('bstrangwood0@linkedin.com','NsmiPkv', 'Simonne', 'B', 'Strangwood'); --
insert into Customer values ('itomaszczyk1@fotki.com','XkV7WMP1', 'Owen', 'I', 'Tomaszczyk'); --
insert into Customer values ('dmaslin2@tumblr.com', 'US0JFIzb', 'Carol', 'D', 'Maslin'); --
insert into Customer values ('gtedman3@flavors.me', 'vOiCsq', 'Wilbur', 'G', 'Tedman'); --
insert into Customer values ('akleen4@cafepress.com', 'UTX63zMKZo', 'Sid', 'A', 'Kleen'); --
insert into Customer values ('gcolling5@stumbleupon.com', '3ITmHoWHcsGe', 'Ganny','R',  'Colling');--
insert into Customer values ('bbattany6@surveymonkey.com', 'G9EBuR', 'Brod','D',  'Battany'); --
insert into Customer values ('cbernaciak7@amazon.de', 'u4YiJFPJS', 'Alexis', 'C', 'Bernaciak'); --
insert into Customer values ('erackley8@hatena.ne.jp','a1IFxqB0v3l', 'Waite', 'E', 'Rackley');--
insert into Customer values ('djaquin9@comcast.net','cfj0zlekz9', 'Aviva', 'D', 'Jaquin');

--Vendor
insert into Vendor values (145, 'Botsford-Tremblay', 'http://botsfordtremblay.com');
insert into Vendor values (256, 'Paucek Inc', 'https://paucek.com');
insert into Vendor values (3758, 'Dibbert Group', 'https://dibbertgroup.net');
insert into Vendor values (4456, 'Crist and Sons', 'https://cristandsons.gov');
insert into Vendor values (5654, 'Fisher Inc', 'http://fisher.com');
insert into Vendor values (689, 'Prohaska', 'https://phm.com');
insert into Vendor values (77854, 'Auer-Hahn', 'http://auer-hahn.org');
insert into Vendor values (87, 'Carter', 'https://cjb.com');
insert into Vendor values (995, 'Botsford-Daugherty', 'http://botsford-daugherty.com');
insert into Vendor values (10453, 'Torp-Mante', 'https://torp-mante.edu');

--Recipient

insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Simonne Strangwood','bstrangwood0@linkedin.com','6 Butterfield Lane', '07', 'US', 'CA', 'Los Angeles');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Wilbur Tedman', 'gtedman3@flavors.me','793 Anzinger Drive', '82', 'US', 'OH', 'Toledo');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Carol Maslin', 'dmaslin2@tumblr.com','479 Fairfield Plaza', '157', 'US', 'CA', 'Corona');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Sid Kleen', 'akleen4@cafepress.com','49925 Straubel Avenue', '3667', 'US', 'NY', 'Hicksville');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Alexa McCullagh', 'akleen4@cafepress.com','474 Riverside Place', '05', 'US', 'DC', 'Washington');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Ganny Colling', 'gcolling5@stumbleupon.com','66774 Claremont Lane', '902', 'US', 'GA', 'Atlanta');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Alexis Bernaciak', 'cbernaciak7@amazon.de','5871 Buell Drive', '3', 'US', 'TN', 'Memphis');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Brod Battany','bbattany6@surveymonkey.com','01 Ryan Pass', '799', 'US', 'PA', 'Erie');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Waite Rackley', 'erackley8@hatena.ne.jp','6 Tennyson Park', '76430', 'US', 'AL', 'Birmingham');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Sella Klauer', 'itomaszczyk1@fotki.com','178 Dryden Avenue', '8376', 'US', 'VA', 'Herndon');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Sissie Radke', 'erackley8@hatena.ne.jp','7 Farwell Street', '8439', 'US', 'NY', 'Buffalo');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Aviva Jaquin', 'djaquin9@comcast.net','48150 Kipling Trail', '96795', 'US', 'CO', 'Fort Collins');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Frederic Mathias', 'dmaslin2@tumblr.com','5112 Lakeland Circle', '312', 'US', 'TN', 'Nashville');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('Carina Gristhwaite', 'gcolling5@stumbleupon.com','99 Portage Plaza', '8637', 'US', 'FL', 'Pensacola');
insert into Recipient (Name,UserEmail,StreetAddr,Zip,Country,State,City) values ('John Jingleheimer','bstrangwood0@linkedin.com','123 6th St.', '32904', 'US', 'FL', 'Melbourne');


--Payment
insert into Payment  values (45, 'MyVisa','Card','dmaslin2@tumblr.com'); --
insert into Payment  values (489, 'Harry''s card','Card','bstrangwood0@linkedin.com'); --
insert into Payment  values (89, 'Dad''s card','Card','gtedman3@flavors.me'); --
insert into Payment  values (546, 'Work Card','Card','akleen4@cafepress.com'); --
insert into Payment  values (1465, 'Sarah''s visa','Card','bbattany6@surveymonkey.com'); --
insert into Payment  values (984, 'MY Discover','Card','gcolling5@stumbleupon.com'); --
insert into Payment  values (956, 'Checking Comerica','Echeck','djaquin9@comcast.net');
insert into Payment  values (4156, 'Business Checking','Echeck','cbernaciak7@amazon.de');
insert into Payment  values (264, 'myChecking','ECheck','erackley8@hatena.ne.jp');
insert into Payment  values (4612, 'AmExpress','Card','itomaszczyk1@fotki.com');

--Item
insert into Item (SKU,Price,Name, VendorID) values (804860378, 39.77, 'Cardify',145);
insert into Item (SKU,Price,Name, VendorID) values (117008034, 43.43, 'Latlux',256);
insert into Item (SKU,Price,Name, VendorID) values (177796829, 50.68, 'Ventosanzap',3758);
insert into Item (SKU,Price,Name, VendorID) values (502085099,  18.5, 'Zamit',4456);
insert into Item (SKU,Price,Name, VendorID) values (506771388, 76.36, 'Namfix',5654);
insert into Item (SKU,Price,Name, VendorID) values (885194543, 41.74, 'Gembucket',689);
insert into Item (SKU,Price,Name, VendorID) values (902020461, 40.64, 'It',77854);
insert into Item (SKU,Price,Name, VendorID) values (456690372, 62.75, 'Bigtax',87);
insert into Item (SKU,Price,Name, VendorID) values (387511327, 99.95, 'Cardguard',995);
insert into Item (SKU,Price,Name, VendorID) values (295436181, 70.07, 'Rank',10453);

--ECheck
insert into ECheck values (358434545, 670191694, 956);
insert into ECheck values (560234534, 355984187, 4156);
insert into ECheck values (201534534, 376948645, 264);

--Card
insert into Card values (3567132057646, '2025-09-30', 'Carol Maslin', 712, 45);
insert into Card values (3582309608578, '2020-08-31', 'Harry Strangwood', 442, 489);
insert into Card values (3417195219937, '2019-07-31', 'Wilson Tedman', 706, 89);
insert into Card values (6767207852757, '2023-08-31', 'Reilly Inc', 702, 546);
insert into Card values (3576971919460, '2021-04-30', 'Sarah Battany', 486, 1465);
insert into Card values (3576269064495, '2020-05-31', 'Ganny Colling', 963, 984);
insert into Card values (5002358333732, '2026-09-30', 'Owen Tomaszczyk', 522, 4612);

--CustOrder
insert into CustOrder values (36, '2017-02-23');
insert into CustOrder values (67, '2017-02-02');
insert into CustOrder values (93, '2017-05-05');
insert into CustOrder values (34, '2016-10-12');
insert into CustOrder values (20, '2017-05-23');
insert into CustOrder values (85, '2016-10-08');
insert into CustOrder values (15, '2017-01-29');
insert into CustOrder values (16, '2017-02-07');
insert into CustOrder values (94, '2017-04-10');
insert into CustOrder values (63, '2014-04-11');

--ItemOrder
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (36,804860378, 145,1);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (67,117008034, 256,5);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (34,506771388, 5654,9);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (20,456690372, 87,3);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (85,885194543, 689,7);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (15,177796829, 3758,10);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (16,295436181, 10453,4);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (94,117008034, 256,5);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (63,502085099, 4456,6);
insert into ItemOrder (OrderID, SKU, VendorID, Quantity) values (93,456690372, 87,5);

--AuthorizedOrders
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('bstrangwood0@linkedin.com',36,1,489);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('gtedman3@flavors.me',67,4,89);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('dmaslin2@tumblr.com',93,8,45);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('akleen4@cafepress.com',34,2,546);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('akleen4@cafepress.com',20,3,546);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('gcolling5@stumbleupon.com',85,7,984);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('cbernaciak7@amazon.de',15,12,4156);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('bbattany6@surveymonkey.com',16,13,1465);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('erackley8@hatena.ne.jp',94,5,264);
insert into AuthorizedOrders (UserEmail,OrderID, RecipientID, PaymentID) values ('itomaszczyk1@fotki.com',63,6,4612);


select * from Customer;
select * from Address;
select * from Payment;
select * from ECheck;
select * from Card;
select * from Vendor;
select * from Item;
select * from CustOrder;
select * from ItemOrder;
select * from AuthorizedOrders;

/*
drop table AuthorizedOrders;
drop table ItemOrder;
drop table CustOrder;
drop table Item;
drop table Vendor
drop table Card;
drop table ECheck;
drop table Payment;
drop table Address
drop table Customer;
*/



-- vendorView displays the table with the online retailers that are compatible with the eShopCart 
CREATE VIEW vendorView AS
SELECT *
FROM Vendor;

-- authorizedOrdersView displays the orders made with the eShopCart
CREATE VIEW authorizedOrdersView AS
SELECT *
FROM AuthorizedOrders;

