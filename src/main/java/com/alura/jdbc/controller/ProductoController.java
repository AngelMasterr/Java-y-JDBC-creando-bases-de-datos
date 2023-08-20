package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import com.alura.jdbc.factory.ConnectionFactory;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.Statement;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		java.sql.Statement statement = con.createStatement();
		
		statement.execute("UPDATE PRODUCTOS SET "
			    + " NOMBRE = '" + nombre + "'"
			    + ", DESCRIPCION = '" + descripcion + "'"
			    + ", CANTIDAD = " + cantidad
			    + " WHERE ID = " + id);
				 		
		return statement.getUpdateCount();
	}

	public int eliminar(Integer id) throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		java.sql.Statement statement = con.createStatement();
		
		statement.execute("DELETE FROM PRODUCTOS WHERE ID = " + id);
				
		return statement.getUpdateCount();		 
		
	}

	public List<Map<String, String>> listar() throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		
		java.sql.Statement statement = con.createStatement();
		
		statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTOS");
			
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String, String>> resultado = new ArrayList<>();
		
		while (resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("id", String.valueOf(resultSet.getInt("id")));
			fila.put("nombre", resultSet.getString("nombre"));
			fila.put("descripcion", resultSet.getString("descripcion"));
			fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));
			
			resultado.add(fila);
		}
			
		con.close();
		
		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		java.sql.Statement statement = con.createStatement();
		
		statement.execute("INSERT INTO PRODUCTOS(nombre, descripcion, cantidad)"
				+ "VALUES('" +producto.get("nombre") + "','"
				+ producto.get("descripcion") + "',"
				+ producto.get("cantidad") + ")", java.sql.Statement.RETURN_GENERATED_KEYS );
		
		ResultSet resultSet = statement.getGeneratedKeys();		
				
		while (resultSet.next()) {
			System.out.println(String.format(
					"fue insertado el producto del id %d", resultSet.getInt(1)));			
		}
		
		
	}

}
