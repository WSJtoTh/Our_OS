package Interrupt;

import Device.*;
import timer.*;
import Process.*;
import Process.Process;
import Global.*;
import memory.*;

//中断服务程序
public class InterService {

	private static InterHandler IntrHandler;

	private static Process PCB;

	private static timer time = new timer();

	private static InterruptReg reg = new InterruptReg();

	// 给进程用的处理中断的函数
	public static InterType DealInterrupt() {
		// 关中断 不允许响应
		IntrHandler.offSwitch();

		// 保存现场

		// 使时间休眠
		timer.setsleepFlag(1);

		InterType type;
		type = reg.getInterType();
		
			System.out.println("此时中断" + type);
			int OK = 0;
			// 判别中断源，转入中断服务程序
			if (type == InterType.NULL) {
			System.out.println("此时没有中断");
			} else if (type == InterType.TIMEOUT) {
				OK = DealTimeOut();
			} else if (type == InterType.AUDIOINT) {
				OK = DealAudioInt();
			} else if (type == InterType.DISKINT) {
				OK = DealDiskInt();
			} else if (type == InterType.KEYBOARDINT) {
				OK = DealKeyboardInt();
			} else if (type == InterType.MICROPHONEINT) {
				OK = DealMicrophoneInt();
			} else if (type == InterType.PRINTERINT) {
				OK = DealPrinterInt();
			} else if (type == InterType.NEEDPAGE) {
				OK = DealNeedPage();
			} else if (type == InterType.IOINTR) {
				OK = DealioInt();
			}

			// 关中断
			IntrHandler.offSwitch();
			// 恢复环境
			// 重新开启时间片？
			boolean timeyes=timer.setsleepFlag(0);
			if(timeyes==true)
			{
				System.out.println("已成功开启时间片");
			}

			// PCB？
			// 开中断
			IntrHandler.onSwitch();
			if (type!=InterType.NULL&&OK == 1) {
				System.out.print("已经成功处理该中断！");
			} else {
				System.out.println("未成功处理该中断");
			}

			
	
		return type;
	}

	public static int DealTimeOut() {
		// 中断寄存器置NULL
		
		// 开中断 允许响应
		IntrHandler.onSwitch();

		// 执行中断服务程序

		// time.setsleepFlag(1);
		// 调度函数

		ProcessMGT.timeoutSchedule();
		// 时间片清零
		time.setRRTime(0);
		System.out.println("成功处理时钟中断");
		reg.SetInterType(InterType.NULL);
		return 1;

	}

	public static int DealNeedPage() {

		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();
		System.out.println("虚拟页号" + InterHandler.getPageNumber());
		Memory.replacePage(PCB.getPid(), InterHandler.getPageNumber());
		System.out.println("成功处理缺页中断");

		return 1;

	}

	public static int DealPrinterInt() {
		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType = DevType.PRINTER;
		// 执行中断服务程序
		Process PCB = IntrHandler.getPCB();
		boolean flag = DevController.responseINTR(IntrHandler.getdevINTRID(), devType);
		boolean flag1 = DevController.signal(devType, PCB.getPid());
		if (flag == true && flag1 == true) {
			ProcessMGT.wakeUpProcess(IntrHandler.getDevReProId(), DevType.PRINTER);
			System.out.println("成功处理打印机中断");
			
			return 1;
		} else {
			System.out.println("未成功处理打印机中断");
			return 0;
		}

	}

	public static int DealKeyboardInt() {
		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();

		// 执行中断服务程序

		DevType devType;
		devType = DevType.KEYBOARD;

		Process PCB = IntrHandler.getPCB();
		boolean flag = DevController.responseINTR(IntrHandler.getdevINTRID(), devType);
		System.out.println("来自Keyboard的数据" + Global.databus);
		boolean flag1 = DevController.signal(devType, PCB.getPid());

		if (flag == true && flag1 == true) {
			ProcessMGT.wakeUpProcess(IntrHandler.getDevReProId(), DevType.KEYBOARD);
			System.out.println("成功处理KEYBOARD中断");
			return 1;
		} else {
			System.out.println("未成功处理KEYBOARD中断");
			return 0;
		}
	}

	public static int DealMicrophoneInt() {

		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType = DevType.MICROPHONE;
		// 执行中断服务程序

		Process PCB = IntrHandler.getPCB();
		boolean flag = DevController.responseINTR(IntrHandler.getdevINTRID(), devType);
		System.out.println("来自Microphone的数据" + Global.databus);
		boolean flag1 = DevController.signal(devType, PCB.getPid());
		if (flag == true && flag1 == true) {
			ProcessMGT.wakeUpProcess(IntrHandler.getDevReProId(), DevType.MICROPHONE);
			System.out.println("成功处理麦克风中断");
			return 1;
		} else {
			System.out.println("未成功处理麦克风中断");
			return 0;
		}

	}

	public static int DealDiskInt() {

		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType = DevType.DISK;
		// 执行中断服务程序
		SignalType signal = IntrHandler.getsType();

		Process PCB = IntrHandler.getPCB();
		boolean flag = DevController.responseINTR(IntrHandler.getdevINTRID(), devType);
		if (signal == SignalType.READ) {
			System.out.println("来自Disk的数据" + Global.databus);
		}
		boolean flag1 = DevController.signal(devType, PCB.getPid());
		System.out.println(PCB.getPid());

		if (flag == true && flag1 == true) {
			ProcessMGT.wakeUpProcess(IntrHandler.getDevReProId(), DevType.DISK);
			System.out.println("成功处理磁盘中断");
			return 1;
		} else {
			System.out.println("未成功处理磁盘中断");
			return 0;
		}

	}

	public static int DealAudioInt() {

		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType = DevType.AUDIO;
		// 执行中断服务程序

		Process PCB = IntrHandler.getPCB();
		boolean flag = DevController.responseINTR(IntrHandler.getdevINTRID(), devType);
		boolean flag1 = DevController.signal(devType, PCB.getPid());
		if (flag == true && flag1 == true) {
			ProcessMGT.wakeUpProcess(IntrHandler.getDevReProId(), DevType.AUDIO);
			System.out.println("成功处理音响中断");
			return 1;
		} else {
			System.out.println("未成功处理音响中断");
			return 0;
		}

	}

	public static int DealioInt() {

		reg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		IntrHandler.onSwitch();

		// 执行中断服务程序

		// 中断服务程序
		// 调度函数
		// sendCMD(CMD读或写,DevType,proID)
		PCB = InterHandler.getPCB();
		SignalType CMD = IntrHandler.getSignal();
		ProcessMGT.blockProcess(PCB,InterHandler.getDevType());
		ProcessMGT.timeoutSchedule();
		time.setRRTime(0);
		boolean flag = DevController.sendCMD(CMD, IntrHandler.getDevType(), PCB.getPid());
		if (CMD == SignalType.WRITE) {
			Global.databus = "来自进程的数据1234";
		}
		
		System.out.println("pid" + PCB.getPid() + " 设备类型" + IntrHandler.getDevType() + " CMD:" + CMD);

		boolean flag1 = DevController.wait(IntrHandler.getDevType(), PCB.getPid());
		if (flag == true && flag1 == true) {

			System.out.println("成功处理IO中断");
			return 1;
		} else {
			System.out.println("未成功处理IO中断");
			return 0;
		}

	}
}
