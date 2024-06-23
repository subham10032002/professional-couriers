//package com.tpcindia.professional_couriers.webcode;
//
//
//
//         /*
//          * To change this template, choose Tools | Templates
//          * and open the template in the editor.
//          */
//         package Model;
//
//import Admin.MenuBKO_Bean;
//import Bean.Acttracker_Bean;
//import Bean.Comptrans;
//import Bean.Users_Bean;
////import Bean.ContactDetails_Bean;
//import javax.faces.bean.ManagedBean;
//////import javax.faces.application.FacesMessage;
////import javax.faces.context.FacesContext;
////import java.io.Serializable;
//import java.util.*;
//        import DataConnect.*;
//        import Helper.*;
////import com.sun.org.apache.bcel.internal.generic.AALOAD;
//        import javax.faces.bean.ViewScoped;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpServletRequest;
////import javax.servlet.RequestDispatcher;
////import javax.servlet.http.*;
//
//@ManagedBean(name = "users_Log")
//@ViewScoped
//public class Users_Login {
//
//    private String enPass, message, finYear, loginId, password, menuyes, dataBase, dataPool, query, message1, designation, brcode, userName;
//    private ArrayList finYearLi = new ArrayList();
//    DataFetch_First fetch;
//    private Comptrans companyTrans;
//    private Acttracker_Bean activDetails = new Acttracker_Bean();
//    PassSer_First
//    = new PassSer_First();
//    private MenuBKO_Bean menuFetch = new MenuBKO_Bean();
//    DataConnect_First dataPush;
//    Utility_First dateFetch = new Utility_First();
//    String browserName = "";
//    String browserVer = "";
//    //private LoggedUser logus=new LoggedUser();
//
//    public Users_Login() {
//        System.gc();
//
//    }
//
//    public ArrayList getfinYearLi() {
//
//        this.dataPool = "FirstOneSql";
////this.dataPool = "FirstOneSqlCare";
//        //     this.dataPool = "FirstOneSqlSync";
////this.dataPool = "FirstOneSqlPE";
//        try {
//            fetch = new DataFetch_First("Temp", dataPool);
//            finYearLi = fetch.getArrayString("select  aliasYear from comptrans order by Status");
//            fetch.closeAll();
//            menuyes = pas.encrypt("Yes");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            fetch.closeAll();
//        }
//
//        return getFinYearLi();
//
//    }
//
//    public void chkBrowser() {
//
//        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        final String userAgent = request.getHeader("user-agent");
//
//        if (userAgent.contains("Chrome")) { //checking if Chrome
//            String substring = userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0];
//            browserName = substring.split("/")[0];
//            browserVer = substring.split("/")[1];
//            message = "Login using Mozilla";
//            message1 = "Login Success, Use Mozilla";
//        } else if (userAgent.contains("Firefox")) {  //Checking if Firefox
//            String substring = userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0];
//            browserName = substring.split("/")[0];
//            browserVer = substring.split("/")[1];
//            double vers = Double.valueOf(browserVer);
//            if (brcode.equalsIgnoreCase("HRD")) {
//                if (vers >= 50 && vers <= 78) {
//                    message = "Success";
//                } else {
//                    message = "Login using Mozilla";
//                    message1 = "Login Success, Use Mozilla Version between 50 and 74";
//                }
//                if (userName.equalsIgnoreCase("BPK") || userName.equalsIgnoreCase("Biju") || userName.equalsIgnoreCase("anu")) {
//                    message = "Success";
//                }
//            }
//
//        } else {  //Checking if Firefox
//
//            browserName = "Other Browser";
//            browserVer = "0";
//
//            message = "Login using Mozilla";
//            message1 = "Login Success, Use Mozilla";
//        }
//        ////System.out.println(browserName);
//        //  //System.out.println(browserVer);
//    }
//
//    public String loginCheck() {
//        String dbase, status;
//        message = "Error";
//        getfinYearLi();
//        FacesContext context = FacesContext.getCurrentInstance();
//        fetch = new DataFetch_First("Temp", dataPool);
//        companyTrans = fetch.compTransScan("Select * from comptrans where aliasyear='" + getFinYear() + "'", 1);
//        //fetch.closeAll();
//
//        dataBase = companyTrans.getDataBaseName();
//        //fetch = new DataFetch_First(dataPool);
//        enPass = pas.encrypt(getPassword().trim());
//        ArrayList<Users_Bean> storedAcc = new ArrayList<Users_Bean>();
//        query = "Select * from " + dataBase + ".users where loginid='" + getLoginId() + "' and password='" + enPass + "' and status='active'";
//        storedAcc = fetch.usersScanMap(query, 1);
//        // //System.out.println(query);
//        if (storedAcc.size() > 0) {
//
//            message = "Success";
//            query = "Select cn.designation from " + dataBase + ".users us," + dataBase + ".ContactDetails cn where us.code=cn.code and  us.code=" + storedAcc.get(0).getCode() + " ";
//            designation = fetch.findString(query, storedAcc.get(0).getName());
//            /* status = fetch.findString("Select status from comptrans where aliasyear='" + getFinYear() + "'");
//             if (status.equals("Active")) {
//             dbase = "FirstOne";
//             } else {
//             dbase = "FirstOneBk";
//
//             }*/
//            brcode = fetch.findString("select subbranchCode from " + dataBase + ".companymaster where DefaultTyp='Yes' ", dataBase).trim();
//            query = "select * from " + dataBase + ".menu where usercode=" + storedAcc.get(0).getCode();
//            menuFetch = fetch.menuScan(query, 1);
//            String oldDatabase = "";
//            int sln = fetch.findInt("select slno from " + dataBase + ".comptrans where datbase='" + dataBase + "'", dataBase);
//            if (sln > 1) {
//                sln = sln - 1;
//                oldDatabase = fetch.findString("select datbase from " + dataBase + ".comptrans where slno=" + sln, String.valueOf(sln));
//            }
//
//            //  addSes.setDataBase(dbase);
//            //    addSes.setName(storedAcc.get(0).getName());
//            //   addSes.setFinYear(finYear);
//            //  brcode="HRD";
//            //   //System.out.println(brcode);
//            userName = storedAcc.get(0).getLoginId().trim();
//            //System.out.println(userName);
//            context.getExternalContext().getSessionMap().put("user", storedAcc.get(0).getName());
//            context.getExternalContext().getSessionMap().put("Loginid", storedAcc.get(0).getLoginId());
//            context.getExternalContext().getSessionMap().put("brCode", brcode.trim());
//            // context.getExternalContext().getSessionMap().put("brCode", "TMR");
//            context.getExternalContext().getSessionMap().put("dataBase", companyTrans.getDataBaseName());
//            context.getExternalContext().getSessionMap().put("olddataBase", oldDatabase);
//            context.getExternalContext().getSessionMap().put("FinYear", getFinYear());
//            context.getExternalContext().getSessionMap().put("userCode", storedAcc.get(0).getCode());
//            context.getExternalContext().getSessionMap().put("privlage", storedAcc.get(0).getPrivlage());
//            context.getExternalContext().getSessionMap().put("loginStatus", companyTrans.getStatus());
//            context.getExternalContext().getSessionMap().put("menu", menuFetch);
//            context.getExternalContext().getSessionMap().put("menuyes", menuyes);
//            context.getExternalContext().getSessionMap().put("dataPool", dataPool);
//            context.getExternalContext().getSessionMap().put("seleCust", 0);
//            context.getExternalContext().getSessionMap().put("currentmodule", "Login");
//            context.getExternalContext().getSessionMap().put("DateTime", dateFetch.getCurrentDateTime());
//            chkBrowser();
//            //FirstOneSql.dbo  //FirstOne
//        }
//        fetch.closeAll();
//        activDetails.setTablename("Logon");
//        activDetails.setActivity("Login");
//        activDetails.setIp("0.0.0.0");
//        FacesMessage doneMessage = null;
//        // chkBrowser();
//        //  message="Success";
//        if (getMessage().equals("Success")) {
//
//            activDetails.setUserCode(storedAcc.get(0).getCode());
//            activDetails.setUserName(storedAcc.get(0).getName());
//            activDetails.setTablename("Loged in");
//            activDetails.setNarration(browserName + " version: " + browserVer);
//            activDetails.setStatus("Active");
//            // dataPush = new DataConnect_First(1);
//            //dataPush.activityTrack_Add(activDetails);
//            //  dataPush.closeAll();
//
//            message1 = " Welcome : " + storedAcc.get(0).getName();
//            LogSettings_First aa = new LogSettings_First(storedAcc.get(0).getLastName(), " Branch : " + brcode.trim() + " Designation: " + designation, " Browser: " + browserName + " version: " + browserVer);
//
//            doneMessage = new FacesMessage(message1);
//        } else {
//            if (message.equals("Login using Mozilla")) {
//                message = message1;
//                doneMessage = new FacesMessage(message1);
//                LogSettings_First aa = new LogSettings_First(storedAcc.get(0).getLastName(), "Login Fail- incompaitable Browser :, Branch : " + brcode.trim() + " Designation: " + designation, " Browser: " + browserName + " version: " + browserVer);
//                dataBase = "";
//                //  LogSettings_First aa = new LogSettings_First(storedAcc.get(0).getLastName(), "Login Fail : Wrong Browser ", browserName + " version: " + browserVer);
//            } else {
//                // LogSettings_First aa = new LogSettings_First(storedAcc.get(0).getLastName(), "Login Fail: Password mismatch", browserName + " version: " + browserVer);
//                message1 = "Password/Login Mismatch";
//                doneMessage = new FacesMessage(message1);
//                dataBase = "";
//            }
//
//        }
//
//        //  RequestDispatcher rd = request.getRequestDispatcher("/path.to.your.jsp");
//        FacesContext.getCurrentInstance().addMessage(null, doneMessage);
//        return getMessage();
//    }
//
//    /**
//     * @return the finYearLi
//     */
//    public ArrayList getFinYearLi() {
//        return finYearLi;
//    }
//
//    /**
//     * @return the finYear
//     */
//    public String getFinYear() {
//        return finYear;
//    }
//
//    /**
//     * @param finYear the finYear to set
//     */
//    public void setFinYear(String finYear) {
//        this.finYear = finYear;
//    }
//
//    /**
//     * @return the loginId
//     */
//    public String getLoginId() {
//        return loginId;
//    }
//
//    /**
//     * @param loginId the loginId to set
//     */
//    public void setLoginId(String loginId) {
//        this.loginId = loginId;
//    }
//
//    /**
//     * @return the password
//     */
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * @param password the password to set
//     */
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
