package io.getstarted.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

@SpringBootTest(
    classes = ParamFormatFeignClientTests.FeignConfiguration.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
class ParamFormatFeignClientTests {

  @Autowired ParamFormatFeignClient feignClient;

  @Test
  public void localDate() {
    final LocalDate date = LocalDate.parse("2019-12-31");
    assertThat(this.feignClient.localDate(date)).isEqualTo("2019-12-31");
  }

  @Test
  public void localTime() {
    final LocalTime time = LocalTime.parse("12:59:59");
    assertThat(this.feignClient.localTime(time)).isEqualTo("12:59:59");
  }

  @Test
  public void localDateTime() {
    final LocalDateTime dateTime = LocalDateTime.parse("2019-12-31T12:59:59");
    assertThat(this.feignClient.localDateTime(dateTime)).isEqualTo("2019-12-31T12:59:59");
  }

  @EnableFeignClients(clients = ParamFormatFeignClient.class)
  @RestController
  @RequestMapping(path = "/param")
  @Configuration
  @EnableAutoConfiguration
  @RibbonClient(
      name = "param",
      configuration = ParamFormatFeignClientTests.RibbonConfiguration.class)
  static class FeignConfiguration {

    @GetMapping(params = {"date"})
    public String localDate(@RequestParam("date") final String date) {
      return date;
    }

    @GetMapping(params = {"time"})
    public String localTime(@RequestParam("time") final String time) {
      return time;
    }

    @GetMapping(params = {"dateTime"})
    public String localDateTime(@RequestParam("dateTime") final String dateTime) {
      return dateTime;
    }
  }

  @Configuration
  static class RibbonConfiguration {

    @LocalServerPort int port;

    @Bean
    public ServerList<Server> serverList() {
      return new StaticServerList<>(new Server("127.0.0.1", port), new Server("127.0.0.1", port));
    }
  }
}
