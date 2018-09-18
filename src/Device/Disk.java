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
		System.out.println("Occupied by process"+this.belongProID);
	}

	
	@Override
	public void run() {
		int i = DevController.getRegister();
		// TODO Auto-generated method stub
		try {
			switch (this.signalType) {
			case WRITE:
				System.out.println("Disk"+this.belongDevID+"receive data from CPU to disk:");
				System.out.println(Global.databus);
				Thread.sleep(this.runTime*1000);
				System.out.println("调中断之前输出一下进程号："+this.belongProID);
				InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.WRITE);
				i = DevController.getRegister();
				while(i  != this.belongDevID) {
					System.out.println("当前response寄存器内的值："+i);
					
					System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					Thread.sleep(this.runTime*1000);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					System.out.println("调中断之前输出一下进程号："+this.belongProID);
					InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.WRITE);
					i = DevController.getRegister();
				}
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR");
				break;
			case READ:
				Thread.sleep(this.runTime*1000);
				System.out.println("Disk"+this.belongDevID+"send data to CPU:");
				System.out.println("调中断之前输出一下进程号："+this.belongProID);
				InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.READ);
				i = DevController.getRegister();
				while(i != this.belongDevID) {
					//System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					i = DevController.getRegister();
					Thread.sleep(this.runTime*100);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					i = DevController.getRegister();
					//System.out.println("调中断之前输出一下进程号："+this.belongProID);
					InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.READ);
					
				}
				Global.databus = data+this.belongDevID;
				System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR");
				break;
			default:
				System.out.println("Disk has no such signal");
				break;
			
			}
			DevController.clearRegister(this.belongDevID, this.belongProID);
			//Register.responseINTRIDReg = -this.belongDevID;
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
