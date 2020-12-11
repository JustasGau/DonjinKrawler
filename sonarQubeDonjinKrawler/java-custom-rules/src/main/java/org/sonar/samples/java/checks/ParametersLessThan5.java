package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "ParametersLessThan5",
        name = "A function should not have more than 5 parameters",
        description = "Any function must not have more than 5 parameters in the declaration",
        priority = Priority.MINOR,
        tags = {"bug"})
public class ParametersLessThan5 extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodTree method = (MethodTree) tree;
        if (method.parameters().size() > 5) {
            reportIssue(method.simpleName(), "Too many parameters! Should not be more than 5 ");
        }
    }

}