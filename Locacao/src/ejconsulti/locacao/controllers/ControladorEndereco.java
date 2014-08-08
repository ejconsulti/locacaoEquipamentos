package ejconsulti.locacao.controllers;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Bairro;
import ejconsulti.locacao.models.Cidade;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.Rua;
import ejconsulti.locacao.models.Uf;
import ejconsulti.locacao.views.PanelEndereco;
import eso.database.ContentValues;
import eso.database.SQLiteDatabase;
import eso.utils.Log;

public class ControladorEndereco {
	public static final String TAG = ControladorEndereco.class.getSimpleName();

	private ControladorEndereco() {}
	
	public static Endereco getEndereco(int id) {
		Endereco endereco = null;
		
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Endereco.VIEW, Endereco.ID+" = ?", new Object[] {id}, null, null);
			
			if(rs.next()) {
				Rua rua = Rua.rsToObject(rs);
				Bairro bairro = Bairro.rsToObject(rs);
				Cidade cidade = Cidade.rsToObject(rs);
				Uf uf = Uf.valueOf(rs.getInt(Uf.ID));

				endereco = Endereco.rsToObject(rs);
				endereco.setRua(rua);
				endereco.setBairro(bairro);
				endereco.setCidade(cidade);
				endereco.setUf(uf);
			}
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar endereço com id = "+id, e);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}
		
