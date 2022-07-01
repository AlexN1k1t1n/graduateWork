package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.SamplesDAO;
import models.Sample;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import panels.MainWindow;


public class Controller {

    private MainWindow mainWindow = new MainWindow();
    private SamplesDAO dao;
    private List<String> elements = new ArrayList<String>();
    private String currentElement;
    private String currentMoreThan;
    private Sample currentSample = new Sample("UGS-01", 53.09, 107.24, new ArrayList<>());
    private List<Sample> samples;
    private static Controller instance = new Controller();

    private Controller(){
        uploadData();
        dao = new SamplesDAO();
        initSamples();
        mainWindow.getCentralPanel().setSamples(samples);
    }

    public void initSamples(){
        samples = new ArrayList<>();
        samples.add(currentSample);
    }

    public static Controller getInstance() {
        return instance;
    }

    public void updateSamples(){
        //update sample data
        samples = dao.quury();
        mainWindow.getCentralPanel().setSamples(samples);
        mainWindow.getCentralPanel().update();
    }

    public void updateCurrentSample(Sample newSample){
        currentSample = newSample;
        mainWindow.getRightPanel().getInfoTextArea().setText(currentSample.toString());
    }

    public List<String> getElements() {
        return elements;
    }

    public Sample getCurrentSample() {
        return currentSample;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void uploadData(){
        String URL = "http://irnok.net:3030/api/export_graph?graph=pollution-database&mimetype=text%2Fplain&format=rdfxml";

        File file = new File("src/main/resources/db/db.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Model model = ModelFactory.createDefaultModel();

        InputStream in = RDFDataMgr.open(URL);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);
        model.write(out);
    }


}

