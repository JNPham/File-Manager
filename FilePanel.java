package cecs277.file.manager;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultTreeModel;

public class FilePanel extends JPanel{
    File currentDir;
    File curFile;
    Desktop desktop = Desktop.getDesktop();
    JList list = new JList();
    DefaultListModel model = new DefaultListModel();
    FilePopup popup = new FilePopup();
    private JScrollPane scrollpane = new JScrollPane();
    public int size=460;
    
    public JList getList() {
        return list;
    }
    public File getCurDir() {
        return currentDir;
    }
    public File getcurFile() {
        return curFile;
    }
    
    public FilePanel(){
        list.setCellRenderer(new ListCellRenderer());
        list.setModel(model);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)  {check(e);}
            @Override
            public void mouseReleased(MouseEvent e) {check(e);}
            @Override
            public void mouseClicked(MouseEvent e) {
                File f;
                if (list.getSelectedValue() != null) {
                    curFile = new File((String)list.getSelectedValue()) ;
                    f = new File((String)list.getSelectedValue());
                    if (f.isFile())
                    if(e.getClickCount()==2 && e.getButton() == MouseEvent.BUTTON1){
                        try {
                            desktop.open(new File((String)list.getSelectedValue()));
                        } catch (IOException ex) {
                            System.out.println(ex.toString());
                        }
                }}
            }
        });
        
        list.setFont( new Font("monospaced", Font.PLAIN, 12) );
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
    }
  
//    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
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
    
    public void setCurrentDir(File f) {
        currentDir = f;
        System.out.println("Current Dir: " + currentDir.toString());
    }

    public void check(MouseEvent e) {
        if (e.isPopupTrigger()) { //if the event shows the menu
            list.setSelectedIndex(list.locationToIndex(e.getPoint())); //select the item
            popup.show(list, e.getX(), e.getY()); //and show the menu
            curFile = new File((String)list.getSelectedValue()) ;
        }
    }

    public class RenameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Dialog2Way rename = new Dialog2Way(null, true);
            rename.setTitle("Rename");
            rename.setCurrentDir(currentDir.toString());
            rename.setFromField(curFile.getName());
            rename.setVisible(true);  
            String toField = rename.getToField(); 
            System.out.println("Change name to: " + toField);
            Path source = Paths.get(curFile.getAbsolutePath());
            try {
                Files.move(source, source.resolveSibling(toField));
            } catch (IOException ex) {
                System.out.println("Fail to rename");
            }
            fillList(currentDir);
        }
    }

    public class CopyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Dialog2Way copy = new Dialog2Way(null, true);
            copy.setTitle("Copying");
            copy.setCurrentDir(currentDir.toString());
            copy.setFromField(curFile.getName());
            copy.setVisible(true);
            String toField = copy.getToField();
            Path source = Paths.get(curFile.getAbsolutePath());
            Path newDir = Paths.get(toField);
            try {
                Files.copy(source, newDir);
            } catch (IOException ex) {
                System.out.println("Unsuccessful Copying");
            }
            fillList(currentDir);
        }
    }

    public class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            JOptionPane op = new JOptionPane();
            String str = "Delete " + curFile.getAbsolutePath();
            int a = JOptionPane.showConfirmDialog(f, str, "Deleting!!!", JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.YES_OPTION) {
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.out.println("Delete sucessfully");
            try {
                Files.delete(Paths.get(curFile.getAbsolutePath()));
            } catch (IOException ex) {
                System.out.println("Delete Unsucessfully");
            }
            fillList(currentDir);
            }
        }
    }
    
    class FilePopup extends JPopupMenu {
        public FilePopup() {
            JMenuItem rename = new JMenuItem("Rename");
            rename.addActionListener(new RenameListener());
            JMenuItem copy = new JMenuItem("Copy");
            copy.addActionListener(new CopyListener());
            JMenuItem paste = new JMenuItem("Paste");
            JMenuItem delete = new JMenuItem("Delete");
            delete.addActionListener(new DeleteListener());

            add(rename);
            add(new JSeparator());
            add(copy);
            add(new JSeparator());
            add(paste);
            add(new JSeparator());
            add(delete);
       }
    }
    public void mousePressed(MouseEvent e)  {check(e);}
    public void mouseReleased(MouseEvent e) {check(e);}
    
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
