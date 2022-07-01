package panels;

import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeftPanel extends JPanel {

    JLabel headerLabel = new JLabel("Выбор параметров отображения");
    JLabel chooseElementLabel = new JLabel("Выберите элемент");
    JLabel moreThanLabel = new JLabel("Содержание больше чем");
    ArrayList<String> elements;
    String[] moreThanValues = new String[]{"Неважно","0.5", "1", "2", "5"};
    JComboBox elementsBox;
    JComboBox moreThanBox = new JComboBox(moreThanValues);
    JButton activateButton = new JButton("Искать");


    public LeftPanel(){
        initElements();
        elementsBox = new JComboBox(elements.toArray(new String[0]));
        this.setLayout(new GridLayout(6,1));
        this.add(headerLabel);
        this.add(chooseElementLabel);
        this.add(elementsBox);
        this.add(moreThanLabel);
        this.add(moreThanBox);
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().updateSamples();
            }
        });
        this.add(activateButton);
    }

    public JComboBox getElementsBox() {
        return elementsBox;
    }

    public void setElementsBox(JComboBox elementsBox) {
        this.elementsBox = elementsBox;
    }

    public JComboBox getMoreThanBox() {
        return moreThanBox;
    }

    public void setMoreThanBox(JComboBox moreThanBox) {
        this.moreThanBox = moreThanBox;
    }

    public void initElements(){
        elements = new ArrayList<>();
        elements.add("Все");
        try {
            File file = new File("src/main/resources/dictionary/elementsDictionary.csv");
            Scanner in = new Scanner(file);
            while(in.hasNextLine()){
                elements.add(in.nextLine().split(",")[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
