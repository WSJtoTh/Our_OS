package UI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

//import javafx.scene.control.TableColumn; 



import Device.*;
import Global.*;
import Interrupt.*;
import memory.Memory;
import pro.*;
import Process.*;
import timer.*;
import Process.*;

public class MainController extends JFrame{
	
		public static JFrame frame;
		public static JPanel os;
		public static Font font;
		public static Font font1;
		public static Font font2;
		public static Font font3;
		public static JLabel systemtimel;
		public static JTextField systemtimet;
		public static JLabel timeslicel;
		public static JTextField timeslicet;
		public static JLabel timerl;
		public static JTextField timert;
		public static JButton loginButton;
		public static JLabel memoryUsagel;
		public static JTextField memoryUsage;
		public static JLabel processStatusl;
		public static JTextArea jta;
		public static JScrollPane jsp;
		public static DefaultTableCellRenderer tcr0;
		public static JLabel promptl;
		public static JTextField prompt;
		public static JLabel intrpromptl;
		public static JTextField intrprompt;
		public static JLabel dealintrpromptl;
		public static JTextField dealintrprompt;
		public static JLabel addDevicel;
		public static JComboBox addDevicet;
		public static JButton addButton;
		public static JButton deleteButton;
		public static JButton okButton;
		public static JLabel Devicenamel;
		public static JLabel ocupyProcessl;
		public static String[] columnNames1 = {"设备名称","占用进程"};
		public static JTable table1;
		public static MyTable1 mytable1;
		public static JScrollPane scrollPane1;
		public static DefaultTableCellRenderer tcr1;
		public static JLabel Deviceidl;
		public static JLabel statusl;
		public static String[] columnNames2 = {"进程id","进程状态"};// 定义表格列名数组
        public static JTable table2;
        public static MyTable2 mytable2;
        public static JScrollPane scrollPane2;
        public static DefaultTableCellRenderer tcr2;
        
        	
        /*时间*/
        public static  String allTimeStr="";
		public static  String rrTimeStr="";
		public static  String rrCountStr="";
		
		/*message中断*/
		public static String insStr="";
		public static String interStr="";
		public static String dealintrStr="";
		public static String promptStr="";
		
		/*memory*/
		 public static String usage="";
	     public static JTable table3;
	     public static javax.swing.table.TableCellRenderer renderer;
	     public static java.awt.Component comp;
	     public static int[] usageList=new int[30];
		
		public static void reloadTable1() {
    		table1.validate();
			table1.updateUI();
    	}
	
		
        	
    	public static void reloadTable2() {
    		table2.validate();
			table2.updateUI();
    	}
	
		    public static void init() 
		    {    
		         // 创建 JFrame 实例
		         frame = new JFrame("模拟操作系统");
		         // Setting the width and height of frame
		         frame.setSize(970, 800);
		         frame.setLocationRelativeTo(null);//窗体居中显示
		         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		         /* 创建面板，这个类似于 HTML 的 div 标签
		          * 我们可以创建多个面板并在 JFrame 中指定位置
		          * 面板中我们可以添加文本字段，按钮及其他组件。
		          */
		         os = new JPanel(); // 添加面板
		         
		         frame.add(os);//  * 调用用户定义的方法并添加组件到面板
		          
		         MainController(os);

		         // 设置界面可见
		         frame.setVisible(true);
		     }

