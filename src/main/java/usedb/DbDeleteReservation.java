package usedb;
import java.io.Serializable;
import java.sql.*;

public class DbDeleteReservation implements Serializable {
    public void Delete(String reserve_id) {
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
            String sql = "DELETE FROM RESERVATION WHERE RESERVE_ID = ?";
            PreparedStatement prestmt = con.prepareStatement(sql);
            prestmt.setString(1, reserve_id);

            // データベースの更新
			prestmt.executeUpdate();
            prestmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
