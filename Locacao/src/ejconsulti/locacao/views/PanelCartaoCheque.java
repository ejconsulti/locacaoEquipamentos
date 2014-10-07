package ejconsulti.locacao.views;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

/**
 * Painel de emitente
 * 
 * @author Sadao
 *
 */
public class PanelCartaoCheque extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField txtNomeTitular;
	private JTextField txtNumeroCartaoCheque;
	private JTextField txtBandeiraBanco;
	private JTextField txtDataVencimento;
	
	private JLabel lblNome;
	private JLabel lblNumero;
	private JLabel lblBandeiraBanco;
	private JLabel lblData;
	
	public PanelCartaoCheque() {
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("", "[right]2[][][][][]", "[][][][][][]"));

		lblNome = new JLabel("Nome Titular");
		add(lblNome, "cell 0 0");
		
		txtNomeTitular = new JTextField(27);
		add(txtNomeTitular, "flowx,cell 1 0 4 1");

		lblNumero = new JLabel("Número");
		add(lblNumero, "flowx,cell 0 1");
		
		txtNumeroCartaoCheque = new JTextField(27);
		add(txtNumeroCartaoCheque, "flowx,cell 1 1");
		
		lblBandeiraBanco = new JLabel("Banco / Bandeira");
		add(lblBandeiraBanco, "cell 0 2,alignx right");
		
		txtBandeiraBanco = new JTextField(27);
		add(txtBandeiraBanco, "cell 1 2");
		
		lblData = new JLabel("Data Vencimento");
		add(lblData, "cell 0 3,alignx right");
		
		txtDataVencimento = new JTextField(27);
		add(txtDataVencimento, "cell 1 3");
	}

	public JLabel getLblNome() {
		return lblNome;
	}

	public void setLblNome(JLabel lblNome) {
		this.lblNome = lblNome;
	}

	public JLabel getLblNumero() {
		return lblNumero;
	}

	public void setLblNumero(JLabel lblNumero) {
		this.lblNumero = lblNumero;
	}

	public JLabel getLblBandeiraBanco() {
		return lblBandeiraBanco;
	}

	public void setLblBandeiraBanco(JLabel lblBandeiraBanco) {
		this.lblBandeiraBanco = lblBandeiraBanco;
	}

	public JLabel getLblData() {
		return lblData;
	}

	public void setLblData(JLabel lblData) {
		this.lblData = lblData;
	}

	public JTextField getTxtNomeTitular() {
		return txtNomeTitular;
	}

	public void setTxtNomeTitular(JTextField txtNomeTitular) {
		this.txtNomeTitular = txtNomeTitular;
	}

	public JTextField getTxtNumeroCartaoCheque() {
		return txtNumeroCartaoCheque;
	}

	public void setTxtNumeroCartaoCheque(JTextField txtNumeroCartaoCheque) {
		this.txtNumeroCartaoCheque = txtNumeroCartaoCheque;
	}

	public JTextField getTxtBandeiraBanco() {
		return txtBandeiraBanco;
	}

	public void setTxtBandeiraBanco(JTextField txtBandeiraBanco) {
		this.txtBandeiraBanco = txtBandeiraBanco;
	}

	public JTextField getTxtDataVencimento() {
		return txtDataVencimento;
	}

	public void setTxtDataVencimento(JTextField txtDataVencimento) {
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
