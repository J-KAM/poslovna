INSERT INTO `quatrofantastico`.`measurement_unit` (`active`,`created`,`updated`,`name`, `label`)  VALUES (1,now(),null,"GRAM","gr"), (1,now(),null,"KILOGRAM","kg"), (1,now(),null,"TONA","t"),  (1,now(),null,"KOMAD","kom"), (1,now(),null, "LITAR", "l");

INSERT INTO `quatrofantastico`.`location` (`active`,`created`,`updated`,`name`,`ptt`) VALUES (1,now(),null, "Beograd","11000"), (1,now(),null, "Novi Sad","21000"), (1,now(),null, "Zrenjanin","23000"),  (1,now(),null, "Sremska Mitrovica","22000"), (1,now(),null, "Niš","18000");

INSERT INTO `quatrofantastico`.`company` (`active`,`created`,`updated`,`address`,`name`,`pib`,`location_id`) VALUES (1, now(), null, "Pozeska BB", "Pionir d.o-o.", 111111111, 1), (1, now(), null, "Zrenjaninski put 100", "Imlek", 222222222, 1), (1, now(), null, "Jovana Cvijica 13", "Dijamant d.o.o.", 333333333, 3), (1, now(), null, "Bulevar Oslobodjenja 112", "3G Shop d.o-o.", 444444444, 2);

INSERT INTO `quatrofantastico`.`business_year` (`active`, `created`, `updated`,`closed`, `year`, `company_id`) VALUES (1,now(),null,true,2015, 1), (1,now(), null, true, 2016, 1), (1, now(), null, false, 2017, 1);

INSERT INTO `quatrofantastico`.`business_partner` (`active`,`created`,`updated`,`address`,`name`,`partnership_type`,`pib`,`company_id`,`location_id`) VALUES (1,now(),null,"Janka Cmelika 29", "MAX IMPORT-EXPORT", "SUPPLIER",12121212, 1, 2), (1,now(),null,"Gunduliceva 100", "4x4 OPREMA D.O.O","ALL",12121333, 2, 1), (1,now(),null,"Stevana Sremca 13", "DEXI SHPED", "SUPPLIER", 121245676, 1, 4), (1,now(),null,"Prva proleterska 1", "MAXI", "BUYER", 133345675, 1, 5), (1,now(),null,"Zelengorska 13", "UNIVEREXPORT D.O.O.", "BUYER", 555555555, 1, 2);

INSERT INTO `quatrofantastico`.`ware_group` (`active`,`created`,`updated`,`name`,`company_id`) VALUES (1,now(),null,"PREHRANA",1),(1,now(),null,"HEMIJA",1), (1,now(),null,"STOLARIJA",3), (1,now(),null,"PREHRANA",3),  (1,now(),null,"HEMIJA",2), (1,now(),null,"PETROHEMIJA",3), (1,now(),null,"PREHRANA",2), (1,now(),null,"TEHNICKA ROBA",4);

INSERT INTO `quatrofantastico`.`ware` (`active`,`created`,`updated`,`name`,`packing`,`measurement_unit_id`,`ware_group_id`) VALUES (1,now(),null, "Cokolada Seka", 100, 1, 1), (1,now(),null, "Cokolada Bata", 100, 1, 1), (1,now(),null, "Negro bombone", 100, 1, 1), (1,now(),null, "Negro bombone", 200, 1, 1), (1,now(),null, "Medeno srce", 150, 1, 1), (1,now(),null, "Medeno srce", 350, 1, 1), (1,now(),null, "Medeno srce-visnja", 150, 1, 1), (1,now(),null, "Minjon kocka", 260, 1, 1), (1,now(),null, "Dzem sipurak", 680, 1, 1), (1,now(),null, "Minjon kocka-stracatela i brusnica", 260, 1, 1), (1,now(),null, "Prozor Eco+", 4, 4, 3), (1,now(),null, "Prozor Focus", 1, 4, 3), (1,now(),null, "Philips Genie sijalica 8Y", 1, 4, 4), (1,now(),null, "Philips Minjon sijalica bistra E14 40W", 1, 4, 4), (1,now(),null, "Philips Minjon sijalica bistra E14 60W", 1, 4, 4), (1,now(),null, "Philips Minjon sijalica bistra E14 60W", 1, 4, 4), (1,now(),null, "Krema za ruke", 1, 1, 2), (1,now(),null, "Sampon Sauma-7 trava", 0.25, 5, 2),  (1,now(),null, "Sampon Sauma-7 trava", 0.5, 5, 2), (1,now(),null, "Sampon Sauma-za farbanu kosu", 0.25, 5, 2), (1,now(),null, "Sampon Sauma-za farbanu kosu", 0.5, 5, 2), (1, now(), null, "NIVEA HAIRMILK SAMPON ZA TANKU KOSU", 0.25, 5, 2), (1,now(),null, "NIVEA HAIRMILK SAMPON ZA GUSTU KOSU", 0.25, 5, 2), (1,now(),null, "NIVEA HAIRMILK SAMPON ZA GUSTU KOSU", 0.5, 5, 2), (1,now(),null, "MERIX POLJSKO CVECE PRASAK ZA VES", 2, 2, 2), (1,now(),null, "MERIX POLJSKO CVECE PRASAK ZA VES", 3, 2, 2), (1,now(),null, "FAKS SUPERAKTIV ULTRA CARE DETERDZENT ZA PRANJE VESA", 2, 2, 2), (1,now(),null, "FAKS SUPERAKTIV ULTRA CARE DETERDZENT ZA PRANJE VESA", 3, 2, 2), (1,now(),null, "FAIRY PLATINUM LEMON&LIME TECNI DETERDŽENT ZA PRANJE SUDOVA", 0.45, 5, 2), (1,now(),null, "DAVIDOFF ADVENTURE EDT MAN MUSKI PARFEM", 0.05, 5, 2);

