package com.github.cnfn.action

/**
 * @author Cnfn
 * @date 2021/08/02
 */
class CommonNegationBoolean2NotTrueAction : CommonNegationBooleanAction() {
    override fun getExp(): String {
        return "BooleanUtils.isNotTrue(a)"
    }

}