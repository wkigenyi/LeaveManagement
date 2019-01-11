/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.hr.LvwLeave;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Wilfred
 */
public class NodeLeaveType extends AbstractNode {
    private final InstanceContent instanceContent;
        LvwLeave unit;
        
        public NodeLeaveType(LvwLeave emp){
            this(new InstanceContent(),emp);
        }
        
        private NodeLeaveType (InstanceContent ic, final LvwLeave unit){
            super(Children.LEAF, new AbstractLookup(ic));
            instanceContent = ic;
            instanceContent.add(unit);
            this.unit = unit;
            
            ic.add(new CapEditable() {
                @Override
                public void edit() {
                    TopComponent tc = new LeaveEditorTopComponent(unit);
                    tc.open();
                    tc.requestActive();
                }
            });
            
            
            setIconBaseWithExtension("systems/tech247/util/icons/document.png");
            setDisplayName(unit.getLeaveName());
        }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Set set = Sheet.createPropertiesSet();
        final LvwLeave leave = getLookup().lookup(LvwLeave.class);
        Property quota = new PropertySupport("quota", String.class, PROP_DISPLAY_NAME, PROP_SHORT_DESCRIPTION, true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return leave.getAnnualQuota();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(quota);
        
        Property paid = new PropertySupport("paid", Boolean.class, PROP_DISPLAY_NAME, PROP_SHORT_DESCRIPTION, true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return leave.getPaid();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(paid);
        
        Property encanshed = new PropertySupport("encanshed", Boolean.class, PROP_DISPLAY_NAME, PROP_SHORT_DESCRIPTION, true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return leave.getEncashable();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(encanshed);
        
        Property gender = new PropertySupport("gender", String.class, PROP_DISPLAY_NAME, PROP_SHORT_DESCRIPTION, true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return leave.getApplicableToGender();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(gender);
        
        
        sheet.put(set);
        
        return sheet; //To change body of generated methods, choose Tools | Templates.
    }

        
}
