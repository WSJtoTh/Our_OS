/**
 * 
 */
package Device;

/**
 * @author 45044
 * 
 *
 */

/*
 * 
 * 存储每个设备的详细信息
 */
public class DevTb {
	private DevType devType;//设备类型
	private int devID;//设备ID
	private DevState devState;//设备状态
	private int belongProID;//所属进程ID
	
	public DevTb(DevType devType, DevState devState, int devID) {
		// TODO Auto-generated constructor stub
		this.devType = devType;
		this.devID = devID;
		this.devState = devState;
		this.belongProID = 0;
	}

	public void setDevType(DevType devType) {
		this.devType = devType;
	}
	
	public void setDevID(int devID) {
		this.devID = devID;
	}
	
	public void setDevSate(DevState devState) {
		this.devState = devState;
	}
	
	public void setBelongProID(int belongProID) {
		this.belongProID = belongProID;
	}
	
	public DevType geDevType() {
		return this.devType;
	}
	
	public int getDevID() {
		return this.devID;
	}
	
	public DevState getDevState() {
		return this.devState;
	}
	//
	public int getBelongProID() {
		return this.belongProID;
	}
}
