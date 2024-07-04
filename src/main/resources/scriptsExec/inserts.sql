-- insert da tabela perfil_usuario
insert into ccb.perfil_usuario (id_perfil, tipo_perfil) values (1,'ADM');
insert into ccb.perfil_usuario (id_perfil, tipo_perfil) values (2, 'USER');


-- insert da tabela sistema_acessos
insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (1,  'Tela cadastro de usuario');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (2,  'Tela cadastro de administracao');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (3,  'Tela cadastro de setores');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (4,  'Tela cadastro de casa de oracoes');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (5,  'Tela cadastro de reuniao');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (6,  'Tela cadastro de participantes');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (7,  'Tela cadastro de prioridades');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (8,  'Tela cadastro de categoria');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (10, 'Tela cadastro de demandas');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (11, 'Acesso ao menu Cadastro');

insert into ccb.sistema_acessos (id_acesso, nome_acesso) values (12, 'Acesso ao menu Geral');




--- insert da tabela cruzamento_perfil_acesso
insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (1, 1, 1);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (2, 1, 2);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (3, 1, 3);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (4, 1, 4);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (5, 1, 5);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (6, 1, 6);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (7, 1, 7);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (8, 1, 8);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (10, 2, 10);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (11, 1, 10);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (12, 1, 11);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (13, 2, 12);

insert into ccb.cruzamento_perfil_acesso (id_perfil_acesso, id_perfil, id_acesso)
values (14, 1, 12);




-- insert da tabela menu_acesso
    insert into ccb.menu_acesso (id_menu, id_acesso, nome_menu, icon_class, path_route) values (
	1, 11, 'Cadastro', 'bi bi-building-fill-add', null );

	insert into ccb.menu_acesso (id_menu,id_menu_pai, id_acesso,  nome_menu, icon_class, path_route) values (
	2, 1, 1, 'Usuários', 'bi bi-person-plus-fill', '/usuarios'  );

    insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso, nome_menu, icon_class, path_route) values (
	3, 1, 2, 'Administração', 'bi bi-diagram-2-fill', '/administração');

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso, nome_menu, icon_class, path_route) values (
	4, 1, 3, 'Setores', 'bi bi-bar-chart-steps', '/setores');

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso,  nome_menu, icon_class, path_route) values (
	5, 1, 4, 'Casa de Orações', 'bi bi-buildings-fill', '/casa-oracoes');

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso,  nome_menu, icon_class, path_route) values (
	6, 1, 5, 'Reuniões', 'bi bi-wechat', '/reunioes');

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso,  nome_menu, icon_class, path_route) values (
	7, 1, 6, 'Participantes', 'bi bi-person-lines-fill', '/participantes');

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso,  nome_menu, icon_class, path_route) values (
	8, 1, 7, 'Prioridades', 'bi bi-patch-exclamation-fill', '/prioridades');

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso,  nome_menu, icon_class, path_route) values (
	9, 1, 8, 'Categorias', 'bi bi-filter-square-fill', '/categorias');

	insert into ccb.menu_acesso (id_menu, id_acesso, nome_menu, icon_class, path_route) values (
	10, 12, 'Geral', 'bi bi-gear-fill', null );

	insert into ccb.menu_acesso (id_menu, id_menu_pai, id_acesso, nome_menu, icon_class, path_route) values (
	11, 10, 10, 'Demandas', 'bi bi-pencil-square','/demandas');







