/**
 * 
 */
package Device;

import java.awt.List;
import java.awt.print.Printable;
import java.util.HashMap;

/**
 * @author 45044
 *
 */
public class SDT {
	//private DevType devType;
	//private int devID;
	
	
	private final int MAX_SIZE = 20;
	private int devCount = 0;	//系统当前的设备总数<=20
	private HashMap<DevType, DCT> sysDevTb;
	
	public SDT(HashMap<DevType, DCT> sysDevTb) {//初始化一个系统设备表
		// TODO Auto-generated constructor stub
		this.sysDevTb = sysDevTb;
	}
	
	public Boolean addDeviceIntoSystem(DevTb devTb) {
		//DCT work = new DCT(devType);
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
	
	public Boolean deleteDeviceFromSystem(int devID, DevType devType) {
		DCT dct = sysDevTb.get(devType);	//获取删除设备类型的设备控制表
		if(dct.delDev(devID)) {
			System.out.println("The device"+devID+"was deleted");
			this.devCount--;
			return true;
		}
		//System.out.println("");
		return false;
	}
	
	public  HashMap<Integer, Integer> allocateFreeDevice(DevType devType, int needCount, int belongProID) {//获取空闲设备
		//int availCount = 0;
		DCT dct = this.sysDevTb.get(devType);
		HashMap<Integer, Integer> allocateMap = dct.allocateFreeDevice(needCount, belongProID);
		System.out.println("success allocate in SDT,device:"+allocateMap);
		return allocateMap;
	}
	
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
	}//??
	
	public int getDevIDByDevTpyeAndProID(int proID, DevType devType) {
		DCT dct = this.sysDevTb.get(devType);
		int devID = dct.getDevIDByProID(proID);
		return devID;
	}
	
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
	
	public void showEntireDevState() {
		DCT dct;
		dct = this.sysDevTb.get(DevType.PRINTER);
		dct.showDevState();
		dct = this.sysDevTb.get(DevType.KEYBOARD);
		dct.showDevState();
		dct = this.sysDevTb.get(DevType.AUDIO);
		dct.showDevState();
		dct = this.sysDevTb.get(DevType.MICROPHONE);
		dct.showDevState();
		dct = this.sysDevTb.get(DevType.DISK);
		dct.showDevState();
	}
	//public int getBelongProID(int devID, DevType devType) {
		//DCT dct = this.sysDevTb.get(devType);
		//int pro
	//}
	//public int getInferDevCount(DevType devType) {
	//	DCT dct = this.sysDevTb.get(devType);
		
	//}
}
