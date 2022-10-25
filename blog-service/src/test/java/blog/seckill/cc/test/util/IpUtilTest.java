package blog.seckill.cc.test.util;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.util.IpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * description: IpUtilTest <br>
 * date: 2022/10/25 16:02 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class IpUtilTest extends TestBase {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void testGetIpRealAddr() {
//        String ip = "209.141.58.148";
//
//        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://opendata.baidu.com/api.php?query=117" +
//                ".136.12.79&co=&resource_id=6006&oe=utf8", String.class);
//
//        System.out.println(forEntity.getBody());
//        JSONObject parse = (JSONObject) JSON.parse(forEntity.getBody());
//        System.out.println(parse.keySet());
//        JSONArray arr = (JSONArray) parse.get("data");
//        System.out.println(">>> arrsize: " + arr.size());
//        System.out.println(" >>>loc" + ((JSONObject) arr.get(0)).get("location"));
//        System.out.println("?>>>>>>>>>>>>" + parse.get("data").getClass());

        String ipPhysicalAddress = IpUtil.getIpPhysicalLocation("209.141.58.148");
        System.out.println(">>>>>" + ipPhysicalAddress);

        ipPhysicalAddress = IpUtil.getIpPhysicalLocation("117.136.12.79");
        System.out.println(">>>>>" + ipPhysicalAddress);
    }


}
