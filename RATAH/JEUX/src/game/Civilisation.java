package game;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

public class Civilisation implements Serializable{
    String nameClan;
    double resource = 100;
    Color color;
    
    public Civilisation(){}
    public Civilisation(String nameClan){
        this.nameClan=nameClan;
    }
    public Civilisation(String nameClan,Color c){
        this.nameClan=nameClan;
        this.color=c;
    }
    public void setNameClan(String n){
        this.nameClan=n;
    }
    public String getNameClan(){
        return nameClan;
    }
    public void setResource(double d){
        this.resource=d;
    }
    public double getResource(){
        return resource;
    }
    public Color getColor(){
        return color;
    } 
    public void setColor(Color c){
        this.color=c;
    }
}