import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.sql.*;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentApp {
	

	private JFrame frmK;
	private JTextField txtsName;
	private JTextField txtAddress;
	private JTable table;
	private JTextField txtPhone;
	private JTextField txtID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentApp window = new StudentApp();
					window.frmK.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentApp() {
		initialize();
		Connect();
		table();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ResultSetMetaData rd;
	DefaultTableModel model;
	
	
	//private JTextField txtID;
	
	
	// Connection to the Database
	public void Connect() {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/school","root","");
			//JOptionPane.showMessageDialog(null, "Connected Successfully!");
		
		} catch (Exception ex) {
			ex.printStackTrace();		
			}
	}
	
	
	public void table() {
		
		int a;
		try {
			pst = con.prepareStatement("SELECT * FROM student");
			rs = pst.executeQuery();
			
			rd = rs.getMetaData();
			a = rd.getColumnCount();
			model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			
			while (rs.next()) {
				
				Vector v = new Vector();
				
				for(int i=1; i<=a; i++) {
					v.add(rs.getString("id"));
					v.add(rs.getString("stname"));
					v.add(rs.getString("address"));
					v.add(rs.getString("phone"));
				}
				
				model.addRow(v);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmK = new JFrame();
		frmK.setTitle("K");
		frmK.getContentPane().setBackground(new Color(235, 235, 235));
		frmK.setBounds(100, 100, 970, 504);
		frmK.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmK.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(124, 162, 218));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(0, 0, 956, 70);
		frmK.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Register");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel.setBounds(337, 10, 259, 32);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(235, 235, 235));
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(0, 81, 446, 258);
		frmK.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Student Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(34, 57, 149, 35);
		panel_1.add(lblNewLabel_1);
		
		txtsName = new JTextField();
		txtsName.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtsName.setBounds(219, 57, 191, 36);
		panel_1.add(txtsName);
		txtsName.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Address");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(34, 118, 149, 35);
		panel_1.add(lblNewLabel_1_1);
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtAddress.setColumns(10);
		txtAddress.setBounds(219, 118, 191, 36);
		panel_1.add(txtAddress);
		
		JLabel lblNewLabel_1_2 = new JLabel("Phone Number");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_2.setBounds(34, 181, 149, 35);
		panel_1.add(lblNewLabel_1_2);
		
		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtPhone.setColumns(10);
		txtPhone.setBounds(219, 181, 191, 36);
		panel_1.add(txtPhone);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(456, 80, 490, 259);
		frmK.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 470, 239);
		panel_2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Address", "Phone No"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,address;
				int phone;
				
				name = txtsName.getText();
				address = txtAddress.getText();
				phone = Integer.parseInt(txtPhone.getText());
				
				try 
				{
					pst = con.prepareStatement("INSERT INTO student(stname,address,phone) VALUES(?,?,?)");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setInt(3, phone);
					pst.executeUpdate();
					table();
					JOptionPane.showMessageDialog(null, "Record Added");
					
					txtsName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtsName.requestFocus();
				
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setBackground(new Color(120, 180, 146));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBounds(465, 361, 109, 56);
		frmK.getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,address;
				int phone,id;
				
				name = txtsName.getText();
				address = txtAddress.getText();
				phone = Integer.parseInt(txtPhone.getText());
				id = Integer.parseInt(txtID.getText());
				
				try 
				{
					pst = con.prepareStatement("UPDATE student SET stname=?,address=?,phone=? WHERE id=?");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setInt(3, phone);
					pst.setInt(4, id);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table();
					txtsName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtsName.requestFocus();
				
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnUpdate.setBackground(new Color(104, 150, 213));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnUpdate.setBounds(584, 361, 109, 56);
		frmK.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id;
				
				id = txtID.getText();
				
				try {
					pst = con.prepareStatement("delete from student where id=?");
					
					pst.setString(1, id);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table();
					
					txtsName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtsName.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBackground(new Color(208, 147, 145));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDelete.setBounds(703, 361, 109, 56);
		frmK.getContentPane().add(btnDelete);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setBackground(new Color(235, 235, 235));
		panel_3.setBounds(0, 361, 446, 56);
		frmK.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("ID");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_2_1.setBounds(34, 11, 27, 35);
		panel_3.add(lblNewLabel_1_2_1);
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = txtID.getText();
					pst = con.prepareStatement("select stname,address,phone from student where id = ?");
	                pst.setString(1, id);
	                ResultSet rs = pst.executeQuery();
	                
	                if(rs.next()==true) {
	                	
	                	String stname = rs.getString(1);
	                    String address = rs.getString(2);
	                    String phone = rs.getString(3);
	                    
	                    txtsName.setText(stname);
	                    txtAddress.setText(address);
	                    txtPhone.setText(phone);
	                    
	                } else {
	                	
	                	txtsName.setText("");
	                	txtAddress.setText("");
	                	txtPhone.setText("");
	                }
				} catch (SQLException ex) {
					
				}
			}
		});
		
		txtID.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtID.setColumns(10);
		txtID.setBounds(219, 10, 194, 36);
		panel_3.add(txtID);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBackground(new Color(255, 255, 255));
		btnExit.setBounds(822, 361, 109, 56);
		frmK.getContentPane().add(btnExit);
	}
}
