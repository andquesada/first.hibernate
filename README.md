This is my first project testing/using Hibernate.

Below a script to create the structure of the expected MariaDB table:

CREATE TABLE IF NOT EXISTS `Employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` tinyint(3) unsigned NOT NULL,
  `role` varchar(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

The name of the DB should be: hibernate_test. The service must listen on the default port (3306) on the localhost.

The code expects a user conforming to the following SQL code:

GRANT USAGE ON *.* TO 'andres'@'%' IDENTIFIED BY PASSWORD 'and1234';
GRANT SELECT, INSERT, UPDATE, DELETE, ALTER ON `hibernate_test`.* TO 'andres'@'%';
