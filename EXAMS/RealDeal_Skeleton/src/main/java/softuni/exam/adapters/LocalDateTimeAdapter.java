package softuni.exam.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String string) throws Exception {
        return LocalDateTime
                .parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
//        return localDateTime.toString();
        return null;
    }
}
