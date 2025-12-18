package com.g2rain.common.exception;

import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Strings;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * 消息解析工具类，用于处理异常信息模板中的占位符替换。
 *
 * <p>本工具类支持占位符格式：{@code {index:key}}，例如：
 * <ul>
 *   <li>{@code {0:username}}</li>
 *   <li>{@code {1:age}}</li>
 *   <li>{@code {2:user.name}}</li>
 * </ul>
 *
 * <p>主要功能：
 * <ul>
 *   <li>{@link #resolveByIndex(String, Object...)}：按占位符的 index 替换值</li>
 *   <li>{@link #resolveByKey(String, Map)}：按占位符的 key 替换值</li>
 * </ul>
 *
 * <p>设计原则：
 * <ul>
 *   <li>统一占位符格式为 {@code {index:key}}，方便模板解析</li>
 *   <li>利用 JDK 21 的 {@code Matcher.replaceAll(Function)} 优化性能</li>
 *   <li>模板解析逻辑集中在私有方法 {@link #resolveTemplate(String, Function)} 中，方便复用</li>
 * </ul>
 *
 * <p>使用场景：
 * <ul>
 *   <li>异常信息模板替换</li>
 *   <li>日志输出模板替换</li>
 *   <li>动态消息生成</li>
 * </ul>
 *
 * <p>示例：
 * <pre>{@code
 * // 示例 1：按索引替换
 * String template1 = "用户{0:username}的年龄是{1:age}岁";
 * String result1 = MessageResolver.resolveByIndex(template1, "张三", 25);
 * // result1 = "用户张三的年龄是25岁"
 *
 * // 示例 2：索引越界时保留原占位符
 * String template2 = "用户{0:username}的年龄是{2:age}岁";
 * String result2 = MessageResolver.resolveByIndex(template2, "张三");
 * // result2 = "用户张三的年龄是{2:age}岁"
 *
 * // 示例 3：按 key 替换
 * String template3 = "用户{0:username}的年龄是{1:age}岁";
 * Map<String, Object> params = Map.of("username", "李四", "age", 30);
 * String result3 = MessageResolver.resolveByKey(template3, params);
 * // result3 = "用户李四的年龄是30岁"
 *
 * // 示例 4：找不到 key 时保留占位符
 * String template4 = "用户{0:username}的年龄是{1:age}岁";
 * Map<String, Object> params2 = Map.of("username", "王五");
 * String result4 = MessageResolver.resolveByKey(template4, params2);
 * // result4 = "用户王五的年龄是{1:age}岁"
 * }</pre>
 *
 * <p><b>JDK 21 特性说明：</b>
 * <ul>
 *   <li>{@code Matcher.replaceAll(Function)} 替换占位符，性能更优</li>
 *   <li>正则编译模式静态缓存，避免重复编译</li>
 * </ul>
 *
 * @author jagger
 * @since 2025/9/25
 */
public class MessageResolver {
    private MessageResolver() {
        // 私有构造，防止实例化
    }

    /**
     * 匹配 {index:key} 占位符的正则模式
     * <p>支持格式：
     * <ul>
     *   <li>{@code {0:username}}</li>
     *   <li>{@code {1:user.name}}</li>
     * </ul>
     * 最多支持 5 层命名空间（如 user.profile.displayName）。
     */
    private static final Pattern INDEX_KEY_PATTERN = Pattern.compile(
        "\\{(\\d+):([a-zA-Z_$][a-zA-Z0-9_$]*(?:\\.[a-zA-Z_$][a-zA-Z0-9_$]*){0,5})}"
    );

    /**
     * 按占位符索引替换模板中的值。
     *
     * <p>处理逻辑：
     * <ol>
     *   <li>若 {@code template} 为空或 {@code args} 为空 → 返回原模板</li>
     *   <li>匹配所有 {@code {index:key}} 占位符，按 index 从 {@code args} 中取值替换</li>
     *   <li>index 越界或格式错误 → 保留原占位符</li>
     * </ol>
     *
     * @param template 消息模板，包含 {index:key} 格式占位符
     * @param args     按索引顺序替换占位符的参数
     * @return 替换后的字符串
     */
    public static String resolveByIndex(String template, Object... args) {
        if (Collections.isEmpty(args)) {
            return template;
        }

        return resolveTemplate(template, mr -> {
            try {
                int idx = Integer.parseInt(mr.group(1));
                if (idx >= 0 && idx < args.length) {
                    return Objects.toString(args[idx], "");
                }

                return mr.group(0); // 越界保留原占位符
            } catch (NumberFormatException e) {
                return mr.group(0); // 格式异常保留占位符
            }
        });
    }

    /**
     * 按占位符 key 替换模板中的值。
     *
     * <p>处理逻辑：
     * <ol>
     *   <li>若 {@code template} 为空或 {@code params} 为空 → 返回原模板</li>
     *   <li>匹配所有 {@code {index:key}} 占位符，按 key 从 {@code params} 获取值替换</li>
     *   <li>key 不存在 → 保留原占位符</li>
     * </ol>
     *
     * @param template 消息模板，包含 {index:key} 格式占位符
     * @param params   按 key 替换占位符的参数
     * @return 替换后的字符串
     */
    public static String resolveByKey(String template, Map<String, Object> params) {
        if (Collections.isEmpty(params)) {
            return template;
        }

        return resolveTemplate(template, mr -> {
            String key = mr.group(2);
            Object value = params.get(key);
            return Objects.nonNull(value) ? String.valueOf(value) : mr.group(0);
        });
    }

    /**
     * 核心模板解析方法。
     *
     * @param template 替换模板
     * @param replacer 替换策略，输入 MatchResult → 输出替换值
     * @return 替换后的字符串
     */
    private static String resolveTemplate(String template, Function<MatchResult, String> replacer) {
        return Strings.isNotBlank(template) ? INDEX_KEY_PATTERN.matcher(template).replaceAll(replacer) : template;
    }
}
