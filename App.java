package cecs277.file.manager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

class App extends JFrame {
    JPanel panel, topPanel;
    JButton simple, details;
    JMenuBar menubar;
    JToolBar toolbar, statusbar;
    Font font;
    JComboBox driveSel;
    String currentDrive;
    JLabel drive, freespace, usedspace,totalspace;
    public App() {
        panel = new JPanel();
        topPanel = new JPanel();
        menubar = new JMenuBar();
        toolbar = new JToolBar();
        statusbar = new JToolBar();
        font = new Font("SansSerif", Font.BOLD, 15);
    }
    public void go() {
        this.setTitle("CECS 277 File Manager");
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        
        buildMenu();
        topPanel.add(menubar, BorderLayout.NORTH);
        
        buildToolbar();
        topPanel.add(toolbar, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        
        currentDrive = "C:\\";
        buildStatusbar();
        panel.add(statusbar, BorderLayout.SOUTH);
        
        this.add(panel);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void buildMenu(){
        JMenu fileMenu, treeMenu, winMenu, helpMenu;
        //file: rename, copy, delete, run, exit
        fileMenu = new JMenu("File");
        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem run = new JMenuItem("Run");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitActionListener());
        fileMenu.add(rename);
        fileMenu.add(copy);
        fileMenu.add(delete);
        fileMenu.add(run);
        fileMenu.add(exit);
        fileMenu.setFont(font);
        menubar.add(fileMenu);
        
        //tree: expand, collapse
        treeMenu = new JMenu("Tree");
        JMenuItem expand = new JMenuItem("Expand Branch");
        JMenuItem collapse = new JMenuItem("Collapse Branch");
        treeMenu.add(expand);
        treeMenu.add(collapse);
        treeMenu.setFont(font);
        menubar.add(treeMenu);
        
        //Window: New, Cascade
        winMenu = new JMenu("Window");
        JMenuItem newframe = new JMenuItem("New");
        JMenuItem cascade = new JMenuItem("Cascade");
        winMenu.add(newframe);
        winMenu.add(cascade);
        winMenu.setFont(font);
        menubar.add(winMenu);
        
        //help: help, about
        helpMenu = new JMenu("Help");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new AboutActionListener());
        helpMenu.add(help);
        helpMenu.add(about);
        helpMenu.setFont(font);
        menubar.add(helpMenu);
    }

    private class AboutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AboutDlg dlg = new AboutDlg(null, true);
            dlg.setVisible(true);
        }
    }

    private class ExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    private void buildToolbar(){
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());
        simple = new JButton("Simple"); //add action 
        details = new JButton("Details"); //add action
        
        //add combo box for drive selection
        driveSel = new JComboBox();
        driveSel.setFont(font);
        driveSel.addItem("C:\\");
        driveSel.addItem("Other drive"); //add action
        
        simple.setFont(font);
        details.setFont(font);
        toolPanel.add(driveSel);
        toolPanel.add(simple);
        toolPanel.add(details);
        toolbar.add(toolPanel);
        toolbar.setFloatable(false);
        
        
    }
    
    private void buildStatusbar() {
        drive = new JLabel("Current Drive: " + currentDrive);
        freespace = new JLabel("        Free Space: " + "......" + "GB");
        usedspace = new JLabel("        Used Space: " + "......" + "GB");
        totalspace = new JLabel("       Total Space: " + "......" + "GB");
        //Note: replace "......." with file space
        
        statusbar.add(drive);
        statusbar.add(freespace);
        statusbar.add(usedspace);
        statusbar.add(totalspace);
        statusbar.setFloatable(false);
    }
}
