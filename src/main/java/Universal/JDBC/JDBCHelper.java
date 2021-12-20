package Universal.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Universal.JDBC.ConnectionUtil.getConnection;


public class JDBCHelper {

    /**
     * 用于查询，返回结果集
     *
     * @param sql
     *            sql语句
     * @return 结果集
     * @throws SQLException
     */

    //get the SMS Verification Code.
    public static List querySMS(String sql,String col){
        return query(sql,col);
    }
    //get the Token to invoke Interface backend.
    public static String queryAppToken(String account,String col){
        String appToken ;
        StringBuffer querySQL = new StringBuffer();
        querySQL.append("select ull.headerSecToken from nh_userloginlog as ull,nh_x_user as u ");
        querySQL.append("where ull.memberId = u.id and u.nickname = '");
        querySQL.append(account);
        querySQL.append("' and u.role = 'MEMBER' and ");
        querySQL.append("u.isDeleted = 0 and ull.isActive = 1 order by ull.createTime desc limit 0,1;");
        String sql = querySQL.toString();
        appToken = query(sql,col).get(0).toString();
        return appToken;
    }

    public static List query(String sql,String col) {

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement(sql);
            rs = pStatement.executeQuery();
            ArrayList list = new ArrayList();

            while (rs.next()) {
                list.add(rs.getString(col));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionUtil.closeResultSet(rs);
            ConnectionUtil.closePreparedStatement(pStatement);
            ConnectionUtil.closeConnection(connection);
        }
    }

    public static void update(String sql) {
        Connection connection = null;
        PreparedStatement pStatement = null;
//        ResultSet rs = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement(sql);
            int result = pStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
//            return null;
        } finally {
//            ConnectionUtil.closeResultSet(result);
            ConnectionUtil.closePreparedStatement(pStatement);
            ConnectionUtil.closeConnection(connection);
        }
    }

    public static List<Map<String, Object>> queryAll(String sql, Map<Integer, Object> conditionMap){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            if(conditionMap!=null&&conditionMap.size()!=0){

                int paramNum = conditionMap.size();
                for(int i=1;i<=paramNum;i++){

                    Object paramValue = conditionMap.get(i);

                    if("java.lang.Integer".equalsIgnoreCase(paramValue.getClass().getName())){
                        pstmt.setInt(i, Integer.parseInt(paramValue.toString()));
                    }else if("java.lang.String".equalsIgnoreCase(paramValue.getClass().getName())){
                        pstmt.setString(i, paramValue.toString());
                    }
                }
            }

            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNum = rsmd.getColumnCount();
            while(rs.next()){
                Map<String, Object> dataMap = new HashMap<String, Object>(0);
                for(int i=1;i<=columnNum;i++){
                    dataMap.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                resultList.add(dataMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            releaseConn(conn, pstmt, rs);
        }

        return resultList;
    }

    public static void releaseConn(Connection conn,PreparedStatement pstmt,ResultSet rs){
        try {
            if(rs!=null) {
                rs.close();
            }
            if(pstmt!=null) {
                pstmt.close();
            }
            if(conn!=null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
