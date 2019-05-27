/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Query;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import static systems.tech247.dbaccess.DataAccess.entityManager;
import systems.tech247.hr.Employees;
import systems.tech247.hr.LvwLeave;
import systems.tech247.hr.LvwLeaveApplication;
import systems.tech247.util.NotifyUtil;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.leavemgt//LeaveApplicationEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LeaveApplicationEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.leavemgt.LeaveApplicationEditorTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_LeaveApplicationEditorAction",
//        preferredID = "LeaveApplicationEditorTopComponent"
//)
@Messages({
    "CTL_LeaveApplicationEditorAction=LeaveApplicationEditor",
    "CTL_LeaveApplicationEditorTopComponent=Leave Application Editor",
    "HINT_LeaveApplicationEditorTopComponent=This is a LeaveApplicationEditor window"
})
public final class LeaveApplicationEditorTopComponent extends TopComponent implements LookupListener {
    
    TopComponent lvtypeTC = WindowManager.getDefault().findTopComponent("LeaveTypeTopComponent");
    
    //Lookup for the Leave Type
    Lookup.Result<LvwLeave> rslt = lvtypeTC.getLookup().lookupResult(LvwLeave.class);
    
    
    
    LvwLeaveApplication application;
    Employees emp;
    InstanceContent ic = new InstanceContent();
    
    //The updateable
    LvwLeaveApplication updateable;
    
    
    LvwLeave leaveType;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    //Leave Details
    int leaveTypeID =0;
    //Start Date
    Date startDate;
    //Number Of Days
    Double numberOfDays=0.0;
    //End Date
    Date endDate;
    //Resumption Date
    Date resumeDate;
    //Remarks
    String remarks = "";
    //Cancelled
    Boolean cancelled = false;
    //Application Date
    Date applicationDate;
    public LeaveApplicationEditorTopComponent(){
       
    }
    
    public LeaveApplicationEditorTopComponent(LvwLeaveApplication applic){
       this(applic,applic.getEmployeeID()); 
    }
    
