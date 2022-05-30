package com.denilson.rest_api_jersey.domain.DAO;

import java.util.List;

public interface PokemonTypeDAO {
	List<String> getByNum(String num);
	String getByTypeAndNum(String type, String num);
	Boolean create(String type, String num);
	Boolean deleteAll(String num);
}
