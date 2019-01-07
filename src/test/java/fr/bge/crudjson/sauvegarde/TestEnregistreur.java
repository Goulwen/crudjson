package fr.bge.crudjson.sauvegarde;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import fr.bge.crudjson.entites.Chaussure;
import fr.bge.crudjson.entites.Fabricant;
import fr.bge.crudjson.entites.Vetement;

public class TestEnregistreur {

	@Test
	public void testFonctionnementClassique() throws Exception {
		
		Enregistreur enregistreur = new Enregistreur();
		
		// On charge la liste actuelle des vêtements
		List <Vetement> dressing1 = enregistreur.lireTous();
		
		// On instancie un nouveau vêtement
		Chaussure chaussure = new Chaussure();
		chaussure.setTaille("42");
		chaussure.setCouleur("Rouge");
		chaussure.setLongueurLacet(25);
		
		Fabricant fabricant = new Fabricant();
		fabricant.setNom("Prada");
		chaussure.setFabricant(fabricant);
		
		// On sauvegarde ce vêtement
		enregistreur.enregistrer(chaussure);
		
		// On vérifie que la liste a pris en compte ce vêtement
		List <Vetement> dressing2 = enregistreur.lireTous();
		Assert.assertEquals("Le nouveau dressing a un élément de plus", dressing1.size() + 1, dressing2.size());
		
		// On modifie ce vêtement et on le sauvegarde
		chaussure.setTaille("38");
		enregistreur.enregistrer(chaussure);
		
		// vérifie que la liste a pris en compte cette modification
		// ?????
		
		// On supprime le vêtement
		enregistreur.supprimer(chaussure);
		
		// On vérifie que la liste a pris en compte cette suppression
		List <Vetement> dressing3 = enregistreur.lireTous();
		Assert.assertEquals("Les deux dressing doivent avoir le même nombre d'éléments", dressing1.size(), dressing3.size());
	}
}
