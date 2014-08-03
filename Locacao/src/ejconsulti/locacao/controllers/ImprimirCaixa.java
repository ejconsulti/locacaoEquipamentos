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

import ejconsulti.locacao.models.Caixa;
import ejconsulti.locacao.models.TipoRecebimento;

public class ImprimirCaixa {
	Document doc = null;
	OutputStream os = null;
	PdfWriter write;
	String pathRecibo;
	String pathLogo; 
	List<Caixa> lista;
	
	public ImprimirCaixa (List<Caixa> lista){
		this.lista = lista;
		
		pathRecibo = "Caixa, " +
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
            Paragraph recibo_de_entrega = new Paragraph("Relatório de Caixa", fonte_titulo);
            recibo_de_entrega.setAlignment(Element.ALIGN_CENTER);
            recibo_de_entrega.setSpacingAfter(25);
            
            //cria uma tabela para exibir os produtos	
            PdfPTable tabela_caixa = new PdfPTable(5);
            tabela_caixa.setTotalWidth(new float[]{ 108, 108, 108, 108, 108 });
            tabela_caixa.setLockedWidth(true);
            
            PdfPCell header_Data = new PdfPCell(new Phrase("Data", fonte_negrito));
            PdfPCell header_Entrada = new PdfPCell(new Phrase("Tipo da Entrada", fonte_negrito));
            PdfPCell header_ValorEntrada = new PdfPCell(new Phrase("Valor da Entrada", fonte_negrito));
            PdfPCell header_Saida = new PdfPCell(new Phrase("Tipo da Saída", fonte_negrito));
            PdfPCell header_ValorSaida = new PdfPCell(new Phrase("Valor da Saída", fonte_negrito));
            
            header_Data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            header_Entrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            header_ValorEntrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            header_Saida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            header_ValorSaida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            tabela_caixa.addCell(header_Data);
            tabela_caixa.addCell(header_Entrada);
            tabela_caixa.addCell(header_ValorEntrada);
            tabela_caixa.addCell(header_Saida);
            tabela_caixa.addCell(header_ValorSaida);
            
            for (int i = 0; i < lista.size(); i++){
            	PdfPCell linha_data = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(lista.get(i).getData().getTime()).toString()));
            	PdfPCell linha_entrada = new PdfPCell(new Phrase(TipoRecebimento.valueOf(lista.get(i).getTipoEntrada()).toString()));
            	PdfPCell linha_valorEntrada = new PdfPCell(new Phrase(String.format("%.2f", lista.get(i).getValorEntrada())));
            	PdfPCell linha_saida = new PdfPCell(new Phrase(lista.get(i).getTipoSaida()));
            	PdfPCell linha_valorSaida = new PdfPCell(new Phrase(String.format("%.2f", lista.get(i).getValorSaida())));
            	
            	linha_data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	linha_entrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	linha_valorEntrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	linha_saida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	linha_valorSaida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	
            	tabela_caixa.addCell(linha_data);
            	tabela_caixa.addCell(linha_entrada);
            	tabela_caixa.addCell(linha_valorEntrada);
            	tabela_caixa.addCell(linha_saida);
            	tabela_caixa.addCell(linha_valorSaida);
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