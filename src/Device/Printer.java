/**
 * 
 */
package Device;

import java.util.Random;

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
	private final int RANGE = 10;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private int belongDevID;//线程所属的设备
	private int belongProID;
	public Printer(int devID, int proID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.belongProID = proID;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		System.out.println("设备"+devID+"运行线程创建");
	}
//???
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//InterHandler interHandler = new InterHandler();
		System.out.println(this.belongDevID+"is running.It will run for"+this.runTime+"seconds");
		try {
			System.out.println("Printer"+this.belongDevID+"receive data from CPU to print:");
			System.out.println(Global.databus);
			Thread.sleep(this.runTime*1000);
			System.out.println("Prnter"+this.belongDevID+"finished");
			InterHandler.devINTR(InterType.PRINTERINT, this.belongDevID, this.belongProID, SignalType.WRITE);
			while(DevController.signalReg.getResponseINTRIDReg() != this.belongDevID) {
				System.out.println("Printer"+this.belongDevID+"INTR wasn't accept by CPU");
				Thread.sleep(this.runTime*1000);
				System.out.println("Printer"+this.belongDevID+"resend INTR");
				InterHandler.devINTR(InterType.PRINTERINT, this.belongDevID, this.belongProID, SignalType.WRITE);
			}
			System.out.println("CPU accept Printer"+this.belongDevID+"'s INTR");
			InterService.setisResponse(true);
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
