package cecs277.file.manager;


import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

public class FilePanel extends JPanel{
    
    private JScrollPane scrollpane = new JScrollPane();
    private JTree dirTree = new JTree();
    
    private DefaultTreeModel treeModel;
    public int size=465;
    
    public FilePanel(){
        add(scrollpane);
        dirTree.setPreferredSize(new Dimension(500,size + 100));
        scrollpane.setViewportView(dirTree);
        scrollpane.setPreferredSize(new Dimension(480,size));
        add(scrollpane);
        this.setPreferredSize(new Dimension(200,size));
    }
  
}
