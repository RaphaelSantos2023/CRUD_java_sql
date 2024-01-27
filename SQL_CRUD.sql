create database UnMEP_DB;

set sql_safe_updates = 1;

use UnMEP_DB;

create table tarefas(
tar_id int auto_increment primary key,
tar_titulo varchar(50),
tar_descricao varchar(200),
tar_status varchar(50),
tar_data date
);