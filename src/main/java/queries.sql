drop database if exists db;
CREATE DATABASE db;
USE db;

CREATE TABLE users (
                       userId INT PRIMARY KEY AUTO_INCREMENT,
                       userName VARCHAR(255) NOT NULL,
                       fullName VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       role TINYINT DEFAULT 0,
                       status BOOLEAN DEFAULT true
);

CREATE TABLE category (
                          categoryId INT PRIMARY KEY AUTO_INCREMENT,
                          categoryName VARCHAR(50) NOT NULL UNIQUE,
                          status BOOLEAN DEFAULT true
);

CREATE TABLE product (
                         productId INT PRIMARY KEY AUTO_INCREMENT,
                         productName VARCHAR(100) NOT NULL,
                         description VARCHAR(255),
                         price DOUBLE NOT NULL,
                         image VARCHAR(255),
                         stock INT NOT NULL,
                         categoryId INT,
                         FOREIGN KEY (categoryId) REFERENCES category(categoryId)
);

create table Orders (
                        orderId int primary key auto_increment,
                        userId int not null ,
                        recipientName varchar(255)NOT NULL ,
                        total float,
                        createdDate DATETIME default(now()),
                        status tinyint default 1,
                        phone varchar(11),
                        address varchar(255),
                        foreign key (userId) references users(userId)
);
create table  OrderDetail(
                             id int primary key  auto_increment,
                             orderId int not null ,
                             productId int not null ,
                             productPrice float,
                             quantity int,
                             foreign key (orderId) references  Orders(orderId)  ,
                             foreign key (productId) references  Product(productId)
);

