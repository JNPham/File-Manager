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
    Stack subFiles = new Stack();
    
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
        
        files = rootFile.listFiles();
        for (int i=0; i< files.length; i++){
            if (!files[i].isHidden() && files[i].isDirectory()) {
                MyFileNode mySubNode = new MyFileNode(files[i].getPath(), files[i]);
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(mySubNode);
                root.add(subNode);
                subFiles.push(files[i].getAbsoluteFile());
                while (subFiles.empty() == false) {
                    readDir((File)subFiles.pop(), subNode);
                }   
            }
        }
        
        dirTree.setModel(treeModel);
    }
    
    private void readDir(File f, DefaultMutableTreeNode sn) {
        File file = f;
        if (file.listFiles() != null) {
            newfiles = file.listFiles();
            for (int i=0; i< newfiles.length; i++){
                if (!newfiles[i].isHidden() && newfiles[i].isDirectory()) {
                    MyFileNode myTemp = new MyFileNode(newfiles[i].getPath(), newfiles[i]);
                    DefaultMutableTreeNode temp = new DefaultMutableTreeNode(myTemp);
                    sn.add(temp);
                }
            }
        }
    } 
    
    public class dirTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
            if (node == null)
                return;
            MyFileNode nodeContent = (MyFileNode) node.getUserObject();
            readDir(nodeContent.getFile(), node);
            System.out.println(dirTree.getMinSelectionRow());
            //updateFilePanel();
        }
    }
}
