/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.util.Date;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.PtmHolidays;



/**
 *
 * @author Wilfred
 */
public class FactoryHolidays extends ChildFactory<PtmHolidays>{

    
    
    
    

    

    
    public FactoryHolidays(){
        
        
    }
    
    @Override
    protected boolean createKeys(List<PtmHolidays> toPopulate) {
        
        //Populate the list of child entries
        List<PtmHolidays> list = DataAccess.getHolidays();
        for(PtmHolidays holiday : list){
            if(holiday.getRecurrent()){
                toPopulate.add(holiday);
            }else{
                Date date = new Date();
                if(date.getYear() == holiday.getDateOf().getYear()){
                    toPopulate.add(holiday);
                }
            }
        }
        //toPopulate.addAll(DataAccess.getHolidays());
        
        //return true since we are set
        return true;
    }
    
    @Override
    protected Node createNodeForKey(PtmHolidays key){
        Node node = null;
        
        try{
            node = new NodeHoliday(key);
        }catch(Exception ex){
            
        }
        
        return node;
    }
    

    
}
