package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Caixa;
import ejconsulti.locacao.models.CaixaTableModel;
import ejconsulti.locacao.views.DialogCaixa;
import ejconsulti.locacao.views.PanelConsultar;
import eso.components.DoubleField;
import eso.utils.Log;

public class ConsultarCaixa implements ActionListener {
	public static final String TAG = ConsultarDespesas.class.getSimpleName();
	
	private PanelConsultar panel;
	private CaixaTableModel model;
	private TableRowSorter<CaixaTableModel> sorter;
	private DialogCaixa dialog;
	private DoubleField txtSaldoDiaAnterior;
	private DoubleField txtSaldoEntrada;
	private DoubleField txtSaldoSaida;
	private DoubleField txtSaldoTotal;
	
	public ConsultarCaixa() {
		initialize();
	}
	
	private void initialize() {
		panel = new PanelConsultar();
		
		panel.getBtnAdicionar().setText("Por Dia");
		panel.getBtnEditar().setText("Por Período");
		panel.getBtnExcluir().setText("Hoje");
		panel.getTxtPesquisar().setVisible(false);
		panel.getBtnPesquisar().setVisible(false);
		
		JLabel lblSaldoDiaAnterior = new JLabel("Saldo total de ontem");
		panel.getHeaderPanel().add(lblSaldoDiaAnterior, "cell 4 0");
		
		txtSaldoDiaAnterior = new DoubleField();
		txtSaldoDiaAnterior.setColumns(8);
		txtSaldoDiaAnterior.setFocusable(false);
		panel.getHeaderPanel().add(txtSaldoDiaAnterior, "cell 5 0");
		
		JLabel lblSaldoEntrada = new JLabel("Entrada Total");
		panel.getHeaderPanel().add(lblSaldoEntrada, "cell 6 0");
		
		txtSaldoEntrada = new DoubleField();
		txtSaldoEntrada.setColumns(8);
		txtSaldoEntrada.setFocusable(false);
		panel.getHeaderPanel().add(txtSaldoEntrada, "cell 7 0");
		
		JLabel lblSaldoSaida = new JLabel("Saída Total");
		panel.getHeaderPanel().add(lblSaldoSaida, "cell 8 0");
		
		txtSaldoSaida = new DoubleField();
		txtSaldoSaida.setColumns(8);
		txtSaldoSaida.setFocusable(false);
		panel.getHeaderPanel().add(txtSaldoSaida, "cell 9 0");
		
		JLabel lblSaldoTotal = new JLabel("Saldo Total");
		panel.getHeaderPanel().add(lblSaldoTotal, "cell 10 0");
		
		txtSaldoTotal = new DoubleField();
		txtSaldoTotal.setColumns(8);
		txtSaldoTotal.setFocusable(false);
		panel.getHeaderPanel().add(txtSaldoTotal, "cell 11 0");
		
		addEvents();
		
		carregar(0);
	}
	
	private void addEvents() {
		panel.getBtnAdicionar().addActionListener(this);
		panel.getBtnEditar().addActionListener(this);
		panel.getBtnExcluir().addActionListener(this);
		panel.getBtnImprimir().addActionListener(this);
	}
	
	public void carregar(int condition) {
		ResultSet rs = null;
		model = new CaixaTableModel();
		panel.getTable().setModel(model);
		
		sorter = new TableRowSorter<CaixaTableModel>(model);
		panel.getTable().setRowSorter(sorter);
		try {
			if (condition == 0) {
				Date data = new Date();
				SimpleDateFormat dia = new SimpleDateFormat("yyyy-MM-dd");
				rs = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data = '" + dia.format(data.getTime()) + "' ORDER BY idCaixa", null);
				Calendar calendario = Calendar.getInstance();
				calendario.add(Calendar.DAY_OF_MONTH, -1);
				ResultSet rs2 = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data = '" + dia.format(calendario.getTime()) + "'", null);
				double entrada = 0;
				double saida = 0;
				while (rs2.next()) {
					Caixa c = Caixa.rsToObject(rs2);
					entrada += c.getValorEntrada();
					saida += c.getValorSaida();
				}
				txtSaldoDiaAnterior.setValue(entrada - saida);
			}
			else if (condition == 1) {
				Date data = dialog.getTxtDataInicio().getDate();
				rs = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data = '" + data + "' ORDER BY idCaixa", null);
			}
			else if (condition == 2) {
				Date data1 = dialog.getTxtDataInicio().getDate();
				Date data2 = dialog.getTxtDataFim().getDate();
				rs = DAO.getDatabase().executeQuery("SELECT * FROM caixa WHERE data BETWEEN '" + data1 + "' AND '" + data2 + "' ORDER BY data", null);
			}
			double entrada2 = 0;
			double saida2 = 0;
			while(rs.next()) {
				Caixa c = Caixa.rsToObject(rs);
				model.add(c);
				entrada2 += c.getValorEntrada();
				saida2 += c.getValorSaida();
			}
			txtSaldoEntrada.setValue(entrada2);
			txtSaldoSaida.setValue(saida2);
			txtSaldoTotal.setValue(entrada2 - saida2);
	
		} catch (SQLException ex) {
			if (condition == 0)
				Log.e(TAG, "Erro ao carregar o caixa", ex);
			else if (condition == 1)
				JOptionPane.showMessageDialog(dialog, "Erro ao carregar o caixa do dia.");
			else if (condition == 2)
				JOptionPane.showMessageDialog(dialog, "Erro ao carregar o caixa do período.");
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
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Por Dia":
			consultarDia();
			break;
		case "Por Período":
			consultarPeriodo();
			break;
		case "Hoje":
			carregar(0);
			break;
		case "Imprimir":
			break;
		}
	}

	public PanelConsultar getContentPanel() {
		return panel;
	}
	
}
