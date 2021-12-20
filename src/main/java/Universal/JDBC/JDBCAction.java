package Universal.JDBC;

//import com.nakedhub.autotester.util.TimeString;

import java.util.List;


public class JDBCAction {

    @org.testng.annotations.Test
    public void JDBCTest(){
//
        String phone = "15618583372";
        StringBuffer querySQL = new StringBuffer();
        querySQL.append("select token from sms_tokens where  phone = ");
        querySQL.append("'");
        querySQL.append(phone);
        querySQL.append("'");
        querySQL.append(" order by created_at desc limit 10");
        String sql = querySQL.toString();

        List smsList = JDBCHelper.querySMS(sql,"token");
        System.out.println(smsList.get(0));
    }

    @org.testng.annotations.Test
    public static void recoverData(String policy_no){
        StringBuffer querySQL = new StringBuffer();
        querySQL.append("update insurance_users SET user_id = null, bound_at=null WHERE policy_no = ");
        querySQL.append("'");
        querySQL.append(policy_no);
        querySQL.append("'");
        String sql = querySQL.toString();
        System.out.println("当前sql语句是："+sql);
        JDBCHelper.update(sql);

    }


}

