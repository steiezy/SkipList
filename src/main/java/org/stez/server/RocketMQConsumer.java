package org.stez.server;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.stez.skiplist.SkipList;

import java.util.List;

public class RocketMQConsumer {


    private final DefaultMQPushConsumer consumer;

    private final SkipList skipList;
    public RocketMQConsumer(SkipList skipList) {
        this.skipList = skipList;
        // 初始化 RocketMQ 消费者
        consumer = new DefaultMQPushConsumer("DefaultGroup");
        consumer.setNamesrvAddr("52.231.225.13:9876");

        // 设置消费者的订阅关系
        try {
            consumer.subscribe("skipListUpdateTopic", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        // 注册消息监听器
        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
            for (MessageExt message : msgs) {
                    try {
                        String jsonMessage = new String(message.getBody());
                        UpdateMessage myMessage = JSON.parseObject(jsonMessage, UpdateMessage.class);

                        // 处理消息
                        System.out.println("Received message: nodeName=" + myMessage.getNodeName() + ", score=" + myMessage.getScore());
                        boolean result = skipList.updateNodeStatus(myMessage.getNodeName(), myMessage.getScore());

                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }
    static class UpdateMessage{
        private String nodeName;
        private int score;

        // getters and setters
        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }


    /**
     * 启动 RocketMQ 消费者
     */
    public void start() throws MQClientException {
        consumer.start();
        System.out.println("RocketMQ Consumer started.");
    }

    /**
     * 停止 RocketMQ 消费者
     */
    public void shutdown() {
        if (consumer != null) {
            consumer.shutdown();
            System.out.println("RocketMQ Consumer shutdown.");
        }
    }


}
