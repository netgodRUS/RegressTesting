package org.example;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import static org.apache.jmeter.util.JMeterUtils.*;

public class JMeterRunner {
    public static void main(String[] args) {
        // Set JMeter properties
        loadJMeterProperties("/home/dexter/Downloads/apache-jmeter-5.5/bin/jmeter.properties");

        // Initialize JMeter engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        // Load test plan
        HashTree testPlanTree = new HashTree();
        TestPlan testPlan = new TestPlan("My Test Plan");
        testPlanTree.add(testPlan);
        testPlanTree.add(testPlan, JMeterUtils.loadProperties("/path/to/testplan.jmx"));

        // Add result collector and summarizer
        ResultCollector resultCollector = new ResultCollector(new Summariser());
        testPlanTree.add(testPlanTree.getArray()[0], resultCollector);

        // Run test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }

}

