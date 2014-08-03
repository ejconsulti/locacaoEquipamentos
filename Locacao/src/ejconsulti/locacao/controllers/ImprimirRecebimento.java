package ejconsulti.locacao.controllers;

import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.StatusRecebimento;
import ejconsulti.locacao.models.TipoRecebimento;

public class ImprimirRecebimento {
	Document doc = null;
	OutputStream os = null;
	PdfWriter write;
	String pathRecibo;
	String pathLogo; 
	List<Recebimento> lista;
	
	public ImprimirRecebimento (List<Recebimento> lista){
		this.lista = lista;
		
		pathRecibo = "Recebimento, " +
				new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()).toString() + 
				".pdf";
		
		pathLogo = "argolo-locacao.jpg";
		
		try {
			inicilizarDocumento();
			criarDocumento();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		abrirDocumento();
	}
	
	public void abrirDocumento (){
		if (Desktop.isDesktopSupported()) {
		    try {
		        File myFile = new File(pathRecibo);
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		}
	}
	
	private void inicilizarDocumento(){		
		doc = new Document(PageSize.A4, 28, 28, 20, 14);
        try {
			os = new FileOutputStream(pathRecibo);
			write = PdfWriter.getInstance(doc, os);
	        doc.open();	
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void criarDocumento () throws IOException{
		try {           
			//adiciona a logo
            Image img = Image.getInstance(pathLogo);
            img.scaleAbsolute(540, 90);
            img.setAlignment(Element.ALIGN_CENTER);
            
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
            
            doc.add(img);
            doc.add(linha_inicio);
            doc.add(recibo_de_entrega);
            doc.add(new Paragraph(" "));
            doc.add(tabela_caixa);
            doc.add(data_dia);
            
        }
		catch (Exception e){
        	e.printStackTrace();
        }
		finally {
            if (doc != null) {
                //fecha o documento
                doc.close();
            }
            if (os != null) {
               //fecha a stream de saída
               os.close();
            }
        }
	}
}