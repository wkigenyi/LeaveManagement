/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.awt.event.ActionEvent;
import systems.tech247.util.SetupItem;
import systems.tech247.util.NodeSetupItem;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;


/**
 *
 * @author WKigenyi
 */
public class FactoryLVWSetup extends ChildFactory<SetupItem> {
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {
        
        toPopulate.add(new SetupItem("Leave Types", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new LeaveTypeTopComponent("view");
                tc.open();
                tc.requestActive();
            }
        }));
        
        toPopulate.add(new SetupItem("Import Leave", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = WindowManager.getDefault().findTopComponent("ImportLeaveTopComponent");
                tc.open();
                tc.requestActive();
            }
        }));
        
        
        


//toPopulate.add(new SetupItem("Match Departments TO SUN"));
        /*toPopulate.add(new SetupItem("Salary Calculator",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent salaryTc = WindowManager.getDefault().findTopComponent("SalaryCalculatorTopComponent");
                salaryTc.open();
            }
        }));*/
        
        
        return true;
    }
    
    @Override
    protected Node createNodeForKey(SetupItem key) {
        
        Node node =  null;
        try {
            
            node = new NodeSetupItem(key);
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
