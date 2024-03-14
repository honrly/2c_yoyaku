package usedb;
import java.io.Serializable;
import java.sql.*;

public class DbDeleteAvailability implements Serializable {
    public void Delete(int room_id, String start_time) {
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

            // レコードの取得
            String sql = "DELETE FROM AVAILABILITY WHERE ROOM_ID = ? AND START_TIME = ?";
            PreparedStatement prestmt = con.prepareStatement(sql);
            prestmt.setInt(1, room_id);
            Timestamp timestamp = Timestamp.valueOf(start_time);
            prestmt.setTimestamp(2, timestamp);    

            // データベースの更新
			prestmt.executeUpdate();
            prestmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
