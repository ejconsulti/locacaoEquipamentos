package ejconsulti.locacao.controllers;

import java.awt.Font;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;

import ejconsulti.locacao.assets.Config;
import ejconsulti.locacao.models.Despesa;
import ejconsulti.locacao.models.StatusDespesa;
import eso.utils.Log;

public class ImprimirDespesa extends Imprimir {
	
	private List<Despesa> lista;
	
	public ImprimirDespesa (List<Despesa> lista) {
		super("relatórios/despesas/", 
				new SimpleDateFormat("yyyy-MMM-dd, EEE HH-mm-ss").format(new Date())+".pdf");
		this.lista = lista;

		if (imprimir())
			abrir();
		else
			JOptionPane.showMessageDialog(Main.getFrame(), "Erro ao gerar relatório.");
	}
	
	@Override
	public void documento() throws DocumentException {
		//adiciona a logo
		Image img = null;
		try {
	        img = Image.getInstance(Config.getProperty(Config.IMAGEM));
	        img.scaleAbsolute(540, 90);
	        img.setAlignment(Element.ALIGN_CENTER);
		} catch (IOException e) {
			Log.e(TAG, "Erro ao carregar imagem", e);
		}
        
        //traça uma linha horizontal
        Paragraph linha_inicio = new Paragraph();
        linha_inicio.add(new LineSeparator());
        linha_inicio.setSpacingBefore(-12f);
        linha_inicio.setSpacingAfter(4f);
                 
        //fontes que srão usadas
        com.itextpdf.text.Font fonte_titulo = FontFactory.getFont("helvetica", 30, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font fonte_negrito = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        com.itextpdf.text.Font fonte_normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
        
        //título do documento
        Paragraph recibo_de_entrega = new Paragraph("Relatório de Despesa", fonte_titulo);
        recibo_de_entrega.setAlignment(Element.ALIGN_CENTER);
        recibo_de_entrega.setSpacingAfter(25);
        
        //cria uma tabela para exibir os produtos	
        PdfPTable tabela_caixa = new PdfPTable(6);
        tabela_caixa.setTotalWidth(new float[]{ 90, 90, 90, 90, 90, 90 });
        tabela_caixa.setLockedWidth(true);
        
        PdfPCell header_nome = new PdfPCell(new Phrase("Nome", fonte_negrito));
        PdfPCell header_desricao = new PdfPCell(new Phrase("Descrição", fonte_negrito));
        PdfPCell header_data = new PdfPCell(new Phrase("Data Pagamento", fonte_negrito));
        PdfPCell header_valor = new PdfPCell(new Phrase("Valor", fonte_negrito));
        PdfPCell header_status = new PdfPCell(new Phrase("Status", fonte_negrito));
        PdfPCell header_tipo = new PdfPCell(new Phrase("Tipo da Despesa", fonte_negrito));
        
        header_nome.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_desricao.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_valor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_status.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_tipo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        tabela_caixa.addCell(header_nome);
        tabela_caixa.addCell(header_desricao);
        tabela_caixa.addCell(header_data);
        tabela_caixa.addCell(header_valor);
        tabela_caixa.addCell(header_status);
        tabela_caixa.addCell(header_tipo);
        
        for (int i = 0; i < lista.size(); i++){
        	PdfPCell linha_nome = new PdfPCell(new Phrase(lista.get(i).getNome()));
        	PdfPCell linha_descricao = new PdfPCell(new Phrase(lista.get(i).getDescricao()));
        	PdfPCell linha_data = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(lista.get(i).getDataPagamento().getTime()).toString()));
        	PdfPCell linha_valor = new PdfPCell(new Phrase(String.format("%.2f", lista.get(i).getValor())));
        	PdfPCell linha_status = new PdfPCell(new Phrase(StatusDespesa.valueOf(lista.get(i).getStatus()).toString()));
        	PdfPCell linha_tipo = new PdfPCell(new Phrase(StatusDespesa.valueOf(lista.get(i).getTipo()).toString()));
        	
        	linha_nome.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_descricao.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_valor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_status.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_tipo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	
        	tabela_caixa.addCell(linha_nome);
        	tabela_caixa.addCell(linha_descricao);
        	tabela_caixa.addCell(linha_data);
        	tabela_caixa.addCell(linha_valor);
        	tabela_caixa.addCell(linha_status);
        	tabela_caixa.addCell(linha_tipo);
        }
        
        //exibe a data da ordem de serviço
        Paragraph data_dia = new Paragraph("Jequié, " + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()).toString(), fonte_normal);
        data_dia.setSpacingBefore(10f);
        data_dia.setSpacingAfter(20f);
        data_dia.setAlignment(Paragraph.ALIGN_RIGHT);
        
        if(img != null)
        	add(img);
        add(linha_inicio);
        add(recibo_de_entrega);
        add(new Paragraph(" "));
        add(tabela_caixa);
        add(data_dia);
	}
}