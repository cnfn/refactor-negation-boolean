import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*

/**
 * @author zhixiao.mzx
 * @date 2021/08/02
 */
abstract class AbstractNegationBooleanAction : PsiElementBaseIntentionAction() {
    private var prefixExpPointer: SmartPsiElementPointer<PsiElement>? = null

    protected fun getTruthOperand(prefixExpression: PsiPrefixExpression): PsiExpression {
        var operand = prefixExpression.operand!!
        if (operand is PsiParenthesizedExpression) {
            operand = operand.expression!!
        }
        return operand
    }

    protected fun getTruthOperand(): PsiExpression {
        return getTruthOperand(getPsiPrefixExpression())
    }

    protected fun getPsiPrefixExpression(): PsiPrefixExpression {
        return prefixExpPointer!!.element as PsiPrefixExpression
    }

    private fun findNegation(element: PsiElement): PsiPrefixExpression? {
        var ancestor = element
        while (ancestor !is PsiExpression) {
            ancestor = ancestor.parent

            if (ancestor is PsiCodeBlock) {
                return null
            }
        }

        while (ancestor !is PsiPrefixExpression) {
            ancestor = ancestor.parent
        }
        return ancestor;
    }

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        val prefixExpression = findNegation(element) ?: return false
        if (JavaTokenType.EXCL == prefixExpression.operationSign.tokenType) {
            prefixExpPointer = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(prefixExpression)
            return true
        }
        return false
    }

    override fun getFamilyName(): @IntentionFamilyName String {
        return "BooleanOperatorAction: getFamilyName"
    }
}