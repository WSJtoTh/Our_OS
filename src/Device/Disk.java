/**
 * 
 */
package Device;

import java.util.Random;

import Interrupt.InterHandler;
import Interrupt.InterType;

/**
 * @author 45044
 *
 */
public class Disk implements Runnable{
	private final int RANGE = 50;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private SignalType signalType;
	private int belongDevID;//线程所属的设备
	public Disk(int devID, SignalType signalType) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		this.signalType = signalType;
		System.out.println("设备"+devID+"运行线程创建");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InterHandler interHandler = new InterHandler();
		System.out.println("Keybord"+this.belongDevID+"is waiting for user's input.");
		try {
			Thread.sleep(this.runTime*1000);
			System.out.println("The keybord"+this.belongDevID+"has get data from users");
			interHandler.devINTR(InterType.DISKINT, this.belongDevID);
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
