package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Janela principal
 * 
 * @author Edison Jr
 *
 */
public class FramePrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPanel;

	private JToggleButton btnClientes;
	private JToggleButton btnProdutos;
	private JToggleButton btnFuncionarios;
	private JToggleButton btnOrdemdeServico;
	private JToggleButton btnDespesas;
	private JToggleButton btnRecebimentos;
	private JToggleButton btnCaixa;
	private JToggleButton btnRecibos;
	
	public FramePrincipal() {
		super("Locaço de Equipamentos");
		
		initialize();
	}

	private void initialize() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);

		JPanel rootPanel = new JPanel(new BorderLayout(0, 0));
		setContentPane(rootPanel);

		contentPanel = new JPanel(new BorderLayout());
		rootPanel.add(contentPanel, BorderLayout.CENTER);
		
		JPanel headerPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		rootPanel.add(headerPanel, BorderLayout.NORTH);

		ButtonGroup btnGroup = new ButtonGroup();
		
		btnClientes = new JToggleButton("Clientes");
		headerPanel.add(btnClientes);
		btnGroup.add(btnClientes);
		
		btnProdutos = new JToggleButton("Produtos");
		headerPanel.add(btnProdutos);
		btnGroup.add(btnProdutos);
		
		btnFuncionarios = new JToggleButton("Funcion\u00E1rios");
		headerPanel.add(btnFuncionarios);
		btnGroup.add(btnFuncionarios);
		
		btnOrdemdeServico = new JToggleButton("Ordem de Serviço");
		headerPanel.add(btnOrdemdeServico);
		btnGroup.add(btnOrdemdeServico);
		
		btnRecibos = new JToggleButton("Recibos");
		headerPanel.add(btnRecibos);
		btnGroup.add(btnRecibos);
		
		btnDespesas = new JToggleButton("Despesas");
		headerPanel.add(btnDespesas);
		btnGroup.add(btnDespesas);
		
		btnRecebimentos = new JToggleButton("Recebimentos");
		headerPanel.add(btnRecebimentos);
		btnGroup.add(btnRecebimentos);
		
		btnCaixa = new JToggleButton("Caixa");
		headerPanel.add(btnCaixa);
		btnGroup.add(btnCaixa);
		
	}

	public void setPane(Container container) {
		contentPanel.removeAll();
		if(container != null)
			contentPanel.add(container, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	public JToggleButton getBtnClientes() {
		return btnClientes;
	}
	
	public JToggleButton getBtnProdutos() {
		return btnProdutos;
	}
	
	public JToggleButton getBtnFuncionarios() {
		return btnFuncionarios;
	}
	
	public JToggleButton getBtnOrdemdeServico() {
		return btnOrdemdeServico;
	}
	
	public JToggleButton getBtnRecibos() {
		return btnRecibos;
	}
	
	public JToggleButton getBtnDespesas() {
		return btnDespesas;
	}
	
	public JToggleButton getBtnRecebimentos() {
		return btnRecebimentos;
	}
	
	public JToggleButton getBtnCaixa() {
		return btnCaixa;
	}
}
