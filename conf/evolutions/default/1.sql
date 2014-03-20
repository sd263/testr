# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table classroom (
  id                        bigint not null,
  teacher_id                bigint not null,
  cname                     varchar(255),
  constraint pk_classroom primary key (id))
;

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

create table student (
  id                        bigint not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_student primary key (id))
;

create table teacher (
  id                        bigint not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_teacher primary key (id))
;

create table test (
  id                        bigint not null,
  name                      varchar(255),
  test_desc                 varchar(255),
  num_questions             integer,
  classroom_id              bigint,
  constraint pk_test primary key (id))
;

create table test_answer (
  id                        bigint not null,
  test_review_id            bigint not null,
  test_id                   bigint,
  score                     integer,
  constraint pk_test_answer primary key (id))
;

create table test_review (
  id                        bigint not null,
  test_id                   bigint,
  constraint pk_test_review primary key (id))
;


create table classroom_student (
  classroom_id                   bigint not null,
  student_id                     bigint not null,
  constraint pk_classroom_student primary key (classroom_id, student_id))
;

create table student_classroom (
  student_id                     bigint not null,
  classroom_id                   bigint not null,
  constraint pk_student_classroom primary key (student_id, classroom_id))
;

create table student_test (
  student_id                     bigint not null,
  test_id                        bigint not null,
  constraint pk_student_test primary key (student_id, test_id))
;
create sequence classroom_seq;

create sequence question_seq;

create sequence student_seq;

create sequence teacher_seq;

create sequence test_seq;

create sequence test_answer_seq;

create sequence test_review_seq;

alter table classroom add constraint fk_classroom_teacher_1 foreign key (teacher_id) references teacher (id) on delete restrict on update restrict;
create index ix_classroom_teacher_1 on classroom (teacher_id);
alter table question add constraint fk_question_test_2 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_question_test_2 on question (test_id);
alter table test add constraint fk_test_classroom_3 foreign key (classroom_id) references classroom (id) on delete restrict on update restrict;
create index ix_test_classroom_3 on test (classroom_id);
alter table test_answer add constraint fk_test_answer_test_review_4 foreign key (test_review_id) references test_review (id) on delete restrict on update restrict;
create index ix_test_answer_test_review_4 on test_answer (test_review_id);
alter table test_answer add constraint fk_test_answer_test_5 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_test_answer_test_5 on test_answer (test_id);
alter table test_review add constraint fk_test_review_test_6 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_test_review_test_6 on test_review (test_id);



alter table classroom_student add constraint fk_classroom_student_classroo_01 foreign key (classroom_id) references classroom (id) on delete restrict on update restrict;

alter table classroom_student add constraint fk_classroom_student_student_02 foreign key (student_id) references student (id) on delete restrict on update restrict;

alter table student_classroom add constraint fk_student_classroom_student_01 foreign key (student_id) references student (id) on delete restrict on update restrict;

alter table student_classroom add constraint fk_student_classroom_classroo_02 foreign key (classroom_id) references classroom (id) on delete restrict on update restrict;

alter table student_test add constraint fk_student_test_student_01 foreign key (student_id) references student (id) on delete restrict on update restrict;

alter table student_test add constraint fk_student_test_test_02 foreign key (test_id) references test (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists classroom;

drop table if exists classroom_student;

drop table if exists question;

drop table if exists student;

drop table if exists student_classroom;

drop table if exists student_test;

drop table if exists teacher;

drop table if exists test;

drop table if exists test_answer;

drop table if exists test_review;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists classroom_seq;

drop sequence if exists question_seq;

drop sequence if exists student_seq;

drop sequence if exists teacher_seq;

drop sequence if exists test_seq;

drop sequence if exists test_answer_seq;

drop sequence if exists test_review_seq;

