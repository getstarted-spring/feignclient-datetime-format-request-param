package io.getstarted.spring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "default", path = "/default")
public interface DefaultFormatFeignClient {

  @GetMapping(params = "date")
  String localDate(@RequestParam("date") final LocalDate date);

  @GetMapping(params = "time")
  String localTime(@RequestParam("time") final LocalTime time);

  @GetMapping(params = "dateTime")
  String localDateTime(@RequestParam("dateTime") final LocalDateTime dateTime);
}
