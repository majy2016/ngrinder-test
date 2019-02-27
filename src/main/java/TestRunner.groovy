import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import net.grinder.plugin.http.HTTPRequest
import net.grinder.plugin.http.HTTPPluginControl
import net.grinder.script.GTest
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
// import static net.grinder.util.GrinderUtils.* // You can use this if you're using nGrinder after 3.2.3
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import HTTPClient.Cookie
import HTTPClient.CookieModule
import HTTPClient.HTTPResponse
import HTTPClient.NVPair

/**
 * A simple example using the HTTP plugin that shows the retrieval of a
 * single page via HTTP.
 *
 * This script is automatically generated by ngrinder.
 *
 *
 *    脚本使用示例
 *
 *
 */
@RunWith(GrinderRunner)
class TestRunner extends BaseTest  {

    //加载的资源文件内容
    public static List jsons
    //加载header文件内容
    public static List header

    /**
     * 进程启动前执行，加载资源文件、初始化 GTest
     *
     */
    @BeforeProcess
    public static void beforeProcess() {
        grinder.logger.info("before process.")
        HTTPPluginControl.getConnectionDefaults().timeout = 60000
        test = new GTest(1, "148.70.59.59")
        url = "http://148.70.59.59:5000"
        uri = "/"
        request = new HTTPRequest()
        //加载资源文件
        jsons = BaseTestUtils.getResourcesList("/test.txt")
        header = BaseTestUtils.getResourcesList("/headers.txt")
        grinder.logger.info("当前加载header:{}",header)
    }

    /**
     * 进程执行前执行，初始化数据之类
     *
     */
    @BeforeThread
    public void beforeThread() {
        grinder.logger.info("before thread.")
        test.record(this, "test")
        grinder.statistics.delayReports=true
        jsonBody = BaseTestUtils.getTextBySequence(jsons)
        headers = BaseTestUtils.setHeaders(headers,header)
        grinder.logger.info("当前线程header:{}",headers)
    }

    /**
     * @Test注解前执行，可用于设置TEST共用逻辑
     *
     */
    @Before
    public void before() {
        grinder.logger.info("before thread. init headers and cookies")
        request.setHeaders(headers)
        cookies.each { CookieModule.addCookie(it, HTTPPluginControl.getThreadHTTPClientContext()) }
    }

    /**
     *  测试结束前一直会执行，各种TEST方法异步执行
     *  @Test 注解里可共享成员变量
     *  只放测试断言逻辑
     */
    @Test
    public void test(){

        HTTPResponse result = request.POST(url+uri, jsonBody)

        assertResult(result)
    }
}
