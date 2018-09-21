/**
 * 
 */
package Device;

import java.awt.List;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
/**
 * @author 45044
 *
 */
public class SDT {
	
	private final int MAX_SIZE = 20;//系统允许的最大设备数量
	private int devCount = 0;	//系统当前的设备总数<=20
	private HashMap<DevType, DCT> sysDevTb;//系统设备
	
	public SDT(HashMap<DevType, DCT> sysDevTb, int devCount) {//初始化一个系统设备表
		// TODO Auto-generated constructor stub
		this.sysDevTb = sysDevTb;
		this.devCount = devCount;
	}
	
	//向系统中添加一个设备
	public Boolean addDeviceIntoSystem(DevTb devTb) {
		DCT dct = sysDevTb.get(devTb.geDevType());	//获取添加设备类型的设备控制表
		if(this.devCount < MAX_SIZE && dct.addDev(devTb)) {
			this.devCount++;
			return true;
		}
		else {
			System.out.println("The system devices table is full!");
			return false;
		}
		
	}
	
	//从系统中删除一个设备
	public Boolean deleteDeviceFromSystem(int devID, DevType devType) {
		DCT dct = sysDevTb.get(devType);	//获取删除设备类型的设备控制表
		if(dct.delDev(devID)) {
			System.out.println("The device"+devID+"was deleted");
			this.devCount--;
			return true;
		}
		return false;
	}
	
	//分配空闲设备
	public  HashMap<Integer, Integer> allocateFreeDevice(DevType devType, int needCount, int belongProID) {//获取空闲设备
		//int availCount = 0;
		DCT dct = this.sysDevTb.get(devType);
		HashMap<Integer, Integer> allocateMap = dct.allocateFreeDevice(needCount, belongProID);
		if(allocateMap.isEmpty()) {
			System.out.println("fail allocate in SDT");
		}
		else {
			System.out.println("success allocate in SDT,device:"+allocateMap);
		}
		return allocateMap;
	}
	
	//释放被占用设备
	public Boolean freeBusyDevice(DevType devType, int devID) {
		DCT dct = this.sysDevTb.get(devType);
		if(dct.freeBusyDevice(devID)) {
			return true;
		}
		return false;
	}
	
	public int getMaxSize() {
		return this.MAX_SIZE;
	}
	
	public int getDevCount() {
		return this.devCount;
	}
	
	//根据进程ID和设备类型来获取设备ID
	public int getDevIDByDevTpyeAndProID(int proID, DevType devType) {
		if(devType == DevType.PRINTER || devType ==DevType.KEYBOARD || devType == DevType.AUDIO || devType == DevType.DISK || devType == DevType.MICROPHONE) {
			DCT dct = this.sysDevTb.get(devType);
			int devID = dct.getDevIDByProID(proID);
			if(devID < 0) {
				System.out.println("no device attaches the process:"+proID+" of device type:"+devType);
			}
			return devID;	
		}
		else {
			System.out.println("There is no such device type");
			return -1;
		}
		
	}
	
	//获取当前系统各类空闲设备的数量
	public int[] getAvailDevCountSortByType() {
		int[] availDev = new int[5];
		int i = 0;
		DCT dct = this.sysDevTb.get(DevType.PRINTER);
		availDev[i] = dct.getAvailDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.KEYBOARD);
		availDev[i] = dct.getAvailDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.DISK);
		availDev[i] = dct.getAvailDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.MICROPHONE);
		availDev[i] = dct.getAvailDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.AUDIO);
		availDev[i] = dct.getAvailDevCount();
		return availDev;
	}
	
	//获取当前系统各类设备的总数
	public int[] getEntireDevCount() {
		int[]	entireDev = new int[5];
		int i = 0;
		DCT dct;
		dct = this.sysDevTb.get(DevType.PRINTER);
		entireDev[i] = dct.getDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.KEYBOARD);
		entireDev[i] = dct.getDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.DISK);
		entireDev[i] = dct.getDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.MICROPHONE);
		entireDev[i] = dct.getDevCount();
		i++;
		dct = this.sysDevTb.get(DevType.AUDIO);
		entireDev[i] = dct.getDevCount();
		i++;
		
		return entireDev;
	}
	
	//展示整个系统中设备的状态
	public ArrayList<String> showEntireDevState() {
		DCT dct;
		int dctDevCount = 0;
		
		ArrayList<String> str = new ArrayList<String>();
		
		String[][] devArr = new String[this.devCount][2];
		String temp;
		int j = 0;
		
		//打印机
		dct = this.sysDevTb.get(DevType.PRINTER);
		dctDevCount = dct.getDevCount();
		String[][] pArr = new String[dctDevCount][2];
		pArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[j][0] = String.valueOf(DevType.PRINTER)+":"+pArr[i][0];
			devArr[j][1] = pArr[i][1];
			temp = devArr[j][0]+" "+devArr[j][1];
			str.add(temp);
		}
		//键盘
		dct = this.sysDevTb.get(DevType.KEYBOARD);
		dctDevCount = dct.getDevCount();
		String[][] kArr = new String[dctDevCount][2];
		kArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[j][0] = String.valueOf(DevType.KEYBOARD)+":"+kArr[i][0];
			devArr[j][1] = kArr[i][1];
			temp = devArr[j][0]+" "+devArr[j][1];
			str.add(temp);
		}
		//音箱
		dct = this.sysDevTb.get(DevType.AUDIO);
		dctDevCount = dct.getDevCount();
		String[][] aArr = new String[dctDevCount][2];
		aArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[j][0] = String.valueOf(DevType.AUDIO)+":"+aArr[i][0];
			devArr[j][1] = aArr[i][1];
			temp = devArr[j][0]+" "+devArr[j][1];
			str.add(temp);
		}
		//麦克风
		dct = this.sysDevTb.get(DevType.MICROPHONE);
		dctDevCount = dct.getDevCount();
		String[][] mArr = new String[dctDevCount][2];
		mArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[j][0] = String.valueOf(DevType.MICROPHONE)+":"+mArr[i][0];
			devArr[j][1] = mArr[i][1];
			temp = devArr[j][0]+" "+devArr[j][1];
			str.add(temp);
		}
		//磁盘
		dct = this.sysDevTb.get(DevType.DISK);
		dctDevCount = dct.getDevCount();
		String[][] dArr = new String[dctDevCount][2];
		dArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[j][0] = String.valueOf(DevType.DISK)+":"+dArr[i][0];
			devArr[j][1] = dArr[i][1];
			temp = devArr[j][0]+" "+devArr[j][1];
			str.add(temp);
		}
		
		return str;
	}
}
