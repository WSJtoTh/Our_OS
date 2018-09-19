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
	//private DevType devType;
	//private int devID;
	
	
	private final int MAX_SIZE = 20;
	private int devCount = 0;	//系统当前的设备总数<=20
	private HashMap<DevType, DCT> sysDevTb;
	
	public SDT(HashMap<DevType, DCT> sysDevTb, int devCount) {//初始化一个系统设备表
		// TODO Auto-generated constructor stub
		this.sysDevTb = sysDevTb;
		this.devCount = devCount;
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
		if(allocateMap.isEmpty()) {
			System.out.println("fail allocate in SDT");
		}
		else {
			System.out.println("success allocate in SDT,device:"+allocateMap);
		}
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
		//System.out.println("j="+j);
		//System.out.println("devCount="+this.devCount);
		System.out.println("设备名称\t设备状态");
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[k+j][0] = String.valueOf(DevType.PRINTER)+pArr[i][0];
			devArr[k+j][1] = pArr[i][1];
			temp = devArr[k+j][0]+" "+devArr[k+j][1];
			str.add(temp);
		}
		//System.out.println("j="+j);
		//System.out.println(devArr[j-1][0]+"\t"+devArr[j-1][1]);
		//键盘
		dct = this.sysDevTb.get(DevType.KEYBOARD);
		dctDevCount = dct.getDevCount();
		String[][] kArr = new String[dctDevCount][2];
		kArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[k+j][0] = String.valueOf(DevType.KEYBOARD)+kArr[i][0];
			devArr[k+j][1] = kArr[i][1];
			temp = devArr[k+j][0]+" "+devArr[k+j][1];
			str.add(temp);
		}
		//System.out.println("j="+j);
		//System.out.println(devArr[j-1][0]+"\t"+devArr[j-1][1]);
		//音箱
		dct = this.sysDevTb.get(DevType.AUDIO);
		dctDevCount = dct.getDevCount();
		String[][] aArr = new String[dctDevCount][2];
		aArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[k+j][0] = String.valueOf(DevType.AUDIO)+aArr[i][0];
			devArr[k+j][1] = aArr[i][1];
			temp = devArr[k+j][0]+" "+devArr[k+j][1];
			str.add(temp);
		}
		//System.out.println("j="+j);
		//System.out.println(devArr[j-1][0]+"\t"+devArr[j-1][1]);
		//麦克风
		dct = this.sysDevTb.get(DevType.MICROPHONE);
		dctDevCount = dct.getDevCount();
		String[][] mArr = new String[dctDevCount][2];
		mArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[k+j][0] = String.valueOf(DevType.MICROPHONE)+mArr[i][0];
			devArr[k+j][1] = mArr[i][1];
			temp = devArr[k+j][0]+" "+devArr[k+j][1];
			str.add(temp);
		}
		//System.out.println("j="+j);
		//System.out.println(devArr[j-1][0]+"\t"+devArr[j-1][1]);
		//磁盘
		dct = this.sysDevTb.get(DevType.DISK);
		dctDevCount = dct.getDevCount();
		String[][] dArr = new String[dctDevCount][2];
		dArr = dct.showDevState();
		for(int i = 0, k = 0;k < dctDevCount;k++,j++,i++) {
			devArr[k+j][0] = String.valueOf(DevType.DISK)+dArr[i][0];
			devArr[k+j][1] = dArr[i][1];
			temp = devArr[k+j][0]+" "+devArr[k+j][1];
			str.add(temp);
		}
		//System.out.println("j="+j);
		//System.out.println(devArr[j-1][0]+"\t"+devArr[j-1][1]);
		//for(int i = 0;i<this.devCount;i++) {
	//		System.out.println(devArr[i][0]+"\t"+devArr[i][1]);
		//}
		System.out.println("出去了");
		return str;
	}
}
