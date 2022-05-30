CREATE TABLE pokemon (
	id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	num TEXT NOT NULL UNIQUE,
	name TEXT NOT NULL,
	pre_evolution TEXT NULL,
	next_evolution TEXT NOT NULL
);

CREATE TABLE pokemon_type (
	id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	name TEXT NOT NULL,
	pokemon_num TEXT NOT NULL
);