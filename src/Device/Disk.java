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
	private int tryCount;
	private final int MAXCOUNT = 50;
	public Disk(int devID, SignalType signalType, int proID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.belongProID = proID;
		this.tryCount = 0;
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
				DevController.dataRegister = Global.databus+"\n";
				Thread.sleep(this.runTime*1000);
				System.out.println("调中断之前输出一下进程号："+this.belongProID);
				InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.WRITE);
				i = DevController.getRegister();
				while(i  != this.belongDevID && this.tryCount < this.MAXCOUNT) {
					System.out.println("当前response寄存器内的值："+i);
					this.tryCount++;
					System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					Thread.sleep(this.runTime*1000);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					System.out.println("调中断之前输出一下进程号："+this.belongProID);
					InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.WRITE);
					i = DevController.getRegister();
				}
				i = DevController.getRegister();
				if(this.tryCount < this.MAXCOUNT || i == this.belongDevID) {
					System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR after try:"+this.tryCount);
					InterService.setisResponse(true);
					DevController.clearRegister(this.belongDevID, this.belongProID);
					//Register.responseINTRIDReg = -this.belongDevID;
					System.out.println("before setIsResponse, register="+DevController.getRegister());
					
				}
				else {
					System.out.println("tryCount > MAXCOUNT");
				}
				break;
			case READ:
				Thread.sleep(this.runTime*1000);
				System.out.println("Disk"+this.belongDevID+"send data to CPU:");
				System.out.println("调中断之前输出一下进程号："+this.belongProID);
				InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.READ);
				i = DevController.getRegister();
				while(i != this.belongDevID && this.tryCount < this.MAXCOUNT) {
					//System.out.println("Disk"+this.belongDevID+"INTR wasn't accept by CPU");
					this.tryCount++;
					i = DevController.getRegister();
					Thread.sleep(this.runTime*100);
					System.out.println("Disk"+this.belongDevID+"resend INTR");
					i = DevController.getRegister();
					//System.out.println("调中断之前输出一下进程号："+this.belongProID);
					InterHandler.devINTR(InterType.DISKINT, this.belongDevID, this.belongProID, SignalType.READ);
					
				}
				if(this.tryCount < this.MAXCOUNT || i == this.belongDevID) {
					Global.databus = data+this.belongDevID;
					System.out.println("CPU accept Disk"+this.belongDevID+"'s INTR after try:"+this.tryCount);
					InterService.setisResponse(true);
					DevController.clearRegister(this.belongDevID, this.belongProID);
					//Register.responseINTRIDReg = -this.belongDevID;
					System.out.println("before setIsResponse, register="+DevController.getRegister());
					
				}
				else {
					System.out.println("tryCount > MAXCOUNT");
				}
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
