package com.g2rain.common.enums;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 组织类型枚举类，表示平台中的不同组织类型。
 * <p>
 * 该枚举类通过位掩码技术（`RULE_MASK`）来控制组织类型之间的层级关系。每种组织类型可以与其他组织类型建立层级关系，而这些关系通过一个 16 位的整数来表达，
 * 使用位运算判断源类型和目标类型是否允许建立关系。
 * </p>
 *
 * <h3>关系矩阵</h3>
 * <p>
 * 组织类型之间的层级关系被编码为一个 4x4 的关系矩阵，其中：
 * <ul>
 *   <li>行代表源组织类型（即起始组织）</li>
 *   <li>列代表目标组织类型（即关联的目标组织）</li>
 *   <li>矩阵中的每个位置（即源类型和目标类型的组合）值为 <code>1</code> 表示允许建立层级关系，</li>
 *   <li>为 <code>0</code> 表示不允许建立关系。</li>
 * </ul>
 * </p>
 *
 * <h4>关系矩阵示例：</h4>
 * <pre>
 *      目标类型
 *       C     S    O    T
 *     ┌─────────────────────┐
 *   C │ ✅   ❌   ✅   ✅   ← 渠道商 (C) 和其他类型的关系
 *   S │ ❌   ❌   ✅   ✅   ← 服务商 (S) 和其他类型的关系
 *   O │ ❌   ❌   ✅   ✅   ← 公司   (O) 和其他类型的关系
 *   T │ ❌   ❌   ❌   ❌   ← 租户   (T) 和其他类型的关系
 *     └─────────────────────┘
 * </pre>
 *
 * <p>
 * <ul>
 *   <li><code>C</code> 代表渠道商 (<code>SALES_PARTNER</code>)</li>
 *   <li><code>S</code> 代表服务商 (<code>SERVICE_PROVIDER</code>)</li>
 *   <li><code>O</code> 代表公司 (<code>COMPANY</code>)</li>
 *   <li><code>T</code> 代表租户 (<code>TENANT</code>)</li>
 * </ul>
 *
 * <h4>关系掩码（`RULE_MASK`）</h4>
 * <p>
 * 该矩阵的每个格子（即源类型和目标类型的组合）都对应着一个二进制位。通过一个 16 位整数（`RULE_MASK`）来表示整个矩阵。
 * <p>
 * 例如，当前的 `RULE_MASK` 值是 `3277`，其二进制表示为：
 * <pre>{@code
 *     0000110011001101
 * }</pre>
 * 该二进制数的每一位对应矩阵中的一个格子，从右往左依次表示矩阵中每一格的值（从最低位到最高位）。
 *
 * <pre>{@code
 * // 生成掩码的方法实现
 * public static long[] generateMask(boolean[][] matrix) {
 *     int matrixSize = matrix.length;
 *     int totalBits = matrixSize * matrixSize;
 *     int longsNeeded = (totalBits + 63) / 64;
 *     long[] mask = new long[longsNeeded];
 *
 *     for (int i = 0; i < matrixSize; i++) {
 *         for (int j = 0; j < matrixSize; j++) {
 *             if (matrix[i][j]) {
 *                 int pos = i * matrixSize + j;
 *                 mask[pos / 64] |= 1L << (pos % 64);
 *             }
 *         }
 *     }
 *
 *     return mask;
 * }
 *
 * // 创建布尔矩阵，设置允许关系
 * boolean[][] matrix = new boolean[4][4];
 * matrix[OrganType.SALES_PARTNER.getIndex()][OrganType.COMPANY.getIndex()] = true;
 * matrix[OrganType.SALES_PARTNER.getIndex()][OrganType.TENANT.getIndex()] = true;
 *
 * // 使用工具方法生成 long[] 掩码
 * long[] mask = OrganTypeMaskUtil.generateMask(matrix);
 * }</pre>
 *
 * </p>
 *
 * <h5>矩阵与 `RULE_MASK` 的映射关系：</h5>
 * <pre>
 *     0000110011001101
 *     C → C:  ✅  -> 位 0
 *     C → S:  ❌  -> 位 1
 *     C → O:  ✅  -> 位 2
 *     C → T:  ✅  -> 位 3
 *     S → C:  ❌  -> 位 4
 *     S → S:  ❌  -> 位 5
 *     S → O:  ✅  -> 位 6
 *     S → T:  ✅  -> 位 7
 *     O → C:  ❌  -> 位 8
 *     O → S:  ❌  -> 位 9
 *     O → O:  ✅  -> 位 10
 *     O → T:  ✅  -> 位 11
 *     T → C:  ❌  -> 位 12
 *     T → S:  ❌  -> 位 13
 *     T → O:  ❌  -> 位 14
 *     T → T:  ❌  -> 位 15
 * </pre>
 * <p>
 * 通过这个 `RULE_MASK`，我们可以非常高效地查询任意两个组织类型之间的关系，利用位运算来判断它们是否允许建立层级关系。
 * </p>
 *
 * <h3>公式和位运算</h3>
 * <p>
 * 通过源类型和目标类型的索引，可以计算出矩阵中的位置，然后利用位运算检查该位置对应的值：
 *
 * <ol>
 *   <li>计算当前位置：<code>position = sourceIndex * MATRIX_SIZE + targetIndex</code></li>
 *   <li>根据 <code>RULE_MASK</code> 右移 <code>position</code> 位：<code>RULE_MASK >> position</code></li>
 *   <li>通过位与运算 `<code>&amp; 1</code>` 获取最低位：如果为 `1`，表示允许建立关系；如果为 `0`，表示不允许建立关系。</li>
 * </ol>
 * </p>
 *
 * <h4>示例：</h4>
 * <pre>{@code
 * SALES_PARTNER.canAssociate(COMPANY)  // 检查渠道商 (C) 和公司 (O) 是否可以建立关系
 * }</pre>
 * 计算过程：
 * <ul>
 *   <li>源类型为渠道商 (C)，目标类型为公司 (O)，索引为：<code>position = 0 * 4 + 2 = 2</code></li>
 *   <li>右移位：<code>RULE_MASK >> 2 = 819</code>，得到二进制：<code>0011</code></li>
 *   <li>最低位为 `1`，表示可以建立关系。</li>
 * </ul>
 *
 * <h4>核心功能</h4>
 * 该类的核心功能是通过 `canAssociate` 方法动态判断源类型和目标类型是否可以建立层级关系，无需手动编写大量的条件判断。
 *
 * @author alpha
 * @since 2025/11/5
 */
