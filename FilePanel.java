package cecs277.file.manager;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

public class FilePanel extends JPanel{
    JList list = new JList();
    DefaultListModel model = new DefaultListModel();
    
    private JScrollPane scrollpane = new JScrollPane();
    public int size=460;

    public FilePanel(){
        list.setCellRenderer(new ListCellRenderer());
        list.setModel(model);
        scrollpane.setViewportView(list);
        scrollpane.setPreferredSize(new Dimension(375,size));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE));
        add(scrollpane);
        this.setDropTarget(new MyDropTarget());
        list.setDragEnabled(true);
        //this.setPreferredSize(new Dimension(200,size));
    }
  
    public void fillList(File dir) {
        File[] files;
        if (dir.listFiles() != null){
            files = dir.listFiles();
            model.clear();
            list.removeAll();
            for (int i=0; i<files.length; i++) {
                if (!files[i].isHidden() && files[i].isDirectory())
                    model.addElement(files[i].getAbsolutePath());
            }
            for (int i=0; i<files.length; i++) {
                if (!files[i].isHidden() && files[i].isFile())
                    model.addElement(files[i].getAbsolutePath());
            }
            list.setModel(model);
        }
        else
            model.clear();
    }
    
    class MyDropTarget extends DropTarget {
        public void drop(DropTargetDropEvent evt){
            try {
                //types of events accepted
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                //storage to hold the drop data for processing
                List result = new ArrayList();
                //what is being dropped? First, Strings are processed
                if(evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                    String temp = (String)evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    //String events are concatenated if more than one list item 
                    //selected in the source. The strings are separated by
                    //newline characters. Use split to break the string into
                    //individual file names and store in String[]
                    String[] next = temp.split("\\n");
                    //add the strings to the listmodel
                    for(int i=0; i<next.length;i++)
                        model.addElement(next[i]); 
                }
                else{ //then if not String, Files are assumed
                    result =(List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    //process the input
                    for(Object o : result){
                        System.out.println(o.toString());
                        model.addElement(o.toString());
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }
}
