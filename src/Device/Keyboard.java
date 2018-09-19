/**
 * 
 */
package Device;

import java.util.Random;

import Global.Global;
import Interrupt.InterHandler;
import Interrupt.InterService;
import Interrupt.InterType;
/**///import Interrupt.*;
/**
 * @author 45044
 *
 */
public class Keyboard implements Runnable {
	private final int RANGE = 2;
	private Random rand; 
	private int runTime;
	private Thread thread;
	private int belongDevID;//线程所属的设备
	private int belongProID;
	private String data;
	private int tryCount;
	private final int MAXCOUNT = 50;
	public Keyboard(int devID, int proID) {
		// TODO Auto-generated constructor stub
		this.belongDevID = devID;
		this.belongProID = proID;
		this.tryCount = 0;
		this.rand = new Random();
		this.runTime = this.rand.nextInt(RANGE)+1;
		this.data = "data from keybord";
		System.out.println("设备"+devID+"运行线程创建");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//InterHandler interHandler = new InterHandler();
		System.out.println("Keybord"+this.belongDevID+"is waiting for user's input.");
		System.out.println("Occupied by process"+this.belongProID);
		try {
			Thread.sleep(this.runTime*1000);
			System.out.println("The keybord"+this.belongDevID+"has get data from users");
			System.out.println("调中断之前输出一下进程号："+this.belongProID);
			InterHandler.devINTR(InterType.KEYBOARDINT, this.belongDevID, this.belongProID, SignalType.READ);
			int i = DevController.getRegister();
			while(i != this.belongDevID && this.tryCount < this.MAXCOUNT) {
				System.out.println("当前response寄存器内的值："+i);
				this.tryCount++;
				//System.out.println("Keyboard"+this.belongDevID+"INTR wasn't accept by CPU");
				i = DevController.getRegister();
				Thread.sleep(this.runTime*100);
				i = DevController.getRegister();
				System.out.println("Keyboard"+this.belongDevID+"resend INTR");
				//System.out.println("调中断之前输出一下进程号："+this.belongProID);
				InterHandler.devINTR(InterType.KEYBOARDINT, this.belongDevID, this.belongProID, SignalType.READ);
				
			}
			i = DevController.getRegister();
			if(this.tryCount < this.MAXCOUNT || i == this.belongDevID) {
				Global.databus = data+this.belongDevID;
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
