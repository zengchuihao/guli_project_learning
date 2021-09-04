package com.zengchuihao.demo.eduService.client;

import com.zengchuihao.commonutils.R;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFileDegradeFeignClient implements OrderClient {

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
