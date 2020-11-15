package os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import ProcessManager.Process;

public class Compiler {
	
	// 컴파일

	public Process getProcess(File file) {
		try {
			int stackSegmentSize = 0;
			int[] codes = null;

			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				try {
					String line = sc.nextLine(); // 빈 라인, 코멘트 라인 다 스킵해야함!

					if (line.substring(0, 2).equals("//")) {

					} else if (line.isEmpty()) { // isBlank는 스페이스바까지. Empty는 엔터만 거른다고 함

					} else if (line.substring(0, 5).contentEquals(".code")) { // 세그먼트
						codes = this.parseCode(sc);
//						System.out.println("--------------codes 출력");
//						for (int i = 0; i < codes.length; i++) {
//							System.out.println(codes[i]);
//						}
					} else if (line.substring(0, 6).contentEquals(".stack")) {
						stackSegmentSize = this.parseStack(sc);
//						System.out.println("stack 사이즈: " + stackSegmentSize);
					}

				} catch (StringIndexOutOfBoundsException e) {
					//
				}
			}
			Process process = new Process(stackSegmentSize, codes); // exe파일
			sc.close();
			return process;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private int parseStack(Scanner sc) {
//		System.out.println("--------------parseStack");
		int stackSize = 0;
		boolean check = true;
		while (check) {
			String stackLine = sc.nextLine();
			if (stackLine.substring(0, 2).equals("//")) {

			} else if (stackLine.substring(0, 4).equals("size")) {
				String[] sta = stackLine.split(" ");
				stackSize = Integer.parseInt(sta[2]);
				return stackSize;
			}
		}
		return stackSize;
	}

	private int[] parseCode(Scanner sc) {
//		System.out.println("---------------parseCode");
		Vector<Integer> compile = new Vector<Integer>();
		int loopIndex = 0;
		while (sc.hasNext()) {
			try {
			String codeLine = sc.nextLine();
			if (!(codeLine.isEmpty())) { // isBlank는 스페이스바까지. Empty는 엔터만 거른다고 함
				if(!(codeLine.substring(0, 2).equals("//"))) {
					String[] temp = codeLine.split(" ");
					int address = 0;;
				if(temp[0].equals("LDV")) {
					if(temp[1].substring(0, 1).equals("\"")) {
//						for(int i = 0; i < temp[1].length(); i++) {
//							
//						}
//						address = (0 << 16) + Integer.parseInt(temp[1]);
						for(int i = 0; temp[1].substring(i, i + 1) == "\""; i++) {
							
						}
//						System.out.println(temp[1]);
					} else {
						address = (0 << 16) + Integer.parseInt(temp[1]);
					} 
				}else if(temp[0].equals("LDA")) {
					address = (1 << 16) + Integer.parseInt(temp[1]);
				}else if(temp[0].equals("ADV")) {
					address = (2 << 16) + Integer.parseInt(temp[1]);
				}else if(temp[0].equals("ADA")) {
					address = (3 << 16) + Integer.parseInt(temp[1]);
				}else if(temp[0].equals("STA")) {
					address = (4 << 16) + Integer.parseInt(temp[1]);
				}else if(temp[0].equals("JGZ")) {
					address = (5 << 16);
				}else if(temp[0].equals("CMP")) {
					address = (6 << 16) + Integer.parseInt(temp[1]);
				}else if (temp[0].equals("Halt")) {
					address = (7 << 16);
				}else if(temp[0].equals("INT")) {
					address = (8 << 16) + Integer.parseInt(temp[1]);
				}else if(temp[0].equals(":loop")) {
					address = (9 << 16);
				} 	
				compile.add(address);
				}
			}
			}catch (StringIndexOutOfBoundsException e) {}
		}
		int[] Instruction = new int[compile.size()];
		for(int i = 0; i<compile.size(); i++) {
			Instruction[i] = compile.elementAt(i);
		}
		return Instruction;
}

}
