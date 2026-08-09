package util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtilHotel {

    private DateUtilHotel() {}

    public static Date toSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate toLocalDate(Date sqlDate) {
        return sqlDate.toLocalDate();
    }

}
