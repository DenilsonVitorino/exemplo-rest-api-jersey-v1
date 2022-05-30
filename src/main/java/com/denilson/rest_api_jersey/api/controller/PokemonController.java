package com.denilson.rest_api_jersey.api.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.denilson.rest_api_jersey.domain.DAO.DAOFactory;
import com.denilson.rest_api_jersey.domain.DAO.PokemonDAO;
import com.denilson.rest_api_jersey.domain.DAO.PokemonTypeDAO;
import com.denilson.rest_api_jersey.domain.model.Pokemon;

@Path("/pokemon")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)  
public class PokemonController {

	PokemonDAO pokemonDAO;
	PokemonTypeDAO pokemonTypeDAO;

    public PokemonController() {
        pokemonDAO = DAOFactory.createPokemonDAO();
        pokemonTypeDAO = DAOFactory.createPokemonTypeDAO();
    }
    
    @GET
    public List<Pokemon> getAll() {     
    	List<Pokemon> pokemons = pokemonDAO.getAll();
    	for (Pokemon pokemon : pokemons) {
    		pokemon.setType(pokemonTypeDAO.getByNum(pokemon.getNum()));
    	}    	
        return pokemons; 
    }
    
    @GET
    @Path("/{num}")
    public Response getByNum(@PathParam("num") String num) { 
        Pokemon pokemon = pokemonDAO.getByNum(num);
        if (pokemon != null) {     
        	pokemon.setType(pokemonTypeDAO.getByNum(pokemon.getNum()));
            return Response.status(Response.Status.OK).entity(pokemon).build();            
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }   
    }
    
    @GET
    @Path("/type/{type}")
    public  List<Pokemon> getByType(@PathParam("type") String type) { 
    	List<Pokemon> pokemons = pokemonDAO.getByType(type);
    	for (Pokemon pokemon : pokemons) {
    		pokemon.setType(pokemonTypeDAO.getByNum(pokemon.getNum()));
    	}    	
        return pokemons; 
    }
    
    @POST  
    public Response create(Pokemon pokemon) {
        Pokemon pokemonCreated = pokemonDAO.create(pokemon);
        if (pokemonCreated != null) {
        	for (String type : pokemon.getType()) {
        		pokemonTypeDAO.create(type, pokemon.getNum());
        	}   
        	pokemonCreated.setType(pokemonTypeDAO.getByNum(pokemon.getNum()));
            return Response.status(Response.Status.CREATED).entity(pokemonCreated).build();            
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }        
    }
    
    @PUT 
    @Path("/{num}")
    public Response update(@PathParam("num") String num, Pokemon pokemon) {
    	pokemon.setNum(num);
    	Pokemon pokemonUpdated = pokemonDAO.update(pokemon);
        if (pokemonUpdated != null) {
        	pokemonTypeDAO.deleteAll(pokemonUpdated.getNum());
        	for (String type : pokemon.getType()) {
        		pokemonTypeDAO.create(type, pokemon.getNum());
        	} 
        	pokemonUpdated.setType(pokemonTypeDAO.getByNum(pokemonUpdated.getNum()));
            return Response.status(Response.Status.OK).entity(pokemonUpdated).build();            
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }        
    }
    
    @DELETE  
    @Path("/{num}")  
    public Response delete(@PathParam("num") String num) {        
        if (pokemonDAO.delete(num)) {
        	pokemonTypeDAO.deleteAll(num);
            return Response.status(Response.Status.NO_CONTENT).build();            
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }        
    }
}
