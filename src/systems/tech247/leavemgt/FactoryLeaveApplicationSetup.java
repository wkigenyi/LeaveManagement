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
import systems.tech247.hr.Employees;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;


/**
 *
 * @author WKigenyi
 */
public class FactoryLeaveApplicationSetup extends ChildFactory<Object> {
    
    Employees emp;
    
    public FactoryLeaveApplicationSetup(Employees emp){
        this.emp = emp;
        
    }
    
    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        
        toPopulate.add(new AddTool(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new LeaveApplicationEditorTopComponent(emp);
                tc.open();
                tc.requestActive();
            }
        }));
        toPopulate.add(new SetupItem("Applications", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new EmployeeLeaveApplicationsTopComponent(emp);
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
    protected Node createNodeForKey(Object key) {
        
        Node node =  null;
        try {
            if(key instanceof SetupItem){
                node = new NodeSetupItem((SetupItem)key);
            }else if(key instanceof AddTool){
                node = new NodeAddTool((AddTool)key);
            }
            
            
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
