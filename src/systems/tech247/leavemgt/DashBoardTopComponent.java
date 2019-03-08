/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponentGroup;
import org.openide.windows.WindowManager;
import systems.tech247.util.CetusUTL;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.leavemgt//DashBoard//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DashBoardTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.leavemgt.DashBoardTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_DashBoardAction",
//        preferredID = "DashBoardTopComponent"
//)
@Messages({
    "CTL_DashBoardAction=DashBoard",
    "CTL_DashBoardTopComponent=Leave Management",
    "HINT_DashBoardTopComponent=This is a DashBoard window"
})
public final class DashBoardTopComponent extends TopComponent {
    
    WindowManager wm = WindowManager.getDefault();
    
    TopComponentGroup group = WindowManager.getDefault().findTopComponentGroup("LVMGroup");

    public DashBoardTopComponent() {
        initComponents();
        setName(Bundle.CTL_DashBoardTopComponent());
        setToolTipText(Bundle.HINT_DashBoardTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        showLVM();
    }

    @Override
    protected void componentActivated() {
        super.componentActivated(); //To change body of generated methods, choose Tools | Templates.
        showLVM();
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
    
        void showLVM(){
        TopComponentGroup tcg = CetusUTL.currentTCG;
        if(tcg != null){
            tcg.close();
        }
//        //Close currently open TCs
//        for(TopComponent tc : WindowManager.getDefault().getRegistry().getOpened()){
//            tc.close();
//        }
        
        if(group != null){
            group.open();
            CetusUTL.currentTCG = group;
        }else{
            StatusDisplayer.getDefault().setStatusText("Leave Management Group is null");
        }
    }
}
