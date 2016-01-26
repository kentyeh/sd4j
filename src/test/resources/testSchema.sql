CREATE TABLE supervisor(
  account    VARCHAR(15) primary key,
  password   varchar(20) not null
);
INSERT INTO supervisor(account,password) values('kentyeh','password');
CREATE TABLE member(
  id         IDENTITY primary key,
  name       VARCHAR(20) not null unique,
  userType   VARCHAR(1) not null,
  version   datetime not null default CURRENT_TIMESTAMP,
  constraint member_ck1 CHECK userType in ('C','V','W')
);
INSERT INTO member(name,userType) values('Jose','C');
INSERT INTO member(name,userType) values('RooBeck','C');
INSERT INTO member(name,userType) values('Kent Yeh','V');
INSERT INTO member(name,userType) values('WareHouse','W');
CREATE TABLE phone(
  id        IDENTITY primary key,
  uid       BIGINT,
  phone     varchar(15) not null,
  constraint phone_fk1 FOREIGN KEY(uid) REFERENCES member(id) ON UPDATE CASCADE ON DELETE SET NULL
);
INSERT INTO phone(uid,phone) values(1,'7777777');
INSERT INTO phone(uid,phone) values(2,'8888888');
CREATE TABLE contactbook(
  oid          BIGINT , /*Owner ID*/
  cid          BIGINT,  /*Contact ID*/
  constraint contactbook_pk primary key(oid,cid),
  constraint cb_fk1 FOREIGN KEY(oid) REFERENCES member(id) ON UPDATE CASCADE,
  constraint cb_fk2 FOREIGN KEY(cid) REFERENCES member(id) ON UPDATE CASCADE
);
INSERT INTO contactbook SELECT a.id,b.id FROM member as a inner join member as b on b.name='Jose' where a.name='WareHouse';
INSERT INTO contactbook SELECT a.id,b.id FROM member as a inner join member as b on b.name='Kent Yeh' where a.name='WareHouse';
INSERT INTO contactbook SELECT a.id,b.id FROM member as a inner join member as b on b.name='Jose' where a.name='Kent Yeh';

CREATE TABLE storage(
  sid    INT primary key,
  location varchar(10)
);
INSERT INTO storage values(1,'Taipei');
INSERT INTO storage values(2,'Taichung');
INSERT INTO storage values(3,'Kaohsiung');

CREATE TABLE userstore(
  id    BIGINT not null,
  sid   int  not null,
  goods varchar(100) not null,
  constraint us_pk primary key(id,sid,goods),
  constraint us_fk1 FOREIGN KEY(id) REFERENCES member(id) ON UPDATE CASCADE,
  constraint us_fk2 FOREIGN KEY(sid) REFERENCES storage(sid) ON UPDATE CASCADE
);
INSERT INTO userstore SELECT id,1,'Flowers' FROM member WHERE name='Jose';
INSERT INTO userstore SELECT id,2,'Rices' FROM member WHERE name='Jose';
INSERT INTO userstore SELECT id,3,'Grapes' FROM member WHERE name='Jose';