package Device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Global.Global;

public class TestMain {
    public static void main(String args[]) throws IOException, InterruptedException { 
        try {
        	System.out.println("Hello World!"); 
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	        //String str = null; 
	        //System.out.println("Enter your value:"); 
	        //str = br.readLine(); 
	        //System.out.println("your value is :"+str); 
	        DevController devController = new DevController();
	        int[] total = DevController.getEntireDevTable();
	        int proID = 713;
	        for(int i = 0;i < 5;i++) {
	        	System.out.println(total[i]);
	        }
	        //System.out.println("input ");
	        //str = br.readLine();
	        devController.startDevController();
	        int[] entire = DevController.getEntireDevTable();
	        for(int i = 0;i<5;i++) {
	        	System.out.println(entire[i]);
	        }
	        
	        /*打印机测试
	        if(DevController.wait(DevType.PRINTER, proID)) {
	        	System.out.println("success to wait printer");
	        }
	        Global.databus = "for printer";
	        if(DevController.sendCMD(SignalType.WRITE, DevType.PRINTER, proID)) {
	        	System.out.println("start device");
	        }
	        Thread.sleep(5000);
	        int devID = DevController.sdt.getDevIDByDevTpyeAndProID(proID, DevType.PRINTER);
	        DevController.responseINTR(devID, DevType.PRINTER);
	        Thread.sleep(1000);
	        DevController.signal(DevType.PRINTER, proID);
	        
	        */
	        
	        /*keybord test
	        if(DevController.wait(DevType.KEYBOARD, proID)) {
	        	System.out.println("success to wait keyboard");
	        }
	        if(DevController.sendCMD(SignalType.READ, DevType.KEYBOARD, proID)) {
	        	System.out.println("start device");
	        }
	        Thread.sleep(5000);
	        int devID = DevController.sdt.getDevIDByDevTpyeAndProID(proID, DevType.KEYBOARD);
	        DevController.responseINTR(devID, DevType.KEYBOARD);
	        Thread.sleep(10000);
	        System.out.println(Global.databus);
	        DevController.signal(DevType.KEYBOARD, proID);
	        */
	        
	        /*Audio
	        if(DevController.wait(DevType.AUDIO, proID)) {
	        	System.out.println("success to wait micro");
	        }
	        Global.databus = "for audio";
	        if(DevController.sendCMD(SignalType.WRITE, DevType.AUDIO, proID)) {
	        	System.out.println("start device");
	        }
	        Thread.sleep(5000);
	        int devID = DevController.sdt.getDevIDByDevTpyeAndProID(proID, DevType.AUDIO);
	        DevController.responseINTR(devID, DevType.AUDIO);
	        Thread.sleep(1000);
	        DevController.signal(DevType.AUDIO, proID);
	        */
	        
	        
	        /*Disk test
	        if(DevController.wait(DevType.DISK, proID)) {
	        	System.out.println("success to wait disk");
	        }
	        Global.databus = "for disk";
	        if(DevController.sendCMD(SignalType.WRITE, DevType.DISK, proID)) {
	        	System.out.println("start device");
	        }
	        Thread.sleep(5000);
	        int devID = DevController.sdt.getDevIDByDevTpyeAndProID(proID, DevType.DISK);
	        DevController.responseINTR(devID, DevType.DISK);
	        Thread.sleep(1000);
	        DevController.signal(DevType.DISK, proID);
	        
	        
	        
	        if(DevController.wait(DevType.DISK, proID)) {
	        	System.out.println("success to wait disk");
	        }
	        if(DevController.sendCMD(SignalType.READ, DevType.DISK, proID)) {
	        	System.out.println("start device");
	        }
	        Thread.sleep(5000);
	        devID = DevController.sdt.getDevIDByDevTpyeAndProID(proID, DevType.DISK);
	        DevController.responseINTR(devID, DevType.DISK);
	        Thread.sleep(10000);
	        System.out.println(Global.databus);
	        DevController.signal(DevType.DISK, proID);
	        */
	        entire = DevController.getEntireDevTable();
	        for(int i = 0;i<5;i++) {
	        	System.out.println(entire[i]);
	        }
	        DevController.addDevice(DevType.KEYBOARD);
	        DevController.addDevice(DevType.DISK);
	        DevController.addDevice(DevType.AUDIO);
	        DevController.addDevice(DevType.PRINTER);
	        entire = DevController.getEntireDevTable();
	        for(int i = 0;i<5;i++) {
	        	System.out.println(entire[i]);
	        }
	        DevController.delDevice(DevType.KEYBOARD, 5);
	        entire = DevController.getEntireDevTable();
	        for(int i = 0;i<5;i++) {
	        	System.out.println(entire[i]);
	        }
        }catch (Exception e) {
        
			// TODO: handle exception
        	System.out.println("Wrong");
		}
    	
        
    } 

}
