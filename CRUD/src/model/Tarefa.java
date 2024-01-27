package model;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

public class Tarefa {
	
	private int id;
	private String titulo;
	private String descricao;
	private String status;
	private Date data;
	
	public Tarefa() {
		
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public void setData(String data){
		try {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil = formato.parse(data);
		this.data = new java.sql.Date(dataUtil.getTime());
		}catch(ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Date getData() {
		return data;
	}
}
