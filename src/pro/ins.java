package pro;

import Device.*;
import Interrupt.*;
import Process.Process;
import Process.*;
import memory.*;

public class ins {

	private static InterHandler intrHandler = new InterHandler();

	public static void ExeInstruction(String ins, Process PCB, int flag) {// 传进来Process PCB，flag为0的时候为计算，1的时候为执行
		String[] content = ins.split(" ");
		int[] resource;
		resource = new int[8];
		for (int i = 0; i < content.length; i++) {
			System.out.print(content[i] + " ");
		}
		switch (content[0]) {
		case "C":// 计算
			System.out.println("指令类型为计算");
			if (flag == 0) {// 计算资源
				int j = 2;
				while (j < content.length) {
					ResourceType reTypeC = ResourceType.valueOf(content[j]);// 所需资源
					j = j + 1;
					int ResourceNumberC = Integer.parseInt(content[j]);
					switch (reTypeC) {// 生成资源数组
					case E:
						resource[0] = ResourceNumberC;
						break;
					case F:
						resource[1] = ResourceNumberC;
						break;
					case G:
						resource[2] = ResourceNumberC;
						break;
					case K:
						resource[3] = ResourceNumberC;
						break;
					case P:
						resource[4] = ResourceNumberC;
						break;
					case D:
						resource[5] = ResourceNumberC;
						break;
					case M:
						resource[6] = ResourceNumberC;
						break;
					case A:
						resource[7] = ResourceNumberC;
						break;

					}
					j = j + 1;

				}
				// 置资源
				PCB.addCommonResource(resource);

			} else {// 执行代码
				int pagenumber = Integer.parseInt(content[1]);// 逻辑页号

				boolean isPage = Memory.seekPage(pagenumber, PCB.getPid());
				if (isPage == false)// 缺页
				{
					InterHandler.pageINTR(InterType.NEEDPAGE, pagenumber);
				}
				// 调李叶的转换函数，看看有没有，没有的话缺页中断
				// 使用函数
			}

			break;
		case "K":// 键盘输入
			System.out.println("指令类型为键盘输入");

			if (flag == 0) {// 计算资源
				PCB.addResource(DevType.KEYBOARD);
			} else {
				SignalType rOrwK = SignalType.READ;
				// 使用函数
				intrHandler.ioINTR(InterType.IOINTR, PCB, rOrwK, DevType.KEYBOARD);
			}
			break;
		case "P":// 打印
			System.out.println("指令类型为打印");
			if (flag == 0) {
				PCB.addResource(DevType.PRINTER);
			} else {
				int FileSizeP = Integer.parseInt(content[1]);
				SignalType rOrwP = SignalType.WRITE;
				intrHandler.ioINTR(InterType.IOINTR, PCB, rOrwP, DevType.PRINTER);
				// 使用函数
			}
			break;
		case "R":// 读磁盘
			System.out.println("指令类型为读磁盘");

			if (flag == 0) {
				PCB.addResource(DevType.DISK);
			} else {
				int FileSizeR = Integer.parseInt(content[1]);
				SignalType rOrwR = SignalType.READ;
				intrHandler.ioINTR(InterType.IOINTR, PCB, rOrwR, DevType.DISK);
			}
			break;
		case "W":// 写磁盘
			System.out.println("指令类型为写磁盘");

			if (flag == 0) {
				PCB.addResource(DevType.DISK);
			} else {
				int FileSizeW = Integer.parseInt(content[1]);
				SignalType rOrwW = SignalType.WRITE;
				intrHandler.ioINTR(InterType.IOINTR, PCB, rOrwW, DevType.DISK);
			}
			break;
		case "M":// 麦克风输入
			System.out.println("指令类型为麦克风输入");

			if (flag == 0) {
				PCB.addResource(DevType.MICROPHONE);
			} else {
				SignalType rOrwM = SignalType.READ;
				intrHandler.ioINTR(InterType.IOINTR, PCB, rOrwM, DevType.MICROPHONE);
			}
			break;
		case "A":// 音响输出
			System.out.println("指令类型为音响输出");

			if (flag == 0) {
				PCB.addResource(DevType.AUDIO);
			} else {
				int FileSizeA = Integer.parseInt(content[1]);
				SignalType rOrwA = SignalType.WRITE;
				intrHandler.ioINTR(InterType.IOINTR, PCB, rOrwA, DevType.AUDIO);
			}
			break;
		case "Q":// 结束运行
			// 调函数
			System.out.println("指令类型为结束类型");

			if (flag == 1) {
				System.out.println(PCB.toString() + "end!");
				ProcessMGT.popRunning();// 调出running队列
				ProcessMGT.terminateProcess(PCB);// 调入terminated队列
			}
			break;
		}

	}

}
