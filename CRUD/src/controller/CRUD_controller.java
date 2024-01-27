package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Tarefa;

public class CRUD_controller {
	
	private final String URL = "jdbc:mysql://localhost:3306/UnMEP_DB?user=root&password=";
	private ArrayList<Tarefa> lista;
	
	public Connection conexao(){
		
		try {
			
			return DriverManager.getConnection(URL);
		}catch( SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void create(Tarefa tarefa) {
		
		String sql = "Insert into tarefas(tar_titulo,tar_descricao,tar_status,tar_data) values (?,?,?,?)";
		try(PreparedStatement statement = conexao().prepareStatement(sql)){
			
				statement.setString(1,tarefa.getTitulo());
				statement.setString(2,tarefa.getDescricao());
				statement.setString(3,tarefa.getStatus());
				statement.setDate(4,tarefa.getData());
				statement.execute();
				statement.close();
				;
		}catch(SQLException e){
				e.printStackTrace();
		}
	
	}
	
	public ArrayList<Tarefa> read() {
		String sql = "select * from tarefas";
		lista = new ArrayList<Tarefa>();
		
		try(PreparedStatement statement = conexao().prepareStatement(sql)){
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()) {
					Tarefa Tread = new Tarefa();
					Tread.setId(resultSet.getInt("tar_id"));
					Tread.setTitulo(resultSet.getString("tar_titulo"));
					Tread.setDescricao(resultSet.getString("tar_descricao"));
					Tread.setStatus(resultSet.getString("tar_status"));
					Tread.setData(resultSet.getDate("tar_data"));
					lista.add(Tread);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public void updateTotal(Tarefa tarefa, int id) {
		String sql = "update tarefas set tar_titulo = ?, tar_descricao = ?, tar_status = ?, tar_data = ? where tar_id = ?";
		try(PreparedStatement statement = conexao().prepareStatement(sql)){
			statement.setString(1, tarefa.getTitulo());
			statement.setString(2, tarefa.getDescricao());
			statement.setString(3, tarefa.getStatus());
			statement.setDate(4, tarefa.getData());
			statement.setInt(5, id);
			statement.execute();
			statement.close();
			System.out.println("Atualização completa");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "Delete from tarefas where tar_id = ?";
		try(PreparedStatement statement = conexao().prepareStatement(sql)){
			statement.setInt(1,id);
			statement.executeUpdate();
			statement.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void colseConnection() {
		try {
			
			Connection connection = conexao();
			if(connection != null) {
				connection.close();
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
