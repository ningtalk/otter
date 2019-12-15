package com.np.otter.experiment.one;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author np
 * @date 2019/12/15
 */
@Configuration
public class MainConfig {

    @Bean(initMethod = "initCompany")
    public Company getCompany() {
        Company company = new Company();
        company.setName("vivo");
        return company;
    }

    @Bean
    public Department getDepartment() {
        Department department = new Department();
        department.setNumber("888");
        return department;
    }
}
