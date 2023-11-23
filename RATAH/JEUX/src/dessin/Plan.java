package dessin;


import java.util.List;
import java.util.Vector;

import centrale.Centrale;
import game.Civilisation;
import game.Entite;
import game.Hopital;
import ressource.Champ;

import java.io.*;

public class Plan implements Serializable{ 
    List<Champ> champs;
    List<Centrale> casernes;
    List<Entite> personnes;
    List<Civilisation> clans;
    List<Hopital> hopital;
    

    
    public Plan(List<Champ> champs, List<Centrale> casernes,List<Hopital> hopital, List<Entite> personnes, List<Civilisation> clans) {
		super();
		this.champs = champs;
		this.casernes = casernes;
		this.personnes = personnes;
		this.clans = clans;
		this.hopital=hopital;
	}
    
	public List<Champ> getChamps() {
		return champs;
	}
	
	public void setChamps(List<Champ> champs) {
		this.champs = champs;
	}
	
	public List<Centrale> getCasernes() {
		return casernes;
	}
	
	public void setCasernes(List<Centrale> casernes) {
		this.casernes = casernes;
	}
	public List<Entite> getPersonnes() {
		return personnes;
	}
	public void setPersonnes(List<Entite> personnes) {
		this.personnes = personnes;
	}
	public List<Civilisation> getClans() {
		return clans;
	}
	public void setClans(List<Civilisation> clans) {
		this.clans = clans;
	}
	
	public List<Hopital> getHopital() {
		return hopital;
	}
	
	public void setHopital(List<Hopital> hopital) {
		this.hopital = hopital;
	}	
}