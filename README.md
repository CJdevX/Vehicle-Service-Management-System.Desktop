# Vehicle-Service-Management-System.Desktop

A comprehensive Java-based desktop application designed to manage a vehicle service center’s daily operations. The application empowers users to smoothly:
- Register and manage customers and vehicles
- Schedule service appointments
- Track and update vehicle service records
- Generate service-related reports

The project focuses on demonstrating strong object-oriented design, clean GUI integration, and fundamental database interaction.

---

## Technical Stacks
- Backend: MySQL database named vsms_local
- Frontend: Java (Swing-based desktop app), HTML/CSS/JS (web app)
- Platform: Windows Desktop
  
## Overview
- Built with Java using NetBeans and Swing
- Role-based access: Admin, Cashier, Mechanic
- Cashier: Register customer, vehicle, add services/spare parts, generate invoices
- Mechanic: Update status of services, including web-booked services
- Admin: Full control of services, stock, customers, and income reports
  
## Target Users
- Customers
- Cashiers: registers customers, adds services/spare parts, and generates invoices
- Mechanics: Mechanic updates service statuses (including web-added)
- Admins: Manage services, users, inventory, and monitor income, registers customers, adds services/spare parts, and generates invoices

## Modules Overview
1. Dashboard <br>
Admin can view count of  customers, mechanics, pending jobs, warning and daily/monthly/yearly income graphically

2. Customer <br>
Add, Update, Delete and view Customer vehicle details

3. Service <br>
Add, Update, Delete and view services details in the company

4. Stock <br>
Add, Update, Delete and view stock details in the company

5. System Users <br>
Manage Internal Staff giving Roles admin, cashier and mechanic

6. Appointment <br>
Change status of appointments create from web application by user. The only mechanic can change status that appointment.

7. Scheduled <br>
Show service status create from desktop application customer who came the service center

8. Invoice <br>
This section is important. In this here, generate the invoice and bring both completed services and buy spare parts.

## Database Design 
<img src="img/1.png">

## UI
<table border=0>
  <tr>
    <td colspan=2><img src="img/2.png" width=500></td>
  </tr>
  <tr>
    <td><img src="img/3.png" width=500></td>
    <td><img src="img/4.png" width=500></td>
  </tr>
  <tr>
    <td><img src="img/5.png" width=500></td>
    <td><img src="img/6.png" width=500></td>
  </tr>
  <tr>
    <td><img src="img/7.png" width=500></td>
    <td><img src="img/8.png" width=500></td>
  </tr>
  <tr>
    <td><img src="img/9.png" width=500></td>
    <td><img src="img/10.png" width=500></td>
  </tr>
</table>
