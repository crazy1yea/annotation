package com.example.annotation.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title:StringUtils.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2021</p >
 * <p>Date:2020/9/10 16:20</p >
 *
 * @author yangy (yangyangd@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
public class StringUtils {

    public static String replaceHtml(String str){
        String str_return = "";
        if(str!=null && !str.trim().equals("")){
            String regexHtml = "<[^>]+>";
            Pattern pHtml = Pattern.compile(regexHtml, Pattern.CASE_INSENSITIVE);
            Matcher mHtml = pHtml.matcher(str);
            // 过滤html标签
            str_return =  mHtml.replaceAll("");

            //str_return = str_return.replaceAll("\"", "");
        }
        return str_return;
    }

    /**
     * 替换文本中的换行符等
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String destination = "";
        if (str != null) {
            Pattern p = Pattern.compile("[\\s\\t\\r\\n]+");
            Matcher m = p.matcher(str);
            destination = m.replaceAll("");
        }
        return destination;
    }

    /**
     * |â|Â |ï¸|â|Â|Â|Â|Â|Â|||||||
     * @param str
     * @return
     */
    public static String replaceLabel(String str) {
        String destination = "";
        if (str != null) {
            str = replaceBlank(str);
            String label = "  ️\u200B\u0085\u0080\u0095\u0087\u0096　";
            String regex = "["+label+"]+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            destination = m.replaceAll("");
        }
        return destination;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //ca_lem_local_event_message$taijiwxs$202009$taijiwxb$1600145315401$taijiwxs$985560
        //ca_lem_local_event_message$taijiwxs$202009$taijiwxb$1600145251966$taijiwxs$392780
        //ca_lem_local_event_message$taijiwxs$202009$taijiwxb$1600145252046$taijiwxs$956093
        //ca_lem_local_event_message$taijiwxs$202009$taijiwxb$1600145252027$taijiwxs$865275
        String orgin = "　　";
        String label = new String(orgin.getBytes("iso-8859-1"));
        log.info("label : |{}|", label);
        label = new String(orgin.getBytes("utf-8"));
        log.info("label : |{}|", label);
        String result = "";
        String str = "　　";
        result = replaceBlank(str);
        log.info("replaceBlank : |{}|", result);
        result = replaceLabel(str);
        log.info("replaceLabel : |{}|", result);
    }

}