INSERT INTO `quatrofantastico`.`user` (`active`,`created`,`updated`,`address`,`enabled`,`first_name`,`jmbg`,`last_name`,`password`,`role`,`username`) VALUES (1,now(),null,"Pariskih komuna 10",1, "Pera",1204966345060,"Peric", "222", "EMPLOYEE", "pera"), (1,now(),null,"Kikindska 68", 1, "Mika",2104966345060,"Mikic", "111", "EMPLOYEE", "mika"), (1,now(),null,"Zelengorska 2a",1, "Admin", 2901975345161,"Admin", "000", "ADMIN", "admin"), (1,now(),null,"Kikindska 52", 1, "Zika",2106961345060,"Zikic", "333", "EMPLOYEE", "zika");

INSERT INTO `quatrofantastico`.`employee` (`id`,`company_id`,`location_id`) VALUES (1, 1, 1), (2, 1, 1), (4, 2, 1);

INSERT INTO `quatrofantastico`.`warehouse` (`active`,`created`,`updated`,`name`,`company_id`,`employee_id`) VALUES (1,now(),null,"Magacin hemijskih proizvoda", 1, 1), (1,now(),null,"Magacin prehrambenih proizvoda", 1, 1), (1,now(),null,"Magacin tehnicke opreme", 1, 2);

INSERT INTO `quatrofantastico`.`document` (active, created, updated, booking_date, document_type, establishment_date, serial_number, status, business_partner_id, business_year_id, inner_warehouse_id, warehouse_id) VALUES (1, '2017-07-07 13:57:01', '2017-07-07 14:57:01', '2017-07-07 14:57:01', "RECEIPT", '2017-07-07 13:57:01', 124, "BOOKED", 1, 3, null, 1), (1, now(), null, null, "RECEIPT", now(), 123, "PENDING", 1, 3, null, 1), (1, now(), null, null, "RECEIPT", now(), 125, "PENDING", 1, 3, null, 1), (1, now(), null, null, "DISPATCH", now(), 128, "PENDING", 1, 3, null, 1), (1, now(), null, null, "INTER_WAREHOUSE_TRAFFIC", now(), 133, "PENDING", null, 3, 2, 1);

INSERT INTO `quatrofantastico`.`document_unit` (`active`,`created`,`updated`,`price`,`quantity`,`value`,`document_id`,`ware_id`) VALUES (1, now(), null, 15, 10, 150, 1, 17), (1, now(), null, 300, 2, 600, 1, 19), (1, now(), null, 250, 1, 250, 1, 23), (1, now(), null, 800, 1, 800, 1, 25), (1, now(), null, 350, 2, 700, 1, 29);

INSERT INTO `quatrofantastico`.`warehouse_card`(`active`, `created`, `updated`,`average_price`,`initial_quantity`,`initial_value`,`quantity_entry_traffic`,`quantity_exit_traffic`,`total_quantity`,`total_value`,`value_entry_traffic`,`value_exit_traffic`,`business_year_id`,`ware_id`,`warehouse_id`) VALUES (1,'2017-07-07 13:57:01',NULL,75,10,500,5,3,12,775,500,225,1,3,2),(1,'2017-07-07 14:03:57',NULL,15,0,0,10,0,10,150,150,0,3,17,1),(1,'2017-07-07 14:03:57',NULL,300,0,0,2,0,2,600,600,0,3,19,1),(1,'2017-07-07 14:03:57',NULL,250,0,0,1,0,1,250,250,0,3,23,1),(1,'2017-07-07 14:03:58',NULL,800,0,0,1,0,1,800,800,0,3,25,1),(1,'2017-07-07 14:03:58',NULL,350,0,0,2,0,2,700,700,0,3,29,1);

INSERT INTO `quatrofantastico`.`warehouse_card_analytics`(`active`,`created`,`updated`,`average_price`,`direction`,`quantity`,`traffic_type`,`value`,`warehouse_card_id`) VALUES (1,'2017-07-07 13:57:01',NULL,100,'INCOMING',5,'RECEIPT',500,1),(1,'2017-07-07 13:57:01',NULL,75,'OUTGOING',3,'DISPATCH',225,1),(1,'2017-07-07 14:03:57',NULL,15,'INCOMING',10,'RECEIPT',150,2),(1,'2017-07-07 14:03:57',NULL,300,'INCOMING',2,'RECEIPT',600,3),(1,'2017-07-07 14:03:58',NULL,250,'INCOMING',1,'RECEIPT',250,4),(1,'2017-07-07 14:03:58',NULL,800,'INCOMING',1,'RECEIPT',800,5),(1,'2017-07-07 14:03:58',NULL,350,'INCOMING',2,'RECEIPT',700,6);


