/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import systems.tech247.api.NodeRefreshEvent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.LvwLeaveApplication;
import systems.tech247.util.AddTool;
import systems.tech247.util.CetusUTL;
import systems.tech247.util.NodeAddTool;


/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeeLeaveApplications extends ChildFactory<Object> implements LookupListener {
    
    Employees emp;
    Lookup.Result<NodeRefreshLeaveApplication> rslt = UtilityLVW.getInstance().getLookup().lookupResult(NodeRefreshLeaveApplication.class);
    
    public FactoryEmployeeLeaveApplications(Employees emp){
        this.emp = emp;
        rslt.addLookupListener(this);
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

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshLeaveApplication> rslt = (Lookup.Result<NodeRefreshLeaveApplication>)ev.getSource();
        for(NodeRefreshLeaveApplication nrp:rslt.allInstances()){
            refresh(true);
        }
    }
}
