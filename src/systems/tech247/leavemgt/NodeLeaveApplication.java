/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.LvwLeaveApplication;


/**
 *
 * @author Admin
 */
public class NodeLeaveApplication extends  AbstractNode{
    
    LvwLeaveApplication app;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public NodeLeaveApplication(LvwLeaveApplication application) throws IntrospectionException {
        super(Children.LEAF,Lookups.singleton(application));
        this.app = application;
        

        setDisplayName(sdf.format(app.getApplicationDate()));
        setIconBaseWithExtension("systems/tech247/util/icons/document.png");
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        final LvwLeaveApplication application = getLookup().lookup(LvwLeaveApplication.class);
        
        
        
        
        Property leaveType = new PropertySupport("leaveType", String.class, "Leave Type", "What type Of Leave?", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return DataAccess.getLeaveByID(application.getLeaveTypeID()).getLeaveName();
            }
            
            @Override
            public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(leaveType);
        
        Property from = new PropertySupport("from", String.class, "From", "Leave Starts", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return sdf.format(application.getFromDate());
            }
            
            @Override
            public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        set.put(from);
        
        Property to = new PropertySupport("to", String.class, "To", "Leave Ends", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return sdf.format(application.getToDate());
            }
            
            @Override
            public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        set.put(to);
        
        Property days = new PropertySupport("days", String.class, "Number Of Days", "Days Taken", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return application.getNoOfDays()+"";
            }
            
            @Override
            public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(days);
        
        Property resume = new PropertySupport("resume", String.class, "Resumption Date", "Return Date", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return sdf.format(application.getResumptionDate());
            }
            
            @Override
            public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        set.put(resume);
        
        Property remarks = new PropertySupport("remarks", String.class, "Resumption Date", "Return Date", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return application.getRemarks();
            }
            
            @Override
            public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(remarks);
        
        
        
        
      
        try {
            
            
            Property isCancelled;
            
            
            
            
            
            
            isCancelled = new PropertySupport(
                    "isCancelled", 
                    Boolean.class, 
                    "Application Cancelled ?", 
                    "Is this application cancelled?", true, false) {
                @Override
                public Boolean getValue() throws IllegalAccessException, InvocationTargetException {
                    return application.getIsCanceled();
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    
                        //Confirm we are editing
                        //StatusDisplayer.getDefault().setStatusText("Weekly Off Changed");
                        //Show the change in the Property
                        //testValue.setValue(val);
                        
                        
                       
                        
                        
                    
                }
            };
            set.put(isCancelled);
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        
        
        
        
        
        
        
        
        
        
        
        /*
        try{
            Property isCOff = new PropertySupport.Reflection(schedule,Boolean.class,"getIsCOff",null);
            isCOff.setName("isCOff");
            set.put(isCOff);
        }catch(NoSuchMethodException ex){
            ErrorManager.getDefault();
        }*/
        
        
        

        
        
        
        sheet.put(set);
        return sheet;
    }
    
    

    
    
    
    
}
