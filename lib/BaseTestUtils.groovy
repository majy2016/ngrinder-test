import HTTPClient.NVPair
import org.codehaus.groovy.reflection.ReflectionUtils

import java.lang.reflect.Array

/**
 *  共用测试方法工具类
 *
 */
class BaseTestUtils {

    /**
     * 加载Resources目录下面文件,带相对路径，如： /test.txt
     * @param resoucePath
     * @return
     */
    public static loadResourceFromClassPath(String resoucePath){
        return ReflectionUtils.getCallingClass(0).getResourceAsStream(resoucePath)
    }

    /**
     *  读取文件
     *  将加载的文件内容，按照每行，处理成一个数组返回
     * @param resoucePath
     */
    public static List getResourcesList(String resoucePath){
        String text = loadResourceFromClassPath(resoucePath).text
        return new ArrayList(Arrays.asList(text.split("\n")))
    }

    /**
     * 按顺序循环取出数组里面的数据返回 byte[]
     * 注：参数必须放在静态公共变量里，每次返回第一个值，将其改变
     * @param texts
     * @return
     */
    public static byte[] getTextBySequence(List texts){
        if (null!=texts&&texts.size()>0){
            String r = texts[0]
            byte [] rr = r.getBytes()
            texts.remove(0)
            texts.add(r)
            return rr
        }
    }

    /**
     * 随机取出数组里面的数据返回 byte[]
     * @param texts
     * @return
     */
    public static byte [] getTextByRandom(List texts){
        Random random = new Random()
        int i = random.nextInt(texts.size())
        return texts[i].toString().getBytes()
    }

    /**
     *  head设置
     *  headers.txt 文件目录格式示例：
     *      Content-Type:application/json
     * @param headers
     * @param header
     * @return
     */
    public static NVPair[] setHeaders(NVPair[] headers,List<String> header){
        if (header.size()==0){
            return headers
        }
        List<NVPair> l = new ArrayList<>(Arrays.asList(headers))
        for(int i=0;i<header.size();i++){
            String[] r = header[i].split(":")
            l.add(new NVPair(r[0],r[1]))
        }
        return l.toArray()
    }
}
