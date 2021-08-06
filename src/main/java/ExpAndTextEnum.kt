/**
 * @author zhixiao.mzx
 * @date 2021/08/06
 */
enum class ExpAndTextEnum(val oldExp: String, val newExp: String) {
    StringUtils_isBlank(
        "StringUtils.isBlank(",
        "StringUtils.isNotBlank(a)"
    ),
    StringUtils_isEmpty(
        "StringUtils.isEmpty(",
        "StringUtils.isNotEmpty(a)"
    ),
    CollectionUtils_isEmpty(
        "CollectionUtils.isEmpty(",
        "CollectionUtils.isNotEmpty(a)"
    ),
    MapUtils_isEmpty("MapUtils.isEmpty(", "MapUtils.isNotEmpty(a)");

    val text: String = "Change to '$newExp'"
}