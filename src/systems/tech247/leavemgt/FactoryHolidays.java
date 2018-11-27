/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.PtmHolidays;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;



/**
 *
 * @author Wilfred
 */
public class FactoryHolidays extends ChildFactory<Object>{

    
    
    
    boolean add;

    

    
    public FactoryHolidays(boolean add){
        this.add = add;
        
    }
    
    @Override
    protected boolean createKeys(List<Object> list) {
        if(add){
            list.add(new AddTool(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  TopComponent editor = new HolidayEditorTopComponent();
                    editor.open();
                    editor.requestActive();
                }
            }));
            
        }
        //Populate the list of child entries
        list.addAll(DataAccess.getHolidays());
        
        //return true since we are set
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Object key){
        Node node = null;
        
        if(key instanceof PtmHolidays){
            
            node = new HolidayNode((PtmHolidays)key);
        }else if(key instanceof AddTool){
            node = new NodeAddTool((AddTool)key);
        }
        
        return node;
    }
    
    private class HolidayNode extends AbstractNode{
        
        private final InstanceContent instanceContent;
        PtmHolidays unit;
        
        public HolidayNode(PtmHolidays emp){
            this(new InstanceContent(),emp);
        }
        
        private HolidayNode (InstanceContent ic, PtmHolidays unit){
            super(Children.LEAF, new AbstractLookup(ic));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            instanceContent = ic;
            instanceContent.add(unit);
            this.unit = unit;
            setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
            setDisplayName(sdf.format(unit.getDateOf())+" - "+unit.getHolidayName());
        }

        @Override
        public Action getPreferredAction() {
            
            return new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                
                    TopComponent tc = new HolidayEditorTopComponent(unit);
                    tc.open();
                    tc.requestActive();
                   
                }
            };
        }
        
        
        
        
    
}
    
}
