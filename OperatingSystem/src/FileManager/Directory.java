package FileManager;
import java.io.File;
import java.util.LinkedList;

public class Directory {
	String objectName;
	public String getName() {return this.objectName;}
	public void setName(String objectName) {this.objectName = objectName;} 
	
	LinkedList<Directory> directory;
	public LinkedList<Directory> getD() {return directory;}
	public void set(LinkedList<Directory> directory) {this.directory = directory;}
	public void newDirectory() {this.directory = new LinkedList<Directory>();}
	public void add(Directory directory) {this.directory.add(directory);}
	
	File file;
	public File getF() {return file;}
	public void set(File file) {this.file = file;}
	public void newFile() {this.file = new File(objectName);}
	
	public Directory() {
		this.objectName = null;
	}
	
	public Directory get(String name) {
//		for(int i = 0; i < directory.getDirectory().; i++) {
//			if(directory.getDirectory().get(i).getName == name) {
//				return directory.getDirectory().get(i);
//			}
//		}
		for(int i = 0; i < this.directory.size(); i++) {
			if(this.directory.get(i).objectName == name) {
				return this.directory.get(i);
			}
		}
		return null;
	}
}
