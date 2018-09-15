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
	//给进程用的处理中断的函数
	private static Process PCB;
	
	private static timer time=new timer();
	
	private static InterruptReg reg=new InterruptReg();
	 
	public int DealInterrupt() {
		//关中断 不允许响应
		IntrHandler.offSwitch();
	
		//保存现场
		
		//使时间休眠
		timer.setsleepFlag(1);
			
		
		InterType type;
		type=reg.getInterType();
		int OK=0;
		//判别中断源，转入中断服务程序
		if (type==InterType.TIMEOUT)
		{
			OK=DealTimeOut();	
		}
		else if(type==InterType.AUDIOINT)
		{
			OK=DealAudioInt();
		}
		else if(type==InterType.DISKINT)
		{
			OK=DealDiskInt();
		}
		else if(type==InterType.KEYBOARDINT)
		{
			OK=DealKeyboardInt();
		}
		else if(type==InterType.MICROPHONEINT) {
			OK=DealMicrophoneInt();
		}
		else if(type==InterType.PRINTERINT) {
			OK=DealPrinterInt();
		}
		else if(type==InterType.NEEDPAGE) {
			OK=DealNeedPage();
		}
		else if(type==InterType.IOINTR) {
			OK=DealioInt();
		}
		
		//关中断
		IntrHandler.offSwitch();
		//恢复环境
		//重新开启时间片？
		timer.setsleepFlag(0);
			
			//PCB？
		//开中断
		IntrHandler.onSwitch();
		if(OK==1) {
			System.out.print("已经成功处理该中断！");
		}
		else {
			System.out.println("未成功处理该中断");
		}
		
		return OK;
	}
	
	public int DealTimeOut()
	{	
		//中断寄存器置NULL
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		
		//执行中断服务程序
			
		
		//time.setsleepFlag(1);
		//调度函数
		ProcessMGT.timeoutSchedule();
		//时间片清零
		time.setRRTime(0);
		
		
		return 1;
		
	}
	
	public int DealNeedPage() {
		
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		
		Memory.replacePage(PCB.getPid(),InterHandler.getPageNumber());
	
		return 1;
		
	}
	
	public int DealPrinterInt()
	{
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.PRINTER;
		//执行中断服务程序
		Process PCB=IntrHandler.getPCB();	
		boolean flag=DevController.responseINTR(IntrHandler.getdevINTRID(),devType);
		signal(devType,PCB.getPid());
	
				
		
		return 1;
	}
	
	public int DealKeyboardInt()
	{
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		
		//执行中断服务程序
			
		DevType devType;
		devType=DevType.KEYBOARD;

		Process PCB=IntrHandler.getPCB();	
		boolean flag=DevController.responseINTR(IntrHandler.getdevINTRID(),devType);
		System.out.println("来自Keyboard的数据"+Global.databus);
		signal(devType,PCB.getPid());
				
		
		return 1;
	}
	
	public int DealMicrophoneInt()
	{
		
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.MICROPHONE;
		//执行中断服务程序
			
		Process PCB=IntrHandler.getPCB();	
		boolean flag=DevController.responseINTR(IntrHandler.getdevINTRID(),devType);
		System.out.println("来自Microphone的数据"+Global.databus);
		signal(devType,PCB.getPid());		
		
		return 1;
	}
	
	public int DealDiskInt()
	{
		
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.DISK;
		//执行中断服务程序
		SignalType signal=IntrHandler.getSignal();
		

		Process PCB=IntrHandler.getPCB();	
		boolean flag=DevController.responseINTR(IntrHandler.getdevINTRID(),devType);
		if(signal==SignalType.READ) {
		System.out.println("来自Disk的数据"+Global.databus);
		}
		signal(devType,PCB.getPid());		
		
		return 1;
	}
	

	
	public int DealAudioInt()
	{
		
		
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.AUDIO;
		//执行中断服务程序
			
		
		Process PCB=IntrHandler.getPCB();	
		boolean flag=DevController.responseINTR(IntrHandler.getdevINTRID(),devType);
		boolean flag1=signal(devType,PCB.getPid());		
		if(flag==true&&flag1==true)
		{
			return 1;
		}
		
	}
	
	public int DealioInt()
	{
	
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		
		//执行中断服务程序
			
		
			//中断服务程序
				//调度函数
				//sendCMD(CMD读或写,DevType,proID)
		SignalType CMD=IntrHandler.getSignal();
		if(CMD==SignalType.WRITE)
		{
			Global.databus="来自进程的数据1234";
		}
		ProcessMGT.timeoutSchedule();
		time.setRRTime(0);
		boolean flag=DevController.sendCMD(CMD,IntrHandler.getDevType(),PCB.getPid());
		if(flag==true)
		{
			System.out.println("成功处理IO中断");
			return 1;
		}
		else {
			System.out.println("未成功处理IO中断");
			return 0;
		}
		
	}
}
