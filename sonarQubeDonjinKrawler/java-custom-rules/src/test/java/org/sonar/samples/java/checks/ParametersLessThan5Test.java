package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ParametersLessThan5Test {

    @Test
    public void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/ParametersLessThan5.java")
                .withCheck(new ParametersLessThan5())
                .verifyIssues();
    }

}