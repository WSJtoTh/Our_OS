/**
 * 
 */
package Device;

import java.awt.List;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;

import Global.Global;
import pro.ins;

/**
 * @author 
 * name:万诗婕
 * class:2015211312
 * ID:2015211488
 *
 */
public class DevController implements Runnable{

	public static SDT sdt;
	public static String dataRegister;
	private static int register;
	private final static int IDRange = 20;
	private static HashMap<Integer, DevIDState> devIDTable;
	private Thread devConThread;
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(Global.Power) {
			try {
				System.out.println("devCon is working");
				Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//每个0.1秒进行一次检测
			
		}
	}
	
	
	public DevController() {
		// TODO Auto-generated constructor stub
		System.out.println("devicecontroller init");
		devIDTable = new HashMap<>();
		System.out.println("DevID table init");
		this.initDeviceIDTable();
		System.out.println("device init");
		HashMap<DevType, DCT> initTable = this.initDevice();
		System.out.println("sdt init");
		sdt = new SDT(initTable,5);
		System.out.println("signal");
		register = -100;
		dataRegister = "";
		
	}
	
	
	
	/*
	 * 初始化设备ID维护表
	 */
	private void initDeviceIDTable() {
		System.out.println("enter DevID table init function");
		for(int i = 0;i < IDRange;i++) {
			
			devIDTable.put(i, DevIDState.VALID);
			System.out.println("DevID table"+i+devIDTable.get(i));
		}
		System.out.println("finish DevID table init");
	}
	

	/*
	 * 初始化系统设备
	 */
	private HashMap<DevType, DCT> initDevice() {
		HashMap<DevType, DCT> initTable = new HashMap<>();
		int i = 0;
		DCT dct = new DCT(DevType.AUDIO);
		DevTb devTb = new DevTb(DevType.AUDIO, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.AUDIO, dct);
		dct = new DCT(DevType.DISK);
		devTb = new DevTb(DevType.DISK, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.DISK, dct);
		dct = new DCT(DevType.KEYBOARD);
		devTb = new DevTb(DevType.KEYBOARD, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.KEYBOARD, dct);
		dct = new DCT(DevType.MICROPHONE);
		devTb = new DevTb(DevType.MICROPHONE, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.MICROPHONE, dct);
		dct = new DCT(DevType.PRINTER);
		devTb = new DevTb(DevType.PRINTER, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.PRINTER, dct);
		return initTable;
	}
	
	
	/*
	 *往系统中增加一个设备
	 *界面调用
	 */
	public static DevTb addDevice(DevType devType) {
		DevTb devTb;
		int devID = findAnValidDevID();
		if(devID > 0) {
			devTb = new DevTb(devType, DevState.FREE, devID);
			devIDTable.put(devID, DevIDState.INVALID);
			sdt.addDeviceIntoSystem(devTb);
			return devTb;
		}
		return null;
	}
	
	/*
	 * 删除一个系统中已有的设备
	 * 
	 */
	public static Boolean delDevice(String devStr) {
		String[] str = devStr.split(":");
		String devTypeStr = str[0];
		DevType devType;
		int devID = Integer.valueOf(str[1]);
		
		switch (devTypeStr) {
		case "PRINTER":
			devType = DevType.PRINTER;
			break;
		case "KEYBOARD":
			devType = DevType.KEYBOARD;
			break;
		case "DISK":
			devType = DevType.DISK;
			break;
		case "MICROPHONE":
			devType = DevType.MICROPHONE;
			break;
		case "AUDIO":
			devType = DevType.AUDIO;
			break;
		default:
			System.out.println("没有这样的设备！！！");
			devType = DevType.DEFAULT;
			break;
		}
		System.out.println("devType:"+devType+"\tdevID:"+devID);
		if(sdt.deleteDeviceFromSystem(devID, devType)) {
			System.out.println("Device "+devID+" is deleted");
			return true;
		}
		return false;
	}
	
	/*
	 * 在系统设备ID表中找到一个没有被占用的表
	 * 由DevController初始化和新增设备函数调用
	 */
	private static int findAnValidDevID() {
		int devID = -1;
		for(int i = 0;i < IDRange;i++) {
			if(devIDTable.get(i) == DevIDState.VALID) {
				devID = i;
				System.out.println("find a valid ID"+i);
				break;
			}
		}
		return devID;
	}
	
	/*
	 * 获取register里的值
	 */
	public static int getRegister() {
		return register;
	}
	
