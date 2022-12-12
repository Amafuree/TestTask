-- Dialect: MySQL 8

-- init data
use test_task_schema;

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('HEAD',
        null,
        '2020-10-10',
        null,
        1,
        '2020-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('subHead_1',
        1,
        '2020-10-10',
        '2040-10-10',
        0,
        '2020-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('subHead_2',
        1,
        '2020-10-10',
        '2050-10-10',
        0,
        '2020-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('sub_2_child_1',
        3,
        '2045-10-10',
        '2050-10-10',
        0,
        '2045-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('child_1_child',
        4,
        '2045-10-10',
        '2050-10-10',
        0,
        '2045-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('subHead_3',
        1,
        '2020-10-10',
        '2025-10-10',
        0,
        '2020-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('sub_1_child',
        2,
        '2020-10-10',
        '2025-10-10',
        0,
        '2020-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('sub_2_child_2',
        3,
        '2040-10-10',
        '2045-10-10',
        0,
        '2040-10-10');

insert into division (name, parent_id, dt_from, dt_till, is_system, creation_date)
values ('created_after',
        1,
        '2020-10-10',
        null,
        0,
        now())