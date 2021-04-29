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
    JList list = new JList();
    DefaultListModel model = new DefaultListModel();
    
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
        list.setPreferredSize(new Dimension(500,500));
        this.setDropTarget(new MyDropTarget());
        list.setDragEnabled(true);

        list.setModel(model);
        add(list);
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
