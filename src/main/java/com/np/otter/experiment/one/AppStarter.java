package com.np.otter.experiment.one;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author np
 * @date 2019/12/15
 */
public class AppStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        context.close();
    }
}
