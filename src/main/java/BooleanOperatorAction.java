import java.util.Objects;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiParenthesizedExpression;
import com.intellij.psi.PsiPrefixExpression;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhixiao.mzx
 * @date 2021/08/02
 */
public class BooleanOperatorAction extends PsiElementBaseIntentionAction {
    public static final String ACTION_HINT_TEXT = "BooleanOperatorAction: getText";

    private SmartPsiElementPointer<PsiElement> prefixExpPointer;

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element)
        throws IncorrectOperationException {
        String exp = "BooleanUtils.isFalse(a)";
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        PsiMethodCallExpression psiNewExpression = (PsiMethodCallExpression)factory.createExpressionFromText(exp, null);

        PsiPrefixExpression prefixExpression = (PsiPrefixExpression)prefixExpPointer.getElement();
        PsiExpression operand = Objects.requireNonNull(prefixExpression.getOperand());
        if (operand instanceof PsiParenthesizedExpression) {
            operand = ((PsiParenthesizedExpression)operand).getExpression();
            assert operand != null;
        }

        psiNewExpression.getArgumentList().getExpressions()[0].replace(operand);
        prefixExpression.replace(psiNewExpression);
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        PsiElement context = element.getContext();

        if (BooleanUtils.isFalse(context instanceof PsiPrefixExpression)) {
            context = element.getParent().getContext();
        }
        assert context != null;
        if (context instanceof PsiPrefixExpression) {
            PsiPrefixExpression prefixExpression = (PsiPrefixExpression)context;
            if (JavaTokenType.EXCL.equals(prefixExpression.getOperationSign().getTokenType())) {
                prefixExpPointer =
                    SmartPointerManager.getInstance(project).createSmartPsiElementPointer(prefixExpression);
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "BooleanOperatorAction: getFamilyName";
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return ACTION_HINT_TEXT;
    }
}
