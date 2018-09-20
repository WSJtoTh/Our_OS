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

	private static boolean isResponse;
	
	public static String DealStr;
	public static String recvStr;//给页面的，收到的数据

	private static timer time = new timer();

	private static InterruptReg reg = new InterruptReg();

	public InterService() {
		isResponse = false;
	}

	public static void setisResponse(boolean isOk) {
		isResponse = isOk;
	}

	// 给进程用的处理中断的函数
	public static InterType DealInterrupt() {
		// 关中断 不允许响应
		InterHandler.offSwitch();

		// 保存现场

		// 使时间休眠
		timer.setsleepFlag(1);

		InterType type;
		type = InterruptReg.getInterType();
		if(type!=InterType.NULL) {
			DealStr="正在处理"+type+"中断";
		}
		System.out.println("此时中断类型为" + type);
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
		InterHandler.offSwitch();
		// 恢复环境
		// 重新开启时间片？
		boolean timeyes = timer.setsleepFlag(0);
		if (timeyes == true) {
			System.out.println("已成功开启时间片");
		}

		// PCB？
		// 开中断
		InterHandler.onSwitch();
		if (OK == 1) {
			DealStr="已经成功处理"+type+"中断";
			System.out.print("已经成功处理该中断！");
		} else if (OK == 0 && type != InterType.NULL) {
			DealStr="未成功处理"+type+"中断";
			System.out.println("未成功处理该中断");
		} else if (type == InterType.NULL) {
			DealStr="没有需要处理的中断";
			System.out.println("没有需要处理的中断！");
		}

		return type;
	}

	public static int DealTimeOut() {
		// 中断寄存器置NULL

		// 开中断 允许响应
		//InterHandler.onSwitch();

		// 执行中断服务程序

		// time.setsleepFlag(1);
		// 调度函数

		ProcessMGT.timeoutSchedule();
		// 时间片清零
		time.setRRTime(0);
		System.out.println("成功处理时钟中断");
		InterruptReg.SetInterType(InterType.NULL);
		return 1;

	}

	public static int DealNeedPage() {

		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();
		System.out.println("虚拟页号" + InterHandler.getPageNumber());
		Memory.replacePage(PCB.getPid(), InterHandler.getPageNumber());
		System.out.println("成功处理缺页中断");

		return 1;

	}

	public static int DealPrinterInt() {
		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();
		DevType devType;
		devType = DevType.PRINTER;
		isResponse = false;
		// 执行中断服务程序
		Process PCB = InterHandler.getPCB();
		System.out.println("处理打印机中断的！！！！此时devINTR" + InterHandler.getdevINTRID() + "设备类型" + devType);
		boolean flag = false;
		while (!isResponse) {
		//	System.out.println("处理打印机中断回复response");
			flag = DevController.responseINTR(InterHandler.getdevINTRID(), devType);
		}
		System.out.println("处理打印机中断！！发送Signal的devType"+devType+"进程id"+InterHandler.getDevReProId());

		 boolean flag1 = DevController.signal(devType,InterHandler.getDevReProId());
		System.out.println("处理打印机中断flag"+flag);
		if (flag == true  && flag1 == true ) {
			System.out.println("处理打印机中断的！！！！此时唤醒的proid" + InterHandler.getDevReProId());
			ProcessMGT.wakeUpProcess(InterHandler.getDevReProId(), DevType.PRINTER);
			System.out.println("成功处理打印机中断");
			return 1;
		} else {
			System.out.println("未成功处理打印机中断");
			return 0;
		}

	}

	public static int DealKeyboardInt() {
		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();

		// 执行中断服务程序

		DevType devType;
		devType = DevType.KEYBOARD;
		isResponse = false;
		Process PCB = InterHandler.getPCB();
		System.out.println("处理键盘中断的！！！！此时devINTR" + InterHandler.getdevINTRID() + "设备类型" + devType);
		boolean flag = false;
		while (!isResponse) {
		//System.out.println("处理键盘中断回复response");
			flag = DevController.responseINTR(InterHandler.getdevINTRID(), devType);
		}
		System.out.println("处理键盘中断flag"+flag);
		System.out.println("来自Keyboard的数据" + Global.databus);
		recvStr="来自KEYBOARD的数据"+Global.databus;
		System.out.println("处理键盘中断！！发送Signal的devType"+devType+"进程id"+InterHandler.getDevReProId());

		boolean flag1 = DevController.signal(devType,InterHandler.getDevReProId());
		
		if (flag == true  && flag1 == true ) {
			System.out.println("处理键盘中断的！！！！此时唤醒的proid" + InterHandler.getDevReProId());
			ProcessMGT.wakeUpProcess(InterHandler.getDevReProId(), DevType.KEYBOARD);
			System.out.println("成功处理KEYBOARD中断");
			return 1;
		} else {
			System.out.println("未成功处理KEYBOARD中断");
			return 0;
		}
	}

	public static int DealMicrophoneInt() {

		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();
		DevType devType;
		devType = DevType.MICROPHONE;
		// 执行中断服务程序
		isResponse = false;
		Process PCB = InterHandler.getPCB();
		System.out.println("处理麦克风中断的！！！！此时devINTR" + InterHandler.getdevINTRID() + "设备类型" + devType);
		boolean flag = false;
		while (!isResponse) {
		//	System.out.println("处理麦克风中断回复response");
			flag = DevController.responseINTR(InterHandler.getdevINTRID(), devType);
		}
		System.out.println("处理麦克风中断flag"+flag);
		System.out.println("来自Microphone的数据" + Global.databus);
		recvStr="来自MICROPHONE的数据"+Global.databus;
		System.out.println("处理麦克风中断！！发送Signal的devType"+devType+"进程id"+InterHandler.getDevReProId());
		boolean flag1 = DevController.signal(devType, InterHandler.getDevReProId());
		if (flag == true  && flag1 == true ) {
			System.out.println("处理麦克风中断的！！！！此时唤醒的proid" + InterHandler.getDevReProId());
			ProcessMGT.wakeUpProcess(InterHandler.getDevReProId(), DevType.MICROPHONE);
			System.out.println("成功处理麦克风中断");
			return 1;
		} else {
			System.out.println("未成功处理麦克风中断");
			return 0;
		}

	}

	public static int DealDiskInt() {

		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();
		DevType devType;
		devType = DevType.DISK;
		isResponse = false;
		// 执行中断服务程序
		SignalType signal = InterHandler.getsType();

		Process PCB = InterHandler.getPCB();
		System.out.println("处理DISK中断的！！！！此时devINTR" + InterHandler.getdevINTRID() + "设备类型" + devType);
		boolean flag = false;
		while (!isResponse) {
		//	System.out.println("处理磁盘中断回复response");
			flag=DevController.responseINTR(InterHandler.getdevINTRID(), devType);
		}

		System.out.println("处理磁盘中断flag"+flag);
		if (signal == SignalType.READ) {
			System.out.println("来自Disk的数据" + Global.databus);
			recvStr="来自DISK的数据"+Global.databus;
		}
		System.out.println("处理麦克风中断！！发送Signal的devType"+devType+"进程id"+InterHandler.getDevReProId());

		 boolean flag1 = DevController.signal(devType,InterHandler.getDevReProId());
		System.out.println(PCB.getPid());

		if (flag == true  && flag1 == true ) {
			System.out.println("处理磁盘中断的！！！！此时唤醒的proid" + InterHandler.getDevReProId());
			ProcessMGT.wakeUpProcess(InterHandler.getDevReProId(), DevType.DISK);
			System.out.println("成功处理磁盘中断");
			return 1;
		} else {
			System.out.println("未成功处理磁盘中断");
			return 0;
		}

	}

	public static int DealAudioInt() {

		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();
		DevType devType;
		devType = DevType.AUDIO;
		isResponse = false;
		// 执行中断服务程序
		System.out.println("处理音响中断的！！！！此时devINTR" + InterHandler.getdevINTRID() + "设备类型" + devType);
		Process PCB = InterHandler.getPCB();
		boolean flag = false;
		while (!isResponse) {
			//System.out.println("处理音响中断回复response");
			flag=DevController.responseINTR(InterHandler.getdevINTRID(), devType);
		}
		System.out.println("处理音响中断flag"+flag);
		System.out.println("音响中断给的pid"+PCB.getPid());
		System.out.println("处理音响中断！！发送Signal的devType"+devType+"进程id"+InterHandler.getDevReProId());

		boolean flag1 = DevController.signal(devType, InterHandler.getDevReProId());
		if (flag == true  && flag1 == true ) {
			System.out.println("处理音响中断的！！！！此时唤醒的proid" + InterHandler.getDevReProId());
			ProcessMGT.wakeUpProcess(InterHandler.getDevReProId(), DevType.AUDIO);
			System.out.println("成功处理音响中断");
			return 1;
		} else {
			System.out.println("未成功处理音响中断");
			return 0;
		}

	}

	public static int DealioInt() {

		InterruptReg.SetInterType(InterType.NULL);
		// 开中断 允许响应
		//InterHandler.onSwitch();

		// 执行中断服务程序

		// 中断服务程序
		// 调度函数
		// sendCMD(CMD读或写,DevType,proID)
		PCB = InterHandler.getPCB();
		SignalType CMD = InterHandler.getSignal();
		ProcessMGT.blockProcess(PCB, InterHandler.getDevType());
		// ProcessMGT.timeoutSchedule();
		// time.setRRTime(0);
		System.out.println("进程id" + PCB.getPid());
		System.out.println("设备类型" + InterHandler.getDevType());
		// boolean flag1 = DevController.wait(IntrHandler.getDevType(), PCB.getPid());
		boolean flag = DevController.sendCMD(CMD, InterHandler.getDevType(), PCB.getPid());
		if (CMD == SignalType.WRITE) {
			Global.databus = "来自进程的数据1234";
		}

		System.out.println("pid" + PCB.getPid() + " 设备类型" + InterHandler.getDevType() + " CMD:" + CMD);

		if (flag == true) {

			System.out.println("成功处理IO中断");
			return 1;
		} else {
			System.out.println("未成功处理IO中断");
			return 0;
		}

	}
}
