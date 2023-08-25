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
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.Statement;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	// constructor
	public ProductoController() {			
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

	public int modificar(Producto producto) throws SQLException {
		return productoDAO.modificar(producto);
	}

	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);		
	}

	public List<Producto> listar() {		
		return productoDAO.listar();		
	}
	
	public List<Producto> listar(Categoria categoria){
		return productoDAO.listar(categoria.getId());	
	}

	public void guardar(Producto producto, Integer categoriaId) {
		producto.setCategoriaId(categoriaId);
		productoDAO.guardarProducto(producto);		
	}
}
