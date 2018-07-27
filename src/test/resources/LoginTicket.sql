-- auto Generated on 2018-02-09 22:06:02 
-- DROP TABLE IF EXISTS login_ticket; 
CREATE TABLE login_ticket(
	id INT (11) AUTO_INCREMENT COMMENT 'id',
	user_id INT (11) COMMENT 'userId',
	expired TIMESTAMP  COMMENT 'expired',
	ticket VARCHAR (50) COMMENT 'ticket',
	status INT (11) COMMENT 'status',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'login_ticket';
