import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.siyeh.ig.psiutils.BoolUtils
import org.apache.commons.lang3.BooleanUtils

/**
 * @author Cnfn
 * @date 2021/08/02
 */
class CommonNegationBoolean2NotTrueAction : CommonNegationBooleanAction() {
    override fun getExp(): String {
        return "BooleanUtils.isNotTrue(a)"
    }

}