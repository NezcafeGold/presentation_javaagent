package com.presentation;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;

public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassTransformer());
    }
}
