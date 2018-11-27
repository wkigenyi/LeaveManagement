/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.leavemgt;

import java.text.SimpleDateFormat;
import java.util.Date;

import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;

/**
 *
 * @author Admin
 */
public class LeaveCycle {
    
    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Employees emp;
    int cycleNumber;
    String sCycleNumber;
    private String daysTaken;
    private Date startDate;
    private Date endDate;
    private String accumulated;
    private String sStart;
    private String sEnd;
    private String balance;
    public LeaveCycle(Date startDate,Date endDate, Employees emp, int cycleNumber){
        this.emp = emp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cycleNumber = cycleNumber;
        this.sCycleNumber = cycleNumber+"";
    }

    public String getsCycleNumber() {
        return sCycleNumber;
    }
    
    
    
    

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        startDate = emp.getDateOfEmployment();
        return startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @return the sStart
     */
    public String getsStart() {
        return sdf.format(startDate);
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the sEnd
     */
    public String getsEnd() {
        return sdf.format(endDate);
    }

    /**
     * @return the accumulated
     */
    public String getAccumulated() {
        Date upperDate;
        if(endDate.compareTo(new Date())>0){
            upperDate = new Date();
        }else{
            upperDate = endDate;
        }
        long diff = upperDate.getTime()-startDate.getTime();
        return Math.round(diff*1.75/(60*60*1000*24*30.4166666))+"";
        
    }

    /**
     * @return the daysTaken
     */
    public String getDaysTaken() {
        
        String sql = "select sum(NoOfDays) FROM lvwLeaveApplication where EmployeeID ="+emp.getEmployeeID()+" and LeaveTypeID =1 AND FromDate >='"+sdf.format(startDate)+"' and ToDate<='"+sdf.format(endDate)+"'";
        return Math.round(DataAccess.getDaysTaken(sql))+"";
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        
        Date upperDate;
        if(endDate.compareTo(new Date())>0){
            upperDate = new Date();
        }else{
            upperDate = endDate;
        }
        long diff = upperDate.getTime()-startDate.getTime();
        long acc = Math.round(diff*1.75/(60*60*1000*24*30.4166666));
        
        
        
        
        String sql = "select sum(NoOfDays) FROM lvwLeaveApplication where EmployeeID ="+emp.getEmployeeID()+" and LeaveTypeID =1 AND FromDate >='"+sdf.format(startDate)+"' and ToDate<='"+sdf.format(endDate)+"'";
        long taken = Math.round(DataAccess.getDaysTaken(sql));
        
        
        
        return (acc-taken)+"";
        
    }

    public Employees getEmp() {
        return emp;
    }
    
    
}