@Getter
public enum OrganType {

    /**
     * 销售伙伴 - 渠道商
     */
    SALES_PARTNER("渠道商", 0),

    /**
     * 服务提供者 - 服务商
     */
    SERVICE_PROVIDER("服务商", 1),

    /**
     * 公司
     */
    COMPANY("公司", 2),

    /**
     * 租户
     */
    TENANT("租户", 3);

    /**
     * 存储 {@link OrganType} 类型与其字符串描述的映射关系。
     * key: type 字符串值，例如 "公司"
     * value: 对应的 OrganType 枚举对象
     */
    private static final Map<String, OrganType> VALUE_MAP = new HashMap<>();

    static {
        for (OrganType ot : OrganType.values()) {
            VALUE_MAP.put(ot.type, ot);
        }
    }

    /**
     * 类型的中文描述
     */
    private final String type;

    /**
     * 在关系矩阵中的索引值，用于确定当前组织类型在矩阵中的行或列。
     * <p>
     * 每个组织类型（如 渠道商、服务商、公司、租户）都有一个对应的索引，索引值用于计算源类型和目标类型在矩阵中的位置。
     * </p>
     * <p>
     * 索引值对应关系如下：
     * </p>
     * <ul>
     *     <li>渠道商 (SALES_PARTNER) 对应的索引为 0</li>
     *     <li>服务商 (SERVICE_PROVIDER) 对应的索引为 1</li>
     *     <li>公司 (COMPANY) 对应的索引为 2</li>
     *     <li>租户 (TENANT) 对应的索引为 3</li>
     * </ul>
     * <p>
     * 该索引值用于计算源类型与目标类型之间的关系是否允许建立。通过位掩码和该索引值，可以高效地查询组织类型间的层级关系。
     * </p>
     */
    private final int index;

    /**
     * 动态计算矩阵的大小, 根据枚举值数量动态计算
     */
    private static final int MATRIX_SIZE = OrganType.values().length;

    /**
     * 关系掩码（根据矩阵生成，更新时只需修改此值）
     * 这个数是根据关系矩阵{@code 0000110011001101}计算出来的
     */
    private static final long[] RULE_MASK = {3277L};

