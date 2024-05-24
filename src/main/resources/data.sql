insert into roles(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Admin'),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Manager'),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Employee');


insert into users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, enabled,
                  first_name, gender, last_name, user_name, role_id, phone)
values ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'admin', 'MALE', 'admin', 'admin@admin.com',
        1, 111),
       ('2024-05-24 17:08:00',1, false, '2024-05-24 17:08:00', 1, true, 'Ashley', 'MALE' , 'Cole', 'cole@gmail.com', 3, 9343212547),
       ('2024-05-24 17:08:00',1, false, '2024-05-24 17:08:00', 1, true, 'Johny', 'MALE' , 'Bravo', 'bravo@yahoo.com', 2, 9497233481);