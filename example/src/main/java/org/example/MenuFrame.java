package org.example;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuFrame extends JFrame {                

    private static final long serialVersionUID = 1L;
                        
    private JMenu menu;
    private JMenuItem menuItem;
    private JMenuBar menuBar;
    private JRadioButtonMenuItem rbMenuItem;
    private MyPanel panel = new MyPanel();
                                  
    @SuppressWarnings("unchecked")
    public MenuFrame()             
    {

        super("DateBase");
        this.setJMenuBar(menuBar); 
        this.setContentPane(panel);
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        

    } 
} 
