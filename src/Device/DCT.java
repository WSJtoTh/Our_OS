/**
 * 
 */
package Device;

import java.io.FileNotFoundException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import pro.ins;

/**
 * @author 45044
 *
 */
public class DCT {
	private final int MAX_SIZE = 10;
	private DevType devType;
	private int devCount;
	private int availDevCount;
	private DevTb[] deviceArray;	//存储当前种类设备的ID号
	public DCT(DevType devType) {
		// TODO Auto-generated constructor stub
		this.devType = devType;
		this.devCount = 0;
		this.availDevCount = 0;
		this.deviceArray = new DevTb[MAX_SIZE];
	}
	
	public Boolean addDev(DevTb devTb) {	//挂载设备
		if(this.devCount >= MAX_SIZE) {		//判断挂载设备是否超过最大数量
			System.out.println("The"+this.devType+"table is full(max count is"+MAX_SIZE+")");
			return false;
		}
		else {
			this.deviceArray[devCount] = devTb;
			this.devCount++;
			this.availDevCount++;
			System.out.println("The new"+this.devType+"device is added successful");
			return true;
		}
	}
	
	public Boolean delDev(int devID) {
		for(int i = 0;i < this.devCount;i++) {
			if(this.deviceArray[i].getDevID() == devID) {
				if(this.deviceArray[i].getDevState() == DevState.FREE) {
					for(int j = i;j < this.devCount-1; j++) {//从列表中删除指定的device
						this.deviceArray[j] = this.deviceArray[j+1];
					}
					this.devCount--;
					this.availDevCount--;
					return true;	
				}
				else{
					System.out.println("Fail to delete device"+devID+"\nThe devices is occupied by process"+this.deviceArray[i].getBelongProID());
					return false;
				}
			}
		}
		System.out.println("There is no such device whose device ID is"+devID);
		return false;//设备列表中
	}
	
	public HashMap<Integer, Integer> allocateFreeDevice(int needCount, int belongProID) {	//获取空闲设备的ID号
		HashMap<Integer, Integer> allocateMap = new HashMap<>();
		Boolean flag = true;
		for(int i = 0;i < this.devCount;i++) {
			if(this.deviceArray[i].getDevState() == DevState.BUSY && this.deviceArray[i].getBelongProID() == belongProID) {
				flag = false;
				System.out.println("The process has been allocated this kind of device before");
			}
		}
		if(flag) {
			if(this.availDevCount >= needCount) {
				for(int i = 0;i < this.devCount; i++) {
					
					if(this.deviceArray[i].getDevState() == DevState.FREE) {//分配空闲设备
						this.deviceArray[i].setDevSate(DevState.BUSY);
						this.deviceArray[i].setBelongProID(belongProID);
						allocateMap.put(belongProID, this.deviceArray[i].getDevID());
						this.availDevCount--;
						System.out.println("success to allocate the device:"+this.deviceArray[i].getDevID());
						break;
					}
				}	
				//return allocateMap;
			}
			else {
				System.out.println(this.devType+"has no enough devices");
				//return allocateMap;
			}	
		}
		
		return allocateMap;
	}
	
	public Boolean freeBusyDevice(int devID) {
		for(int i = 0;i < this.devCount; i++) {
			if(this.deviceArray[i].getDevID()== devID) {//分配空闲设备
				this.deviceArray[i].setDevSate(DevState.FREE);
				this.deviceArray[i].setBelongProID(0);
				this.availDevCount++;
				System.out.println("device"+devID+"is freed successfully");
				return true;
			}
		}
		System.out.println("Device"+devID+"was already free");
		return false;
	}
	
	/*
	 * 返回系统中当前类型设备的总数
	 */
	public int getDevCount() {
		return this.devCount;
	}
	
	/*
	 * 返回系统中当前类型设备空闲的数量
	 */
	public int getAvailDevCount() {
		return this.availDevCount;
	}
	//???
	/*
	 * 获取被ProID占用的设备的ID
	 */
	public int getDevIDByProID(int proID) {
		int devID = -1;
		for(int i = 0;i < this.devCount; i++) {
			if(this.deviceArray[i].getBelongProID()== proID) {//分配空闲设备
				devID = this.deviceArray[i].getDevID();
				return devID;
			}
		}
		System.out.println("no device attaches th process:"+proID);
		return devID;
	}
	
	public String[][] showDevState() {
		String[][]	devArr = new String[this.devCount][2];
		int devID = 0;
		int belongID;
		//System.out.println(this.devType+"\n设备ID\t设备状态");
		for(int i = 0;i < this.devCount;i++) {
			devID =  this.deviceArray[i].getDevID();
			devArr[i][0] = String.valueOf(devID);
			belongID = this.deviceArray[i].getBelongProID();
			devArr[i][1] = String.valueOf(belongID);
			//System.out.println(devArr[i][0]+"\t"+devArr[i][1]);
		}
		return devArr;
	}
}
