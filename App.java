package cecs277.file.manager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JTree;

class App extends JFrame {
    JPanel panel, topPanel;
    JButton simple, details;
    JMenuBar menubar;
    JToolBar toolbar, statusbar;
    static JDesktopPane desktop;
    FileFrame ff;
    Font font;
    static JComboBox driveSel;
    static String currentDrive;
    JLabel drive, freespace, usedspace,totalspace;
    public App() {
        panel = new JPanel();
        topPanel = new JPanel();
        menubar = new JMenuBar();
        toolbar = new JToolBar();
        statusbar = new JToolBar();
        desktop = new JDesktopPane();
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
        buildStatusbar(currentDrive);
        panel.add(statusbar, BorderLayout.SOUTH);
        
        panel.add(desktop, BorderLayout.CENTER);
        ff = new FileFrame();
        desktop.add(ff);
        
        
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
        expand.addActionListener(new ExpandCollapseListener());
        collapse.addActionListener(new ExpandCollapseListener());
        treeMenu.add(expand);
        treeMenu.add(collapse);
        treeMenu.setFont(font);
        menubar.add(treeMenu);
        
        winMenu = new JMenu("Window");
        JMenuItem newframe = new JMenuItem("New");
        JMenuItem cascade = new JMenuItem("Cascade");
        newframe.addActionListener(new NewActionListener());
        cascade.addActionListener(new CascadeActionListener());
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

    private static class ExpandCollapseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileFrame selectedFrame = (FileFrame) desktop.getSelectedFrame();
            if (selectedFrame == null)
                return;
            JTree tree = selectedFrame.dirpanel.getDirTree();
            int row = tree.getMinSelectionRow();
            if (e.getActionCommand().equals("Expand Branch") && tree.isCollapsed(row))
                tree.expandRow(row);
            if (e.getActionCommand().equals("Collapse Branch") && tree.isExpanded(row))
                tree.collapseRow(row);
        }
    }

    private class NewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            desktop.add(new FileFrame());
        }
    }

    private class driSelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            currentDrive = driveSel.getSelectedItem().toString().substring(0, 3);
            FileFrame selectedFrame = (FileFrame) desktop.getSelectedFrame();
            if (selectedFrame == null)
                return;
            currentDrive += "\\";
            selectedFrame.dirpanel.setRootFile(currentDrive);
            selectedFrame.dirpanel.setTree();
            buildStatusbar(currentDrive);
        }
    }

    private class CascadeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int x = 0;
            int y = 0;
            JInternalFrame[] frames = desktop.getAllFrames();
            int count = frames.length;
            x = 27 * (count-1);
            y = 27 * (count-1);
            for (JInternalFrame fr: frames) {
                fr.setLocation(x, y);
                x -= 27;
                y -= 27;
                fr.requestFocusInWindow();
            }
        }

    }

    private class AboutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AboutDlg dlg = new AboutDlg(null, true);
            dlg.setTitle("About");
            dlg.setVisible(true);
        }
    }

    private class ExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    File[] paths = File.listRoots();
    private void buildToolbar(){
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());
        simple = new JButton("Simple"); //add action 
        details = new JButton("Details"); //add action
        
        //add combo box for drive selection
        driveSel = new JComboBox();
        driveSel.setFont(font);
        driveSel.setPrototypeDisplayValue("text here           ");
        driveSel.addActionListener(new driSelActionListener());
        for (File path : paths) 
            driveSel.addItem(path.toString());   
        simple.setFont(font);
        details.setFont(font);
        toolPanel.add(driveSel);
        toolPanel.add(simple);
        toolPanel.add(details);
        toolbar.add(toolPanel);
        toolbar.setFloatable(false);
        
    }
    
    private void buildStatusbar(String currentDrive) {
        File f = new File(currentDrive);
        drive = new JLabel("Current Drive: " + currentDrive);
        freespace = new JLabel("        Free Space: " + String.valueOf(f.getFreeSpace()/(1024*1024*1024)) + "GB");
        usedspace = new JLabel("        Used Space: " + String.valueOf((f.getTotalSpace() - f.getFreeSpace())/1073741824) + "GB");
        totalspace = new JLabel("       Total Space: " + String.valueOf(f.getTotalSpace()/(1024*1024*1024)) + "GB");
        
        statusbar.add(drive);
        statusbar.add(freespace);
        statusbar.add(usedspace);
        statusbar.add(totalspace);
        statusbar.setFloatable(false);
    }
}
