import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*

/**
 * @author Cnfn
 * @date 2021/08/02
 */
open class CommonNegationBooleanAction : AbstractNegationBooleanAction() {
    open protected fun getExp(): String {
        return "BooleanUtils.isFalse(a)"
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val prefixExpression = getPsiPrefixExpression()
        val operand = getTruthOperand(prefixExpression)

        val factory = JavaPsiFacade.getElementFactory(project)
        val psiNewExpression = factory.createExpressionFromText(getExp(), null) as PsiMethodCallExpression
        psiNewExpression.argumentList.expressions[0].replace(operand)
        prefixExpression.replace(psiNewExpression)
    }

    override fun getText(): @IntentionName String {
        return "Change to '${getExp()}'"
    }
}