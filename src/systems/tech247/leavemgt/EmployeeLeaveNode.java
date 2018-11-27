/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.windows.TopComponent;
import systems.tech247.hr.Employees;

/**
 *
 * @author Admin
 */
public class EmployeeLeaveNode extends AbstractNode{
    Employees emp;
    
    public EmployeeLeaveNode(Employees emp){
        super(Children.create(new FactoryEmployeeLeaveDetails(emp), true));
        this.emp = emp;
        setIconBaseWithExtension("systems/tech247/util/icons/person.png");
            setDisplayName(emp.getSurName()+" "+emp.getOtherNames());
    }

    @Override
    public Action getPreferredAction() {
        final TopComponent tc = new LeaveDashBoardTopComponent(emp);
        return new  AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tc.open();
                tc.requestActive();
            }
        };
    }
    
    
    
}
