package fr.bge.crudjson.sauvegarde;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.bge.crudjson.entites.Chaussure;
import fr.bge.crudjson.entites.Chemise;
import fr.bge.crudjson.entites.Pantalon;
import fr.bge.crudjson.entites.Vetement;

public class Enregistreur {
	
	private final static String NOM_REPERTOIRE_STOCKAGE = "C:\\Users\\admin\\Downloads\\crudjson";
	private File repertoireStockage;
	
	public Enregistreur() {
		repertoireStockage = new File(NOM_REPERTOIRE_STOCKAGE);
		// On vérifie que le répertoire existe sinon on le créée
		if (!repertoireStockage.exists()) {
			repertoireStockage.mkdirs();
		}
	}

	public void enregistrer(Vetement vetement) throws Exception {
		
		String nomFichier = "";
		
		if (vetement instanceof Chaussure) {
			nomFichier = "Chaussure_";
		}
		else if (vetement instanceof Pantalon) {
			nomFichier = "Pantalon_";
		}
		else if (vetement instanceof Chemise) {
			nomFichier = "Chemise_";
		}
		
		if (vetement.getId() == null) {
			vetement.setId(UUID.randomUUID().toString());
		}
		
		// On créée le fichier json aléatoirement qui va stocker l'objet
		nomFichier += vetement.getId();
		File fichierJson = new File(repertoireStockage, nomFichier + ".json");
		
		// On convertit le java en json pour le stocker/l'écrire dans le fichier json
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(fichierJson, vetement);
	}
	
	public void supprimer(Vetement vetement) {
		if (vetement.getId() == null) {
			return;
		}
		else {
			File[] listFiles = repertoireStockage.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File fichier = listFiles[i];
				if (fichier.getName().contains(vetement.getId())) {
					fichier.delete();
				}
			}
		}
	}
	
	public List<Vetement> lireTous() throws Exception {
		List<Vetement> resultat = new ArrayList<Vetement>();
		
		// On parcourt les fichiers .json et on les convertit en java
		File[] fichiers = repertoireStockage.listFiles();
		for (int i = 0; i < fichiers.length; i++) {
			File fichier = fichiers[i];
			// Si fichier qui a un nom et finit par .json alors entre dans le if
			if (fichier.getName().endsWith(".json")) {
				ObjectMapper mapper = new ObjectMapper();
				if (fichier.getName().startsWith("Chaussure_")) {
					resultat.add(mapper.readValue(fichier, Chaussure.class));
				}
				else if (fichier.getName().startsWith("Pantalon_")) {
					resultat.add(mapper.readValue(fichier, Pantalon.class));
				}
				else if (fichier.getName().startsWith("Chemise_")) {
					resultat.add(mapper.readValue(fichier, Chemise.class));
				}
			}
		}
		
		return resultat;
	}
}
