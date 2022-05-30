package com.denilson.rest_api_jersey.domain.model;

public class PokemonEvolution {

	private String num;
	private String name;
	
	public PokemonEvolution() {
	}

	public PokemonEvolution(String num, String name) {
		this.num = num;
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
