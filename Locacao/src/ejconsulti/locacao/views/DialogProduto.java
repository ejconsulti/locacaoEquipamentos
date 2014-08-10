package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import eso.components.DoubleField;

/**
 * Dialog de produto
 * 
 * @author Edison Jr
 *
 */
public class DialogProduto extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtNome;
	private DoubleField txtValorDiario;
	private DoubleField txtValorMensal;
	private DoubleField spnQuantidade;

	private JButton btnSalvar;
	private JButton btnCancelar;

	public DialogProduto(Window owner, String title) {
		super(owner, title);
		intialize();
	}
	
	private void intialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[right]2[][]", "[][][][]"));
		
		JLabel lblNome = new JLabel("Nome");
		contentPanel.add(lblNome, "cell 0 0,alignx trailing");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 0,alignx trailing");
		
		txtNome = new JTextField(30);
		contentPanel.add(txtNome, "cell 2 0");
		
		JLabel lblValorDiario = new JLabel("Valor di\u00E1rio");
		contentPanel.add(lblValorDiario, "cell 0 1");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 1,alignx trailing");
		
		txtValorDiario = new DoubleField(0.0);
		txtValorDiario.addFocusListener(new FocusListener() {
			double valor;
			@Override
			public void focusLost(FocusEvent e) {
				double valorAtual = txtValorDiario.doubleValue();
				if (valorAtual != valor) {
					txtValorMensal.setValue(valorAtual * 30);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				valor = txtValorDiario.doubleValue();
			}
		});
		txtValorDiario.setColumns(8);
		contentPanel.add(txtValorDiario, "cell 2 1");
		
		JLabel lblValorMensal = new JLabel("Valor mensal");
		contentPanel.add(lblValorMensal, "cell 0 2");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 2,alignx trailing");
		
		txtValorMensal = new DoubleField(0.0);
		txtValorMensal.setColumns(8);
		contentPanel.add(txtValorMensal, "cell 2 2");
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		contentPanel.add(lblQuantidade, "cell 0 3");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		contentPanel.add(l4, "cell 1 3,alignx trailing");
		
		spnQuantidade = new DoubleField();
		spnQuantidade.setColumns(8);
		spnQuantidade.setMinValue(0.0);
		contentPanel.add(spnQuantidade, "cell 2 3");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogProduto.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogProduto.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelar);
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

	public DoubleField getTxtValorDiario() {
		return txtValorDiario;
	}

	public DoubleField getTxtValorMensal() {
		return txtValorMensal;
	}

	public DoubleField getTxtQuantidade() {
		return spnQuantidade;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	@Override
	public void setVisible(boolean b) {
		if(b) {
			pack();
			setLocationRelativeTo(null);
		}
		super.setVisible(b);
	}
}
