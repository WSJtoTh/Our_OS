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
public class Disk implements Runnable{
	private final int RANGE = 3;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private SignalType signalType;
	private int belongDevID;//线程所属的设备
	private int belongProID;
	private String data;
	public Disk(int devID, SignalType signalType, int proID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.belongProID = proID;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		this.signalType = signalType;
		this.data = "data from disk";
		System.out.println("设备"+devID+"运行线程创建");
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			switch (this.signalType) {
			case WRITE:
				System.out.println("Disk"+this.belongDevID+"receive data from CPU to disk:");
				System.out.println(Global.databus);
				Thread.sleep(this.runTime*1000);
				InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.WRITE);
				while(DevController.signalReg.getResponseINTRIDReg() != this.belongDevID) {
					System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					Thread.sleep(this.runTime*1000);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.WRITE);
				}
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR");
				break;
			case READ:
				Thread.sleep(this.runTime*1000);
				System.out.println("Disk"+this.belongDevID+"send data to CPU:");
				InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.READ);
				while(DevController.signalReg.getResponseINTRIDReg() != this.belongDevID) {
					System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					Thread.sleep(this.runTime*1000);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.READ);
				}
				Global.databus = data+this.belongDevID;
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR");
				break;
			default:
				System.out.println("Disk has no such signal");
				break;
			
			}
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
