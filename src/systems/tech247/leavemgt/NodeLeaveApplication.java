/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.LvwLeaveApplication;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;


/**
 *
 * @author Admin
 */
public class NodeLeaveApplication extends  AbstractNode implements LookupListener,PropertyChangeListener{
    
    LvwLeaveApplication app;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Lookup.Result<NodeRefreshLeaveApplication> rslt = UtilityLVW.getInstance().getLookup().lookupResult(NodeRefreshLeaveApplication.class);
    
    public NodeLeaveApplication(LvwLeaveApplication app) throws IntrospectionException{
        this(app, new InstanceContent());
    }
    
    public NodeLeaveApplication(LvwLeaveApplication application, InstanceContent ic) throws IntrospectionException {
        super(Children.LEAF,new AbstractLookup(ic));
        this.app = application;
        ic.add(application);
        ic.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new LeaveApplicationEditorTopComponent(app);
                tc.open();
                tc.requestActive();
            }
        });
        
        ic.add(new CapDeletable() {
            @Override
            public void delete() {
                Object result = DialogDisplayer.getDefault().notify(new NotifyDescriptor.Confirmation("Delete this application?"));
                if(result==NotifyDescriptor.YES_OPTION){
                    DataAccess.entityManager.getTransaction().begin();
                    app = DataAccess.entityManager.find(LvwLeaveApplication.class, app.getRecordNo());
                    DataAccess.entityManager.remove(app);
                    DataAccess.entityManager.getTransaction().commit();
                    UtilityLVW.content.set(Arrays.asList(new NodeRefreshLeaveApplication()), null);
                }
            }
        });
        
        rslt.addLookupListener(this);
        
        
        
        setDisplayName(application.getLeaveTypeID().getLeaveName());
        setIconBaseWithExtension("systems/tech247/util/icons/document.png");
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        final LvwLeaveApplication application = getLookup().lookup(LvwLeaveApplication.class);
        
        
        
        
        
        
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
                return application.getNoOfDays();
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

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshLeaveApplication> rslt = (Lookup.Result<NodeRefreshLeaveApplication>)ev.getSource();
        for(NodeRefreshLeaveApplication nrp:rslt.allInstances()){
            app = DataAccess.entityManager.find(LvwLeaveApplication.class, app.getRecordNo());
            try{
            setDisplayName(app.getLeaveTypeID().getLeaveName());
            }catch(NullPointerException ex){
                
            }
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        String propName = evt.getPropertyName();
        Sheet.Set propertySet = getSheet().get(Sheet.PROPERTIES);
        if(propertySet != null){
            if(propertySet.get(propName)!=null){
                firePropertyChange(propName, evt.getOldValue(), evt.getNewValue());
            }else{
                //
            }
        }
    }
    
    

    
    
    
    
}
