package utils;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SichuanSqlUtils {// 定义 DM JDBC驱动串
	
	private static Logger log = LoggerFactory.getLogger(SichuanSqlUtils.class);
	
    String jdbcString = "dm.jdbc.driver.DmDriver";// 定义 DM URL 连接串
    String urlString = "jdbc:dm://202.61.89.33:16002";// 定义连接用户名
    String userName = "sichuanceshiku";// 定义连接用户口令
    String password = "dameng@1234";// 定义连接对象
    Connection conn = null;

    /**
     * 加载JDBC驱动
     * @throws SQLException
     */
    public void loadJdbcDriver() throws SQLException {
        try {
            log.warn("Loading JDBC Driver...");
            // 加载 JDBC驱动程序
            Class.forName(jdbcString);
        } catch (ClassNotFoundException e) {
        	log.error("Load JDBC Driver Error : {}",e.getMessage());
        } catch (Exception ex) {
        	log.error("Load JDBC Driver Error :  {}",ex.getMessage());
        }
    }

    /**
     * 连接数据库
     * @throws SQLException
     */
    public void connect() throws SQLException {
        try {
            log.warn("Connecting to DM Server...");
            conn = DriverManager.getConnection(urlString, userName, password);
        } catch (SQLException e) {
            log.error("Connect to DM Server Error : {}" ,e.getMessage());
        }
    }

    /**
     * 关闭连接
     * @throws SQLException
     */
    public void disConnect() throws SQLException {
        try {
            conn.close();
        } catch (SQLException e) {
        	log.error("close connection error :  {}" ,e.getMessage());
        }
    }
    
    /**
     * 插入心跳
     * @throws SQLException 
     */
    public void insertXintiao(String sjbh,String yhd,String jcz) throws SQLException{
			      String sql = "INSERT INTO JCCA16A("
			      		+ "JCCA16A010,"
			      		+ "JCCA16A020,"
			      		+ "JCCA16A025,"
			      		+ "JCCA16A030,"
			      		+ "JCCA16A040) "
			      + "VALUES(?,?,?,?,?);";
			// 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 为参数赋值
			pstmt.setString(1, UUIDUtils.random());
			pstmt.setString(2, sjbh);
			pstmt.setString(3, yhd);
			pstmt.setString(4, jcz);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			pstmt.setDate(5,Date.valueOf(sdf.format(new java.util.Date())));
			pstmt.executeUpdate();
			pstmt.close();
    }
    
    /**
     * 插入报警
     * @throws SQLException 
     */
    public void insertBaojing(String jb,int qz,String jcd,String yhd,Double jcz,Double yz) throws SQLException{
			      String sql = "INSERT INTO JCCA20A("
			      		+ "JCCA20A010,"
			      		+ "JCCA20A020,"
			      		+ "JCCA20A030,"
			      		+ "JCCA20A040,"
			      		+ "JCCA20A050,"
			      		+ "JCCA20A055,"
			      		+ "JCCA20A080,"
			      		+ "JCCA20A090) "
			      + "VALUES(?,?,?,?,?,?,?,?);";
			// 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 为参数赋值
			pstmt.setString(1, UUIDUtils.random());
			pstmt.setString(2, jb);//C1-注意级、C2-警示 级、C3-警戒级、C4-警报级
			pstmt.setInt(3, qz);//对应:C1:1/ C2:2/ C3:3/ C4:4
			pstmt.setDate(4, Date.valueOf(sdf.format(new java.util.Date())));
			pstmt.setString(5, jcd);//监测点
			pstmt.setString(6, yhd);//隐患点
			pstmt.setDouble(7, jcz);//监测值
			pstmt.setDouble(8, yz);//阈值
			pstmt.executeUpdate();
			pstmt.close();
    }
    
    /**
     * 插入承建单位
     * @throws SQLException 
     */
    public void insertChengjiandanwei() throws SQLException{
			      String sql = "INSERT INTO JCCA07A("
			      		+ "JCCA07A010,"
			      		+ "JCCA07A020,"
			      		+ "JCCA07A030,"
			      		+ "JCCA07A040,"
			      		+ "JCCA07A050,"
			      		+ "JCCA07A060,"
			      		+ "JCCA07A070,"
			      		+ "JCCA07A080,"
			      		+ "JCCA07A090,"
			      		+ "JCCA07A100) "
			      + "VALUES(?,?,?,?,?,?,?,?,?,?);";
			// 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 为参数赋值
			pstmt.setString(1, "2012010201");
			pstmt.setString(2, "上海展为智能技术股份有限公司");
			pstmt.setString(3, "上海市锦绣东路2777弄34号楼301室");
			pstmt.setString(4, "植强");
			pstmt.setString(5, "植强");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setString(8, "18108192627");
			pstmt.setString(9, "");
			pstmt.setString(10, "");
			pstmt.executeUpdate();
			pstmt.close();
    }
    
    /**
     * 插入建设单位
     * @throws SQLException 
     */
    public void insertJianshedanwei() throws SQLException{
			      String sql = "INSERT INTO JCCA06A("
			      		+ "JCCA06A010,"
			      		+ "JCCA06A020,"
			      		+ "JCCA06A030,"
			      		+ "JCCA06A040,"
			      		+ "JCCA06A050,"
			      		+ "JCCA06A060,"
			      		+ "JCCA06A070,"
			      		+ "JCCA06A080,"
			      		+ "JCCA06A090,"
			      		+ "JCCA06A100,"
			      		+ "JCCA06A110) "
			      + "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
			// 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 为参数赋值
			pstmt.setString(1, "2012010101");
			pstmt.setString(2, "上海展为智能技术股份有限公司");
			pstmt.setString(3, "");
			pstmt.setString(4, "上海市锦绣东路2777弄34号楼301室");
			pstmt.setString(5, "植强");
			pstmt.setString(6, "植强");
			pstmt.setString(7, "");
			pstmt.setString(8, "");
			pstmt.setString(9, "18108192627");
			pstmt.setString(10, "");
			pstmt.setString(11, "");
			pstmt.executeUpdate();
			pstmt.close();
    }
    
    /**
     * 插入监测仪器
     * @throws SQLException 
     */
    public void insertJianceyiqi(String num) throws SQLException{
			      String sql = "INSERT INTO JCCA08A("
			      		+ "JCCA08A010,"
			      		+ "JCCA08A030,"
			      		+ "JCCA08A040,"
			      		+ "JCCA08A050,"
			      		+ "JCCA08A070,"
			      		+ "JCCA08A080,"
			      		+ "JCCA08A100,"
			      		+ "JCCA08A110) "
			      + "VALUES(?,?,?,?,?,?,?,?);";
			// 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 为参数赋值
			pstmt.setString(1, num);
			pstmt.setString(2, "倾角传感器");
			pstmt.setString(3, "ZW-AGS-DX");
			pstmt.setString(4, "上海展为智能技术股份有限公司");
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			pstmt.setInt(7,0);
			pstmt.setString(8, "");
			pstmt.executeUpdate();
			pstmt.close();
    }
    
    /**
     * 插入承建单位
     * @throws SQLException 
     */
    public void insertYunweidanwei() throws SQLException{
			      String sql = "INSERT INTO JCCA25A("
			      		+ "JCCA25A010,"
			      		+ "JCCA25A020,"
			      		+ "JCCA25A030,"
			      		+ "JCCA25A040,"
			      		+ "JCCA25A050,"
			      		+ "JCCA25A060,"
			      		+ "JCCA25A070,"
			      		+ "JCCA25A080,"
			      		+ "JCCA25A090,"
			      		+ "JCCA25A100,"
			      		+ "JCCA25A110,"
			      		+ "JCCA25A120) "
			      + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
			// 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 为参数赋值
			pstmt.setString(1, "2012010301");
			pstmt.setString(2, "上海展为智能技术股份有限公司");
			pstmt.setString(3, "上海市锦绣东路2777弄34号楼301室");
			pstmt.setString(4, "植强");
			pstmt.setString(5, "植强");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setString(8, "18108192627");
			pstmt.setString(9, "");
			pstmt.setDate(10, Date.valueOf("2019-08-14"));
			pstmt.setDate(11, Date.valueOf("2020-08-14"));
			pstmt.setString(12, "");
			pstmt.executeUpdate();
			pstmt.close();
    }




   /**
    * 修改产品信息
    * @throws SQLException
    */
    public void updateTable() throws SQLException {
        String sql = "UPDATE production.product SET name = ?" + "WHERE productid = 11;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "三国演义（上）");
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * 删除
     * @throws SQLException
     */
    public void deleteTable(String sql) throws SQLException {
//        String sql = "DELETE FROM production.product WHERE productid = 11;";
    	log.warn("dqlete sql:{}",sql);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    /**
     * SQL查询
     * @param sql
     * @throws SQLException
     */
    public void queryTable(String sql) throws SQLException {
//        String sql = "SELECT productid,name,author,publisher FROM production.product";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        displayResultSet(rs);
        rs.close();
        stmt.close();
    } 
    

    /* 显示结果集 * @param rs 结果集对象 * @throws SQLException 异常 */
    private void displayResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCols = rsmd.getColumnCount();
        for (int i = 1; i <= numCols; i++) {
            if (i > 1) {
            	 System.out.print(",");
            }
            System.out.print(rsmd.getColumnLabel(i));
        }
        System.out.println("");
        while (rs.next()) {
            for (int i = 1; i <= numCols; i++) {
                if (i > 1) {
                	 System.out.print(",");
                }
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }



    public static void main(String args[]) throws SQLException {
    	SichuanSqlUtils basicApp = new SichuanSqlUtils();
         basicApp.loadJdbcDriver();
         basicApp.connect();
//         basicApp.insertChengjiandanwei(); //插入承建单位
//         basicApp.insertYunweidanwei();//插入运维单位
//         basicApp.insertJianshedanwei();//插入建设单位
//         basicApp.insertJianceyiqi("");
         basicApp.queryTable(" SELECT * FROM JCCA02A WHERE JCCA02A010 = '510181010288' ");
    }
}