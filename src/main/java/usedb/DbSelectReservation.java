package usedb;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
//import java.util.Date;
//import java.text.SimpleDateFormat;

public class DbSelectReservation implements Serializable {
    public List<String[]> Select() {
    	
        List<String[]> Reservations = new ArrayList<>();
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
            String sql = "SELECT * FROM RESERVATION";
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
				
                String reserve_id = resultSet.getString("RESERVE_ID");
                String reserve_name = resultSet.getString("RESERVE_NAME");
                //String user_id = resultSet.getString("USER_ID");
                int room_id = resultSet.getInt("ROOM_ID");
                int num_p = resultSet.getInt("NUM_P");
                String reason = resultSet.getString("REASON");
                String note = "";
                if (resultSet.getString("NOTE") != null || !resultSet.getString("NOTE").isEmpty()) {
                    note = resultSet.getString("NOTE");
                }
                Timestamp reception_date = resultSet.getTimestamp("RECEPTION_DATE");
                Timestamp start_time = resultSet.getTimestamp("START_TIME");
                Timestamp end_time = resultSet.getTimestamp("END_TIME");

                String[] setData = new String[9];
                setData[0] = reserve_id;
                setData[1] = reserve_name;
                //setData[2] = user_id;
                setData[2] = String.valueOf(room_id);
                setData[3] = String.valueOf(num_p);
                setData[4] = reason;
                setData[5] = note;
                setData[6] = String.valueOf(reception_date);
                setData[7] = String.valueOf(start_time);
                setData[8] = String.valueOf(end_time);

                Reservations.add(setData);

            }

            resultSet.close();
            stmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        //逆順に並び替える
        Collections.reverse(Reservations);

        return Reservations;
    }

}
