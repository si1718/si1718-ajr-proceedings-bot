package web.scraping.jsoup;

import java.util.Arrays;
import java.util.List;

import web.scraping.jsoup.vo.Proceeding;

public class ProceedingUtils {
	
	public static Proceeding createProceeding(String editors, String data) {
		Proceeding proceeding = new Proceeding();
		List<String> splitted_editors = Arrays.asList(editors.split("\\(.*?\\)[,:]"));
		
		for(int i=0; i<splitted_editors.size(); i++) {
			if(i==0) {
				proceeding.setEditor(splitted_editors.get(i).trim());
			} else{
				proceeding.addCoeditor(splitted_editors.get(i).trim());
			}
		}
		
		
		//Match a comma only if it's not followed by any number of characters except opening parens followed by a closing parens
		List<String> splitted_data = Arrays.asList(data.split("\\.(?![^(]*\\))"));
		
		for(int j=0; j<splitted_data.size(); j++) {
			String data_j = splitted_data.get(j).trim();
			if(j==0 || (j==1 && !data_j.matches("\\d+"))) {
				if(j==0) {
					proceeding.setTitle(data_j);
				} else {
					proceeding.setTitle(proceeding.getTitle() + ". " + data_j);
				}
			} else if(j==1 || (j==2 && data_j.matches("\\d{4}"))) {
				proceeding.setYear(new Integer(data_j));
			} else {
				if(data_j.matches("ISBN(.*?)")) {			
					proceeding.setIsbn(reformatIsbn(data_j));
				} else if(!data_j.matches("\\d+") && j==splitted_data.size() - 2) {
					proceeding.setPublisher(data_j);				
				} else if(j==splitted_data.size() - 1) {
					List<String> splitted_locate = Arrays.asList(data_j.split("[,\\((.*?)\\)]"));
					if(splitted_locate.size()==2) {
						proceeding.setCity(splitted_locate.get(0).trim());
						proceeding.setCountry(splitted_locate.get(1).trim());
					} else {
						proceeding.setCity(data_j);					
					}
				}
			}
		}
		
		proceeding.calculeIdProceeding();
		return proceeding;
	}
	
	public static String reformatIsbn(String isbn) {
		return isbn.replaceAll("[ISBN -]", "");
	}
}
