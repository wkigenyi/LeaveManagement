/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.LvwLeave;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.pdr//PositionEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LeaveTypeEditorTopComponent",
        iconBase="systems/tech247/util/icons/settings.png", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)



@Messages({
    
    "CTL_LeaveEditorTopComponent=Leave Type Editor",
    "HINT_LeaveEditorTopComponent=Leave Type Editor"
})
public final class LeaveEditorTopComponent extends TopComponent{
    
    LvwLeave emp;
    
    DataAccess da = new DataAccess();
    InstanceContent ic = new InstanceContent();
    
    
    //Updatables
    String leavename = "";
    String prefix = "";
    double annualQuota =  0;
    double monthlyDeduction = 0;
    int maximum = 0;
    int maximumContiguous = 0;
    boolean paid = false;
    boolean balanceToCarry = false;
    boolean encashable = false;
    boolean quotacheck = false;
    
    
    
    
    
    
    
    
    
    EntityManager entityManager = DataAccess.getEntityManager();
    
    LvwLeave updatable;
    
    public LeaveEditorTopComponent(){
        this(null);
    }

    public LeaveEditorTopComponent(LvwLeave e) {
        initComponents();
        setName(Bundle.CTL_LeaveEditorTopComponent());
        setToolTipText(Bundle.HINT_LeaveEditorTopComponent());
        
        emp = e;
        
        if(null!=e){
            updatable = entityManager.find(LvwLeave.class, e.getLvwLeavePK());
        }
        
        
        //Fill in the employee details
        if(null!=e){
            fillPosition(e);
        }
        
        
        
        
        
        
        jtLeaveName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                if(leavename!=jtLeaveName.getText()){
                        leavename = jtLeaveName.getText();
                        try{
                            updatable.setLeaveName(leavename);
                        }catch(Exception ex){
                            
                        }
                        modify();
                    }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                
                
                   if(leavename!=jtLeaveName.getText()){
                        leavename = jtLeaveName.getText();
                        try{
                            updatable.setLeaveName(leavename);
                        }catch(Exception ex){
                            
                        }
                        modify();
                    }
                    
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                
                
                    if(leavename!=jtLeaveName.getText()){
                        leavename = jtLeaveName.getText();
                        try{
                            updatable.setLeaveName(leavename);
                        }catch(Exception ex){
                            
                        }
                        modify();
                    }
                    
               
                    
                
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
       
                
        
        
        

    }
    
    private class LeaveSavable extends AbstractSavable{
        
        LeaveSavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            if(null==emp){
                return "New Leave";
            }else{
                return emp.getLeaveName();
            }
        }
        
        LeaveEditorTopComponent tc(){
            return LeaveEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
            
            tc().ic.remove(this);
            unregister();
            //New Employee
            if(null==emp){
                
                        String insertSQL = "INSERT INTO [dbo].[lvwLeave]\n" +
"           ([Prefix]\n" +
"           ,[LeaveName]\n" +
"           ,[AnnualQuota]\n" +
"           ,[MonthlyDeduction]\n" +
"           ,[MaxLeaves]\n" +
"           ,[MaxContiguousLeaves]\n" +
"           ,[Paid]\n" +
"           ,[BalanceToCarry]\n" +
"           ,[Encashable]\n" +
"           ,[CoCode]\n" +
"           \n" +
"           )\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?,?,?,1)";
            Query query = entityManager.createNativeQuery(insertSQL);
            query.setParameter(1, prefix);
            query.setParameter(2, leavename);
            
           
            
            
            query.setParameter(3, annualQuota);
            
            
            query.setParameter(4, monthlyDeduction);
            
            
            query.setParameter(5, maximum);
            query.setParameter(6, maximumContiguous);
            query.setParameter(7, paid);
            query.setParameter(8, balanceToCarry);
            query.setParameter(9, encashable);
            
            
            

            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            }else{
                //Creating a new Employee
                entityManager.getTransaction().begin();
                entityManager.getTransaction().commit();
                
                    
                
            }
            
            UtilityLVW.loadLVWSetup();
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof LeaveSavable){
                LeaveSavable e = (LeaveSavable)o;
                return tc() == e.tc();
            }
            return false;        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }
        
    }
    
    public void modify(){
        
        
    
            if(leavename.length()<=1){
                StatusDisplayer.getDefault().setStatusText("Proper Position Name is Required");
            }else{
                if(getLookup().lookup(LeaveSavable.class)==null){
                            ic.add(new LeaveSavable());
                }
            }
        
        
            
        
    }
    
    void fillPosition(LvwLeave e){
        try{
                setName(e.getLeaveName());
                
                leavename = e.getLeaveName();
                jtLeaveName.setText(leavename);
                
                
                prefix = e.getPrefix();
                jtPrefix.setText(prefix);
                
                
              
                annualQuota = e.getAnnualQuota();
                jtAnnualQuota.setText(e.getAnnualQuota()+"");
                
                
                
                
                                
                
                
                
                
                
                
        }catch(Exception ex){
            
        }
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jtLeaveName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtPrefix = new javax.swing.JTextField();
        jtAnnualQuota = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jcbPaid = new javax.swing.JCheckBox();
        jcbNoQuotaCheck = new javax.swing.JCheckBox();
        jcbBalanceToCarry = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jcbPaid1 = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jLabel7.text")); // NOI18N

        jtLeaveName.setText(org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jtLeaveName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jLabel8.text")); // NOI18N

        jtPrefix.setText(org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jtPrefix.text")); // NOI18N

        jtAnnualQuota.setText(org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jtAnnualQuota.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jLabel10.text")); // NOI18N

        jTextField4.setText(org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jTextField4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbPaid, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jcbPaid.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbNoQuotaCheck, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jcbNoQuotaCheck.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbBalanceToCarry, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jcbBalanceToCarry.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jLabel11.text")); // NOI18N

        jTextField5.setText(org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jTextField5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jLabel12.text")); // NOI18N

        jTextField6.setText(org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jTextField6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbPaid1, org.openide.util.NbBundle.getMessage(LeaveEditorTopComponent.class, "LeaveEditorTopComponent.jcbPaid1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcbBalanceToCarry)
                    .addComponent(jcbNoQuotaCheck)
                    .addComponent(jcbPaid)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtLeaveName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtPrefix, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtAnnualQuota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jcbPaid1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtLeaveName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbNoQuotaCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtAnnualQuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbBalanceToCarry)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbPaid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbPaid1)
                .addContainerGap(133, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JCheckBox jcbBalanceToCarry;
    private javax.swing.JCheckBox jcbNoQuotaCheck;
    private javax.swing.JCheckBox jcbPaid;
    private javax.swing.JCheckBox jcbPaid1;
    private javax.swing.JTextField jtAnnualQuota;
    private javax.swing.JTextField jtLeaveName;
    private javax.swing.JTextField jtPrefix;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
