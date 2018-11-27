/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.LvwLeaveApplication;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;


/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeeLeaveApplications extends ChildFactory<Object> {
    
    Employees emp;
    
    public FactoryEmployeeLeaveApplications(Employees emp){
        this.emp = emp;
    }
    
    
    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        
        
        toPopulate.addAll(DataAccess.getEmployeeLeaveApplications(emp));
        
        
        
        
        
        
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Object key) {
        
        Node node =  null;
        if(key instanceof AddTool){
            node = new NodeAddTool((AddTool)key);
        }else{
            try {
                node = new NodeLeaveApplication((LvwLeaveApplication)key);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        
        
        return node;
    }
}
