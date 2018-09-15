/**
 * 
 */
package Device;

/**
 * @author 45044
 *
 */
public class SignalReg {
	private SignalType cmdType;
	private DevType devType;
	private int proID;
	
	private SignalType responseType;
	private int responseINTRIDReg;
	private DevType responseINTRDevType;
	//中断响应寄存器
	//"-1":无中断响应
	
	private SignalType askType;
	//private int askAvailDeviceReg;
	//请求系统设备状态信息寄存器
	//"-1":无请求
	//>0:其值表示响应的中断号
	
	
	public SignalReg() {
		// TODO Auto-generated constructor stub
		this.cmdType = SignalType.NONE;
		this.devType = DevType.DEFAULT;
		this.proID = 0;
		
		this.askType = SignalType.NONE;
		//this.askAvailDeviceReg = -1;
		
		this.responseType = SignalType.NONE;
		this.responseINTRIDReg = -1;
	}
	
	public void setCMDReg(SignalType cmdType, DevType devType,int proID) {
		this.proID = proID;
		this.cmdType = cmdType;
		this.devType = devType;
	}
	
	public Boolean testCMDReg() {
		if(this.cmdType == SignalType.NONE) {
			System.out.println("CMDReg is null");
			return false;
		}
		else {
			System.out.println("CMDReg get a CMD");
			return true;
		}
	}
	
	public void clearCMDReg() {
		this.cmdType = SignalType.NONE;
		this.devType = DevType.DEFAULT;
		this.proID = 0;
		System.out.println("CMDReg has been cleared");
	}
	
	public SignalType getCMDType() {
		return this.cmdType;
	}
	
	public DevType getDevType() {
		return this.devType;
	}
	
	public int getProID() {
		return this.proID;
	}
	
	public void setResponseINTRIDReg(SignalType responseType,int INTRID, DevType devType) {
		this.responseType = responseType;
		this.responseINTRIDReg = INTRID;
		this.responseINTRDevType = devType;
	}
	
	public int getResponseINTRIDReg() {
		return this.responseINTRIDReg;
	}
	
	public DevType getResponseINTRDevType() {
		return this.responseINTRDevType;
	}
	
	public Boolean testResponseINTRIDReg() {
		if(this.responseType == SignalType.INTR) {
			System.out.println("Receive answer of INTR:"+this.responseINTRIDReg);
			return true;
		}
		else {
			System.out.println("resposeINTRReg is null");
			return false;
		}
	}

	public void clearResponseINTRIDReg() {
		this.responseINTRIDReg = 0;
		this.responseType = SignalType.NONE;
	}
	
	public void setAskAvailDeviceReg(SignalType askType) {
		//this.askAvailDeviceReg = 1;
		this.askType = askType;
	}
	
	public Boolean testAskAvailDeviceReg() {
		if(this.askType == SignalType.ASKDEV) {
			System.out.println("CPU send a CMD asks for system devices states ");
			return true;
		}
		else {
			System.out.println("askReg is null");
			return false;
		}
	}
	
	public void clearAskAvailDeviceReg() {
		this.askType = SignalType.NONE;
	}
}
