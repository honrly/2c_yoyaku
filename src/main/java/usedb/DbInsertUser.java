package usedb;
import java.io.Serializable;
import java.sql.*;

public class DbInsertUser implements Serializable {
    public void Insert(String user_id, String password, String reserver_name, String tell) {

        try {
        	// PostgreSQLでのデータベースへの接続
    		// 接続先とポート: tokushima.data.ise.shibaura-it.ac.jp:5432/
    		// データベース: group1
    		// ユーザ: group1_user
    		// パスワード: bond

			String database = "group1";
            String server = "tokushima.data.ise.shibaura-it.ac.jp:5432/";
			String url="jdbc:postgresql://" + server + database;
            Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url, "group1_user", "bond");	

    		// レコードの追加
    		String sql = "INSERT INTO USER_INFO VALUES (?, ?, ?, ?)";
    		PreparedStatement prestmt = con.prepareStatement(sql);
    		prestmt.setString(1, user_id);
    		prestmt.setString(2, password);
    		prestmt.setString(3, reserver_name);
			prestmt.setString(4, tell);

    		// データベースの更新
			prestmt.executeUpdate();
			// プリファードステートメントオブジェクトのクローズ
			prestmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
