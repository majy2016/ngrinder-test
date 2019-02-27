import HTTPClient.Cookie
import HTTPClient.HTTPResponse
import HTTPClient.NVPair
import net.grinder.plugin.http.HTTPRequest
import net.grinder.script.GTest

import static net.grinder.script.Grinder.grinder
import static org.hamcrest.Matchers.is
import static org.junit.Assert.assertThat

/**
 *
 * 基础测试类
 *
 */

class BaseTest {

    protected static GTest test
    protected static HTTPRequest request
    protected static NVPair[] headers = []
    protected static Cookie[] cookies = []
    // HTTP请求域名
    protected static String url
    //HTTP请求接口后缀
    protected static String uri
    //HTTP POST参数
    protected static byte[] jsonBody

    //断言结果
    protected void assertResult(HTTPResponse result){
        if (result.statusCode == 301 || result.statusCode == 302) {
            grinder.logger.warn("Warning. The response may not be correct. The response code was {}.", result.statusCode);
        } else {
            assertThat(result.statusCode, is(200));
        }
    }
}
