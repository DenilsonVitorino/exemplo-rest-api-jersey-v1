package com.denilson.rest_api_jersey.domain.DAO;

import java.util.List;

import com.denilson.rest_api_jersey.domain.model.Pokemon;

public interface PokemonDAO {
	List<Pokemon> getAll();
	Pokemon getByNum(String num);
	List<Pokemon> getByType(String type);
	Pokemon create(Pokemon pokemon);
	Pokemon update(Pokemon pokemon);
	Boolean delete(String num);
}
