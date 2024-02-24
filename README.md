Serializing the deserailized KnowledgeBaseImpl object gives a byte array of different size . This behaviour is seen in Java 17 and wasnt observed in Java 8 and 11.
This project demonstrates this behaviour with simple test case.

**Java 17 Behaviour**
![image](https://github.com/maquebax/DroolsSerialization/assets/5489927/ff9b0b52-94f4-4bad-b5ef-cefb03b7b832)


**Java 11 Behaviour**
![image](https://github.com/maquebax/DroolsSerialization/assets/5489927/2de9abd2-0f8f-4734-af4e-badfcb04b629)
