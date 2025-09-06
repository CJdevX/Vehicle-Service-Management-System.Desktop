package UI;

import Class.JPanelLoader;
import Panel.appointmentPanel;
import Panel.dashboardPanel;
import Panel.customerPanel;
import Panel.invoicePanel;
import Panel.loginPanel;
import Panel.servicePanel;
import Panel.stockPanel;
import Panel.userPanel;
import Panel.scheduledJobPanel;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MainUI extends javax.swing.JFrame {

    JPanelLoader panelLoad = new JPanelLoader();
    
    public MainUI() {
        initComponents();
        setExtendedState(MainUI.MAXIMIZED_BOTH);
    }

    public void adminLoad() {
        title.setText("Dashboard");
        dashboardPanel dashboard = new dashboardPanel();
        panelLoad.jPanelLoader(mainPanel, dashboard);
        lbl_user.setText(loginPanel.name);
        lbl_user.setIcon(new ImageIcon(loginPanel.path));
    }

    public void cashierLoad() {
        title.setText("Job");
        invoicePanel invoice = new invoicePanel();
        panelLoad.jPanelLoader(mainPanel, invoice);
        lbl_user.setText(loginPanel.name);
        lbl_user.setIcon(new ImageIcon(loginPanel.path));

        Dashboard.setVisible(false);
        Service.setVisible(false);
        Stock.setVisible(false);
        SystemUser.setVisible(false);
        Customer.setVisible(true);
        Invoice.setVisible(true);
        Appointment.setVisible(true);
        Scheduled.setVisible(true);
    }

    public void technicianLoad() {
        title.setText("Waiting List");
        scheduledJobPanel waiting = new scheduledJobPanel();
        panelLoad.jPanelLoader(mainPanel, waiting);
        lbl_user.setText(loginPanel.name);
        lbl_user.setIcon(new ImageIcon(loginPanel.path));

        Dashboard.setVisible(false);
        Customer.setVisible(false);
        Service.setVisible(false);
        Stock.setVisible(false);
        SystemUser.setVisible(false);
        Invoice.setVisible(false);
        Appointment.setVisible(true);
        Scheduled.setVisible(true);
        
    }

//    public JLabel changeTitle() {
//        return title;
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TitlePanel = new keeptoo.KGradientPanel();
        title = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        Dashboard = new javax.swing.JMenu();
        Customer = new javax.swing.JMenu();
        Service = new javax.swing.JMenu();
        Stock = new javax.swing.JMenu();
        SystemUser = new javax.swing.JMenu();
        Appointment = new javax.swing.JMenu();
        Scheduled = new javax.swing.JMenu();
        Invoice = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TitlePanel.setkEndColor(new java.awt.Color(0, 0, 153));
        TitlePanel.setkGradientFocus(1000);
        TitlePanel.setkStartColor(new java.awt.Color(204, 0, 0));

        title.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Title");
        title.setPreferredSize(new java.awt.Dimension(0, 60));

        lbl_user.setBackground(new java.awt.Color(255, 255, 255));
        lbl_user.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_user.setForeground(new java.awt.Color(255, 255, 255));
        lbl_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/female_49px.png"))); // NOI18N
        lbl_user.setText("User");
        lbl_user.setToolTipText("");
        lbl_user.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_userMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout TitlePanelLayout = new javax.swing.GroupLayout(TitlePanel);
        TitlePanel.setLayout(TitlePanelLayout);
        TitlePanelLayout.setHorizontalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_user)
                .addGap(50, 50, 50))
        );
        TitlePanelLayout.setVerticalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_user)
                .addGap(10, 10, 10))
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.setBackground(java.awt.Color.lightGray);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1075, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 706, Short.MAX_VALUE)
        );

        Dashboard.setText("Dashboard");
        Dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashboardMouseClicked(evt);
            }
        });
        jMenuBar1.add(Dashboard);

        Customer.setText("Customer");
        Customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CustomerMouseClicked(evt);
            }
        });
        jMenuBar1.add(Customer);

        Service.setText("Service");
        Service.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ServiceMouseClicked(evt);
            }
        });
        jMenuBar1.add(Service);

        Stock.setText("Stock");
        Stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StockMouseClicked(evt);
            }
        });
        jMenuBar1.add(Stock);

        SystemUser.setText("System User");
        SystemUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SystemUserMouseClicked(evt);
            }
        });
        jMenuBar1.add(SystemUser);

        Appointment.setText("Appointment");
        Appointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AppointmentMouseClicked(evt);
            }
        });
        jMenuBar1.add(Appointment);

        Scheduled.setText("Scheduled");
        Scheduled.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScheduledMouseClicked(evt);
            }
        });
        jMenuBar1.add(Scheduled);

        Invoice.setText("Invoice");
        Invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InvoiceMouseClicked(evt);
            }
        });
        jMenuBar1.add(Invoice);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardMouseClicked
        title.setText("Dashboard");
        dashboardPanel dashboard = new dashboardPanel();
        panelLoad.jPanelLoader(mainPanel, dashboard);
    }//GEN-LAST:event_DashboardMouseClicked

    private void CustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CustomerMouseClicked
        title.setText("Customer");
        customerPanel customer = new customerPanel();
        panelLoad.jPanelLoader(mainPanel, customer);
    }//GEN-LAST:event_CustomerMouseClicked

    private void ServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ServiceMouseClicked
        title.setText("Service");
        servicePanel service = new servicePanel();
        panelLoad.jPanelLoader(mainPanel, service);
    }//GEN-LAST:event_ServiceMouseClicked

    private void StockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StockMouseClicked
        title.setText("Stock");
        stockPanel stock = new stockPanel();
        panelLoad.jPanelLoader(mainPanel, stock);
    }//GEN-LAST:event_StockMouseClicked

    private void SystemUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SystemUserMouseClicked
        title.setText("System User");
        userPanel user = new userPanel();
        panelLoad.jPanelLoader(mainPanel, user);
    }//GEN-LAST:event_SystemUserMouseClicked

    private void InvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InvoiceMouseClicked
        title.setText("Invoice");
        invoicePanel job = new invoicePanel();
        panelLoad.jPanelLoader(mainPanel, job);
    }//GEN-LAST:event_InvoiceMouseClicked

    private void lbl_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_userMouseClicked
        this.dispose();
        LoginUI login = new LoginUI();
        login.setVisible(true);
    }//GEN-LAST:event_lbl_userMouseClicked

    private void AppointmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AppointmentMouseClicked
        title.setText("Appointment");
        appointmentPanel appointment = new appointmentPanel();
        panelLoad.jPanelLoader(mainPanel, appointment);
    }//GEN-LAST:event_AppointmentMouseClicked

    private void ScheduledMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScheduledMouseClicked
        title.setText("Scheduled Job");
        scheduledJobPanel schedule = new scheduledJobPanel();
        panelLoad.jPanelLoader(mainPanel, schedule);
    }//GEN-LAST:event_ScheduledMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Appointment;
    private javax.swing.JMenu Customer;
    private javax.swing.JMenu Dashboard;
    private javax.swing.JMenu Invoice;
    private javax.swing.JMenu Scheduled;
    private javax.swing.JMenu Service;
    private javax.swing.JMenu Stock;
    private javax.swing.JMenu SystemUser;
    private keeptoo.KGradientPanel TitlePanel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
