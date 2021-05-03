package cecs277.file.manager;

import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

public class FileFrame extends JInternalFrame {
    JSplitPane splitpane;
    DirPanel dirpanel = new DirPanel();
    FilePanel filepanel = new FilePanel();
    Dimension miniD = new Dimension(250,460);
    public FileFrame() {
        dirpanel.setMinimumSize(miniD);
        filepanel.setMinimumSize(miniD);
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, dirpanel, filepanel);
        splitpane.setDividerLocation(250);
        splitpane.setResizeWeight(0.3);
        this.setTitle("C:");
        
        this.getContentPane().add(splitpane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setResizable(true);
        this.setSize(700, 500);
        this.setVisible(true);
    }
}
