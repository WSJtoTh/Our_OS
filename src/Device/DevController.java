/**
 * 
 */
package Device;

import java.awt.List;
import java.util.HashMap;

/**
 * @author 45044
 *
 */
public class DevController implements Runnable{
	
	
	private SDT sdt;
	//private final int initialDevNum = 5;
	private HashMap<Integer, DevIDState> devIDTable;
	private Thread devConThread;
	private Boolean POWER = true;
	private SignalReg signalReg;
	@Override
	public void run() {
		//Boolean responseINTR;
		//Boolean askAvailDev;
		//Boolean CMD;
		// TODO Auto-generated method stub
		while(POWER) {
			try {
				Thread.sleep(100);
				
				//responseINTR = ;
				//askAvailDev = ;
				//CMD = ;
				//检测中断响应寄存器
				if(this.signalReg.testResponseINTRIDReg()) {
					int devID = this.signalReg.getResponseINTRIDReg();
					DevType devType = this.signalReg.getResponseINTRDevType();
					this.sdt.freeBusyDevice(devType, devID);
				}
				
				if(this.signalReg.testCMDReg()) {
					
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//每个0.1秒进行一次检测
			
		}
	}
	
	
	public DevController() {
		// TODO Auto-generated constructor stub
		this.sdt = new SDT();
		this.signalReg = new SignalReg();
		this.initDeviceIDTable();
		this.initDevice();
	}
	
	public void startDevController() {
		this.devConThread = new Thread(this,"DeviceControllerThread");
		System.out.println(("Create the DeviceController thread"));
		this.devConThread.start();
		System.out.println("DeviceController starts");
		
	}
	
	/*
	 * 初始化设备ID维护表
	 */
	private void initDeviceIDTable() {
		for(int i = 0;i < this.sdt.getMaxSize();i++) {
			devIDTable.put(i, DevIDState.VALID);
		}
	}
	
	/*
	 * 初始化系统设备
	 */
	private void initDevice() {
		DevTb devTb = this.newDevice(DevType.AUDIO);
		this.sdt.addDeviceIntoSystem(devTb);
		devTb = this.newDevice(DevType.DISK);
		this.sdt.addDeviceIntoSystem(devTb);
		devTb = this.newDevice(DevType.KEYBOARD);
		this.sdt.addDeviceIntoSystem(devTb);
		devTb = this.newDevice(DevType.MICROPHONE);
		this.sdt.addDeviceIntoSystem(devTb);
		devTb = this.newDevice(DevType.PRINTER);
		this.sdt.addDeviceIntoSystem(devTb);
		System.out.println("Initialize SDT finished"+"Now there are "+sdt.getDevCount()+"outer devices in system");
		
	}
	
	
	/*
	 *初始化一个新设备 
	 */
	private DevTb newDevice(DevType devType) {
		DevTb devTb;
		for(int i = 0;i < this.sdt.getMaxSize();i++) {
			if(devIDTable.get(i) == DevIDState.VALID) {//如果这个设备ID号有效，就将设备与这个ID绑定
				devTb = new DevTb(devType, DevState.FREE, i);
				devIDTable.put(i, DevIDState.INVALID);
				System.out.println("Create a"+devType+"object succuss");
				return devTb;			
				}
		}
		System.out.println("There is no valid Device ID to be distributed for a new "+devType);
		return null;
	}
//
	/*
	 * 中断响应函数
	 * 由中断处理机调用
	 */
	public Boolean responseINTR(int INTRID, DevType devType) {
		this.signalReg.setResponseINTRIDReg(SignalType.INTR, INTRID, devType);
		System.out.println("Receive INTR response"+INTRID);
		return true;
	}
	
	/*
	 * 下达命令函数
	 * 由CPU调用
	 */
	public Boolean sendCMD(SignalType signalType, DevType devType, int proID) {
		/*
		 * 
		 this.signalReg.setCMDReg(signalType, devType, proID);
		System.out.println("Receive CMD from CPU"+signalType);
		 */
		
		int devID = this.sdt.getDevIDByDevTpyeAndProID(proID, devType);
		switch (devType) {
		case PRINTER://启动打印机
			Printer printer = new Printer(devID);
			printer.start();
			break;

		default:
			break;
		}
		return true;
	}
	
	/*
	 * 获取系统中各类设备的空闲设备数量
	 * 由CPU调用
	 * 
	 */
	public int[] getAvailDevTable() {
		int[] availTable = new int[5];
		availTable = this.sdt.getAvailDevCountSortByType();
		return availTable;
	}
	
	/*
	 * 获取系统中各类设备的总数
	 * 由CPU调用
	 *
	 */
	public int[] getEntireDevTable() {
		int[] entireTable = new int[5];
		entireTable = this.sdt.getEntireDevCount();
		return entireTable;
	}
	
	
	
}
