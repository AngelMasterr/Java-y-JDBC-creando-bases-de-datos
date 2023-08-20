package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaDelete {
	
	public static void main(String[] args) throws SQLException {
		

		Connection con = new ConnectionFactory().recuperaConexion();
		java.sql.Statement statement = con.createStatement();
		
		statement.execute("DELETE FROM PRODUCTOS WHERE ID = 25");
		System.out.println(statement.getUpdateCount());		
		
	}

}
