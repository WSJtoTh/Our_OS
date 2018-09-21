/**
 * 
 */
package Device;

import java.util.Random;

import Global.Global;
/**///import Interrupt.InterHandler;
/**///import Interrupt.InterType;
import Interrupt.InterHandler;
import Interrupt.InterService;
import Interrupt.InterType;

/**
 * @author 45044
 *
 */
public class Audio implements Runnable{
	private final int RANGE = 3;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private int belongDevID;//线程所属的设备
	private int belongProID;//线程所属的进程
	private int tryCount;//尝试发送中断的次数
	private final int MAXCOUNT = 50;//最大发送中断的
	public Audio(int devID, int proID) {
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
		//InterHandler interHandler = new InterHandler();
		System.out.println("Audio"+this.belongDevID+"is running.");
		System.out.println("Occupied by process"+this.belongProID);
		try {
			System.out.println("Audio"+this.belongDevID+" receive data from CPU to print:");
			System.out.println(Global.databus);
			DevController.dataRegister = Global.databus+"\n";
			Thread.sleep(this.runTime*1000);
			System.out.println("Audio"+this.belongDevID+"finished");
			System.out.println("调中断之前输出一下进程号："+this.belongProID);
			InterHandler.devINTR(InterType.AUDIOINT, this.belongDevID, this.belongProID, SignalType.WRITE);
			int i = DevController.getRegister();
			while(i != this.belongDevID && this.tryCount < this.MAXCOUNT) {
				System.out.println("当前response寄存器内的值："+i);
				this.tryCount++;
				i = DevController.getRegister();
				//System.out.println("Audio"+this.belongDevID+"INTR wasn't accept by CPU");
				Thread.sleep(this.runTime*100);
				i = DevController.getRegister();
				//System.out.println("Audio"+this.belongDevID+"resend INTR");
				System.out.println("调中断之前输出一下进程号："+this.belongProID);
				InterHandler.devINTR(InterType.AUDIOINT, this.belongDevID, this.belongProID, SignalType.WRITE);
			}
			i = DevController.getRegister();
			if(this.tryCount < this.MAXCOUNT || i == this.belongDevID) {
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR after try:"+this.tryCount);
				InterService.setisResponse(true);
				DevController.clearRegister(this.belongDevID, this.belongProID);
				System.out.println("before setIsResponse, register="+DevController.getRegister());
				
			}
			else {
				System.out.println("tryCount > MAXCOUNT");
			}
			//Register.responseINTRIDReg = -this.belongDevID;
			
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
