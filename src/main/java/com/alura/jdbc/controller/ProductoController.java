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

import com.alura.jdbc.factory.ConnectionFactory;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.Statement;

public class ProductoController {

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

		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTOS WHERE ID = ?");
			try (statement) {

				statement.setInt(1, id);
				statement.execute();
				return statement.getUpdateCount();
			}
		}
	}

	public List<Map<String, String>> listar() throws SQLException {

		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTOS");
			try (statement) {
				statement.execute();

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
				return resultado;
			}
		}
	}

	public void guardar(Map<String, String> producto) throws SQLException {
		String nombre = producto.get("nombre");
		String descripcion = producto.get("descripcion");
		Integer cantidad = Integer.valueOf(producto.get("cantidad"));
		Integer maxCantidad = 50;

		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {
			con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTOS " + "(nombre, descripcion, cantidad)" + "VALUES(?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				do {
					int cantidadParaGuardar = Math.min(cantidad, maxCantidad);
					ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
					cantidad -= maxCantidad;
					System.out.println(cantidad);

				} while (cantidad > 0);

				con.commit();
			} catch (Exception e) {
				con.rollback();
			}

		}
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);

		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				System.out.println(String.format("fue insertado el producto del id %d", resultSet.getInt(1)));
			}
		}
	}

}
