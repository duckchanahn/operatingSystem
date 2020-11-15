package FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class FileManager {
	// ���ϰ����ϱ�
	// �ϵ��ũ�� DMA�� ����, ���ϵ��� ����, �������� ���ȭ��, ���Ϲ�ġ ����
	
//	LinkedList<Directory> directory;
	Directory directory;
//	public LinkedList<Directory> getDirectory() {return directory;}
	public Directory getDirectory() {return this.directory;}
	public FileManager() {
//		this.directory = new LinkedList<Directory>();
		this.directory = new Directory();
		this.directory.newDirectory();
		File file = new File("data/fileInfo.txt");
		this.initialize(file);
		
//		Directory directorydata = new Directory();
//		directorydata.setName("data");
//		directorydata.newDirectory();
//		this.directory.add(directorydata);
	}
	
	private void initialize(File file) {
		Scanner scan;
		try {
			scan = new Scanner(file);
			String fileInfo;
//			System.out.println(scan.hasNext());
			while(scan.hasNext()) {
				fileInfo = scan.nextLine();
				String[] temp = fileInfo.split(" ");
				String[] temp1 = temp[1].split("/");
				Directory directoryexe = new Directory();
				directoryexe.setName(temp1[temp1.length-1]);
				if(temp[0] == "Direcory") {directoryexe.newDirectory();} 
				else if(temp[0] == "File") {directoryexe.newFile();}
				if(temp1.length == 1) {
					this.directory.add(directoryexe);
				}
				else {
					Directory d = null;
					for(int i = 0; i < temp1.length; i++) {
						d = this.directory.get(temp1[i]);
					}
//					d.add(directoryexe);
				}
			}
			System.out.println(""); //Ȯ��
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	public File getFile(String fileName) {
		File file = new File(fileName);
		return file;
	}

}
