package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Caixa;
import ejconsulti.locacao.models.CaixaTableModel;
import ejconsulti.locacao.views.DialogCaixa;
import ejconsulti.locacao.views.PanelConsultarCaixa;
import eso.utils.Log;

public class ConsultarCaixa implements ActionListener {
	public static final String TAG = ConsultarDespesas.class.getSimpleName();
	
	private PanelConsultarCaixa panel;
	private CaixaTableModel model;
	private TableRowSorter<CaixaTableModel> sorter;
	private DialogCaixa dialog;
	
	private List<Caixa> lista;
	
	public ConsultarCaixa() {
		initialize();
	}
	
	private void initialize() {
		
		panel = new PanelConsultarCaixa();
		
		lista = new ArrayList<Caixa>();
		
		addEvents();
		
		carregar(0);
	}
	
	private void addEvents() {		
		panel.getBtnPorDia().addActionListener(this);
		panel.getBtnPorPeriodo().addActionListener(this);
		panel.getBtnHoje().addActionListener(this);
	}
	
	public void carregar(int condition) {
		ResultSet rs = null;
		model = new CaixaTableModel();
		panel.getTable().setModel(model);
		sorter = new TableRowSorter<CaixaTableModel>(model);
		panel.getTable().setRowSorter(sorter);
		try {
			SimpleDateFormat dia = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendario = Calendar.getInstance();
			if (condition == 0) { //Hoje
				Date data = new Date();
				calendario.add(Calendar.DAY_OF_MONTH, -1);
				rs = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data = '" + dia.format(data.getTime()) + "' ORDER BY idCaixa", null);
			}
			else if (condition == 1) { //Por dia
				Date data = dialog.getTxtDataInicio().getDate();
				calendario.setTime(data);
				rs = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data = '" + data + "' ORDER BY idCaixa", null);
				calendario.add(Calendar.DAY_OF_MONTH, -1);
			}
			else if (condition == 2) { //Por periodo
				Date data1 = dialog.getTxtDataInicio().getDate();
				Date data2 = dialog.getTxtDataFim().getDate();
				rs = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data BETWEEN '" + data1 + "' AND '" + data2 + "' ORDER BY data", null);
			}
			double entrada2 = 0;
			double saida2 = 0;
			
			lista.clear();
			while(rs.next()) {
				Caixa c = Caixa.rsToObject(rs);
				model.add(c);
				
				lista.add(c);
				entrada2 += c.getValorEntrada();
				saida2 += c.getValorSaida();
			}
			panel.getTxtSaldoEntrada().setValue(entrada2);
			panel.getTxtSaldoSaida().setValue(saida2);
			panel.getTxtSaldoTotal().setValue(entrada2 - saida2);
		} catch (SQLException ex) {
			if (condition == 0)
				JOptionPane.showMessageDialog(dialog, "Erro ao carregar o caixa do dia.");
			else if (condition == 1)
				JOptionPane.showMessageDialog(dialog, "Erro ao carregar o caixa do dia.");
			else if (condition == 2)
				JOptionPane.showMessageDialog(dialog, "Erro ao carregar o caixa do período.");
			Log.e(TAG, "Erro ao carregar caixa", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {}
			}
		}
	}
	
	public void consultarDia() {
		dialog = new DialogCaixa(Main.getFrame(), "Pesquisar por Dia");
		dialog.getContentPanel().remove(dialog.getLblDataFim());
		dialog.getContentPanel().remove(dialog.getTxtDataFim());
		dialog.getContentPanel().remove(dialog.getBtnBuscar());
		dialog.getContentPanel().add(dialog.getBtnBuscar(), "cell 1 0");
		dialog.setVisible(true);
		dialog.getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				carregar(1);
				dialog.dispose();
			}
		});
	}
	
	public void consultarPeriodo() {
		dialog = new DialogCaixa(Main.getFrame(), "Pesquisar por Período");
		dialog.setVisible(true);
		dialog.getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				carregar(2);
				dialog.dispose();
			}
		});
	}
	
	public void imprimir() {
		new ImprimirCaixa(lista);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Dia":
			consultarDia();
			break;
		case "Período":
			consultarPeriodo();
			break;
		case "Hoje":
			carregar(0);
			break;
		case "Imprimir":
			imprimir();
			break;
		}
	}

	public PanelConsultarCaixa getContentPanel() {
		return panel;
	}
	
}
