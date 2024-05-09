package com.example.demo.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:57
 * @Version: 1.0
 */
public class XssShieldUtils {
    private static volatile List<Pattern> patterns = null;

    public XssShieldUtils() {
    }

    private static List<Object[]> getXssPatternList() {
        List<Object[]> ret = new ArrayList<>();
        ret.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", 2});
        ret.add(new Object[]{"eval\\((.*?)\\)", 42});
        ret.add(new Object[]{"expression\\((.*?)\\)", 42});
        ret.add(new Object[]{"(javascript:|vbscript:|view-source:)*", 2});
        ret.add(new Object[]{"<(\"[^\"]*\"|'[^']*'|[^'\">])*>", 42});
        ret.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", 42});
        ret.add(new Object[]{"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", 42});
        return ret;
    }

    private static List<Pattern> getPatterns() {
        if (patterns == null) {
            List<Pattern> list = new ArrayList();
            String regex = null;
            Integer flag = null;
            int arrLength = 0;
            Iterator iterator = getXssPatternList().iterator();

            while (iterator.hasNext()) {
                Object[] arr = (Object[]) iterator.next();
                arrLength = arr.length;

                for (int i = 0; i < arrLength; ++i) {
                    regex = (String) arr[0];
                    flag = (Integer) arr[1];
                    list.add(Pattern.compile(regex, flag));
                }
            }

            patterns = list;
        }

        return patterns;
    }

    public static String stripXss(String value) {
        if (StringUtils.isNotBlank(value)) {
            Matcher matcher = null;
            Iterator iterator = getPatterns().iterator();

            while (iterator.hasNext()) {
                Pattern pattern = (Pattern) iterator.next();
                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    value = matcher.replaceAll("");
                }
            }

            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }

        return value;
    }
}
