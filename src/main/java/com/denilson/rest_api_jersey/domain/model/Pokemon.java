package com.denilson.rest_api_jersey.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String num;
	private String name;
	private List<String> type = new ArrayList<String>();
	private PokemonEvolution pre_evolution;
	private PokemonEvolution next_evolution;
	
	public Pokemon() {
		
	}

	public Pokemon(Integer id, String num, String name, List<String> type, PokemonEvolution pre_evolution,
			PokemonEvolution next_evolution) {
		super();
		this.id = id;
		this.num = num;
		this.name = name;
		this.type = type;
		this.pre_evolution = pre_evolution;
		this.next_evolution = next_evolution;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public PokemonEvolution getPre_evolution() {
		return pre_evolution;
	}

	public void setPre_evolution(PokemonEvolution pre_evolution) {
		this.pre_evolution = pre_evolution;
	}

	public PokemonEvolution getNext_evolution() {
		return next_evolution;
	}

	public void setNext_evolution(PokemonEvolution next_evolution) {
		this.next_evolution = next_evolution;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
