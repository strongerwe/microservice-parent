package cn.stronger.we.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 参数转换函数工具
 * @department Platform Center
 * @date 2024-09-07 16:28
 */
public class ParserTools {

    /**
     * 模板参数替换函数
     *
     * @param template template
     * @param args     args
     * @return {@link String }
     */
    public static String parse(String template, Object... args) {
        return ParserTools.ops("{", "}", template, args);
    }

    public static String customize(String template, String openWith, String closeWith, Object... args) {
        return ParserTools.ops(openWith, closeWith, template, args);
    }

    private static String ops(String openWith, String endWith, String text, Object... args) {
        int argsIndex = 0;
        if (StringUtils.isBlank(text)) {
            return "";
        }
        if (Objects.isNull(args) || args.length <= 0) {
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        // search open token
        int start = text.indexOf(openWith, offset);
        if (start == -1) {
            return text;
        }
        final StringBuilder sb = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                sb.append(src, offset, start - offset - 1).append(openWith);
                offset = start + openWith.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                sb.append(src, offset, start - offset);
                offset = start + openWith.length();
                int end = text.indexOf(endWith, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(endWith);
                        offset = end + endWith.length();
                        end = text.indexOf(endWith, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + endWith.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    sb.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    String value = (argsIndex <= args.length - 1) ?
                            (args[argsIndex] == null ? ""
                                    : args[argsIndex].toString()) : expression.toString();
                    sb.append(value);
                    offset = end + endWith.length();
                    argsIndex++;
                }
            }
            start = text.indexOf(openWith, offset);
        }
        if (offset < src.length) {
            sb.append(src, offset, src.length - offset);
        }
        return sb.toString();
    }
}
