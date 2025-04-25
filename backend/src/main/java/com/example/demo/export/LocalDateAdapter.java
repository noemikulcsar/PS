package com.example.demo.export;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    @Override
    public LocalDate unmarshal(String v) {
        return (v == null || v.isEmpty()) ? null : LocalDate.parse(v, formatter);
    }
    @Override
    public String marshal(LocalDate v) {
        return (v == null) ? null : v.format(formatter);
    }
}

