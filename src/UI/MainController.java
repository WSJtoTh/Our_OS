package UI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

//import javafx.scene.control.TableColumn; 



import Device.*;
import Global.*;
import Interrupt.*;
import memory.Memory;
import pro.*;
import Process.*;
import timer.*;
import Process.*;

public class MainController extends JFrame {
/*	     
		 public JFrame frame;
		 
		 public JLabel systemtimel;	//系统时间
		 public JTextField systemtimet;//
		 public JLabel timeslicel;	//时间片
		 public JPasswordField timeslicet;
		 public JLabel timerl;//计时器
		 public  JPasswordField timert;
		 public JButton loginButton;
		 
		 public JLabel processStatusl;//进程执行情况
		 public JTable table0;
		 public  JScrollPane scrollPane0;
		 
		 public  JLabel promptl;//提示框
		 public JPasswordField prompt;
		 
		 public  JLabel addDevicel;//添加设备
		 public	JComboBox addDevicet;
		 
		 public  JLabel Devicenamel;//设备以及占用的进程
		 public JLabel ocupyProcessl;
		 public JTable table1;
		 public JScrollPane scrollPane1;
		 public DefaultTableCellRenderer tcr1;
		 
		 
		 public JLabel Deviceidl;//进程ID及状态
		 public  JLabel statusl;
		 public JTable table2;
		 public JScrollPane scrollPane2;
		 public DefaultTableCellRenderer tcr2;
	 
		 public static JFrame os;
		 public static osUI UI;///////////
		 
		 
		 
	
		 public MainController panel1 = new MainController(os); 
		 
		 
		 public MainController(JFrame os) {
				this.os=os;
			//	initialize();
			}
		 
		public void newMainController(JFrame os)
		{	
			UI=new osUI UI(os);
			UI.start();
		}*/

	
		public static JFrame frame;
		public static JPanel os;
		public static Font font;
		public static Font font1;
		public static JLabel systemtimel;
		public static JTextField systemtimet;
		public static JLabel timeslicel;
		public static JTextField timeslicet;
		public static JLabel timerl;
		public static JTextField timert;
		public static JButton loginButton;
		public static JLabel memoryUsagel;
		public static JPasswordField memoryUsage;
		public static JLabel processStatusl;
		public static String[] columnNames0= {"A"};
		public static String[][] tableValues0 = {{"A1"},{"A2"},{"A3"},{"A4"},{"A5"}};
		public static JTable table0;
		public static JScrollPane scrollPane0;
		public static DefaultTableCellRenderer tcr0;
		public static JLabel promptl;
		public static JPasswordField prompt;
		public static JLabel addDevicel;
		public static JComboBox addDevicet;
		public static JButton okButton;
		public static JLabel Devicenamel;
		public static JLabel ocupyProcessl;
		public static String[] columnNames1 = {"A","B"};
		public static String[][] tableValues1 = {{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}};// 定义表格数据数组
		public static JTable table1;
		public static JScrollPane scrollPane1;
		public static DefaultTableCellRenderer tcr1;
		public static JLabel Deviceidl;
		public static JLabel statusl;
		public static String[] columnNames2 = {"进程id","进程状态"};// 定义表格列名数组
        public static String[][] tableValues2 = {{"",""}}; // 定义表格数据数组
        public static JTable table2;
        public static JScrollPane scrollPane2;
        public static DefaultTableCellRenderer tcr2;
        
        	
        	public static JScrollPane getjScrollPane2() {
        		return scrollPane2;
        	}
        	
        	public static void setjScrollPane2(JScrollPane jScrollPane) {
        		scrollPane2 = jScrollPane;
        	}
        	
        	public static JTable getjTable2() {
        		return table2;
        	}
        	
        	public static void setjTable2(JTable jTable) {
        		table2 = jTable;
        	}
        	
