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



















USE sell_watch;

DROP TABLE user;
CREATE TABLE user(
                     user_id int primary key not null auto_increment,
                     user_name varchar(50) not null ,
                     email varchar(100) not null  ,
                     password varchar(255) not null ,
                     status BIT default(1),
                     role enum('ADMIN','USER')
);

# findAll
delimiter //
CREATE PROCEDURE PROC_FIND_ALL_USER
begin
    SELECT * FROM user;
end //
drop procedure PROC_FIND_ALL_USER;

# findById
delimiter //
CREATE PROCEDURE PROC_FIND_By_ID_USER(id int)
begin
    SELECT * FROM user WHERE user_id = id ;
end //
drop procedure PROC_FIND_By_ID_USER;

# create
delimiter //
CREATE PROCEDURE PROC_CREATE_USER(newName varchar(50), newEmail varchar(100), newPass varchar(255))
begin
    INSERT INTO user( user_name, email, password)
    values (newName, newEmail,newPass);
end //
drop procedure PROC_CREATE_USER;


# delete
delimiter //
CREATE PROCEDURE PROC_DELETE_USER(id int)
begin
    DELETE  FROM user WHERE user_id = id;
end //
drop procedure PROC_DELETE_USER;

# update
delimiter //
CREATE PROCEDURE PROC_UPDATE_USER( newName varchar(50), newEmail varchar(100), newStatus BIT, id int )
begin
    UPDATE user SET user_name = newName , email = newEmail , status = newStatus WHERE user_id = id ;
end //
drop procedure PROC_UPDATE_USER;


# product
CREATE TABLE products (
                          product_id int primary key not null auto_increment,
                          product_name varchar(100) not null ,
                          category_id int ,
                          image varchar(255),
                          price double,
                          description text,
                          stock int ,
                          status BIT default (1) ,
                          foreign key (category_id) references category(category_id)
) ;



# Chỉ mục cho cột category_id
CREATE INDEX idx_category_id ON products (category_id);

# thủ tục product
# findAll
delimiter //
CREATE PROCEDURE PROC_FIND_ALL_PRODUCT()
begin
    SELECT * FROM products;
end //
drop procedure PROC_FIND_ALL_PRODUCT;

# findById
delimiter //
CREATE PROCEDURE PROC_FIND_By_ID_PRODUCT(id int)
begin
    SELECT * FROM products WHERE product_id = id ;
end //
drop procedure PROC_FIND_By_ID_PRODUCT;

# create
delimiter //
CREATE PROCEDURE PROC_CREATE_PRODUCT(newName varchar(50), newCategoryId int ,newImage varchar(255), newPrice double,  newDescrition text, newStock int )
begin
    INSERT INTO products( product_name, category_id, image, price, description, stock)
    values (newName, newCategoryId,newImage,newPrice,newDescrition, newStock);
end //
drop procedure PROC_CREATE_PRODUCT;


# delete
delimiter //
CREATE PROCEDURE PROC_DELETE_PRODUCT(id int)
begin
    DELETE  FROM products WHERE product_id = id;
end //
drop procedure PROC_DELETE_PRODUCT;

# update
delimiter //
CREATE PROCEDURE PROC_UPDATE_PRODUCT( newName varchar(50), newCategoryId int ,newImage varchar(255), newPrice double,  newDescrition text, newStock int , newStatus BIT, id int )
begin
    UPDATE products SET product_name = newName , category_id = newCategoryId, image= newImage, price = newPrice, description = newDescrition, stock = newStock, status = newStatus WHERE product_id = id ;
end //
drop procedure PROC_UPDATE_PRODUCT;


CREATE TABLE cart(
                     cart_id int auto_increment primary key not null ,
                     user_id int not null ,
                     constraint fk_user_cart foreign key (user_id) references user(user_id)
);

CREATE TABLE cart_item(
                          id int primary key auto_increment not null ,
                          product_id int not null ,
                          quantity int ,
                          cart_id int ,
                          constraint fk_product_cart foreign key (product_id) references products(product_id),
                          constraint fk_cart foreign key (cart_id) references cart(cart_id)
);



# show product by category
delimiter //
CREATE PROCEDURE PROC_SHOW_PRODUCT_BY_CATEGORY(id int ,offset int , size int)
begin
    SELECT * from products
    where category_id = id
    ORDER BY product_id
    LIMIT offset, size;
end //
drop procedure PROC_SHOW_PRODUCT_BY_CATEGORY;

# sum product
delimiter //
CREATE PROCEDURE PROC_COUNT_PRODUCT()
begin
    SELECT COUNT(*) as 'total_product' from products;
end //

# phân trang
delimiter //
CREATE PROCEDURE PROC_GET_ALL_PRODUCT_PAGE(offset int , size int )
begin
    SELECT * FROM products
    ORDER BY product_id
    LIMIT offset, size;
end //
# thu tuc cart
# create cart
delimiter //
CREATE PROCEDURE PROC_CREATE_CART( id int)
begin
    INSERT INTO cart ( user_id) VALUE (id) ;
end //
# findById
delimiter //
CREATE PROCEDURE PROC_FIND_BY_ID_CART( id_ int)
begin
    SELECT * from cart where user_id = id_;
end //

# thu tuc cart_item
# findAll
delimiter //
CREATE PROCEDURE PROC_FIND_CART_ITEM_BY_CART(id_cart_ int)
begin
    SELECT * FROM cart_item where id = id_cart_;
end //
drop procedure PROC_FIND_CART_ITEM_BY_CART;

# findById
delimiter //
CREATE PROCEDURE PROC_FIND_BY_ID_CART_ITEM(id_ int)
begin
    SELECT * FROM cart_item where id = id_ ;
end //
drop procedure PROC_FIND_BY_ID_CART_ITEM;

# create
delimiter //
CREATE PROCEDURE PROC_CREATE_CART_ITEM(newProduct int  , newQuantity int , newCart int )
begin
    INSERT INTO cart_item( product_id, quantity, cart_id)
    VALUES (newProduct , newQuantity, newCart);
end //
drop procedure PROC_CREATE_CART_ITEM;

# delete
CREATE PROCEDURE PROC_DELETE_CART_ITEM(id_ int )
begin
    DELETE  FROM cart_item WHERE id = id_;
end //
drop procedure PROC_DELETE_CART_ITEM;

# update
delimiter //
CREATE PROCEDURE PROC_UPDATE_CART_ITEM( newProduct int , newQuantity int , newCart int , id_ int)
begin
    UPDATE cart_item SET product_id = newProduct , quantity = newQuantity , cart_id = newCart WHERE id = id_  ;
end //
drop procedure PROC_UPDATE_CART_ITEM;

SELECT * from cart where user_id = 21 ;