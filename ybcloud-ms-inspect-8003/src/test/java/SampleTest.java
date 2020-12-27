/*
package com.yaobanTech.springcloud;

import com.yaobanTech.springcloud.mapper.InspectMapper;
import com.yaobanTech.springcloud.pojos.Inspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private InspectMapper inspectMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Inspect> userList = inspectMapper.selectList(null);
        if(userList.size()>0){
            System.out.println("11");
        }else{
            System.out.println("00");
        }
    }

}*/