		return endereco;
	}
	
	public static int cadastrar(Window owner, PanelEndereco panel) {
		
		// Verificar campos obrigatórios
		
		String nomeRua = panel.getTxtRua().getText().trim();
		if(nomeRua.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Rua'.");
			panel.getTxtRua().requestFocus();
			return -1;
		}
		String numero = panel.getTxtNumero().getText().trim();
		if(numero.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Numero'.");
			panel.getTxtNumero().requestFocus();
			return -1;
		}
		String nomeBairro = panel.getTxtBairro().getText().trim();
		if(nomeBairro.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Bairro'.");
			panel.getTxtBairro().requestFocus();
			return -1;
		}
		String nomeCidade = panel.getTxtCidade().getText().trim();
		if(nomeCidade.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Bairro'.");
			panel.getTxtCidade().requestFocus();
			return -1;
		}
		Uf uf = null;
		try {
			uf = (Uf) panel.getBoxUf().getSelectedItem();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(uf == null) {
			JOptionPane.showMessageDialog(owner, "Favor selecionar um estado.");
			panel.getBoxUf().requestFocus();
			return -1;
		}
		
		// Abrir banco de dados
		SQLiteDatabase db = DAO.getDatabase();
		
		int idCidade = -1; 
		try { // Buscar cidade
			ResultSet rs = db.select(new String[] {Cidade.ID}, Cidade.TABLE, 
					Cidade.NOME+" = ? AND "+Cidade.ID_UF+" = ?", 
					new Object[] {nomeCidade, uf.getId()}, null, null);
			
			if(rs.next())
				idCidade = rs.getInt(Cidade.ID);
			
			rs.close();
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar cidade", e);
		}
		
		// Se não existir, insere a cidade
		if(idCidade < 1) {
			ContentValues values = new ContentValues();
			values.put(Cidade.NOME, nomeCidade);
			values.put(Cidade.ID_UF, uf.getId());
			try {
				idCidade = db.insert(Cidade.TABLE, values);
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao inserir cidade", e);
				return -1;
			}
		}
		
		int idBairro = -1; 
		try { // Buscar bairro
			ResultSet rs = db.select(new String[] {Bairro.ID}, Bairro.TABLE, 
					Bairro.NOME+" = ? AND "+Bairro.ID_CIDADE+" = ?", 
					new Object[] {nomeBairro, idCidade}, null, null);
			
			if(rs.next())
				idBairro = rs.getInt(Bairro.ID);
			
			rs.close();
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar bairro", e);
		}
		
		// Se não existir, insere o bairro
		if(idBairro < 1) {
			ContentValues values = new ContentValues();
			values.put(Bairro.NOME, nomeBairro);
			values.put(Bairro.ID_CIDADE, idCidade);
			try {
				idBairro = db.insert(Bairro.TABLE, values);
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao inserir bairro", e);
				return -1;
			}
		}
		
		int idRua = -1; 
		try { // Buscar rua
			ResultSet rs = db.select(new String[] {Rua.ID}, Rua.TABLE, 
					Rua.NOME+" = ? AND "+Rua.ID_BAIRRO+" = ?", 
					new Object[] {nomeRua, idBairro}, null, null);
			
			if(rs.next())
				idRua = rs.getInt(Rua.ID);
			
			rs.close();
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar rua", e);
		}
		
		// Se não existir, insere a rua
		if(idRua < 1) {
			ContentValues values = new ContentValues();
			values.put(Rua.NOME, nomeRua);
			values.put(Rua.ID_BAIRRO, idBairro);
			try {
				idRua = db.insert(Rua.TABLE, values);
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao inserir rua", e);
				return -1;
			}
		}
		
		int idEndereco = -1;
		ContentValues values = new ContentValues();
		values.put(Endereco.NUMERO, numero);
		values.put(Endereco.ID_RUA, idRua);
		values.put(Endereco.COMPLEMENTO, panel.getTxtComplemento().getText());
		values.put(Endereco.REFERENCIA, panel.getTxtReferencia().getText());
		try {
			idEndereco = db.insert(Endereco.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao inserir endereco", e);
			return -1;
		}
		
		return idEndereco;
	}
	
	public static int editar(Window owner, PanelEndereco panel, Endereco endereco) {
		
		// Verificar campos obrigatórios
		
		String nomeRua = panel.getTxtRua().getText().trim();
		if(nomeRua.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Rua'.");
			panel.getTxtRua().requestFocus();
			return -1;
		}
		String numero = panel.getTxtNumero().getText().trim();
		if(numero.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Numero'.");
			panel.getTxtNumero().requestFocus();
			return -1;
		}
		String nomeBairro = panel.getTxtBairro().getText().trim();
		if(nomeBairro.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Bairro'.");
			panel.getTxtBairro().requestFocus();
			return -1;
		}
		String nomeCidade = panel.getTxtCidade().getText().trim();
		if(nomeCidade.isEmpty()) {
			JOptionPane.showMessageDialog(owner, "Favor preencher campo 'Bairro'.");
			panel.getTxtCidade().requestFocus();
			return -1;
		}
		Uf uf = null;
		try {
			uf = (Uf) panel.getBoxUf().getSelectedItem();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(uf == null) {
			JOptionPane.showMessageDialog(owner, "Favor selecionar um estado.");
			panel.getBoxUf().requestFocus();
			return -1;
		}
		
		// Abrir banco de dados
		SQLiteDatabase db = DAO.getDatabase();
		
		int idCidade = endereco.getCidade().getId();
		// Verificase foi alterado a cidade ou o estado (Uf)
		if(!endereco.getCidade().getNome().equals(nomeCidade) 
				|| uf.getId() != endereco.getCidade().getIdUf()) {
			
			idCidade = -1;
			// Buscar cidade
			try {
				ResultSet rs = db.select(new String[] {Cidade.ID}, Cidade.TABLE, 
						Cidade.NOME+" = ? AND "+Cidade.ID_UF+" = ?", 
						new Object[] {nomeCidade, uf.getId()}, null, null);

				if(rs.next())
					idCidade = rs.getInt(Cidade.ID);

				rs.close();
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao buscar cidade", e);
			}

			// Se não existir, insere a cidade
			if(idCidade < 1) {
				ContentValues values = new ContentValues();
				values.put(Cidade.NOME, nomeCidade);
				values.put(Cidade.ID_UF, uf.getId());
				try {
					idCidade = db.insert(Cidade.TABLE, values);
				} catch (SQLException e) {
					Log.e(TAG, "Erro ao inserir cidade", e);
					return -1;
				}
			}
		}
		
		int idBairro = endereco.getBairro().getId();
		// Verificase foi alterado o bairro ou a cidade
		if(!endereco.getBairro().getNome().equals(nomeBairro)
				|| idCidade != endereco.getCidade().getId()) {
			
			idBairro = -1;
			// Buscar bairro
			try {
				ResultSet rs = db.select(new String[] {Bairro.ID}, Bairro.TABLE, 
						Bairro.NOME+" = ? AND "+Bairro.ID_CIDADE+" = ?", 
						new Object[] {nomeBairro, idCidade}, null, null);

				if(rs.next())
					idBairro = rs.getInt(Bairro.ID);

				rs.close();
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao buscar bairro", e);
			}

			// Se não existir, insere o bairro
			if(idBairro < 1) {
				ContentValues values = new ContentValues();
				values.put(Bairro.NOME, nomeBairro);
				values.put(Bairro.ID_CIDADE, idCidade);
				try {
					idBairro = db.insert(Bairro.TABLE, values);
				} catch (SQLException e) {
					Log.e(TAG, "Erro ao inserir bairro", e);
					return -1;
				}
			}
		}
		
		int idRua = endereco.getRua().getId(); 
		// Verificase foi alterado a rua ou o bairro
		if(!endereco.getRua().getNome().equals(nomeRua)
				|| idBairro != endereco.getBairro().getId()) {
			
			idRua = -1;
			// Buscar rua
			try {
				ResultSet rs = db.select(new String[] {Rua.ID}, Rua.TABLE, 
						Rua.NOME+" = ? AND "+Rua.ID_BAIRRO+" = ?", 
						new Object[] {nomeRua, idBairro}, null, null);

				if(rs.next())
					idRua = rs.getInt(Rua.ID);

				rs.close();
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao buscar rua", e);
			}

			// Se não existir, insere a rua
			if(idRua < 1) {
				ContentValues values = new ContentValues();
				values.put(Rua.NOME, nomeRua);
				values.put(Rua.ID_BAIRRO, idBairro);
				try {
					idRua = db.insert(Rua.TABLE, values);
				} catch (SQLException e) {
					Log.e(TAG, "Erro ao inserir rua", e);
					return -1;
				}
			}
		}
		
		int result = -1;
		ContentValues values = new ContentValues();
		values.put(Endereco.NUMERO, numero);
		values.put(Endereco.ID_RUA, idRua);
		values.put(Endereco.COMPLEMENTO, panel.getTxtComplemento().getText());
		values.put(Endereco.REFERENCIA, panel.getTxtReferencia().getText());
		try {
			result = db.update(Endereco.TABLE, values, Endereco.ID+" = ?", endereco.getId());
			if(result > -1)
				result = endereco.getId();
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao editar endereco", e);
		}
		return result;
	}

}
