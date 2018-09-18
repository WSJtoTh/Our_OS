/**
 * 
 */
package Device;

import java.net.StandardSocketOptions;
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
public class Microphone implements Runnable {
	private final int RANGE = 3;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private int belongDevID;//线程所属的设备
	private int belongProID;
	private String data;
	public Microphone(int devID, int proID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.belongProID = proID;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		this.data = "data from microphone";
		System.out.println("设备"+devID+"运行线程创建");
	}
//???
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//InterHandler interHandler = new InterHandler();
		System.out.println("Microphone"+this.belongDevID+"is running.");
		System.out.println("Occupied by process"+this.belongProID);
		try {
			Thread.sleep(this.runTime*1000);
			System.out.println("The device"+this.belongDevID+"finished");
			System.out.println("调中断之前输出一下进程号："+this.belongProID);
			InterHandler.devINTR(InterType.MICROPHONEINT, this.belongDevID, this.belongProID, SignalType.READ);
			int i = DevController.getRegister();
			while(i  != this.belongDevID) {
				System.out.println("当前response寄存器内的值："+i);				
				//System.out.println("Microphone"+this.belongDevID+"INTR wasn't accept by CPU");
				i = DevController.getRegister();
				Thread.sleep(this.runTime*100);
				System.out.println("Microphone"+this.belongDevID+"resend INTR");
				//System.out.println("调中断之前输出一下进程号："+this.belongProID);
				i = DevController.getRegister();
				InterHandler.devINTR(InterType.MICROPHONEINT, this.belongDevID, this.belongProID, SignalType.READ);
			}
			
			Global.databus = data+this.belongDevID;
			Register.responseINTRIDReg = -this.belongDevID;
			System.out.println("CPU accept Microphone"+this.belongDevID+"'s INTR");
			DevController.clearRegister(this.belongDevID, this.belongProID);
			System.out.println("before setIsResponse, register="+DevController.getRegister());
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
