package Panel;

import Class.Database;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class dashboardPanel extends javax.swing.JPanel {

    Connection connect;
    private int startYear;
    private int endYear;
    private int monthCount = 0;
    private int yearCount = 0;

    public dashboardPanel() {
        initComponents();
        connect = Database.openConnection();
        cmb_getYear.removeAllItems();

        try {
            connect = Database.openConnection();
            
            // GET DATE
            Date d = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(d);

            SimpleDateFormat monthFormat = new SimpleDateFormat("MM"); // "MM" for month
            String month = monthFormat.format(d);

            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); // "yyyy" for year
            String year = yearFormat.format(d);
            //======================================================================================//

            cmb_getMonth.setSelectedIndex(Integer.parseInt(month));
            cmb_getYear.setSelectedItem(year);
            showDailyBarChart(year, month);
            
            // LOADING YEARS FOR cmb_Year
            connect = Database.openConnection();
            String sql1 = "SELECT DISTINCT YEAR(date) FROM invoice;";
            PreparedStatement statement1 = connect.prepareStatement(sql1);
            ResultSet result1 = statement1.executeQuery();

            String sql2 = "SELECT DISTINCT YEAR(date) FROM invoice ORDER BY YEAR(date) LIMIT 1";
            PreparedStatement statement2 = connect.prepareStatement(sql2);
            ResultSet result2 = statement2.executeQuery();

            String sql3 = "SELECT DISTINCT YEAR(date) FROM invoice ORDER BY YEAR(date) DESC LIMIT 1";
            PreparedStatement statement3 = connect.prepareStatement(sql3);
            ResultSet result3 = statement3.executeQuery();

            while (result1.next()) {
                cmb_getYear.addItem(result1.getString("YEAR(date)"));
            }

            if (result2.next()) {
                this.startYear = result2.getInt("YEAR(date)");
            }

            if (result3.next()) {
                this.endYear = result3.getInt("YEAR(date)");
            }
            //======================================================================================//

            // TODAY PIE CHART LOADING
            String sql4 = "SELECT SUM(subTotal) FROM invoice WHERE type = 'PART' AND date = '" + date + "'";
            PreparedStatement statement4 = connect.prepareStatement(sql4);
            ResultSet result4 = statement4.executeQuery();

            String sql5 = "SELECT SUM(subTotal) FROM invoice WHERE type = 'SERVICE' AND date = '" + date + "'";
            PreparedStatement statement5 = connect.prepareStatement(sql5);
            ResultSet result5 = statement5.executeQuery();

            Double part = null;
            Double service = null;
            if (result4.next()) {
                part = result4.getDouble("SUM(subTotal)");
            }

            if (result5.next()) {
                service = result5.getDouble("SUM(subTotal)");
            }

            Double tot = part + service;
            Double partSale = (part * 100) / tot;
            Double serviceSale = (service * 100) / tot;

            //create dataset
            DefaultPieDataset barDataset = new DefaultPieDataset();
            barDataset.setValue("Service", serviceSale); //barDataset.setValue("Service", new Double(serviceSale))
            barDataset.setValue("Part", partSale);

            //create chart
            JFreeChart piechart = ChartFactory.createPieChart("Today Sale", barDataset, true, true, false);//explain
            PiePlot piePlot = (PiePlot) piechart.getPlot();

            //changing pie chart blocks colors
            piePlot.setSectionPaint("Service", new Color(255, 0, 0));
            piePlot.setSectionPaint("Part", new Color(0, 0, 255));
            piePlot.setBackgroundPaint(Color.white);

            //create chartPanel to display chart(graph)
            ChartPanel barChartPanel = new ChartPanel(piechart);
            PieChart.removeAll();
            PieChart.add(barChartPanel, BorderLayout.CENTER);
            PieChart.validate();
            //======================================================================================//

            // COUNT CUSTOMERS
            String sql6 = "SELECT COUNT(vehicleNo) FROM vehicle";
            PreparedStatement statement6 = connect.prepareStatement(sql6);
            ResultSet result6 = statement6.executeQuery();

            if (result6.next()) {
                lbl_customerCount.setText(result6.getString("COUNT(vehicleNo)"));
            }

            //======================================================================================//
            
            // COUNT MECHANICS
            String sql7 = "SELECT COUNT(userID) FROM user WHERE role = 'MECHANIC'";
            PreparedStatement statement7 = connect.prepareStatement(sql7);
            ResultSet result7 = statement7.executeQuery();

            if (result7.next()) {
                lbl_mechanicCount.setText(result7.getString("COUNT(userID)"));
            }
            //======================================================================================//
            
            // COUNT PENDING JOB
            String sql8 = "SELECT COUNT(status) FROM job_has_service WHERE status = 'In Progress'";
            PreparedStatement statement8 = connect.prepareStatement(sql8);
            ResultSet result8 = statement8.executeQuery();

            if (result8.next()) {
                lbl_pendingJob.setText(result8.getString("COUNT(status)"));
            }
            //======================================================================================//
            
            // COUNT WARNINGS
            String sql9 = "SELECT COUNT(qty) FROM stock WHERE qty < 100";
            PreparedStatement statement9 = connect.prepareStatement(sql9);
            ResultSet result9 = statement9.executeQuery();

            if (result9.next()) {
                lbl_warning.setText(result9.getString("COUNT(qty)"));
            }
            //======================================================================================//
            connect.close();
                       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showDailyBarChart(String Year, String Month) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String month = null;

        try {
            connect = Database.openConnection();
            for (int i = 1; i <= 31; i++) {
                String sql = "SELECT SUM(subTotal) FROM invoice ";
                sql = sql + "WHERE YEAR(date) = " + Year + " AND ";
                sql = sql + "MONTH(date) = " + Month + " AND ";
                sql = sql + "DAY(date) = " + i;
                PreparedStatement statement = connect.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    Double amount = result.getDouble("SUM(subTotal)");
                    dataset.setValue(amount, "Amount", Integer.toString(i));
                }
            }
            connect.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        switch (cmb_getMonth.getSelectedIndex()) {
            case 1 ->
                month = "January";
            case 2 ->
                month = "February";
            case 3 ->
                month = "March";
            case 4 ->
                month = "April";
            case 5 ->
                month = "May";
            case 6 ->
                month = "June";
            case 7 ->
                month = "July";
            case 8 ->
                month = "August";
            case 9 ->
                month = "September";
            case 10 ->
                month = "October";
            case 11 ->
                month = "November";
            case 12 ->
                month = "December";
        }

        JFreeChart chart = ChartFactory.createBarChart(Year + " " + month + " Daily Income", "Day", "Amount", dataset, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color barColor = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, barColor);

        ChartPanel barpChartPanel = new ChartPanel(chart);
        BarChart.removeAll();
        BarChart.add(barpChartPanel, BorderLayout.CENTER);
        BarChart.validate();
    }

    public void showMonthlyBarChart(String Year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            connect = Database.openConnection();
            for (int i = 1; i <= 12; i++) {
                String sql = "SELECT SUM(subTotal) FROM invoice ";
                sql = sql + "WHERE YEAR(date) = " + Year + " AND ";
                sql = sql + "MONTH(date) = " + i;
                PreparedStatement statement = connect.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    Double amount = result.getDouble("SUM(subTotal)");

                    int month = i;
                    switch (month) {
                        case 1:
                            dataset.setValue(amount, "Amount", "January");
                        case 2:
                            dataset.setValue(amount, "Amount", "February");
                        case 3:
                            dataset.setValue(amount, "Amount", "March");
                        case 4:
                            dataset.setValue(amount, "Amount", "April");
                        case 5:
                            dataset.setValue(amount, "Amount", "May");
                        case 6:
                            dataset.setValue(amount, "Amount", "June");
                        case 7:
                            dataset.setValue(amount, "Amount", "July");
                        case 8:
                            dataset.setValue(amount, "Amount", "August");
                        case 9:
                            dataset.setValue(amount, "Amount", "September");
                        case 10:
                            dataset.setValue(amount, "Amount", "October");
                        case 11:
                            dataset.setValue(amount, "Amount", "November");
                        case 12:
                            dataset.setValue(amount, "Amount", "December");
                    }
                }
            }
            connect.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        JFreeChart chart = ChartFactory.createBarChart(Year + " Monthly Income", "Month", "Amount", dataset, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, clr3);

        ChartPanel barpChartPanel = new ChartPanel(chart);
        BarChart.removeAll();
        BarChart.add(barpChartPanel, BorderLayout.CENTER);
        BarChart.validate();
    }

    public void showYearlyBarChart(int startYear, int endYear) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            connect = Database.openConnection();
            for (int i = startYear; i <= endYear; i++) {
                String sql = "SELECT SUM(subTotal) FROM invoice ";
                sql = sql + "WHERE YEAR(date) = " + i;
                PreparedStatement statement = connect.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    Double amount = result.getDouble("SUM(subTotal)");
                    dataset.setValue(amount, "Amount", Integer.toString(i));
                }
            }
            connect.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        JFreeChart chart = ChartFactory.createBarChart("Yearly Income", "Year", "Amount", dataset, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, clr3);

        ChartPanel barpChartPanel = new ChartPanel(chart);
        BarChart.removeAll();
        BarChart.add(barpChartPanel, BorderLayout.CENTER);
        BarChart.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_customerCount = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        lbl_mechanicCount = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        lbl_pendingJob = new javax.swing.JLabel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        lbl_warning = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        PieChart = new javax.swing.JPanel();
        BarChart = new javax.swing.JPanel();
        cmb_getMonth = new javax.swing.JComboBox<>();
        cmb_getYear = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(211, 211, 211));

        kGradientPanel1.setkEndColor(new java.awt.Color(0, 153, 0));
        kGradientPanel1.setkGradientFocus(300);
        kGradientPanel1.setkStartColor(new java.awt.Color(102, 255, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Total Customers");

        lbl_customerCount.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lbl_customerCount.setText("0");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(47, 47, 47))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_customerCount)
                .addGap(20, 20, 20))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(lbl_customerCount)
                .addGap(20, 20, 20))
        );

        kGradientPanel2.setkEndColor(new java.awt.Color(0, 102, 204));
        kGradientPanel2.setkGradientFocus(300);
        kGradientPanel2.setkStartColor(new java.awt.Color(204, 255, 255));

        lbl_mechanicCount.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lbl_mechanicCount.setText("0");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Total Mechanics");

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_mechanicCount)
                .addGap(20, 20, 20))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(49, 49, 49))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(20, 20, 20)
                .addComponent(lbl_mechanicCount)
                .addGap(20, 20, 20))
        );

        kGradientPanel3.setkEndColor(new java.awt.Color(255, 255, 0));
        kGradientPanel3.setkGradientFocus(300);
        kGradientPanel3.setkStartColor(new java.awt.Color(255, 255, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Pending Jobs");

        lbl_pendingJob.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lbl_pendingJob.setText("0");

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_pendingJob)
                .addGap(24, 24, 24))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(20, 20, 20)
                .addComponent(lbl_pendingJob)
                .addGap(20, 20, 20))
        );

        kGradientPanel4.setkEndColor(new java.awt.Color(204, 0, 51));
        kGradientPanel4.setkGradientFocus(300);
        kGradientPanel4.setkStartColor(new java.awt.Color(255, 153, 153));

        lbl_warning.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lbl_warning.setText("0");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Warnings");

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(122, 122, 122))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_warning)
                .addGap(20, 20, 20))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(lbl_warning)
                .addGap(20, 20, 20))
        );

        PieChart.setBackground(new java.awt.Color(255, 255, 255));
        PieChart.setMaximumSize(new java.awt.Dimension(350, 350));
        PieChart.setLayout(new java.awt.BorderLayout());

        BarChart.setBackground(new java.awt.Color(255, 255, 255));
        BarChart.setLayout(new java.awt.BorderLayout());

        cmb_getMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cmb_getMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_getMonthItemStateChanged(evt);
            }
        });

        cmb_getYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year" }));
        cmb_getYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_getYearItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 852, Short.MAX_VALUE)
                                .addComponent(cmb_getMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_getYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(PieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_getMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_getYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmb_getYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_getYearItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            yearCount++;
            if (yearCount > 1) {
                cmb_getMonth.setSelectedIndex(0);
                showMonthlyBarChart(cmb_getYear.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_cmb_getYearItemStateChanged

    private void cmb_getMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_getMonthItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            monthCount++;
            if (monthCount > 1) {
                showDailyBarChart(cmb_getYear.getSelectedItem().toString(), Integer.toString(cmb_getMonth.getSelectedIndex()));
            }
        }
    }//GEN-LAST:event_cmb_getMonthItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarChart;
    private javax.swing.JPanel PieChart;
    private javax.swing.JComboBox<String> cmb_getMonth;
    private javax.swing.JComboBox<String> cmb_getYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private javax.swing.JLabel lbl_customerCount;
    private javax.swing.JLabel lbl_mechanicCount;
    private javax.swing.JLabel lbl_pendingJob;
    private javax.swing.JLabel lbl_warning;
    // End of variables declaration//GEN-END:variables
}
