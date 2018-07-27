-- auto Generated on 2018-02-14 14:18:57 
-- DROP TABLE IF EXISTS message; 
CREATE TABLE message(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	from_id INT (11) NOT NULL COMMENT 'fromId',
	to_id INT (11) NOT NULL COMMENT 'toId',
	conversation_id VARCHAR (50) NOT NULL COMMENT 'conversationId',
	created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'createdTime',
	content TEXT NOT NULL COMMENT 'content',
	INDEX(from_id),
	INDEX(to_id),
	INDEX(conversation_id),
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'message';
ALTER TABLE message
	ADD has_read INT(11) NOT NULL DEFAULT 0 COMMENT 'hasRead';
