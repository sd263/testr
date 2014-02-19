# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  text                      varchar(255) not null,
  question_id               bigint not null,
  correct                   boolean,
  constraint pk_answer primary key (text))
;

create table question (
  id                        bigint not null,
  test_id                   bigint,
  question_text             varchar(255),
  constraint pk_question primary key (id))
;

create table test (
  id                        bigint not null,
  name                      varchar(255),
  test_desc                 varchar(255),
  num_questions             integer,
  constraint pk_test primary key (id))
;

create table test_answer (
  current                   integer)
;

create sequence answer_seq;

create sequence question_seq;

create sequence test_seq;

alter table answer add constraint fk_answer_question_1 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_question_1 on answer (question_id);
alter table question add constraint fk_question_test_2 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_question_test_2 on question (test_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists answer;

drop table if exists question;

drop table if exists test;

drop table if exists test_answer;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists answer_seq;

drop sequence if exists question_seq;

drop sequence if exists test_seq;

