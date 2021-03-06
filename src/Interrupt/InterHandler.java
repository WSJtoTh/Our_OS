package Interrupt;

import Device.*;
import Process.*;
import Process.Process;

//和其余模块的接口
public class InterHandler {

	private static int INTRSwitch;// 1为开中断，即此时可以收到中断请求，0为关中断，此时不能收到任何中断请求
	// static int interID;
	private static int devINTRID;
	//private static InterruptReg INTRreg = new InterruptReg();
	private static String outString;
	private static SignalType rOrw;//io中断时用的记录读或写
	private static int virPage;// 缺页时使用的虚拟页号
	private static Process pcb = new Process(); // 从进程获得
	private static DevType devtype;// io中断时需要记录的devType
	private static int DevReProId;
	private static SignalType stype;//dev中断时给的type类型

	public InterHandler() {
		INTRSwitch = 1;
		devINTRID = 0;
	}

	public static Process getPCB() {
		return pcb;
	}

	public static SignalType getSignal() {
		return rOrw;
	}

	public static DevType getDevType() {
		return devtype;
	}

	public static int getPageNumber() {
		return virPage;
	}
	
	public static int getSwitch()
	{
		return INTRSwitch;
	}

	// 开中断
	public static void onSwitch() {
		INTRSwitch = 1;
	}

	// 关中断
	public static void offSwitch() {
		INTRSwitch = 0;
	}

	// 给进程的IO中断
	public static void ioINTR(InterType type, Process PCB, SignalType signal, DevType devType)// 传入PCB
	{
		System.out.println("IO中断！！!!!!绑定的PCBid为"+PCB.getPid());
		System.out.println("IO中断！！！！！设备类型"+devType);
		if (INTRSwitch == 0) {
			System.out.println("中断已关闭，无法收到中断请求");
		}
		
			boolean setType = false;
			setType = InterruptReg.SetInterType(InterType.IOINTR);
			if (setType == true) {
				pcb = PCB;
				rOrw = signal;
				devtype=devType;
				System.out.println("IO中断成功写入中断寄存器！");
				outString = "IO中断！";

			} else {
				System.out.println("IO中断未成功写入中断寄存器");
			}
		
	}

	// 给进程的缺页中断
	public static void pageINTR(InterType type, int virtualPage)// 传入还要加个PCB
	{
		if (INTRSwitch == 0) {
			System.out.println("中断已关闭，无法收到中断请求");
		}
		
		boolean setType = false;
		setType = InterruptReg.SetInterType(InterType.NEEDPAGE);
		if (setType == true) {
			virPage = virtualPage;

			System.out.println("缺页中断成功写入中断寄存器！");
			outString = "缺页中断！";
		} else {
			System.out.println("缺页中断未成功写入中断寄存器");
		}
		
	}

	// 给时钟的

	public static void timeINTR(InterType type) {
		if (INTRSwitch == 0) {
			System.out.println("中断已关闭，无法收到中断请求");
		}
		
		boolean setType = false;
		System.out.println(type);
		setType = InterruptReg.SetInterType(InterType.TIMEOUT);
		if (setType == true) {
			System.out.println("时钟中断成功写入中断寄存器！");
			outString = "时钟中断";
		} else {
			System.out.println("时钟中断未成功写入中断寄存器");
		}
		
	}

	// 给设备的
	public static void devINTR(InterType type, int INTRID,int proId,SignalType sType) {
		if (INTRSwitch == 0) {
			System.out.println("中断已关闭，无法收到中断请求");
		}
		
		boolean setType = false;
		setType = InterruptReg.SetInterType(type);
		if (setType == true) {
			devINTRID = INTRID;
			System.out.println("收到设备中断，中断id为"+devINTRID);
			DevReProId=proId;
			stype=sType;
			System.out.println("设备中断成功写入中断寄存器！");
			// outString="设备中断";
			switch (type) {
			case AUDIOINT:

				outString = "音响中断";
			case DISKINT:
				outString = "磁盘中断";
			case IOINTR:
				outString = "IO中断";
			case KEYBOARDINT:
				outString = "键盘中断";
			case MICROPHONEINT:
				outString = "麦克风中断";
			case PRINTERINT:
				outString = "打印机中断";

			}
		} else {
			System.out.println("设备中断未成功写入中断寄存器");
		}

	}

	public static int getdevINTRID() {
		return devINTRID;
	}

	public static SignalType getsType() {
		return stype;
	}
	
	public static int getDevReProId() {
		return DevReProId;
	}
	///////////////////// 处理输出信息

	public static String output() {
		String Tips = outString;
		outString = "";// 消息已被取走后就会清空
		return Tips;
	}
}
