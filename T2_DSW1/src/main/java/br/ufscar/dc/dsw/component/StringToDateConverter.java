package br.ufscar.dc.dsw.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            return new Date(dateFormat.parse(source).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }
}
