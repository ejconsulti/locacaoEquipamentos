package ejconsulti.locacao.models;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import eso.utils.Text;

@SuppressWarnings("serial")
public class DataField extends JFormattedTextField implements DocumentListener{
	public DataField (){
		initialize();
	}
	
	private void initialize(){
		new JFormattedTextField(Text.buildMask("##/##/####"));
		this.setColumns(7);
	}
	
	public String getData(){
		return getText();
	}
	
	public String getDataAtual(){
		Calendar dataAtual = Calendar.getInstance();
		String dia = new DecimalFormat("00").format(dataAtual.get(Calendar.DATE)).toString();
		String mes = new DecimalFormat("00").format(dataAtual.get(Calendar.MONTH) + 1).toString();
		String ano =  "" + dataAtual.get(Calendar.YEAR);
		String data = dia + "/" + mes + "/" + ano;
		
		return data;
	}
	
	public boolean validarData (String texto){
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");  
		sdf.setLenient(false);  
		try {  
			sdf.parse(texto);  
			return true;  
		}  
		catch (ParseException ex) {  
			return false; 
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		String texto = this.getText().replaceAll("/", "").replaceAll("_", "");
		if (texto.length() == 8 && !validarData(texto)){			
			Runnable doHighlight = new Runnable() {
		        @Override
		        public void run() {
		        	setText(getDataAtual());
		        }
		    };       
		    SwingUtilities.invokeLater(doHighlight);
			
			//System.out.println(dia + "/" + mes + "/" + ano);
		}
	}
	@Override
	public void removeUpdate(DocumentEvent e) {}
	@Override
	public void changedUpdate(DocumentEvent e) {}
}
