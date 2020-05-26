select r.* from sys_resource rs
left join sys_role_resource rr on rr.resource_id = rs.id
left join sys_role r on rr.role_id = r.id
where r.id = '' or r.`code` = 'admin'

select r.* from sys_resource rs
left join sys_role_resource rr on rr.resource_id = rs.id
left join sys_role r on rr.role_id = r.id
left join sys_user_role ur on ur.role_id = r.id
left join sys_user u on ur.user_id = u.id
where u.username = 'test' and rs.type = 1