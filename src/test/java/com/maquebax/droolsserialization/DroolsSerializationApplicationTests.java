package com.maquebax.droolsserialization;

import org.drools.core.impl.KnowledgeBaseImpl;
import org.drools.kiesession.rulebase.InternalKnowledgeBase;
import org.drools.kiesession.rulebase.KnowledgeBaseFactory;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.MBeansOption;
import org.kie.api.io.Resource;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SealedObject;
import java.io.*;

@SpringBootTest
class DroolsSerializationApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testKnowledgeBaseReserialization(){
        {
            Object input1  = (Object) getKnowledgeBase();

            try {
                byte[] barray = methodToMarshall(input1);
                Object readObj = methodToUnMarshall(barray);
                KnowledgeBaseImpl input2 = (KnowledgeBaseImpl) readObj;


                byte[] barray2 = methodToMarshall(input2);
                readObj = methodToUnMarshall(barray2);
                KnowledgeBaseImpl input3 = (KnowledgeBaseImpl) readObj;

                System.out.println("***********Byte Array 1 : " +barray.length);
                System.out.println("***********Byte Array 2 : " +barray2.length);

                assert barray.length == barray2.length;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    private static byte[] methodToMarshall(Object input) throws IOException {
        byte[] content = null;
        ObjectOutputStream outputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            outputStream = new ObjectOutputStream(baos);
            outputStream.writeObject(input);
            content = baos.toByteArray();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            baos.close()   ;
        }

        return content ;
    }

    private static Object methodToUnMarshall( byte[] content) throws IOException {

        ObjectInputStream inputStream = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        try {

            inputStream = new ObjectInputStream(bais);
            KnowledgeBaseImpl readObj  =  (KnowledgeBaseImpl) inputStream.readObject();


            return readObj;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            bais.close();
        }
    }

    private  KieBase getKnowledgeBase() {

        KieBaseConfiguration config = KieServices.get().newKieBaseConfiguration();
        config.setOption(MBeansOption.ENABLED);
        InternalKnowledgeBase knowledgeBase =   KnowledgeBaseFactory.newKnowledgeBase("knowledgeBase", config);
        return knowledgeBase;
    }

    private void testMethod(){
        KnowledgeBuilder kb=  KnowledgeBuilderFactory.newKnowledgeBuilder();
        Resource r = ResourceFactory.newByteArrayResource(new byte[0]);
//         kb.add(r,);
    }
}
