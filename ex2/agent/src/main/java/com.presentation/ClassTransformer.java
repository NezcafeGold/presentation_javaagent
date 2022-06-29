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

                ex1(ctClass);

                ex2(ctClass);

                ex3(ctClass);

                byteCode = ctClass.toBytecode();
                ctClass.detach();

            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return byteCode;
    }

    private void ex3(CtClass ctClass) throws CannotCompileException, NotFoundException {
        CtMethod method = ctClass.getDeclaredMethod("initialize");
        method.insertAfter("javax.swing.JOptionPane.showMessageDialog(null, \"PLEASE BUY LICENSE FOR USING THE TOOL\");  ");
    }

    private void ex2(CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtMethod method = ctClass.getDeclaredMethod("initialize");
        ctClass.addMethod(CtNewMethod.make("public void hack(){ frame.setTitle(\"HACKED BY XFIRM\"); }", ctClass));
        method.insertAfter("hack(); ");
    }

    private void ex1(CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtMethod method = ctClass.getDeclaredMethod("initialize");
        method.insertBefore("System.out.println(TITLE); ");
    }
}
