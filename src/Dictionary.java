import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Dictionary extends JFrame implements ActionListener { //JFrame front end olusturmaya yardimci

    JPanel panelTR; //like frame, we can direct
    JPanel panelEN;
    JPanel panel;

    JLabel labelTR;//in order to add text or image
    JLabel labelEN;//in order to add text or image
    JTextField textTR; //turkish word
    JTextField textEN;//english word
    JButton addButton; //button to add word to dictionary
    JButton deleteButton;

    Map<String, String> words;//insertion order
    PrintWriter pw_TR; //create file to store words
    PrintWriter pw_EN; //create file to store words

    Scanner tr;
    Scanner en;

   ArrayList<String> trList;
    ArrayList<String> enList;


    public Dictionary() {//create frame
        this.setTitle("Dictionary");//Title
        this.setLocation(600, 300); //frame location on screen

        tRBuildPanel();
        eNBuildPanel();
        buildPanel();
        addToMap();

        words = new HashMap<>();

        add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //system exit
        // this.setDefaultCloseOperation(3); //system exit
        // this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //prevents to system exit
        this.setSize(500, 500);//pixel = height & wiegth
        this.setVisible(true);//it shows set frame ,it should be at the bottom

    }

    private void addToMap() {


        try {
            tr = new Scanner(new File("tr_words"));
            en = new Scanner(new File("en_words"));
             trList = new ArrayList<>();
             enList = new ArrayList<>();

            //read file

            while (tr.hasNext()){//dosyayi oku
                trList.add(tr.next());
            }
            while (en.hasNext()){//dosyayi oku
                enList.add(en.next());
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            tr.close();
            en.close();
        }

    }

    private void buildMap() {

        try {
            pw_TR = new PrintWriter("tr_words");
            pw_EN = new PrintWriter("en_words");

            for (int i = 0; i < trList.size(); i++) {
                words.put( trList.get(i) , enList.get(i) );
            }

            for (Map.Entry<String, String> eachWord : words.entrySet()) {
                System.out.println(eachWord.getKey() + " : " + eachWord.getValue());

                pw_TR.println(eachWord.getKey());
                pw_EN.println(eachWord.getValue());

            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    private void buildPanel() {
        panel = new JPanel();
        //ImageIcon icon = new ImageIcon("Pikachu.png");

        addButton = new JButton("Add");
        //addButton.setIcon(icon);
        addButton.setBackground(Color.CYAN);
        addButton.setFocusable(false);
        addButton.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
        addButton.addActionListener(this);//make clickable
        addButton.setBorder(BorderFactory.createEtchedBorder(5,Color.CYAN,Color.BLUE));


        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.PINK);
        deleteButton.setFocusable(false);
        deleteButton.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));

        deleteButton.setBorder(BorderFactory.createEtchedBorder(5,Color.PINK,Color.RED));


        panel.setLayout(null);
        addButton.setBounds(150,50,100,50);
        deleteButton.setBounds(275,50,100,50);
        panelTR.setBounds(150,125,100,100);
        panelEN.setBounds(275,125,100,100);
        panel.add(panelTR);
        panel.add(panelEN);
        panel.add(addButton);
        panel.add(deleteButton);
    }

    private void eNBuildPanel() {
        panelEN = new JPanel();
        labelEN = new JLabel("en");
        textEN = new JTextField(10);

        panelEN.setLayout(new GridLayout(2,1));
        panelEN.add(labelEN);
        panelEN.add(textEN);
    }

    private void tRBuildPanel() {

        panelTR = new JPanel();
        labelTR = new JLabel("tr");
        textTR = new JTextField(10);

        panelTR.setLayout(new GridLayout(2,1));
        panelTR.add(labelTR);
        panelTR.add(textTR);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //  words = new HashMap<>();//random order
        if (e.getSource() == addButton) {

            words.put(textTR.getText(),textEN.getText());
            System.out.println("added");

            buildMap();

            textEN.setText("");
            textTR.setText("");
            pw_EN.close();
            pw_TR.close();
        }

    }
}