        	public static void reloadjTable2() {
        		String[][] info = ProcessMGT.getAllProcess();
        		/*
        		JTable table = new JTable(info,columnNames2);
        		table.setFont(font1);
        		setjTable2(table);
        		getjScrollPane2().setViewportView(table);*/
        		
        		int row = info.length;
        		for(int i = 0;i < row;i++) {
        			table2.setValueAt(info[i][0],i,0);
        			table2.setValueAt(info[i][1],i,1);
        		}
        		
        		
        		
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
		         timeslicel =new JLabel("时间片:");
		         timeslicel.setFont(font);
		         timeslicel.setBounds(270,20,100,40);
		         os.add(timeslicel);
		         
	
		         /* 
		          *这个类似用于输入的文本域
		          * 但是输入的信息会以点号代替，用于包含密码的安全性
		          */
		         timeslicet = new JTextField(40);
		         timeslicet.setBounds(350,20,100,40);
		         timeslicet.setEditable(false);
		         os.add(timeslicet);
	
		         
		         // 输入密码的文本域
		         timerl = new JLabel("计时器:");
		         timerl.setFont(font);
		         timerl.setBounds(520,20,100,40);
		         os.add(timerl);
		         
	
		         /* 
		          *这个类似用于输入的文本域
		          * 但是输入的信息会以点号代替，用于包含密码的安全性
		          */
		         timert = new JTextField(40);
		         timert.setBounds(590,20,100,40);
		         timert.setEditable(false);
		         os.add(timert);
		         
		         // 创建登录按钮
		         loginButton = new JButton("start");
		         loginButton.setFont(font);
		         loginButton.setBounds(800, 20, 100, 40);
		         os.add(loginButton);
		          
		        ////////////////////内存使用率
		         memoryUsagel = new JLabel("内存使用率");
		         memoryUsagel.setFont(font1);
		         memoryUsagel.setBounds(100,150,200,40);
		         os.add(memoryUsagel);	
		         
		         
		         memoryUsage = new JPasswordField(40);
		         memoryUsage.setBounds(20, 190, 280, 200);
		         os.add(memoryUsage);
		         
		         
		         
		         ///////////////////////////进程执行情况
		         processStatusl = new JLabel("设备名称");
		         processStatusl.setFont(font1);
		         processStatusl.setBounds(415,150,100,40);
		         os.add(processStatusl);	
	
		       
		        // columnNames0 = {"A"};// 定义表格列名数组
		         
		         // 定义表格数据数组
		         //String[][] tableValues0 = {{"A1"},{"A2"},{"A3"},{"A4"},{"A5"}};
		         // 创建指定列名和数据的表格
		         table0 = new JTable(tableValues0,columnNames0);
		         // 创建显示表格的滚动面板
		         scrollPane0 = new JScrollPane(table0);
		         // 将滚动面板添加到边界布局的中间
		        // getContentPane().add(scrollPane, BorderLayout.CENTER);
		         table0.setFont(font1);
		         table0.setRowHeight(30);//设置表格的行高
		         //内容居中
		         tcr0= new DefaultTableCellRenderer();
		         tcr0.setHorizontalAlignment(JLabel.CENTER);
		         table0.setDefaultRenderer(Object.class,tcr0);
		        // table.setBackground(null);
		         table0.setBounds(320, 190, 300, 200);
		         os.add(table0);
		         
		         
		         
		         
		         //////////////////////提示框
		         promptl = new JLabel("提示框");
		         promptl.setFont(font1);
		         promptl.setBounds(750,150,100,40);
		         os.add(promptl);	        
		         
		         prompt = new JPasswordField(40);
		         prompt.setBounds(640, 190, 300, 200);
		         os.add(prompt);
		         
		         
		         
		         
		        ////////////////////// //添加设备
		         addDevicel = new JLabel("添加设备");
		         addDevicel.setFont(font1);
		         addDevicel.setBounds(100,470,100,40);
		         os.add(addDevicel);	         
		         
		         addDevicet= new JComboBox();
		         addDevicet.setFont(font);
		         addDevicet.setBounds(20, 520, 280, 50);
		         os.add(addDevicet);
		         addDevicet.addItem(null);
		         addDevicet.addItem("PRINTER");
		         addDevicet.addItem("KEYBOARD");
		         addDevicet.addItem("DISK");
		         addDevicet.addItem("MICROPHONE");
		         addDevicet.addItem("AUDIO");
		         
		         // 创建确认按钮
		         okButton = new JButton("ok");
		         okButton.setFont(font);
		         okButton.setBounds(110, 600, 100, 40);
		         os.add(okButton);
		         
		         
		         
		         ////////////////////////////设备以及占用的进程
		         Devicenamel = new JLabel("设备名称");
		         Devicenamel.setFont(font1);
		         Devicenamel.setBounds(340,470,100,40);
		         os.add(Devicenamel);	
		         
		         ocupyProcessl = new JLabel("占用的进程");
		         ocupyProcessl.setFont(font1);
		         ocupyProcessl.setBounds(480,470,140,40);
		         os.add(ocupyProcessl);	
		         
		       
		         // String[] columnNames1 = {"A","B"};// 定义表格列名数组
		         
		         
		       //  String[][] tableValues1 = {{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}};// 定义表格数据数组
		         
		         table1 = new JTable(tableValues1,columnNames1);// 创建指定列名和数据的表格
		         
		         scrollPane1 = new JScrollPane(table1);// 创建显示表格的滚动面板
		         // 将滚动面板添加到边界布局的中间
		        // getContentPane().add(scrollPane, BorderLayout.CENTER);
		         table1.setFont(font1);
		         table1.setRowHeight(30);//设置表格的行高
		        
		         tcr1= new DefaultTableCellRenderer(); //内容居中
		         tcr1.setHorizontalAlignment(JLabel.CENTER);
		         table1.setDefaultRenderer(Object.class,tcr1);
		        // table.setBackground(null);
		         table1.setBounds(320, 520, 300, 200);
		         os.add(table1);
		         
	
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
		         table2 = new JTable(tableValues2,columnNames2);
		         // 创建显示表格的滚动面板
		         scrollPane2 = new JScrollPane(table2);
		         table2.setFont(font1);
		         table2.setRowHeight(30);//设置表格的行高
		         
		         
		         // 将滚动面板添加到边界布局的中间
		        // getContentPane().add(scrollPane, BorderLayout.CENTER);
		        
		         //内容居中
		         tcr2= new DefaultTableCellRenderer();
		         tcr2.setHorizontalAlignment(JLabel.CENTER);
		         table2.setDefaultRenderer(Object.class,tcr2);
		        // table.setBackground(null);
		         table2.setBounds(640, 520, 300, 200);
		         os.add(table2);
	         
	     }
	     
}