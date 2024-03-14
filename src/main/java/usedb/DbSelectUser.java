package usedb;
import java.io.Serializable;
import java.sql.*;

public class DbSelectUser implements Serializable {
    public String[] Select(String id) {
    	
        String[] user_info = new String[4];
        // データベースへの接続設定
        try {
            
            // 接続先とポート: tokushima.data.ise.shibaura-it.ac.jp:5432/
			// データベース: group1
			// ユーザ: group1_user
			// パスワード: bond
			String database = "group1";
            String server = "tokushima.data.ise.shibaura-it.ac.jp:5432/";
			String url="jdbc:postgresql://" + server + database;
            Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url, "group1_user", "bond");
			Statement stmt = con.createStatement();

            // レコードの取得
            String sql = "SELECT * FROM USER_INFO WHERE USER_ID = '" + id + "'";
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
				
                String user_id = resultSet.getString("USER_ID");
                String password = resultSet.getString("PASSWORD");
                String name = resultSet.getString("RESERVER_NAME");
                String tell = resultSet.getString("TELL");

                user_info[0] = user_id;
                user_info[1] = password;
                user_info[2] = name;
                user_info[3] = tell;
            }

            resultSet.close();
            stmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return user_info;
    }

}
