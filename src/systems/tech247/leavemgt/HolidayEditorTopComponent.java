/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.PtmHolidays;
/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.pdr//PositionEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "HolidayEditorTopComponent",
        iconBase="systems/tech247/util/icons/Calendar.png", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)



@Messages({
    
    "CTL_HolidayEditorTopComponent=Holiday Editor",
    "HINT_HolidayEditorTopComponent=Holiday Editor"
})
public final class HolidayEditorTopComponent extends TopComponent{
    
    PtmHolidays emp;
    
    DataAccess da = new DataAccess();
    InstanceContent ic = new InstanceContent();
    
    
    //Updatables
    String leavename = "";
    Date date = new Date();
    boolean recurrent = false;
    
    
    
    
    
    
    
    
    
    
    EntityManager entityManager = DataAccess.getEntityManager();
    
    PtmHolidays updatable;
    
    public HolidayEditorTopComponent(){
        this(null);
    }

    public HolidayEditorTopComponent(PtmHolidays e) {
        initComponents();
        setName(Bundle.CTL_HolidayEditorTopComponent());
        setToolTipText(Bundle.HINT_HolidayEditorTopComponent());
        
        emp = e;
        
        if(null!=e){
            updatable = entityManager.find(PtmHolidays.class, e.getHolidayID());
        }
        
        
        //Fill in the employee details
        if(null!=e){
            fillPosition(e);
        }
        
        
        
        
        
        
        jtHolidayName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                if(leavename!=jtHolidayName.getText()){
                        leavename = jtHolidayName.getText();
                        try{
                            updatable.setHolidayName(leavename);
                        }catch(Exception ex){
                            
                        }
                        modify();
                    }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                
                
                   if(leavename!=jtHolidayName.getText()){
                        leavename = jtHolidayName.getText();
                        try{
                            updatable.setHolidayName(leavename);
                        }catch(Exception ex){
                            
                        }
                        modify();
                    }
                    
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                
                
                    if(leavename!=jtHolidayName.getText()){
                        leavename = jtHolidayName.getText();
                        try{
                            updatable.setHolidayName(leavename);
                        }catch(Exception ex){
                            
                        }
                        modify();
                    }
                    
               
                    
                
            }
        });
        
        jdcDate.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if("date".equals(evt.getPropertyName()) && evt.getSource()==jdcDate){
                    date = jdcDate.getDate();
                    try{
                        updatable.setDateOf(date);
                        updatable.setDayOf(date.getDate());
                        updatable.setMonthOf(date.getMonth()+1);
                        
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
       
                
        
        
        

    }
    
    private class HolidaySavable extends AbstractSavable{
        
        HolidaySavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            if(null==emp){
                return "New Holiday";
            }else{
                return emp.getHolidayName();
            }
        }
        
        HolidayEditorTopComponent tc(){
            return HolidayEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
            
            
            
            tc().ic.remove(this);
            unregister();
            //New Employee
            if(null==emp){
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                        String insertSQL = "INSERT INTO [dbo].[ptmHolidays]\n" +
"           ([MonthOf]\n" +
"           ,[DayOf]\n" +
"           ,[HolidayName]\n" +
"           ,[DateOf]\n" +
"           ,[Recurrent]\n" +
"           ,[Valid])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?)";
            Query query = entityManager.createNativeQuery(insertSQL);
            query.setParameter(2, date.getDate());
            query.setParameter(1, date.getMonth()+1);
            query.setParameter(3, leavename);
            query.setParameter(4, sdf.format(date));
            query.setParameter(5, recurrent);
            query.setParameter(6, true);
           
            
            

            
            
            

            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            
            resetFields();
            }else{
                //Creating a new Employee
                entityManager.getTransaction().begin();
                entityManager.getTransaction().commit();
                
                    
                
            }
            
            UtilityLVW.loadLVWSetup();
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof HolidaySavable){
                HolidaySavable e = (HolidaySavable)o;
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
                StatusDisplayer.getDefault().setStatusText("Proper Holiday Name is Required");
            }else if(null == date){
                StatusDisplayer.getDefault().setStatusText("Specify The Date");
            }else{
                if(getLookup().lookup(HolidaySavable.class)==null){
                            ic.add(new HolidaySavable());
                }
            }
        
        
            
        
    }
    
    void fillPosition(PtmHolidays e){
        try{
                setName(e.getHolidayName());
                
                leavename = e.getHolidayName();
                jtHolidayName.setText(leavename);
                
                jdcDate.setDate(e.getDateOf());
                jcbRecurrent.setSelected(e.getRecurrent());
                
                
                
                
                                
                
                
                
                
                
                
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
        jtHolidayName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jcbRecurrent = new javax.swing.JCheckBox();
        jdcDate = new com.toedter.calendar.JDateChooser();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(HolidayEditorTopComponent.class, "HolidayEditorTopComponent.jLabel7.text")); // NOI18N

        jtHolidayName.setText(org.openide.util.NbBundle.getMessage(HolidayEditorTopComponent.class, "HolidayEditorTopComponent.jtHolidayName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(HolidayEditorTopComponent.class, "HolidayEditorTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbRecurrent, org.openide.util.NbBundle.getMessage(HolidayEditorTopComponent.class, "HolidayEditorTopComponent.jcbRecurrent.text")); // NOI18N
        jcbRecurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbRecurrentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcbRecurrent)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtHolidayName, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(jdcDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtHolidayName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRecurrent)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcbRecurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbRecurrentActionPerformed
        recurrent = jcbRecurrent.isSelected();
        try{
            updatable.setRecurrent(recurrent);
        }catch(NullPointerException ex){
            
        }
        modify();
    }//GEN-LAST:event_jcbRecurrentActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JCheckBox jcbRecurrent;
    private com.toedter.calendar.JDateChooser jdcDate;
    private javax.swing.JTextField jtHolidayName;
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
    
    void resetFields(){
        recurrent = false;
        jcbRecurrent.setSelected(recurrent);
        leavename = "";
        jtHolidayName.setText(leavename);
        date = new Date();
        jdcDate.setDate(date);
       
    }

    
}
