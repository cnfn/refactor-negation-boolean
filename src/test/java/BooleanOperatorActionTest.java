// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Assert;

public class BooleanOperatorActionTest extends LightJavaCodeInsightFixtureTestCase {

  /**
   * Defines path to files used for running tests.
   *
   * @return The path from this module's root directory ({@code $MODULE_WORKING_DIR$}) to the
   * directory containing files for these tests.
   */
  @Override
  protected String getTestDataPath() {
    return "src/test/testData/BooleanOperatorAction";
  }

  protected void doTest(String testName, String hint) {
    myFixture.configureByFile(testName + ".java");
    final IntentionAction action = myFixture.findSingleIntention(hint);
    Assert.assertNotNull(action);
    myFixture.launchAction(action);
    myFixture.checkResultByFile(testName + ".after.java");
  }

  public void testIntention() {
    doTest("before.template", BooleanOperatorAction.ACTION_HINT_TEXT);
  }

}
