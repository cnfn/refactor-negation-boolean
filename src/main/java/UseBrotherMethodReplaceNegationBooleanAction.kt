import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import org.apache.commons.lang3.BooleanUtils

/**
 * @author zhixiao.mzx
 * @date 2021/08/02
 */
class UseBrotherMethodReplaceNegationBooleanAction : AbstractNegationBooleanAction() {
    private var expAndTextEnum: ExpAndTextEnum? = null
    private var psiExpression: PsiExpression? = null

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val prefixExpression = getPsiPrefixExpression()

        val factory = JavaPsiFacade.getElementFactory(project)
        val psiNewExpression = factory.createExpressionFromText(expAndTextEnum!!.newExp, null) as PsiMethodCallExpression

        psiNewExpression.argumentList.expressions[0].replace(psiExpression!!)
        prefixExpression.replace(psiNewExpression)
    }

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        if (BooleanUtils.isFalse(super.isAvailable(project, editor, element))) {
            return false
        }

        val operand = getTruthOperand()
        if (operand !is PsiMethodCallExpression) {
            return false
        }

        for (item in ExpAndTextEnum.values()) {
            if (operand.text.startsWith(item.oldExp)) {
                expAndTextEnum = item
                psiExpression = operand.argumentList.expressions[0]
                return true
            }
        }

        return false
    }


    override fun getText(): @IntentionName String {
        return expAndTextEnum!!.text
    }
}