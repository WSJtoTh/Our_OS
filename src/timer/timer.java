package timer;

import Global.Global;
import Interrupt.*;


public class timer extends Thread{
	 private static int AllTime;
	 private static int RRTime;
	 private static int RRCount;
	 private static int sleepFlag;//sleepFlag=1ʱ˯��
	 
	 private static InterHandler handler=new InterHandler();
	 
	 public boolean setsleepFlag(int x)
	 {
		 sleepFlag=x;
		 return true;
	 }
	 
	 public int getAllTime()
	 {
		 return AllTime;
	 }
	 
	 public int getRRTime()
	 {
		 return RRTime;
	 }
	 
	 public void setAllTime(int x) {
		 AllTime=x;
	 }
	 
	 public void setRRTime(int x) {
		 RRTime=x;
	 }
	 
	 public void run() {
		 
		 while(true) {
			 while(sleepFlag==1) {
				 try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			AllTime++;
			RRTime++;
			//01234 01234
			if(RRTime%(Global.RRLength)==0&&RRTime!=0)
			{
				//�����ж�
				handler.timeINTR(InterType.TIMEOUT);
				RRCount++;
				//����
				RRTime=0;
				
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(handler.output());
			System.out.println("��ǰʱ��"+AllTime+"  ��ǰʱ��Ƭ��ʱ"+RRTime+"  ��ǰʱ��Ƭ"+RRCount);
		 }
	 }
	
	 
}
