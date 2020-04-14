-- 测试用户初始化
-- 用户
insert into company_xc.act_id_user values('000001','','丽红','林','2830174715@qq.com','123456',null);
insert into company_xc.act_id_user values('000002','','红','林','2830174715@qq.com','123456',null);
insert into company_xc.act_id_user values('000003','','丽','林','2830174715@qq.com','123456',null);
insert into company_xc.act_id_user values('000004','','红红','林','2830174715@qq.com','123456',null);
insert into company_xc.act_id_user values('000005','','丽丽','林','2830174715@qq.com','123456',null);

-- 角色
insert into company_xc.act_id_group(ID_,NAME_) values('1','管理员');
insert into company_xc.act_id_group(ID_,NAME_) values('2','普通用户');


-- 用户角色关系
insert into company_xc.act_id_membership values('000001','1');
insert into company_xc.act_id_membership values('000002','2');
insert into company_xc.act_id_membership values('000003','2');
insert into company_xc.act_id_membership values('000004','2');
insert into company_xc.act_id_membership values('000005','2');