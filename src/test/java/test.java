import kg.apc.jmeter.threads.UltimateThreadGroup
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui
import org.apache.jmeter.protocol.http.sampler.HTTPSampler
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.testelement.TestPlan
import org.apache.jmeter.threads.ThreadGroup
import org.apache.jmeter.util.JMeterUtils


public class test {


JMeterUtils.setProperty("jmeter.save.saveservice.output_format", "xml")

    TestPlan testPlan = new TestPlan("My Test Plan")
testPlan.setEnabled(true)

    ThreadGroup threadGroup = new UltimateThreadGroup()
threadGroup.setName("My Thread Group")
        threadGroup.setNumThreads(10)
        threadGroup.setRampUp(5)
        threadGroup.setDuration(60)
        threadGroup.setScheduler(true)
        threadGroup.setDelay(0)

    HTTPSampler httpSampler = new HTTPSampler()
httpSampler.setName("My HTTP Sampler")
        httpSampler.setEnabled(true)
        httpSampler.setDomain("91.244.183.36")
        httpSampler.setPort(30012)
        httpSampler.setPath("/")
        httpSampler.setMethod("GET")

    HTTPArgument httpArgument = new HTTPArgument()
httpArgument.setName("myParam")
        httpArgument.setValue("myValue")
        httpArgument.setAlwaysEncoded(false)

        httpSampler.addArgument(httpArgument)

    HttpDefaultsGui httpDefaults = new HttpDefaultsGui()
httpDefaults.setName("My HTTP Defaults")
        httpDefaults.setEnabled(true)
        httpDefaults.setProperty("HTTPSampler.path", "/")
        httpDefaults.setProperty("HTTPSampler.method", "GET")
        httpDefaults.setProperty("HTTPSampler.port", "30012")
        httpDefaults.setProperty("HTTPSampler.domain", "91.244.183.36")

    Arguments arguments = new Arguments()
arguments.addArgument("myArg", "myValue")

        httpDefaults.setProperty("HTTPsampler.Arguments", arguments)

            testPlan.addThreadGroup(threadGroup)
            testPlan.addSampler(httpSampler)
            testPlan.addTestElement(httpDefaults)

            return testPlan

}
