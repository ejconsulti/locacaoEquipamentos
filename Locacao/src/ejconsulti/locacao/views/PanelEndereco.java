package ejconsulti.locacao.views;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Uf;

/**
 * Painel de endereço
 * 
 * @author Edison Jr
 *
 */
public class PanelEndereco extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox<Uf> boxUf;
	private JTextField txtComplemento;
	private JTextField txtReferencia;

	public PanelEndereco() {
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("", "[right]2[][][][][]", "[][][][][][]"));

		JLabel lblRua = new JLabel("Rua");
		add(lblRua, "cell 0 0");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		add(l1, "cell 1 0,alignx trailing");

		txtRua = new JTextField(27);
		add(txtRua, "flowx,cell 2 0 4 1");

		JLabel lblNumero = new JLabel("Numero");
		add(lblNumero, "flowx,cell 0 1");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		add(l2, "cell 1 1,alignx trailing");

		txtNumero = new JTextField(10);
		add(txtNumero, "flowx,cell 2 1");
		
		JLabel lblComplemento = new JLabel("Complemento");
		add(lblComplemento, "cell 0 2,alignx right");
		
		txtComplemento = new JTextField(15);
		add(txtComplemento, "cell 2 2");

		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblBairro, "cell 0 3");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		add(l3, "cell 1 3,alignx trailing");

		txtBairro = new JTextField(20);
		add(txtBairro, "cell 2 3");

		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblCidade, "flowx,cell 0 4");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		add(l4, "cell 1 4,alignx trailing");

		txtCidade = new JTextField(20);
		add(txtCidade, "cell 2 4");

		JLabel lblUf = new JLabel("UF");
		lblUf.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblUf, "flowx,cell 3 4");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		add(l5, "cell 4 4,alignx trailing");

//		boxUf = new JComboBox<Uf>(); // Uf.values() causa erro no WindowBuilder Editor
		boxUf = new JComboBox<Uf>(Uf.values()); 
		boxUf.setSelectedItem(Uf.Bahia);
		add(boxUf, "cell 5 4");
		
		JLabel lblReferncia = new JLabel("Refer\u00EAncia");
		add(lblReferncia, "cell 0 5");
		
		txtReferencia = new JTextField(27);
		add(txtReferencia, "cell 2 5 4 1");
	}

	public JTextField getTxtRua() {
		return txtRua;
	}

	public JTextField getTxtNumero() {
		return txtNumero;
	}
	
	public JTextField getTxtComplemento() {
		return txtComplemento;
	}

	public JTextField getTxtBairro() {
		return txtBairro;
	}

	public JTextField getTxtCidade() {
		return txtCidade;
	}

	public JComboBox<Uf> getBoxUf() {
		return boxUf;
	}
	
	public JTextField getTxtReferencia() {
		return txtReferencia;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		for(Component c : getComponents())
			c.setEnabled(enabled);
		super.setEnabled(enabled);
	}
	
	public void setEditable(boolean editable) {
		for(Component c : getComponents()) {
			if(c instanceof JTextComponent)
				((JTextComponent) c).setEditable(editable);
			else
				c.setEnabled(false);
		}
	}

}
