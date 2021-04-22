package cecs277.file.manager;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class DirPanel extends JPanel{
    private JScrollPane scrollpane = new JScrollPane();
    private JTree dirTree = new JTree();
    private DefaultTreeModel treeModel;
    public int size=460;
    
    public DirPanel() {
        buildTree();
        dirTree.setPreferredSize(new Dimension(200,size + 100));
        scrollpane.setViewportView(dirTree);
        scrollpane.setPreferredSize(new Dimension(200,size));
        add(scrollpane);
        this.setPreferredSize(new Dimension(200,size));
    }
    
    private void buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(root);
        
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Node 1");
        root.add(node);
        node = new DefaultMutableTreeNode("Node 2");
        root.add(node);
        for (int i=0; i<20; i++) {
            MyFileNode myfilenode = new MyFileNode("SubNode" + i);
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(myfilenode);
            node.add(subNode);
        }
        dirTree.setModel(treeModel);
    }
}
