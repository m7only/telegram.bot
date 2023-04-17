--liquibase formatted sql

--changeSet skryagin:1
insert into public.animal_shelters (id, about, address, adoption_rules, animal_type, contacts, opening_hours, safety_rules, security_rules) values (1, 'Приют для кошек в нашем любимом городе.В настоящий момент здесь содежится 159 кошек. За более чем два года нами проделана огромная работа по благоустройству приюта (Поставлен бетонный забор, проведено отопление и вода в дом, обустроена комната для кошек).
Обращаем Ваше внимание, что приют-это благотворительная некоммерческая организация, спонсоров и финансирования у нас нет, а потому мы полностью зависим от пожертвований граждан.', 'г. Город, ул. Улица, д.1', 'Дибилам животных не даем!', 'CAT', '+ 7 999 999 99 99, +7 888 888 88 88', '10:00 - 20:00 Без выходных', 'Нужна привика от бешенства!', 'Перед въездом в поселок будка, там расскажут');
insert into public.animal_shelters (id, about, address, adoption_rules, animal_type, contacts, opening_hours, safety_rules, security_rules) values (2, 'Приют для собак в нашем любимом городе.В настоящий момент здесь содежится 5 собак. Обращаем Ваше внимание, что приют-это благотворительная некоммерческая организация, спонсоров и финансирования у нас нет, а потому мы полностью зависим от пожертвований граждан.', 'с. Село, д.2', 'Хозяевам беляшных и их сотрудникам собак не даем!', 'DOG', '+ 7 999 999 99 99, +7 888 888 88 88', '8:00 - 18:00 Без выходных', 'Всем посетителям иметь намордники!', 'Перед въездом в поселок таможенный пункт, с собой паспорт, права, страховой мед, полис, виза');


insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (1, 'Выберите, пожалуйста, дальнейшее действие.', '/mainMenu_CAT', 'Кошки', 1, null);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (2, 'Вы находитесь в меню приюта для кошек, выберите пожалуйста действие.', '/shelterInfoMenu_CAT', 'О приюте для кошек', 1, 1);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (3, 'Нужна привика от бешенства!', '/pray', 'Обратиться к волонтеру', 1, 2);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (4, 'Нужна привика от бешенства!', '/safety_CAT', 'Правила безопасности', 1, 2);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (5, 'Приют для кошек в нашем любимом городе.В настоящий момент здесь содежится 159 кошек. За более чем два года нами проделана огромная работа по благоустройству приюта (Поставлен бетонный забор, проведено отопление и вода в дом, обустроена комната для кошек).
Обращаем Ваше внимание, что приют-это благотворительная некоммерческая организация, спонсоров и финансирования у нас нет, а потому мы полностью зависим от пожертвований граждан.', '/aboutShelter_CAT', 'О приюте', 1, 2);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (6, '+ 7 999 999 99 99, +7 888 888 88 88', '/contacts_CAT', 'Контакты приюта', 1, 2);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (7, 'В данном разделе мы расскажем, как подготовиться ко встрече с новым членом семьи', '/aloha_CAT', 'Как взять животное', 1, 1);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (8, 'Котику с ограниченными возможностями необходим постоянный, круглосуточный уход. Мы рекомендуем нанять несколько сиделок для круглосуточного удовлетворения потребностей котика.', '/disabled_CAT', 'Котик-ивналид', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (9, 'Взрослому котику необходима большая площадь, расчитывайте примерно 30 кв.м. на каждого члена семьи + 50 кв.м. для одного котика.', '/adult_CAT', 'Дом для котика', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (10, 'Животное нужно прикормить, дать обнюхать себя, дать время, чтобы КОТЭ привыкло к вашему присутствию.', '/introduce_CAT', 'Знакомство с котиком', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (11, 'Никаких гробов для котиков! Максимум шлейка!', '/transport_CAT', 'Транспортировка', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (12, 'Не выдаем животных дебилам, наркоманам, алергикам.', '/reasonOfRefusal_CAT', 'Причины отказа', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (13, 'Изначально котенку хватит 20-30 кв.м. без учета остальных членов семьи.', '/cub_CAT', 'Дом для котенка', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (14, 'Чтобы мы могли отдать вам животное вам нужно подготовить справку об отсутствии аллергии на кошек у всех членов вашей семьи и знакомы. Так же от них нужна справка от наркоголога и психиатра о том, что они не состоят на учете. Рекомендуем пройти военно-летную комиссию, уход за котиком это большая ответственность!', '/documents_CAT', 'Документы', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (16, 'Перед въездом в поселок будка, там расскажут', '/security_CAT', 'Контакты охраны', 1, 2);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (17, 'В данном разделе вы можете отправить отчет по ввереному вам котику.', '/reportInfo_CAT', 'Отправить отчет', 1, 1);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (18, '', '/start', 'Назад, к приютам', 1, 1);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (19, 'Выберите, пожалуйста, дальнейшее действие.', '/mainMenu_DOG', 'Собаки', 2, null);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (20, '', '/start', 'Назад, к приютам', 2, 19);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (21, 'В данном разделе вы можете отправить отчет по ввереной вам собаке.', '/reportInfo_DOG', 'Отправить отчет', 2, 19);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (22, 'В данном разделе мы расскажем, как подготовиться ко встрече с новым членом семьи', '/aloha_DOG', 'Как взять собаку', 2, 19);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (24, 'Животное нужно прикормить, дать обнюхать себя, дать время, чтобы ПСЭ привыкло к вашему присутствию.', '/introduce_DOG', 'Знакомство с собакой', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (25, 'Не выдаем животных дебилам, наркоманам, алергикам.', '/reasonOfRefusal_DOG', 'Причины отказа', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (26, 'Вы находитесь в меню приюта для собак, выберите пожалуйста действие.', '/shelterInfoMenu_DOG', 'О приюте для собак', 2, 19);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (27, 'Приют для собак в нашем любимом городе.В настоящий момент здесь содежится 5 собак. Обращаем Ваше внимание, что приют-это благотворительная некоммерческая организация, спонсоров и финансирования у нас нет, а потому мы полностью зависим от пожертвований граждан.', '/aboutShelter_DOG', 'О приюте', 2, 26);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (28, 'Перед въездом в поселок таможенный пункт, с собой паспорт, права, страховой мед, полис, виза', '/security_DOG', 'Контакты охраны', 2, 26);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (29, '+ 7 999 999 99 99, +7 888 888 88 88', '/contacts_DOG', 'Контакты приюта', 2, 26);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (30, 'Всем посетителям иметь намордники!', '/safety_DOG', 'Правила безопасности', 2, 26);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (31, 'Всем посетителям иметь намордники!', '/pray', 'Обратиться к волонтеру', 2, 26);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (32, 'Изначально собакену хватит будки 0,5 кв.м. без учета остальных членов семьи.', '/cub_DOG', 'Дом для щенка', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (33, 'Чтобы мы могли отдать вам СОБАКЕНА вам нужно подготовить справку об отсутствии аллергии на СОБАКЕНОВ у всех членов вашей семьи и знакомых. Так же от них нужна справка от наркоголога и психиатра о том, что они не состоят на учете. Рекомендуем пройти военно-летную комиссию, уход за котиком это большая ответственность!', '/documents_DOG', 'Документы', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (34, 'Ну тут кинолог советует не бить, а дрессировать.', '/cynologistAdvice_DOG', 'Совет кинолога', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (35, 'Поводок, намордник, металлическая клетка с прутком из кругляка стали 15-25 мм', '/transport_DOG', 'Транспортировка', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (36, 'Взрослой собаке необходима площадь, немногим больше, чем для щенка. Расчитывайте примерно 10 кв.м. на каждого члена семьи и + 0,7 кв.м. для одной собаки.', '/adult_DOG', 'Дом для собаки', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (37, 'Собаке с ограниченными возможностями необходим постоянный, круглосуточный уход. Мы рекомендуем нанять несколько сиделок для круглосуточного удовлетворения потребностей собакена.', '/disabled_DOG', 'Собака-инвалид', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (38, 'Вот вам контакты супермега пупер кинологов', '/cynologist_DOG', 'Наши кинологи', 2, 22);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (15, '', '/getContacts_DOG', 'Оставьте свои данные', 1, 7);
insert into public.endpoints (id, content, endpoint_text, title, animal_shelter_id, parent_id) values (23, '', '/getContacts_CAT', 'Оставьте свои данные', 2, 22);

insert into public.endpoint_relations (parent_id, child_id) values (1, 2);
insert into public.endpoint_relations (parent_id, child_id) values (1, 7);
insert into public.endpoint_relations (parent_id, child_id) values (1, 17);
insert into public.endpoint_relations (parent_id, child_id) values (1, 18);
insert into public.endpoint_relations (parent_id, child_id) values (2, 3);
insert into public.endpoint_relations (parent_id, child_id) values (2, 4);
insert into public.endpoint_relations (parent_id, child_id) values (2, 5);
insert into public.endpoint_relations (parent_id, child_id) values (2, 6);
insert into public.endpoint_relations (parent_id, child_id) values (2, 15);
insert into public.endpoint_relations (parent_id, child_id) values (2, 16);
insert into public.endpoint_relations (parent_id, child_id) values (7, 8);
insert into public.endpoint_relations (parent_id, child_id) values (7, 9);
insert into public.endpoint_relations (parent_id, child_id) values (7, 10);
insert into public.endpoint_relations (parent_id, child_id) values (7, 11);
insert into public.endpoint_relations (parent_id, child_id) values (7, 12);
insert into public.endpoint_relations (parent_id, child_id) values (7, 15);
insert into public.endpoint_relations (parent_id, child_id) values (7, 3);
insert into public.endpoint_relations (parent_id, child_id) values (7, 13);
insert into public.endpoint_relations (parent_id, child_id) values (7, 14);
insert into public.endpoint_relations (parent_id, child_id) values (19, 20);
insert into public.endpoint_relations (parent_id, child_id) values (19, 21);
insert into public.endpoint_relations (parent_id, child_id) values (19, 22);
insert into public.endpoint_relations (parent_id, child_id) values (19, 26);
insert into public.endpoint_relations (parent_id, child_id) values (22, 23);
insert into public.endpoint_relations (parent_id, child_id) values (22, 24);
insert into public.endpoint_relations (parent_id, child_id) values (22, 25);
insert into public.endpoint_relations (parent_id, child_id) values (22, 31);
insert into public.endpoint_relations (parent_id, child_id) values (22, 32);
insert into public.endpoint_relations (parent_id, child_id) values (22, 33);
insert into public.endpoint_relations (parent_id, child_id) values (22, 34);
insert into public.endpoint_relations (parent_id, child_id) values (22, 35);
insert into public.endpoint_relations (parent_id, child_id) values (22, 36);
insert into public.endpoint_relations (parent_id, child_id) values (22, 37);
insert into public.endpoint_relations (parent_id, child_id) values (22, 38);
insert into public.endpoint_relations (parent_id, child_id) values (26, 27);
insert into public.endpoint_relations (parent_id, child_id) values (26, 28);
insert into public.endpoint_relations (parent_id, child_id) values (26, 31);
insert into public.endpoint_relations (parent_id, child_id) values (26, 29);
insert into public.endpoint_relations (parent_id, child_id) values (26, 23);
insert into public.endpoint_relations (parent_id, child_id) values (26, 30);
