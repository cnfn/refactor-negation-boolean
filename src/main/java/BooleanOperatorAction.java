import java.util.Objects;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiParenthesizedExpression;
import com.intellij.psi.PsiPrefixExpression;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhixiao.mzx
 * @date 2021/08/02
 */
public class BooleanOperatorAction extends PsiElementBaseIntentionAction {
    public static final String ACTION_HINT_TEXT = "BooleanOperatorAction: getText";

    private PsiPrefixExpression prefixExpression;

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element)
        throws IncorrectOperationException {
        String exp = "BooleanUtils.isFalse(a)";
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        PsiMethodCallExpression psiNewExpression = (PsiMethodCallExpression)factory.createExpressionFromText(exp, null);

        psiNewExpression.getArgumentList().getExpressions()[0].replace(
            Objects.requireNonNull(prefixExpression.getOperand()));

        prefixExpression.replace(psiNewExpression);
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        PsiElement context = element.getContext();

        if (BooleanUtils.isFalse(context instanceof PsiPrefixExpression)) {
            context = element.getParent().getContext();
        }
        return checkPsiPrefixExpression(context);
        //
        //if (context instanceof PsiBinaryExpression && context.getChildren().length == 0) {
        //    context = context.getParent().getContext();
        //}
        //
        //if (context instanceof PsiParenthesizedExpression) {
        //    context = context.getParent();
        //}
        //
        //return checkPsiPrefixExpression(context);
    }

    /**
     * !x
     *
     * @param context
     * @return
     */
    private boolean checkPsiPrefixExpression(PsiElement context) {
        if (BooleanUtils.isFalse(context instanceof PsiPrefixExpression)) {
            return false;
        }

        prefixExpression = (PsiPrefixExpression)context;
        return Objects.equals(JavaTokenType.EXCL, prefixExpression.getOperationSign().getTokenType());
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
