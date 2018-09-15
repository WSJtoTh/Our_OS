/**
 * 
 */
package Device;

import java.util.Random;

import Global.Global;
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
	private String data;
	public Disk(int devID, SignalType signalType) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		this.signalType = signalType;
		this.data = "data from disk";
		System.out.println("设备"+devID+"运行线程创建");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InterHandler interHandler = new InterHandler();
		System.out.println("Keybord"+this.belongDevID+"is waiting for user's input.");
		try {
			switch (this.signalType) {
			case WRITE:
				System.out.println("Disk"+this.belongDevID+"receive data from CPU to disk:");
				System.out.println(Global.databus);
				Thread.sleep(this.runTime*1000);
				interHandler.devINTR(InterType.DISKINT, this.belongDevID);
				while(DevController.signalReg.getResponseINTRIDReg() != this.belongDevID) {
					System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					//Thread.sleep(this.runTime*1000);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					interHandler.devINTR(InterType.DISKINT, this.belongDevID);
				}
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR");
				break;
			case READ:
				Thread.sleep(this.runTime*1000);
				System.out.println("Disk"+this.belongDevID+"send data to CPU:");
				interHandler.devINTR(InterType.DISKINT, this.belongDevID);
				while(DevController.signalReg.getResponseINTRIDReg() != this.belongDevID) {
					System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					//Thread.sleep(this.runTime*1000);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					interHandler.devINTR(InterType.DISKINT, this.belongDevID);
				}
				Global.databus = data+this.belongDevID;
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR");
				break;
			default:
				System.out.println("Disk has no such signal");
				break;
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
