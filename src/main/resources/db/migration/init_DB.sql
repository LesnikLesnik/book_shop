create table books
(
    id bigserial not null,
    name varchar(255) not null,
    brand varchar(255) not null,
    cover varchar(127) not null,
    author varchar(255) not null,
    count int not null
);