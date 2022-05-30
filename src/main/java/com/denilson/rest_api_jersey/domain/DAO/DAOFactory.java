package com.denilson.rest_api_jersey.domain.DAO;

import com.denilson.rest_api_jersey.domain.db.DbConfig;
import com.denilson.rest_api_jersey.domain.service.PokemonServiceImpl;
import com.denilson.rest_api_jersey.domain.service.PokemonTypeServiceImpl;

public class DAOFactory {
	public static PokemonDAO createPokemonDAO() {
		return new PokemonServiceImpl(DbConfig.connect());
    }
	
	public static PokemonTypeDAO createPokemonTypeDAO() {
		return new PokemonTypeServiceImpl(DbConfig.connect());
    }
}
