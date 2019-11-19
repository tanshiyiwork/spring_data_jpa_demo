-- Create table
create table CARD_LOSE_INFO
(
  st_id   VARCHAR2(50) not null,
  st_01   VARCHAR2(200),
  st_02   VARCHAR2(200),
  st_03   VARCHAR2(200),
  st_04   VARCHAR2(200),
  st_05   VARCHAR2(200),
  st_06   VARCHAR2(200),
  st_07   VARCHAR2(200),
  st_08   VARCHAR2(200),
  st_09   VARCHAR2(200),
  st_10   VARCHAR2(200),
  st_11   VARCHAR2(200),
  st_12   VARCHAR2(200),
  st_13   VARCHAR2(200),
  st_14   VARCHAR2(200),
  st_15   VARCHAR2(200),
  st_16   VARCHAR2(200),
  st_type VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table CARD_LOSE_INFO
  is '执法证遗失信息';
-- Add comments to the columns 
comment on column CARD_LOSE_INFO.st_id
  is '主键';
comment on column CARD_LOSE_INFO.st_01
  is '字段1';
comment on column CARD_LOSE_INFO.st_02
  is '字段2';
comment on column CARD_LOSE_INFO.st_03
  is '字段3';
comment on column CARD_LOSE_INFO.st_04
  is '字段4';
comment on column CARD_LOSE_INFO.st_05
  is '字段5';
comment on column CARD_LOSE_INFO.st_06
  is '字段6';
comment on column CARD_LOSE_INFO.st_07
  is '字段7';
comment on column CARD_LOSE_INFO.st_08
  is '字段8';
comment on column CARD_LOSE_INFO.st_09
  is '字段9';
comment on column CARD_LOSE_INFO.st_10
  is '字段10';
comment on column CARD_LOSE_INFO.st_11
  is '字段11';
comment on column CARD_LOSE_INFO.st_12
  is '字段12';
comment on column CARD_LOSE_INFO.st_13
  is '字段13';
comment on column CARD_LOSE_INFO.st_14
  is '字段14';
comment on column CARD_LOSE_INFO.st_15
  is '字段15';
comment on column CARD_LOSE_INFO.st_16
  is '字段16';
comment on column CARD_LOSE_INFO.st_type
  is '类型';
-- Create/Recreate primary, unique and foreign key constraints 
alter table CARD_LOSE_INFO
  add constraint PK_CARD_LOSE_INFO primary key (ST_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
