package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import ejconsulti.locacao.assets.DAO;
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
			LookAndFeel look = new NimbusLookAndFeel();
			UIManager.setLookAndFeel(look);
		} catch (Exception ex) {
			Log.w(TAG, "Apar�ncia não suportada", ex);
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
		
		// Se não existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			new Configuracoes(frame); // Inicia as configurações

		// Se ainda não existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			return;
		
//		// Se acesso permitido
//		if(!login.autorizar())
//			System.exit(1);
		
		notificacoes();
		frame.setPane(Notificacoes.getContentPanel());
	}

	private void addEvents() {
		frame.getBtnInicio().addActionListener(this);
		
		frame.getBtnClientes().addActionListener(this);

		frame.getBtnProdutos().addActionListener(this);

		frame.getBtnFuncionarios().addActionListener(this);
		
		frame.getBtnOrdemdeServico().addActionListener(this);
		
		frame.getBtnDespesas().addActionListener(this);
		
		frame.getBtnRecebimentos().addActionListener(this);
		
		frame.getBtnCaixa().addActionListener(this);
	}
	
	public void notificacoes() {
		//Carregar notificações
		ConsultarFuncionarios.notificacoes();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Inicio":
			Notificacoes.clear();
			notificacoes();
			frame.setPane(Notificacoes.getContentPanel());
			break;
		case "Clientes":
			ConsultarClientes consClientes = new ConsultarClientes();
			frame.setPane(consClientes.getContentPanel());
			break;
		case "Produtos":
			ConsultarProdutos consProdutos = new ConsultarProdutos();
			frame.setPane(consProdutos.getContentPanel());
			break;
		case "Funcion\u00e1rios":
			ConsultarFuncionarios consFuncionarios = new ConsultarFuncionarios();
			frame.setPane(consFuncionarios.getContentPanel());
			break;
		case "Ordem de Servi\u00e7o":
			ConsultarOrdemServico consOrdemDeServico = new ConsultarOrdemServico();
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
}
	
	
