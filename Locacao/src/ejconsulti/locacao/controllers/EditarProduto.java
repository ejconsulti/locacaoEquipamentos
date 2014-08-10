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
 * Editar produto
 * 
 * @author Edison Jr
 *
 */
public class EditarProduto implements ActionListener {
	public static final String TAG = EditarProduto.class.getSimpleName();
	
	private DialogProduto dialog;
	
	private Produto produto; 

	public EditarProduto(Produto produto) {
		this.produto = produto;
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogProduto(Main.getFrame(), "Editar produto");
		dialog.getTxtNome().setText(produto.getNome());
		dialog.getTxtValorDiario().setValue(produto.getValorDiario());
		dialog.getTxtValorMensal().setValue(produto.getValorMensal());
		dialog.getTxtQuantidade().setValue(produto.getQuantidadeTotal());
		
		addEvents();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void editar() {
		
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
		
		// Editar produto
		ContentValues values = new ContentValues();
		values.put(Produto.NOME, nome);
		values.put(Produto.VALOR_DIARIO, dialog.getTxtValorDiario().doubleValue());
		values.put(Produto.VALOR_MENSAL, dialog.getTxtValorMensal().doubleValue());
		values.put(Produto.QUANTIDADE_TOTAL, dialog.getTxtQuantidade().doubleValue());
		try {
			DAO.getDatabase().update(Produto.TABLE, values, Produto.ID+" = ?", produto.getId());
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao editar produto");
			return;
		}
		
		dialog.dispose();
		
		// Atualizar da tabela
		Main.getFrame().getBtnProdutos().doClick();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Salvar":
			editar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
	}

}
