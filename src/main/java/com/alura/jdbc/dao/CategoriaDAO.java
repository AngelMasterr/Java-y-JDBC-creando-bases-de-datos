package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {
	
	private Connection con;

	// constructor
	public CategoriaDAO(Connection con) {
		this.con = con;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<Categoria>();
		
		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE FROM CATEGORIA");
			try (statement){
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet){	
					while (resultSet.next()) {
						var categoria = new Categoria(resultSet.getInt("ID"),
								resultSet.getString("nombre"));
						
						resultado.add(categoria); 
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}

	public List<Categoria> listarConProductos() {
		List<Categoria> resultado = new ArrayList<Categoria>();
		
		try {
			var querySelect = "SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD "
					+ "FROM CATEGORIA C "
					+ "INNER JOIN PRODUCTOS P ON C.ID = P.CATEGORIA_ID";
			
			final PreparedStatement statement = con.prepareStatement(
					querySelect);
			try (statement){
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet){	
					while (resultSet.next()) {
						Integer categoriaId = resultSet.getInt("ID");
						String categoriaNommbre = resultSet.getString("NOMBRE");
						
						var categoria = resultado
								.stream()
								.filter(cat -> cat.getId().equals(categoriaId))
								.findAny().orElseGet(() -> {
									Categoria cat = new Categoria(categoriaId,
											categoriaNommbre);
									
									resultado.add(cat);
									return cat;
								});						
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}
}