		     private static void MainController(JPanel os) 
		     {

		         /* 布局部分我们这边不多做介绍
		          * 这边设置布局为 null
		          */ os.setLayout(null);
		    	 
		        // Label颜色字体设置        
		         font = new Font("宋体",Font.BOLD, 18);//宋体、加粗、18像素
		         font1 = new Font("宋体",Font.BOLD, 24);//宋体、加粗、24像素	  
		         font2 = new Font("宋体",Font.BOLD, 16);//宋体、加粗、16像素
		         font3 = new Font("宋体",Font.BOLD, 14);//宋体、加粗、16像素
		         // 创建 JLabel
		         systemtimel = new JLabel("系统时间:");
		         systemtimel.setFont(font); 
		         /* 这个方法定义了组件的位置。
		          * setBounds(x, y, width, height)
		          * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
		          */
		         systemtimel.setBounds(10,20,100,40);
		         os.add(systemtimel);
	
		         /* 
		          * 创建文本域用于用户输入
		          */
		         systemtimet = new JTextField(40);
		         systemtimet.setBounds(100,20,100,40);
		         systemtimet.setEditable(false);
		         os.add(systemtimet);
	
		         // 输入密码的文本域
		         timeslicel =new JLabel("时间片计时:");
		         timeslicel.setFont(font);
		         timeslicel.setBounds(260,20,130,40);
		         os.add(timeslicel);
		         
	
		         /* 
		          *这个类似用于输入的文本域
		          * 
		          */
		         timeslicet = new JTextField(40);
		         timeslicet.setBounds(370,20,100,40);
		         timeslicet.setEditable(false);
		         os.add(timeslicet);
	
		         
		         // 文本域
		         timerl = new JLabel("时间片数:");
		         timerl.setFont(font);
		         timerl.setBounds(520,20,100,40);
		         os.add(timerl);
		         
	
		         /* 
		          *这个类似用于输入的文本域
		          * 但是输入的信息会以点号代替，用于包含密码的安全性
		          */
		         timert = new JTextField(40);
		         timert.setBounds(610,20,100,40);
		         timert.setEditable(false);
		         os.add(timert);
		         
		         // 创建登录按钮
		         loginButton = new JButton("start");
		         loginButton.setFont(font);
		         loginButton.setBounds(800, 20, 100, 40);
		         os.add(loginButton);
		          
		        ////////////////////内存使用率
		         memoryUsagel = new JLabel();
		         memoryUsagel.setFont(font1);
		         memoryUsagel.setBounds(60,150,250,40);
		         os.add(memoryUsagel);	
		     
		          table3 = new JTable(6, 5);  
		    	  table3.setBounds(20, 190, 280, 200);
		    	  TableColumn column = null;  
		          int colunms = table3.getColumnCount();  
		          for(int i = 0; i < colunms; i++)  
		          {  
		              column = table3.getColumnModel().getColumn(i);  
		              /*将每一列的默认宽度设置为33*/  
		              table3.setRowHeight(33);  
		          }
		    	  os.add(table3);
		         
	
				///////////////////////////进程执行情况
		        processStatusl = new JLabel("进程信息");
		        processStatusl.setFont(font1);
		        processStatusl.setBounds(415,150,100,40);
		        os.add(processStatusl);	
		
		      
		        jta = new JTextArea();
		        jsp = new JScrollPane(jta);//加滚动条
		        jsp.setBounds(320, 190, 300, 200);//设置矩形大小
		        os.add(jsp);//加入frame
		        
		         
		         
		         //////////////////////提示框
		         promptl = new JLabel("指令");
		         promptl.setFont(font1);
		         promptl.setBounds(750,150,100,40);
		         os.add(promptl);	        
		         
		         prompt = new JTextField(10);
		         prompt.setBounds(640, 190, 300, 40);
		         os.add(prompt);
		         
		         intrpromptl = new JLabel("中断信息");
		         intrpromptl.setFont(font1);
		         intrpromptl.setBounds(730,230,100,40);
		         os.add(intrpromptl);
		         
		         intrprompt = new JTextField(10);
		       //  intrprompt.setFont(font1);
		         intrprompt.setBounds(640,270,300,40);
		         os.add(intrprompt);
		         
		         dealintrpromptl = new JLabel("中断处理信息");
		         dealintrpromptl.setFont(font1);
		         dealintrpromptl.setBounds(720, 310, 200, 40);
		         os.add(dealintrpromptl);
		         
		         dealintrprompt = new JTextField(10);
		         dealintrprompt.setBounds(640,350,300,40);
		         os.add(dealintrprompt);
		         
		         
		         
		         
		         
		         
		        ////////////////////// //添加设备
		         addDevicel = new JLabel("添加设备");
		         addDevicel.setFont(font1);
		         addDevicel.setBounds(100,470,100,40);
		         os.add(addDevicel);	         
		         
		         addDevicet= new JComboBox();
		         addDevicet.setFont(font);
		         addDevicet.setBounds(20, 520, 140, 40);
		         os.add(addDevicet);
		         addDevicet.addItem(null);
		         addDevicet.addItem("PRINTER");
		         addDevicet.addItem("KEYBOARD");
		         addDevicet.addItem("DISK");
		         addDevicet.addItem("MICROPHONE");
		         addDevicet.addItem("AUDIO");
		         
		         
		         //添加设备按钮
				 addButton = new JButton("add");
				 addButton.setFont(font3);
		         addButton.setBounds(170, 525, 60, 30);
		         os.add(addButton);
				 
				 //删除设备按钮
				 deleteButton = new JButton("del");
				 deleteButton.setFont(font3);
		         deleteButton.setBounds(240, 525, 60, 30);
		         os.add(deleteButton);
		         
		         deleteButton.addActionListener(new ActionListener() {
		        	 @Override
						public void actionPerformed(ActionEvent arg0) {
		        		 	int row = table1.getSelectedRow();
		        	 
				        	 if(row != -1) {
				        		String str = table1.getValueAt(row, 0).toString();
				        		DevController.delDevice(str);
				        	 }
				        	 
				        	 
		        	 }
		     
		         });
		         
		         
		         addButton.addActionListener(new ActionListener() {//增加设备监听事件
		        	 
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							String devTypeStr = (String)addDevicet.getSelectedItem();
							DevType devType;
							System.out.println("选择的新增设备类型是："+devTypeStr);
							switch (devTypeStr) {
							case "PRINTER":
								devType = DevType.PRINTER;
								break;
							case "KEYBOARD":
								devType = DevType.KEYBOARD;
								break;
							case "DISK":
								devType = DevType.DISK;
								break;
							case "MICROPHONE":
								devType = DevType.MICROPHONE;
								break;
							case "AUDIO":
								devType = DevType.AUDIO;
								break;
							default:
								System.out.println("没有这样的设备！！！");
								devType = DevType.DEFAULT;
								break;
							}
							DevTb devTb = DevController.addDevice(devType);
							
							//DevTb devTb = DevController.addDevice(devType);
							if(devTb != null) {
								System.out.println("新增设备成功");
								
							}
							else {
								System.out.println("新增设备失败");
							}
						}
					});
			         
		         
		         
