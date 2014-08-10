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
import ejconsulti.locacao.models.Caixa;
import eso.utils.Log;

public class ImprimirCaixa extends Imprimir {
	
	private List<Caixa> lista;
	
	public ImprimirCaixa(List<Caixa> lista){
		super("relatórios/caixa/", 
				new SimpleDateFormat("yyyy-MMM-dd, EEE HH-mm-ss").format(new Date())+".pdf");
		this.lista = lista;
		
		if (imprimir())
			abrir();
		else
			JOptionPane.showMessageDialog(Main.getFrame(), "Erro ao gerar relatório");
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
        
        for (Caixa c : lista){
        	PdfPCell linha_data = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(c.getData())));
        	PdfPCell linha_entrada = new PdfPCell(new Phrase(c.getTipoEntrada().toString()));
        	PdfPCell linha_valorEntrada = new PdfPCell(new Phrase(String.format("%.2f", c.getValorEntrada())));
        	PdfPCell linha_saida = new PdfPCell(new Phrase(c.getTipoSaida()));
        	PdfPCell linha_valorSaida = new PdfPCell(new Phrase(String.format("%.2f", c.getValorSaida())));
        	
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

        if (img != null)
        	add(img);
        add(linha_inicio);
        add(recibo_de_entrega);
        add(new Paragraph(" "));
        add(tabela_caixa);
        add(data_dia);
	}
}