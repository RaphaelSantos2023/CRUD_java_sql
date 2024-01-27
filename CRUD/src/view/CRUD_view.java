package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.CRUD_controller;
import model.Tarefa;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CRUD_view extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField FieldTitulo;
	private JTextField FieldDescricao;
	private JTextField FieldDia;
	private JTextField FieldMes;
	private JTextField FieldAno;
	private JTable Tabela;
	private JLabel Id;
	private JLabel Warning;
	
	private JRadioButton RadioPendente;
	private JRadioButton RadioExecutando;
	private JRadioButton RadioConcluido;
	
	private Tarefa tarefa;
	private CRUD_controller controller;
	private JButton Delete;
	
	private DefaultTableModel model;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUD_view frame = new CRUD_view();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public CRUD_view() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		controller = new CRUD_controller();
		tarefa = new Tarefa();
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.colseConnection();
			}
		});
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		FieldTitulo = new JTextField();
		FieldTitulo.setBounds(76, 42, 188, 20);
		contentPane.add(FieldTitulo);
		FieldTitulo.setColumns(10);
		
		JLabel LabelTitulo = new JLabel("Título");
		LabelTitulo.setBounds(10, 45, 46, 14);
		contentPane.add(LabelTitulo);
		
		JButton Create = new JButton("Inserir");
		Create.setBounds(10, 155, 89, 23);
		contentPane.add(Create);
		
		Warning = new JLabel("");
		Warning.setBounds(143, 17, 398, 14);
		contentPane.add(Warning);
		
		Create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!FieldTitulo.getText().isEmpty() && !FieldDescricao.getText().isEmpty() 
						&& !FieldDia.getText().isEmpty() && !FieldMes.getText().isEmpty()
						&& !FieldAno.getText().isEmpty() && (RadioPendente.isSelected() || RadioConcluido.isSelected() || RadioExecutando.isSelected())) {
					Create();
					cleaningField();
					Read();
					WarningMessage("C");
				}else {
					WarningMessage("F");
				}
			}
		});
		
		FieldDescricao = new JTextField();
		FieldDescricao.setBounds(76, 73, 311, 23);
		contentPane.add(FieldDescricao);
		FieldDescricao.setColumns(10);
		
		FieldDia = new JTextField();
		FieldDia.setBounds(76, 126, 33, 20);
		contentPane.add(FieldDia);
		FieldDia.setColumns(10);
		
		JLabel LabelDescricao = new JLabel("Descrição");
		LabelDescricao.setBounds(10, 75, 62, 14);
		contentPane.add(LabelDescricao);
		
		JLabel LabelStatus = new JLabel("Status");
		LabelStatus.setBounds(10, 104, 46, 14);
		contentPane.add(LabelStatus);
		
		JLabel LabelData = new JLabel("Data");
		LabelData.setBounds(10, 129, 46, 14);
		contentPane.add(LabelData);
		
		RadioPendente = new JRadioButton("Pendente");
		RadioPendente.setBounds(76, 96, 79, 23);
		contentPane.add(RadioPendente);
		
		RadioPendente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tarefa.setStatus("pendente");
				RadioExecutando.setSelected(false);
				RadioConcluido.setSelected(false);
			}
		});
		
		RadioExecutando = new JRadioButton("Executando");
		RadioExecutando.setBounds(156, 96, 89, 23);
		contentPane.add(RadioExecutando);
		
		RadioExecutando.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tarefa.setStatus("executando");
				RadioPendente.setSelected(false);
				RadioConcluido.setSelected(false);
			}
		});
		
		RadioConcluido = new JRadioButton("Concluido");
		RadioConcluido.setBounds(246, 96, 79, 23);
		contentPane.add(RadioConcluido);
		
		RadioConcluido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tarefa.setStatus("concluido");
				RadioExecutando.setSelected(false);
				RadioPendente.setSelected(false);
			}
		});
		
		FieldMes = new JTextField();
		FieldMes.setBounds(119, 126, 36, 20);
		contentPane.add(FieldMes);
		FieldMes.setColumns(10);
		
		FieldAno = new JTextField();
		FieldAno.setBounds(165, 126, 46, 20);
		contentPane.add(FieldAno);
		FieldAno.setColumns(10);
		
		Tabela = new JTable();
		Tabela.setBounds(10, 189, 672, 213);
		contentPane.add(Tabela);
		
		JButton Read = new JButton("Carregar Campos");
		Read.setBounds(343, 155, 128, 23);
		contentPane.add(Read);
		
		Read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cleaningField();
				loading();
			}
		});
		
		Id = new JLabel("");
		Id.setBounds(76, 17, 46, 14);
		contentPane.add(Id);
		
		JButton update = new JButton("Atualizar");
		update.setBounds(109, 155, 97, 23);
		contentPane.add(update);
		
		setJtabelModel();
		
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!FieldTitulo.getText().isEmpty() && !FieldDescricao.getText().isEmpty() 
						&& !FieldDia.getText().isEmpty() && !FieldMes.getText().isEmpty()
						&& !FieldAno.getText().isEmpty() && (RadioPendente.isSelected() || RadioConcluido.isSelected() || RadioExecutando.isSelected())) {
					
					update();
					cleaningField();
					Read();
					WarningMessage("C");
				}else {
					WarningMessage("F");
				}
			}
		});
		Read();
		
		Delete = new JButton("Deletar");
		Delete.setBounds(216, 155, 89, 23);
		contentPane.add(Delete);
		
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				delete();
				cleaningField();
				Read();
				WarningMessage("C");
			}
		});
		
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setBounds(10, 20, 46, 14);
		contentPane.add(IDLabel);
		
		JButton Clear = new JButton("Limpar campos");
		Clear.setBounds(481, 155, 112, 23);
		contentPane.add(Clear);
		
		Clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cleaningField();
				WarningMessage("C");
			}
			
		});
		
	}
	
	public void Create() {
		
		tarefa.setTitulo(FieldTitulo.getText());
		tarefa.setDescricao(FieldDescricao.getText());
		String data = FieldAno.getText()+"-"+FieldMes.getText()+"-"+FieldDia.getText();
		tarefa.setData(data);
		controller.create(tarefa);

	}
	
	public void setJtabelModel() {
		model = (DefaultTableModel) Tabela.getModel();
		model.setNumRows(0);
		model.addColumn("Id");
		model.addColumn("Titulo");
		model.addColumn("Descrição");
		model.addColumn("Status");
		model.addColumn("Data");
		
	}
	public void Read() {
		try {
			
			RowClear();
			
			ArrayList<Tarefa> lista = new ArrayList<Tarefa>();			
			lista = controller.read();
			
			for(int num = 0; num < lista.size(); num++) {
				Object[] linhas = {
						lista.get(num).getId(),
						lista.get(num).getTitulo(),
						lista.get(num).getDescricao(),
						lista.get(num).getStatus(),
						lista.get(num).getData()
					};
				model.addRow(linhas);
			}
			
			model.fireTableDataChanged();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void RowClear() {
		int rowCount = model.getRowCount();
		
	    for (int i = rowCount - 1; i >= 0; i--) {
	        model.removeRow(i);
	    }
	    
	}
	
	public void cleaningField() {
		Id.setText("");
		FieldTitulo.setText("");
		FieldDescricao.setText("");
		RadioExecutando.setSelected(false);
		RadioPendente.setSelected(false);
		RadioConcluido.setSelected(false);
		FieldDia.setText("");
		FieldMes.setText("");
		FieldAno.setText("");
	}
	
	public void loading() {
		
		if(Id.getText() != null) {
			int tabelData = Tabela.getSelectedRow();
	        
			try {
			Id.setText(Tabela.getModel().getValueAt(tabelData,0).toString());
			FieldTitulo.setText(Tabela.getModel().getValueAt(tabelData,1).toString());
			FieldDescricao.setText(Tabela.getModel().getValueAt(tabelData,2).toString());
			
			switch(Tabela.getModel().getValueAt(tabelData,3).toString()) {
			
				case "pendente":
					RadioPendente.setSelected(true);
					RadioExecutando.setSelected(false);
					RadioConcluido.setSelected(false);
				break;
				case "executando":
					RadioExecutando.setSelected(true);
					RadioPendente.setSelected(false);
					RadioConcluido.setSelected(false);
				break;
				case "concluido":
					RadioConcluido.setSelected(true);
					RadioExecutando.setSelected(false);
					RadioPendente.setSelected(false);
				break;
			}
			
	        FieldDia.setText(Tabela.getModel().getValueAt(tabelData, 4).toString().substring(8));
			FieldMes.setText(Tabela.getModel().getValueAt(tabelData, 4).toString().substring(5,7));
			FieldAno.setText(Tabela.getModel().getValueAt(tabelData, 4).toString().substring(0,4));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void WarningMessage(String st) {
		switch(st) {
		case "F":
			Warning.setText("*Preencha todas as infromações para executar essa função*");
		break;
		case "C":
			Warning.setText("");
		break;
		}
	}
	
	public void update() {
		if(Id.getText() != null) {
			int tabelData = Integer.parseInt(Id.getText());
			
			if( Id.getText() != null) {
				Tarefa trf = new Tarefa();
				
				trf.setTitulo(FieldTitulo.getText());
				trf.setDescricao(FieldDescricao.getText());
			
				if(RadioPendente.isSelected()) {
					trf.setStatus("pendente");
				}else if(RadioExecutando.isSelected()) {
					trf.setStatus("executando");
				}else if(RadioConcluido.isSelected()) {
					trf.setStatus("concluido");
				}
				trf.setData(FieldAno.getText()+"-"
						+FieldMes.getText()+"-"
						+FieldDia.getText());
				
				controller.updateTotal(trf, tabelData);
			}
		}
	}
	
	public void delete() {
		if(Id.getText() != null) {
			int tabelData = Integer.parseInt(Id.getText());
			
			if(Id.getText() != null) {
				controller.delete(tabelData);
			}
		}
	}
}

