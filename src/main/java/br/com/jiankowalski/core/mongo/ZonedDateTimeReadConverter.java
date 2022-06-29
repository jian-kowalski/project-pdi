package br.com.jiankowalski.core.mongo;

import org.springframework.core.convert.converter.Converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {
  @Override
  public ZonedDateTime convert(Date date) {
    return date.toInstant().atZone(ZoneId.of("UTC"));
  }
}
