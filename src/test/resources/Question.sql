-- auto Generated on 2018-02-08 17:02:02 
DROP TABLE IF EXISTS question;
CREATE TABLE question(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	title VARCHAR (50) COMMENT 'title',
	user_id INT (11) COMMENT 'userId',
	created_time TIMESTAMP COMMENT 'createdTime',
	comment_count INT (11) COMMENT 'commentCount',
	content TEXT COMMENT 'content',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'question';
