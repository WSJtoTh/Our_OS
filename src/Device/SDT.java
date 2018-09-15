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
	
	public SDT() {//初始化一个系统设备表
		// TODO Auto-generated constructor stub
		this.sysDevTb = new HashMap<>();
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
		return allocateMap;
	}
	
	public Boolean freeBusyDevice(DevType devType, int devCount, int belongProID) {
		DCT dct = this.sysDevTb.get(devType);
		if(dct.freeBusyDevice(belongProID)) {
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
}
