package com.playground.samplewebapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

@Ignore
public abstract class BaseTest {
    protected Logger logger = LogManager.getLogger(this.getClass());

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void starting(Description d) {
            logger.info("Starting test: " + d.getMethodName());
        }

        @Override
        protected void succeeded(Description d) {
            logger.info(d.getMethodName() + " succeeded");
        }

        @Override
        protected void failed(Throwable e, Description d) {
            logger.info(d.getMethodName() +" failed " + e.getMessage());
        }

        @Override
        protected void finished(Description d) {
            logger.info(d.getMethodName() + " completed");
        }
    };
}
