/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/15 15:59:14                           */
/*==============================================================*/


/* drop table if exists permission;

drop table if exists role;

drop table if exists role_permission;

drop table if exists user;

drop index Index_1 on user_auth;

drop table if exists user_auth;

drop table if exists user_role; */

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   id                   bigint not null,
   permission           varchar(20) not null,
   type                 varchar(4) not null,
   url                  varchar(255),
   description          varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   bigint not null,
   role_name            varchar(15) not null,
   description          varchar(100),
   primary key (id)
);

/*==============================================================*/
/* Table: role_permission                                       */
/*==============================================================*/
create table role_permission
(
   id                   bigint not null,
   role_id              bigint not null,
   permission_id        bigint not null,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null,
   nickname             varchar(12) not null,
   avatar               varchar(255) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: user_auth                                             */
/*==============================================================*/
create table user_auth
(
   id                   bigint not null,
   user_id              bigint not null,
   auth_type            varchar(10) not null,
   indentifier          varchar(255) not null,
   credential           varchar(255) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on user_auth
(
   user_id
);

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   id                   bigint not null,
   user_id              bigint not null,
   role_id              bigint not null,
   primary key (id)
);

alter table role_permission add constraint FK_Reference_4 foreign key (role_id)
      references role (id) on delete restrict on update restrict;

alter table role_permission add constraint FK_Reference_5 foreign key (permission_id)
      references permission (id) on delete restrict on update restrict;

alter table user_auth add constraint FK_Reference_1 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table user_role add constraint FK_Reference_2 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table user_role add constraint FK_Reference_3 foreign key (role_id)
      references role (id) on delete restrict on update restrict;

