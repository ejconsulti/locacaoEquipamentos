package ejconsulti.locacao.controllers;

import java.awt.Font;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.ProdutoOS;
import eso.utils.Log;

public class GerarRecibo extends Imprimir {
	
	private OrdemDeServico ordem;
	private Cliente cliente;
	private List<ProdutoOS> produtos;
	
	public GerarRecibo (OrdemDeServico ordem, List<ProdutoOS> produtos){
		super("recibos/pagamento/", 
				new SimpleDateFormat("yyyy-MMM-dd, EEE HH-mm-ss").format(new Date())+".pdf");
		this.ordem = ordem;
		this.produtos = produtos;
		
		cliente = buscarCliente();

		if (imprimir())
			abrir();
		else
			JOptionPane.showMessageDialog(Main.getFrame(), "Erro ao gerar recibo");
	}
	
	private Cliente buscarCliente (){
		ResultSet rs = null;
		Cliente c = null;
		try {
			rs = DAO.getDatabase().select(null, Cliente.TABLE, Cliente.ID + "=?", new Object[]{ordem.getIdCliente()}, null, null);
			if (rs.next())
				c = Cliente.rsToObject(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return c;
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
        Paragraph recibo_de_entrega = new Paragraph("RECIBO DE PAGAMENTO", fonte_titulo);
        recibo_de_entrega.setAlignment(Element.ALIGN_CENTER);
        recibo_de_entrega.setSpacingAfter(25);
          
        //nome do cliente            
        Chunk n = new Chunk("Recebi de:", fonte_negrito);
        Chunk n_valor = new Chunk("  " + cliente.getNome(), fonte_normal);
        //n_valor.setUnderline(0.1f, -2f);
        
        Phrase nome = new Phrase();
        nome.add(n);
        nome.add(n_valor);
        
        //telefone do cliente
        Chunk c = new Chunk("CPF/CNPJ:", fonte_negrito);
        Chunk c_valor = new Chunk("  " + cliente.getCpf(), fonte_normal);
        
        Phrase cpf = new Phrase();
        cpf.add(c);
        cpf.add(c_valor);
        
        //telefone do cliente
        Chunk tel = new Chunk("  Telefone:", fonte_negrito);
        String tel_formated = "(" + cliente.getTelefone().substring(0, 2) + ")" + 
        			cliente.getTelefone().substring(2, 6) + "-" + cliente.getTelefone().substring(6);
        Chunk tel_valor = new Chunk("  " + tel_formated, fonte_normal);
        					//tel_valor.setUnderline(0.1f, -2f);
        
        Phrase telefone = new Phrase();
        telefone.add(tel);
        telefone.add(tel_valor);
        
        //endereço do cliente
        Chunk end = new Chunk("Endereço: ", fonte_negrito);
        Endereco e = ControladorEndereco.getEndereco(cliente.getIdEnderecoEntrega());
        String enderecoCompleto = e.getRua().getNome() + ", Nº" + e.getNumero() + ", " + e.getBairro().getNome() +
        						  ", " + e.getCidade() + "-" + e.getUf();
        
        Chunk end_valor = new Chunk("  " + enderecoCompleto, fonte_normal);
        		//end_valor.setUnderline(0.1f, -2f);
        
        Phrase endereco = new Phrase();
        endereco.add(end);
        endereco.add(end_valor);
        
        //valor a pagar pelo cliente
        Chunk valor = new Chunk("A importância de: ", fonte_negrito);
        Phrase importancia = new Phrase();
        importancia.add(valor);
        
        //Adicionar um texto antes da tabela
        Paragraph pre_tabela = new Paragraph("Referente à locação de:", fonte_normal);
        pre_tabela.setSpacingBefore(10f);
        
        //cria uma tabela sem borda para exibir os dados do cliente
        PdfPTable tabela_dados = new PdfPTable(2);
        tabela_dados.setTotalWidth(new float[]{ 382, 160 });
        tabela_dados.setLockedWidth(true);
        
        PdfPCell linha1_1 = new PdfPCell(nome);
        PdfPCell linha2_1 = new PdfPCell(cpf);
        PdfPCell linha2_2 = new PdfPCell(telefone);
        PdfPCell linha3_1 = new PdfPCell(endereco);
        PdfPCell linha5_1 = new PdfPCell(importancia);
        
        PdfPCell vazio = new PdfPCell();
        
        linha1_1.setFixedHeight(16f);
        linha1_1.setBorderWidthTop(0);
        linha1_1.setBorderWidthLeft(0);
        linha1_1.setBorderWidthRight(0);
        	tabela_dados.addCell(linha1_1);

    	vazio.setFixedHeight(16f);
    	vazio.setBorderWidthTop(0);
    	vazio.setBorderWidthTop(0);
    	vazio.setBorderWidthLeft(0);
    	vazio.setBorderWidthRight(0);
        	tabela_dados.addCell(vazio);
        	
    	linha2_1.setFixedHeight(16f);
        linha2_1.setBorderWidthTop(0);
        linha2_1.setBorderWidthTop(0);
        linha2_1.setBorderWidthLeft(0);
        linha2_1.setBorderWidthRight(0);
        	tabela_dados.addCell(linha2_1);
        
        linha2_2.setFixedHeight(16f);
        linha2_2.setBorderWidthTop(0);
        linha2_2.setBorderWidthTop(0);
        linha2_2.setBorderWidthLeft(0);
        linha2_2.setBorderWidthRight(0);
        	tabela_dados.addCell(linha2_2);
        	
        //linha2_1.setBorder(0);
        linha3_1.setFixedHeight(16f);
        linha3_1.setBorderWidthTop(0);
        linha3_1.setBorderWidthLeft(0);
        linha3_1.setBorderWidthRight(0);
        linha3_1.setColspan(2);
        	tabela_dados.addCell(linha3_1);
        	
        //linha4_1.setBorder(0);
        linha5_1.setFixedHeight(16f);
        linha5_1.setBorderWidthTop(0);
        linha5_1.setBorderWidthLeft(0);
        linha5_1.setBorderWidthRight(0);
        linha5_1.setColspan(2);
            tabela_dados.addCell(linha5_1);
        
        	
        //cria uma tabela para exibir os produtos	
        PdfPTable tabela_produtos = new PdfPTable(2);
        tabela_produtos.setTotalWidth(new float[]{ 422, 120 });
        tabela_produtos.setLockedWidth(true);
        
        PdfPCell header_Quantidade = new PdfPCell(new Phrase("Equipamento", fonte_negrito));
        PdfPCell header_Data = new PdfPCell(new Phrase("Entrega Prevista", fonte_negrito));
        
        header_Quantidade.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header_Data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        tabela_produtos.addCell(header_Quantidade);
        tabela_produtos.addCell(header_Data);
        
        for (int i = 0; i < produtos.size(); i++){
        	Phrase p = new Phrase();
        	p.add(produtos.get(i).getQuantidade() + "  -  ");
        	p.add(produtos.get(i).getNome());
        	
        	Calendar data = Calendar.getInstance();
			
			data.setTime(ordem.getData());
			data.add(Calendar.DAY_OF_MONTH, produtos.get(i).getDias());
        	
        	PdfPCell linha_produto = new PdfPCell(p);
        	PdfPCell linha_data = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(data.getTime()).toString()));
        	linha_data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	
        	tabela_produtos.addCell(linha_produto);
        	tabela_produtos.addCell(linha_data);
        }
        
        //exibe a data da ordem de serviço
        Paragraph data_dia = new Paragraph("Jequié, " + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()).toString(), fonte_normal);
        data_dia.setSpacingBefore(10f);
        data_dia.setSpacingAfter(20f);
        data_dia.setAlignment(Paragraph.ALIGN_RIGHT);
        
