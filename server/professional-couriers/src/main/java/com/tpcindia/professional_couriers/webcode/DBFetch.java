//package com.tpcindia.professional_couriers.webcode;
//
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package DataConnect;
//
//import Admin.MenuBKO_Bean;
//import HR.*;
//import Branch.QuotMaster_Bean;
//import java.sql.*;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpSession;
//import java.util.*;
//import Bean.*;
//import BeanCare.*;
//import BeanRepo.*;
//import BeanAcc.*;
//import Vehicle.*;
//import BeanStores.*;
//import Branch.Cash_CounterBooking_Bean;
//import Branch.ConsigneeMaster_Bean;
//import Branch.HoldingTrans_Bean;
//import Branch.Holding_Bean;
//import Branch.BillingCustInfo_Bean;
//import Branch.Billing_Branch_Bean;
//import GSTN.EwayBill_Followup_Bean;
//import GSTN.GstGenDe_Bean;
//import GSTN.GstNDetails_Addon_Bean;
//import GSTN.GstNDetails_Bean;
//import Operation.Hub_Return_Bean;
//import Operation.repo_Opt_Bean;
//import Reports.Repo_Opt_TTS;
//import java.math.BigDecimal;
//import java.math.BigInteger;
////5805286, 5291
//
///**
// *
// * @author biju
// */
//public class DataFetch_First {
//
//    Connection con;
//    ResultSet rs, rsTem;
//    Statement stat;
//    PreparedStatement pstat;
//    private String userName = "intial", dataBase, dataPool, calledMethod, errorQuery = "New", currentmodule;
//    private int criticalError = 0;
//    DBConnection_First dbconn = new DBConnection_First();
//    // private static final Logger logger = Logger.getLogger(DataFetch_First.class);
//    FacesContext ctx = FacesContext.getCurrentInstance();
//    HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
//    private static BigDecimal ZERO = new BigDecimal(0.00);
//
//    public DataFetch_First(String start, String initdatapool) {
//        calledMethod = " DataFetch_First(start,initdatapool ";
//        try {
//            //this is to be used only intially -> login, relogin
//            //this.userName = (String) session.getAttribute("user");
//            // this.dataBase = (String) session.getAttribute("dataBase");
//            con = dbconn.getConnection(initdatapool);
//            stat = con.createStatement();
//            con.setAutoCommit(true);
//            rs = null;
//            // loadSessionDetails();
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First(String start, String initdatapool)", initdatapool);
//        }
//    }
//
//    public DataFetch_First(String Database) {
//        calledMethod = " DataFetch_First(database) ";
//        //to call other database like optserver etc - 17-nov16
//        try {
//            ctx = FacesContext.getCurrentInstance();
//
//            session = (HttpSession) ctx.getExternalContext().getSession(false);
//            this.userName = (String) session.getAttribute("user");
//            this.currentmodule = (String) session.getAttribute("currentmodule");
//            // this.dataBase = (String) session.getAttribute("dataBase");
//            // this.dataPool = (String) session.getAttribute("dataPool");
//            //  System.out.print(dataPool);
//            //  System.out.print(Database);
//            con = dbconn.getConnection(Database);
//            stat = con.createStatement();
//            con.setAutoCommit(true);
//            rs = null;
//            // loadSessionDetails();
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First(String Database)", Database);
//        }
//    }
//
//    public DataFetch_First(int no, String Database) {
//        calledMethod = " DataFetch_First(no,database) ";
//        try {
//            this.userName = (String) session.getAttribute("user");
//            this.dataBase = (String) session.getAttribute("dataBase");
//            this.currentmodule = (String) session.getAttribute("currentmodule");
//            dataPool = (String) session.getAttribute("dataPool");
//
//            con = dbconn.getConnection(dataPool);
//            stat = con.createStatement();
//            con.setAutoCommit(false);
//            rs = null;
//            // to use with commit and rollback
//            // loadSessionDetails();
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First(int no,String database)", Database);
//        }
//    }
//
//    public DataFetch_First(int no) {
//        calledMethod = "DataFetch_First(no)";
//        //new 4-nov-16
//        // this to be uses when we need to save record : autocommit(false)
//        try {
//            this.userName = (String) session.getAttribute("user");
//            this.dataBase = (String) session.getAttribute("dataBase");
//            this.currentmodule = (String) session.getAttribute("currentmodule");
//            dataPool = (String) session.getAttribute("dataPool");
//
//            con = dbconn.getConnection(dataPool);
//            stat = con.createStatement();
//            con.setAutoCommit(false);
//            rs = null;
//            // to use with commit and rollback
//            // loadSessionDetails();
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First(int no)", dataBase);
//        }
//    }
//
//    public DataFetch_First() {
//        calledMethod = "DataFetch_First()";
//        //new 4-nov-16
//        //used while searching records
//        try {
//            ctx = FacesContext.getCurrentInstance();
//            session = (HttpSession) ctx.getExternalContext().getSession(false);
//            this.userName = (String) session.getAttribute("user");
//            this.dataBase = (String) session.getAttribute("dataBase");
//            this.dataPool = (String) session.getAttribute("dataPool");
//            this.currentmodule = (String) session.getAttribute("currentmodule");
//            //   System.out.print(dataPool);
//            //   System.out.print(dataBase);
//            con = dbconn.getConnection(dataPool);
//            stat = con.createStatement();
//            con.setAutoCommit(true);
//            rs = null;
//
//            // loadSessionDetails();
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First()", dataBase);
//
//        }
//    }
//
//    public DataFetch_First(String datap, String usr, String databs, String fin, int user) {
//        calledMethod = "DataFetch_First()";
//        //new 4-nov-16
//        //used while searching records
//        try {
//            System.out.println(databs);
//            this.userName = usr;
//            this.dataBase = databs;
//            this.dataPool = datap;
//            // this.currentmodule = (String) session.getAttribute("currentmodule");
//
//            con = dbconn.getConnection(dataPool);
//            stat = con.createStatement();
//            con.setAutoCommit(true);
//            rs = null;
//            System.out.println(datap);
//            // loadSessionDetails();
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First()", dataBase);
//
//        }
//    }
//
//    public void rsClose() {
//        calledMethod = " DataFetch_First:rsClose()";
//        try {
//            //if (rs.next()) {
//            if (rs != null) {
//                rs.close();
//            }
//        } catch (Exception e) {
//            logErrors(e, "DataFetch_First():rsClose()", dataBase);
//            // logger.error("DataFetch_First:rsClose()->    UserName: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-:: , database-> " + dataBase + "::-:: , " + e.getLocalizedMessage() + "::-:: ," + "Error query :" + errorQuery);
//        }
//    }
//
//    /*   public void closeConnection() {
//     Throwable t = new Throwable();
//     StackTraceElement[] elements = t.getStackTrace();
//     String calleeMethod = elements[0].getMethodName();
//     String callerMethodName = elements[1].getMethodName();
//     String callerClassName = elements[1].getClassName();
//     try {
//     if (stat != null) {
//     stat.close();
//     }
//     if (pstat != null) {
//     pstat.close();
//     }
//     } catch (Exception e) {
//
//     logger.error(" CallerClassName= " + callerClassName + " , Caller method name: " + callerMethodName);
//     //   logger.error("Callee method name: " + calleeMethod);
//     logger.error("while closing connection");
//     logErrors(e, "closeAll","");
//     }
//     }
//     * */
//    public void commit() {
//        calledMethod = " DataFetch_First:commit() ";
//        try {
//            con.commit();
//
//        } catch (Exception e) {
//            logErrors(e, "commit()", "");
//            // logger.error("DataFetch_First:commit()->    UserName: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-:: , database-> " + dataBase + "::-:: , " + e.getLocalizedMessage() + "::-:: ," + "Error query :" + errorQuery);
//        }
//    }
//
//    public void rollBack() {
//        calledMethod = " DataFetch_First:rollBack() ";
//        try {
//            con.rollback();
//
//        } catch (Exception e) {
//            LogSettings_First aa = new LogSettings_First("Error : DataFetch_First:rollBack()  UserName: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-::  , " + e.getLocalizedMessage() + errorQuery);
//        }
//    }
//
//    public void closeAll() {
//        calledMethod = " DataFetch_First:closeAll() ";
//        int sl = 0;
//        try {
//            sl = 1;
//            if (rs != null) {
//                rs.close();
//                sl = 2;
//            }
//            sl = 3;
//            if (stat != null) {
//                stat.close();
//                sl = 4;
//            }
//            sl = 5;
//            if (pstat != null) {
//                pstat.close();
//                sl = 6;
//            }
//            sl = 7;
//            if (con != null) {
//                con.close();
//                sl = 8;
//            }
//            sl = 9;
//            dbconn.closeConnection();
//            sl = 10;
//
//        } catch (Exception e) {
//            //  logErrors(e, "closeAll() :"+sl, "");
//            LogSettings_First aa = new LogSettings_First("Error : DataFetch_First:closeAll()  UserName: " + sl + ":: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-::  , " + e.getLocalizedMessage() + errorQuery);
//
//            // logger.error("DataFetch_First:closeAll()-> count :" + sl + "    UserName: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-:: , database-> " + dataBase + "::-:: , " + e.getLocalizedMessage() + "::-:: ," + "Error query :" + errorQuery);
//        } finally {
//            /* if (con != null) {
//             con.close();
//             }*/
//        }
//    }
//
//    public void logErrors(Exception e, String method, String addinfo) {
//        e.printStackTrace();
//        StackTraceElement[] elements = e.getStackTrace();
//
//        String calleeMethod = elements[0].getMethodName();
//        String callerMethodName = elements[1].getMethodName();
//        String callerClassName = elements[1].getClassName();
//        // logger.error("DataFetch_First:logErrors()  UserName: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-:: , Error at DataFetch_First-> " + method + "()::-:: , " + dataBase + "::-:: , " + e.getLocalizedMessage() + "::-:: ," + " calle Method" + calleeMethod + "::-:: ," + callerMethodName + "::-:: ," + callerClassName + "::-:: ," + e.getMessage() + "::-::," + "Error query :" + errorQuery);
//        LogSettings_First aa = new LogSettings_First("Error : DataFetch_First:logErrors()  UserName: " + userName + " Called Module : " + currentmodule + "::-:: called Method :-" + calledMethod + "::-:: , Error at DataFetch_First-> " + method + "()::-:: , " + dataBase + "::-:: , " + e.getLocalizedMessage() + "::-:: ," + " calle Method" + calleeMethod + "::-:: ," + callerMethodName + "::-:: ," + callerClassName + "::-:: ," + e.getMessage() + "::-::," + "Error query :" + errorQuery);
////rsClose();
//        closeAll();
//        System.gc();
//        criticalError = 1;
//
//    }
//
//    public String findString(String query, String name) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:findString(string,string) ";
//        String cod = "";
//        if (name == null || name.equalsIgnoreCase("Select")) {
//            return cod;
//        } else {
//            try {
//                rs = stat.executeQuery(query);
//                while (rs.next()) {
//                    cod = rs.getString(1).trim();
//                }
//                rsClose();
//            } catch (Exception e) {
//                cod = " ";
//                logErrors(e, "findString(String query, String name)", query);
//                //   logger.error(e.getMessage()+ " DataFetch_First-findString Error while executing query = " + query);
//                //   logErrors(e, "closeAll","");
//            }
//
//        }
//        if (cod == null) {
//            cod = " ";
//        }
//        return cod;
//    }
//
//    public int findInt(String query, String name) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:findInt";
//        int cod = 0;
//        ////System.out.println(name);
//        if (name == null || name.equalsIgnoreCase("Select")) {
//            return cod;
//        } else {
//            try {
//                rs = stat.executeQuery(query);
//                while (rs.next()) {
//                    //  rs.next();
//                    cod = rs.getInt(1);
//                }
//                rsClose();
//
//            } catch (Exception e) {
//                logErrors(e, "findInt(String query, String name)", query);
//                //  logger.error(e.getMessage()+ " DataFetch_First->findInt Error while executing query = " + query);
//            }
//
//        }
//
//        return cod;
//    }
//
//    public long findLong(String query, String name) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:findLong";
//        long cod = 0;
//        if (name == null || name.equalsIgnoreCase("Select")) {
//            return cod;
//        } else {
//            try {
//                rs = stat.executeQuery(query);
//                while (rs.next()) {
//                    //  rs.next();
//                    cod = rs.getLong(1);
//                }
//                rsClose();
//
//            } catch (Exception e) {
//                logErrors(e, "findInt(String query, String name)", query);
//                //  logger.error(e.getMessage()+ " DataFetch_First->findInt Error while executing query = " + query);
//            }
//        }
//
//        return cod;
//    }
//
//    public BigDecimal findBigDecimal(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:findBigDecimal";
//        BigDecimal cod = ZERO;
//
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                cod = rs.getBigDecimal(1);
//            }
//            if (cod == null) {
//                cod = ZERO;
//            }
//            rsClose();
//
//        } catch (Exception e) {
//            logErrors(e, "findbigDecimal(String query, String name)", query);
//            //  logger.error(e.getMessage()+ " DataFetch_First->findInt Error while executing query = " + query);
//        }
//
//        return cod;
//    }
//
//    /*   public BigDecimal findBigDecimal(String query) {
//     errorQuery = query;
//     calledMethod = "DataFetch_First:findBigDecimal";
//     BigDecimal cod = new BigDecimal(BigInteger.ONE);
//     try {
//     rs = stat.executeQuery(query);
//     rs.next();
//     cod = rs.getBigDecimal(1);
//     rsClose();
//     } catch (Exception e) {
//     logErrors(e, "findbigDecimal(String query)", query);
//     //  logger.error(e.getMessage()+ " DataFetch_First->findInt Error while executing query = " + query);
//     }
//
//     return cod;
//     }
//     */
//    public Double findDouble(String query, String name) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:findDouble";
//        Double cod = 0.00;
//        if (name == null || name.equalsIgnoreCase("Select")) {
//            return cod;
//        } else {
//            try {
//                rsTem = stat.executeQuery(query);
//                rsTem.next();
//                cod = rsTem.getDouble(1);
//                rsTem = null;
//            } catch (Exception e) {
//                logErrors(e, "findDouble(String query, String name)", query);
//                //  logger.error(e.getMessage()+ " DataFetch_First->findInt Error while executing query = " + query);
//            }
//        }
//        // rsClose();
//        return cod;
//    }
//
//    public Double findDoubleNew(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:findDouble";
//        Double cod = 0.00;
//        try {
//            rsTem = stat.executeQuery(query);
//            if (rsTem.next()) {
//                // rsTem.next();
//                cod = rsTem.getDouble(1);
//            } else {
//                cod = 0.00;
//            }
//            rsTem = null;
//        } catch (Exception e) {
//            logErrors(e, "findDouble(String query)", query);
//            //  logger.error(e.getMessage()+ " DataFetch_First->findInt Error while executing query = " + query);
//        }
//        // rsClose();
//        return cod;
//    }
//
//    public ArrayList getArrayDouble(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:getArrayDouble";
//        //rollBack();
//        ArrayList terri = new ArrayList();
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                terri.add(rs.getDouble(1));
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getArrayDouble(String query)", query);
//            // logger.error(e.getMessage()+ "Error in Method DataFetch_First->getArrayString  " + query);
//            //  logErrors(e, "closeAll","");
//        }
//        return terri;
//    }
//
//    public ArrayList getArrayInt(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:getArrayInt";
//        //rollBack();
//        ArrayList terri = new ArrayList();
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                terri.add(rs.getInt(1));
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getArrayInt(String query)", query);
//            // logger.error(e.getMessage()+ "Error in Method DataFetch_First->getArrayString  " + query);
//            //  logErrors(e, "closeAll","");
//        }
//        return terri;
//    }
//
//    public ArrayList getArrayLong(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:getArrayLong";
//        //rollBack();
//        ArrayList terri = new ArrayList();
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                terri.add(rs.getLong(1));
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getArrayLong(String query)", query);
//            // logger.error(e.getMessage()+ "Error in Method DataFetch_First->getArrayString  " + query);
//            //  logErrors(e, "closeAll","");
//        }
//        return terri;
//    }
//
//    public ArrayList getArrayString(String query) {
//        errorQuery = query;
//        calledMethod = "gDataFetch_First:etArrayString";
//        //rollBack();
//        ArrayList terri = new ArrayList();
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                terri.add(rs.getString(1).trim());
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getArrayString(String query)", query);
//            // logger.error(e.getMessage()+ "Error in Method DataFetch_First->getArrayString  " + query);
//            //  logErrors(e, "closeAll","");
//        }
//        return terri;
//    }
//
//    public ArrayList getArrayStringSele(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:getArrayStringSele";
//        //rollBack();
//        ArrayList terri = new ArrayList();
//        try {
//            terri.add("Select");
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                terri.add(rs.getString(1).trim());
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getArrayStringSele(String query)", query);
//            //   logger.error(e.getMessage()+ " Error in Method DataFetch_First->getArrayStringSele  " + query);
//            //     logErrors(e, "closeAll","");
//        }
//        return terri;
//    }
//
//    public ArrayList getArrayMasterLedgerTolist(String query) {
//        errorQuery = query;
//        calledMethod = "DataFetch_First:getArrayStringSele";
//        //rollBack();
//        ArrayList terri = new ArrayList();
//        try {
//            terri.add("Select");
//            terri.add("Accounts");
//            terri.add("Other Branches");
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                terri.add(rs.getString(1).trim());
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getArrayStringSele(String query)", query);
//            //   logger.error(e.getMessage()+ " Error in Method DataFetch_First->getArrayStringSele  " + query);
//            //     logErrors(e, "closeAll","");
//        }
//        return terri;
//    }
//
//    public ArrayList<ChartBean> getChartData(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "DataFetch_First:getChartData";
//        ArrayList<ChartBean> accViewDet = new ArrayList<ChartBean>();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    accViewDet.add(new ChartBean(rs.getString(1).trim(), rs.getInt(2)));
//                } else if (typ == 2) {
//                    accViewDet.add(new ChartBean(rs.getString(1).trim(), rs.getString(2)));
//                } else if (typ == 3) {
//                    //repo_zerobilling 6/oct-21
//                    accViewDet.add(new ChartBean(rs.getString(1).trim(), rs.getString(2), rs.getString(3), rs.getString(4)));
//                }
//            }
//            rsClose();
//        } catch (Exception e) {
//            logErrors(e, "getChartData(String quer, int typ)", quer);
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->usersScanMap  " + quer);
//            //  logErrors(e, "closeAll","");
//        }
//        return accViewDet;
//    }
//
//    public ArrayList<Users_Bean> usersScanMap(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "usersScanMap";
//        ArrayList<Users_Bean> accViewDet = new ArrayList<Users_Bean>();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    accViewDet.add(new Users_Bean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
//                }
//
//            }
//            rsClose();
//
//        } catch (Exception e) {
//            logErrors(e, "usersScanMap(String quer, int typ)", quer);
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->usersScanMap  " + quer);
//            //  logErrors(e, "closeAll","");
//        }
//        return accViewDet;
//    }
//
//    public Users_Bean usersScan(String quer) {
//        errorQuery = quer;
//        calledMethod = "usersScan";
//        Users_Bean accViewDet = new Users_Bean();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                accViewDet.setCode(rs.getInt(1));
//                accViewDet.setName(rs.getString(2));
//                accViewDet.setLastName(rs.getString(3));
//                accViewDet.setStatus(rs.getString(4));
//                accViewDet.setLoginId(rs.getString(5));
//                accViewDet.setPassword(rs.getString(6));
//                accViewDet.setPrivlage(rs.getString(7));
//                accViewDet.setBranchCode(rs.getInt(8));
//
//            }
//            rsClose();
//        } catch (Exception e) {
//            //logger.error(e.getMessage()+ " Error in Method DataFetch_First->usersScan  " + quer);
//            logErrors(e, "usersScan(String quer)", quer);
//        }
//        return accViewDet;
//    }
//
//    public ContactDetails_Bean contactDetailScan(String quer) {
//        errorQuery = quer;
//        calledMethod = "contactDetailScan";
//        ContactDetails_Bean accViewDet = new ContactDetails_Bean();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                accViewDet.setCntCode(rs.getInt(1));
//                accViewDet.setTable(rs.getString(2));
//                accViewDet.setCntPerson(rs.getString(3));
//                accViewDet.setLastName(rs.getString(4));
//                accViewDet.setDesign(rs.getString(5));
//                accViewDet.setCntNo(String.valueOf(rs.getInt(6)));
//                accViewDet.setExtn(String.valueOf(rs.getInt(7)));
//                accViewDet.setEmaiId(rs.getString(8));
//                accViewDet.setMobile(rs.getString(9));
//
//            }
//            rsClose();
//        } catch (Exception e) {
//            //logger.error(e.getMessage()+ " Error in Method DataFetch_First->contactDetailScan  " + quer);
//            logErrors(e, "contactDetailScan(String quer)", quer);
//        }
//        return accViewDet;
//    }
//
//    public ArrayList<ContactDetails_Bean> contactDetailScanMap(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "contactDetailScanMap";
//        ArrayList<ContactDetails_Bean> accViewDet = new ArrayList<ContactDetails_Bean>();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    //default
//                    accViewDet.add(new ContactDetails_Bean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
//                } else if (typ == 2) {
//                    //mail_bulkto_contacts
//                    accViewDet.add(new ContactDetails_Bean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
//                } else if (typ == 3) {
//                    //mail_bulkto_contacts
//                    accViewDet.add(new ContactDetails_Bean(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5)));
//                }
//            }
//            rsClose();
//        } catch (Exception e) {
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->destnScanMap  " + quer);
//            logErrors(e, "contactDetailScanMap(String quer, int typ)", quer);
//        }
//        return accViewDet;
//    }
//
//    public ArrayList<DestBean> destnScanMap(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "destnScanMap";
//        ArrayList<DestBean> accViewDet = new ArrayList<DestBean>();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    //default
//                    accViewDet.add(new DestBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getInt(19)));
//                }
//            }
//            rsClose();
//        } catch (Exception e) {
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->destnScanMap  " + quer);
//            logErrors(e, "destnScanMap(String quer, int typ)", quer);
//        }
//        return accViewDet;
//    }
//
//    public DestBean destScan(String quer) {
//        errorQuery = quer;
//        calledMethod = "destScan";
//        DestBean det = new DestBean();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                det.setPinCode(rs.getString(1));
//                det.setCity(rs.getString(2));
//                det.setState(rs.getString(3));
//                det.setSector(rs.getString(4));
//                det.setHub(rs.getString(5));
//                det.setDestn(rs.getString(6));
//                det.setDestCode(rs.getInt(7));
//                det.setAreaCode(rs.getString(8));
//                det.setServiceType(rs.getString(9));
//                det.setAmName(rs.getString(10).trim());
//                det.setDcName(rs.getString(11).trim());
//                det.setManager(rs.getString(12));
//                det.setIncharge(rs.getString(13));
//                det.setPhone(rs.getString(14));
//                det.setMobile(rs.getString(15));
//                det.setEmail(rs.getString(16));
//                det.setEmail2(rs.getString(17));
//                det.setAddress(rs.getString(18));
//                det.setStaffcode(rs.getInt(19));
//            }
//            rsClose();
//        } catch (Exception e) {
//            //  logger.error(e.getMessage()+ " Error in Method DataFetch_First->destScan  " + quer);
//            logErrors(e, "destScan", quer);
//        }
//        return det;
//    }
//
//    public ArrayList<QuotationBean> quotationMap(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "quotationMap";
//        ArrayList<QuotationBean> accViewDet = new ArrayList<QuotationBean>();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    //called from quotationView
//                    accViewDet.add(new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), rs.getDouble(22), rs.getString(23), rs.getString(24)));
//                } else if (typ == 2) {
//                    //default
//                    accViewDet.add(new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), rs.getDouble(22)));
//                } else if (typ == 3) {
//                    accViewDet.add(new QuotationBean(rs.getDouble(1), rs.getString(2), rs.getDouble(3)));
//
//                } else if (typ == 4) {
//                    //called from quotationbkp view
//                    accViewDet.add(new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getInt(20), rs.getInt(21), rs.getDate(22), rs.getString(23), rs.getDouble(24), rs.getDouble(25), rs.getDouble(26), rs.getString(27), rs.getString(28), rs.getString(29)));
//                } else if (typ == 5) {
//                    //called from ratefinder pro
//                    accViewDet.add(new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), rs.getDouble(22), rs.getString(23)));
//                } else if (typ == 6) {
//                    //ratefinder
//                    accViewDet.add(new QuotationBean(rs.getDouble(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
//
//                } else if (typ == 7) {
//                    //called from letter_RateRevison
//                    accViewDet.add(new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), rs.getDouble(22), rs.getString(23), rs.getString(24), rs.getInt(25)));
//                } else if (typ == 8) {
//                    //called from quotation_repo 22-july20
//                    accViewDet.add(new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), rs.getDouble(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getDouble(28), rs.getDouble(29), rs.getDouble(30), rs.getDouble(31)));
//                }
//
//            }
//            rsClose();
//        } catch (Exception e) {
//            //  logger.error(e.getMessage()+ " Error in Method DataFetch_First->quotationMap  " + quer);
//            logErrors(e, "quotationMap", quer);
//        }
//        return accViewDet;
//    }
//
//    public QuotationBean quotationScan(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "quotationScan";
//        QuotationBean accViewDet = new QuotationBean();
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    //default
//                    accViewDet = (new QuotationBean(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getInt(12), rs.getInt(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), rs.getDouble(22)));
//                }
//                if (typ == 2) {
//                    accViewDet = (new QuotationBean(rs.getDouble(1), rs.getString(2), rs.getDouble(3)));
//
//                }
//
//            }
//            rsClose();
//        } catch (Exception e) {
//            //  logger.error(e.getMessage()+ " Error in Method DataFetch_First->quotationMap  " + quer);
//            logErrors(e, "quotationScan", quer);
//        }
//        return accViewDet;
//    }
//
//    public ResultSet rset(String quer) {
//        errorQuery = quer;
//        calledMethod = "rset";
//        try {
//            rs = stat.executeQuery(quer);
//
//        } catch (Exception e) {
//            // logger.error(e.getMessage() + " Error in Method DataFetch_First->rset  " + quer);
//            logErrors(e, "rset()", quer);
//        }
//        return rs;
//    }
//
//    public ResultSet billing_edit(String quer) {
//        errorQuery = quer;
//        calledMethod = "billing_edit";
//        try {
//            rs = stat.executeQuery(quer);
//
//        } catch (Exception e) {
//            //  logger.error(e.getMessage()+ " Error in Method DataFetch_First->billing_edit  " + quer);
//            logErrors(e, "billing_edit", quer);
//        }
//        return rs;
//    }
//
//    // need to program in dataconnect_first 3mar2020
//    public int mod_billing_edit(String query) {
//        errorQuery = query;
//        calledMethod = "mod_billing_edit";
//        int cnt1 = 0;
//        try {
//            pstat = con.prepareStatement(query);
//            cnt1 = pstat.executeUpdate();
//            if (cnt1 == 1) {
//                commit();
//            }
//
//        } catch (Exception e) {
//            //  logger.error(e.getMessage()+ " Error in Method DataFetch_First->mod_billing_edit  " + query);
//            logErrors(e, "mod_billing_edit", query);
//        }
//
//        return cnt1;
//    }
//
//    public AccountsCustomer accountsCustomerScan(String query, int type) {
//        errorQuery = query;
//        calledMethod = "accountsCustomerScan";
//        //ArrayList<AccountsCustomer> flists = new ArrayList<AccountsCustomer>();
//        AccountsCustomer custlist = new AccountsCustomer();
//
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                if (type == 1) {
//                    //Default
//                    custlist = new AccountsCustomer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getString(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28));
//
//                    // flists.add(listd);
//                } else if (type == 2) {
//                    //clientedit-find
//                    custlist = new AccountsCustomer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getString(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28), rs.getString(29), rs.getString(30));
//                } else if (type == 3) {
//                    //master_ledger 12-aug20
//                    custlist = new AccountsCustomer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getString(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28), rs.getString(29));
//
//                }
//            }
//            rsClose();
//        } catch (Exception e) {
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->accountsCustomerScan  " + query);
//            logErrors(e, "accountsCustomerScan", query);
//        }
//
//        return custlist;
//
//    }
//
//    public ArrayList<AccountsCustomer> accountsCustomerScanMap(String query, int type) {
//        errorQuery = query;
//        calledMethod = "accountsCustomerScanMap";
//        ArrayList<AccountsCustomer> custlist = new ArrayList<AccountsCustomer>();
//        try {
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                if (type == 1) {
//                    custlist.add(new AccountsCustomer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getString(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28)));
//                }
//            }
//            rsClose();
//        } catch (Exception e) {
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->accountsCustomerScan  " + query);
//            logErrors(e, "accountsCustomerScanMap", query);
//        }
//        return custlist;
//    }
//
//    public ArrayList<Ledger_Bean> ledgerScanMap(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "ledgerScanMap";
//        ArrayList<Ledger_Bean> viewDet = new ArrayList<Ledger_Bean>();
//        Double debtot = 0.00;
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
//                    //default
//                    //called from pdfcreditbooking
//                    viewDet.add(new Ledger_Bean(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14)));
//                } else if (typ == 2) {
//                    //accgroupview
//                    viewDet.add(new Ledger_Bean(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getDouble(15)));
//                } else if (typ == 3) {
//                    //accgroupview
//
//                    viewDet.add(new Ledger_Bean(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getDouble(15), rs.getDouble(16)));
//                } else if (typ == 4) {
//                    //Repo_Branch_Status
//                    viewDet.add(new Ledger_Bean(rs.getString(1), rs.getDouble(2)));
//                } else if (typ == 5) {
//                    //called from acc_ledger_Add
//                    viewDet.add(new Ledger_Bean(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15)));
//                } else if (typ == 6) {
//                    //Repo_sales_summary
//                    viewDet.add(new Ledger_Bean(rs.getString(1), rs.getString(2), rs.getDouble(3)));
//                } else if (typ == 7) {
//                    //called from acc_ledger_Add
//                    viewDet.add(new Ledger_Bean(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15), rs.getString(16)));
//                } else if (typ == 8) {
//                    //called from acc_ledger_Add
//                    viewDet.add(new Ledger_Bean(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getBigDecimal(7), rs.getBigDecimal(8), rs.getBigDecimal(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15)));
//                }
//
//            }
//            rsClose();
//        } catch (Exception e) {
//            // logger.error(e.getMessage()+ " Error in Method DataFetch_First->ledgerScanMap  " + quer);
//            logErrors(e, "ledgerScanMap", quer);
//        }
//        return viewDet;
//    }
//
//    public ArrayList<Annualy_Bean> annualy_scan(String quer, int typ) {
//        errorQuery = quer;
//        calledMethod = "annualy_scan";
//        ArrayList<Annualy_Bean> viewDet = new ArrayList<Annualy_Bean>();
//        Double debtot = 0.00;
//        try {
//            rs = stat.executeQuery(quer);
//            while (rs.next()) {
//                if (typ == 1) {
