-- auto Generated on 2018-02-13 11:58:08 
-- DROP TABLE IF EXISTS comment; 
CREATE TABLE comment(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	user_id INT (11) COMMENT 'userId',
	created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'createdTime',
	entity_type INT (11) COMMENT 'entityType',
	entity_id INT (11) COMMENT 'entityId',
	content TEXT COMMENT 'content',
	INDEX(user_id),
INDEX `ix_entity_type_entity_id`(entity_type,entity_id),
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'comment';
ALTER TABLE comment
	ADD status INT(11) NOT NULL DEFAULT -1 COMMENT 'status';
