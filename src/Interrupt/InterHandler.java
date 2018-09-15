package Interrupt;
//������ģ��Ľӿ�
public class InterHandler {
	
		static private int INTRSwitch;//1Ϊ���жϣ�����ʱ�����յ��ж�����0Ϊ���жϣ���ʱ�����յ��κ��ж�����
		//static int interID;
		private int devINTRID;
		private static InterruptReg INTRreg=new InterruptReg();
		private static String outString;
		
		public InterHandler(){
			INTRSwitch=1;
			devINTRID=0;
		}
		
		
		//���ж�
		public void onSwitch() {
			INTRSwitch=1;
		}
		//���ж�
		public void offSwitch() {
			INTRSwitch=0;
		}
		
		
		//�����̵�IO�ж� 
		public static void ioINTR(InterType type)
		{
			if(INTRSwitch==0) {
				System.out.println("�ж��ѹرգ��޷��յ��ж�����");
			}
			boolean setType=false;
			setType=INTRreg.SetInterType(type);
			if (setType==true)
			{
				System.out.println("IO�жϳɹ�д���жϼĴ�����");
				outString="IO�жϣ�";
				
			}
			else {
				System.out.println("IO�ж�δ�ɹ�д���жϼĴ���");
			}
		}
		//�����̵�ȱҳ�ж�
		public static void pageINTR(InterType type)
		{
			if(INTRSwitch==0) {
				System.out.println("�ж��ѹرգ��޷��յ��ж�����");
			}
			boolean setType=false;
			setType=INTRreg.SetInterType(type);
			if (setType==true)
			{
				System.out.println("ȱҳ�жϳɹ�д���жϼĴ�����");
				outString="ȱҳ�жϣ�";
			}
			else {
				System.out.println("ȱҳ�ж�δ�ɹ�д���жϼĴ���");
			}
			
		}
		
		
		
		
		//��ʱ�ӵ�
		
		public void timeINTR(InterType type) {
			if(INTRSwitch==0) {
				System.out.println("�ж��ѹرգ��޷��յ��ж�����");
			}
			boolean setType=false;
			System.out.println(type);
			setType=INTRreg.SetInterType(type);
			if (setType==true)
			{
				System.out.println("ʱ���жϳɹ�д���жϼĴ�����");
				outString="ʱ���ж�";
			}
			else {
				System.out.println("ʱ���ж�δ�ɹ�д���жϼĴ���");
			}
		}
		
		//���豸��
		public void devINTR(InterType type,int INTRID) {
			if(INTRSwitch==0) {
				System.out.println("�ж��ѹرգ��޷��յ��ж�����");
			}
			devINTRID=INTRID;
			boolean setType=false;
			setType=INTRreg.SetInterType(type);
			if (setType==true)
			{
				System.out.println("�豸�жϳɹ�д���жϼĴ�����");
				//outString="�豸�ж�";
				switch(type) {
				case AUDIOINT:
					outString="�����ж�";
				case DISKINT:
					outString="�����ж�";
				case IOINTR:
					outString="IO�ж�";
				case KEYBOARDINT:
					outString="�����ж�";
				case MICROPHONEINT:
					outString="��˷��ж�";
				case PRINTERINT:
					outString="��ӡ���ж�";
				}
			}
			else {
				System.out.println("�豸�ж�δ�ɹ�д���жϼĴ���");
			}
			
		}
		
		public int getdevINTRID() {
			return devINTRID;
		}
		
		/////////////////////���������Ϣ

		public String output() {
			String Tips=outString;
			outString="";//��Ϣ�ѱ�ȡ�ߺ�ͻ����
			return Tips;
		}
}