		         ////////////////////////////设备以及占用的进程
		         Devicenamel = new JLabel("设备名称");
		         Devicenamel.setFont(font1);
		         Devicenamel.setBounds(340,470,100,40);
		         os.add(Devicenamel);	
		         
		         ocupyProcessl = new JLabel("占用的进程");
		         ocupyProcessl.setFont(font1);
		         ocupyProcessl.setBounds(480,470,140,40);
		         os.add(ocupyProcessl);	
		         
		       
		         mytable1 = new MyTable1();
		         table1 = new JTable(mytable1);
		         scrollPane1 = new JScrollPane(table1);// 创建显示表格的滚动面板
		         table1.setFont(font2);
		         table1.setRowHeight(20);//设置表格的行高
		        
		         tcr1= new DefaultTableCellRenderer(); //内容居中
		         tcr1.setHorizontalAlignment(JLabel.CENTER);
		         table1.setDefaultRenderer(Object.class,tcr1);
		         scrollPane1.setBounds(320, 520, 300, 200);
		         os.add(scrollPane1);
		         
	
		         /////////////////////// //进程ID及状态
		         Deviceidl = new JLabel("进程ID");
		         Deviceidl.setFont(font1);
		         Deviceidl.setBounds(680,470,100,40);
		         os.add(Deviceidl);	
		         
		         statusl = new JLabel("状态");
		         statusl.setFont(font1);
		         statusl.setBounds(840,470,140,40);
		         os.add(statusl);	
		         	       
