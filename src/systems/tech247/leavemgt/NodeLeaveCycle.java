/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;

/**
 *
 * @author Admin
 */
public class NodeLeaveCycle extends BeanNode<LeaveCycle>{
    
    public NodeLeaveCycle(LeaveCycle bean) throws IntrospectionException{
        super(bean);
        setDisplayName(""+bean.getEmp().getSurName()+" "+bean.getEmp().getOtherNames());
        setIconBaseWithExtension("systems/tech247/util/icons/person.png");
    }
    
}