    /**
     * 构造函数，初始化组织类型的索引值。
     *
     * @param type  类型的描述字符串，例如 "公司"
     * @param index 组织类型在关系矩阵中的索引值。这个索引用于确定该类型在矩阵中的位置。
     *              例如，渠道商 (SALES_PARTNER) 的索引为 0，服务商 (SERVICE_PROVIDER) 的索引为 1，依此类推。
     */
    OrganType(String type, int index) {
        this.type = type;
        this.index = index;
    }


    /**
     * 判断当前组织类型是否可以与目标类型建立层级关系。
     * <p>
     * 根据源组织类型和目标组织类型的索引，通过位掩码（`RULE_MASK`）计算出对应的位置。
     * 如果对应位置的值为 1，则表示可以建立层级关系，返回 true；否则返回 false。
     * </p>
     *
     * @param target 目标组织类型
     * @return true 如果允许建立关系，false 如果不允许
     *
     * <p>例如：</p>
     * <pre>{@code
     * SALES_PARTNER.canAssociate(COMPANY)  // 判断渠道商 (C) 和公司 (O) 是否能建立关系
     * }</pre>
     * 计算位置：<code>position = 0 * 4 + 2 = 2</code>
     * 通过 <code>RULE_MASK</code> 获取对应位置的值，判断是否为 1（允许关系）或 0（禁止关系）。
     */
    public boolean canAssociate(OrganType target) {
        if (Objects.isNull(target)) {
            return false;
        }

        // 计算当前位置[position = rowIndex * MATRIX_SIZE + colIndex]
        int position = this.index * MATRIX_SIZE + target.index;

        // 如果 position >= 64，需要找到对应的 long 元素和在其中的偏移
        int arrayIndex = position >>> 6;    // 等价于 position / 64
        int bitIndex = position & 63;       // 等价于 position % 64

        // 超出范围的 bit 当然是 false
        if (arrayIndex >= RULE_MASK.length) {
            return false;
        }

        /*
         * 根据位置从 RULE_MASK 中获取对应的位，并判断是否允许建立关系
         * 无符号右移 高位总是填 0, 不考虑符号位
         */
        return ((RULE_MASK[arrayIndex] >>> bitIndex) & 1L) == 1L;
    }

    /**
     * 根据描述字符串查找对应的 {@link OrganType} 枚举值。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * OrganType type = OrganType.typeOf("公司");
     * System.out.println(type); // 输出：COMPANY
     * }</pre>
     *
     * @param type 描述字符串，例如 "公司"
     * @return 对应的 {@link OrganType} 枚举对象，找不到返回 {@code null}
     */
    public static OrganType typeOf(String type) {
        return VALUE_MAP.get(type);
    }

    /**
     * 判断给定枚举对象是否为 {@link #COMPANY}。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = OrganType.isCompany(OrganType.COMPANY);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param type 待判断的 {@link OrganType} 对象
     * @return {@code true} 如果是 {@link #COMPANY} 类型，否则 {@code false}
     */
    public static boolean isCompany(OrganType type) {
        return Objects.nonNull(type) && COMPANY.equals(type);
    }

    /**
     * 判断给定字符串是否为 {@link #COMPANY} 枚举名称。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = OrganType.isCompany("COMPANY");
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param type 待判断的枚举名称字符串，例如 "COMPANY"
     * @return {@code true} 如果是 {@link #COMPANY} 类型，否则 {@code false}
     */
    public static boolean isCompany(String type) {
        return Objects.nonNull(type) && COMPANY.name().equals(type);
    }

    /**
     * 判断给定枚举对象是否为 {@link #TENANT}。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = OrganType.isTenant(OrganType.TENANT);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param type 待判断的 {@link OrganType} 对象
     * @return {@code true} 如果是 {@link #TENANT} 类型，否则 {@code false}
     */
    public static boolean isTenant(OrganType type) {
        return Objects.nonNull(type) && TENANT.equals(type);
    }

    /**
     * 判断给定字符串是否为 {@link #TENANT} 枚举名称。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = OrganType.isTenant("TENANT");
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param type 待判断的枚举名称字符串，例如 "TENANT"
     * @return {@code true} 如果是 {@link #TENANT} 类型，否则 {@code false}
     */
    public static boolean isTenant(String type) {
        return Objects.nonNull(type) && TENANT.name().equals(type);
    }
}
