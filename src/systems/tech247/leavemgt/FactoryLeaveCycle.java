/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.beans.IntrospectionException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import systems.tech247.hr.Employees;

/**
 *
 * @author Admin
 */
public class FactoryLeaveCycle extends ChildFactory<LeaveCycle>{
    
    List<Employees> emp;
    
    public FactoryLeaveCycle(List<Employees> emp){
        this.emp =emp; 
    }
    


    @Override
    protected boolean createKeys(List<LeaveCycle> list) {
        
        for(Employees employee: emp ){
            
            
            Calendar cal = Calendar.getInstance();
        int cycleCounter = 1;
        cal.setTime(employee.getDateOfEmployment());
        Date start = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, -1);
        Date end = cal.getTime();
        
        list.add(new LeaveCycle(start, end, employee,cycleCounter));
        
        Date today = new Date();
        
        while(today.compareTo(end)>0){
            cal.add(Calendar.DATE, 1);
            start = cal.getTime();
            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.DATE, -1);
            end = cal.getTime();
            cycleCounter = cycleCounter+1;
            list.add(new LeaveCycle(start, end, employee, cycleCounter));
        }
            
        }
        
        
        
        
        
        
        
        return true;
    }

    @Override
    protected Node createNodeForKey(LeaveCycle key) {
        try {
            return new NodeLeaveCycle(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
    
    
    
}
