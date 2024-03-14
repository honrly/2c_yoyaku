package usedb;
import java.io.Serializable;
import java.sql.*;

public class DbSelectAvailability implements Serializable {
    public String[][] Select(String setDate) {
    	
        String[][] available = new String[10][13]; // 戻り値、部屋が10個、時間帯が9時から22時で13時間
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
            String sql = "SELECT * FROM AVAILABILITY WHERE start_time BETWEEN ? AND ?";
            PreparedStatement prestmt = con.prepareStatement(sql);
            prestmt.setTimestamp(1, Timestamp.valueOf(setDate + " 00:00:00"));
            prestmt.setTimestamp(2, Timestamp.valueOf(setDate + " 23:59:59"));


            ResultSet resultSet = prestmt.executeQuery(); 

            while(resultSet.next()) {
                int room_id = resultSet.getInt("ROOM_ID");
                Timestamp start_time = resultSet.getTimestamp("START_TIME");
                Timestamp end_time = resultSet.getTimestamp("END_TIME");

                // タイムスタンプを時間に変換（1時間ごとのスロットと仮定）
                int start_hour = start_time.toLocalDateTime().getHour();
                int end_hour = end_time.toLocalDateTime().getHour();

                // 予約されたスロットを"×"でマークする
                for (int i = start_hour; i < end_hour; i++) {
                    // 部屋IDに応じて配列のインデックスを調整
                    int roomIndex = -1;
                    switch (room_id) {
                        case 101:
                            roomIndex = 0;
                            break;
                        case 102:
                            roomIndex = 1;
                            break;
                        case 103:
                            roomIndex = 2;
                            break;
                        case 201:
                            roomIndex = 3;
                            break;
                        case 202:
                            roomIndex = 4;
                            break;
                        case 203:
                            roomIndex = 5;
                            break;
                        case 301:
                            roomIndex = 6;
                            break;
                        case 302:
                            roomIndex = 7;
                            break;
                        case 303:
                            roomIndex = 8;
                            break;
                        case 401:
                            roomIndex = 9;
                            break;
                        default:
                            break;
                    }

                    // インデックスが範囲内の場合のみ"×"でマーク
                    if (roomIndex >= 0 && roomIndex < 10) {
                        available[roomIndex][i - 9] = "×";
                    } else {
                        // インデックスが範囲外の場合のエラー処理
                        System.out.println("Invalid roomIndex: " + roomIndex);
                    }
                }
            }

            resultSet.close();
            stmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return available;
    }

}
