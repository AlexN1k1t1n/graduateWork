package panels;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    JPanel mainPanel;
    LeftPanel leftPanel;
    CentralPanel centralPanel;
    RightPanel rightPanel;

    public MainWindow(){
        setTitle("Sanyara-\"gis\"");
        init();
    }

    public JPanel createLeftPanel(){
        leftPanel = new LeftPanel();
        JPanel container = new JPanel();
        container.add(leftPanel);
        return container;
    }

    public JPanel createCentralPanel(){
        centralPanel = new CentralPanel();
        JPanel container = new JPanel();
        container.add(centralPanel);
        return container;
    }

    public JPanel createRightPanel(){
        rightPanel = new RightPanel();
        JPanel container = new JPanel();
        container.add(rightPanel);
        return container;
    }

    public void init(){
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1000,1000);

        JPanel panel = new JPanel(new GridLayout(1,3));
        panel.add(createLeftPanel());
        panel.add(createCentralPanel());
        panel.add(createRightPanel());

        mainPanel = panel;
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public LeftPanel getLeftPanel() {
        return leftPanel;
    }

    public CentralPanel getCentralPanel() {
        return centralPanel;
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }
}
