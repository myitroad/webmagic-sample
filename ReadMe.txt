Sample for Using WebMagic
=========================

## abstract
从食药监网站获取数据的简单实现。
数据页面参见http://app2.sfda.gov.cn/datasearchp/gzcxSearch.do?formRender=cx&optionType=V1.

为实现药品详细数据获取，分两步进行：
- 获取药品名称
- 通过药品名称获取药品详细信息

这里采用生产-消费模式，DrugNameConsumer是药品名称的生产者，DrugNameProducter是药品名称的消费者，两者通过阻塞队列进行通信。

## build&run
To build this project use

    mvn install

To run this project from within Maven use

    mvn exec:java

For more help see the Apache Camel documentation

    http://camel.apache.org/

