package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BigFunctionTest {

    @Test
    public void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/BigFunction.java")
                .withCheck(new BigFunction())
                .verifyIssues();
    }

}