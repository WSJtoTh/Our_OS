/**
 * 
 */
package Device;

import java.awt.List;
import java.beans.IntrospectionException;
import java.util.HashMap;

import Global.Global;
import pro.ins;

/**
 * @author 45044
 *
 */
public class DevController implements Runnable{
	
	/**////
	private static HashMap<Integer, DevIDState> INTRIDTable;
	public static SDT sdt;
	private static int register;
	private final static int IDRange = 20;
	//private final int initialDevNum = 5;
	private static HashMap<Integer, DevIDState> devIDTable;
	private Thread devConThread;
	
	//private Boolean POWER = true;
	public static SignalReg signalReg = new SignalReg();
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
		//sdt = new SDT();
		this.devIDTable = new HashMap<>();
		System.out.println("DevID table init");
		this.initDeviceIDTable();
		System.out.println("device init");
		HashMap<DevType, DCT> initTable = this.initDevice();
		System.out.println("sdt init");
		sdt = new SDT(initTable);
		System.out.println("signal");
		register = -100;
		this.initINTRIDTable();
		//signalReg = new SignalReg();
		//System.out.println("finish devCon init");
		
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
		System.out.println("enter DevID table init function");
		//System.out.println("sdt maxsize:"+sdt.getMaxSize());
		for(int i = 0;i < IDRange;i++) {
			
			devIDTable.put(i, DevIDState.VALID);
			System.out.println("DevID table"+i+devIDTable.get(i));
		}
		System.out.println("finish DevID table init");
	}
	
	public static DevIDState getINTRState(int devID) {
		DevIDState state = DevIDState.WRONG;
		for(int i = 0;i < IDRange;i++) {
			if(devID == i) {
				state = INTRIDTable.get(i);
			}
		}
		return state;
	}
	
	private void initINTRIDTable() {
		INTRIDTable = new HashMap<>();
		System.out.println("enter INTRID table init function");
		for(int i = 0;i < IDRange;i++) {
			INTRIDTable.put(i, DevIDState.SHIELD);
			System.out.println("INTRID table"+i+INTRIDTable.get(i));
		}
		System.out.println("finish INTRID table init");
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
		System.out.println("！！！！！！");
		dct = new DCT(DevType.DISK);
		devTb = new DevTb(DevType.DISK, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.DISK, dct);
		System.out.println("！！！！！！");
		dct = new DCT(DevType.KEYBOARD);
		devTb = new DevTb(DevType.KEYBOARD, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.KEYBOARD, dct);
		System.out.println("！！！！！！");
		dct = new DCT(DevType.MICROPHONE);
		devTb = new DevTb(DevType.MICROPHONE, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.MICROPHONE, dct);
		System.out.println("！！！！！！");
		dct = new DCT(DevType.PRINTER);
		devTb = new DevTb(DevType.PRINTER, DevState.FREE, i);
		devIDTable.put(i, DevIDState.INVALID);
		dct.addDev(devTb);
		i++;
		initTable.put(DevType.PRINTER, dct);
		System.out.println("！！！！！！");
		//System.out.println("Initialize SDT finished"+"Now there are "+sdt.getDevCount()+"outer devices in system");
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
//
	public static Boolean delDevice(DevType devType, int devID) {
		if(sdt.deleteDeviceFromSystem(devID, devType)) {
			System.out.println("Device "+devID+" is deleted");
			return true;
		}
		return false;
	}
	
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
	
	public static int getRegister() {
		return register;
	}
	
	public static void clearRegister(int devID, int proID) {
		register = -100;
		System.out.println("device: "+devID+"occupied by process: "+proID+"release the register");
		//return true;
	}
	/*
	 * 中断响应函数
	 * 由中断处理机调用
	 * 
	 */
	public static Boolean responseINTR(int INTRID, DevType devType) {
		//signalReg.setResponseINTRIDReg(SignalType.INTR, INTRID, devType);
		//Register.responseINTRIDReg = INTRID;
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
		
		Register.responseDevType = devType;
		//System.out.println("register:"+Register.responseINTRIDReg);
		//System.out.println("Receive INTR response"+INTRID);
		//System.out.println("Receive INTR response"+signalReg.getResponseINTRIDReg());
		return true;
	}
	
	/*
	 * 下达命令函数
	 * 由CPU调用
	 */
	public static Boolean sendCMD(SignalType signalType, DevType devType, int proID) {
		/*
		 * 
		 this.signalReg.setCMDReg(signalType, devType, proID);
		System.out.println("Receive CMD from CPU"+signalType);
		 */
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
	
	public static int getRegValue() {
		int i;
		i = signalReg.getResponseINTRIDReg();
		System.out.println("Reg:"+i);
		return i;
	}
	
}
