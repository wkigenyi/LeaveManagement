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

    
    
    
    

    

    
    public FactoryHolidays(){
        
        
    }
    
    @Override
    protected boolean createKeys(List<Object> list) {
        
        //Populate the list of child entries
        list.addAll(DataAccess.getHolidays());
        
        //return true since we are set
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Object key){
        Node node = null;
        
        if(key instanceof PtmHolidays){
            
            node = new NodeHoliday((PtmHolidays)key);
        }else if(key instanceof AddTool){
            node = new NodeAddTool((AddTool)key);
        }
        
        return node;
    }
    

    
}
