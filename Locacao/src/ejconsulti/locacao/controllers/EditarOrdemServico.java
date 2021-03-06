package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.OrdemServico;
import ejconsulti.locacao.models.ProdutoOSTableModel;
import ejconsulti.locacao.models.ValorCellRenderer;
import ejconsulti.locacao.models.OrdemServico.Status;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.views.DialogOrdemDeServico;
import eso.database.ContentValues;
import eso.utils.Log;

/**
 * Visualizar Ordem de Serviço
 * 
 * @author Érico Jr
 * @author Edison Jr
 *
 */
public class EditarOrdemServico implements ActionListener {
	public static final String TAG = EditarOrdemServico.class.getSimpleName();

	private DialogOrdemDeServico dialog;

	private OrdemServico ordem;

	public EditarOrdemServico(OrdemServico ordem) {
		initialize(ordem);
	}

	private void initialize(OrdemServico ordem) {
		dialog = new DialogOrdemDeServico(Main.getFrame(), "Ordem de Serviço");
		this.ordem = ordem;

		addEvents();
		addProdutosTabela(ordem);

		carregarCliente();

		//Organizar colunas
		TableColumnModel model = dialog.getTabela().getColumnModel();
		model.getColumn(ProdutoOSTableModel.NOME.getIndex()).setPreferredWidth(300);
		TableColumn c = model.getColumn(ProdutoOSTableModel.VALOR_DIARIO.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
		c = model.getColumn(ProdutoOSTableModel.VALOR_MENSAL.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
		model.getColumn(ProdutoOSTableModel.QUANTIDADE.getIndex()).setPreferredWidth(50);
		model.getColumn(ProdutoOSTableModel.DIAS.getIndex()).setPreferredWidth(50);
		c = model.getColumn(ProdutoOSTableModel.TOTAL.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
				
		dialog.getCboxNome().setEnabled(false);
		dialog.getTxtTelefone().setEnabled(false);
		dialog.getCboxProdutos().setEnabled(false);
		dialog.getTabela().setEnabled(false);
		dialog.getBtnAdicionar().setEnabled(false);
		dialog.getBtnExcluir().setEnabled(false);
		dialog.getTxtDataDevolucao().setEnabled(false);
		dialog.getTxtTotal().setEnabled(false);

		if (ordem.getStatus().getId() == Status.Cancelada.getId()){
			dialog.getTxtDataEntrega().setEnabled(false);
			dialog.getBtnSalvar().setEnabled(false);
			dialog.getBtnCancelar().setEnabled(false);
		}
		dialog.setVisible(true);
	}

	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}

	private void addProdutosTabela(OrdemServico ordem) {
		ResultSet rs = null;
		try {
			//Carrega produtos da ordem de serviço
			rs = DAO.getDatabase().select(null, ProdutoOS.VIEW, ProdutoOS.ID_ORDEMSERVICO+" = ?", new Object[]{ordem.getId()}, null, null);

			List<ProdutoOS> list = new ArrayList<ProdutoOS>();
			while(rs.next()){
				ProdutoOS p = ProdutoOS.rsToObject(rs);
				list.add(p);
			}
			dialog.getProdutoOrdemTableModel().addAll(list);

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar produtos", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {}
			}
		}		
	}


	private void carregarCliente() {
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(new String[]{Cliente.NOME, Cliente.TELEFONE}, 
					Cliente.TABLE, Cliente.ID+" = ?", new Integer[]{ordem.getIdCliente()}, null, null);
			
			if(rs.next()) {
				dialog.getTxtTelefone().setText(rs.getString(Cliente.TELEFONE));
				dialog.getCboxNome().setSelectedItem(rs.getString(Cliente.NOME));
			}
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar cliente", e);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}

		Endereco endereco = ControladorEndereco.getEndereco(ordem.getIdEnderecoEntrega());

		dialog.getPanelEnderecoEntrega().getTxtRua().setText(endereco.getRua().getNome());
		dialog.getPanelEnderecoEntrega().getTxtNumero().setText(endereco.getNumero());
		dialog.getPanelEnderecoEntrega().getTxtComplemento().setText(endereco.getComplemento());
		dialog.getPanelEnderecoEntrega().getTxtBairro().setText(endereco.getBairro().getNome());
		dialog.getPanelEnderecoEntrega().getTxtCidade().setText(endereco.getCidade().getNome());
		dialog.getPanelEnderecoEntrega().getBoxUf().setSelectedItem(endereco.getUf().getNome());
		dialog.getPanelEnderecoEntrega().getTxtReferencia().setText(endereco.getComplemento());
		dialog.getTxtDataEntrega().setDate(ordem.getData());
		dialog.getTxtTotal().setText(String.format("%.2f", ordem.getValor()));
	}

	private void salvar() {
		//altera a data da ordem de serviço e dos produtos

		//		 Verificar campos obrigatórios
		Date data = dialog.getTxtDataEntrega().getDate();
		if(data == null) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Data de entrega'.");
			dialog.getTxtDataEntrega().requestFocus();
			return;
		}
		if(!data.equals(ordem.getData())) {
			try {
				ContentValues values = new ContentValues();
				values.put(OrdemServico.DATA_ENTREGA, data);
				DAO.getDatabase().update(OrdemServico.TABLE, values, OrdemServico.ID + " = ?", new Object[]{ordem.getId()});
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao alterar ordem de serviço.");
				return;
			}
		}

		Main.getFrame().getBtnOrdemdeServico().doClick();
		dialog.dispose();
	}

	private void cancelar() {
		int option = JOptionPane.showConfirmDialog(dialog, "Deseja realmente cancelar Ordem de Serviço?", 
				"Cancelar Ordem de Serviço", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if(option == JOptionPane.YES_OPTION) {
			try {
				//altera o status da ordem de serviço
				ContentValues contValues = new ContentValues();
				contValues.put(OrdemServico.STATUS, Status.Cancelada.getId());
				DAO.getDatabase().update(OrdemServico.TABLE, contValues, OrdemServico.ID+" = ?", new Object[]{ordem.getId()});
			} catch (Exception e) {
				Log.e(TAG, "Erro ao cancelar ordem de serviço.", e);
			}

			Main.getFrame().getBtnOrdemdeServico().doClick();
			dialog.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		switch(e.getActionCommand()) {
		case "Salvar":
			salvar();
			break;
		case "Cancelar":
			cancelar();
			break;
		}
	}
}

