package Interrupt;
import Device.*;
import timer.*;
//�жϷ������
public class InterService {
	
	private static InterHandler IntrHandler;
	//�������õĴ����жϵĺ���
	
	private static timer time=new timer();
	
	private static InterruptReg reg=new InterruptReg();
	 
	public int DealInterrupt() {
		//���ж� ��������Ӧ
		IntrHandler.offSwitch();
	
		//�����ֳ�
			//����һ��PCB
			//��ʱ��Ƭֹͣ�������Ƽ�ʱ�����Ǹ�ȫ������λ���ü�ʱ��sleep��Ҳ��һ�¼�ʱ����ǰ��ֵ
		
		InterType type;
		type=reg.getInterType();
		int OK=0;
		//�б��ж�Դ��ת���жϷ������
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
		
		//���ж�
		IntrHandler.offSwitch();
		//�ָ�����
			//���¿���ʱ��Ƭ��
			//PCB��
		//���ж�
		IntrHandler.onSwitch();
		if(OK==1) {
			System.out.print("�Ѿ��ɹ�������жϣ�");
		}
		else {
			System.out.println("δ�ɹ�������ж�");
		}
		return OK;
	}
	///////////////////����д������
	public int DealTimeOut()
	{	
		//�жϼĴ�����NULL
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		
		//ִ���жϷ������
			
		
			//�жϷ������
				//���Ⱥ��������Ⱥ�������һ����ʱ��Ƭ����ģ�
				time.setsleepFlag(1);
				time.setRRTime(0);
		
		
		return 1;
		
	}
	
	public int DealNeedPage() {
		
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		
		//ִ���жϷ������
			
		
			//�жϷ������
				//�ڴ��Ǳߵĵ�ҳ����������PCB��Ľ���id��
		return 1;
		
	}
	
	public int DealPrinterInt()
	{
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.PRINTER;
		//ִ���жϷ������
			
		
			//�жϷ������
				//������ responseINTR(IntrHandler.getdevINTRID())��ȡ�豸���ͣ��豸����������ID
				//signal(DevType,DevCount,ProID)
				
		
		return 1;
	}
	
	public int DealKeyboardInt()
	{
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		
		//ִ���жϷ������
			
		DevType devType;
		devType=DevType.KEYBOARD;
			//�жϷ������
				//������ responseINTR(IntrHandler.getdevINTRID())��ȡ�豸����������ID,��hashmap
				//signal(DevType,DevCount,ProID)
				
		
		return 1;
	}
	
	public int DealMicrophoneInt()
	{
		
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.MICROPHONE;
		//ִ���жϷ������
			
		
			//�жϷ������
				//������ responseINTR(IntrHandler.getdevINTRID())��ȡ�豸���ͣ��豸����������ID
				//signal(DevType,DevCount,ProID)
				
		
		return 1;
	}
	
	public int DealDiskInt()
	{
		
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.DISK;
		//ִ���жϷ������
			
		
			//�жϷ������
				//������ responseINTR(IntrHandler.getdevINTRID())��ȡ�豸���ͣ��豸����������ID
				//signal(DevType,DevCount,ProID)
				
		
		return 1;
	}
	

	
	public int DealAudioInt()
	{
		
		
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		DevType devType;
		devType=DevType.AUDIO;
		//ִ���жϷ������
			
		
			//�жϷ������
				//������ responseINTR(IntrHandler.getdevINTRID())��ȡ�豸���ͣ��豸����������ID
				//signal(DevType,DevCount,ProID)
				
		
		return 1;
	}
	
	public int DealioInt()
	{
	
		reg.SetInterType(InterType.NULL);
		//���ж� ������Ӧ
		IntrHandler.onSwitch();
		
		//ִ���жϷ������
			
		
			//�жϷ������
				//���Ⱥ���
				//sendCMD(CMD����д,DevType,proID)
		
		
		return 1;
	}
}
