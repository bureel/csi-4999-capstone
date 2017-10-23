package com.seniorproject.mims.config;

import net.sargue.mailgun.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MailGunConfig {
    @Bean
    public Configuration mailGunConfiguration() {
        return new Configuration()
         .domain("sandbox6ecc3cfccae04697a128a0a31e9f58aa.mailgun.org")
         .apiKey("key-b53a6d2ce14a76785aab60a1ca3edf9d")
         .from("My Student Email Account", "bureel@oakland.edu");
    }
}
