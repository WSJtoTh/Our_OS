package Interrupt;

//中断寄存器 中断屏蔽表
public class InterruptReg {

	private static InterType NowInterType;

	InterruptReg() {
		NowInterType = InterType.NULL;
	}

	// 用于获取当前的中断类型
	public static InterType getInterType() {

		return NowInterType;

	}

	// 设置此时的中断类型
	public static boolean SetInterType(InterType type) {
		
		int IntrSwitch=InterHandler.getSwitch();
		if (type == InterType.NULL) {
			NowInterType = type;
			System.out.println("中断处理完毕，已成功将寄存器置为NULL");
			return true;
		}
		else if(IntrSwitch==0)//中断为关
		{
			System.out.println("无法写入寄存器，因为中断为关");
			return false;
		}
		else {// 调用屏蔽表
			boolean admit = false;
			admit = AdmitInter(type);
			
			if (admit == true) {

				NowInterType = type;
				return true;
			} else {
				System.out.println("中断被屏蔽掉了，有更高优先级中断在");
				return false;
			}
		}

	}

	public static int getPrior(InterType type) {
		switch (type) {
		case NEEDPAGE:
			return 1;// 优先级最高
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

	public static boolean AdmitInter(InterType type) {
		int thisPrior = getPrior(type);
		int alreadyPrior = getPrior(NowInterType);
		if (thisPrior < alreadyPrior) {
			System.out.println("新加中断优先级" + thisPrior + " 已存在中断优先级" + alreadyPrior);
			return true;
		} else {
			return false;
		}
	}

}