	/*
	 * 将寄存器清空
	 * 由各个设备设备调用
	 */
	public static void clearRegister(int devID, int proID) {
		register = -100;
		System.out.println("device: "+devID+"occupied by process: "+proID+"release the register");

	}
	
	
/*****************************************
 * 
 * 以下为对外接口函数
 * 
 * 
 ***************************************** 	*/
	
	/*
	 * 启动DevController
	 * 由CPU调用
	 * 
	 */
	public void startDevController() {
		this.devConThread = new Thread(this,"DeviceControllerThread");
		System.out.println(("Create the DeviceController thread"));
		this.devConThread.start();
		System.out.println("DeviceController starts");
		
	}
	
	/*
	 * CPU申请设备
	 * 由CPU调用
	 *
	 */
	public static Boolean wait(DevType devType, int proID) {
		
		System.out.println("Process"+proID+" waits for device "+devType);
		HashMap<Integer, Integer> allocate = sdt.allocateFreeDevice(devType, 1, proID);
		if(allocate.isEmpty()) {
			System.out.println("wait failed");
			return false;
		}else {
			System.out.println("{ProID=DevID}");
			System.out.println("wait successfully of device:"+allocate);
			return true;
		}
	}
	
	/*
	 * CPU释放设备
	 * 由CPU调用
	 */
	public static Boolean signal(DevType devType, int proID) {
		//register = -100;
		int devID = sdt.getDevIDByDevTpyeAndProID(proID, devType);
		if(devID < 0) {
			System.out.println("in signal ,the process doesn't own this type of device(process:"+proID+" devType:"+devType+")");
			return false;
		}
		else {
			int[] dev = sdt.getAvailDevCountSortByType();
			for(int i = 0;i < 5;i++) {
				System.out.println(dev[i]);
			}
			sdt.freeBusyDevice(devType, devID);
			dev = sdt.getAvailDevCountSortByType();
			for(int i = 0;i < 5;i++) {
				System.out.println(dev[i]);
			}
			return true;
		}
		
	}
	

	
	/*
	 * 中断响应函数
	 * 由中断处理机调用
	 * 
	 */
	public static Boolean responseINTR(int INTRID, DevType devType) {
		if(register == -100) {
			register = INTRID;
			System.out.println("Receive INTR of"+INTRID);
		}
		else {
			System.out.println("Register is occupied");
		}		
		int s = 100;
		try {
			Thread.sleep(s);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}
	
	/*
	 * 下达命令函数
	 * 由CPU调用
	 */
	public static Boolean sendCMD(SignalType signalType, DevType devType, int proID) {

		System.out.println("in sendCMD");
		System.out.println("the devcie type is:"+devType);
		System.out.println("the proID is"+proID);
		sdt.showEntireDevState();
		int devID = sdt.getDevIDByDevTpyeAndProID(proID, devType);
		if(devID < 0) {
			System.out.println("The process"+proID+"doesn't have such device"+devType);
			return false;
		}
		else {
			System.out.println("the devID is"+devID);
			switch (devType) {
			case PRINTER://启动打印机
				System.out.println("ready to start PRINTER");
				Printer printer = new Printer(devID, proID);
				printer.start();
				System.out.println("start the printer");
				break;
			case KEYBOARD://启动打印机
				Keyboard keyboard = new Keyboard(devID, proID);
				keyboard.start();
				break;
			case DISK://启动打印机
				Disk disk = new Disk(devID, signalType, proID);
				disk.start();
				break;
			case MICROPHONE://启动打印机
				Microphone microphone = new Microphone(devID, proID);
				microphone.start();
				break;
			case AUDIO://启动打印机
				Audio audio = new Audio(devID, proID);
				audio.start();
				break;
			default:
				break;
			}
			return true;	
		}
		
	}
	
	/*
	 * 获取系统中各类设备的空闲设备数量
	 * 由CPU调用
	 * 
	 */
	public static int[] getAvailDevTable() {
		int[] availTable = new int[5];
		availTable = sdt.getAvailDevCountSortByType();
		System.out.println("当前空闲设备数量");
		for(int i = 0;i < 5; i++) {
			System.out.println(availTable[i]);
		}
		return availTable;
	}
	
	/*
	 * 获取系统中各类设备的总数
	 * 由CPU调用
	 *
	 */
	public static int[] getEntireDevTable() {
		int[] entireTable = new int[5];
		entireTable = sdt.getEntireDevCount();
		return entireTable;
	}
	
	/*
	 * 展示当前系统中的所有设备和设备所属进程
	 * 由界面调用
	 * 
	 */
	public static ArrayList<String> showDeviceInfo() {
		ArrayList<String> devArr = sdt.showEntireDevState();
		return devArr;
	}

	
}
