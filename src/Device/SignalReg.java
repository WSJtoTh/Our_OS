/**
 * 
 */
package Device;

/**
 * @author 45044
 *
 */
public class SignalReg {
	private static SignalType cmdType;
	private static DevType devType;
	private static int proID;
	private static int devID;
	
	private static SignalType responseType;
	private static int responseINTRIDReg;
	private static DevType responseINTRDevType;
	//中断响应寄存器
	//"-1":无中断响应
	
	private static SignalType askType;
	//private int askAvailDeviceReg;
	//请求系统设备状态信息寄存器
	//"-1":无请求
	//>0:其值表示响应的中断号
	
	
	public SignalReg() {
		// TODO Auto-generated constructor stub
		cmdType = SignalType.NONE;
		devType = DevType.DEFAULT;
		proID = 0;
		
		askType = SignalType.NONE;
		//this.askAvailDeviceReg = -1;
		
		responseType = SignalType.NONE;
		responseINTRIDReg = -1;
	}
	
	public void setCMDReg(SignalType cmdType1, DevType devType1,int proID1, int devID1) {
		proID = proID1;
		cmdType = cmdType1;
		devType = devType1;
		devID = devID1;
	}
	
	public static int getStatic() {
		return devID;
	}
	
	public Boolean testCMDReg() {
		if(cmdType == SignalType.NONE) {
			System.out.println("CMDReg is null");
			return false;
		}
		else {
			System.out.println("CMDReg get a CMD");
			return true;
		}
	}
	
	public void clearCMDReg() {
		cmdType = SignalType.NONE;
		devType = DevType.DEFAULT;
		proID = 0;
		System.out.println("CMDReg has been cleared");
	}
	
	public SignalType getCMDType() {
		return cmdType;
	}
	
	public DevType getDevType() {
		return devType;
	}
	
	public int getProID() {
		return proID;
	}
	
	public void setResponseINTRIDReg(SignalType responseType1,int INTRID, DevType devType) {
		responseType = responseType1;
		responseINTRIDReg = INTRID;
		responseINTRDevType = devType;
		//System.out.println("Reg value:"+responseINTRIDReg);
		//System.out.println("INTRID"+INTRID);
	}
	
	public int getResponseINTRIDReg() {
		return responseINTRIDReg;
	}
	
	public DevType getResponseINTRDevType() {
		return responseINTRDevType;
	}
	//??
	public Boolean testResponseINTRIDReg() {
		if(responseType == SignalType.INTR) {
			System.out.println("Receive answer of INTR:"+responseINTRIDReg);
			return true;
		}
		else {
			System.out.println("resposeINTRReg is null");
			return false;
		}
	}

	public void clearResponseINTRIDReg() {
		responseINTRIDReg = -1;
		responseType = SignalType.NONE;
	}
	
	public void setAskAvailDeviceReg(SignalType askType1) {
		//this.askAvailDeviceReg = 1;
		askType = askType1;
	}
	
	public Boolean testAskAvailDeviceReg() {
		if(askType == SignalType.ASKDEV) {
			System.out.println("CPU send a CMD asks for system devices states ");
			return true;
		}
		else {
			System.out.println("askReg is null");
			return false;
		}
	}
	
	public void clearAskAvailDeviceReg() {
		askType = SignalType.NONE;
	}
}
