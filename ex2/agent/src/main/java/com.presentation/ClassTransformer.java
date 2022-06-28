package com.presentation;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(final ClassLoader loader,
                            final String className,
                            final Class<?> classBeingRedefined,
                            final ProtectionDomain protectionDomain,
                            final byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;

       // if ("com.thiz.was.written.not.by.me.honestly.calc.CreditCalcWithWB".equals(className.replaceAll("/", "."))) {

            System.out.println(className);
            ClassPool pool = ClassPool.getDefault();
            //pool.appendClassPath(new LoaderClassPath(loader));
            try {
        System.out.println("1");

                CtClass ctClass = pool.get("com.thiz.was.written.not.by.me.honestly.calc.CreditCalcWithWB");
        System.out.println("SSDADASD");



                System.out.println("Class was loaded successful!");

                //CtField titleField = ctClass.getField("TITLE");
//                ctClass.removeField(titleField);
//
//                CtField newTitle = CtField.make("public final String TITLE = \"HACKED BY XFIRM\";", ctClass);
//                ctClass.addField(newTitle);
//
//                byteCode = ctClass.toBytecode();
//                ctClass.detach();
            } catch (NotFoundException e) {
                System.out.println("ssadas");
            }

      // }


        return byteCode;
    }
}
