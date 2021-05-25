package com.nocarpe.test;

import com.nocarpe.openutil.feign.FeignUtilClient;
import feign.Param;
import feign.RequestLine;
import java.util.List;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
@FeignUtilClient(name = "tcp", url = "${tcp.url}")
public interface TestClient {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<BaseResp> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
