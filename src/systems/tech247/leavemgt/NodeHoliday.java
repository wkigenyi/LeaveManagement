/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.hr.PtmHolidays;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Wilfred
 */
public class NodeHoliday extends AbstractNode{
    
        PtmHolidays unit;
        
        public NodeHoliday(PtmHolidays emp){
            this(new InstanceContent(),emp);
        }
        
        private NodeHoliday (InstanceContent ic, final PtmHolidays unit){
            super(Children.LEAF, new AbstractLookup(ic));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ic.add(unit);
            ic.add(new CapEditable() {
                @Override
                public void edit() {
                    TopComponent tc = new HolidayEditorTopComponent(unit);
                    tc.open();
                    tc.requestActive();
                }
            });
            setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
            setDisplayName(unit.getHolidayName());
        }

//        @Override
//        public Action getPreferredAction() {
//            
//            return new AbstractAction() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                
//                    TopComponent tc = new HolidayEditorTopComponent(unit);
//                    tc.open();
//                    tc.requestActive();
//                   
//                }
//            };
//        }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Set set = Sheet.createPropertiesSet();
        final PtmHolidays holiday = getLookup().lookup(PtmHolidays.class);
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy");
        Property date = new PropertySupport("date", String.class, "Date", "Date", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return sdf.format(holiday.getDateOf());
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Property recurrent = new PropertySupport("recurrent", Boolean.class, "Recurrent", "Recurrent", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return holiday.getRecurrent();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(recurrent);
        set.put(date);
        sheet.put(set);
        return sheet;
    }
        
}
