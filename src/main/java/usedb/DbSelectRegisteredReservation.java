package usedb;
import java.io.Serializable;
import java.sql.*;

public class DbSelectRegisteredReservation implements Serializable {
    public boolean Select(String user_id, String password) {
    	
        Boolean registered = false;
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
            String sql = "SELECT * FROM RESERVATION WHERE USER_ID = ? AND PASSWORD = ?";
            PreparedStatement prestmt = con.prepareStatement(sql);
            prestmt.setString(1, user_id);
            prestmt.setString(2, password);

            ResultSet resultSet = prestmt.executeQuery(); 
            
            while(resultSet.next()) {
                registered = true;
            }

            resultSet.close();
            stmt.close();
            prestmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return registered;
    }

}
