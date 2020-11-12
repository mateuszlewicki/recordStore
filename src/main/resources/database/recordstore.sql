drop database if exists recordstore;

create database recordstore;

use recordstore;

create table releases(
                         id bigint auto_increment,
                         code varchar(30) not null,
                         artist varchar(30) not null,
                         title varchar(100) not null,
                         label varchar(30) not null,
                         release_date date not null,
                         genre varchar(30) not null,
                         format varchar(30) not null,
                         price double not null,
                         img varchar(30) not null,
                         quantity int(3) not null,
                         primary key (id));

create table accounts(
                         user_name varchar(30) not null,
                         password varchar(100) not null,
                         user_role varchar(20) not null,
                         primary key(user_name));

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Gantz", "Enso/Siyam", "Innamind Recordings", "2013-02-01", "dubstep", "IMRV003", "10\"", 50.0, "gantz.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Tes La Rok", "Up In The VIP", "Dub Police", "2008-09-01", "dubstep", "DP021", "12\"", 10.0, "teslarok.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Sully", "Vacancy/Digitalis", "Uncertain Hour", "2018-10-12", "jungle", "UH-01", "12\"", 12.0, "sully_vacancy.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Sully", "Soundboy Don't Push Your Luck /368ft High And Rising", "Foxy Jangle", "2018-03-27", "jungle", "Foxy2", "12\"", 20.0, "sully_foxy.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Dimension", "UK/InBleach", "More Than Alot Records", "2017-02-15", "drumandbass", "MTA096", "12\"", 130.0, "dimension.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("V.I.V.E.K", "Where Are You/Step Fwd", "Blacklist", "2017-02-15", "dubstep", "BLACKLIST008", "10\"", 15.0, "vivek.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Egoless", "Decolonize/Global", "Sentry Records", "2018-08-03", "dubstep", "SEN006", "12\"", 8.0, "egoless.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Headland", "Young Blood/Witness", "System Music", "2018-11-02", "dubstep", "SYSTM024", "12\"", 13.0, "headland.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Cymatic", "Electric Church/Jungle Fever", "Box Clever", "2010-11-29", "dubstep", "BOXCLEVER003", "10\"", 5.0, "cymatic.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Aloe Blacc", "I Need A Dollar/Take Me Back", "Stones Throw Records", "2010-04-23", "funk", "STH2246", "12\"", 15.0, "aloe_blacc.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Bonobo", "Black Sands LP", "Ninja Tune", "2010-03-29", "downtempo", "ZEN140", "2x12\"", 15.0, "bonobo.jpg", 1);

insert into releases (artist, title, label, release_date, genre, code, format, price, img, quantity)
values("Sully", "Blue EP", "Keysound Recordings", "2014-06-13", "jungle", "LDN046", "2x12\"", 20.0, "sully.jpg", 1);



