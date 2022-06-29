package com.presentation;

import javassist.*;

import javax.swing.*;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

// java -Xbootclasspath/a:.\ex2\agent\build\libs\javassist-3.29.0-GA.jar -javaagent:.\ex2\agent\build\libs\agent.jar -jar .\ex2\CreditCalc.jar
public class ClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(final ClassLoader loader,
                            final String className,
                            final Class<?> classBeingRedefined,
                            final ProtectionDomain protectionDomain,
                            final byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;

        ClassPool pool = ClassPool.getDefault();
        if ("com.thiz.was.written.not.by.me.honestly.calc.CreditCalcWithWB".equals(className.replaceAll("/", "."))) {

            try {


                CtClass ctClass = pool.get("com.thiz.was.written.not.by.me.honestly.calc.CreditCalcWithWB");


                CtMethod method = ctClass.getDeclaredMethod("initialize");
                method.insertBefore("System.out.println(TITLE); ");


                ctClass.addMethod(CtNewMethod.make("public void hack(){ frame.setTitle(\"HACKED BY XFIRM\"); }", ctClass));
                method.insertAfter("hack(); ");


                byteCode = ctClass.toBytecode();
                ctClass.detach();

            } catch (NotFoundException e) {
                System.out.println("ssadas");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        return byteCode;
    }
}
