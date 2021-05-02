package cecs277.file.manager;

import java.awt.Dimension;
import java.io.File;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class DirPanel extends JPanel{
    private JScrollPane scrollpane = new JScrollPane();
    private JTree dirTree = new JTree();
    private DefaultTreeModel treeModel;
    public int height = 460;
    public int width = 295;
    File[] files, newfiles;
    File rootFile;
    
    //FileFrame myFrame;
    FilePanel filePanel;
    public void setRootFile(String rf) {
        rootFile = new File(rf);
    }
    public String getRootFile() {
        return rootFile.getPath();
    }
    public void setFilePanel(FilePanel fp) {
        filePanel = fp;
    }
    public JTree getDirTree() {
        return dirTree;
    }
    
    public DirPanel() {
        dirTree.setCellRenderer(new MyTreeCellRenderer());
        dirTree.addTreeSelectionListener(new dirTreeSelectionListener());
        buildTree();
        dirTree.setScrollsOnExpand(true);
        dirTree.setVisibleRowCount(100);
        scrollpane.setViewportView(dirTree);
        scrollpane.setPreferredSize(new Dimension(width,height));
        add(scrollpane);
        this.setPreferredSize(new Dimension(width,height));
    }
    
    private void buildTree() {
        rootFile = new File("C:\\");
        MyFileNode node = new MyFileNode(rootFile.getPath(), rootFile);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(node);
        treeModel = new DefaultTreeModel(root);
        readDir2(rootFile, root);
        dirTree.setModel(treeModel);
    }
    
    private void readDir(File f, DefaultMutableTreeNode sn) {
        if (f.listFiles() != null) {
            newfiles = f.listFiles();
            for (int i=0; i< newfiles.length; i++){
                if (!newfiles[i].isHidden() && newfiles[i].isDirectory()) {
                    MyFileNode myTemp = new MyFileNode(newfiles[i].getName(), newfiles[i]);
                    DefaultMutableTreeNode temp = new DefaultMutableTreeNode(myTemp);
                    sn.add(temp);   
                }
            }
        }
    } 
    private void readDir2(File f, DefaultMutableTreeNode n) {
        DefaultMutableTreeNode temp = null;
        if (f.listFiles() != null) {
            files = f.listFiles();
            for (int i=0; i< files.length; i++){
                if (!files[i].isHidden() && files[i].isDirectory()) {
                    MyFileNode mySubNode = new MyFileNode(files[i].getName(), files[i]);
                    temp = new DefaultMutableTreeNode(mySubNode);
                    readDir(files[i], temp);
                    n.add(temp);
                }
            }
        }
    }
    
    private void updateTree(DefaultMutableTreeNode node) {
        System.out.println("Child count: " + node.getChildCount());
        node.removeAllChildren();
        MyFileNode nodeContent = (MyFileNode) node.getUserObject();
        readDir2(nodeContent.getFile(), node);
    }
    
    public class dirTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
            if (node == null)
                return;
            System.out.println(dirTree.getMinSelectionRow());
            updateTree(node);
            //updateFilePanel();
        }
    }
}
