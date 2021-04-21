package cecs277.file.manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

class App extends JFrame {
    JPanel panel, topPanel;
    JButton simple, details;
    JMenuBar menubar;
    public App() {
        panel = new JPanel();
        topPanel = new JPanel();
        menubar = new JMenuBar();
        simple = new JButton("Simple");
        details = new JButton("Details");
    }
    public void go() {
        this.setTitle("CECS 277 File Manager");
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        buildMenu();
        //topPanel.add(details, BorderLayout.SOUTH);
        //panel.add(simple);
        //panel.add(topPanel, BorderLayout.NORTH);
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
        menubar.add(fileMenu);
        
        //tree: expand, collapse
        treeMenu = new JMenu("Tree");
        JMenuItem expand = new JMenuItem("Expand Branch");
        JMenuItem collapse = new JMenuItem("Collapse Branch");
        treeMenu.add(expand);
        treeMenu.add(collapse);
        menubar.add(treeMenu);
        
        //Window: New, Cascade
        winMenu = new JMenu("Window");
        JMenuItem newframe = new JMenuItem("New");
        JMenuItem cascade = new JMenuItem("Cascade");
        winMenu.add(newframe);
        winMenu.add(cascade);
        menubar.add(winMenu);
        
        //help: help, about
        helpMenu = new JMenu("Help");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new AboutActionListener());
        helpMenu.add(help);
        helpMenu.add(about);
        menubar.add(helpMenu);
        
        panel.add(menubar, BorderLayout.NORTH);
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
}
