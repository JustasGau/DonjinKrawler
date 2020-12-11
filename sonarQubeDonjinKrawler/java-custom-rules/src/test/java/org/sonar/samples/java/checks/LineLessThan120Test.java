package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class LineLessThan120Test {

    @Test
    public void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/LineLessThan120.java")
                .withCheck(new LineLessThan120())
                .verifyIssues();
    }

}