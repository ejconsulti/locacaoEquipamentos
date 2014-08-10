package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.views.DialogProduto;
import eso.database.ContentValues;
import eso.utils.Log;

/**
 * Cadastrar produto
 * 
 * @author Edison Jr
 *
 */
public class CadastrarProduto implements ActionListener {
	public static final String TAG = CadastrarProduto.class.getSimpleName();
	
	private DialogProduto dialog;

	public CadastrarProduto() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogProduto(Main.getFrame(), "Cadastrar produto");
		
		addEvents();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void cadastrar() {
		
		// Verificar campos obrigatórios
		
		String nome = dialog.getTxtNome().getText().trim();
		if(nome.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Nome'.");
			dialog.getTxtNome().requestFocus();
			return;
		}
		String valorDiario = dialog.getTxtValorDiario().getText();
		if(valorDiario.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Valor diário'.");
			dialog.getTxtValorDiario().requestFocus();
			return;
		}
		String valorMensal = dialog.getTxtValorMensal().getText();
		if(valorMensal.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Valor mensal'.");
			dialog.getTxtValorMensal().requestFocus();
			return;
		}
		String strQuantidade = dialog.getTxtValorMensal().getText();
		if(strQuantidade.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Quantidade'.");
			dialog.getTxtQuantidade().requestFocus();
			return;
		}
		
		// Cadastrar produto
		ContentValues values = new ContentValues();
		values.put(Produto.NOME, nome);
		values.put(Produto.VALOR_DIARIO, dialog.getTxtValorDiario().doubleValue());
		values.put(Produto.VALOR_MENSAL, dialog.getTxtValorMensal().doubleValue());
		Double quantidade = dialog.getTxtQuantidade().doubleValue();
		values.put(Produto.QUANTIDADE_TOTAL, quantidade);
		values.put(Produto.QUANTIDADE, quantidade);
		try {
			DAO.getDatabase().insert(Produto.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar produto");
			return;
		}
		
		dialog.dispose();
		
		// Atualização da tabela
		Main.getFrame().getBtnProdutos().doClick();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Salvar":
			cadastrar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
	}

}
