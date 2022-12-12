-- Dialect: MySQL 8

-- drop on startup
drop table if exists division;

-- create on startup
create table division
(
    id              int not null auto_increment,
    name            varchar(150),
    parent_id       int,
    dt_from         datetime(6),
    dt_till         datetime(6),
    is_system       bit,
    creation_date   datetime(6),
    correction_date datetime(6),
    primary key (id)
) engine=InnoDB;

-- add self reference
alter table division
    add constraint parent_id_division_id_FK
        foreign key (parent_id) references division (id)