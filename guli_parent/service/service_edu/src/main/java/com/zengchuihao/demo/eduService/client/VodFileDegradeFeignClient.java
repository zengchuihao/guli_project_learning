package com.zengchuihao.demo.eduService.client;

import com.zengchuihao.commonutils.R;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("time out");
    }

    @Override
    public R deleteBatch(List<String> idList) {
        return R.error().message("time out");
    }
}
