package CMD;

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

import FileManager.Directory;
import FileManager.FileManager;
import ProcessManager.Process;
import os.Compiler;
import os.Loader;

public class CMD { // cmd
	
	private FileManager fileManager;
	private Compiler compiler;
	private Loader loader;
	
	private String[] Commands = {"start", "newFile", "newDirectory"};
	
	public void associate(FileManager fileManager, Compiler compiler, Loader loader) {
		// TODO Auto-generated method stub
		this.fileManager = fileManager;
		this.compiler = compiler;
		this.loader = loader;
	}
	
	public Process interpret(String consoleText) {
		// TODO Auto-generated method stub
		String [] line = consoleText.split(" ");
		
		String command = null;
		String fileName = null;
		if(line.length > 0) {
			command = line[0];
			if(line.length > 1) {
				fileName = line[1];
			}
		}
		return this.shellExecute(command, fileName);
	}
	
	private Process shellExecute(String command, String fileName) {
//		"start", "newFile", "newDirectory"
		switch(command) {
		case "start":
			return this.start(fileName); 
		case "newFile":
			this.newFile();
			break;
		case "newDirectory":
			this.newDirectory();
			break;
		case "":
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;
			
		}			
		return null;
	}

	private Process start(String fileName) {
		Process process = this.compiler.getProcess(this.fileManager.getFile(fileName));
		this.loader.load(process);
		return process;
	}
	
	private void newDirectory() {
		Scanner scan = new Scanner(System.in);
		System.out.println("디렉토리 이름을 입력하세요");
		String directoryName = scan.next();
		Directory directory = new Directory();
		directory.setName(directoryName);
		System.out.println("디렉토리 생성 완료");
		System.out.println("경로를 입력하세요");
		System.out.println("ex) exe/Directory1");
		String directoryRoute = scan.nextLine();
		String[] route = directoryRoute.split("/");
		for(String name : route) {
			// 디렉토리 가져오기
			// 1. 파일매니저
			// 2. 파일매니저 속 원하는 애
			// n. 저장
			this.get(name);
		}
	}
	private Directory get(String fileName) {
		return this.fileManager.getDirectory().get(fileName);
	}

	private void newFile() {
		Scanner scan = new Scanner(System.in);
		System.out.println("파일 이름을 입력하세요");
		String fileName = scan.next();
		File file = new File(fileName);
	}


	
}
