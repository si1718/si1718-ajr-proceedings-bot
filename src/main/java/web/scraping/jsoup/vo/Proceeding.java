package web.scraping.jsoup.vo;

import java.util.ArrayList;
import java.util.List;

public class Proceeding {
	private Editor editor;
	private String title, isbn, publisher, city, country, idProceeding;
	private Integer year;
	private List<String> coeditors;
	
	public Proceeding() {
		this.coeditors = new ArrayList<String>();
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public void updateEditor(String editor) {
		Editor aux = new Editor();
		aux.setName(editor);
		this.editor = aux;
	}

	public List<String> getCoeditors() {
		return coeditors;
	}

	public void setCoeditors(List<String> coeditors) {
		this.coeditors = coeditors;
	}

	public void addCoeditor(String coeditor) {
		this.coeditors.add(coeditor);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public String getIdProceeding() {
		return idProceeding;
	}

	public void setIdProceeding(String idProceeding) {
		this.idProceeding = idProceeding;
	}
	
	public void calculeIdProceeding() {
		if(this.idProceeding == null || this.idProceeding.isEmpty()) {
			if(this.isbn == null || this.isbn.isEmpty()) {
				if (this.title != null && this.editor != null && this.year != null)
				this.idProceeding = getCleanedString(title).substring(0,5) + 
			            getCleanedString(editor.getName()).substring(0,5) + year;
			} else {
				this.idProceeding = this.isbn;
			}
		}
	}

	public String toString() {
		return "Proceeding ("+idProceeding+") [editor=" + editor + ", coeditors=" + coeditors + ", title=" + title + ", year=" + year
				+ ", isbn=" + isbn + ", publisher=" + publisher + ", city=" + city + ", country=" + country + "]";
	}
	
	//https://www.relaxate.com/tutorial-javascript-limpiar-cadena-acentos-tildes-extranos
	private static String getCleanedString(String cadena){
	   // Definimos los caracteres que queremos eliminar
	   String specialChars = "!@#$^&%*()+=-[]\\/{}|:<>?,.";

	   // Los eliminamos todos
	   for (int i = 0; i < specialChars.length(); i++) {
	       cadena= cadena.replaceAll("\\" + specialChars.charAt(i), "");
	   }   

	   // Lo queremos devolver limpio en minusculas
	   cadena = cadena.toLowerCase();

	   // Quitamos espacios y los sustituimos por nada porque nos gusta mas asi
	   cadena = cadena.replaceAll(" ","");

	   // Quitamos acentos y "ρ". Fijate en que va sin comillas el primer parametro
	   cadena = cadena.replaceAll("[ΰαδ]","a");
	   cadena = cadena.replaceAll("[θιλ]","e");
	   cadena = cadena.replaceAll("[μνο]","i");
	   cadena = cadena.replaceAll("[ςσφ]","o");
	   cadena = cadena.replaceAll("[ωϊό]","u");
	   cadena = cadena.replaceAll("ρ","n");
	   return cadena;
	}
	
	
}
