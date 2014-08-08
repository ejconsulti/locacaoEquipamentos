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
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.StatusRecebimento;
import ejconsulti.locacao.models.TipoRecebimento;
import eso.utils.Log;

public class ImprimirRecebimento extends Imprimir {
	
	private List<Recebimento> lista;
	
	public ImprimirRecebimento (List<Recebimento> lista){
		super("relatórios/recebimentos/", 
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
        Paragraph recibo_de_entrega = new Paragraph("Relatório de Recebimentos", fonte_titulo);
        recibo_de_entrega.setAlignment(Element.ALIGN_CENTER);
        recibo_de_entrega.setSpacingAfter(25);
        
        //cria uma tabela para exibir os produtos	
        PdfPTable tabela_caixa = new PdfPTable(6);
        tabela_caixa.setTotalWidth(new float[]{ 90, 90, 90, 90, 90, 90 });
        tabela_caixa.setLockedWidth(true);
        
        PdfPCell header_idOrdem = new PdfPCell(new Phrase("Ordem de Serviço", fonte_negrito));
        PdfPCell header_tipoUltimaEntrada = new PdfPCell(new Phrase("Tipo da Última Entrada", fonte_negrito));
        PdfPCell header_quantidadeParcial = new PdfPCell(new Phrase("Quantidade Parcial", fonte_negrito));
        PdfPCell header_quantidadeRestante = new PdfPCell(new Phrase("Quantidade Restante", fonte_negrito));
        PdfPCell header_quantidadeTotal = new PdfPCell(new Phrase("Quantidade Total", fonte_negrito));
        PdfPCell header_status = new PdfPCell(new Phrase("Status", fonte_negrito));
        
        header_idOrdem.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_tipoUltimaEntrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_quantidadeParcial.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_quantidadeRestante.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_quantidadeTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_status.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        tabela_caixa.addCell(header_idOrdem);
        tabela_caixa.addCell(header_tipoUltimaEntrada);
        tabela_caixa.addCell(header_quantidadeParcial);
        tabela_caixa.addCell(header_quantidadeRestante);
        tabela_caixa.addCell(header_quantidadeTotal);
        tabela_caixa.addCell(header_status);
        
        for (int i = 0; i < lista.size(); i++){
        	PdfPCell linha_ordem = new PdfPCell(new Phrase(lista.get(i).getIdOrdemServico().toString()));
        	PdfPCell linha_tipoUltimaEntrada = new PdfPCell(new Phrase(TipoRecebimento.valueOf(lista.get(i).getTipo()).toString()));
        	PdfPCell linha_quantidadeParcial = new PdfPCell(new Phrase(String.format("%.2f", lista.get(i).getQuantidadeParcial())));
        	PdfPCell linha_quantidadeRestante = new PdfPCell(new Phrase(String.format("%.2f", lista.get(i).getQuantidadeTotal() - lista.get(i).getQuantidadeParcial())));
        	PdfPCell linha_quantidadeTotal = new PdfPCell(new Phrase(String.format("%.2f", lista.get(i).getQuantidadeTotal())));
        	PdfPCell linha_status = new PdfPCell(new Phrase(StatusRecebimento.valueOf(lista.get(i).getStatus()).toString()));
        	
        	linha_ordem.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_tipoUltimaEntrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_quantidadeParcial.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_quantidadeRestante.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_quantidadeTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	linha_status.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	
        	tabela_caixa.addCell(linha_ordem);
        	tabela_caixa.addCell(linha_tipoUltimaEntrada);
        	tabela_caixa.addCell(linha_quantidadeParcial);
        	tabela_caixa.addCell(linha_quantidadeRestante);
        	tabela_caixa.addCell(linha_quantidadeTotal);
        	tabela_caixa.addCell(linha_status);
        }
        
        //exibe a data da ordem de serviço
        Paragraph data_dia = new Paragraph("Jequié, " + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()).toString(), fonte_normal);
        data_dia.setSpacingBefore(10f);
        data_dia.setSpacingAfter(20f);
        data_dia.setAlignment(Paragraph.ALIGN_RIGHT);

        if (img != null)
        	add(img);
        add(linha_inicio);
        add(recibo_de_entrega);
        add(new Paragraph(" "));
        add(tabela_caixa);
        add(data_dia);
	}
}