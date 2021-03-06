/**
 * 
 */
package Device;

import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import Global.Global;
/**///import Interrupt.*;
import Interrupt.InterHandler;
import Interrupt.InterService;
import Interrupt.InterType;

/**
 * @author 45044
 *
 */
public class Printer implements Runnable {
	private final int RANGE = 3;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private int belongDevID;//线程所属的设备
	private int belongProID;//线程所属的进程
	private int tryCount;//尝试发送中断的次数
	private final int MAXCOUNT = 50;//最大发送中断的
	public Printer(int devID, int proID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.belongProID = proID;
		this.tryCount = 0;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		System.out.println("设备"+devID+"运行线程创建");
	}
//???
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(this.belongDevID+"is running.It will run for"+this.runTime+"seconds");
		System.out.println("Occupied by process"+this.belongProID);
		try {
			System.out.println("Printer"+this.belongDevID+"receive data from CPU to print:");
			System.out.println(Global.databus);
			DevController.dataRegister = Global.databus+"\n";//送给UI从CPU收到的
			Thread.sleep(this.runTime*1000);
			System.out.println("Prnter"+this.belongDevID+"finished");
			System.out.println("调中断之前输出一下进程号："+this.belongProID);
			InterHandler.devINTR(InterType.PRINTERINT, this.belongDevID, this.belongProID, SignalType.WRITE);
			int i = DevController.getRegister();
			while(i != this.belongDevID && this.tryCount < this.MAXCOUNT) {//通过寄存器的值判断CPU是否响应了我的中断
				System.out.println("当前response寄存器内的值："+i);
				this.tryCount++;
				i = DevController.getRegister();
				Thread.sleep(this.runTime*100);//等待随机时间片重发中断请求
				i = DevController.getRegister();
				System.out.println("Printer"+this.belongDevID+"resend INTR");
				InterHandler.devINTR(InterType.PRINTERINT, this.belongDevID, this.belongProID, SignalType.WRITE);//发送中断请求
				
			}
			i = DevController.getRegister();
			if(this.tryCount < this.MAXCOUNT || i == this.belongDevID) {//CPU响应中断后对设备进行善后处理
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR after try:"+this.tryCount);
				InterService.setisResponse(true);
				DevController.clearRegister(this.belongDevID, this.belongProID);
				System.out.println("before setIsResponse, register="+DevController.getRegister());
			}
			else {
				System.out.println("tryCount > MAXCOUNT");
			}
			//发送完成中断请求
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		if(thread == null) {
			thread = new Thread(this, String.valueOf(this.belongDevID));
			thread.start();
		}
		
	
		
	}
}
