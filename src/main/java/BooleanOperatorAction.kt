import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import org.apache.commons.lang3.BooleanUtils

/**
 * @author zhixiao.mzx
 * @date 2021/08/02
 */
class BooleanOperatorAction : PsiElementBaseIntentionAction() {
    private var prefixExpPointer: SmartPsiElementPointer<PsiElement>? = null

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val prefixExpression = prefixExpPointer!!.element as PsiPrefixExpression
        var operand = prefixExpression.operand!!
        if (operand is PsiParenthesizedExpression) {
            operand = operand.expression!!
        }

        val exp = "BooleanUtils.isFalse(a)"
        val factory = JavaPsiFacade.getElementFactory(project)
        val psiNewExpression = factory.createExpressionFromText(exp, null) as PsiMethodCallExpression
        psiNewExpression.argumentList.expressions[0].replace(operand)
        prefixExpression.replace(psiNewExpression)
    }

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        var context = element.context
        if (context !is PsiPrefixExpression) {
            context = element.parent.context
        }

        if (context !is PsiPrefixExpression) {
            return false
        }

        val prefixExpression = context
        if (JavaTokenType.EXCL == prefixExpression.operationSign.tokenType) {
            prefixExpPointer = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(prefixExpression)
            return true
        }
        return false
    }

    override fun getFamilyName(): @IntentionFamilyName String {
        return "BooleanOperatorAction: getFamilyName"
    }

    override fun getText(): @IntentionName String {
        return ACTION_HINT_TEXT
    }

    companion object {
        const val ACTION_HINT_TEXT = "BooleanOperatorAction: getText"
    }
}