package al.ali.blast;

import java.util.ArrayList;

public class ProxyDatabaseHelper {
	static int eleven = 0;
	public static ArrayList<String> proxyDatabaseName(int time){
		ArrayList<String> lineage_name = new ArrayList<String>();
		
		if (time == 1){
			
        	String[] eee = {"Vavraia culicis 'floridensis'", "Vavraia culicis", "Vavraia", "Pleistophoridae", "Pansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
	
		}
		else if (time == 2){
			String[] eee = {"Encephalitozoon cuniculi GB-M1", "Encephalitozoon cuniculi", "Encephalitozoon", "Unikaryonidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}
		else if (time == 3){
			String[] eee = {"Encephalitozoon cuniculi GB-M1", "Encephalitozoon cuniculi", "Encephalitozoon", "Unikaryonidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}	
		}
		else if (time == 4){
			String[] eee = {"Encephalitozoon hellem ATCC 50504", "Encephalitozoon hellem", "Encephalitozoon", "Unikaryonidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}
		else if (time == 5){
			String[] eee = {"Nosema bombycis CQ1", "Nosema bombycis", "Nosema", "Nosematidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}
		else if (time == 6){
			String[] eee = {"Vavraia culicis 'floridensis'", "Vavraia culicis", "Vavraia", "Pleistophoridae", "Pansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}
		else if (time == 7){
			String[] eee = {"Encephalitozoon intestinalis ATCC 50506", "Encephalitozoon intestinalis", "Encephalitozoon", "Unikaryonidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}
		else if (time == 8){
			String[] eee = {"Edhazardia aedis USNM 41457", "Edhazardia aedis", "Edhazardia", "Culicosporidae", "Microsporidia incertae sedis", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}		
		}
		else if (time == 9){
			System.out.print("yani che");
		}
		else if (time == 10){
			String[] eee = {"Pseudomonas sp. CF150", "Pseudomonas", "Pseudomonadaceae", "Pseudomonadales", "Gammaproteobacteria", "Proteobacteria", "Bacteria", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}		
		else if (time == 11){
			if (eleven >= 2){
				String[] eee = {"Planctomyces brasiliensis DSM 5305", "Planctomyces brasiliensis", "Planctomyces", "Planctomycetaceae", "Planctomycetales", "Planctomycetia", "Planctomycetes", "Bacteria", "cellular organisms"};
	        	for(int ii=0;ii<eee.length; ii++){
	        		lineage_name.add(eee[ii]);
	        	}
				
			}
			else if(eleven <= 1){
				String[] eee = {"Prunus persica", "Prunus", "Amygdaleae", "Maloideae", "Rosaceae", "Rosales", "fabids", "rosids", "core eudicotyledons", "eudicotyledons", "Magnoliophyta", "Spermatophyta", "Euphyllophyta", "Tracheophyta", "Embryophyta", "Streptophytina", "Streptophyta", "Viridiplantae", "Eukaryota", "cellular organisms"};
	        	for(int ii=0;ii<eee.length; ii++){
	        		lineage_name.add(eee[ii]);
	        	}
			}
			
		}
		else if (time == 12){
			String[] eee = {"Encephalitozoon cuniculi", "Encephalitozoon", "Unikaryonidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}
		}
		else if (time == 13){
			String[] eee = {"Encephalitozoon intestinalis ATCC 50506", "Encephalitozoon intestinalis", "Encephalitozoon", "Unikaryonidae", "Apansporoblastina", "Microsporidia", "Fungi", "Opisthokonta", "Eukaryota", "cellular organisms"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_name.add(eee[ii]);
        	}	
		}
		
		return lineage_name;
		
	}
	public static ArrayList<String> proxyDatabaseTaxID(int time){
		
		ArrayList<String> lineage_taxid = new ArrayList<String>();
		
		if (time == 1){
			
        	String[] eee = {"948595", "103449", "35235", "35232", "6036", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
	
		}
		else if (time == 2){
			String[] eee = {"284813", "6035", "6033", "36734", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}
		else if (time == 3){
			String[] eee = {"284813", "6035", "6033", "36734", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}	
		}
		else if (time == 4){
			String[] eee = {"907965", "27973", "6033", "36734", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}
		else if (time == 5){
			String[] eee = {"578461", "27978", "27977", "27974", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}
		else if (time == 6){
			String[] eee = {"948595", "103449", "35235", "35232", "6036", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}
		else if (time == 7){
			String[] eee = {"876142", "58839", "6033", "36734", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}
		else if (time == 8){
			String[] eee = {"1003232", "70536", "70535", "322152", "469895", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}		
		}
		else if (time == 9){
			System.out.print("yani che");
		}
		else if (time == 10){
			String[] eee = {"911240", "286", "135621", "72274", "1236", "1224", "2", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}		
		else if (time == 11){
			if (eleven >= 2){
				String[] eee = {"756272", "119", "118", "126", "112", "203683", "203682", "2", "131567"};
	        	for(int ii=0;ii<eee.length; ii++){
	        		lineage_taxid.add(eee[ii]);
	        	}
				
			}
			else if(eleven <= 1){
				String[] eee = {"3760", "3754", "721805", "171637", "3745", "3744", "91835", "71275", "91827", "71240", "3398", "58024", "78536", "58023", "3193", "131221", "35493", "33090", "2759", "131567"};
	        	for(int ii=0;ii<eee.length; ii++){
	        		lineage_taxid.add(eee[ii]);
	        	}
			}
						
			
			
			eleven++;
		}
		else if (time == 12){
			String[] eee = {"6035", "6033", "36734", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}
		}
		else if (time == 13){
			String[] eee = {"876142", "58839", "6033", "36734", "6032", "6029", "4751", "33154", "2759", "131567"};
        	for(int ii=0;ii<eee.length; ii++){
        		lineage_taxid.add(eee[ii]);
        	}	
		}
		
		return lineage_taxid;
	
		
	}
	

}
