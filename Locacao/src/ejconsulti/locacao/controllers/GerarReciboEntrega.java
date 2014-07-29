package ejconsulti.locacao.controllers;

import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoOS;

public class GerarReciboEntrega {
	Document doc = null;
	OutputStream os = null;
	OrdemDeServico ordem = null;
	PdfWriter write;
	Cliente cliente = null;
	String pathRecibo;
	String pathLogo;
	ArrayList<Produto> produtos = new ArrayList<Produto>();
	ArrayList<Date> datas_entrega = new ArrayList<Date>();
	ArrayList<Integer> quantidades = new ArrayList<Integer>();
	
	public GerarReciboEntrega (OrdemDeServico ordem){
		this.ordem = ordem;
		
		cliente = buscarCliente();
		buscarProdutos();
		
		pathRecibo = ordem.getId() + 
				", Entrega, " +
				new SimpleDateFormat("dd-MM-yyyy").format(ordem.getData()).toString() + 
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
	
	private void buscarProdutos (){
		ResultSet rs = null;
		ResultSet rsProduto = null;
		try {
			//busca os codigos de produto da ordem de serviçi na tabela ProdutosLocados
			rs = DAO.getDatabase().select(null, ProdutoOS.TABLE, ProdutoOS.ID_ORDEMSERVICO + " =? ", new Integer[]{ordem.getId()}, null, Produto.ID);

			while(rs.next()){
				int codigoProduto = rs.getInt(Produto.ID);
				rsProduto = DAO.getDatabase().select(null, Produto.TABLE, Produto.ID + "=?", new Integer[]{codigoProduto}, null, null);

				Produto p = Produto.rsToObject(rsProduto);
				
				Calendar c = Calendar.getInstance();
				
				c.setTime(ordem.getData());
				c.add(Calendar.DAY_OF_MONTH, rs.getInt(ProdutoOS.DIAS));
				
				datas_entrega.add(c.getTime());
				quantidades.add(rs.getInt(ProdutoOS.QUANTIDADE_LOCADA));
				produtos.add(p);
				

				rsProduto.close();
			}	

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			//if(rs != null) {
			try {
				rs.close();
				rsProduto.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

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
            Paragraph recibo_de_entrega = new Paragraph("RECIBO DE ENTREGA", fonte_titulo);
            recibo_de_entrega.setAlignment(Element.ALIGN_CENTER);
            recibo_de_entrega.setSpacingAfter(25);
              
            //nome do cliente            
            Chunk n = new Chunk("Nome:", fonte_negrito);
            Chunk n_valor = new Chunk("  " + cliente.getNome(), fonte_normal);
            //n_valor.setUnderline(0.1f, -2f);
            
            Phrase nome = new Phrase();
            nome.add(n);
            nome.add(n_valor);
            
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
            
            //ponto de referência do cliente
            Chunk ref = new Chunk("Ponto de Referência: ", fonte_negrito);
            Chunk ref_valor = new Chunk("   " + e.getReferencia(), fonte_normal);
            ref_valor.setUnderline(0.1f, -2f);
            
            Phrase referencia = new Phrase();
            referencia.add(ref);
            referencia.add(ref_valor);
            
            //Adicionar um texto antes da tabela
            Paragraph pre_tabela = new Paragraph("Recebe os equipamentos abaixo para locação em perfeito estado de uso:", fonte_normal);
            pre_tabela.setSpacingBefore(10f);
            
            //cria uma tabela sem borda para exibir os dados do cliente
            PdfPTable tabela_dados = new PdfPTable(2);
            tabela_dados.setTotalWidth(new float[]{ 382, 160 });
            tabela_dados.setLockedWidth(true);
            
            PdfPCell linha1_1 = new PdfPCell(nome);
            PdfPCell linha1_2 = new PdfPCell(telefone);
            PdfPCell linha2_1 = new PdfPCell(endereco);
            PdfPCell linha3_1 = new PdfPCell(referencia);
            
            
            linha1_1.setFixedHeight(16f);
            linha1_1.setBorderWidthTop(0);
            linha1_1.setBorderWidthLeft(0);
            linha1_1.setBorderWidthRight(0);
            	tabela_dados.addCell(linha1_1);
            
            linha1_2.setFixedHeight(16f);
            linha1_2.setBorderWidthTop(0);
            linha1_2.setBorderWidthTop(0);
            linha1_2.setBorderWidthLeft(0);
            linha1_2.setBorderWidthRight(0);
            	tabela_dados.addCell(linha1_2);
            	
            //linha2_1.setBorder(0);
            linha2_1.setFixedHeight(16f);
            linha2_1.setBorderWidthTop(0);
            linha2_1.setBorderWidthLeft(0);
            linha2_1.setBorderWidthRight(0);
            linha2_1.setColspan(2);
            	tabela_dados.addCell(linha2_1);
            
            //linha3_1.setBorder(0);
            linha3_1.setFixedHeight(16f);
            linha3_1.setBorderWidthTop(0);
            linha3_1.setBorderWidthLeft(0);
            linha3_1.setBorderWidthRight(0);
            linha3_1.setColspan(2);
            	tabela_dados.addCell(linha3_1);
            
            	
            //cria uma tabela para exibir os produtos	
            PdfPTable tabela_produtos = new PdfPTable(2);
            tabela_produtos.setTotalWidth(new float[]{ 422, 120 });
            tabela_produtos.setLockedWidth(true);
            
            PdfPCell header_Quantidade = new PdfPCell(new Phrase("Equipamento", fonte_negrito));
            PdfPCell header_Data = new PdfPCell(new Phrase("Devolução Prevista", fonte_negrito));
            
            header_Quantidade.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            header_Data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            tabela_produtos.addCell(header_Quantidade);
            tabela_produtos.addCell(header_Data);
            
            for (int i = 0; i < produtos.size(); i++){
            	Phrase p = new Phrase();
            	p.add(quantidades.get(i) + "  -  ");
            	p.add(produtos.get(i).getNome());
            	
            	PdfPCell linha_produto = new PdfPCell(p);
            	PdfPCell linha_data = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(datas_entrega.get(i)).toString()));
            	linha_data.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	
            	tabela_produtos.addCell(linha_produto);
            	tabela_produtos.addCell(linha_data);
            }
            
            //exibe a data da ordem de serviço
            Paragraph data_ordem = new Paragraph("Jequié, " + new SimpleDateFormat("dd/MM/yyyy").format(ordem.getData()).toString(), fonte_normal);
            data_ordem.setSpacingBefore(10f);
            data_ordem.setSpacingAfter(20f);
            data_ordem.setAlignment(Paragraph.ALIGN_RIGHT);
            
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
           
            doc.add(img);
            doc.add(linha_inicio);
            doc.add(recibo_de_entrega);
            doc.add(tabela_dados);
            doc.add(pre_tabela);
            doc.add(new Paragraph(" "));
            doc.add(tabela_produtos);
            doc.add(data_ordem);
            doc.add(assinaturas_campos);
            doc.add(assinaturas_nomes);
            doc.add(linha_contrato);
            doc.add(titulo_contrato);
            
            //exibe o texto do contrato
            BufferedReader br;
            String texto_contrato = "";
            try {
            	
            	br = new BufferedReader(new FileReader("contrato.txt"));
            	String line = null;
            	while ((line = br.readLine()) != null) {
            		texto_contrato += line;
            	    if (texto_contrato.contains("\\n")){
            	    	boolean tab = false;
            	    	            	    	
            	    	if (texto_contrato.contains("\\t"))
            	    		tab = true;
            	    	
            	    	texto_contrato = texto_contrato.replace("\\n", "");
            	    	texto_contrato = texto_contrato.replace("\\t", "");
            	    	
            	    	Paragraph contrato = new Paragraph(texto_contrato, FontFactory.getFont(FontFactory.HELVETICA, 7f, BaseColor.GRAY));
            	    	contrato.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            	    	contrato.setLeading(9f);
            	    	
            	    	if (tab)
            	    		contrato.setFirstLineIndent(25f);
            	    	
            	    	doc.add(contrato);
            	    }
            	    texto_contrato = "";
            	}
            	
            	write.setOpenAction(new PdfAction(PdfAction.PRINTDIALOG));
            	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
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