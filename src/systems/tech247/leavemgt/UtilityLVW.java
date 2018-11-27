/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import org.openide.explorer.ExplorerManager;


/**
 *
 * @author Wilfred
 */
public class UtilityLVW {
    
    //load PDR Setup
    public static ExplorerManager emLVWsetup = new ExplorerManager();
    public static void loadLVWSetup(){
        emLVWsetup.setRootContext(new NodeLVWSetup());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
