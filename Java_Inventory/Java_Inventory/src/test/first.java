

package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class first extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql";
    private DefaultTableModel model;

    public first() {
        setTitle("Inventory management system");
        setBounds(760, 10, 800, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 160, 240));

        JButton B_close = new JButton("Close");
        B_close.setBounds(650, 20, 100, 30);
        B_close.setFont(new Font("Arial", Font.BOLD, 14));
        B_close.setBackground(new Color(255, 49, 90));  
        B_close.setForeground(Color.WHITE);
        add(B_close);

        B_close.addActionListener(e -> setVisible(false));

        
        JLabel l_tital = new JLabel("Product Management");
        l_tital.setBounds(270, 20, 300, 30);
        l_tital.setFont(new Font("Arial", Font.BOLD, 25));

        l_tital.setForeground(Color.BLACK);
        add(l_tital);
        
        JButton B_reset = new JButton("Reseat");
        B_reset.setBounds(0, 20, 100, 30);
        B_reset.setFont(new Font("Arial", Font.BOLD, 14));
        B_reset.setBackground(new Color(0, 19, 62));  
        B_reset.setForeground(Color.WHITE);
        add(B_reset);

        JPanel pan = new JPanel();
        pan.setLayout(null);
        pan.setBounds(0, 60, 800, 600);
        pan.setBackground(new Color(0, 200, 250));

        JLabel p_id = new JLabel(" Search Product ID :");
        p_id.setBounds(400, 30, 180, 30);
        p_id.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField text_id = new JTextField();
        text_id.setBounds(577, 32, 40, 30);
        text_id.setFont(new Font("Arial", Font.BOLD, 15));

      

        JLabel p_name = new JLabel("Product Name:");
        p_name.setBounds(10, 20, 120, 30);
        p_name.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_name = new JTextField();
        text_name.setBounds(120, 20, 200, 30);
        text_name.setFont(new Font("Arial", Font.BOLD, 12));


        JLabel p_quantity = new JLabel("Quantity:");
        p_quantity.setBounds(10, 100, 100, 30);
        p_quantity.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_quantity = new JTextField();
        text_quantity.setBounds(120, 100, 200, 30);
        text_quantity.setFont(new Font("Arial", Font.BOLD, 12));


        JLabel p_price = new JLabel("Unit Price:");
        p_price.setBounds(10, 60, 100, 30);
        p_price.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField text_price = new JTextField();
        text_price.setBounds(120, 60, 200, 30);
        text_price.setFont(new Font("Arial", Font.BOLD, 12));


        JLabel p_description = new JLabel("Description:");
        p_description.setBounds( 10, 200, 200, 30);
        p_description.setFont(new Font("Arial", Font.BOLD, 15));

        JTextArea text_description = new JTextArea();
        text_description.setBounds(120, 190, 200, 50 );
        text_description.setFont(new Font("Arial", Font.BOLD, 12));
        text_description.setLineWrap(true);
        text_description.setWrapStyleWord(true);
        text_description.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setBounds(10, 150, 100, 30  );  
        supplierLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JComboBox<String> supplierList = new JComboBox<>();
        supplierList.setBounds(  120, 150, 200, 30);
        supplierList.setFont(new Font("Arial", Font.BOLD, 12));

        loadSuppliers(supplierList);

        JButton add_p = new JButton("Add Product");
        add_p.setBounds(340, 200, 130, 30);
        add_p.setFont(new Font("Arial", Font.BOLD, 15));
        add_p.setBackground(new Color(150, 200, 50));  
        add_p.setForeground(Color.WHITE);

        JButton search_p = new JButton("Search");
        search_p.setBounds(650, 20, 100, 30);
        
        search_p.setFont(new Font("Arial", Font.BOLD, 14));
        search_p.setBackground(new Color(0, 123, 255));  
        search_p.setForeground(Color.WHITE);

        JButton delete_p = new JButton("Delete");
        delete_p.setBounds(650, 100, 100, 30);
        delete_p.setFont(new Font("Arial", Font.BOLD, 14));
        delete_p.setBackground(new Color(255, 69, 0));  
        delete_p.setForeground(Color.WHITE);

        JButton update_p = new JButton("Update");
        update_p.setBounds(650, 60, 100, 30);
        update_p.setFont(new Font("Arial", Font.BOLD, 14));
        update_p.setBackground(new Color(40, 167, 69));  
        update_p.setForeground(Color.WHITE);

        JButton clear_p = new JButton("Clear");
        clear_p.setBounds( 500, 200, 150, 30);
        clear_p.setFont(new Font("Arial", Font.BOLD, 14));
        clear_p.setBackground(new Color(0, 123, 255));  
        clear_p.setForeground(Color.WHITE);

        JLabel not_Supplier = new JLabel("ِFiald Supplier Enter:");
        not_Supplier.setBounds(335, 150, 150, 30);
        
        not_Supplier.setFont(new Font("Arial", Font.BOLD, 15));
        pan.add(not_Supplier);

        JButton add_supplier = new JButton("ADD Supplier");
        add_supplier.setBounds(500, 150, 150, 30);
       
        add_supplier.setFont(new Font("Arial", Font.BOLD, 14));
        add_supplier.setBackground(new Color(5, 69, 50));  
        add_supplier.setForeground(Color.WHITE);
        add_supplier.addActionListener(e -> new Two());

        String[] columnNames = {"Product ID", "Product Name", "Supplier", "Quantity", "Unit Price", "Description"};
        model = new DefaultTableModel(null, columnNames);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(2, 250, 765, 400);

        

        add_p.addActionListener(e -> {
            String name = text_name.getText().trim();
            String quantity = text_quantity.getText().trim();
            String price = text_price.getText().trim();
            String description = text_description.getText().trim();
            String supplierName = (String) supplierList.getSelectedItem();

            if (name.isEmpty() || quantity.isEmpty() || price.isEmpty() || supplierName == null) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                // جلب ID المورد بناءً على الاسم
                String supplierIdQuery = "SELECT id FROM suppliers WHERE name = ?";
                try (PreparedStatement stmtSupplier = conn.prepareStatement(supplierIdQuery)) {
                    stmtSupplier.setString(1, supplierName);
                    try (ResultSet rsSupplier = stmtSupplier.executeQuery()) {
                        if (rsSupplier.next()) {
                            int supplierId = rsSupplier.getInt("id");

                            // إضافة المنتج في قاعدة البيانات مع ID المورد
                            String sql = "INSERT INTO products (name, quantity, price, description, supplier_id) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                                stmt.setString(1, name);
                                stmt.setInt(2, Integer.parseInt(quantity));
                                stmt.setDouble(3, Double.parseDouble(price));
                                stmt.setString(4, description);
                                stmt.setInt(5, supplierId);
                                stmt.executeUpdate();

                                try (ResultSet rs = stmt.getGeneratedKeys()) {
                                    if (rs.next()) {
                                        int generatedId = rs.getInt(1);  // الـ ID الذي تم توليده

                                        // تحديث الجدول في الواجهة مع الـ ID
                                        model.addRow(new Object[]{generatedId, name, supplierName, quantity, price, description});
                                    }
                                }

                                // مسح الحقول بعد إضافة المنتج
                                text_name.setText("");
                                text_quantity.setText("");
                                text_price.setText("");
                                text_description.setText("");
                                supplierList.setSelectedIndex(0); 
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Supplier not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        search_p.addActionListener(e -> {
            String id = text_id.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean found = false;
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM products WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            text_name.setText(rs.getString("name"));
                            text_quantity.setText(rs.getString("quantity"));
                            text_price.setText(rs.getString("price"));
                            text_description.setText(rs.getString("description"));
                            found = true;
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: ");
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Product not found!");
            }
        });

        delete_p.addActionListener(e -> {

            String id = text_id.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an ID to delete!");
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "DELETE FROM products WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " );
            }
        });

        update_p.addActionListener(e -> {
            String id = text_id.getText().trim();
            String name = text_name.getText().trim();
            String quantity = text_quantity.getText().trim();
            String price = text_price.getText().trim();
            String description = text_description.getText().trim();

            if (id.isEmpty() || name.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String supplierName = (String) supplierList.getSelectedItem();
            int supplierId = getSupplierIdByName(supplierName); // الحصول على supplier_id بناءً على الاسم

            if (supplierId == -1) {
                JOptionPane.showMessageDialog(this, "Supplier not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "UPDATE products SET name = ?, supplier_id = ?, quantity = ?, price = ?, description = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setInt(2, supplierId);  
                    stmt.setInt(3, Integer.parseInt(quantity));
                    stmt.setDouble(4, Double.parseDouble(price));
                    stmt.setString(5, description);
                    stmt.setString(6, id); 
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Product updated successfully!");
                        loadProducts();  
                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found for update!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        B_reset.addActionListener(e -> {

            loadProducts();   
            loadSuppliers(supplierList);   
        });
 

        clear_p.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            text_id.setText("");            
	            text_name.setText("");          
	            text_quantity.setText("");     
	            text_price.setText("");         
	            text_description.setText("");
			}
		});


        pan.add(update_p);
        pan.add(clear_p);
        pan.add(add_supplier);
        pan.add(p_id);
        pan.add(text_id);
        pan.add(p_name);
        pan.add(text_name);
        pan.add(p_quantity);
        pan.add(text_quantity);
        pan.add(p_price);
        pan.add(text_price);
        pan.add(p_description);
        pan.add(text_description);
        pan.add(supplierLabel);
        pan.add(supplierList);
        pan.add(add_p);
        pan.add(search_p);
        pan.add(delete_p);
        pan.add(scrollPane);

        add(pan);
        setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void loadProducts() {
        String query = "SELECT p.id, p.name, s.name AS supplier_name, p.quantity, p.price, p.description " +
                       "FROM products p " +
                       "JOIN suppliers s ON p.supplier_id = s.id"; 

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); 

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),             
                    rs.getString("name"),           
                    rs.getString("supplier_name"),  
                    rs.getInt("quantity"),          
                    rs.getDouble("price"),          
                    rs.getString("description")     
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSuppliers(JComboBox<String> supplierList) {
        String sql = "SELECT name FROM suppliers";  
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            supplierList.removeAllItems();  
            while (rs.next()) {
                supplierList.addItem(rs.getString("name"));  
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getSupplierIdByName(String supplierName) {
        int supplierId = -1;  
        String sql = "SELECT id FROM suppliers WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplierName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    supplierId = rs.getInt("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return supplierId;
    }

 

//    public static void main(String[] args) {
//    	
//        new first();
//        
//    }
}

