package Panel;

import Class.Database;
import Class.JPanelLoader;
import PopUpWindow.addVehicleWindow;
import UI.MainUI;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import Notification.Notification;
import java.io.InputStream;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class invoicePanel extends javax.swing.JPanel {

    Connection connect;
    MainUI main;
    private int invoiceNoCheck;
    private int jobNoCheck;
    private String checkName;
    List<String> serviceList = new ArrayList<>();
    List<String> partList = new ArrayList<>();
    JPanelLoader panelLoad = new JPanelLoader();
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public invoicePanel() {
        initComponents();

        // Show Date
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(d);
        Date.setText(date);

        // Show Time
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                String time = dateFormat.format(d);
                Time.setText(time);
            }
        }).start();

        // Cashier Name Get
        try {
            connect = Database.openConnection();
            String sql = "SELECT firstName FROM user WHERE userID = " + loginPanel.user;
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                checkName = result.getString("firstName");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // TABLE CELL RENDERING
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
        tbl_invoice.getColumnModel().getColumn(2).setCellRenderer(rightAlign);
        tbl_invoice.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
        tbl_invoice.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
        //====================================================================//

        jobNoLoader();
        invoiceNoLoader();
        clearData();
        partArrayLoader();
        partAutoComplete();
    }

    private void serviceArrayLoader() {
        try {
            connect = Database.openConnection();
            String sql = "SELECT name FROM service";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String serviceItem = result.getString("name");
                serviceList.add(serviceItem);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void serviceAutoComplete() {
        AutoCompleteDecorator.decorate(search, serviceList, false);

        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String currentText = search.getText();

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (String item : serviceList) {
                        if (item.toLowerCase().startsWith(currentText.toLowerCase())) {
                            search.setText(item);
                            search.setCaretPosition(item.length());
                            break;
                        }
                    }
                    search.setText("");
                }
            }
        });
    }

    private void partArrayLoader() {
        try {
            connect = Database.openConnection();
            String sql = "SELECT name FROM stock";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String stockItem = result.getString("name");
                partList.add(stockItem);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void partAutoComplete() {
        AutoCompleteDecorator.decorate(search, partList, false); // Assuming you're using a library for autocomplete

        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String currentText = search.getText();

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (String item : partList) {
                        if (item.toLowerCase().startsWith(currentText.toLowerCase())) {
                            search.setText(item);
                            search.setCaretPosition(item.length());
                            break;
                        }
                    }
                    search.setText("");
                }
            }
        });
    }

    private void jobNoLoader() {
        try {
            connect = Database.openConnection();
            String sql = "SELECT jobNo FROM job ORDER BY jobNo DESC LIMIT 1";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int checkJobNo = result.getInt("jobNo");
                jobNoCheck = checkJobNo + 1;
            } else {
                jobNoCheck = 1;
            }
            jobNo.setText(Integer.toString(jobNoCheck));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void invoiceNoLoader() {
        try {
            connect = Database.openConnection();
            String sql = "SELECT invoiceNo FROM invoice ORDER BY invoiceNo DESC LIMIT 1";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int checkInvoiceNo = result.getInt("invoiceNo");
                invoiceNoCheck = checkInvoiceNo + 1;
            } else {
                invoiceNoCheck = 1;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotal() {
        double Total = 0;
        for (int i = 0; i < tbl_invoice.getRowCount(); i++) {
            Total = Total + Double.parseDouble(tbl_invoice.getValueAt(i, 4).toString());
        }
        total.setText(decimalFormat.format(Total));
        subTotal.setText(decimalFormat.format(Total));
    }

    private void clearData() {
        vehicleNo.setText("");
        customerName.setText("");
        Phone.setText("");
        Type.setText("");
        Make.setText("");
        Model.setText("");
        txt_description.setText("");
        total.setText("");
        discount.setText("");
        subTotal.setText("");
        cash.setText("");
        balance.setText("");
        search.setText("");
    }

    private void showNotification(String Message) {
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, Message);
        noti.showNotification();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jobMainPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lbl_customerDetails3 = new javax.swing.JLabel();
        lbl_name = new javax.swing.JLabel();
        lbl_phone = new javax.swing.JLabel();
        lbl_vehicleNo = new javax.swing.JLabel();
        lbl_vehicleType = new javax.swing.JLabel();
        lbl_vehicleName = new javax.swing.JLabel();
        lbl_vehicleModel = new javax.swing.JLabel();
        lbl_description = new javax.swing.JLabel();
        Phone = new javax.swing.JLabel();
        customerName = new javax.swing.JLabel();
        Model = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txt_description = new javax.swing.JTextArea();
        Make = new javax.swing.JLabel();
        Type = new javax.swing.JLabel();
        vehicleNo = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        lbl_search = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        lbl_jobNo = new javax.swing.JLabel();
        jobNo = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        subTotal = new javax.swing.JLabel();
        discount = new javax.swing.JTextField();
        btn_ok = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_invoice = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        cash = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        balance = new javax.swing.JLabel();

        jobMainPanel.setBackground(new java.awt.Color(211, 211, 211));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        lbl_customerDetails3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbl_customerDetails3.setText("Invoice Details");

        lbl_name.setText("Customer Name");

        lbl_phone.setText("Telephone Number");

        lbl_vehicleNo.setText("Vehicle No");
        lbl_vehicleNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_vehicleNoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_vehicleNoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_vehicleNoMouseExited(evt);
            }
        });

        lbl_vehicleType.setText("Vehicle Type");

        lbl_vehicleName.setText("Vehicle Make");

        lbl_vehicleModel.setText("Vehicle Model");

        lbl_description.setText("Description");

        Phone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Phone.setText("Phone");

        customerName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customerName.setText("Name");

        Model.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Model.setText("Model");

        txt_description.setColumns(20);
        txt_description.setRows(5);
        txt_description.setAutoscrolls(false);
        jScrollPane7.setViewportView(txt_description);

        Make.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Make.setText("Make");

        Type.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Type.setText("Type");

        vehicleNo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        vehicleNo.setText("Vehicle");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_customerDetails3)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_vehicleNo)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(lbl_name)
                                    .addGap(20, 20, 20))
                                .addComponent(lbl_phone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vehicleNo, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(Phone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_vehicleType)
                                    .addComponent(lbl_vehicleModel))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Make, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Model, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)))
                            .addComponent(lbl_vehicleName))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_description)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbl_customerDetails3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_vehicleNo)
                            .addComponent(vehicleNo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_name)
                            .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_phone)
                            .addComponent(Phone)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_vehicleType, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Type))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_vehicleName)
                            .addComponent(Make))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_vehicleModel)
                            .addComponent(Model))))
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbl_description)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        lbl_date.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbl_date.setText("Date");

        lbl_time.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbl_time.setText("Time");

        lbl_search.setText("Add Part");

        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchKeyPressed(evt);
            }
        });

        lbl_jobNo.setText("Job Number");
        lbl_jobNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_jobNoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_jobNoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_jobNoMouseExited(evt);
            }
        });

        jobNo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jobNo.setText("Job");

        Date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Date.setText("Date");

        Time.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Time.setText("Time");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel4.setText("Total");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel6.setText("Sub Total");

        total.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        total.setText("Total");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel9.setText("Discount");

        subTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        subTotal.setText("Sub Price");

        discount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        discount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                discountKeyPressed(evt);
            }
        });

        btn_ok.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_ok.setText("Print");
        btn_ok.setFocusPainted(false);
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        btn_cancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        tbl_invoice.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tbl_invoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Cost Price", "QTY", "Sub Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_invoice.setName(""); // NOI18N
        tbl_invoice.setRowHeight(30);
        tbl_invoice.setShowGrid(false);
        tbl_invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_invoiceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_invoice);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel7.setText("Cash");

        cash.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cashKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel8.setText("Balance");

        balance.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        balance.setText("Balance");

        javax.swing.GroupLayout jobMainPanelLayout = new javax.swing.GroupLayout(jobMainPanel);
        jobMainPanel.setLayout(jobMainPanelLayout);
        jobMainPanelLayout.setHorizontalGroup(
            jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobMainPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jobMainPanelLayout.createSequentialGroup()
                                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(discount)
                                    .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cash)
                                    .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jobMainPanelLayout.createSequentialGroup()
                                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_time)
                                    .addComponent(lbl_date)
                                    .addComponent(lbl_jobNo))
                                .addGap(18, 18, 18)
                                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jobNo)
                                    .addComponent(Date)
                                    .addComponent(Time)))))
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cancel)))
                .addGap(30, 30, 30))
        );
        jobMainPanelLayout.setVerticalGroup(
            jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobMainPanelLayout.createSequentialGroup()
                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_jobNo)
                            .addComponent(jobNo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_date)
                            .addComponent(Date))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_time)
                            .addComponent(Time)))
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(total))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(subTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(balance))
                        .addGap(18, 18, 18)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_cancel)
                            .addComponent(btn_ok))
                        .addGap(46, 46, 46))
                    .addGroup(jobMainPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jobMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_search))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jobMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jobMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_vehicleNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_vehicleNoMouseClicked
        String checkVehicleNo = JOptionPane.showInputDialog(this, "Enter Vehicle Number");
        DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
        model.setRowCount(0);

        if (checkVehicleNo != null) {
            try {
                connect = Database.openConnection();
                String sql = "SELECT * FROM vehicle v INNER JOIN customer c ON v.customerID = c.customerID ";
                sql = sql + "WHERE vehicleNo = '" + checkVehicleNo + "'";
                PreparedStatement statement = connect.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    vehicleNo.setText(result.getString("vehicleNo"));
                    Type.setText(result.getString("type"));
                    Make.setText(result.getString("make"));
                    Model.setText(result.getString("model"));
                    customerName.setText(result.getString("firstName") + " " + result.getString("lastName"));
                    Phone.setText(result.getString("phone"));

                    btn_ok.setText("Create Job");
                    lbl_search.setText("Add Service");
                    partList.clear();
                    serviceArrayLoader();
                    serviceAutoComplete();

                } else {
                    int response = JOptionPane.showConfirmDialog(null, "Vehicle not Found.\nDo you want to Add Vehicle ?", null, JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        addVehicleWindow addvehicle = new addVehicleWindow();
                        addvehicle.setVisible(true);
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_lbl_vehicleNoMouseClicked

    private void lbl_jobNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_jobNoMouseClicked
        String checkJobNo = JOptionPane.showInputDialog(this, "Enter Job Number");

        if (checkJobNo != null) {
            jobNo.setText(checkJobNo);
            btn_ok.setText("Print");
            lbl_search.setVisible(false);
            search.setVisible(false);

            try {
                connect = Database.openConnection();
                String sql = "SELECT j.jobNo, v.vehicleNo,v.type,v.make,v.model,v.firstName,v.lastName,v.phone, js.serviceID, s.name,s.subPrice ";
                sql = sql + "FROM job j ";
                sql = sql + "INNER JOIN vehicle v ON j.vehicleNo = v.vehicleNo ";
                sql = sql + "INNER JOIN job_has_service js ON j.jobNo = js.jobNo ";
                sql = sql + "INNER JOIN service s ON js.serviceId = s.serviceID ";
                sql = sql + "WHERE j.jobNo = " + checkJobNo;
                PreparedStatement statement = connect.prepareStatement(sql);

                ResultSet result = statement.executeQuery();
                DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
                model.setRowCount(0);

                while (result.next()) {
                    vehicleNo.setText(result.getString("vehicleNo"));
                    Type.setText(result.getString("type"));
                    Make.setText(result.getString("make"));
                    Model.setText(result.getString("model"));
                    customerName.setText(result.getString("firstName") + " " + result.getString("lastName"));
                    Phone.setText(result.getString("phone"));

                    String id = result.getString("serviceID");
                    String name = result.getString("name");
                    String price = result.getString("subPrice");

                    model.addRow(new Object[]{id, name, null, null, price});
                }
                connect.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
            calculateTotal();
        }
    }//GEN-LAST:event_lbl_jobNoMouseClicked

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(d);

        if ("Create Job".equals(btn_ok.getText())) {
            try {
                connect = Database.openConnection();
                String sql1 = "SELECT customerID FROM vehicle WHERE vehicleNo = '" + vehicleNo.getText() + "'";
                PreparedStatement statement1 = connect.prepareStatement(sql1);
                ResultSet result = statement1.executeQuery();
                
                int idCheck = 0;
                if (result.next()) {
                    idCheck = result.getInt("customerID");
                }
                
                String sql2 = "INSERT INTO job(vehicleNo, date, customerID) VALUES(?, ?, ?)";
                PreparedStatement statement2 = connect.prepareStatement(sql2);
                statement2.setString(1, vehicleNo.getText());
                statement2.setString(2, date);
                statement2.setInt(3, idCheck);
                statement2.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String id = model.getValueAt(i, 0).toString();

                    String sql3 = "INSERT INTO job_has_service(jobNo, serviceID) VALUES(?, ?)";
                    PreparedStatement statement3 = connect.prepareStatement(sql3);
                    statement3.setInt(1, Integer.parseInt(jobNo.getText()));
                    statement3.setInt(2, Integer.parseInt(id));
                    statement3.executeUpdate();
                }

                showNotification("Job Created Successful");
                model.setRowCount(0);
                connect.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if ("Print".equals(btn_ok.getText())) {
            // Part Invoice Print
            if (lbl_search.isVisible() && search.isVisible()) {
                try {
                    connect = Database.openConnection();
                    String sql1 = "INSERT INTO invoice(date, type, total, discount, subTotal, userID) VALUES(?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement1 = connect.prepareStatement(sql1);
                    statement1.setString(1, date);
                    statement1.setString(2, "PART");
                    statement1.setDouble(3, Double.parseDouble(total.getText()));
                    if (discount.getText().isEmpty()) {
                        statement1.setString(4, null);
                        statement1.setDouble(5, Double.parseDouble(total.getText()));
                    } else {
                        statement1.setDouble(4, Double.parseDouble(discount.getText()));
                        double sub = Double.parseDouble(total.getText()) - Double.parseDouble(discount.getText());
                        statement1.setDouble(5, sub);
                    }
                    statement1.setInt(6, loginPanel.user);
                    statement1.executeUpdate();

                    DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String id = model.getValueAt(i, 0).toString();
                        String qty = model.getValueAt(i, 3).toString();

                        String sql2 = "INSERT INTO invoice_has_stock(invoiceNo, partID, qty) VALUES(?, ?, ?)";
                        PreparedStatement statement2 = connect.prepareStatement(sql2);
                        statement2.setInt(1, invoiceNoCheck);
                        statement2.setInt(2, Integer.parseInt(id));
                        statement2.setInt(3, Integer.parseInt(qty));
                        statement2.executeUpdate();

                        String sql3 = "SELECT qty FROM stock WHERE partID = " + id;
                        PreparedStatement statement3 = connect.prepareStatement(sql3);
                        ResultSet result = statement3.executeQuery();
                        while (result.next()) {
                            String currentQTY = result.getString("qty");

                            int newQTY = Integer.parseInt(currentQTY) - Integer.parseInt(qty);
                            String sql4 = "UPDATE stock SET qty = ? WHERE partID = ?";
                            PreparedStatement statement4 = connect.prepareStatement(sql4);
                            statement4.setInt(1, newQTY);
                            statement4.setInt(2, Integer.parseInt(id));
                            statement4.executeUpdate();
                        }
                    }
                    model.setRowCount(0);

                    HashMap<String, Object> check = new HashMap<>();
                    check.put("js_invoiceNo", Integer.toString(invoiceNoCheck));
                    check.put("js_cashier", checkName);
                    check.put("js_total", total.getText());
                    check.put("js_discount", discount.getText());
                    check.put("js_subTotal", subTotal.getText());

                    String query = "D:\\COT\\COTFinalProject\\src\\Resource\\PartInvoice.jrxml";
                    JasperReport report = JasperCompileManager.compileReport(query);
                    JasperPrint print = JasperFillManager.fillReport(report, check, connect);
                    //JasperViewer.viewReport(print);
                    JasperPrintManager.printReport(print, false);
                    connect.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }

                // Job Invoice Print
            } else {
                try {
                    connect = Database.openConnection();
                    String sql1 = "INSERT INTO invoice(date, type, total, discount, subTotal, userID) VALUES(?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement1 = connect.prepareStatement(sql1);
                    statement1.setString(1, date);
                    statement1.setString(2, "SERVICE");
                    statement1.setDouble(3, Double.parseDouble(total.getText()));
                    if (discount.getText().isEmpty()) {
                        statement1.setString(4, null);
                        statement1.setDouble(5, Double.parseDouble(total.getText()));
                    } else {
                        statement1.setDouble(4, Double.parseDouble(discount.getText()));
                        double sub = Double.parseDouble(total.getText()) - Double.parseDouble(discount.getText());
                        statement1.setDouble(5, sub);
                    }
                    statement1.setInt(6, loginPanel.user);
                    statement1.executeUpdate();

                    String sql2 = "INSERT INTO invoice_has_job(invoiceNo, jobNo) VALUES(?, ?)";
                    PreparedStatement statement2 = connect.prepareStatement(sql2);
                    statement2.setInt(1, invoiceNoCheck);
                    statement2.setInt(2, Integer.parseInt(jobNo.getText()));
                    statement2.executeUpdate();

                    DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
                    model.setRowCount(0);

                    HashMap<String, Object> check = new HashMap<>();
                    check.put("js_jobNo", jobNo.getText());
                    check.put("js_invoiceNo", Integer.toString(invoiceNoCheck));
                    check.put("js_vehicleNo", vehicleNo.getText());
                    check.put("js_cutomerName", customerName.getText());
                    check.put("js_make", Make.getText());
                    check.put("js_model", Model.getText());
                    check.put("js_subTotal", total.getText());
                    check.put("js_discount", discount.getText());
                    check.put("js_total", subTotal.getText());

                    //String query = "D:\\COT\\COTFinalProject\\src\\Resource\\ServiceInvoice.jrxml";
                    InputStream input = getClass().getClassLoader().getResourceAsStream("Resource/ServiceInvoice.jrxml");
                    //JasperReport report = JasperCompileManager.compileReport(query);
                    JasperReport report = JasperCompileManager.compileReport(input);
                    JasperPrint print = JasperFillManager.fillReport(report, check, connect);
                    //JasperViewer.viewReport(print);
                    JasperPrintManager.printReport(print, false);
                    connect.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        clearData();
        jobNoLoader();
        invoiceNoLoader();
        lbl_search.setVisible(true);
        search.setVisible(true);
        lbl_search.setText("Add Part");
        btn_ok.setText("Print");
        serviceList.clear();
        partArrayLoader();
        partAutoComplete();
    }//GEN-LAST:event_btn_okActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
        model.setRowCount(0);
        clearData();
        jobNoLoader();
        lbl_search.setVisible(true);
        search.setVisible(true);
        lbl_search.setText("Add Part");
        btn_ok.setText("Print");
        serviceList.clear();
        partArrayLoader();
        partAutoComplete();
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if ("Add Service".equals(lbl_search.getText())) {
                try {
                    connect = Database.openConnection();
                    String sql = "SELECT serviceID, name, subPrice FROM service WHERE name = '" + search.getText() + "'";
                    PreparedStatement statement = connect.prepareStatement(sql);

                    ResultSet result = statement.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();

                    while (result.next()) {
                        String id = result.getString("serviceID");
                        String name = result.getString("name");
                        Double price = result.getDouble("subPrice");
                        
                        model.addRow(new Object[]{id, name, null, null, decimalFormat.format(price)});
                    }
                    connect.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
                calculateTotal();

            } else if ("Add Part".equals(lbl_search.getText())) {
                try {
                    connect = Database.openConnection();
                    String sql = "SELECT partID, name, price FROM stock WHERE name = '" + search.getText() + "'";
                    PreparedStatement statement = connect.prepareStatement(sql);

                    ResultSet result = statement.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();

                    while (result.next()) {
                        String id = result.getString("partID");
                        String name = result.getString("name");
                        Double price = result.getDouble("price");
                        String qty = JOptionPane.showInputDialog(this, "Enter QTY", "QTY", JOptionPane.QUESTION_MESSAGE);
                        Double subPrice = price * Integer.parseInt(qty);

                        model.addRow(new Object[]{id, name, decimalFormat.format(price), qty, decimalFormat.format(subPrice)});
                    }
                    connect.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
                calculateTotal();
            }
        }
    }//GEN-LAST:event_searchKeyPressed

    private void discountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_discountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Double sub = Double.parseDouble(total.getText()) - Double.parseDouble(discount.getText());
            subTotal.setText(decimalFormat.format(sub));
            discount.setText(discount.getText() + ".00");
        }
    }//GEN-LAST:event_discountKeyPressed

    private void cashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Double cash = Double.parseDouble(this.cash.getText()) - Double.parseDouble(this.subTotal.getText());
            balance.setText(decimalFormat.format(cash));
            this.cash.setText(this.cash.getText() + ".00");
        }
    }//GEN-LAST:event_cashKeyPressed

    private void lbl_vehicleNoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_vehicleNoMouseEntered
        lbl_vehicleNo.setFont(lbl_vehicleNo.getFont().deriveFont(Font.BOLD));
    }//GEN-LAST:event_lbl_vehicleNoMouseEntered

    private void lbl_vehicleNoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_vehicleNoMouseExited
        lbl_vehicleNo.setFont(lbl_vehicleNo.getFont().deriveFont(Font.PLAIN));
    }//GEN-LAST:event_lbl_vehicleNoMouseExited

    private void lbl_jobNoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_jobNoMouseEntered
        lbl_jobNo.setFont(lbl_vehicleNo.getFont().deriveFont(Font.BOLD));
    }//GEN-LAST:event_lbl_jobNoMouseEntered

    private void lbl_jobNoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_jobNoMouseExited
        lbl_jobNo.setFont(lbl_vehicleNo.getFont().deriveFont(Font.PLAIN));
    }//GEN-LAST:event_lbl_jobNoMouseExited

    private void tbl_invoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_invoiceMouseClicked
        int row = tbl_invoice.getSelectedRow();

        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this ?", "Delete", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tbl_invoice.getModel();
            model.removeRow(row);
        }
    }//GEN-LAST:event_tbl_invoiceMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Date;
    private javax.swing.JLabel Make;
    private javax.swing.JLabel Model;
    private javax.swing.JLabel Phone;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel Type;
    private javax.swing.JLabel balance;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_ok;
    private javax.swing.JTextField cash;
    private javax.swing.JLabel customerName;
    private javax.swing.JTextField discount;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel jobMainPanel;
    private javax.swing.JLabel jobNo;
    private javax.swing.JLabel lbl_customerDetails3;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_description;
    private javax.swing.JLabel lbl_jobNo;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_phone;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_vehicleModel;
    private javax.swing.JLabel lbl_vehicleName;
    private javax.swing.JLabel lbl_vehicleNo;
    private javax.swing.JLabel lbl_vehicleType;
    private javax.swing.JTextField search;
    private javax.swing.JLabel subTotal;
    private javax.swing.JTable tbl_invoice;
    private javax.swing.JLabel total;
    private javax.swing.JTextArea txt_description;
    private javax.swing.JLabel vehicleNo;
    // End of variables declaration//GEN-END:variables

}
