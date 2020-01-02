package com.sdttttt.helloworld.test;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * This one sentinel Hello world of sdttttt
 *
 * @author sdttttt
 */
public class HelloTest {

    @Before
    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        // QPS is request per second
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(30);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);
        System.out.println("init flow Rules OK...");
    }

    /**
     * Example of Sentinel Hello World
     */
    @Test
    public void helloSentinel() throws InterruptedException {
        Entry entry = null;
        try {
            SphU.entry("HelloWorld");
            System.out.println("业务代码开始");
            Thread.sleep(25);
        } catch (BlockException | InterruptedException e) {
            System.out.println("业务堵塞了！");
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    /**
     * 1000 request per second
     * QPS set to 30
     * <p>
     * That's a maximum of 30 request per second,
     * with the remaining 970 discarded
     */
    @Test
    public void qps30Test() throws InterruptedException {
        Entry entry = null;
        for (; ; ) {
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                try {
                    SphU.entry("HelloWorld");
                    System.out.println("业务代码开始" + i);
                } catch (BlockException e) {
                    System.out.println("业务堵塞了！" + i);
                } finally {
                    if (entry != null) {
                        entry.exit();
                    }
                }
            }
        }
    }
}
