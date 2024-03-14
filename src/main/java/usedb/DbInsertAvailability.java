package usedb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class DbInsertAvailability implements Serializable {
    public void Insert(int room_id, String reserve_day, String start_time, String end_time) {

        try {
            String database = "group1";
            String server = "tokushima.data.ise.shibaura-it.ac.jp:5432/";
            String url = "jdbc:postgresql://" + server + database;
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, "group1_user", "bond");

            String sql = "INSERT INTO AVAILABILITY VALUES (?, ?, ?)";
            PreparedStatement prestmt = con.prepareStatement(sql);

            prestmt.setInt(1, room_id);
            LocalDate reserveDate = LocalDate.parse(reserve_day);

            // 時間のフォーマットが "HH:mm" であることを仮定
            LocalTime startTime = LocalTime.parse(start_time);
            LocalTime endTime = LocalTime.parse(end_time);

            // reserveDateの時刻部分を0時に設定する
            LocalDate zeroTimeDate = reserveDate.atStartOfDay().toLocalDate();
            prestmt.setTimestamp(2, new Timestamp(zeroTimeDate.atTime(startTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            prestmt.setTimestamp(3, new Timestamp(zeroTimeDate.atTime(endTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

            prestmt.executeUpdate();
            prestmt.close();
            con.close();
            

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
