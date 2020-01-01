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
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);
        System.out.println("init flow Rules OK...");
    }

    /**
     * Example of Sentinel Hello World
     */
    @Test
    public void helloSentinel() {
        Entry entry = null;
        for (; ; ) {
            try {
                SphU.entry("HelloWorld");
                System.out.println("业务代码开始");
                Thread.sleep(5);
            } catch (BlockException | InterruptedException e) {
                System.out.println("业务堵塞了！");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }

    }
}
