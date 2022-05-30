package com.denilson.rest_api_jersey.domain.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denilson.rest_api_jersey.domain.DAO.PokemonTypeDAO;
import com.denilson.rest_api_jersey.domain.db.DbConfig;
import com.denilson.rest_api_jersey.domain.exception.DbException;

public class PokemonTypeServiceImpl implements PokemonTypeDAO {
	
	private Connection connection;

    public PokemonTypeServiceImpl(Connection connection) {
        this.connection = connection;
    }

	@Override
	public List<String> getByNum(String num) {
		String query = "select name from pokemon_type where pokemon_num = ?";	
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> types = new ArrayList<String>();
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	types.add(resultSet.getString(1));           	            	                          
            }  
            return types;
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }       
	}
	
	@Override
	public String getByTypeAndNum(String type, String num) {
		String query = "select name from pokemon_type where name = ? and pokemon_num = ?";	
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, num);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	return resultSet.getString(1);           	            	                          
            }  
            return "";
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }      
	}

	@Override
	public Boolean create(String type, String num) {
		String query = "insert into pokemon_type (name,pokemon_num) values (?, ?)";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, num);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;            
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }       
	}

	@Override
	public Boolean deleteAll(String num) {
		String query = "delete from pokemon_type where pokemon_num = ?";
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
