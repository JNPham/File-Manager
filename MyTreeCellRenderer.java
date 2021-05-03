
package cecs277.file.manager;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer { 
    @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (node.getUserObject() instanceof MyFileNode) {
                MyFileNode n = (MyFileNode)  node.getUserObject();
                    if (n.isDir()) {
                        setIcon(UIManager.getIcon("FileView.directoryIcon"));
                    } else {
                        setIcon(UIManager.getIcon("FileView.fileIcon"));
                    }                
                }
            }

            return this;
        }
}
