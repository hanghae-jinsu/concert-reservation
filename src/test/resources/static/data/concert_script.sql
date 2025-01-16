

-- concert.concert_entity definition

CREATE TABLE `concert_entity` (
                                  `cost` double DEFAULT NULL,
                                  `running_time` int DEFAULT NULL,
                                  `target_age` int DEFAULT NULL,
                                  `concert_id` varchar(255) NOT NULL,
                                  `concert_name` varchar(255) DEFAULT NULL,
                                  `notice` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`concert_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.concert_hall definition

CREATE TABLE `concert_hall` (
                                `hall_capacity` int DEFAULT NULL,
                                `hall_id` varchar(255) NOT NULL,
                                `hall_name` varchar(255) DEFAULT NULL,
                                `hall_type` enum('BUSINESS','COMMON','VIP') DEFAULT NULL,
                                PRIMARY KEY (`hall_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.concert_schedule definition

CREATE TABLE `concert_schedule` (
                                    `finished` bit(1) NOT NULL,
                                    `running_time` int DEFAULT NULL,
                                    `end_date_time` datetime(6) DEFAULT NULL,
                                    `start_date_time` datetime(6) DEFAULT NULL,
                                    `concert_hall_id` varchar(255) DEFAULT NULL,
                                    `concert_id` varchar(255) DEFAULT NULL,
                                    `concert_name` varchar(255) DEFAULT NULL,
                                    `concert_schedule_id` varchar(255) NOT NULL,
                                    `notice` varchar(255) DEFAULT NULL,
                                    PRIMARY KEY (`concert_schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.reservation_entity definition

CREATE TABLE `reservation_entity` (
                                      `cost` double NOT NULL,
                                      `count` int NOT NULL,
                                      `running_time` int NOT NULL,
                                      `seat_id` int NOT NULL,
                                      `target_age` int NOT NULL,
                                      `concert_id` varchar(255) DEFAULT NULL,
                                      `concert_name` varchar(255) DEFAULT NULL,
                                      `payment_id` varchar(255) DEFAULT NULL,
                                      `reservation_id` varchar(255) NOT NULL,
                                      `user_id` varchar(255) DEFAULT NULL,
                                      `payment_type` enum('CARD','GIFTCARD','MONEY','OTHER') DEFAULT NULL,
                                      PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.seat_lock definition

CREATE TABLE `seat_lock` (
                             `seat_id` int NOT NULL,
                             `end_time` datetime(6) DEFAULT NULL,
                             `start_time` datetime(6) DEFAULT NULL,
                             `seat_lock_id` varchar(255) NOT NULL,
                             `user_id` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`seat_lock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.user_entity definition

CREATE TABLE `user_entity` (
                               `age` int NOT NULL,
                               `money` double DEFAULT NULL,
                               `user_id` varchar(255) NOT NULL,
                               `user_name` varchar(255) DEFAULT NULL,
                               `payment_type` enum('CARD','GIFTCARD','MONEY','OTHER') DEFAULT NULL,
                               PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.user_token_entity definition

CREATE TABLE `user_token_entity` (
                                     `is_active` bit(1) NOT NULL,
                                     `created_at` datetime(6) DEFAULT NULL,
                                     `expires_at` datetime(6) DEFAULT NULL,
                                     `token_id` varchar(255) NOT NULL,
                                     `user_id` varchar(255) DEFAULT NULL,
                                     PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.concert_seats definition

CREATE TABLE `concert_seats` (
                                 `concert_seat_id` int NOT NULL AUTO_INCREMENT,
                                 `concert_schedule_id` varchar(255) NOT NULL,
                                 `user_id` varchar(255) DEFAULT NULL,
                                 `seat_status` enum('AVAILABLE','HOLD','RESERVED') DEFAULT NULL,
                                 PRIMARY KEY (`concert_seat_id`),
                                 KEY `FKh7n8i0adj0m9nf0fr5sjumsl9` (`concert_schedule_id`),
                                 CONSTRAINT `FKh7n8i0adj0m9nf0fr5sjumsl9` FOREIGN KEY (`concert_schedule_id`) REFERENCES `concert_schedule` (`concert_schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- concert.payment definition

CREATE TABLE `payment` (
                           `amount` double NOT NULL,
                           `payment_id` varchar(255) NOT NULL,
                           `user_entity_user_id` varchar(255) DEFAULT NULL,
                           `payment_type` enum('CARD','GIFTCARD','MONEY','OTHER') DEFAULT NULL,
                           `transaction` enum('CHARGE','PAYMENT','REFUND') DEFAULT NULL,
                           PRIMARY KEY (`payment_id`),
                           KEY `FKo78vyim1m80sldgdllr3o1m1y` (`user_entity_user_id`),
                           CONSTRAINT `FKo78vyim1m80sldgdllr3o1m1y` FOREIGN KEY (`user_entity_user_id`) REFERENCES `user_entity` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO concert.user_entity
(age, money, user_id, user_name, payment_type)
VALUES(10, 40000.0, "user1", 'jinsu', 'CARD'),
      (10, 40000.0, "user2", 'jane', 'CARD'),
      (10, 40000.0, "user3", 'jisu', 'CARD'),
      (10, 30000.0, "user4", 'jisuu', 'CARD'),
      (10, 300000.0, "user5", 'jisuuz', 'CARD');


--- concert Hall

INSERT INTO concert.concert_hall
(hall_capacity, hall_id, hall_name, hall_type)
VALUES(30, "hall1", "A관", "COMMON"),
      (50, "hall2", "B관", "COMMON"),
      (50, "hall3", "C관", "COMMON");

--- concert

INSERT INTO concert.concert_entity
(running_time, target_age, concert_id, concert_name, notice, cost)
VALUES
    (120, 10, "concert1", '라_트라비아타', '정숙', 3000.0),
    (120, 10, "concert2", '해리포터와 마법사의 돌', '정숙', 3000.0),
    (120, 10, "concert3", '해리포터와 불의 잔', '정숙', 3000.0);

--- concert Schedule

INSERT INTO concert.concert_schedule
(finished,running_time, end_date_time, start_date_time, concert_id, concert_hall_id, concert_name, concert_schedule_id, notice)
VALUES(false,120, '2025-01-10 16:00:00', '2025-01-10 18:00:00', 'concert1', 'hall1', '라_트라비아', UUID(), '조용');








