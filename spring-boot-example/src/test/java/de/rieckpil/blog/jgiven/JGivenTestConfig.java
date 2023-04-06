package de.rieckpil.blog.jgiven;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJGiven
@ComponentScan(basePackages = {"de.rieckpil.blog.zendesk"})
public class JGivenTestConfig {}
