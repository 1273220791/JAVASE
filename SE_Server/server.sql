-- 创建用户表的索引
drop sequence user17_id_seq;
create sequence user17_id_seq;

-- 创建用户表
drop table user17 cascade constraints;
create table user17(
  id number,
  username varchar(50),
  password varchar(50),
  nickname varchar(50),
  ismanager number,
  constraint user17_id_pk primary key(id)
);

-- 添加用户表的数据
insert into user17 values(user17_id_seq.nextval,'admin','123456','manager',1);
insert into user17 values(user17_id_seq.nextval,'user','123456','username',0);



-- 创建试题表的索引
drop sequence exam_id_seq;
create sequence exam_id_seq;

-- 创建试题表
drop table exam cascade constraints;
create table exam(
  id number,
  problems varchar(200),
  optionA varchar(200),
  optionB varchar(200),
  optionC varchar(200),
  optionD varchar(200),
  answer varchar(50),
  constraint exam_id_pk primary key(id)
);

-- 试题表中插入数据
insert into exam values(exam_id_seq.nextval,'1+1=?','1','2','3','4','B');
insert into exam values(exam_id_seq.nextval,'谁最帅?','邓启坤','王尼玛','敖厂长','卡卡罗特','A');
insert into exam values(exam_id_seq.nextval,'北纬43度有一只熊两秒钟掉入陷阱,请问是什么品种','白熊','北极熊','棕熊','灰熊','C');
insert into exam values(exam_id_seq.nextval,'1-1X999999=?','1','0','999999','-999998','D');
insert into exam values(exam_id_seq.nextval,'美国是于哪一年宇宙飞船登月成功?','1964','1965','1966','1967','C');
insert into exam values(exam_id_seq.nextval,'世界四大洋中面积最小的是?','太平洋','大西洋','印度洋','北冰洋','D');
insert into exam values(exam_id_seq.nextval,'世界上海拔最高的山峰是哪一座?','乔戈里峰','珠穆朗玛峰','干城章嘉峰','公格尔山峰','C');
insert into exam values(exam_id_seq.nextval,'下列海洋中哪一个是我国最大的海?','南海','东海','黄海','渤海','A');
insert into exam values(exam_id_seq.nextval,'下列哪一个湖泊是在我国江西省的境内?','洞庭湖','潘阳湖','太湖','洪泽湖','B');
insert into exam values(exam_id_seq.nextval,'石头城是对我国哪座城市的美称?','南昌','南京','拉萨','西安','B');
insert into exam values(exam_id_seq.nextval,'下列我国名茶中哪一种是产于福建安溪?','龙井','碧螺春','武夷岩茶','铁观音','D');
insert into exam values(exam_id_seq.nextval,'吉林省抚松县被人们称为是哪种药材之乡?','当归','枸杞','人参','田七','C');



-- 创建用户表和试题表关联表的索引
drop sequence user17_exam_id_seq;
create sequence user17_exam_id_seq;

-- 创建用户表和试题表的关联表
drop table user17_exam cascade constraints;
create table user17_exam(
  id number,
  user_id number,
  exam_id number,
  user_result varchar(50),
  exam_time varchar(200),
  souce number,
  constraint user17_exam_id_pk primary key(id),
  constraint user17_exam_user_id_fk foreign key(user_id) references user17(id),
  constraint user17_exam_exam_id_fk foreign key(exam_id) references exam(id)
);


