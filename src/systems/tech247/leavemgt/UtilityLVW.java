/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import org.openide.explorer.ExplorerManager;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;


/**
 *
 * @author Wilfred
 */
public class UtilityLVW implements Lookup.Provider {
    
    
    static UtilityLVW instance;
    public static InstanceContent content = new InstanceContent();
    Lookup lookup = new AbstractLookup(content);
    //load PDR Setup
    public static ExplorerManager emLVWsetup = new ExplorerManager();
    
    public static UtilityLVW getInstance() {
        if(instance==null){
            instance = new UtilityLVW();
        }
        return instance;
    }
    
    
    
    public static void loadLVWSetup(){
        emLVWsetup.setRootContext(new NodeLVWSetup());
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
