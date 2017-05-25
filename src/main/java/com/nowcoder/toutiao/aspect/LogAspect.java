package com.nowcoder.toutiao.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


/**
 * Created by liwei on 17/5/19.
 */
@Aspect           //创建切面
public class LogAspect {
    public static final Logger logger= org.slf4j.LoggerFactory.getLogger(LogAspect.class);

}
