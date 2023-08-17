package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.Statement;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	public List<?> listar() throws SQLException {
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC", 
				"root", 
				"Holocausto3@");
		
		java.sql.Statement statement = con.createStatement();
		
		boolean result = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTOS");
			
		System.out.println(result);
		
		con.close();

		return new ArrayList<>();
	}

    public void guardar(Object producto) {
		// TODO
	}

}
