package ejconsulti.locacao.views;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Cartao;
import ejconsulti.locacao.models.Cheque;
import ejconsulti.locacao.models.Emitente;
import eso.components.DateField;

/**
 * Painel de endereço
 * 
 * @author Sadao
 *
 */
public class PanelEmitente extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<Emitente> cboxEmitente;
	private JComboBox<Cartao> cboxCartao;
	private JComboBox<Cheque> cboxCheque;
	
	private JTextField txtNomeTitular;
	private JTextField txtNumeroCartao;
	private JTextField txtBandeira;
	private DateField txtDataVencimento;
	
	public PanelEmitente() {
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("", "[right]2[][][][][]", "[][][][][][]"));

		JLabel lblNome = new JLabel("Nome Titular");
		add(lblNome, "cell 0 0");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		add(l1, "cell 1 0,alignx trailing");

		txtNomeTitular = new JTextField(27);
		add(txtNomeTitular, "flowx,cell 2 0 4 1");

		JLabel lblNumero = new JLabel("Numero Cartão");
		add(lblNumero, "flowx,cell 0 1");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		add(l2, "cell 1 1,alignx trailing");

		txtNumeroCartao = new JTextField(10);
		add(txtNumeroCartao, "flowx,cell 2 1");
		
		JLabel lblBandeira = new JLabel("Bandeira");
		add(lblBandeira, "cell 0 2,alignx right");
		
		txtBandeira = new JTextField(10);
		add(txtBandeira, "cell 2 2");

		JLabel lblData = new JLabel("Data Vencimento");
		add(lblData, "cell 0 3");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		add(l3, "cell 1 3,alignx trailing");

		txtDataVencimento = new DateField();
		add(txtDataVencimento, "cell 2 3");
	}

	public JComboBox<Emitente> getCboxEmitente() {
		return cboxEmitente;
	}

	public void setCboxEmitente(JComboBox<Emitente> cboxEmitente) {
		this.cboxEmitente = cboxEmitente;
	}

	public JComboBox<Cartao> getCboxCartao() {
		return cboxCartao;
	}

	public void setCboxCartao(JComboBox<Cartao> cboxCartao) {
		this.cboxCartao = cboxCartao;
	}

	public JComboBox<Cheque> getCboxCheque() {
		return cboxCheque;
	}

	public void setCboxCheque(JComboBox<Cheque> cboxCheque) {
		this.cboxCheque = cboxCheque;
	}

	public JTextField getTxtNomeTitular() {
		return txtNomeTitular;
	}

	public void setTxtNomeTitular(JTextField txtNomeTitular) {
		this.txtNomeTitular = txtNomeTitular;
	}

	public JTextField getTxtNumeroCartao() {
		return txtNumeroCartao;
	}

	public void setTxtNumeroCartao(JTextField txtNumeroCartao) {
		this.txtNumeroCartao = txtNumeroCartao;
	}

	public JTextField getTxtBandeira() {
		return txtBandeira;
	}

	public void setTxtBandeira(JTextField txtBandeira) {
		this.txtBandeira = txtBandeira;
	}

	public DateField getTxtDataVencimento() {
		return txtDataVencimento;
	}

	public void setTxtDataVencimento(DateField txtDataVencimento) {
		this.txtDataVencimento = txtDataVencimento;
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
