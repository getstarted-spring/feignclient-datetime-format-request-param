package io.getstarted.spring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "param", path = "/param")
public interface ParamFormatFeignClient {

  @GetMapping(params = "date")
  String localDate(
      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate date);

  @GetMapping(params = "time")
  String localTime(
      @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") final LocalTime time);

  @GetMapping(params = "dateTime")
  String localDateTime(
      @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
          final LocalDateTime dateTime);
}
