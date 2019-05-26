package com.linln;

import com.linln.modules.activity.service.ActivityService;
import com.linln.modules.wxuser.domain.WxUser;
import com.linln.modules.wxuser.repository.WxUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private WxUserRepository wxUserRepository;

    @Test
    public void contextLoads() {

        wxUserRepository.save( WxUser.builder().build());
    }

}