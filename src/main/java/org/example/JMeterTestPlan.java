package org.example;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;



    public class JMeterTestPlan {
        public static void main(String[] args) throws Exception {
            JMeterUtils.loadJMeterProperties("/home/dexter/Downloads/apache-jmeter-5.5/bin/jmeter.properties");
            JMeterUtils.setJMeterHome("/home/dexter/Downloads/apache-jmeter-5.5/bin/jmeter");

            StandardJMeterEngine jmeter = new StandardJMeterEngine();

            TestPlan testPlan = new TestPlan("My Test Plan");
            Arguments arguments = new Arguments();
            arguments.addArgument("param1", "value1");
            arguments.addArgument("param2", "value2");
            testPlan.setUserDefinedVariables(arguments);

            ThreadGroup threadGroup = new ThreadGroup();
            threadGroup.setName("My Thread Group");
            threadGroup.setNumThreads(10);
            threadGroup.setRampUp(1);
            threadGroup.setScheduler(true);
            threadGroup.setDuration(120);

            LoopController loopController = new LoopController();
            loopController.setLoops(10);
            loopController.setFirst(true);
            loopController.initialize();

            HTTPSampler httpSampler = new HTTPSampler();
            httpSampler.setDomain("example.com");
            httpSampler.setPath("/api/myendpoint");
            httpSampler.setMethod("GET");
            httpSampler.setName("My HTTP Request");

            ResultCollector resultCollector = new ResultCollector();


        }
    }