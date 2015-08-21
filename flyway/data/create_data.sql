insert into ads_categories (name) values ('Авто');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Возьму в аренду', (select * from parent_id))
  , ('Сдаю в аренду', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Легковые авто', (select * from parent_id))
  , ('Спецтехника, грузовики', (select * from parent_id))
  , ('Запчасти', (select * from parent_id))
  , ('Автохимия, автокосметика и ГСМ', (select * from parent_id))
  , ('Аудио, видео, GPS-навигаторы', (select * from parent_id))
  , ('Инструменты и оборудование', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Аренда посуточно');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Возьму в аренду', (select * from parent_id))
  , ('Сдаю в аренду', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Квартиры', (select * from parent_id))
  , ('Комнаты', (select * from parent_id))
  , ('Дома', (select * from parent_id))
  , ('Дачи', (select * from parent_id))
  , ('Коттеджи', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Бытовая электроника');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Аудиотехника', (select * from parent_id))
  , ('Телевизоры, видеотехника', (select * from parent_id))
  , ('Фототехника', (select * from parent_id))
  , ('Стиральные машины, сушилки', (select * from parent_id))
  , ('Плиты, микроволновки', (select * from parent_id))
  , ('Холодильники, морозильные камеры', (select * from parent_id))
  , ('Комплектующие, аксессуары', (select * from parent_id))
  , ('Другая бытовая техника', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Вело, Мото');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Меняю', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Вело', (select * from parent_id))
  ,('Мото', (select * from parent_id))
  ,('Запчасти', (select * from parent_id))
  ,('Аксессуары', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Детский мир');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Ищу', (select * from parent_id))
  , ('Предлагаю', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Детское питание', (select * from parent_id))
  , ('Одежда и обувь', (select * from parent_id))
  , ('Игрушки', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Дом и дача');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Бытовая химия', (select * from parent_id))
  , ('Хозтовары', (select * from parent_id))
  , ('Посуда и товары для кухни', (select * from parent_id))
  , ('Растения', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Животные');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Отдам', (select * from parent_id))
  , ('Возьму', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Оказываю услуги', (select * from parent_id))
  , ('Воспользуюсь услугами', (select * from parent_id))
  , ('Найдены животные', (select * from parent_id))
  , ('Потеряны животные', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Домашние животные', (select * from parent_id))
  , ('Другие животные', (select * from parent_id))
  , ('Перевозка, выгул, передержка', (select * from parent_id))
  , ('Найдены, потеряны', (select * from parent_id))
  , ('Ветеринария, уход, случка', (select * from parent_id))
  , ('Товары, корм для животных', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Компьютеры и ПО');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Прокат', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Настольные компьютеры', (select * from parent_id))
  , ('Ноутбуки, нетбуки, планшеты', (select * from parent_id))
  , ('Комплектующие аксессуары', (select * from parent_id))
  , ('Сетевое оборудование', (select * from parent_id))
  , ('Программное обеспечение', (select * from parent_id))
  , ('IT-инфраструктура, серверы, АТС', (select * from parent_id))
  , ('Игровые приставки и аксессуары', (select * from parent_id))
  , ('Игры', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Красота');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Косметика, парфюмерия', (select * from parent_id))
  , ('БАДы', (select * from parent_id))
  , ('Товары для здоровья', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Мебель и интерьер');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Мебель', (select * from parent_id))
  , ('Предметы интерьера', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Находки, потери');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Ищу', (select * from parent_id))
  , ('Найдено', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Утеряно', (select * from parent_id))
  , ('Найдено', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Недвижимость');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Возьму в аренду', (select * from parent_id))
  , ('Сдаю в аренду', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Квартиры', (select * from parent_id))
  , ('Комнаты', (select * from parent_id))
  , ('Дома', (select * from parent_id))
  , ('Дачи', (select * from parent_id))
  , ('Коттеджи', (select * from parent_id))
  , ('Земельные участки', (select * from parent_id))
  , ('Гаражи', (select * from parent_id))
  , ('Коммерческая недвижимость', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Одежда, Обувь, Аксессуары');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Мужская одежда', (select * from parent_id))
  , ('Женская одежда', (select * from parent_id))
  , ('Верхняя одежда мужская', (select * from parent_id))
  , ('Верхняя одежда женская', (select * from parent_id))
  , ('Обувь мужская', (select * from parent_id))
  , ('Обувь женская', (select * from parent_id))
  , ('Аксессуары, галантерея', (select * from parent_id))
  , ('Головные уборы', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Оргтехника, Кацтовары');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Оргтехника', (select * from parent_id))
  , ('Расходные материалы', (select * from parent_id))
  , ('Канцтовары', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Отдам, Возьму бесплатно, Обменяю');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Отдам бесплатно', (select * from parent_id))
  , ('Возьму бесплатно', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Отдам бесплатно', (select * from parent_id))
  , ('Возьму бесплатно', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Продукты питания');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('В розницу', (select * from parent_id))
  , ('Оптом', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Профессиональное оборудование');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Для магазинов', (select * from parent_id))
  , ('Для производств, заводов', (select * from parent_id))
  , ('Для кафе, ресторанов', (select * from parent_id))
  , ('Для предприятий бытовых услуг', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Работа');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Вакансии', (select * from parent_id))
  , ('Резюме', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Топ-менеджмент, руководство', (select * from parent_id))
  , ('Автомобильный бизнес, автосервис', (select * from parent_id))
  , ('Административный персонал, АХО', (select * from parent_id))
  , ('Бухгалтерия, аудит, консалтинг', (select * from parent_id))
  , ('Бытовые услуги, сервисное обслуживание', (select * from parent_id))
  , ('Государственная служба, некоммерческие организации', (select * from parent_id))
  , ('Добыча сырья, ТЭК', (select * from parent_id))
  , ('Домашний персонал', (select * from parent_id))
  , ('ЖКХ, благоустройство, эксплуатация', (select * from parent_id))
  , ('Информационные технологии, интернет', (select * from parent_id))
  , ('Культура, искусство', (select * from parent_id))
  , ('Логистика, транспорт, склад', (select * from parent_id))
  , ('Маркетинг, реклама, PR', (select * from parent_id))
  , ('Медицина, фармацевтика', (select * from parent_id))
  , ('Начало карьеры, без опыта работы', (select * from parent_id))
  , ('Образование, наука, языки', (select * from parent_id))
  , ('Отдел кадров, обучение персонала, HR', (select * from parent_id))
  , ('Охрана, служба безопасности', (select * from parent_id))
  , ('Подработка, временная работа', (select * from parent_id))
  , ('Продажи, сбыт, торговля', (select * from parent_id))
  , ('Промышленность, производство', (select * from parent_id))
  , ('Рабочие специальности, разнорабочие', (select * from parent_id))
  , ('Развлечения, рестораны, кафе', (select * from parent_id))
  , ('Сельское хозяйство, агропромышленность', (select * from parent_id))
  , ('СМИ, дизайн, издательства, полиграфия', (select * from parent_id))
  , ('Спорт, красота, здоровье', (select * from parent_id))
  , ('Страхование', (select * from parent_id))
  , ('Строительство, ремонт, недвижимость', (select * from parent_id))
  , ('Телекоммуникации, связь', (select * from parent_id))
  , ('Туризм, гостиничное дело', (select * from parent_id))
  , ('Финансы, экономика, банковская сфера', (select * from parent_id))
  , ('Юриспруденция', (select * from parent_id))
  , ('Другое', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Ремонт и строительство');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Стройматериалы', (select * from parent_id))
  , ('Сантехника', (select * from parent_id))
  , ('Инструменты и оборудование', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Спорт и здоровье');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Спортинвентарь', (select * from parent_id))
  , ('Спортивное питание', (select * from parent_id))
  , ('БАДы', (select * from parent_id))
  , ('Медтехника и оборудование', (select * from parent_id))
  , ('Товары для здоровья', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Телефоны и связь');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Сотовые телефоны', (select * from parent_id))
  , ('Комплектующие и аксессуары', (select * from parent_id))
  , ('Радиотехника', (select * from parent_id))
  , ('Станционарные телефоны', (select * from parent_id))
  , ('SIM-карты', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Транспортировка, Услуги спецтехники');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Оказываю услуги', (select * from parent_id))
  , ('Воспользуюсь услугами', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Пассажирские перевозки', (select * from parent_id))
  , ('Грузовые перевозки', (select * from parent_id))
  , ('Спецтехника', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Туризм, Охота, Рыбалка');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Турснаряжение', (select * from parent_id))
  , ('Снаряжение охоты и рыбалки', (select * from parent_id))
  , ('Лодки, катера, моторы', (select * from parent_id))
  , ('Разное', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Украшения, Часы');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Украшения', (select * from parent_id))
  , ('Часы', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Услуги');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Оказываю услуги', (select * from parent_id))
  , ('Воспользуюсь услугами', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Авто', (select * from parent_id))
  , ('Аренда оборудования', (select * from parent_id))
  , ('Безопасность', (select * from parent_id))
  , ('Бухгалтерия и финансы', (select * from parent_id))
  , ('Грузчики', (select * from parent_id))
  , ('Детский мир', (select * from parent_id))
  , ('Дипломы, курсовые, рефераты, контрольные', (select * from parent_id))
  , ('Красота и здоровье', (select * from parent_id))
  , ('Мебель, ремонт и изготовление', (select * from parent_id))
  , ('Курсы, семинары, тренинги', (select * from parent_id))
  , ('Няни, сиделки', (select * from parent_id))
  , ('Образование, обучение', (select * from parent_id))
  , ('Перевод', (select * from parent_id))
  , ('Питание, кейтеринг', (select * from parent_id))
  , ('Пошив, ремонт одежды, обуви', (select * from parent_id))
  , ('Праздники и мероприятия', (select * from parent_id))
  , ('Реклама и полиграфия', (select * from parent_id))
  , ('Ремонт, установка техники', (select * from parent_id))
  , ('Ремонт компьютеров, программное обеспечение', (select * from parent_id))
  , ('Ремонт мобильных устройств', (select * from parent_id))
  , ('Репетиторство', (select * from parent_id))
  , ('Риэлторские', (select * from parent_id))
  , ('Сантехники, слесари', (select * from parent_id))
  , ('Строительство и ремонт', (select * from parent_id))
  , ('Уборка', (select * from parent_id))
  , ('Украшения и часы, ремонт и изготовление', (select * from parent_id))
  , ('Уход за животными', (select * from parent_id))
  , ('Фото- и видеосъёмка', (select * from parent_id))
  , ('Юридические услуги', (select * from parent_id))
  , ('Другое', (select * from parent_id));

------------------------------------------------------------------------------------------------------------------------
insert into ads_categories (name) values ('Хобби, Диски, Литература');

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_types (name, category_id) values
  ('Продаю', (select * from parent_id))
  , ('Куплю', (select * from parent_id))
  , ('Обмен', (select * from parent_id))
  , ('Другое', (select * from parent_id));

with parent_id as (SELECT currval('ads_categories_id_seq'))
insert into ads_categories (name, parent_category) values
  ('Антиквариат, коллекционные издания', (select * from parent_id))
  , ('Музыкальные инструменты', (select * from parent_id))
  , ('Книги, журналы', (select * from parent_id))
  , ('DVD, CD, Blue-Ray и др.', (select * from parent_id))
  , ('Художественные принадлежности', (select * from parent_id))
  , ('Произведения искусства', (select * from parent_id))
  , ('Разное', (select * from parent_id));