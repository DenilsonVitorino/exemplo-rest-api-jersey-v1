package com.denilson.rest_api_jersey.domain.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.denilson.rest_api_jersey.domain.DAO.PokemonDAO;
import com.denilson.rest_api_jersey.domain.db.DbConfig;
import com.denilson.rest_api_jersey.domain.exception.DbException;
import com.denilson.rest_api_jersey.domain.model.Pokemon;
import com.denilson.rest_api_jersey.domain.model.PokemonEvolution;

public class PokemonServiceImpl implements PokemonDAO {

	private Connection connection;

    public PokemonServiceImpl(Connection connection) {
        this.connection = connection;
    }

	@Override
	public List<Pokemon> getAll() {
		String query = "select * from pokemon order by num";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pokemon> pokemons = new ArrayList<Pokemon>();
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	Pokemon pokemon = new Pokemon();
            	pokemon.setId(resultSet.getInt(1));
            	pokemon.setNum(resultSet.getString(2));
            	pokemon.setName(resultSet.getString(3));
            	pokemon.setPre_evolution(getPokemonEvolutionByNum(resultSet.getString(4)));
            	pokemon.setNext_evolution(getPokemonEvolutionByNum(resultSet.getString(5)));             	
            	pokemons.add(pokemon);
            }
            return pokemons;
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
	}

	@Override
	public Pokemon getByNum(String num) {
		String query = "select * from pokemon where num = ?";	
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;       
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	Pokemon pokemon = new Pokemon();
            	pokemon.setId(resultSet.getInt(1));
            	pokemon.setNum(resultSet.getString(2));
            	pokemon.setName(resultSet.getString(3));
            	pokemon.setPre_evolution(getPokemonEvolutionByNum(resultSet.getString(4)));
            	pokemon.setNext_evolution(getPokemonEvolutionByNum(resultSet.getString(5)));            	            	            
                return pokemon;
            }            
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
	}
	
	public PokemonEvolution getPokemonEvolutionByNum(String num) {
		String query = "select num,name from pokemon where num = ?";	
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;       
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	PokemonEvolution pokemonEvolution = new PokemonEvolution();
            	pokemonEvolution.setNum(resultSet.getString(1));
            	pokemonEvolution.setName(resultSet.getString(2));            	            	            	           
                return pokemonEvolution;
            }            
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
	}
	
	@Override
	public List<Pokemon> getByType(String type) {
		String query = "select pokemon.* from pokemon  inner join pokemon_type on pokemon_type.pokemon_num = pokemon.num where pokemon_type.name = ? order by pokemon.num";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;        
        List<Pokemon> pokemons = new ArrayList<Pokemon>();
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	Pokemon pokemon = new Pokemon();
            	pokemon.setId(resultSet.getInt(1));
            	pokemon.setNum(resultSet.getString(2));
            	pokemon.setName(resultSet.getString(3));
            	pokemon.setPre_evolution(getPokemonEvolutionByNum(resultSet.getString(4)));
            	pokemon.setNext_evolution(getPokemonEvolutionByNum(resultSet.getString(5)));             	
            	pokemons.add(pokemon);
            }
            return pokemons;
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
	}

	@Override
	public Pokemon create(Pokemon pokemon) {
		String query = "insert into pokemon (num,name,pre_evolution,next_evolution) values (?, ?, ?, ?)";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pokemon.getNum());
            preparedStatement.setString(2, pokemon.getName());
            preparedStatement.setString(3, pokemon.getPre_evolution().getNum());
            preparedStatement.setString(4, pokemon.getNext_evolution().getNum());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) { 
                	Pokemon pokemonCreated = new Pokemon();
                	pokemonCreated.setId(resultSet.getInt(1));
                	pokemonCreated.setNum(resultSet.getString(2));              	
                	if (pokemon.getPre_evolution() != null) {
                    	preparedStatement.setString(3, pokemon.getPre_evolution().getNum());
                    } else {
                    	preparedStatement.setString(3, null);
                    }                      
                    if (pokemon.getNext_evolution() != null) {
                    	 preparedStatement.setString(4, pokemon.getNext_evolution().getNum());
                    } else {
                    	preparedStatement.setString(4, null);
                    }                            	            	           
                    return pokemonCreated;                    
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
	}

	@Override
	public Pokemon update(Pokemon pokemon) {
		String query = "update pokemon set name = ?, pre_evolution = ?, next_evolution = ? where num = ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pokemon.getName());
            if (pokemon.getPre_evolution() != null) {
            	preparedStatement.setString(2, pokemon.getPre_evolution().getNum());
            } else {
            	preparedStatement.setString(2, null);
            }                      
            if (pokemon.getNext_evolution() != null) {
            	 preparedStatement.setString(3, pokemon.getNext_evolution().getNum());
            } else {
            	preparedStatement.setString(3, null);
            }            
            preparedStatement.setString(4, pokemon.getNum());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                	Pokemon pokemonUpdated = new Pokemon();
                	pokemonUpdated.setId(resultSet.getInt(1));
                	pokemonUpdated.setNum(resultSet.getString(2));
                	pokemonUpdated.setName(resultSet.getString(3));                	
                	pokemonUpdated.setPre_evolution(getPokemonEvolutionByNum(resultSet.getString(4)));
                	pokemonUpdated.setNext_evolution(getPokemonEvolutionByNum(resultSet.getString(5)));         	            	            
                    return pokemonUpdated;   
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
	}

	@Override
	public Boolean delete(String num) {
		String query = "delete from pokemon where num = ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;           
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
        }        
	}	
}
