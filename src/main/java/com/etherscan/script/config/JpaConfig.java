package com.etherscan.script.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.etherscan.script.repositories")
public class JpaConfig
{
}
