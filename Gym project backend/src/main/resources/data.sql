INSERT INTO gymplan (body_part,exercise1,exercise2,exercise3,exercise4,exercise5) VALUES ('Push','DB press$15kg','FLY$3plates','Declined DB Press$7.5kg','Triceps$11kg','Tricep2$2.5kg');
INSERT INTO gymplan (body_part,exercise1,exercise2,exercise3,exercise4,exercise5) VALUES ('Pull','Lat pull down$25kg','barbell rows$5kg','cable rows$24kg','latpush down$18kg','reverse flys$3plates');
INSERT INTO gymplan (body_part,exercise1,exercise2,exercise3,exercise4,exercise5) VALUES ('Leg',' SQUAT$23kg','calves$5kg','calfs$2.5kg','ham$10kg','RDL$5kg');


DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO users (username,password,enabled) VALUES ('john','{noop}test123',1);
INSERT INTO users (username,password,enabled) VALUES ('mary','{noop}test123',1);
INSERT INTO users (username,password,enabled) VALUES ('susan','{noop}test123',1);




CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `authorities`
VALUES
('john','ROLE_EMPLOYEE'),
('mary','ROLE_EMPLOYEE'),
('mary','ROLE_MANAGER'),
('susan','ROLE_EMPLOYEE'),
('susan','ROLE_MANAGER'),
('susan','ROLE_ADMIN');