		         // String[] columnNames2 = {"A","B"};// 定义表格列名数组
		         
		     //    String[][] tableValues2 = {{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}}; // 定义表格数据数组
		         // 创建指定列名和数据的表格
		         mytable2 = new MyTable2();
		         table2 = new JTable(mytable2);
		         // 创建显示表格的滚动面板
		         scrollPane2 = new JScrollPane(table2);
		         table2.setFont(font2);
		         table2.setRowHeight(20);//设置表格的行高
		         
		         
		         // 将滚动面板添加到边界布局的中间
		        // getContentPane().add(scrollPane, BorderLayout.CENTER);
		        
		         //内容居中
		         tcr2= new DefaultTableCellRenderer();
		         tcr2.setHorizontalAlignment(JLabel.CENTER);
		         table2.setDefaultRenderer(Object.class,tcr2);
		        // table.setBackground(null);
		         scrollPane2.setBounds(640, 520, 300, 200);
		         os.add(scrollPane2);
	         
	     }
	     
		     
		 public static void setTimer() {
			
			 System.out.println("界面输出!!!!!!!!!!"+timer.AllTimeStr);
			 
			 allTimeStr=timer.AllTimeStr;
			 rrTimeStr=timer.RRTimeStr;
			 rrCountStr=timer.RRCountStr;


			 systemtimet.setText(allTimeStr);
			 systemtimet.setFont(font);
			
			 timeslicet.setText(rrTimeStr);
			 timeslicet.setFont(font);
			 
			 timert.setText(rrCountStr);
			 timert.setFont(font);
		 }
		 
		 
		 public static void setMessage() {
			 
		
			 System.out.println("页面获得了ins！！！"+ins.insString);
			 insStr=ins.insString;
			 System.out.println("页面收到了ins！！！"+insStr);
			 prompt.setText(insStr);
			 prompt.setFont(font);

			 //ins.insString="";
			

			 
			//收到中断，寄存器
			
				interStr=InterruptReg.setIntrString;
				intrprompt.setText(interStr);
				intrprompt.setFont(font);
				InterruptReg.setIntrString="";
			

			//中断处理
		
				
				dealintrStr=InterService.DealStr;
				dealintrprompt.setText(dealintrStr);
				dealintrprompt.setFont(font);
				InterService.DealStr="";
			

			
			
			 
		 }
		 
		 
		 public static void setProcessMessage() { 
			 String info = ProcessMGT.info;
			 String info2 = ProcessMGT.info2;
			 //System.out.println("页面收到了ins！！！"+insStr);
			 jta.append(info);
			 jta.append(info2);
			 ProcessMGT.info="";
			 ProcessMGT.info2="";
		 }
		 
		 public static void setUsage() {
	    	 usage=Memory.getPageUseRate();
	    	 memoryUsagel.setText("内存使用率"+usage+"%");
	     }
}



class MyTable1 extends AbstractTableModel{
	//ArrayList<String> str = DevController.showDeviceInfo();
	
	public int getColumnCount() {
		return 2;
	}
	public int getRowCount() {
		return DevController.showDeviceInfo().size();
	}
	@Override
	public Object getValueAt(int row,int col) {
		switch(col) {
			case(0):
				return DevController.showDeviceInfo().get(row).split(" ",0)[0];
			default:
				return DevController.showDeviceInfo().get(row).split(" ",0)[1];
		}
	}
}

class MyTable2 extends AbstractTableModel{
	//List<String> str = ProcessMGT.getAllProcess();
	
	public int getColumnCount() {
		return 2;
	}
	public int getRowCount() {
		return ProcessMGT.getAllProcess().size();
	}
	@Override
	public Object getValueAt(int row,int col) {
		switch(col) {
			case(0):
				return ProcessMGT.getAllProcess().get(row).split(" ",0)[0];
			default:
				return ProcessMGT.getAllProcess().get(row).split(" ",0)[1];
		}
	}
}