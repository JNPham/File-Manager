package cecs277.file.manager;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

public class FileFrame extends JInternalFrame {
    JSplitPane splitpane;
    public FileFrame() {
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new DirPanel(), new FilePanel());
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
