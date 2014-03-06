# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table question (
  id                        bigint not null,
  test_id                   bigint not null,
  question_text             varchar(255),
  answer1                   varchar(255),
  answer2                   varchar(255),
  answer3                   varchar(255),
  answer4                   varchar(255),
  correct_answer            integer,
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
  id                        bigint not null,
  test_review_id            bigint not null,
  current                   integer,
  test_id                   bigint,
  score                     integer,
  constraint pk_test_answer primary key (id))
;

create table test_review (
  id                        bigint not null,
  test_id                   bigint,
  constraint pk_test_review primary key (id))
;

create sequence question_seq;

create sequence test_seq;

create sequence test_answer_seq;

create sequence test_review_seq;

alter table question add constraint fk_question_test_1 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_question_test_1 on question (test_id);
alter table test_answer add constraint fk_test_answer_test_review_2 foreign key (test_review_id) references test_review (id) on delete restrict on update restrict;
create index ix_test_answer_test_review_2 on test_answer (test_review_id);
alter table test_answer add constraint fk_test_answer_test_3 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_test_answer_test_3 on test_answer (test_id);
alter table test_review add constraint fk_test_review_test_4 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_test_review_test_4 on test_review (test_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists question;

drop table if exists test;

drop table if exists test_answer;

drop table if exists test_review;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists question_seq;

drop sequence if exists test_seq;

drop sequence if exists test_answer_seq;

drop sequence if exists test_review_seq;

