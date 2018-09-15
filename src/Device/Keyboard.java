/**
 * 
 */
package Device;

import java.util.Random;

import Global.Global;
import Interrupt.*;
/**
 * @author 45044
 *
 */
public class Keyboard implements Runnable {
	private final int RANGE = 50;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private int belongDevID;//线程所属的设备
	private String data;
	public Keyboard(int devID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		this.data = "data from keybord";
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
			interHandler.devINTR(InterType.KEYBOARDINT, this.belongDevID);
			while(DevController.signalReg.getResponseINTRIDReg() != this.belongDevID) {
				System.out.println("Keyboard"+this.belongDevID+"INTR wasn't accept by CPU");
				//Thread.sleep(this.runTime*1000);
				System.out.println("Keyboard"+this.belongDevID+"resend INTR");
				interHandler.devINTR(InterType.KEYBOARDINT, this.belongDevID);
			}
			Global.databus = data;
			System.out.println("CPU accept Keyboard"+this.belongDevID+"'s INTR");
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
