package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "BigFunction",
        name = "Small functions",
        description = "A function should not exceed 50 lines of code",
        priority = Priority.MINOR,
        tags = {"bug"})
public class BigFunction extends IssuableSubscriptionVisitor {

    public int max = 50;

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodTree methodTree = (MethodTree) tree;
        if (methodTree.block() == null) {
            return;
        }
        int methodSize = methodTree.block().body().size();
        if (methodSize > max) {
            reportIssue(methodTree, "Reduce this Function from " + methodSize + " lines to " + max);
        }
    }

}