package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Funcionario;
import ejconsulti.locacao.views.FramePrincipal;
import eso.utils.Log;

/**
 * Main (controlador principal)
 * 
 * @author Edison Jr
 *
 */
public class Main implements ActionListener {
	public static final String TAG = Main.class.getSimpleName();

	private static Main instance;

	private FramePrincipal frame;

	public static void main(String[] args) {
		try {
<<<<<<< HEAD
			LookAndFeel look = new NimbusLookAndFeel();
			UIManager.setLookAndFeel(look);
		} catch (Exception ex) {
			Log.w(TAG, "Apar�ncia n�o suportada", ex);
		}
		
		try{
			instance = new Main();
		} catch (Exception ex){
			Log.wtf(TAG, ex);
		}
	}

	private Main() {
		initialize();
		
	}
	
	private void initialize() {
		frame = new FramePrincipal();
		
		addEvents();
		
		frame.setVisible(true);
		
		// Se n�o existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			new Configuracoes(frame); // Inicia as configura��es

		// Se ainda n�o existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			return;
		
		
//		Login login = new Login(frame);
//		// Se acesso permitido
//		if(login.autorizar()) {
//			lembretePagamento();
//			lembreteFerias();
//		} else {
//			// Se n�o, sair do sistema
//			System.exit(1);
//		}
	}

	private void addEvents() {
		frame.getBtnClientes().addActionListener(this);

		frame.getBtnProdutos().addActionListener(this);

		frame.getBtnFuncionarios().addActionListener(this);
		
		frame.getBtnOrdemdeServico().addActionListener(this);
		
		frame.getBtnDespesas().addActionListener(this);
		
		frame.getBtnRecebimentos().addActionListener(this);
		
		frame.getBtnCaixa().addActionListener(this);
	}

	public void lembretePagamento() {
		ResultSet rs = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 3);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			rs = DAO.getDatabase().select(new String[] {Funcionario.NOME}, Funcionario.TABLE, 
					Funcionario.DIA_PAGAMENTO+" = ?", new Object[]{dia}, null, Funcionario.NOME);

			if(rs.next()) {
				StringBuilder strFunc = new StringBuilder("Faltam 3 dias para o pagamento de: \n");
				strFunc.append(rs.getString(Funcionario.NOME));
				while(rs.next()) {
					strFunc.append('\n');
					strFunc.append(rs.getString(Funcionario.NOME));
				}

				JOptionPane.showMessageDialog(frame, strFunc, "Lembrete de pagamento", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar lembrete de pagamento.", e);
		}
	}

	public void lembreteFerias() {
		ResultSet rs = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 3);
			Date date = new Date(cal.getTimeInMillis());
			rs = DAO.getDatabase().select(new String[] {Funcionario.NOME}, Funcionario.TABLE, 
					Funcionario.PREVISAO_FERIAS+" < ?", new Object[]{date.toString()}, null, Funcionario.NOME);

			if(rs.next()) {
				StringBuilder strFunc = new StringBuilder("F�rias vencendo ou vencidas: \n");
				strFunc.append(rs.getString(Funcionario.NOME));
				while(rs.next()) {
					strFunc.append('\n');
					strFunc.append(rs.getString(Funcionario.NOME));
				}

				JOptionPane.showMessageDialog(frame, strFunc, "Lembrete de f�rias", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar lembrete de f��rias.", e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Clientes":
			ConsultarClientes consClientes = new ConsultarClientes();
			frame.setPane(consClientes.getContentPanel());
			break;
		case "Produtos":
			ConsultarProdutos consProdutos = new ConsultarProdutos();
			frame.setPane(consProdutos.getContentPanel());
			break;
		case "Funcionários":
			ConsultarFuncionarios consFuncionarios = new ConsultarFuncionarios();
			frame.setPane(consFuncionarios.getContentPanel());
			break;
		case "Ordem de Servi�o":
			ConsultarOrdemDeServico consOrdemDeServico = new ConsultarOrdemDeServico();
			frame.setPane(consOrdemDeServico.getContentPanel());
			break;
		case "Despesas":
			ConsultarDespesas consDespesas = new ConsultarDespesas();
			frame.setPane(consDespesas.getContentPanel());
			break;
		case "Recebimentos":
			ConsultarRecebimentos consRecebimentos = new ConsultarRecebimentos();
			frame.setPane(consRecebimentos.getContentPanel());
			break;
		case "Caixa":
			ConsultarCaixa consCaixa = new ConsultarCaixa();
			frame.setPane(consCaixa.getContentPanel());
			break;
		}
	}

