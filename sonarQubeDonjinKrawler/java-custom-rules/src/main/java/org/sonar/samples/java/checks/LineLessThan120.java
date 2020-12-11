package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.SyntaxToken;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "LineLessThan120",
        name = "A line should not be longer than 120 symbols",
        description = "Any line in code should not reach over 120 symbols. The rule is important to increase readability and promote concise comments",
        priority = Priority.MINOR,
        tags = {"bug"})
public class LineLessThan120 extends IssuableSubscriptionVisitor {

    int firstFoundLine = 0;

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.TOKEN);
    }

    @Override
    public void visitToken(SyntaxToken lastToken){
        if (lastToken.column() > 120 && lastToken.line() != firstFoundLine) {
            firstFoundLine = lastToken.line();
            reportIssue(lastToken, "Lines is too long, maximum 120 symbols allowed");
        }
    }

}