        //exibe os campos assinatura
        Paragraph assinaturas_campos = new Paragraph("____________________________________          " +
        		                                     "____________________________________");
        assinaturas_campos.setAlignment(Paragraph.ALIGN_CENTER);
        
        Paragraph assinaturas_nomes =  new Paragraph("                  VISTO                       " +
        											 "                                FIRMA OU RESPONSÁVEL", fonte_normal);
        assinaturas_nomes.setSpacingBefore(-4f);
        assinaturas_nomes.setAlignment(Paragraph.ALIGN_CENTER);
        
        //exibe uma linha horizontal com os devidos espaçamentos
        Paragraph linha_contrato = new Paragraph();
        
        linha_contrato.add(new LineSeparator(1, 100, BaseColor.GRAY, LineSeparator.ALIGN_CENTER, 0));
        linha_contrato.setSpacingBefore(7f);
        linha_contrato.setSpacingAfter(15f);
        
        //exibe o título do contrato
        Paragraph titulo_contrato = new Paragraph("CONTRATO DE LOCAÇÃO", FontFactory.getFont(FontFactory.HELVETICA, 7f, Font.BOLD, BaseColor.GRAY));
        titulo_contrato.setSpacingAfter(5f);
       
        if (img != null)
        	add(img);
        add(linha_inicio);
        add(recibo_de_entrega);
        add(tabela_dados);
        add(pre_tabela);
        add(new Paragraph(" "));
        add(tabela_produtos);
        add(data_dia);
        add(assinaturas_campos);
        add(assinaturas_nomes);
        add(linha_contrato);
        add(titulo_contrato);
	}
}