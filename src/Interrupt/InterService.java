package Interrupt;
import Device.*;
import timer.*;
//中断服务程序
public class InterService {
	
	private static InterHandler IntrHandler;
	//给进程用的处理中断的函数
	//private static Process PCB;
	
	private static timer time=new timer();
	
	private static InterruptReg reg=new InterruptReg();
	 
	public int DealInterrupt() {//传入PCB
		//关中断 不允许响应
		IntrHandler.offSwitch();
	
		//保存现场
			//保存一下PCB
			//让时间片停止，将控制计时器的那个全局量置位，让计时器sleep，也存一下计时器当前的值
		
		InterType type;
		type=reg.getInterType();
		int OK=0;
		//判别中断源，转入中断服务程序
		if (type==InterType.TIMEOUT)
		{
			OK=DealTimeOut();	//传入PCB
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
	///////////////////继续写服务函数
	public int DealTimeOut()
	{	
		//中断寄存器置NULL
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		
		//执行中断服务程序
			
		
			//中断服务程序
				//调度函数（调度函数里有一个让时间片清零的）
				time.setsleepFlag(1);
				time.setRRTime(0);
		
		
		return 1;
		
	}
	
	public int DealNeedPage() {
		
		reg.SetInterType(InterType.NULL);
		//开中断 允许响应
		IntrHandler.onSwitch();
		
		//执行中断服务程序
			
		
			//中断服务程序
				//内存那边的调页函数（传入PCB里的进程id）
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
		boolean flag=responseINTR(IntrHandler.getdevINTRID(),devType);
		signal(devType,PCB.getPID());
			//中断服务程序
				//从外设 responseINTR(IntrHandler.getdevINTRID()) 回布尔型
				//signal(DevType,ProID)
				
		
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
			//中断服务程序
				//从外设 responseINTR(IntrHandler.getdevINTRID())获取设备数量，进程ID,用hashmap
				//signal(DevType,ProID)
		Process PCB=IntrHandler.getPCB();	
		boolean flag=responseINTR(IntrHandler.getdevINTRID(),devType);
		signal(devType,PCB.getPID());
				
		
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
			
		
			//中断服务程序
				//从外设 responseINTR(IntrHandler.getdevINTRID())获取设备类型，设备数量，进程ID
				//signal(DevType,DevCount,ProID)
		Process PCB=IntrHandler.getPCB();	
		boolean flag=responseINTR(IntrHandler.getdevINTRID(),devType);
		signal(devType,PCB.getPID());		
		
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
			
		
			//中断服务程序
				//从外设 responseINTR(IntrHandler.getdevINTRID(),DevType type)
				//signal(DevType,DevCount,ProID)
		Process PCB=IntrHandler.getPCB();	
		boolean flag=responseINTR(IntrHandler.getdevINTRID(),devType);
		signal(devType,PCB.getPID());		
		
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
			
		
			//中断服务程序
				//从外设 responseINTR(IntrHandler.getdevINTRID())获取设备类型，设备数量，进程ID
				//signal(DevType,DevCount,ProID)
		Process PCB=IntrHandler.getPCB();	
		boolean flag=responseINTR(IntrHandler.getdevINTRID(),devType);
		boolean flag1=signal(devType,PCB.getPID());		
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
		boolean flag=sendCMD(CMD,IntrHandler.getDevType(),PCB.getPID());
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
