package cecs277.file.manager;

import java.awt.Component;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;

public class ListCellRenderer extends DefaultListCellRenderer{
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {

        Component c = super.getListCellRendererComponent(list, value, index, selected, expanded);
        String str = (String) value;
        File f = new File(str);
        if (f instanceof File) {
            if (f.isDirectory()) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
                setText(f.getName());

            }
            else {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
                setText(String.format("%-50s %-20s %s byte", f.getName(), dateFormat.format(f.lastModified()), String.valueOf(f.length())));
            }
        }
        return this;
    }
}
