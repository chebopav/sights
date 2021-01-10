INSERT INTO public.role_table(id, name)
	VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_ADMIN');

INSERT INTO public.country(id, name)
    VALUES
    (1, 'Россия');

INSERT INTO public.city(id, name, country_id)
    VALUES
    (1, 'Санкт-Петербург', 1);