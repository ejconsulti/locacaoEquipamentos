package ejconsulti.locacao.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.TableColumnModel;

import ejconsulti.locacao.models.Notificacao;
import ejconsulti.locacao.models.NotificacaoTableModel;
import ejconsulti.locacao.views.BoxNotificacao;
import ejconsulti.locacao.views.PanelNotificacoes;

/**
 * Consultar notificações
 * 
 * @author Edison Jr
 *
 */
public class Notificacoes {
	public static final String TAG = Notificacoes.class.getSimpleName();
	
	private static Notificacoes instance;

	public static Notificacoes getInstance() {
		if(instance == null)
			instance = new Notificacoes();
		return instance;
	}

	private PanelNotificacoes panel;

	public Notificacoes(){
		initialize();
	}

	private void initialize() {
		panel = new PanelNotificacoes();
	}

	public static void addGrupo(String grupo, List<Notificacao> lista, final Action acao) {
		if(lista.size() > 0) {
			final BoxNotificacao box = new BoxNotificacao(grupo, "/icones/funcionarios.png");
			box.getTable().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
						int row = box.getTable().getSelectedRow();

						if(row > -1) {
							// Converte a linha do filtro para a linha do modelo
							int index = box.getTable().convertRowIndexToModel(row);
							Notificacao c = box.getModel().get(index);
							acao.action(c.getObjeto());
						}
					}
				}
			});
			for(Notificacao o : lista)
				box.addNotificacao(o);
			
			TableColumnModel col = box.getTable().getColumnModel();
			col.getColumn(NotificacaoTableModel.NOTIFICACAO.getIndex()).setPreferredWidth(390);
			col.getColumn(NotificacaoTableModel.PRIORIDADE.getIndex()).setPreferredWidth(10);
			
			getContentPanel().add(box);
		}
	}
	
	public static final void clear() {
		getInstance().panel.clear();
	}

	public static PanelNotificacoes getContentPanel() {
		return getInstance().panel;
	}

	public static interface Action {
		public void action(Object objeto);
	}

}
