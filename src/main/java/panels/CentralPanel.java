package panels;

import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.event.OMMouseMode;
import com.bbn.openmap.gui.BasicMapPanel;
import com.bbn.openmap.gui.OverlayMapPanel;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMPoint;
import com.bbn.openmap.proj.coords.LatLonPoint;
import models.Place;
import models.Sample;
import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CentralPanel extends JPanel {

    List<Sample> samples;
    BasicMapPanel mapPanel;
    OMGraphicHandlerLayer graphicHandlerLayer;

    public CentralPanel(){
        mapPanel = new BasicMapPanel();

        // olkhon island solids layer
        ShapeLayer shapeLayer = new ShapeLayer();

        Properties shapeLayerProps = new Properties();
        shapeLayerProps.put("prettyName", "Olkhon Solid");
        shapeLayerProps.put("lineColor", "000000");
        //shapeLayerProps.put("fillColor", "BDDE83");
        shapeLayerProps.put("shapeFile", "src/main/resources/map/island.shp");
        //shapeLayerProps.put("spatialIndex", "data/500m-join.ssx");
        shapeLayer.setProperties(shapeLayerProps);

        MapBean mapBean = mapPanel.getMapBean();
        mapBean.setCenter(new LatLonPoint.Double(53.09, 107.24));

        mapPanel.getMapBean().add(shapeLayer);

        mapBean.setScale(300000f);

        // samples dots
        graphicHandlerLayer = new OMGraphicHandlerLayer();

        mapPanel.addMapComponent(new LayerHandler());
        mapPanel.addMapComponent(new OMMouseMode());
        mapPanel.addMapComponent(new ToolPanel());

        mapPanel.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Controller.getInstance().updateCurrentSample(findNearestSample(mapPanel.getMapBean().getCoordinates(e).getX(), mapPanel.getMapBean().getCoordinates(e).getY()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
        });

        add(mapPanel);
    }

    public void update(){
        mapPanel.getMapBean().remove(graphicHandlerLayer);
        graphicHandlerLayer = new OMGraphicHandlerLayer();

        OMPoint point;
        OMGraphicList graphics = new OMGraphicList();

        for (int i = 0; i < samples.size(); i++){
            point = new OMPoint(samples.get(i).getLat(), samples.get(i).get_long());
            point.setFillPaint(Color.BLUE);
            graphics.add(point);
        }

        graphicHandlerLayer.setList(graphics);
        mapPanel.getMapBean().add(graphicHandlerLayer);
        mapPanel.revalidate();
        mapPanel.repaint();

    }

    /*
    public CentralPanel(int a){
        BasicMapPanel mapPanel = new OverlayMapPanel();

        ShapeLayer shapeLayer = new ShapeLayer();
        OMGraphicHandlerLayer layer = new OMGraphicHandlerLayer();

        OMPoint point = new OMPoint(53.09, 107.24);
        point.setFillPaint(Color.darkGray);
        OMGraphicList graphics = new OMGraphicList();
        graphics.add(point);
        layer.setList(graphics);

        Properties shapeLayerProps = new Properties();
        //shapeLayerProps.put("prettyName", "Political Solid");
        shapeLayerProps.put("lineColor", "000000");
        //shapeLayerProps.put("fillColor", "BDDE83");
        shapeLayerProps.put("shapeFile", "src/main/resources/map/island.shp");
        //shapeLayerProps.put("spatialIndex", "data/500m-join.ssx");
        shapeLayer.setProperties(shapeLayerProps);



        MapBean mapBean = mapPanel.getMapBean();
        mapBean.setCenter(new LatLonPoint.Double(53.09, 107.24));

        mapPanel.getMapBean().add(shapeLayer);


        mapBean.setScale(1200000f);


        mapPanel.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
        });


        mapPanel.addMapComponent(new LayerHandler());
        // Add MouseDelegator, which handles mouse modes (managing mouse
        // events via MouseModes)
        //mapPanel.addMapComponent(new MouseDelegator());
        // Add OMMouseMode, which handles how the map reacts to mouse
        // movements
        mapPanel.addMapComponent(new OMMouseMode());
        // Add a ToolPanel for widgets on the north side of the map.
        mapPanel.addMapComponent(new ToolPanel());
        // Add a LayersPanel, which lets you control layers
        //mapPanel.addMapComponent(new LayersPanel());


        this.add(mapPanel);
    }

    */

    private Sample findNearestSample(double lat, double _long){
        if (samples == null){
            return null;
        }
        double minDistance = 10000;
        double distance;
        int index = 0;
        for (int i = 0; i < samples.size(); i++){
        distance = samples.get(i).getDistanceTo(lat, _long);
        if (minDistance > distance){
            minDistance = distance;
            index = i;
            }
        }
        return samples.get(index);
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
