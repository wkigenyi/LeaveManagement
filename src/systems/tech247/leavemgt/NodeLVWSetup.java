/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import systems.tech247.dbaccess.DataAccess;


/**
 *
 * @author Admin
 */
public class NodeLVWSetup extends  AbstractNode{

    public NodeLVWSetup() {
        super(Children.create(new FactoryLVWSetup(), true));
        setDisplayName(DataAccess.getDefaultCompany().getCompanyName());
        setIconBaseWithExtension("systems/tech247/util/icons/company.png");
    }

    
    
    
    
}