CREATE TABLE cart (
                      cartId INT PRIMARY KEY AUTO_INCREMENT,
                      userId INT NOT NULL,
                      FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE cartItem (
                          id int primary key auto_increment not null ,
                          cartId INT NOT NULL,
                          productId INT NOT NULL,
                          quantity INT NOT NULL,
                          FOREIGN KEY (cartId) REFERENCES cart(cartId),
                          FOREIGN KEY (productId) REFERENCES product(productId)
);



-- Start procedure user
DELIMITER //

CREATE PROCEDURE PROC_GET_ALL_USER()
BEGIN
    SELECT * FROM users;
END //

CREATE PROCEDURE PROC_ADD_USER(IN uName VARCHAR(255), IN uFullName VARCHAR(255), IN uEmail VARCHAR(255), IN uPassword VARCHAR(255), IN uPhone VARCHAR(255))
BEGIN
    INSERT INTO users(userName, fullName, email, password, phone) VALUES (uName, uFullName, uEmail, uPassword, uPhone);
END //

CREATE PROCEDURE PROC_FIND_USER_BY_ID(IN uID INT)
BEGIN
    SELECT * FROM users WHERE userId = uID;
END //

CREATE PROCEDURE PROC_FIND_USER_BY_EMAIL(IN uEmail VARCHAR(255))
BEGIN
    SELECT * FROM users WHERE email = uEmail;
END //

DELIMITER ;
-- End procedure user

-- Start procedure category
DELIMITER //

CREATE PROCEDURE PROC_GET_ALL_CATEGORY()
BEGIN
    SELECT * FROM category;
END //

CREATE PROCEDURE PROC_UPDATE_CATEGORY_BY_ID(IN idParam INT, IN nameParam VARCHAR(20), IN statusParam BOOLEAN)
BEGIN
    UPDATE category SET categoryName = nameParam, status = statusParam WHERE categoryId = idParam;
END //

CREATE PROCEDURE PROC_DELETE_CATEGORY_BY_ID(IN idParam INT)
BEGIN
    DELETE FROM category WHERE categoryId = idParam;
END //

CREATE PROCEDURE CATEGORY_BY_ID(IN idPram INT)
BEGIN
    SELECT * FROM category WHERE categoryId = idPram;
END //

CREATE PROCEDURE PROC_INSERT_CATEGORY(IN nameCategoryParam VARCHAR(20), IN statusParam BOOLEAN)
BEGIN
    INSERT INTO category (categoryName, status) VALUES (nameCategoryParam, statusParam);
END //

DELIMITER ;
-- End procedure category


DELIMITER //

CREATE PROCEDURE PROC_GET_ALL_PRODUCT()
BEGIN
    SELECT * FROM product ORDER BY productId desc ;
END //

CREATE PROCEDURE PROC_UPDATE_PRODUCT_BY_ID(IN idParam INT, IN nameProductParam VARCHAR(100), IN description_param VARCHAR(255), IN priceParam DOUBLE, IN imageParam VARCHAR(255), IN category_idParam INT, IN stock_param INT)
BEGIN
    UPDATE product SET productName = nameProductParam,description= description_param, price = priceParam, image = imageParam, categoryId = category_idParam,stock= stock_param WHERE productId = idParam;
END //

CREATE PROCEDURE PROC_DELETE_PRODUCT_BY_ID(IN idParam INT)
BEGIN
    DELETE FROM product WHERE productId = idParam;
END //

CREATE PROCEDURE PRODUCT_BY_ID(IN idParam INT)
BEGIN
    SELECT * FROM product WHERE productId = idParam;
END //

CREATE PROCEDURE PROC_INSERT_PRODUCT(IN nameProductParam VARCHAR(100), IN description_param VARCHAR(255), IN priceParam DOUBLE, IN imageParam VARCHAR(255), IN category_idParam INT, IN stock_param INT )
BEGIN
    INSERT INTO product (productName,description, price, image, categoryId,stock) VALUES (nameProductParam,description_param, priceParam, imageParam, category_idParam,stock_param);
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_TOGGLE_USER(IN idParam INT)
BEGIN
    UPDATE users SET status = NOT status WHERE userId = idParam;
END //
DELIMITER ;


# phan trang
DELIMITER //
CREATE PROCEDURE PROC_FIND_PRODUCT_BY_PAGE(IN pageNumber INT,countP INT)
BEGIN
    declare  startNumber int;
    set  startNumber =  (pageNumber-1)*countP ;
SELECT * FROM  product LIMIT startNumber,countP;
END //
DELIMITER ;
CALL PROC_FIND_PRODUCT_BY_PAGE(2,3);


# lấy product theo category
DELIMITER //
CREATE PROCEDURE PRODUCT_BY_ID_CATEGORY(IN idCategory INT)
BEGIN
    SELECT * FROM product WHERE categoryId = idCategory;
END //
DELIMITER ;

# procedure tìm kiếm product theo keyName
DELIMITER //
CREATE PROCEDURE PROC_SEARCH_PRODUCT(IN keyName VARCHAR(255))
BEGIN
    DECLARE searchTerm VARCHAR(255);
    SET searchTerm = CONCAT('%', keyName, '%');
    SELECT * FROM product WHERE productName LIKE searchTerm;
END //
DELIMITER ;


# procedure sort product theo key
DELIMITER //
CREATE PROCEDURE PROC_SORT_PRODUCTS(IN sortOption INT)
BEGIN
    DECLARE orderByClause VARCHAR(255);

    CASE sortOption
        WHEN 2 THEN SET orderByClause = 'productName ASC';
        WHEN 3 THEN SET orderByClause = 'productName DESC';
        WHEN 4 THEN SET orderByClause = 'price ASC';
        WHEN 5 THEN SET orderByClause = 'price DESC';
        ELSE SET orderByClause = 'productId DESC';
        END CASE;

    SET @sql = CONCAT('SELECT * FROM product ORDER BY ', orderByClause);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;




DELIMITER //
CREATE PROCEDURE PROC_RELATED_PRODUCTS(IN idCategory INT, IN productIdParam INT)
BEGIN
    SELECT * FROM product
    WHERE categoryId = idCategory AND productId <> productIdParam;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE PROC_CHECK_DUPLICATE_EMAIL()
BEGIN
    SELECT email FROM users;
END //
DELIMITER ;
CALL PROC_CHECK_DUPLICATE_EMAIL();



DELIMITER //
CREATE PROCEDURE PROC_FIND_USER_BY_PAGE(IN pageNumber INT, IN countP INT)
BEGIN
    DECLARE startNumber INT;
    SET startNumber = (pageNumber-1) * countP;
    SELECT * FROM users LIMIT startNumber, countP;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE PROC_PRODUCT_FEATURED()
BEGIN
    SELECT * FROM product ORDER BY price DESC LIMIT 5;
END //

DELIMITER ;
CALL PROC_PRODUCT_FEATURED();




# create cart
delimiter //
CREATE PROCEDURE PROC_CREATE_CART( id int)
begin
    INSERT INTO cart ( userId) VALUE (id) ;
end //
delimiter //
# findById
delimiter //
CREATE PROCEDURE PROC_FIND_CART_BY_ID( idUser int)
begin
    SELECT * from cart where userId = idUser;
end //



delimiter //
CREATE PROCEDURE PROC_FIND_CART_ITEM_BY_CART(idCart int)
begin
    SELECT * FROM cartItem where cartId = idCart;
end //

CALL PROC_FIND_CART_ITEM_BY_CART(6);

# findAll
delimiter //
CREATE PROCEDURE PROC_FIND_CART_ITEM_BY_CART(idCart int)
begin
    SELECT * FROM cartItem where cartId = idCart;
end //

# create
delimiter //
CREATE PROCEDURE PROC_CREATE_CART_ITEM(newProductId int  , newQuantity int , newCartId int )
begin
    INSERT INTO cartItem( productId, quantity, cartId)
    VALUES (newProductId , newQuantity, newCartId);
end //


# findById
delimiter //
CREATE PROCEDURE PROC_FIND_CART_ITEM_BY_ID(productIdParam int)
begin
    SELECT * FROM cartItem where productId = productIdParam ;
end //
# update
delimiter //
CREATE PROCEDURE PROC_UPDATE_QUANTITY_CART_ITEM(   newQuantity int ,idCartItem int)
begin
    UPDATE cartItem SET quantity = newQuantity  WHERE id = idCartItem  ;
end //

# delete
delimiter //
CREATE PROCEDURE PROC_DELETE_CART_ITEM(idCartItem int )
begin
    DELETE  FROM cartItem WHERE id = idCartItem;
end //


# order

# xoá toàn bộ giỏ hàng
delimiter //
create procedure PROC_CLEAR_CART_ITEM(cartIdParam int)
begin
    delete  from cartItem where cartId = cartIdParam;
end //;

# thêm mới
DELIMITER //
CREATE PROCEDURE PROC_CREATE_NEW_ORDER(
    IN userIdParam INT,
    IN recipientNameP VARCHAR(255),
    IN totalP FLOAT,
    IN statusP TINYINT,
    IN addressP VARCHAR(255),
    IN phoneP VARCHAR(11),
    IN createdDateP DATE

)
BEGIN
    -- Insert a new order into the Orders table
    INSERT INTO Orders(userId, recipientName, total, status, address, phone,createdDate)
    VALUES (userIdParam, recipientNameP, totalP, statusP, addressP, phoneP,createdDateP);
END //

# findAllOrder
DELIMITER //
CREATE PROCEDURE PROC_GET_ALL_ORDER()
BEGIN
    SELECT * FROM orders ORDER BY orderId desc ;
END //













