package usedb;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
//import java.util.Date;
//import java.text.SimpleDateFormat;

public class DbSelectRoom implements Serializable {
    public List<String[]> Select() {
    	
        List<String[]> room_info = new ArrayList<>();
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
            String sql = "SELECT * FROM ROOM";
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
				
                int id = resultSet.getInt("ROOM_ID");
                String name = resultSet.getString("ROOM_NAME");
                int capacity = resultSet.getInt("CAPACITY");
                boolean eANDd = resultSet.getBoolean("EANDD");
                String equipment = resultSet.getString("EQUIPMENT");

                String[] setData = new String[5];
                setData[0] = String.valueOf(id);
                setData[1] = "部屋名：" + name;
                setData[2] = "目安収納人数：" + String.valueOf(capacity);
                if(eANDd == true) {
                    setData[3] = "飲食：〇";
                }
                else {
                    setData[3] = "飲食：×";
                }
                if(equipment == null){
                    setData[4] = "備品：-";
                }
                else{
                    setData[4] = "備品：" + equipment;
                }

                room_info.add(setData);

            }

            resultSet.close();
            stmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return room_info;
    }
}
