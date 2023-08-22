package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.Statement;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	// constructor
	public ProductoController() {			
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {

		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con.prepareStatement(
					"UPDATE PRODUCTOS SET " + " NOMBRE = ?," + " DESCRIPCION = ?," + " CANTIDAD = ?" + " WHERE ID = ?");
			try (statement) {

				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);

				statement.execute();

				System.out.println(String.format("%s, %s, %d, %d", nombre, descripcion, cantidad, id));
				return statement.getUpdateCount();
			}
		}
	}

	public int eliminar(Integer id) throws SQLException {
		return productoDAO.eliminar(id);		
	}

	public List<Producto> listar() {		
		return productoDAO.listar();		
	}

	public void guardar(Producto producto) {
		productoDAO.guardarProducto(producto);
	}
}
