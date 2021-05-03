package cecs277.file.manager;

import java.awt.Component;
import java.io.File;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;

public class ListCellRenderer extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {

        Component c = super.getListCellRendererComponent(list, value, index, selected, expanded);

        File file = (File) value;
        if (file instanceof File) {
            if (file.isDirectory()) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
            }
            else {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
            }
            setText(file.getAbsolutePath());
        }
        return this;
    }
}
