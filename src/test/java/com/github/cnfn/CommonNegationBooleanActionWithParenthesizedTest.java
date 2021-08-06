package com.github.cnfn;

import com.github.cnfn.action.CommonNegationBooleanAction;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Assert;

public class CommonNegationBooleanActionWithParenthesizedTest extends LightJavaCodeInsightFixtureTestCase {

    /**
     * Defines path to files used for running tests.
     *
     * @return The path from this module's root directory ({@code $MODULE_WORKING_DIR$}) to the
     * directory containing files for these tests.
     */
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/CommonNegationBooleanActionWithParenthesized";
    }

    protected void doTest(String testName, String hint) {
        myFixture.configureByFile(testName + ".java");
        final IntentionAction action = myFixture.findSingleIntention(hint);
        Assert.assertNotNull(action);
        myFixture.launchAction(action);
        myFixture.checkResultByFile(testName + ".after.java");
    }

    public void testIntention() {
        doTest("before.template", new CommonNegationBooleanAction().getText());
    }

}