    public LeaveApplicationEditorTopComponent(Employees employee){
        this(null,employee);
        
    }
    
    
    public LeaveApplicationEditorTopComponent(LvwLeaveApplication application,final Employees emp) {
        initComponents();
        setName(Bundle.CTL_LeaveApplicationEditorTopComponent());
        setToolTipText(Bundle.HINT_LeaveApplicationEditorTopComponent());
        this.emp = emp;
        
        
        if(null!=application){
            this.updateable = DataAccess.getEntityManager().find(LvwLeaveApplication.class, application.getRecordNo());
            
        }
        
        
        
        fillTheFields();
        
        jdcLeaveStarts.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getSource()==jdcLeaveStarts && evt.getPropertyName()=="date"){
                    String dateString = sdf.format(jdcLeaveStarts.getDate());
                    startDate = new Date(dateString); //new date
                    
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    
                    
                    if(updateable != null){
                        
                        cal.add(Calendar.DAY_OF_MONTH, numberOfDays.intValue()-1);
                        endDate = cal.getTime();
                        int restDays = DataAccess.getRestDays(emp.getEmployeeID(), startDate, endDate);
                        resumeDate = DataAccess.getReturnDate(emp.getEmployeeID(), endDate);
                        if(restDays>0){
                            NotifyUtil.warn("Rest Days In Date Range", emp.getSurName()+" has "+restDays+" rest day(s) from "+sdf.format(startDate)+" to "+ sdf.format(endDate), false);
                            cal.add(Calendar.DAY_OF_MONTH, restDays);
                            endDate = cal.getTime();
                            resumeDate = DataAccess.getReturnDate(emp.getEmployeeID(), endDate);
                        }
                        
                        updateable.setFromDate(startDate);
                        updateable.setToDate(endDate);
                        updateable.setResumptionDate(resumeDate);
                        updateable.setNoOfDays(numberOfDays);
                        jdcLeaveEnds.setDate(endDate);
                        
                        jdcResume.setDate(resumeDate);
                        
                    }else{
                        cal.add(Calendar.DAY_OF_MONTH, numberOfDays.intValue()-1);
                        endDate = cal.getTime();
                        int restDays = DataAccess.getRestDays(emp.getEmployeeID(), startDate, endDate);
                        resumeDate = DataAccess.getReturnDate(emp.getEmployeeID(), endDate);
                        if(restDays>0){
                            NotifyUtil.warn("Rest Days In Date Range", emp.getSurName()+" has "+restDays+" rest day(s) from "+sdf.format(startDate)+" to "+ sdf.format(endDate), false);
                            cal.add(Calendar.DAY_OF_MONTH, restDays);
                            endDate = cal.getTime();
                            resumeDate = DataAccess.getReturnDate(emp.getEmployeeID(), endDate);
                        }
                        
                        
                        jdcLeaveEnds.setDate(endDate);
                        jdcResume.setDate(resumeDate);
                    }
                    
                    
                    modify(1);
                }
            }
        });
        
        
        
        jdcResume.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getSource()==jdcResume && evt.getPropertyName()=="date"){
                    String resume = sdf.format(jdcResume.getDate());
                    //NotifyUtil.info("Date Created", resume, false);
                    resumeDate = new Date(resume);
                    endDate = DataAccess.getEndDate(emp.getEmployeeID(), resumeDate);
                    jdcLeaveEnds.setDate(endDate);
                    int leaveDays = DataAccess.getLeaveDays(emp.getEmployeeID(), startDate, endDate);
                    numberOfDays = new Double(leaveDays);
                    jtNumberOfDays.setValue(numberOfDays);
                    
                    try{
                        updateable.setResumptionDate(resumeDate);
                        updateable.setNoOfDays(numberOfDays);
                        
                    }catch(NullPointerException ex){
                        
                    }
                    
                    modify(2);
                }
                
            }
        });
        
        jtNumberOfDays.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getSource()==jtNumberOfDays && evt.getPropertyName()=="value"){
                    computeDates();
                }
            }
        });
        
        
        
        
        
        jtRemarks.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                remarks = jtRemarks.getText();
                try{
                    updateable.setRemarks(remarks);
                }catch(NullPointerException ex){
                    
                }
                modify(3);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                remarks = jtRemarks.getText();
                try{
                    updateable.setRemarks(remarks);
                }catch(NullPointerException ex){
                    
                }
                modify(4);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                remarks = jtRemarks.getText();
                try{
                    updateable.setRemarks(remarks);
                }catch(NullPointerException ex){
                    
                }
                modify(5);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
       
            
        
            
    }
    void computeDates(){
        try{
                numberOfDays = new Double(jtNumberOfDays.getText());
                int number = numberOfDays.intValue();
                Calendar cal =  Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.DAY_OF_MONTH, number-1);
                endDate = cal.getTime();
                
                int restDays = DataAccess.getRestDays(emp.getEmployeeID(), startDate, endDate);
                if(restDays>0){
                    NotifyUtil.warn("Rest Days In Date Range", emp.getSurName()+" has "+restDays+" rest day(s) from "+sdf.format(startDate)+" to "+ sdf.format(endDate), false);
                }
                cal.add(Calendar.DAY_OF_MONTH, restDays);
                endDate = cal.getTime();
                jdcLeaveEnds.setDate(endDate);
                resumeDate = DataAccess.getReturnDate(emp.getEmployeeID(), endDate);
                jdcResume.setDate(resumeDate);
                    try{
                        
                        updateable.setResumptionDate(resumeDate);
                        updateable.setNoOfDays(numberOfDays);
                        updateable.setToDate(endDate);
                    }catch(NullPointerException ex){
                        //NotifyUtil.error("Error", "Error",ex, false);
                    }
                    modify(6);
                }catch(Exception ex){
                    NotifyUtil.error("Error", "Error",ex, false);
                }
                //jtRemarks.requestFocus();
    }
    
    void fillTheFields(){
        
       if(null!=emp){
           //Fill the Employee Name and Lock the field
           jtEmployee.setText(emp.getSurName()+" "+emp.getOtherNames());
           jtEmployee.setEditable(false);
           
           
           
       }
       
       if(null!=updateable){
           //Fill the Employee Name and Lock the field
           emp = updateable.getEmployeeID();
           jtEmployee.setText(emp.getSurName()+" "+emp.getOtherNames());
           jtEmployee.setEditable(false);
           jcbApproved.setSelected(!updateable.getIsCanceled());
           cancelled = updateable.getIsCanceled();
           applicationDate = updateable.getApplicationDate();
           jdcApplication.setDate(applicationDate);
           startDate = updateable.getFromDate();
           jdcLeaveStarts.setDate(startDate);
           endDate = updateable.getToDate();
           jdcLeaveEnds.setDate(endDate);
           resumeDate = updateable.getResumptionDate();
           jdcResume.setDate(resumeDate);
           remarks = updateable.getRemarks();
           jtRemarks.setText(remarks);
           numberOfDays = updateable.getNoOfDays();
           jtNumberOfDays.setValue(numberOfDays);
           leaveType = updateable.getLeaveTypeID();
           jtLeaveType.setText(leaveType.getLeaveName());
                   
                   
           
          
           
           
       }
    }

    @Override
    public void resultChanged(LookupEvent le) {
        Lookup.Result<LvwLeave> rslt = (Lookup.Result<LvwLeave>)le.getSource();
        for(LvwLeave l : rslt.allInstances()){
            jtLeaveType.setText(l.getLeaveName());
            //leaveTypeID = l.getLvwLeavePK().getLeaveID();
            leaveType = l;
            try{
                updateable.setLeaveTypeID(leaveType);
            }catch(NullPointerException ex){
                
            }
            
            modify(7);
        }
    }
    
    
    private class LeaveAppSavable extends AbstractSavable{
        
        LeaveAppSavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            if(null==updateable){
                return "New Leave Application";
            }else{
                return "Leave Application For" + emp.getSurName()+" "+emp.getOtherNames();
            }
        }
        
        LeaveApplicationEditorTopComponent tc(){
            return LeaveApplicationEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
           //tc().requestFocusInWindow(true);
            
            
            tc().ic.remove(this);
            unregister();
            //New Application
            if(null==updateable){
                applicationDate = new Date();
                jdcApplication.setDate(applicationDate);
                
                        String insertSQL = "INSERT INTO [dbo].[lvwLeaveApplication]\n" +
"           ([EmployeeID]\n" +
"           ,[FromDate]\n" +
"           ,[ToDate]\n" +
"           ,[LeaveTypeID]\n" +
"           ,[NoOfDays]\n" +
"           ,[HalfDayLeave]\n" +
"           ,[IsCanceled]\n" +
"           ,[CoCode]\n" +
"           ,[YearId]\n" +
"           ,[UserId]\n" +
"           ,[EntryDate]\n" +
"           ,[Remarks]\n" +
"           ,[LeaveCumulativeBalance]\n" +
"           ,[LeaveAnnualBalance]\n" +
"           ,[ResumptionDate]\n" +
"           ,[ApplicationDate]\n" +
"           ,[ToDateHalfDayLeave])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Query query = entityManager.createNativeQuery(insertSQL);
            query.setParameter(1, emp.getEmployeeID());
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, leaveType.getLeaveID());
            query.setParameter(5, numberOfDays);
            query.setParameter(6, false);
            query.setParameter(7, cancelled);
            query.setParameter(8, 1);
            query.setParameter(9, applicationDate.getYear());
            query.setParameter(11, applicationDate);
            query.setParameter(12, remarks);
            query.setParameter(15, resumeDate);
            query.setParameter(16, applicationDate);
            
            
            

            
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            }else{
                //Updating an existing leave
                entityManager.getTransaction().begin();
                entityManager.getTransaction().commit();
                
                    
                
            }
            
            
            
            UtilityLVW.content.set(Arrays.asList(new NodeRefreshLeaveApplication()), null);
            this.tc().close();
            
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof LeaveAppSavable){
                LeaveAppSavable e = (LeaveAppSavable)o;
                return tc() == e.tc();
            }
            return false;        
        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }
        
    }
    
    public void modify(int i){
        
            //NotifyUtil.info("Where is Modify", "Here At "+i, false);
    
            if(leaveType == null){
                StatusDisplayer.getDefault().setStatusText("Specify the Leave Type");
            }else if(startDate == null){
                StatusDisplayer.getDefault().setStatusText("Specify the Start Date");
            }else if(numberOfDays == 0 || endDate==null || resumeDate==null){
                
                    StatusDisplayer.getDefault().setStatusText("Specify either the number of days, the Leave End Date or Resume Date");
                
                
            }else{
                if(getLookup().lookup(LeaveAppSavable.class)==null){
                            ic.add(new LeaveAppSavable());
                }
            }
        
        
            
        
    }
    
    void reset(){
        
    
    //Start Date
    startDate = null;
    //Number Of Days
    numberOfDays=0.0;
    //End Date
    endDate = null;
    //Resumption Date
    resumeDate = null;
    //Remarks
    remarks = "";
    
    
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtEmployee = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtLeaveType = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jdcLeaveStarts = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jdcLeaveEnds = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jdcResume = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtRemarks = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jdcApplication = new com.toedter.calendar.JDateChooser();
        jcbApproved = new javax.swing.JCheckBox();
        jtNumberOfDays = new javax.swing.JFormattedTextField();

        jtEmployee.setEditable(false);
        jtEmployee.setText(org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jtEmployee.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel2.text")); // NOI18N

        jtLeaveType.setText(org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jtLeaveType.text")); // NOI18N
        jtLeaveType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtLeaveTypeMouseClicked(evt);
            }
        });
        jtLeaveType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtLeaveTypeKeyPressed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel4.text")); // NOI18N

        jdcLeaveEnds.setEnabled(false);
        jdcLeaveEnds.setFocusable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel5.text")); // NOI18N

        jtRemarks.setColumns(20);
        jtRemarks.setRows(5);
        jtRemarks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtRemarksKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtRemarks);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jLabel8.text")); // NOI18N

        jdcApplication.setEnabled(false);
        jdcApplication.setFocusable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jcbApproved, org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jcbApproved.text")); // NOI18N
        jcbApproved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbApprovedActionPerformed(evt);
            }
        });

        jtNumberOfDays.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jtNumberOfDays.setText(org.openide.util.NbBundle.getMessage(LeaveApplicationEditorTopComponent.class, "LeaveApplicationEditorTopComponent.jtNumberOfDays.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcbApproved)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtLeaveType, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jdcLeaveStarts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                    .addComponent(jdcLeaveEnds, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jdcResume, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtEmployee)
                                    .addComponent(jdcApplication, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtNumberOfDays, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(jLabel6))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtLeaveType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jdcLeaveStarts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jtNumberOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jdcLeaveEnds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jdcResume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jdcApplication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbApproved)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jtLeaveTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtLeaveTypeKeyPressed
        
        DialogDisplayer.getDefault().notify(new DialogDescriptor(lvtypeTC, "Select Leave Type"));
    }//GEN-LAST:event_jtLeaveTypeKeyPressed

    private void jcbApprovedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbApprovedActionPerformed
        cancelled = !jcbApproved.isSelected();
        try{
            updateable.setIsCanceled(cancelled);
        }catch(Exception ex){
            //NotifyUtil.error("Exception", "Exception", ex, false);
        }
        modify(8);
    }//GEN-LAST:event_jcbApprovedActionPerformed

    private void jtRemarksKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtRemarksKeyReleased
        
    }//GEN-LAST:event_jtRemarksKeyReleased

    private void jtLeaveTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtLeaveTypeMouseClicked
        DialogDisplayer.getDefault().notify(new DialogDescriptor(lvtypeTC, "Select Leave Type"));
    }//GEN-LAST:event_jtLeaveTypeMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jcbApproved;
    private com.toedter.calendar.JDateChooser jdcApplication;
    private com.toedter.calendar.JDateChooser jdcLeaveEnds;
    private com.toedter.calendar.JDateChooser jdcLeaveStarts;
    private com.toedter.calendar.JDateChooser jdcResume;
    private javax.swing.JTextField jtEmployee;
    private javax.swing.JTextField jtLeaveType;
    private javax.swing.JFormattedTextField jtNumberOfDays;
    private javax.swing.JTextArea jtRemarks;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        rslt.addLookupListener(this);
        resultChanged(new LookupEvent(rslt));
    }

    @Override
    public void componentClosed() {
        reset();
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
