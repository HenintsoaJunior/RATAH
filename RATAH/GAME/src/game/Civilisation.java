package game;

import java.text.DecimalFormat;
import java.util.List;

public class Civilisation {
	
	private String nom;
    private int NbPopulation = 0;
    private int Nbmilitary = 0;
    private int Nbresearchers = 0;
    private double resources;
    private int terrainResource = 0;
    private int woodResource = 0;
    private int goldResource = 0;
    private List<Population> populations;
    private List<Militaire> militaires;
    private List<Chercheur> chercheurs;
    
    
    
    
    public List<Population> getPopulations() {
		return populations;
	}

	public void setPopulations(List<Population> populations) {
		this.populations = populations;
	}

	public List<Militaire> getMilitaires() {
		return militaires;
	}

	public void setMilitaires(List<Militaire> militaires) {
		this.militaires = militaires;
	}

	public List<Chercheur> getChercheurs() {
		return chercheurs;
	}

	public void setChercheurs(List<Chercheur> chercheurs) {
		this.chercheurs = chercheurs;
	}

	public Civilisation() {
        
    }
    
    public Civilisation(String nom) {
        this.nom = nom;
    }
    
    
    public void addResource(double amount) {
        resources += amount;
    }
    
    // Méthode pour afficher les informations de la civilisation
    public void displayCivilizationInfo() {
        System.out.println("Civilisation Info:");
        System.out.println("Population: " + NbPopulation);
        System.out.println("Military: " + Nbmilitary);
        System.out.println("Researchers: " + Nbresearchers);
        System.out.println("Resources: " + resources);
        System.out.println("Terrain Resource: " + terrainResource);
        System.out.println("Wood Resource: " + woodResource);
        System.out.println("Gold Resource: " + goldResource);
    }

    public Population createPopulation(int x, int y) {
        Population population = new Population(x, y, this);
        NbPopulation++;
        return population;
    }

    public Population createRandomPopulation() {
        int x = (int) (Math.random() * (800 - 20));
        int y = (int) (Math.random() * (600 - 20));
        Population population = createPopulation(x, y);
        return population;
    }


    public Militaire createMilitaire(int x, int y) {
        Militaire militaire = new Militaire(x, y, this);
        Nbmilitary++;
        return militaire;
    }

    public Chercheur createChercheur(int x, int y) {
        Chercheur chercheur = new Chercheur(x, y, this);
        Nbresearchers++;
        return chercheur;
    }

    public int getNbPopulation() {
        return NbPopulation;
    }

    public void setNbPopulation(int nbPopulation) {
        NbPopulation = nbPopulation;
    }

    public int getNbmilitary() {
        return Nbmilitary;
    }

    public void setNbmilitary(int nbmilitary) {
        Nbmilitary = nbmilitary;
    }

    public int getNbresearchers() {
        return Nbresearchers;
    }

    public void setNbresearchers(int nbresearchers) {
        Nbresearchers = nbresearchers;
    }

    public double getResources() {
        return resources;
    }


    public void setResources(double resources) {
        this.resources = resources;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTerrainResource() {
        return terrainResource;
    }

    public void setTerrainResource(int terrainResource) {
        this.terrainResource = terrainResource;
    }

    public int getWoodResource() {
        return woodResource;
    }

    public void setWoodResource(int woodResource) {
        this.woodResource = woodResource;
    }

    public int getGoldResource() {
        return goldResource;
    }

    public void setGoldResource(int goldResource) {
        this.goldResource = goldResource;
    }
}
