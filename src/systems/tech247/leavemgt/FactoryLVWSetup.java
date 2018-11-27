/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import systems.tech247.util.SetupItem;
import systems.tech247.util.NodeSetupItem;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;


/**
 *
 * @author WKigenyi
 */
public class FactoryLVWSetup extends ChildFactory<SetupItem> {
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {
        
        toPopulate.add(new SetupItem("Leave Types", Children.create(new FactoryLeaveTypes(true), true)));
        toPopulate.add(new SetupItem("Holidays", Children.create(new FactoryHolidays(Boolean.TRUE), true),"systems/tech247/util/icons/Calendar.png"));
        toPopulate.add(new SetupItem("Reports", Children.create(new FactoryLeaveReports(), true),"systems/tech247/util/icons/capex.png"));
        
        


//toPopulate.add(new SetupItem("Match Departments TO SUN"));
        /*toPopulate.add(new SetupItem("Salary Calculator",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent salaryTc = WindowManager.getDefault().findTopComponent("SalaryCalculatorTopComponent");
                salaryTc.open();
            }
        }));*/
        
        
        return true;
    }
    
    @Override
    protected Node createNodeForKey(SetupItem key) {
        
        Node node =  null;
        try {
            
            node = new NodeSetupItem(key);
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
