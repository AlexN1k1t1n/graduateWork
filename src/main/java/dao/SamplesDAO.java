package dao;

import models.Element;
import models.Sample;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SamplesDAO {

    private final String PATH = "src/main/resources/db/db.txt";

    public SamplesDAO(){}

    public List<Sample> getSamples(String currentElement, String currentMoreThan){
        List<Sample> samples = new ArrayList<>();

        Model model = ModelFactory.createDefaultModel();

        InputStream in = RDFDataMgr.open(PATH);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);
        model.write(System.out);

        return samples;
    }

    public List<Sample> quury(){
        List<Sample> samples = new ArrayList<>();
        //create a Jena model to query against
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open(PATH);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);

        //create a node for our variable
        //RDFNode blog = model.createResource("http://journal.dajobe.org");
        //store the node in a QuerySolutionMap
        QuerySolutionMap initialBindings = new QuerySolutionMap();
        //initialBindings.add("blog", blog);
        //here's the query
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                        "PREFIX http: <http://www.w3.org/2011/http#>" +
                        "PREFIX dbr: <http://dbpedia.org/resource/>" +
                        "PREFIX ns2: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
                        "SELECT * WHERE {" +
                        "  ?sub a dbr:Sample_\\(material\\) ." +
                        "  ?sub rdfs:label ?name ." +
                        "  ?sub ns2:lat ?lat ." +
                        "  ?sub ns2:long ?long ." +
                        "} ";
        //create the query object
        Query query = QueryFactory.create(queryString);
        //execute the query over the model, providing the
        //initial bindings for all variables
        QueryExecution exec = QueryExecutionFactory.create(query, model, initialBindings);
        ResultSet results = exec.execSelect();
        int count = 0;
        QuerySolution solution;
        while (results.hasNext()){
            count++;
            solution = results.next();
            samples.add(new Sample(solution.getLiteral("name").toString(),
                                   solution.getLiteral("lat").getFloat(),
                                   solution.getLiteral("long").getFloat(),
                                   new ArrayList<Element>() ));
            System.out.print(solution.getLiteral("name") + " ");
            System.out.print(solution.getLiteral("lat") + " ");
            System.out.println(solution.getLiteral("long"));
        }
        //now format the results..
        // .
        return samples;
    }

}


/*
public List<Sample> updateData(String query){
        samples = new ArrayList<>();

//        OntModel ontModel = ModelFactory.createOntologyModel();
//        ontModel.read(URL);
//        System.out.println((ontModel.getGraph().toString()));

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open(URL);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + URL + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

        //model = model.query(new SimpleSelector(subject, predicate, object));

        // write it to standard out
        //model.write(System.out);

        StmtIterator iter = model.listStatements();
        int count = 0;
        try {
            while ( iter.hasNext() && count<10 ) {
                Statement stmt = iter.next();

                Resource s = stmt.getSubject();
                Resource p = stmt.getPredicate();
                RDFNode o = stmt.getObject();

                if ( s.isURIResource() ) {
                    System.out.print(s.getURI());
                } else if ( s.isAnon() ) {
                    System.out.print("blank");
                }

                if ( p.isURIResource() )
                    System.out.print(p.getURI());

                if ( o.isURIResource() ) {
                    System.out.print(p.getURI());
                } else if ( o.isAnon() ) {
                    System.out.print("blank");
                } else if ( o.isLiteral() ) {
                    System.out.print(p.getProperty(null,"Literal"));
                }

                System.out.println();
            }
        } finally {
            if ( iter != null ) iter.close();
        }

        //create query
        return null;
    }
 */