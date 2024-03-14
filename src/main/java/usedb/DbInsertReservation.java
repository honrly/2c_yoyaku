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
import java.util.Random;

public class DbInsertReservation implements Serializable {
    public void Insert(String reserve_name, int room_id, String user_id, int num_p,
                        String reason, String note, String reserve_day, String start_time, String end_time) {

        try {
            String database = "group1";
            String server = "tokushima.data.ise.shibaura-it.ac.jp:5432/";
            String url = "jdbc:postgresql://" + server + database;
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, "group1_user", "bond");

            String sql = "INSERT INTO RESERVATION VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prestmt = con.prepareStatement(sql);

            String reserve_id = generateRandomNumericId(10);
            prestmt.setString(1, reserve_id);
            prestmt.setString(2, reserve_name);
            prestmt.setString(3, user_id);
            //int roomIdInt = Integer.parseInt(room_id);
            prestmt.setInt(4, room_id);
            System.out.println(room_id);
            prestmt.setInt(5, num_p);
            prestmt.setString(6, reason);
            prestmt.setString(7, note);
            prestmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            
            LocalDate reserveDate = LocalDate.parse(reserve_day);

            // 時間のフォーマットが "HH:mm" であることを仮定
            LocalTime startTime = LocalTime.parse(start_time);
            LocalTime endTime = LocalTime.parse(end_time);

            // reserveDateの時刻部分を0時に設定する
            LocalDate zeroTimeDate = reserveDate.atStartOfDay().toLocalDate();

            prestmt.setTimestamp(9, new Timestamp(zeroTimeDate.atTime(startTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            prestmt.setTimestamp(10, new Timestamp(zeroTimeDate.atTime(endTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

            prestmt.executeUpdate();
            prestmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomNumericId(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than zero");
        }

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 0から9の乱数を生成
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }


}
