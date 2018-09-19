package timer;

import Global.Global;
import Interrupt.*;


public class timer extends Thread{
	 private static int AllTime;
	 private static int RRTime;
	 private static int RRCount;
	 private static int sleepFlag;//sleepFlag=1时睡眠
	 
	 public static String AllTimeStr;
	 public static String RRTimeStr;
	 public static String RRCountStr;
	 
	 private static InterHandler handler=new InterHandler();
	 
	 public static boolean setsleepFlag(int x)
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
		 
		 while(Global.Power) {
			 while(sleepFlag==1) {
				 try {
					sleep(10);
					System.out.println("时间片正在休息");
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
				//触发中断
				InterHandler.timeINTR(InterType.TIMEOUT);
				RRCount++;
				
				//置零
				RRTime=0;
				//RRTimeStr=String.valueOf(RRTime);
				
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(InterHandler.output());
			AllTimeStr=String.valueOf(AllTime);
			RRTimeStr=String.valueOf(RRTime);
			RRCountStr=String.valueOf(RRCount);
			System.out.println("给页面的！！！！！！！！！！！！"+AllTimeStr);
			System.out.println("当前时间"+AllTime+"  当前时间片计时"+RRTime+"  当前时间片"+RRCount);
		 }
	 }
	
	 
}
