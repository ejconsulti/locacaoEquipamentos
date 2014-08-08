package ejconsulti.locacao.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import eso.utils.Log;

public abstract class Imprimir {
	public static final String TAG = Imprimir.class.getSimpleName();
	
	private Document doc;
	private String diretorio;
	private String nomeArquivo;
	
	/*
	 * A rotina de impressão deve extender esta classe.
	 * 
	 * O método 'documento()' será sobreescrito para 
	 * adicionar elementos utilizando o método 'add(Element)'
	 * 
	 * Para gerar o documento utilize o método 'imprimir()'
	 * 
	 */
	public Imprimir(String diretorio, String nomeArquivo) {
		setDiretorio(diretorio);
		setNomeArquivo(nomeArquivo);
	}
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getDiretorio() {
		return diretorio;
	}
	
	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
		File directory = new File(diretorio);
		if (!directory.exists())
			directory.mkdirs();
	}
	
	//Adicionar elemento ao documento
	public final boolean add(Element e) throws DocumentException {
		return getDoc().add(e);
	}
	
	//Abrir documento
	public final boolean abrir() {		
		boolean result = false;
		if (Desktop.isDesktopSupported()) {
		    try {
		        File file = new File(diretorio+nomeArquivo);
		        if (file.exists()) {
		        	Desktop.getDesktop().open(file);
		        	result = true;
		        }
		    } catch (IOException e) {
		        Log.e(TAG, "Erro ao abrir documento", e);
		    }
		}
		return result;
	}
	
	//Gerar documento
	public final boolean imprimir() {		
		boolean result = true;
		OutputStream os = null;
		try {
			//Inicializar documento
			os = new FileOutputStream(diretorio+nomeArquivo);
			PdfWriter.getInstance(getDoc(), os);
			getDoc().open();
			
			//Gerar documento
			documento();
			
		} catch (FileNotFoundException | DocumentException e) {
	        Log.e(TAG, "Erro ao criar documento", e);
			result = false;
		} finally {
			//Fechar documento
			
			getDoc().close();
			
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	//Adicionar elementos ao documento
	public abstract void documento() throws DocumentException;
	
	private Document getDoc() {
		if (doc == null)
			doc = new Document(PageSize.A4, 28, 28, 20, 14);
		return doc;
	}
	
	public final void setPageSize(Rectangle r) {
		getDoc().setPageSize(r);
	}
	
	public final void setMargins(float f1, float f2, float f3, float f4) {
		getDoc().setMargins(f1, f2, f3, f4);
	}
	
}