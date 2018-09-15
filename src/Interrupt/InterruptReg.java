package Interrupt;
//中断寄存器 中断屏蔽表
public class InterruptReg {

	private InterType NowInterType;
	
	InterruptReg(){
		NowInterType=InterType.NULL;
	}
	//用于获取当前的中断类型
	public InterType getInterType()
	{
		
		return NowInterType;
		
	}
	//设置此时的中断类型
	public boolean SetInterType(InterType type)
	{
		//调用屏蔽表
		boolean admit=false;
		admit=AdmitInter(type);
		
		if (admit==true)
		{
			
			NowInterType=type;
			return true;
		}
		else {
			System.out.println("中断被屏蔽掉了，有更高优先级中断在");
			return false;
		}
		
	}
	
	public int getPrior(InterType type)
	{
		switch(type) {
		case NEEDPAGE:
			return 1;//优先级最高
		case IOINTR:
			return 2;
		case TIMEOUT:
			return 3;
		case KEYBOARDINT:
			return 4;
		case MICROPHONEINT:
			return 5;
		case DISKINT:
			return 6;
		case AUDIOINT:
			return 7;
		case PRINTERINT:
			return 8;
		default:
			return 100;
		
			
		}
	}
	
	public boolean AdmitInter(InterType type)
	{
		int thisPrior=getPrior(type);
		int alreadyPrior=getPrior(NowInterType);
		if (thisPrior<alreadyPrior) {
			System.out.println("新加中断优先级"+thisPrior+" 已存在中断优先级"+alreadyPrior);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
}