# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table test (
  id                        bigint not null,
  name                      varchar(255),
  test_desc                 varchar(255),
  constraint pk_test primary key (id))
;

create sequence test_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists test;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists test_seq;

