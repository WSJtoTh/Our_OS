package Interrupt;
//�жϼĴ��� �ж����α�
public class InterruptReg {

	private InterType NowInterType;
	
	InterruptReg(){
		NowInterType=InterType.NULL;
	}
	//���ڻ�ȡ��ǰ���ж�����
	public InterType getInterType()
	{
		
		return NowInterType;
		
	}
	//���ô�ʱ���ж�����
	public boolean SetInterType(InterType type)
	{
		//�������α�
		boolean admit=false;
		admit=AdmitInter(type);
		
		if (admit==true)
		{
			
			NowInterType=type;
			return true;
		}
		else {
			System.out.println("�жϱ����ε��ˣ��и������ȼ��ж���");
			return false;
		}
		
	}
	
	public int getPrior(InterType type)
	{
		switch(type) {
		case NEEDPAGE:
			return 1;//���ȼ����
		case IOINTR:
			return 2;
		case TIMEOUT:
			return 3;//���ȼ��ڶ�
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
			System.out.println("�¼��ж����ȼ�"+thisPrior+"  �Ѵ����ж����ȼ�"+alreadyPrior);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
}