	public static FramePrincipal getFrame() {
		return instance.frame;
	}
=======
			LookAndFeel look = new NimbusLookAndFeel();
			UIManager.setLookAndFeel(look);
		} catch (Exception ex) {
			Log.w(TAG, "Apar�ncia n�o suportada", ex);
		}
		
		try{
			instance = new Main();
		} catch (Exception ex){
			Log.wtf(TAG, ex);
		}
	}

	private Main() {
		initialize();
		
	}
	
	private void initialize() {
		frame = new FramePrincipal();
		
		addEvents();
		
		frame.setVisible(true);
		
		// Se n�o existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			new Configuracoes(frame); // Inicia as configura��es

		// Se ainda n�o existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			return;
		
		
//		Login login = new Login(frame);
//		// Se acesso permitido
//		if(login.autorizar()) {
//			lembretePagamento();
//			lembreteFerias();
//		} else {
//			// Se n�o, sair do sistema
//			System.exit(1);
//		}
	}

	private void addEvents() {
		frame.getBtnClientes().addActionListener(this);

		frame.getBtnProdutos().addActionListener(this);

		frame.getBtnFuncionarios().addActionListener(this);
		
		frame.getBtnOrdemdeServico().addActionListener(this);
		
		frame.getBtnDespesas().addActionListener(this);
		
		frame.getBtnRecebimentos().addActionListener(this);
		
		frame.getBtnCaixa().addActionListener(this);
	}

	public void lembretePagamento() {
		ResultSet rs = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 3);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			rs = DAO.getDatabase().select(new String[] {Funcionario.NOME}, Funcionario.TABLE, 
					Funcionario.DIA_PAGAMENTO+" = ?", new Object[]{dia}, null, Funcionario.NOME);

			if(rs.next()) {
				StringBuilder strFunc = new StringBuilder("Faltam 3 dias para o pagamento de: \n");
				strFunc.append(rs.getString(Funcionario.NOME));
				while(rs.next()) {
					strFunc.append('\n');
					strFunc.append(rs.getString(Funcionario.NOME));
				}

				JOptionPane.showMessageDialog(frame, strFunc, "Lembrete de pagamento", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar lembrete de pagamento.", e);
		}
	}

	public void lembreteFerias() {
		ResultSet rs = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 3);
			Date date = new Date(cal.getTimeInMillis());
			rs = DAO.getDatabase().select(new String[] {Funcionario.NOME}, Funcionario.TABLE, 
					Funcionario.PREVISAO_FERIAS+" < ?", new Object[]{date.toString()}, null, Funcionario.NOME);

			if(rs.next()) {
				StringBuilder strFunc = new StringBuilder("F�rias vencendo ou vencidas: \n");
				strFunc.append(rs.getString(Funcionario.NOME));
				while(rs.next()) {
					strFunc.append('\n');
					strFunc.append(rs.getString(Funcionario.NOME));
				}

				JOptionPane.showMessageDialog(frame, strFunc, "Lembrete de f�rias", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar lembrete de f��rias.", e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Clientes":
			ConsultarClientes consClientes = new ConsultarClientes();
			frame.setPane(consClientes.getContentPanel());
			break;
		case "Produtos":
			ConsultarProdutos consProdutos = new ConsultarProdutos();
			frame.setPane(consProdutos.getContentPanel());
			break;
		case "Funcionários":
			ConsultarFuncionarios consFuncionarios = new ConsultarFuncionarios();
			frame.setPane(consFuncionarios.getContentPanel());
			break;
		case "Ordem de Servi�o":
			ConsultarOrdemDeServico consOrdemDeServico = new ConsultarOrdemDeServico();
			frame.setPane(consOrdemDeServico.getContentPanel());
			break;
		case "Despesas":
			ConsultarDespesas consDespesas = new ConsultarDespesas();
			frame.setPane(consDespesas.getContentPanel());
			break;
		case "Recebimentos":
			ConsultarRecebimentos consRecebimentos = new ConsultarRecebimentos();
			frame.setPane(consRecebimentos.getContentPanel());
			break;
		case "Caixa":
			ConsultarCaixa consCaixa = new ConsultarCaixa();
			frame.setPane(consCaixa.getContentPanel());
			break;
		}
	}

	public static FramePrincipal getFrame() {
		return instance.frame;
	}
>>>>>>> d38bde6fa6113d955d3b2db53d138f69376196d3
}
