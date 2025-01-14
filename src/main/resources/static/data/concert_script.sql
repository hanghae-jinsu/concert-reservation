use concert;

INSERT INTO concert.user_entity
(age, money, user_id, user_name, payment_type)
VALUES(10, 40000.0, UUID(), 'jinsu', 'CARD'),
(10, 40000.0, UUID(), 'jane', 'CARD'),
(10, 40000.0, UUID(), 'jisu', 'CARD');


--- concert Hall

INSERT INTO concert.concert_hall
(hall_capacity, hall_id, hall_name, hall_type)
VALUES(30, UUID(), "A관", "COMMON"),
(50, UUID(), "B관", "COMMON"),
(50, UUID(), "C관", "COMMON");

--- concert

INSERT INTO concert.concert_entity
    (running_time, target_age, concert_id, concert_name, notice, cost)
VALUES
    (120, 10, UUID(), '라_트라비아타', '정숙', 3000.0),
    (120, 10, UUID(), '해리포터와 마법사의 돌', '정숙', 3000.0),
    (120, 10, UUID(), '해리포터와 불의 잔', '정숙', 3000.0);

--- concert Schedule

INSERT INTO concert.concert_schedule
(running_time, end_date_time, start_date_time, concert_concert_id, concert_hall_hall_id, concert_name, concert_schedule_id, notice)
VALUES(120, '2025-01-10 16:00:00', '2025-01-10 18:00:00', 'e5a229f4-ceca-11ef-b261-0242ac130002', 'e4a68352-ceca-11ef-b261-0242ac130002', '라_트라비아', UUID(), '조용');




