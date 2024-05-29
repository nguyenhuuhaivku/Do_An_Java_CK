create TABLE `nganhang1`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `AccountNumber` VARCHAR(20) NULL,
  `Accountpassword` VARCHAR(50) NULL,
  `AccountName` VARCHAR(20) NULL,
  `Phone` VARCHAR(20) NULL,
  `isActive` TINYINT NULL,
  PRIMARY KEY (`id`)
);

create TABLE `nganhang1`.`trade_history` (
   `id` BIGINT NOT NULL AUTO_INCREMENT,
   `Accountid` INT NULL,
   `trade_date` DATETIME NULL DEFAULT NOW(), 
   `trade_type` CHAR(30) NULL,
   `amount` INT NULL, 
   PRIMARY KEY (`id`),
   CONSTRAINT `fk_account_trade` FOREIGN KEY (`Accountid`) REFERENCES `account` (`id`)
 );

	
select now(); -- cái nay là lịch sử giao dịch


SELECT * FROM nganhang1.account;

USE nganhang1;
DELIMITER $$
create PROCEDURE trade(
    IN accountid INT,
    IN amount INT,
    IN tradetype CHAR(30))
BEGIN
    INSERT INTO trade_history (Accountid, amount, trade_type)
    VALUES (accountid, amount, tradetype);

    SELECT ROW_COUNT() AS result;
END $$
DELIMITER ;
use nganhang1;


CALL trade(1, 5000, 'nạp tiền'); -- cái này là nạp tiền 
-- nạp 3 lần là 15000

CALL trade(1,5000,'rút tiền'); -- cái này là rút tiền

CALL trade(1,5000,'chuyển khoản'); --  cái này là chuyển khoản

CALL trade(1,5000,'nhận tiền'); -- nhận tiền từ tài khoản khác

-- số dư tk= nạp tiền + nhận tiền - rút tiền - chuyển khoản

select sum(case when trade_type='rút tiền' then amount else 0 end) as sotienrut from trade_history;
select sum(case when trade_type='nạp tiền' then amount else 0 end) as sotiennap from trade_history;
select sum(case when trade_type='chuyển khoản' then amount else 0 end) as sotienchuyenkhoan from trade_history;
select sum(case when trade_type='nhận tiền' then amount else 0 end) as sotiennhan from trade_history;

select 
	sum(case when trade_type='nạp tiền' then amount else 0 end) + 
    sum(case when trade_type='nhận tiền' then amount else 0 end) - 
    sum(case when trade_type='chuyển khoản' then amount else 0 end) - 
    sum(case when trade_type='rút tiền' then amount else 0 end)
as sodu from trade_history;


-- hàm tính số tiền dư trong tk
DELIMITER $$
CREATE PROCEDURE getBalance(IN userId INT)
BEGIN
    SELECT 
        SUM(CASE WHEN trade_type='nạp tiền' THEN amount ELSE 0 END) + 
        SUM(CASE WHEN trade_type='nhận tiền' THEN amount ELSE 0 END) -
        SUM(CASE WHEN trade_type='chuyển khoản' THEN amount ELSE 0 END) - 
        SUM(CASE WHEN trade_type='rút tiền' THEN amount ELSE 0 END) 
    AS sodu
    FROM trade_history
    WHERE Accountid = userId;
END$$
DELIMITER ;

use nganhang1;
call getBalance(1); -- tính theo id


-- hàm lấy lịch sử giao dịch thời gian, phương thức , số tiền 
DELIMITER $$
create PROCEDURE getHistoryTrade(IN userId INT)
BEGIN
    SELECT trade_date, trade_type,amount
    FROM trade_history
    WHERE Accountid = userId;
END$$
DELIMITER ;
use nganhang1;
call getHistoryTrade(1);


SELECT * FROM nganhang1.trade_history;

-- hàm thêm và nó kiểm tra stk mật khẩu và phone 
use nganhang1;
DELETE FROM account WHERE id>0;
DELETE FROM account;


DELIMITER $$

