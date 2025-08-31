

package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Two extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
    private DefaultTableModel model;

    public Two() {
        setTitle("Inventory management system");
        setBounds(850, 10, 600, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(45, 45, 45));

        connectToDatabase();

        JButton B_close = new JButton("Close");
        B_close.setBounds(450, 20, 100, 30);
        B_close.setFont(new Font("Arial", Font.BOLD, 14));
        B_close.setBackground(new Color(255, 69, 0));  
        B_close.setForeground(Color.WHITE);
        add(B_close);

        B_close.addActionListener(e -> setVisible(false));

        JLabel l_tital = new JLabel("Supplier Management");
        l_tital.setBounds(150, 20, 300, 30);
        l_tital.setFont(new Font("Arial", Font.BOLD, 18));
        l_tital.setBackground(new Color(255, 69, 0));  
        l_tital.setForeground(Color.WHITE);
        add(l_tital);
        
        JButton B_reset = new JButton("Reset");
        B_reset.setBounds(0, 20, 100, 30);
        B_reset.setFont(new Font("Arial", Font.BOLD, 14));
        B_reset.setBackground(new Color(255, 69, 0));  
        B_reset.setForeground(Color.WHITE);
        add(B_reset);
        

        JPanel pan = new JPanel();
        pan.setLayout(null);
        pan.setBounds(0, 60, 600, 500);
        pan.setBackground(new Color(220, 220, 220));

        JLabel s_id = new JLabel("Search Enter ID:");
        s_id.setBounds(350, 10, 150, 30);
        s_id.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_id = new JTextField();
        text_id.setBounds(480, 10, 50, 30);
        text_id.setFont(new Font("Arial", Font.BOLD, 15));


        JLabel s_name = new JLabel("Supplier Name:");
        s_name.setBounds(10, 10, 120, 30);
        s_name.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_name = new JTextField();
        text_name.setBounds(140, 10, 200, 30);
        text_name.setFont(new Font("Arial", Font.BOLD, 12));


        JLabel s_address = new JLabel("Address:");
        s_address.setBounds(10, 50, 120, 30);
        s_address.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_address = new JTextField();
        text_address.setBounds(140, 50, 200, 30);
        text_address.setFont(new Font("Arial", Font.BOLD, 12));


        JLabel s_phone = new JLabel("Phone:");
        s_phone.setBounds(10, 90, 120, 30);
        s_phone.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_phone = new JTextField();
        text_phone.setBounds(140, 90, 200, 30);       
        text_phone.setFont(new Font("Arial", Font.BOLD, 12));


        String[] columnNames = {"Supplier ID", "Name", "Address", "Phone"};
        model = new DefaultTableModel(null, columnNames);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(2, 180, 575, 320);
        
        
        
        
        
        JButton b_add = new JButton("Add Supplier");
        b_add.setBounds(100, 130, 130, 30);
        b_add.setFont(new Font("Arial", Font.BOLD, 14));
        b_add.setBackground(new Color(40, 167, 69));
        b_add.setForeground(Color.WHITE);
        
        JButton b_clear = new JButton("Clear");
        b_clear.setBounds(250, 130, 90, 30);
        b_clear.setFont(new Font("Arial", Font.BOLD, 14));
        b_clear.setBackground(new Color(40, 167, 150));
        b_clear.setForeground(Color.WHITE);
        b_clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				text_name.setText("");
				text_id.setText("");
				text_address.setText("");
				text_phone.setText("");
				// TODO Auto-generated method stub
				
			}
		});

        JButton b_delet = new JButton("Delete Supplier");
        b_delet.setBounds(370, 90, 150, 30);
        b_delet.setFont(new Font("Arial", Font.BOLD, 14));
        b_delet.setBackground(new Color(220, 53, 69));
        b_delet.setForeground(Color.WHITE);

        JButton b_serach = new JButton("Search");
        b_serach.setBounds(370, 50, 150, 30);
        b_serach.setFont(new Font("Arial", Font.BOLD, 14));
        b_serach.setBackground(new Color(23, 162, 184));
        b_serach.setForeground(Color.WHITE);

        JButton b_update = new JButton("Update");
        b_update.setBounds(370, 130, 150, 30);
        b_update.setFont(new Font("Arial", Font.BOLD, 14));
        b_update.setBackground(new Color(0, 123, 255));
        b_update.setForeground(Color.WHITE);

        // Add Supplier to Database
        b_add.addActionListener(e -> {
        	String name = text_name.getText().trim();
            String address = text_address.getText().trim();
            String phone = text_phone.getText().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            try {
                String query = "INSERT INTO suppliers (name, address, phone) VALUES (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, phone);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    model.addRow(new Object[]{generatedId, name, address, phone});
                }

                text_name.setText("");
                text_address.setText("");
                text_phone.setText("");
                JOptionPane.showMessageDialog(this, "Supplier added successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding supplier: " + ex.getMessage());
            }
        });

        b_delet.addActionListener(e -> {
        	String id = text_id.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an ID to delete!");
                return;
            }

            try {
                String query = "DELETE FROM suppliers WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, id);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).equals(id)) {
                            model.removeRow(i);
                            break;
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Supplier deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Supplier not found!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting supplier: " + ex.getMessage());
            }
        });

        b_update.addActionListener(e -> {
        	  String id = text_id.getText().trim();
        	    String name = text_name.getText().trim();
        	    String address = text_address.getText().trim();
        	    String phone = text_phone.getText().trim();

        	    if (id.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
        	        JOptionPane.showMessageDialog(this, "All fields are required!");
        	        return;
        	    }

        	    try {
        	        String query = "UPDATE suppliers SET name = ?, address = ?, phone = ? WHERE id = ?";
        	        PreparedStatement ps = connection.prepareStatement(query);
        	        ps.setString(1, name);
        	        ps.setString(2, address);
        	        ps.setString(3, phone);
        	        ps.setString(4, id);
        	        int rowsAffected = ps.executeUpdate();

        	        if (rowsAffected > 0) {
        	            for (int i = 0; i < model.getRowCount(); i++) {
        	                if (model.getValueAt(i, 0).equals(id)) {
        	                    model.setValueAt(name, i, 1);
        	                    model.setValueAt(address, i, 2);
        	                    model.setValueAt(phone, i, 3);
        	                    break;
        	                }
        	            }
        	            JOptionPane.showMessageDialog(this, "Supplier updated successfully!");
        	        } else {
        	            JOptionPane.showMessageDialog(this, "Supplier not found!");
        	        }
        	    } catch (SQLException ex) {
        	        JOptionPane.showMessageDialog(this, "Error updating supplier: " + ex.getMessage());
        	    }
        });

        B_reset.addActionListener(e -> {
        	 try {
        	        String query = "SELECT * FROM suppliers";
        	        PreparedStatement ps = connection.prepareStatement(query);
        	        ResultSet rs = ps.executeQuery();

        	        model.setRowCount(0); 
        	        while (rs.next()) {
        	            String id = rs.getString("id");
        	            String name = rs.getString("name");
        	            String address = rs.getString("address");
        	            String phone = rs.getString("phone");
        	            model.addRow(new Object[]{id, name, address, phone});
        	        }
        	    } catch (SQLException ex) {
        	        JOptionPane.showMessageDialog(this, "Error refreshing data: " + ex.getMessage());
        	    }
        });


        b_serach.addActionListener(e -> {
        	  String id = text_id.getText().trim();

        	    if (id.isEmpty()) {
        	        JOptionPane.showMessageDialog(this, "Enter Supplier ID to search!");
        	        return;
        	    }

        	    try {
        	        String query = "SELECT * FROM suppliers WHERE id = ?";
        	        PreparedStatement ps = connection.prepareStatement(query);
        	        ps.setString(1, id);
        	        ResultSet rs = ps.executeQuery();

        	        if (rs.next()) {
        	            text_name.setText(rs.getString("name"));
        	            text_address.setText(rs.getString("address"));
        	            text_phone.setText(rs.getString("phone"));
        	        } else {
        	            JOptionPane.showMessageDialog(this, "Supplier not found!");
        	        }
        	    } catch (SQLException ex) {
        	        JOptionPane.showMessageDialog(this, "Error searching for supplier: " + ex.getMessage());
        	    }
        });
        

   pan.add(b_clear);
        pan.add(b_update);
        pan.add(b_delet);
        pan.add(b_serach);
        pan.add(s_id);
        pan.add(text_id);
        pan.add(s_name);
        pan.add(text_name);
        pan.add(s_address);
        pan.add(text_address);
        pan.add(s_phone);
        pan.add(text_phone);
        pan.add(scrollPane);
        pan.add(b_add);

        add(pan);
        setVisible(true);

        loadSuppliers();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "mysql");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void loadSuppliers() {
        try {
            String query = "SELECT * FROM suppliers";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                	rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " );
        }
        
        
    }
    
    public static void main(String[] args) {
    	
        new Two();
        
    }
        
        
    }


