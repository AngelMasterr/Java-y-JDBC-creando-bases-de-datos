package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {

	final private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}

	// Metodo guardar producto
	public void guardarProducto(Producto producto) {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTOS (nombre, descripcion, cantidad, categoria_id) "
					+ "VALUES(?, ?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutaRegistro(producto, statement);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
		statement.setInt(4, producto.getCantidadId());
		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(String.format("fue insertado el producto de %s", producto));
			}
		}
	}

	// Metodo Listar o mostra los productos existentes en la tabla
	public List<Producto> listar() {
		List<Producto> resultado = new ArrayList<Producto>();
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTOS");
			try (statement) {
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				try (resultSet){

					while (resultSet.next()) {
						Producto fila = new Producto(
								resultSet.getInt("id"),
								resultSet.getString("nombre"), 
								resultSet.getString("descripcion"),
								resultSet.getInt("cantidad"));
						resultado.add(fila);
					}
				}				
			}
			return resultado;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Metodo para eliminar la fila seleccionada
	public int eliminar(Integer id) {	
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTOS WHERE ID = ?");			
			try (statement) {
				statement.setInt(1, id);
				statement.execute();	
				return statement.getUpdateCount();
			}										 
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Metodo para modificar la fila seleccionada
	public int modificar(Producto producto) {
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con.prepareStatement(
					"UPDATE PRODUCTOS SET " + " NOMBRE = ?," + " DESCRIPCION = ?," + " CANTIDAD = ?" + " WHERE ID = ?");
			try (statement) {

				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getId());
				statement.execute();

				System.out.println(String.format("%s, %s, %d, %d", producto.getNombre(), producto.getDescripcion(), producto.getCantidad(), producto.getId()));
				return statement.getUpdateCount();
			}			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<Producto>();
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD "
					+ "FROM PRODUCTOS "
					+ "WHERE CATEGORIA_ID = ?");
			
			try (statement) {
				statement.setInt(1, categoriaId);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				try (resultSet){

					while (resultSet.next()) {
						Producto fila = new Producto(
								resultSet.getInt("id"),
								resultSet.getString("nombre"), 
								resultSet.getString("descripcion"),
								resultSet.getInt("cantidad"));
						resultado.add(fila);
					}
				}				
			}
			return resultado;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
}