CREATE PROCEDURE insertaccount(
    IN p_accountNumber VARCHAR(20),
    IN p_accountpassword VARCHAR(50),
    IN p_accountName VARCHAR(20),
    IN p_phone VARCHAR(20),
    IN p_isactive TINYINT
)
BEGIN
    DECLARE accountExists INT;
    DECLARE phoneExists INT;
    DECLARE passwordExists INT;
    
    -- Kiểm tra xem accountNumber đã tồn tại hay không
    SELECT COUNT(*) INTO accountExists FROM account WHERE AccountNumber = p_accountNumber;
    
    -- Nếu accountNumber đã tồn tại, kiểm tra số điện thoại và mật khẩu
    IF accountExists > 0 THEN
        -- Kiểm tra số điện thoại
        SELECT COUNT(*) INTO phoneExists FROM account WHERE Phone = p_phone;
        
        -- Kiểm tra mật khẩu
        SELECT COUNT(*) INTO passwordExists FROM account WHERE Accountpassword = p_accountpassword;
        
        -- Nếu số điện thoại hoặc mật khẩu đã tồn tại, in ra -1
        IF phoneExists > 0 OR passwordExists > 0 THEN
            SELECT -1 AS result;
        ELSE
            -- Nếu cả số điện thoại và mật khẩu đều không tồn tại, thêm tài khoản mới vào bảng
            INSERT INTO account(AccountNumber, Accountpassword, AccountName, Phone, isActive)
            VALUES (p_accountNumber, p_accountpassword, p_accountName, p_phone, p_isactive);
            
            -- Trả về số hàng đã được thêm
            SELECT ROW_COUNT() AS result;
        END IF;
    ELSE
        -- Nếu accountNumber không tồn tại, thêm tài khoản mới vào bảng
        INSERT INTO account(AccountNumber, Accountpassword, AccountName, Phone, isActive)
        VALUES (p_accountNumber, p_accountpassword, p_accountName, p_phone, p_isactive);
        
        -- Trả về số hàng đã được thêm
        SELECT ROW_COUNT() AS result;
    END IF;
END$$

DELIMITER ;

SELECT * FROM nganhang1.account;
-- khi chạy 1 là thành công còn o là thất bại 
CALL insertaccount('123490', 'mypassrdd', 'anhc', '12386900',1); -- thành công là in ra 1 thát bại là -1




-- hàm cập nhật tài khaonr mật khẩu và tên
DELIMITER $$

create PROCEDURE updateAccount(
    IN p_Id INT,
    IN p_accountNumber VARCHAR(20),
    IN p_passwordAccount VARCHAR(50),
    IN p_accountName VARCHAR(30)
)
BEGIN
    UPDATE account 
    SET AccountNumber = p_accountNumber,
        Accountpassword = p_passwordAccount,
        AccountName = p_accountName
    WHERE id = p_Id;
    SELECT ROW_COUNT() AS result;
END$$

DELIMITER ;
-- 1 là thành cong 0 là thất bại
CALL updateAccount(1, '123', 'password123', 'John Doe');



-- thay mậtkhaur
DELIMITER $$
CREATE PROCEDURE changepassword(
    IN p_Id INT,
    IN p_passwordAccount VARCHAR(50)
)
BEGIN
    UPDATE account 
    SET
        Accountpassword = p_passwordAccount
    WHERE id = p_Id;
    SELECT ROW_COUNT() AS result;
END$$
DELIMITER ;

CALL changepassword(1, 'newpassword');


-- hàm đăng nhâpk nó tìm xem có tk và mật khẩu có trong này chưa
DELIMITER $$
create PROCEDURE login(
    IN p_accountNumber VARCHAR(20),
    IN p_passwordAccount VARCHAR(50)
)
BEGIN
    DECLARE recordCount INT;

    SELECT COUNT(*)
    INTO recordCount
    FROM account 
    WHERE 
        AccountNumber = p_accountNumber
        AND Accountpassword = p_passwordAccount
        AND isActive = 1;

    SELECT recordCount AS result;
END$$
DELIMITER ;

-- 1 là thành công
call login('123','newpassword');

-- thống  kê
DELIMITER $$
CREATE PROCEDURE thongke()
BEGIN
    SELECT 
        a.id,
        a.AccountNumber,
        a.AccountName,
        a.Phone,
        (SUM(CASE WHEN t.trade_type='nạp tiền' THEN amount ELSE 0 END) + 
        SUM(CASE WHEN t.trade_type='nhận tiền' THEN amount ELSE 0 END) -
        SUM(CASE WHEN t.trade_type='chuyển khoản' THEN amount ELSE 0 END) - 
        SUM(CASE WHEN t.trade_type='rút tiền' THEN amount ELSE 0 END) ) as balance

    FROM account a
    INNER JOIN trade_history t ON a.id = t.Accountid 
    GROUP BY a.id, a.AccountNumber, a.AccountName, a.Phone;
END$$
DELIMITER ;


