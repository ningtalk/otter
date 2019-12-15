package com.np.otter.experiment.one;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author np
 * @date 2019/12/15
 */
public class Company implements InitializingBean {

    private String name;

    public Company() {
        System.out.println("构造函数");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行InitializingBean的afterPropertiesSet()");
    }

    public void initCompany() {
        System.out.println("执行initCompany()");
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }
}
