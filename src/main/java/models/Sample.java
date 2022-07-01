package models;

import models.Element;
import util.Controller;

import java.util.List;

public class Sample {

    private String label;
    private double lat;
    private double _long;
    private List<Element> elements;

    public Sample(){}

    public Sample(String label, double lat, double _long, List<Element> elements) {
        this.label = label;
        this.lat = lat;
        this._long = _long;
        this.elements = elements;
    }

    public String toString(){
        return label + "\n lat - " + lat + "\n long -" + _long;
    }

    public double getDistanceTo(double x, double y){
        return Math.sqrt( Math.pow((lat-x),2) + Math.pow((_long-y),2) );
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double get_long() {
        return _long;
    }

    public void set_long(double _long) {
        this._long = _long;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
