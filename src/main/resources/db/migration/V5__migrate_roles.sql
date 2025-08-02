alter table public.roles drop column if exists id;
alter table roles add constraint roles_pkey primary key (name);
alter table public.user_roles alter column role_id type varchar(50) using role_id::varchar(50);

