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
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import systems.tech247.hr.Employees;


/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeeLeaveDetails extends ChildFactory<SetupItem> {
    
    Employees emp;
    
    public FactoryEmployeeLeaveDetails(Employees emp){
        this.emp = emp;
    }
    
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {
        toPopulate.add(new SetupItem("Leave Taken",Children.LEAF));
        toPopulate.add(new SetupItem("Leave Application", Children.create(new FactoryLeaveApplicationSetup(emp), true)));
        //Leave applications show:
        //1.Leaves Applied for and their status
        //2.Summary Of Leave, accrued
        toPopulate.add(new SetupItem("Leave Transactions", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show How Leaves Have been accumulating
            }
        }));
        toPopulate.add(new SetupItem("Process Leave", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Process Leave For Employees
            }
        }));
        
        toPopulate.add(new SetupItem("Leave Encashment", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Process Leave For Employees
            }
        }));
        
        
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
