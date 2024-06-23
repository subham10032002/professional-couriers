//package com.tpcindia.professional_couriers.webcode;
//
//public class DBConnection {
//}
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package DataConnect;
//
//import java.sql.*;
//        import java.sql.Connection;
//import javax.sql.DataSource;
//import javax.naming.InitialContext;
//import org.apache.log4j.*;
//        import javax.faces.bean.ManagedBean;
//import java.io.Serializable;
//
///**
// *
// * @author biju
// */
//public class DBConnection_First implements Serializable {
//
//    Connection con = null;
//    DataSource ds = null;
//    public static String status;
//    private static final Logger logger = Logger.getLogger(DBConnection_First.class);
//
//    public DBConnection_First() {
//    }
//
//    public Connection getConnection(String Database) {
//        try {
//            if (Database.equals("FirstOne")) {
//                //mysql JDBC Settings
//                InitialContext ctx = new InitialContext();
//                ds = (DataSource) ctx.lookup("FirstOneResources");
//                con = ds.getConnection();
//                if (con == null) {
//                    throw new SQLException("Error establishing connection!");
//
//                }
//                status = "good";
//            } else if (Database.equals("FirstOneSql")) {
//                //mysql JDBC Settings
//                InitialContext ctx = new InitialContext();
//                ds = (DataSource) ctx.lookup("FirstOneResourcesSql");
//                con = ds.getConnection();
//                if (con == null) {
//                    throw new SQLException("Error establishing connection!");
//
//                }
//                status = "good";
//            } else if (Database.equals("FirstOneSqlPE")) {
//                //mysql JDBC Settings
//                InitialContext ctx = new InitialContext();
//                ds = (DataSource) ctx.lookup("FirstOneResourcesSqlPE");
//                con = ds.getConnection();
//                if (con == null) {
//                    throw new SQLException("Error establishing connection!");
//
//                }
//                status = "good";
//            } else if (Database.equals("FirstOneSqlCare")) {
//                //mysql JDBC Settings
//                InitialContext ctx = new InitialContext();
//                ds = (DataSource) ctx.lookup("FirstOneSqlCareRes");
//                con = ds.getConnection();
//                if (con == null) {
//                    throw new SQLException("Error establishing connection!");
//
//                }
//                status = "good";
//            } else if (Database.equals("FirstOneSqlSync")) {
//                //mysql JDBC Settings
//                InitialContext ctx = new InitialContext();
//                ds = (DataSource) ctx.lookup("FirstOneSqlSyncRes");
//                con = ds.getConnection();
//                if (con == null) {
//                    throw new SQLException("Error establishing connection!");
//
//                }
//                status = "good";
//            }else if (Database.equalsIgnoreCase("OptServer")) {
//                InitialContext ctx = new InitialContext();
//                ds = (DataSource) ctx.lookup("OptServerRes");
//                con = ds.getConnection();
//                if (con == null) {
//                    throw new SQLException("Error establishing connection!");
//
//                }
//
//            }
//        } catch (Exception e) {
//            status = "bad";
//            logger.error(e.getLocalizedMessage() + " :::- Error at DBConnection_First-> getConnection :::-- " + e.getMessage());
//            e.printStackTrace();
//            System.gc();
//
//        }
//        return con;
//    }
//
//    public void closeConnection() {
//        try {
//            if (con != null) {
//                // con= null;
//                //ds=null;
//                con.close();
//
//            }
//        } catch (Exception e) {
//            // logger.error(e.getMessage() + " Error at DBConnection_First-> closeConnection : ");
//            logger.error(e.getLocalizedMessage() + " :::- Error at DBConnection_First-> closeConnection :::-- " + e.getMessage());
//
//            e.printStackTrace();
//            System.gc();
//        }
//    }
//    /*
//
//     } else if (Database.equalsIgnoreCase("FirstTrack15")) {
//     InitialContext ctx = new InitialContext();
//     ds = (DataSource) ctx.lookup("FirstTrack15Resources");
//     con = ds.getConnection();
//     if (con == null) {
//     throw new SQLException("Error establishing connection!");
//
//     }
//     }*/
//
//    public String testAGIServer() {
//        // String Url = "jdbc:sqlserver://FIRSTONE;DatabaseName=FirstOne20;user=sa;Password=admin2008";
//        //Url = "jdbc:sqlserver://serverURL;DatabaseName=DBname;user=dbUsername;Password=dbPassword";
//        String message = "Checking";
//        String url = "jdbc:sqlserver://10.10.3.82:2301;DatabaseName=Mc82020;user=sa;Password=AtlantGlobal@#1234";
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection connection = DriverManager.getConnection(url);
//            // message="Connection Established Successfull and the DATABASE NAME IS:"+ connection.getMetaData().getDatabaseProductName();
//            message = "Connected";
//        } catch (Exception e) {
//            message = "Unable to make connection with Atlant Global Database Server";
//            // e.printStackTrace();
//        }
//        return message;
//
//    }
//
//    public String testDSHServer() {
//        // String Url = "jdbc:sqlserver://FIRSTONE;DatabaseName=FirstOne20;user=sa;Password=admin2008";
//        //Url = "jdbc:sqlserver://serverURL;DatabaseName=DBname;user=dbUsername;Password=dbPassword";
//        String message = "Checking";
//        String url = "jdbc:sqlserver://192.168.0.15:1433;DatabaseName=dummy;user=sa;Password=TTSserver@2009";
//        //  String url = "jdbc:sqlserver://TTSSERVER:1433;DatabaseName=dummy;user=sa;Password=TTSserver@2009";
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection connection = DriverManager.getConnection(url);
//            // message="Connection Established Successfull and the DATABASE NAME IS:"+ connection.getMetaData().getDatabaseProductName();
//            message = "Connected";
//        } catch (Exception e) {
//            message = "Unable to make connection with DSH HUB Database Server";
//            // e.printStackTrace();
//        }
//        return message;
//
//    }
//
//    public String testTMRServer() {
//        // String Url = "jdbc:sqlserver://FIRSTONE;DatabaseName=FirstOne20;user=sa;Password=admin2008";
//        //Url = "jdbc:sqlserver://serverURL;DatabaseName=DBname;user=dbUsername;Password=dbPassword";
//        String message = "Checking";
//        String url = "jdbc:sqlserver://103.74.138.242:1433;DatabaseName=dummy;user=sa;Password=TTSserver@2009";
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection connection = DriverManager.getConnection(url);
//            // message="Connection Established Successfull and the DATABASE NAME IS:"+ connection.getMetaData().getDatabaseProductName();
//            message = "Connected";
//        } catch (Exception e) {
//            message = "Unable to make connection with TMR BTW HUB Database Server";
//            // e.printStackTrace();
//        }
//        return message;
//
//    }
//
//}
//
