package cecs277.file.manager;

import java.io.File;

public class MyFileNode {
    File file;
    String filename;
    
    public MyFileNode(String filename) {
        file = new File(filename);
    }
   
    public MyFileNode(String name, File f){
        filename = name;
        file = f;
    }
    
    public File getFile(){
        return file;
    }
    
    public boolean hasChildren() {
        if (file.listFiles() != null)
            return true;
        return false;
    }
    
    public String toString() {
        if(file.getName().equals(""))
            return file.getPath();
        return file.getName();
    }
    
    public boolean isDirectory(){
        return file.isDirectory();
    }
}
