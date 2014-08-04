
package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

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
	private JPanel panel;
	private JLabel label;
	
	public FramePrincipal() {
		super("Loca\u00e7\u00e3o de Equipamentos");
		
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
		btnClientes.setIcon(new ImageIcon(getClass().getResource("/icones/clientes.png")));
		headerPanel.add(btnClientes);
		btnGroup.add(btnClientes);
		
		btnProdutos = new JToggleButton("Produtos");
		btnProdutos.setIcon(new ImageIcon(FramePrincipal.class.getResource("/icones/produtos.png")));
		headerPanel.add(btnProdutos);
		btnGroup.add(btnProdutos);
		
		btnFuncionarios = new JToggleButton("Funcion\u00E1rios");
		btnFuncionarios.setIcon(new ImageIcon(FramePrincipal.class.getResource("/icones/funcionarios.png")));
		headerPanel.add(btnFuncionarios);
		btnGroup.add(btnFuncionarios);
		
		btnOrdemdeServico = new JToggleButton("Ordem de Servi\u00e7o");
		btnOrdemdeServico.setIcon(new ImageIcon(FramePrincipal.class.getResource("/icones/os.png")));
		headerPanel.add(btnOrdemdeServico);
		btnGroup.add(btnOrdemdeServico);
		
		btnRecibos = new JToggleButton("Recibos");
		headerPanel.add(btnRecibos);
		btnGroup.add(btnRecibos);
		
		btnDespesas = new JToggleButton("Despesas");
		btnDespesas.setIcon(new ImageIcon(FramePrincipal.class.getResource("/icones/despesas.png")));
		headerPanel.add(btnDespesas);
		btnGroup.add(btnDespesas);
		
		btnRecebimentos = new JToggleButton("Recebimentos");
		btnRecebimentos.setIcon(new ImageIcon(FramePrincipal.class.getResource("/icones/recebimentos.png")));
		headerPanel.add(btnRecebimentos);
		btnGroup.add(btnRecebimentos);
		
		btnCaixa = new JToggleButton("Caixa");
		btnCaixa.setIcon(new ImageIcon(FramePrincipal.class.getResource("/icones/caixa.png")));
		headerPanel.add(btnCaixa);
		btnGroup.add(btnCaixa);
		
		panel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		rootPanel.add(panel, BorderLayout.SOUTH);
		
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(label);
		
		Dimension dim = new Dimension(600, 100);
		label.setMinimumSize(dim);
		label.setMaximumSize(dim);
		ImageIcon img =  new ImageIcon("img/header.jpg");
		if(img.getIconWidth() > dim.width || img.getIconHeight() > dim.height) {
			img = new ImageIcon(img.getImage()
					.getScaledInstance(dim.width, dim.height, 0));
		}
		label.setIcon(img);
		
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