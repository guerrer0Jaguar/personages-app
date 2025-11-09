CREATE DATABASE IF NOT EXISTS personages_db;

USE personages_db;

create table image (
  id bigint not null, 
  name varchar(255), 
  url varchar(255), 
  primary key (id)
) engine = InnoDB;

create table image_seq (next_val bigint) engine = InnoDB;

insert into image_seq 
values (1);

create table media (
  id bigint not null, 
  image_id bigint, 
  media_type_id bigint, 
  name varchar(255), 
  primary key (id)
) engine = InnoDB;

create table media_seq (next_val bigint) engine = InnoDB;

insert into media_seq 
values (1);

create table media_type (
  id bigint not null, 
  name varchar(255), 
  primary key (id)
) engine = InnoDB;

create table personage (
  creation_date datetime(6), 
  id bigint not null, 
  image_id bigint, 
  media_id bigint, 
  description varchar(255), 
  name varchar(255), 
  rol varchar(255), 
  primary key (id)
) engine = InnoDB;

create table personage_seq (next_val bigint) engine = InnoDB;

insert into personage_seq values (1);

alter table 
  media 
add 
  constraint UKtgo8pjsvt8x89be6c9d3wwuuj unique (image_id);

alter table 
  media 
add 
  constraint UK24gk2bom1j7819n9a2ugouyuq unique (media_type_id);

alter table 
  personage 
add 
  constraint UKixxicgrx4ttn82ad1r16f4hx8 unique (image_id);

alter table 
  media 
add 
  constraint FKk0umoyhi0jpwh1n0dl83bo5i4 foreign key (image_id) references image (id);

alter table 
  media 
add 
  constraint FK970n3pdj8afmsen9axuif2tnr foreign key (media_type_id) references media_type (id);

alter table 
  personage 
add 
  constraint FKdu5bm8vb9yhhf70odsr2vadc8 foreign key (image_id) references image (id);

alter table 
  personage 
add 
  constraint FKdxjak5vpx4scncbpbysrk913w foreign key (media_id) references media (id);

insert into media_type values (1,'película');
insert into media_type values (2,'serie');

create user 'personages_db_user'@'127.0.0.1' identified by 'very-very-strong-password';
grant insert, update, delete, select, lock tables,show view, create routine, create temporary tables, execute, index, trigger on personages_db.* to 'personages_db_user'@'127.0.0.1';

create user 'personages_db_user'@'172.18.0.1' identified by 'very-very-strong-password';
grant insert, update, delete, select, lock tables,show view, create routine, create temporary tables, execute, index, trigger on personages_db.* to 'personages_db_user'@'172.18.0.1';

flush privileges;


